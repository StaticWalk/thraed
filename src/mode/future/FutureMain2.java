package mode.future;

import java.util.concurrent.*;

/**
 * Created by xiongxiaoyu on 2018/1/22.
 */
public class FutureMain2 {
	public static void main(String[] args) throws ExecutionException, InterruptedException {

		ExecutorService executor= Executors.newFixedThreadPool(1);
		//执行FutureTask相当于上例的client.request("a")发送请求
		//在这里开启线程进行RealData的call()执行
		Future<String> future=executor.submit(new RealData("A"));
		System.out.println("请求完毕");
		try{
			//这里可以做额外的数据操作，使sleep代替其他业务逻辑的处理
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//相当于data.getResult(),取得call()的返回值
		//如果此时call()方法没有执行完成，依然会等待
		System.out.println("数据="+future.get());
	}
}
