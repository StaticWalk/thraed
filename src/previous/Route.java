package previous;

/**
 * Created by xiongxiaoyu on 2018/3/4.
 * 执行路线只有一条
 * main-m1-m2-m2返回-m1继续-m3-m3返回-m1返回-mian继续-main结束
 * 进程是一个静态的概念（包含一个主线程Main），机器运行的都是线程
 */
public class Route {
	public static void main(String[] args) {
		m1();
	}
	private static void m1() {
		m2();
		m3();
	}
	private static void m3() {}
	private static void m2() {}
}
