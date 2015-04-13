package njuse.ffff.ui.animation;

/**
 * 匀速直线运动动画
 * 
 * @author Li
 *
 */
public class DirectMoveAnimation extends MoveAnimation {

	/**
	 * 
	 * @param distance
	 * @param direction
	 * @param speed
	 *            speed会根据distance调整
	 */
	public DirectMoveAnimation(int distance, int direction, double speed) {
		int times = (int) Math.ceil(distance / speed);
		speed = distance / times;
		initAnimation(direction, speed, times);
	}

	public DirectMoveAnimation(int direction, double speed, int times) {
		initAnimation(direction, speed, times);
	}

	public DirectMoveAnimation(double speedX, double speedY, double timeSec) {
		int time = (int) (timeSec * 1000 / 15);
		initAnimation(speedX, speedY, time);
	}

	public DirectMoveAnimation(double speedX, double speedY, int times) {
		initAnimation(speedX, speedY, times);
	}

	public DirectMoveAnimation(int distance, double speedX, double speedY) {
		double l = Math.sqrt(speedX * speedX + speedY * speedY);
		int frames = (int) (distance / l);
		// 微调速度
		speedX /= frames * l / distance;
		speedY /= frames * l / distance;
		initAnimation(speedX, speedY, frames);
	}
}
