package business.weibo;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class weibo {

    public static void main(String args[]) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException{
    	
    	HtmlPage page = new WeiboLoginExec().loginExec();
    	System.out.println(page.asXml());
        
    	//获取到“写微博”这个按钮，因为这个按钮没有name和id,获取所有<a>标签
        DomNodeList<DomElement> button2 = page.getElementsByTagName("a");
        //跳转到发送微博页面    
        page =(HtmlPage)button2.get(4).click();
        //等待页面加载
        Thread.sleep(1000);
        //获取发送控件 标签为<a>中的2个
        DomNodeList<DomElement> button3 = page.getElementsByTagName("a");
        //获取文本宇
        HtmlTextArea content =(HtmlTextArea) page.getElementById("txt-publisher");
        content.focus();
        //填写你要发送的内容
        Date date = new Date();
        content.setText("随便发一条\n@小虫子啦啦\n"+date);
        
        DomElement fasong = button3.get(1);
        //改变发送按钮的属性，不能无法发送
        fasong.setAttribute("class", "fr txt-link");
        //发送！！！
        page = (HtmlPage)fasong.click();
        Thread.sleep(5000);
//        System.out.println(page.asText());
    }
}

