package business.toutiao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import business.BasePageProcessor;
import conf.db.GlobalComponent;
import domain.toutiao.Content;
import domain.toutiao.Reply;
import domain.toutiao.User;
import lombok.extern.slf4j.Slf4j;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.JsonPathSelector;
import utils.SeleniumUtils;

/**
 * 今日头条 [ajax获取数据]
 * 表：
 * 表1：内容信息
 * 表2：发布者信息
 * 表3：回复内容
 * @author xingwuzhao
 *
 */
@Slf4j
public class TouTiaoProcessor extends BasePageProcessor {

	private String list_regex = "http://www.toutiao.com/api/pc/feed/?category=";
	private String toutiao_url = "http://www.toutiao.com/api/pc/feed/?category=%s&utm_source=toutiao&widen=%s&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A1E559112451042&cp=5914511014B23E1";
	
	private String content_regex = "http://www.toutiao.com/a\\d+\\/";
	private String common_regex = "http://www.toutiao.com/api/comment/list/?group_id=";
	private String user_url_regex = "http://www.toutiao.com/c/user/\\d+/";
	
	private String keyword;
	private int count;
	
	public TouTiaoProcessor(int count, String keyword) {
		this.count = count;
		this.keyword = keyword;
	}
	
	@Override
	public void process(Page page) {
		if (StringUtils.contains(page.getUrl().toString(), list_regex)) {
			this.pushList(page);
		} else if (page.getUrl().regex(content_regex).match()) {
			this.contentExec(page);
		} else if (StringUtils.contains(page.getUrl().toString(), common_regex)) {
			this.commonExec(page);
		} else if (page.getUrl().regex(user_url_regex).match()) {
			this.userExec(page);
		} else {
			System.out.println("else....." + page.getUrl());
		}
	}
	
	private void pushList(Page page){
		List<String> lists = new JsonPathSelector("$.data[*]").selectList(page.getRawText());
		if (CollectionUtils.isNotEmpty(lists)) {
			for (String list : lists) {
				try {
					String group_id = new JsonPathSelector("$.group_id").select(list);
					String comments_count = new JsonPathSelector("$.comments_count").select(list);
					String chinese_tag = new JsonPathSelector("$.chinese_tag").select(list);
					
					String media_url = "";
					String userid = "";
					
					Request request = new Request();
					Map<String, Object> extras = new HashMap<String, Object>();
					
					if(StringUtils.contains(list, "media_url")){
						media_url = new JsonPathSelector("$.media_url").select(list);
						userid = StringUtils.substringAfter(media_url, "/c/user/");
						request = new Request();
						request.setUrl("http://www.toutiao.com/c/user/" + userid);
						extras = new HashMap<String, Object>();
						extras.put("userid", userid);
						request.setExtras(extras);
						page.addTargetRequest(request);
					}
					
					request = new Request();
					extras = new HashMap<String, Object>();
					extras.put("commons", comments_count);
					extras.put("chinese_tag", chinese_tag);
					request.setExtras(extras);
					request.setUrl("http://www.toutiao.com/a" + group_id + "/");
					page.addTargetRequest(request);
					
				} catch (Exception e) {
					log.error(e.getMessage() + ">>>> " + list);
				}
			}
			for (int i = 2; i <= count; i++) {
				page.addTargetRequest(String.format(toutiao_url, keyword, i));
			}
		}
	}
	
	private void contentExec(Page page){
		
		String title = page.getHtml().xpath("//*[@id=\"article-main\"]/h1/text()").toString();
		if(title == null || title.length() == 0){
			return;
		}
		String author = page.getHtml().xpath("//*[@id=\"article-main\"]/div[1]/span[1]/text()").toString();
		String url = page.getUrl().toString();
		String createTime = page.getHtml().xpath("//*[@id=\"article-main\"]/div[1]/span[@class='time']/text()").toString();
		String comments_count = page.getRequest().getExtra("commons").toString();
		String chinese_tag = page.getRequest().getExtra("chinese_tag").toString();
		
		GlobalComponent.dbBean.insert_data(Content.class, title, author, url, createTime, comments_count, chinese_tag);

		String item_id = StringUtils.substringBetween(page.getRawText(), "item_id: '", "',");
		String group_id = StringUtils.substringBetween(page.getRawText(), "group_id: '", "',");
		String commons_url  = "http://www.toutiao.com/api/comment/list/?group_id=%s&item_id=%s&offset=0&count=20";
		
		Request request = new Request();
		request.setUrl(String.format(commons_url, group_id, item_id));
		Map<String, Object> extras = new HashMap<String, Object>();
		extras.put("title", title);
		request.setExtras(extras);
		page.addTargetRequest(request);
	}
	
	private void userExec(Page page){
		String author_name = StringUtils.substringBetween(page.getRawText(), "name: '", "',");
		String author_url = page.getUrl().toString();
		
		String html = SeleniumUtils.seleniumExec(page.getUrl().toString());
		Document doc = Jsoup.parse(html);
		Elements els = doc.getElementsByAttributeValue("riot-tag", "number");
		if(els == null || els.size() == 0){
			GlobalComponent.dbBean.insert_data(User.class, author_name, author_url, 0, 0);
		}else{
			GlobalComponent.dbBean.insert_data(User.class, author_name, author_url, els.get(0).getElementsByAttribute("number").attr("number"), els.get(1).getElementsByAttribute("number").attr("number"));
		}
	}
	
	private void commonExec(Page page) {
		if (StringUtils.contains(page.getUrl().toString(), "&offset=1&count=20")) {
			int pageNum = 0;
			int total_commons = Integer.parseInt(new JsonPathSelector("$.data[*].total").select(page.getRawText()));
			if (total_commons % 20 >= 0) {
				pageNum = (total_commons / 20) + 1;
			} else {
				pageNum = 1;
			}
			String commons_url_left = StringUtils.substringBefore(page.getUrl().toString(), "&offset=");
			for (int i = 1; i <= pageNum; i++) {
				page.addTargetRequest(commons_url_left + "&offset=" + i + "&count=20");
			}
		}

		List<String> total_comments = new JsonPathSelector("$.data[*].comments").selectList(page.getRawText());
		for (String comment : total_comments) {
			String title = page.getRequest().getExtra("title").toString();
			String content = new JsonPathSelector("$.text").select(comment);
			String author = new JsonPathSelector("$.user.name").select(comment);
			String userid = new JsonPathSelector("$.user.user_id").select(comment);
			String authorurl = "http://www.toutiao.com/c/user/" + userid + "/";
			String createTime = new JsonPathSelector("$.create_time").select(comment);
			String upvote = new JsonPathSelector("$.digg_count").select(comment);
			GlobalComponent.dbBean.insert_data(Reply.class, title, content, author, authorurl, createTime, upvote);
			
			page.addTargetRequest(authorurl);
		}

	}
}
