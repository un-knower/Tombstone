package domain.toutiao;

import conf.db.mysql.DBTable;

@DBTable(name = "data_toutiao_reply")
public class Reply {

	private String title; //回复主题
	private String content; //回复内容
	private String author; //回复人昵称
	private String authorurl; //回复人主页url
	private String createTime; //回复人时间
	private String upvote; //点赞数
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorurl() {
		return authorurl;
	}
	public void setAuthorurl(String authorurl) {
		this.authorurl = authorurl;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpvote() {
		return upvote;
	}
	public void setUpvote(String upvote) {
		this.upvote = upvote;
	}
}
