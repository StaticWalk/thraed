/**
 * Created by xiongxiaoyu on 2018/1/11.
 */
public class JoinMain {
	public volatile  static int i=0;
	public static class AddThread extends Thread{
		@Override
		public void run(){
			for (int i = 0; i <10000000 ; i++) ;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		AddThread at=new AddThread();
		at.start();
		at.join();
		System.out.print("嘻嘻嘻");
	}
}
