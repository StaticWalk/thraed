package non;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by xiongxiaoyu on 2018/1/16.
 */
public class AtomicReferenceTest {
	public final static AtomicReference<String> atomicStr=new AtomicReference<String>("abc");

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			final int num=i;
			new Thread(){
				public void run(){
					try {
						Thread.sleep(Math.abs((int)(Math.random()*100)));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (atomicStr.compareAndSet("abc","def")){
						System.out.println("Thread:"+Thread.currentThread().getId()+"Change value to");
					}
					else {
						System.out.println("Thread:"+Thread.currentThread().getId()+"Failed");
					}
				}
			}.start();
		}
	}
}
