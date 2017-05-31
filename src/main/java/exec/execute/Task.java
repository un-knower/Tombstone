package exec.execute;

public class Task {

	private String type;
	private String topic;
	private String createTime;
	private String author;
	private int isVip;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getIsVip() {
		return isVip;
	}
	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}
}
