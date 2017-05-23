package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class TimerUtils {

	/* 
     * 将时间转换为时间戳
     */    
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }
    
    /* 
     * 将时间戳转换为时间
     */
	public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
	
	/**
	 * sina微博时间转换
	 */
	public static String sinaweiboTime(String t){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = StringUtils.deleteWhitespace(t);
		if(StringUtils.contains(t, "今天")){
			time = time.replace("今天", "");
			time = dateFormat.format(new Date()) + " " + time;
		}else if(StringUtils.contains(t, "分钟前")){
			time = time.replace("分钟前", "");
			if(StringUtils.isNumeric(time)){
				time = TimerUtils.timeToTime(Integer.parseInt(time));
			}else{
				time = dateFormat.format(new Date());
			}
		}else if(t.length() == 11){
			dateFormat = new SimpleDateFormat("yyyy-");
			time = dateFormat.format(new Date()) + t;
		}else{
			time = t;
		}
        return time;
    }
	
	public static String timeToTime(int t){
		Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - t);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(calendar.getTime());
	}
}
