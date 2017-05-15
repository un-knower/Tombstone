package business.angularjs;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import business.BasePageProcessor;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class AngularjsProcessor extends BasePageProcessor {

	public void process(Page page) {
		// 获取列表所有_id集合
		List<String> ids = new JsonPathSelector("$.data[*]._id").selectList(page.getRawText());
		if (CollectionUtils.isNotEmpty(ids)) {
			for (String id : ids) {
				page.addTargetRequest("http://angularjs.cn/api/article/" + id); // 获取内容API

				page.putField("title", new JsonPathSelector("$.data.title").select(page.getRawText()));
				page.putField("content", new JsonPathSelector("$.data.content").select(page.getRawText()));
			}
		}
	}
}
