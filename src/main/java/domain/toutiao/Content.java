package domain.toutiao;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_toutiao_content")
public class Content {

	private String title;
	private String author;
	private String url;
	private String createTime;
	private String comments;
	private String tags;
	//还需要持久化html 内容
	
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
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}
