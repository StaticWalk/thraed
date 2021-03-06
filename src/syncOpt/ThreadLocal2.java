package syncOpt;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiongxiaoyu on 2018/1/24.
 * 为每一个线程分配一个实例
 * ThreadLocal只会维护线程中的对象
 */
public class ThreadLocal2 {

	static ThreadLocal<SimpleDateFormat> tl=new ThreadLocal<SimpleDateFormat>();
	public static class ParseDate implements Runnable{
		int i=0;
		public ParseDate(int i){this.i=i;}
		public void run() {
			try {
				if(tl.get()==null){
					tl.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
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
