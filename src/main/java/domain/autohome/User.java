package domain.autohome;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_autohome_user")
public class User {

	private String author; //作者昵称
	private String url; //主页url
	private String topic; //帖子
	private String essential; //精华帖
	
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
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getEssential() {
		return essential;
	}
	public void setEssential(String essential) {
		this.essential = essential;
	}
	
	public static void main(String[] args) {
		GlobalComponent.dbBean.create_table(User.class);
	}
}
