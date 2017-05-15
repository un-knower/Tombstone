package domain.autohome;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_autohome_content")
public class Content {

	private String title;
	private String author;
	private String url;
	private String createTime;
	
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
	
	public static void main(String[] args) {
		GlobalComponent.dbBean.create_table(Content.class);
	}
}
