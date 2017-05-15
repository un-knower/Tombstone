package conf.db;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;
import org.aeonbits.owner.Config.Sources;

@Sources({"classpath:db_properties"})
public interface ServerConfig extends Config {

	String dirverClassName();
	String url();
	String username();
	String password();
	String chromeDriver();
	String chromeDriverUrl();
	
	public static ServerConfig config = ConfigFactory.create(ServerConfig.class);
}
