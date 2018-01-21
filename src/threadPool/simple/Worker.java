package threadPool.simple;

/**
 * Created by xiongxiaoyu on 2018/1/19.
 */
public class Worker extends Thread{
	//线程池
	private ThreadPool pool;
	//任务
	private Runnable target;
	private boolean isShutDown=false;
	private boolean isIdle=false;

	public Worker(Runnable target,String name,ThreadPool pool){
		super(name);
		this.pool=pool;
		this.target=target;
	}
	public Runnable getTarget(){
		return  target;
	}
	public  boolean isIdle(){
		return isIdle;
	}
	public void run(){
		while (!isShutDown){
			isIdle=false;
			if (target!=null){
				//运行任务
				target.run();
			}
			//任务结束后
			isIdle=true;
			try{
				//该任务结束后，不关闭线程，而是放入线程池空闲列队
				pool.repool(this);
				synchronized (this){
					//线程空闲，等待心得任务到来
					wait();
				}
			} catch (InterruptedException e) {
//				e.printStackTrace();
			}
			isIdle=false;
		}
	}
	public synchronized void setTarget(Runnable newTarget){
		target=newTarget;
		notifyAll();
	}

	public synchronized void shutDown(){
		isShutDown=true;
		notifyAll();
	}
}
