package conf.regex;

import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Sources({ "classpath:regex/auto_regex" })
public interface AutoHomeRegex extends Config {

	String url_list();
	String url_post();
	String url_user();
	String url_user_info();
	String url_post_format();
	String url_user_other_format();
	String url_user_format();
	
	public static AutoHomeRegex autoHomeRegex = ConfigFactory.create(AutoHomeRegex.class);
}
