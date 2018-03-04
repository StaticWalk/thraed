package syncOpt;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiongxiaoyu on 2018/1/24.
 * 如果使用共享实例，达不到效果
 */
public class ThreadLocal3 {
	static ThreadLocal<SimpleDateFormat> tl=new ThreadLocal<SimpleDateFormat>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static class ParseDate implements Runnable{
		int i=0;
		public ParseDate(int i){this.i=i;}
		public void run() {
			try {
				if(tl.get()==null){
					tl.set(sdf);//ThreadLocal每次只维护当前的一个对象实例，这里的sdf始终只是一个对象实例，多个线程都指向了一个sdf。达不到效果
				}
				Date t=tl.get().parse("2015-03-29 19:29:"+i%60);
				System.out.println(i+":"+t);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		ExecutorService es= Executors.newFixedThreadPool(10);
		for(int i=0;i<1000;i++){
			es.execute(new ParseDate(i));
		}
	}
}
