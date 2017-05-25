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
		String tableKey = "_20170525";
		GlobalComponent.dbBean.create_table(tableKey, Content.class, User.class, Reply.class);
		Spider.create(new AutohomeProcessor(tableKey)).addUrl(String.format("http://club.autohome.com.cn/bbs/forum-c-%s-1.html", topic_id)).thread(2).run();
	}
}
