package previous;

/**
 * Created by xiongxiaoyu on 2018/3/4.
 */
public class TestThread1 {
	public static void main(String[] args) {
//		Runner1 r= new Runner1();
//		Thread t=new Thread(r);
		Runner1 r=new Runner1();
		r.start();
		for (int i = 0; i < 100; i++) {
			System.out.println("Thread : "+i);
		}
	}
//	static class Runner1 implements Runnable{  推荐使用接口创建
static class Runner1 extends Thread{

		@Override
		public void  run() {
			for (int i = 0; i < 100; i++) {
				System.out.println("Runner1 : "+i);
			}
		}
	}
}
