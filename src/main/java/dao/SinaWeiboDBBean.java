package dao;

public class SinaWeiboDBBean extends DBBean {

	public void update_data(Class<?> t, String area, String tags, String age, String where_url) {
		try {
			String tableName = super.getTableName(t);
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE " + tableName +" SET area=?,tags=?,birthday=? WHERE url=?");
			super.qRunner.update(conn, sql.toString(),  area, tags, age, where_url);   
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
