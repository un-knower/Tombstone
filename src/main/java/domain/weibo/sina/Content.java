package domain.weibo.sina;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_sinaweibo_content")
public class Content {

	private String title;
	private String author;
	private String url;
	private String createTime;
	private String attitudes_count;	//赞数
	private String comments_count;	//评论数
	private String reposts_count;	//回复数
	private String source;	//来源
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAttitudes_count() {
		return attitudes_count;
	}
	public void setAttitudes_count(String attitudes_count) {
		this.attitudes_count = attitudes_count;
	}
	public String getComments_count() {
		return comments_count;
	}
	public void setComments_count(String comments_count) {
		this.comments_count = comments_count;
	}
	public String getReposts_count() {
		return reposts_count;
	}
	public void setReposts_count(String reposts_count) {
		this.reposts_count = reposts_count;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public static void main(String[] args) {
		GlobalComponent.dbBean.create_table(Content.class);
	}
}
