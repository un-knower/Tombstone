package exec.execute;

import business.autohome.AutohomeProcessor;
import conf.GlobalComponent;
import domain.autohome.Content;
import domain.autohome.Reply;
import domain.autohome.User;
import us.codecraft.webmagic.Spider;

public class AutoHomeExecute implements TaskExecute {

	@Override
	public void excute(Task task) {
		GlobalComponent.dbBean.create_table("", Content.class, User.class, Reply.class);
		Spider.create(new AutohomeProcessor("", task.getIsVip()))
		.addUrl(String.format("http://club.autohome.com.cn/bbs/forum-c-%s-1.html", task.getTopic()))
		.thread(2).run();
	}

	@Override
	public TaskExecute getExecute() {
		return new AutoHomeExecute();
	}
}
