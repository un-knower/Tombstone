package exec;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;

import conf.GlobalComponent;
import exec.execute.AutoHomeExecute;
import exec.execute.SinaWeiBoExecute;
import exec.execute.Task;
import exec.execute.TaskExecute;
import exec.execute.TouTiaoExecute;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainExec implements Runnable {
	
	Map<String, TaskExecute> taskExecute_map = new HashMap<String, TaskExecute>();
	
	public MainExec() {
		taskExecute_map.put("autohome", new AutoHomeExecute());
		taskExecute_map.put("sinaweibo", new SinaWeiBoExecute());
		taskExecute_map.put("toutiao", new TouTiaoExecute());
	}

	@Override
	public void run() {
		log.info("获取任务解析json.....");
		String json = GlobalComponent.redisAPI.rpop("autohome_task");
		if(json == null){
			log.info("没有任务json需要解析 返回");
			return;
		}
		Task task = JSON.parseObject(json, Task.class);
		log.info("任务json解析 结果：" + json);
		TaskExecute taskExecute = taskExecute_map.get(task.getType());
		if(taskExecute == null){
			return;
		}
		taskExecute.excute(task);
	}
	
	public static void main(String[] args) {
		log.info("开始执行..........");
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		executor.scheduleAtFixedRate(new MainExec(), 0, 5, TimeUnit.SECONDS);
	}
}
