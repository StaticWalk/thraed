package debug;

import java.util.concurrent.locks.StampedLock;

/**
 * Created by xiongxiaoyu on 2018/3/4.
 * 读写锁的改进
 * 读不阻塞写
 */
public class Point {
	private double x, y;
	private final StampedLock sl = new StampedLock();

	void move(double deltaX, double deltaY) { // an exclusively locked method
		long stamp = sl.writeLock();
		try {
			x += deltaX;
			y += deltaY;
		} finally {
			sl.unlockWrite(stamp);
		}
	}

	double distanceFromOrigin() { // A read-only method
		long stamp = sl.tryOptimisticRead();
		double currentX = x, currentY = y;
		if (!sl.validate(stamp)) {
			stamp = sl.readLock();
			try {
				currentX = x;
				currentY = y;
			} finally {
				sl.unlockRead(stamp);
			}
		}
		return Math.sqrt(currentX * currentX + currentY * currentY);
	}
}