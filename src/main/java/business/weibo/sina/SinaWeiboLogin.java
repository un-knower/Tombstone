package business.weibo.sina;

import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class SinaWeiboLogin implements Runnable {
	
	public static String SINAWEIBO_COOKIE = "";

	/**
	 * 模拟登陆sina微博获取cookie
	 */
	private void getCookie() throws InterruptedException, FailingHttpStatusCodeException, MalformedURLException, IOException{
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53");
        webClient.getCookieManager().setCookiesEnabled(true);
        
        HtmlPage page = webClient.getPage("https://passport.weibo.cn/signin/login");
        Thread.sleep(1000);
        HtmlInput usr = (HtmlInput) page.getElementById("loginName");
        usr.setValueAttribute("zxw0066@sina.cn");
        HtmlInput pwd = (HtmlInput) page.getElementById("loginPassword");
        pwd.setValueAttribute("wjf018698255zxw");
        DomElement button = page.getElementById("loginAction");
        page =(HtmlPage) button.click();
        Thread.sleep(1000);
        
        StringBuilder cookieBuilder = new StringBuilder();
        for (Cookie cookie : webClient.getCookieManager().getCookies()) {
        	cookieBuilder.append(cookie.getName());
        	cookieBuilder.append("=");
        	cookieBuilder.append(cookie.getValue());
        	cookieBuilder.append("; ");
		}
        SINAWEIBO_COOKIE = cookieBuilder.substring(0, cookieBuilder.length() - 2);
	}

	@Override
	public void run() {
		try {
			this.getCookie();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
