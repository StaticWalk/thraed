package syncOpt;

import java.util.List;
import java.util.Vector;

/**
 * Created by xiongxiaoyu on 2018/1/24.
 */
public class Deviation {

	public static List<Integer> numberList =new Vector<Integer>();
	public static void main(String[] args) throws InterruptedException {
		long begin=System.currentTimeMillis();
		int count=0;
		int startnum=0;
		while(count<10000000){
			numberList.add(startnum);
			startnum+=2;
			count++;
		}
		long end=System.currentTimeMillis();
		System.out.println(end-begin);
	}
}
