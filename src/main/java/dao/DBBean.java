package dao;

import java.lang.reflect.Field;
import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import conf.db.mysql.ConnectDb;
import conf.db.mysql.DBTable;

public class DBBean {

	private Connection conn = null;  
    private QueryRunner qRunner = null;  
    
	public DBBean() {
		conn = ConnectDb.Connect();  
        qRunner = new QueryRunner();  
	}

	public void closeDB() {
		DbUtils.closeQuietly(conn);
	}

	public String getTableName(Class<?> t) {
		return t.getAnnotation(DBTable.class).name();
	}

	public void create_table(Class<?>... ts) {
		try {
			for (Class<?> t : ts) {
				// drop table <start>
				String tableName = getTableName(t);
				StringBuilder sql = new StringBuilder();

				sql.append("DROP TABLE IF EXISTS `" + tableName + "`");
				qRunner.update(conn, sql.toString());
				// drop table <end>
				
				// create table <start>
				sql = new StringBuilder();
				sql.append("CREATE TABLE `" + tableName + "` (`id` int(11) NOT NULL AUTO_INCREMENT,");
				Field[] fields = t.getDeclaredFields();
				for (Field field : fields) {
					field.setAccessible(true);
					sql.append("`" + field.getName() + "` varchar(1024) NOT NULL,");
				}
				sql.append("PRIMARY KEY (`id`)) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4");

				qRunner.update(conn, sql.toString());
				// create table <end>
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert_data(Class<?> t, Object... params) {
		
		String tableName = getTableName(t);
		try {
			Field[] fields = t.getDeclaredFields();
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO " + tableName + "(");
			
			for (Field field : fields) {
				field.setAccessible(true);
				sql.append('`' + field.getName() + '`' + ",");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(") VALUES(");
			
			for (int i = 0; i < params.length; i++) {
				sql.append("?,");
			}
			sql.deleteCharAt(sql.length() - 1);
			sql.append(")");

			qRunner.update(conn, sql.toString(),  params);   
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
