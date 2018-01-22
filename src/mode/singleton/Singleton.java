package mode.singleton;

/**
 * Created by xiongxiaoyu on 2018/1/22.
 *
 * 单例模式中
 * 合适产生实例，不好控制
 */
public class Singleton {
//	public static int STATUS=1;
	private Singleton(){
		System.out.println("Singleton is create");
//		System.out.println(Singleton.STATUS);
	}
	private static Singleton instance=new Singleton();
	public static Singleton getInstance(){
		return  instance;
	}
}
