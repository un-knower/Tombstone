package exec.execute;

import business.weibo.sina.SinaWeiboProcessor;
import conf.GlobalComponent;
import domain.autohome.Content;
import domain.autohome.User;
import us.codecraft.webmagic.Spider;

public class SinaWeiBoExecute implements TaskExecute {

	@Override
	public void excute(Task task) {
		String topic = task.getTopic();
		String url = "https://m.weibo.cn/api/container/getIndex?type=wb&queryVal=" + topic + "&containerid=100103type%3D2%26q%3D" + topic;
		GlobalComponent.dbBean.create_table("", Content.class, User.class);
		Spider.create(new SinaWeiboProcessor("")).addUrl(url).thread(2).run();
	}

	@Override
	public TaskExecute getExecute() {
		return new SinaWeiBoExecute();
	}
}
