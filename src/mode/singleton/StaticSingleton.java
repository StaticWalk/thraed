package mode.singleton;

/**
 * Created by xiongxiaoyu on 2018/1/22.
 * 静态内部类不会被初始化
 * 只有调用getInstance才会调用内部类
 * 单例构造函数必须是private
 */
public class StaticSingleton {
	private StaticSingleton(){
		System.out.println("StaticSingleton is create");
	}

	private static class SingletonHolder{
		private static StaticSingleton instance=new StaticSingleton();
	}

	public static StaticSingleton getInstance(){
		return  SingletonHolder.instance;
	}
}