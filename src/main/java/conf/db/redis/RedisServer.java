package conf.db.redis;

import java.util.UUID;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.scheduler.Scheduler;

public class RedisServer implements Scheduler {
	
	private JedisPool pool;
	
	private static final String QUEUE_PREFIX = "queue_";
    private static final String SET_PREFIX = "set_";
	
	public RedisServer() {
		pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
	}

	@Override
    public void push(Request request, Task task) {
        Jedis jedis = pool.getResource();
          //使用SortedSet进行url去重
        if (jedis.zrank(SET_PREFIX+task.getUUID(),request.getUrl())==null){
            //使用List保存队列
            jedis.rpush(QUEUE_PREFIX+task.getUUID(),request.getUrl());
            jedis.zadd(SET_PREFIX+task.getUUID(),System.currentTimeMillis(),request.getUrl());
        }
    }

    @Override
    public Request poll(Task task) {
        Jedis jedis = pool.getResource();
        String url = jedis.lpop(QUEUE_PREFIX+task.getUUID());
            if (url==null) {
                return null;
            }
        return new Request(url);
    }

	public static void main(String[] args) {
    	Request request = new Request();
    	request.setUrl("http://www.baidu.com");
    	
    	Task task = new Task() {
			
			@Override
			public String getUUID() {
				return UUID.randomUUID().toString();
			}
			
			@Override
			public Site getSite() {
				return null;
			}
		};
		new RedisServer().push(request, task);
	}
}
