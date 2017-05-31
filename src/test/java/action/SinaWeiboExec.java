package action;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import business.weibo.sina.SinaWeiboLogin;
import business.weibo.sina.SinaWeiboProcessor;
import conf.GlobalComponent;
import domain.weibo.sina.Content;
import domain.weibo.sina.User;
import us.codecraft.webmagic.Spider;

public class SinaWeiboExec {

	public static void main(String[] args) {
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		executor.scheduleAtFixedRate(new SinaWeiboLogin(), 0, 2, TimeUnit.MINUTES);
		
		String tableKey = "_20170525";
		String topic = "美国移民";
		String url = "https://m.weibo.cn/api/container/getIndex?type=wb&queryVal=" + topic + "&containerid=100103type%3D2%26q%3D" + topic;
//		String url = "http://weibo.com/p/1005055878746225/home?from=page_100505&mod=TAB&is_all=1#place";
		GlobalComponent.dbBean.create_table(tableKey, Content.class, User.class);
		Spider.create(new SinaWeiboProcessor(tableKey)).addUrl(url).thread(2).run();
	}
}