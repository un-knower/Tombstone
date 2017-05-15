package action.angularjs;

import business.angularjs.AngularjsProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

public class Test {

	public static void main(String[] args) {
		Spider.create(new AngularjsProcessor()).addUrl("http://angularjs.cn/api/article/latest?p=1&s=20")
				// .addPipeline(new ConsolePipeline()) //打印到控制台
				 .addPipeline(new JsonFilePipeline("/Users/xingwuzhao/Downloads")) //保存到文件夹
//				.addPipeline(new MyPipeline()) // 自定义Pipeline
				// 开启5个线程抓取
				.thread(5)
				// 启动爬虫
				.run();
	}
}
