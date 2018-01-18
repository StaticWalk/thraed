package JDKbf;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by xiongxiaoyu on 2018/1/18.
 */
public class TimerLock implements Runnable{
	public static ReentrantLock lock=new ReentrantLock();
	@Override
	public void run() {
		try{
			if (lock.tryLock(5,TimeUnit.SECONDS)){
				Thread.sleep(6000);//如果小于5s，就能成功；超时就会失败
			}else {
				System.out.println("get lock faild");
			}
		}catch (InterruptedException e){
			e.printStackTrace();
		}finally {
			if (lock.isHeldByCurrentThread())
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		TimerLock tl=new TimerLock();
		Thread t1=new Thread(tl);
		Thread t2=new Thread(tl);
		t1.start();t2.start();
	}
}
