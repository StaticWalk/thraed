package threadPool;

import java.util.concurrent.*;

/**
 * Created by xiongxiaoyu on 2018/1/21.
 * 因为线程池提交的时间是10ms每次，容量是5个
 * tast线程执行的时间是100ms
 * 持续提交后，线程池满，
 * 多数tast会被拒绝
 */
public class RejectThreadPoolDemo {

	public static class MyTask implements Runnable{
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis()+":Thread Id :"+
					Thread.currentThread().getId());
			try{
				Thread.sleep(100);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		MyTask task=new MyTask();
		ExecutorService es=new ThreadPoolExecutor(5, 5, 0L
				, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>()
				, Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
			@Override
			public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
					System.out.println(r.toString()+"is discard");
			}
		});
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			es.submit(task);
			Thread.sleep(10);
		}
	}
}
