package exec;

import business.weibo.sina.SinaWeiboProcessor;
import conf.db.GlobalComponent;
import domain.weibo.sina.Content;
import domain.weibo.sina.User;
import us.codecraft.webmagic.Spider;

public class SinaWeiboExec {

	public static void main(String[] args) {
		String topic = "zxw";
		String url = "https://m.weibo.cn/api/container/getIndex?type=wb&queryVal=" + topic + "&containerid=100103type%3D2%26q%3D" + topic;
		GlobalComponent.dbBean.create_table(Content.class, User.class);
		Spider.create(new SinaWeiboProcessor()).addUrl(url).thread(2).run();
	}
}