package business.weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.Set;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class WeiboLoginExec {

	public static LinkedList<Set<Cookie>> COOKIE_LIST = new LinkedList<Set<Cookie>>();
	
	 //新浪微博登录页面
    String baseUrl = "https://passport.weibo.cn/signin/login";
    
    public HtmlPage loginExec() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
    	//打开
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.addRequestHeader("User-Agent", "Mozilla/5.0 (iPad; CPU OS 7_0_2 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A501 Safari/9537.53");
        webClient.getCookieManager().setCookiesEnabled(true);
        
        HtmlPage page = webClient.getPage(baseUrl);
        //等待页面加载
        Thread.sleep(1000);
        //获取输入帐号的控件
        HtmlInput usr = (HtmlInput) page.getElementById("loginName");
        usr.setValueAttribute("zxw0066@sina.cn");
        //获取输入密码的控件
        HtmlInput pwd = (HtmlInput) page.getElementById("loginPassword");
        pwd.setValueAttribute("wjf018698255zxw");
        //点击登录
        DomElement button = page.getElementById("loginAction");
        page =(HtmlPage) button.click();
        //等待页面加载
        Thread.sleep(1000);
        
        return page;
	}
}
