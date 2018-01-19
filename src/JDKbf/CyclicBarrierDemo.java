package JDKbf;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by xiongxiaoyu on 2018/1/18.
 */
public class CyclicBarrierDemo {
	public static class Soldier implements Runnable{
		private String soldier;
		private final CyclicBarrier cyclic;
		Soldier(CyclicBarrier cyclic,String soldierName){
			this.cyclic=cyclic;
			this.soldier=soldierName;
		}

		@Override
		public void run() {
			try {
				//等待所有士兵到齐
				cyclic.await();
				doWork();
				//等待所有士兵完成工作
				cyclic.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		private void doWork() {
			try{
				Thread.sleep(Math.abs(new Random().nextInt()%10000));
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			System.out.println(soldier+":任务完成");
		}
	}
	public static class BarrierRun implements Runnable{
		boolean flag;
		int n;
		public BarrierRun(Boolean flag,int n){
			this.flag=flag;
			this.n=n;
		}

		@Override
		public void run() {
			if (flag){
				System.out.println("任务完成");
			}else {
				System.out.println("集合完成");
				flag=true;
			}

		}
	}

	public static void main(String[] args) {
		final int n=10;
		Thread[] allSoldier=new Thread[n];
		boolean flag=false;
		CyclicBarrier cyclic=new CyclicBarrier(n,new BarrierRun(flag,n));
		//设置屏障点，为了执行方法
		System.out.println("集合队伍！");
		for (int i = 0; i < n; i++) {
			System.out.println("士兵"+i+"报道");
			allSoldier[i]=new Thread(new Soldier(cyclic,"士兵"+i));
			allSoldier[i].start();

			//使某一个线程中断，士兵不能完成集合，线程被中断的不能被执行的报.BrokenBarrierException错
			if (i==5){allSoldier[0].interrupt();}
		}
	}
}
