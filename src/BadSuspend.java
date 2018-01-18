/**
 * Created by xiongxiaoyu on 2018/1/11.
 */
public class BadSuspend {
	public static Object u=new Object();
	static ChangeObjectThreader t1=new ChangeObjectThreader("t1");
	static ChangeObjectThreader t2=new ChangeObjectThreader("t2");
	public static class ChangeObjectThreader extends Thread{
		public ChangeObjectThreader(String name){
			super.setName(name);
		}
		@Override
		public void run(){
			synchronized (u){
				System.out.println("in "+getName());
				Thread.currentThread().suspend();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		t1.start();
		Thread.sleep(1000);
		t2.start();
		t1.resume();
		t2.resume();
		t1.join();
		t2.join();
	}
}

