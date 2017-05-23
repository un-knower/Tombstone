package conf.db;

import dao.DBBean;
import dao.SinaWeiboDBBean;

/**
 * 定义全量 用于多种持久化
 * @author xingwuzhao
 *
 */
public class GlobalComponent {

	public static DBBean dbBean  = new DBBean();
	public static SinaWeiboDBBean sinaWeiboDBBean  = new SinaWeiboDBBean();
}
