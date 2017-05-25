package domain.toutiao;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_toutiao_user")
public class User {

	private String author; //作者昵称
	private String url; //主页url
	private String fans; //粉丝
	private String fources; //关注
	
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
	public String getFans() {
		return fans;
	}
	public void setFans(String fans) {
		this.fans = fans;
	}
	public String getFources() {
		return fources;
	}
	public void setFources(String fources) {
		this.fources = fources;
	}
}
