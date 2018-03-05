package previous;

import java.util.Date;

/**
 * Created by xiongxiaoyu on 2018/3/4.
 */
public class TestInterrupt {
	public static void main(String[] args) {
		MyThread thread=new MyThread();
		thread.start();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			thread.interrupt();
		}
	}

	static   class MyThread extends Thread{
		boolean flag = true;
		public void run() {
			while (flag){
				System.out.println("====="+new Date()+"====");
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					flag = false;
				}
			}
		}
	}
}
