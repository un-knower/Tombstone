package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import conf.db.ServerConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeleniumUtils {
	
	public static String seleniumExec(String webUrl){
		System.getProperties().setProperty(ServerConfig.config.chromeDriver(), ServerConfig.config.chromeDriverUrl());
		WebDriver webDriver = null;
		WebElement webElement = null;
		String returnStr = "";
		try {
			webDriver = new ChromeDriver();
			webDriver.get(webUrl);
			webElement = webDriver.findElement(By.xpath("/html"));
			returnStr = webElement.getAttribute("outerHTML");
			webDriver.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			webDriver.close();
		}
		return returnStr;
	}
}
