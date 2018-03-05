package sync;

/**
 * Created by xiongxiaoyu on 2018/1/11.
 */
public class AccountingSyncBad implements Runnable{

	static int i=0;
	public synchronized void increase(){i++;}
	@Override
	public void  run() {
		for (int j = 0; j <10000000 ; j++) {
			increase();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		//把t1 t2 的AccountingSyncBad提出来
//		AccountingSyncBad t=new AccountingSyncBad();
//		Thread t1=new Thread(t);
//		Thread t2=new Thread(t);

		//t1 t2的AccountingSyncBad对象不同，操作的锁就不同
		Thread t1=new Thread(new AccountingSyncBad());
		Thread t2=new Thread(new AccountingSyncBad());
		t1.start();t2.start();
		t1.join();t2.join();
		System.out.println(i);
	}
}
