package threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiongxiaoyu on 2018/1/21.
 */
public class ThreadPoolDemo {
	public static class MyTask implements Runnable{
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis()+":Thread Id :"+
			Thread.currentThread().getId());
			try{
				Thread.sleep(1000);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		MyTask task=new MyTask();
		ExecutorService es= Executors.newFixedThreadPool(5);
		for (int i = 0; i < 10; i++) {
//			es.submit(task);//会返回一个future的对象
			es.execute(task);
		}

	}
}
