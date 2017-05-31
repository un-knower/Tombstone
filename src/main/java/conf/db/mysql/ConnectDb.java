package conf.db.mysql;

import java.sql.SQLException;

import conf.ServerConfig;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDb {
	
	public static Connection Connect() {
		
		Connection conn = null;

		try {
			Class.forName(ServerConfig.config.dirverClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(ServerConfig.config.url(), ServerConfig.config.username(), ServerConfig.config.password());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
