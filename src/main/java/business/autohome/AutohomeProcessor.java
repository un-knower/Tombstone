package business.autohome;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import business.BasePageProcessor;
import conf.db.GlobalComponent;
import conf.regex.AutoHomeRegex;
import domain.autohome.Content;
import domain.autohome.Reply;
import domain.autohome.User;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Selectable;

/**
 * 汽车之家 论坛[常规抓取]
 * 表：
 * 表1：内容信息
 * 表2：发布者信息
 * 表3：回复内容
 * @author xingwuzhao
 *
 */
public class AutohomeProcessor extends BasePageProcessor {

    private String url_list = "";  //列表 url
    private String url_post = "";  //内容 url
    private String url_user = "";  //用户 url
    private String url_user_info = "";  //用户信息 url
    private String url_user_other_format = "";
    private String url_user_format = ""; 
    
    public AutohomeProcessor() {
    	url_list = AutoHomeRegex.autoHomeRegex.url_list();
		url_post = AutoHomeRegex.autoHomeRegex.url_post();
		url_user = AutoHomeRegex.autoHomeRegex.url_user();
		url_user_info = AutoHomeRegex.autoHomeRegex.url_user_info();
		url_user_other_format = "http://i.service.autohome.com.cn/clubapp/OtherTopic-%s-all-1.html";
		url_user_format = "http://i.autohome.com.cn/%s/home.html";
	}
	
	public void process(Page page) {
        if (page.getUrl().regex(url_list).match()) {
        	this.pushList(page);
        }else if(page.getUrl().regex(url_post).match()) {  
        	this.contentExec(page);
        }else if(page.getUrl().regex(url_user).match()) {
        	this.pushUser(page);
        }else if(page.getUrl().regex(url_user_info).match()) {
        	this.userExec(page);
        }
	}
	
	/**
	 * 分析列表 推送url
	 * @param page
	 */
	private void pushList(Page page){
		//获取所有a标签下class为a_topic的所有link 集合 并和内容url 匹配
        List<String> l_post = page.getHtml().xpath("//*[@id='subcontent']").links().regex(url_post).all();
        List<String> l_url = page.getHtml().links().regex(url_list).all();    //所有的列表  
        page.addTargetRequests(l_post);  
        page.addTargetRequests(l_url);
	}
	
	/**
	 * 持久化内容信息
	 * @param page
	 */
	private void contentExec(Page page){
		String title = page.getHtml().xpath("//*[@id='F0']/div[2]/div[2]/h1/div/text()").toString();
    	String author = page.getHtml().xpath("//*[@id='F0']/div[1]/ul[1]/li[1]/a/text()").toString();
    	String createTime = page.getHtml().xpath("//*[@id='F0']/div[2]/div[1]/span[2]/text()").toString();
    	
    	if(title == null || author == null || createTime == null){
    		return;
    	}
    	// 插入内容表
        GlobalComponent.dbBean.insert_data(Content.class, title, author, page.getUrl().toString(), createTime);
       
        if(StringUtils.contains(page.getUrl().toString(), "-1.html")){
        	String pageCount = StringUtils.deleteWhitespace(page.getHtml().css("#x-pages1 > span.gopage > span", "text").toString())
            		.replace("页", "").replace("/", "");
            int maxCount = 0;
            if(pageCount != null && pageCount.length() != 0){
            	maxCount = Integer.parseInt(pageCount);
            	List<String> urls = new ArrayList<String>();
            	String page_url = page.getUrl().toString();
            	page_url = page_url.replace("-1.html", "-%s.html");
                for (int i = 2; i <= maxCount; i++) {
                	urls.add(String.format(page_url, i));
        		}
                page.addTargetRequests(urls);
            }
        }
        
        //获取所有内容页用户url
        List<String> l_user = page.getHtml().xpath("a[@xname='uname']").regex(url_user).all();
        page.addTargetRequests(l_user);
        
        //回复信息
    	Selectable reply_div = page.getHtml().xpath("//*[@id='maxwrap-reply']/div");
    	List<Selectable> repls = reply_div.nodes();
    	for (Selectable selectable : repls) {
    		String reply_author = selectable.xpath("//*[@class='c01439a']/text()").toString().trim();
    		String reply_content = selectable.xpath("//*[@class='x-reply font14']/div/text()").toString().trim();
        	String reply_authorurl = selectable.xpath("//*[@class='c01439a']/@href").toString().trim();
        	String reply_createTime = selectable.xpath("//*[@class='plr26 rtopconnext']/span[@xname='date']/text()").toString().trim();
        	//插入回复表
            GlobalComponent.dbBean.insert_data(Reply.class, title, reply_content, reply_author, reply_authorurl, reply_createTime);
		}
	}
	
	/**
	 * 分析用户 推送用户信息
	 * @param page
	 */
	private void pushUser(Page page){
		String author_id = StringUtils.substringAfterLast(page.getHtml().xpath("//*[@id='leftsideBar']/div/div[1]/a").links().toString(), "/");
		if(!StringUtils.isNumeric(author_id)){
			return;
		}
		page.addTargetRequest(String.format(url_user_other_format, author_id));
	}
	
	/**
	 * 持久化用户信息
	 * @param page
	 */
	private void userExec(Page page){
		String author = page.getHtml().xpath("//title/text()").toString().replace("的论坛主帖_汽车之家", "");
		String JHTopicCount = page.getHtml().xpath("/html/body/div[1]/div/ul[1]/li[1]/a[1]/text()").toString();
    	JHTopicCount = StringUtils.replace(JHTopicCount, "篇精华帖", "");
    	String TopicCount = page.getHtml().xpath("//*[@id='lnkTopicCount']/text()").toString();
    	TopicCount = TopicCount.replace("篇帖子", "");
		String auth_id = page.getHtml().xpath("/html/body/ul/li[1]/a").links().toString()
				.replace("http://i.autohome.com.cn/", "")
				.replace("/club/topic", "");
        // 插入用户表
        GlobalComponent.dbBean.insert_data(User.class, author, String.format(url_user_format, auth_id), TopicCount, JHTopicCount);
	}
}