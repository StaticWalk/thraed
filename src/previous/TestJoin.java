package previous;

/**
 * Created by xiongxiaoyu on 2018/3/4.
 * join()合并线程
 * t1.start()以后，线程启动了，主线程和t1交替进行，
 * t1.join()类似于方法的调用，率先执行t1线程，再执行主线程
 */
public class TestJoin {
	public static void main(String[] args) {
		MyThread2 t1=new MyThread2("t1");
		t1.start();
		try {
			t1.join();
		} catch (InterruptedException e) {}
		for (int i = 0; i <= 10; i++) {
			System.out.println("i am main Thread");
		}
	}
	static class MyThread2 extends Thread{
		MyThread2(String s){
			super(s);
		}

		public void run() {
			for (int i = 0; i <=10; i++) {
				System.out.println("i am "+getName());
				try {sleep(100);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
	}
}
