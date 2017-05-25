package domain.autohome;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_autohome_user")
public class User {

	private String author; //作者昵称
	private String sex; //性别
	private String url; //主页url
	private String topic; //帖子
	private String essential; //精华帖
	private String area  ; //地域
	private String createtime  ; //注册时间
	private String lasttime  ; //最后登录时间
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
}
