package domain.weibo.sina;

import conf.db.GlobalComponent;
import conf.db.mysql.DBTable;

@DBTable(name = "data_sinaweibo_user")
public class User {

	private String author; //作者昵称
	private String url; //主页url
	private String statuses_count; //转发评论数
	private String verified ; //是否认证
	private String description  ; //描述
	private String gender; //性别
	private String followers_count ; //粉丝数
	private String follow_count  ; //关注数
	private String birthday  ; //生日
	private String area  ; //地域
	private String tags  ; //标签
	
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
	public String getStatuses_count() {
		return statuses_count;
	}
	public void setStatuses_count(String statuses_count) {
		this.statuses_count = statuses_count;
	}
	public String getVerified() {
		return verified;
	}
	public void setVerified(String verified) {
		this.verified = verified;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(String followers_count) {
		this.followers_count = followers_count;
	}
	public String getFollow_count() {
		return follow_count;
	}
	public void setFollow_count(String follow_count) {
		this.follow_count = follow_count;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
}
