package mode;

/**
 * Created by xiongxiaoyu on 2018/1/22.
 */
public final class Product {
	//确保无子类
	private final String no;
	//私有属性，不会被其他对象获取
	private final String name;
	//final保证属性不会被2次赋值
	private final Double price;

	//创建对象时要指明参数，因为不能再被修改了
	public Product(String no, String name, Double price) {
		super();
		this.no = no;
		this.name = name;
		this.price = price;
	}
}
