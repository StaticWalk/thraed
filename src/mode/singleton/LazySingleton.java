package mode.singleton;

/**
 * Created by xiongxiaoyu on 2018/1/22.
 */
public class LazySingleton {
	private LazySingleton(){
		System.out.println("LazySingleleton is create");
	}
	private static LazySingleton instance=null;

	//高并发 锁的操作会影响进程
	public static synchronized LazySingleton getInstance(){
		if (instance==null)
			instance=new LazySingleton();
		return instance;
	}
}
