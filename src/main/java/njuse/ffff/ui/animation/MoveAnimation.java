package njuse.ffff.ui.animation;

import njuse.ffff.util.MathCal;

/**
 * 控件移动动画（待完善）
 * 
 * @author Li
 *
 */
public class MoveAnimation extends Animation {

	/**
	 * 
	 * @param distance
	 * @param direction
	 * @param speed
	 *            speed会根据distance调整
	 */
	public MoveAnimation(int distance, int direction, double speed) {
		int frames = (int) Math.ceil(distance / speed);
		speed = distance / frames;
		initAnimation(direction, speed, frames);
	}

	public MoveAnimation(int distance, int direction, int frames) {
		double speed = distance / (double) frames;
		initAnimation(direction, speed, frames);
	}

	public MoveAnimation(int direction, double speed, int frames) {
		initAnimation(direction, speed, frames);
	}

	public MoveAnimation(double speedX, double speedY, double timeSec) {
		int time = (int) (timeSec * 1000 / AnimationTimer.MS_PER_FRAME);
		initAnimation(speedX, speedY, time);
	}

	public MoveAnimation(double speedX, double speedY, double aX, double aY, int frames) {
		initAnimation(speedX, speedY, aX, aY, frames);
	}

	public MoveAnimation(double speedX, double speedY, double aX, double aY, double timeSec) {
		int frames = (int) (timeSec * 1000 / AnimationTimer.MS_PER_FRAME);
		initAnimation(speedX, speedY, aX, aY, frames);
	}

	/**
	 * 有加速度的单方向移动（未完成）
	 * 
	 * @param direction
	 * @param distance
	 * @param speed
	 * @param acceleration
	 */
	public MoveAnimation(int direction, int distance, double speed, double acceleration) {
		// l=vt+(at^2)/2 -> a/2*t^2+vt-l=0
		int frames = (int) Math.round(
				MathCal.solveQuadraticEquation(acceleration / 2, speed, -distance)[0]);
		// TODO 微调
		initAnimation(direction, speed, acceleration, frames);
	}

	public MoveAnimation(int direction, int distance, double speed, int frames) {
		// a*t^2/2+v*t-l=0
		double acceleration = MathCal.solveQuadraticEquation(0, frames * frames / 2, speed
				* frames - distance)[0];
		initAnimation(direction, speed, acceleration, frames);
	}

	public MoveAnimation(int direction, int distance, int frames, double acceleration) {
		// v*t+a*t^2/2-l=0
		double speed = MathCal.solveQuadraticEquation(0, frames, acceleration * frames
				* frames / 2 - distance)[0];
		initAnimation(direction, speed, frames);
	}

	public MoveAnimation(double speed, int direction, int distance) {
		int frames = (int) (2 * distance / speed);
		// 微调
//		speed = 2.0 * distance / frames;
		double acceleration = -speed / frames;
		initAnimation(direction, speed, acceleration, frames);
	}
	
	public MoveAnimation(int direction, double speed, double acceleration, int frames) {
		initAnimation(direction, speed, acceleration, frames);
	}

	public MoveAnimation(int direction, double speed, double acceleration, double timeSec) {
		int frames = (int) (timeSec * 1000 / AnimationTimer.MS_PER_FRAME);
		initAnimation(direction, speed, acceleration, frames);

	}

	public MoveAnimation(double speedX, double speedY, int times) {
		initAnimation(speedX, speedY, times);
	}

	public MoveAnimation(int distance, double speedX, double speedY) {
		double l = Math.sqrt(speedX * speedX + speedY * speedY);
		int frames = (int) (distance / l);
		// 微调速度
		speedX /= frames * l / distance;
		speedY /= frames * l / distance;
		initAnimation(speedX, speedY, frames);
	}

	protected MoveAnimation() {
	}

	protected void initAnimation(double speedX, double speedY, int frames) {
		initAnimation(speedX, speedY, 0.0, 0.0, frames);
	}

	protected void initAnimation(double speedX, double speedY, double aX, double aY,
			int frames) {
		initTimer(frames);
		action = new MoveAnimationAction(speedX, speedY, aX, aY);
		animation.addAnimationAction(action);
	}

	protected void initAnimation(int direction, double speed, int frames) {
		initAnimation(direction, speed, 0.0, frames);
	}

	protected void initAnimation(int direction, double speed, double acceleration, int frames) {
		double speedX = 0.0;
		double speedY = 0.0;
		double aX = 0.0;
		double aY = 0.0;
		switch (direction) {
		case MoveAnimationAction.UP:
			speedY = -speed;
			aY = -acceleration;
			break;
		case MoveAnimationAction.DOWN:
			speedY = speed;
			aY = acceleration;
			break;
		case MoveAnimationAction.LEFT:
			speedX = -speed;
			aX = -acceleration;
			break;
		default:
			speedX = speed;
			aX = acceleration;
			break;
		}
		initAnimation(speedX, speedY, aX, aY, frames);
	}

}
