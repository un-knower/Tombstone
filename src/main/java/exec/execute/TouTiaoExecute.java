package exec.execute;

import business.toutiao.TouTiaoProcessor;
import conf.GlobalComponent;
import domain.autohome.Content;
import domain.autohome.Reply;
import domain.autohome.User;
import us.codecraft.webmagic.Spider;

public class TouTiaoExecute implements TaskExecute {

	private static String TOUTIAO_URL = "http://www.toutiao.com/api/pc/feed/?category=%s&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A1E559112451042&cp=5914511014B23E1";
	
	@Override
	public void excute(Task task) {
		int count = 1;
		if(task.getIsVip() > 0){
			count = 3;
		}
		GlobalComponent.dbBean.create_table("", Content.class, User.class, Reply.class);
		Spider.create(new TouTiaoProcessor(count, task.getTopic(), "")).addUrl(String.format(TOUTIAO_URL, task.getTopic())).thread(2).run();
	}

	@Override
	public TaskExecute getExecute() {
		return new TouTiaoExecute();
	}
}
