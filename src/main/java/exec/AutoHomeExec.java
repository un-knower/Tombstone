package exec;

import business.autohome.AutohomeProcessor;
import conf.db.GlobalComponent;
import domain.autohome.Content;
import domain.autohome.Reply;
import domain.autohome.User;
import us.codecraft.webmagic.Spider;

public class AutoHomeExec {
	
	public static void main(String[] args) {
		int topic_id = 145; //polo
		if(topic_id == 0){
			return;
		}
		GlobalComponent.dbBean.create_table(Content.class, User.class, Reply.class);
		Spider.create(new AutohomeProcessor()).addUrl(String.format("http://club.autohome.com.cn/bbs/forum-c-%s-1.html", topic_id)).thread(2).run();
	}
}
