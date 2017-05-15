package db;

import conf.db.ServerConfig;
import conf.regex.AutoHomeRegex;

public class ServerConfigMain {

	public static void main(String[] args) {
		System.out.println(ServerConfig.config.dirverClassName());
		System.out.println(ServerConfig.config.url());
		System.out.println(ServerConfig.config.username());
		System.out.println(ServerConfig.config.password());
		
		System.out.println(AutoHomeRegex.autoHomeRegex.url_list());
		System.out.println(AutoHomeRegex.autoHomeRegex.url_post());
	}

}
