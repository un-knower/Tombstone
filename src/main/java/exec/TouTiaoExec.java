package exec;

import business.toutiao.TouTiaoProcessor;
import conf.db.GlobalComponent;
import domain.toutiao.Content;
import domain.toutiao.Reply;
import domain.toutiao.User;
import us.codecraft.webmagic.Spider;

public class TouTiaoExec {

	private static String TOUTIAO_URL = "http://www.toutiao.com/api/pc/feed/?category=%s&utm_source=toutiao&widen=1&max_behot_time=0&max_behot_time_tmp=0&tadrequire=true&as=A1E559112451042&cp=5914511014B23E1";
	
	public static void main(String[] args) {
		int count = 1;
		String topic_id = "news_hot";
		
		if(topic_id == null || topic_id.length() == 0){
			return;
		}
		GlobalComponent.dbBean.create_table(Content.class, User.class, Reply.class);
		Spider.create(new TouTiaoProcessor(count, topic_id)).addUrl(String.format(TOUTIAO_URL, topic_id)).thread(2).run();
	}
}
