package action;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HuaBan {

	@Test
    public void testSelenium() {
        System.getProperties().setProperty("webdriver.chrome.driver", "/Users/xingwuzhao/chromedriver");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("http://www.toutiao.com/c/user/relation/52086194668/?tab=followed#mid=52086194668");
        WebElement webElement = webDriver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));
        webDriver.close();
    }
}
