/**
 * Created by xiongxiaoyu on 2018/1/11.
 */
public class DeamonDemo {
	public static class DeamonT extends Thread{
		public void run(){
			while (true){
				System.out.println("I am alive");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		Thread t=new DeamonT();
		//t.setDaemon(true);//设置守护线程，在线程开始之前，成功后非守护线程将不会被执行
		t.start();
	}
}
