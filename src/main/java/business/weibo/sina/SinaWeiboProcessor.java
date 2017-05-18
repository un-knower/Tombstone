package business.weibo.sina;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import business.BasePageProcessor;
import conf.db.GlobalComponent;
import domain.weibo.sina.Content;
import domain.weibo.sina.User;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * 主要是 根据内容搜索 微博 获取评论用户、转发用户信息
 * 微博默认只有1000行 每页显示10行 也就是 100页
 * @author xingwuzhao
 *
 */
public class SinaWeiboProcessor extends BasePageProcessor {

	private String list_url_regex = "https://m.weibo.cn/api/container/getIndex?type=wb&queryVal=";
	private String comments_url = "https://m.weibo.cn/api/comments/show?id=%s";
	private String user_url = "https://m.weibo.cn/api/container/getIndex?type=uid&value=";
	private String user_url_regex = "https://m.weibo.cn/api/container/getIndex?type=uid&value=";
	private String comments_url_regex = "https://m.weibo.cn/api/comments/show?id=";
	private String attitudes_url_regex = "https://m.weibo.cn/api/statuses/repostTimeline?id=";
	
	@Override
	public void process(Page page) {
		if(StringUtils.contains(page.getUrl().toString(), list_url_regex)){
			if(StringUtils.contains(page.getUrl().toString(), "&page=")){
				this.contentExec(page);
			}else{
				this.pushList(page);
			}
		}else if(StringUtils.contains(page.getUrl().toString(), user_url_regex)){
			this.userExec(page);
		}else if(StringUtils.contains(page.getUrl().toString(), comments_url_regex)){
			this.commentsExec(page);
		}else if(StringUtils.contains(page.getUrl().toString(), attitudes_url_regex)){
			this.attitudesExec(page);
		}else{
			System.out.println("no path match!");
		}
	}
	
	private void pushList(Page page){
		String total = new JsonPathSelector("$.cardlistInfo.total").select(page.getRawText());
		int maxCount = Integer.parseInt(total);
		int maxPage = 0;
		if(maxCount % 10 == 0){
			maxPage = maxCount / 10;
		}else{
			maxPage = maxCount / 10 + 1;
		}
		for (int i = 1; i <= maxPage; i++) {
			page.addTargetRequest(page.getUrl().toString() + "&page=" + i);
		}
	}
	
	private void contentExec(Page page){
		List<String> lists = new JsonPathSelector("$.cards[0].card_group[*]").selectList(page.getRawText());
		for (String s : lists) {
			String title = new JsonPathSelector("$.mblog.text").select(s);
			String author = new JsonPathSelector("$.mblog.user.screen_name").select(s);
			String content_id = new JsonPathSelector("$.mblog.id").select(s);
			String url = String.format(comments_url, content_id);
			String createTime = new JsonPathSelector("$.mblog.created_at").select(s);
			String attitudes_count = new JsonPathSelector("$.mblog.attitudes_count").select(s);
			String comments_count = new JsonPathSelector("$.mblog.comments_count").select(s);
			String reposts_count = new JsonPathSelector("$.mblog.reposts_count").select(s);
			String author_id = new JsonPathSelector("$.mblog.user.id").select(s);
			
			GlobalComponent.dbBean.insert_data(Content.class, title, author, url, createTime, attitudes_count, comments_count, reposts_count);
			
			page.addTargetRequest(user_url + author_id);
			//推送评论url
			page.addTargetRequest(url);
			//推送转发url
			page.addTargetRequest("https://m.weibo.cn/api/statuses/repostTimeline?id=" + content_id);
		}
	}
	
	private void userExec(Page page){
		String screen_name = new JsonPathSelector("$.userInfo.screen_name").select(page.getRawText());
		String profile_url = new JsonPathSelector("$.userInfo.profile_url").select(page.getRawText());
		String statuses_count = new JsonPathSelector("$.userInfo.statuses_count").select(page.getRawText());
		String verified = new JsonPathSelector("$.userInfo.verified").select(page.getRawText());
		String description = new JsonPathSelector("$.userInfo.description ").select(page.getRawText());
		String gender = new JsonPathSelector("$.userInfo.gender ").select(page.getRawText());
		String followers_count = new JsonPathSelector("$.userInfo.followers_count ").select(page.getRawText());
		String follow_count = new JsonPathSelector("$.userInfo.follow_count").select(page.getRawText());
		
		GlobalComponent.dbBean.insert_data(User.class, screen_name, profile_url, statuses_count, verified, description, gender, followers_count, follow_count);
	}
	
	private void commentsExec(Page page){
		String msg = new JsonPathSelector("$msg").select(page.getRawText());
		if(!"数据获取成功".equals(msg)){
			return;
		}
		int max  = Integer.parseInt(new JsonPathSelector("$.max").select(page.getRawText()));
		if(!StringUtils.contains(page.getUrl().toString(), "&page=")){
			for (int i = 1; i <= max; i++) {
				page.addTargetRequest(page.getUrl().toString() + "&page=" + i);
			}
		}else{
			List<String> lists = new JsonPathSelector("$data[*]").selectList(page.getRawText());
			
			for (String s : lists) {
				String author_id = new JsonPathSelector("$.user.id").select(s);
				page.addTargetRequest(user_url + author_id);
			}
		}
	}
	
	private void attitudesExec(Page page){
		String msg = new JsonPathSelector("$msg").select(page.getRawText());
		if(!"数据获取成功".equals(msg)){
			return;
		}
		int max  = Integer.parseInt(new JsonPathSelector("$.max").select(page.getRawText()));
		if(!StringUtils.contains(page.getUrl().toString(), "&page=")){
			for (int i = 1; i <= max; i++) {
				page.addTargetRequest(page.getUrl().toString() + "&page=" + i);
			}
		}else{
			List<String> lists = new JsonPathSelector("$data[*]").selectList(page.getRawText());
			for (String s : lists) {
				String author_id = new JsonPathSelector("$.user.id").select(s);
				page.addTargetRequest(user_url + author_id);
			}
		}
	}
}
