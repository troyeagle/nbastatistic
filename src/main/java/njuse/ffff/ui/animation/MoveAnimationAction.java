package njuse.ffff.ui.animation;

import java.awt.Component;
import java.awt.event.ActionEvent;

public class MoveAnimationAction extends AnimationAction {

	/** Move up */
	public static final int UP = 0;
	/** Move down */
	public static final int DOWN = 1;
	/** Move left */
	public static final int LEFT = 2;
	/** Move right */
	public static final int RIGHT = 3;

	private double speedX;
	private double speedY;

	private double accelerateX;
	private double accelerateY;

	private double x;
	private double y;

	/**
	 * 沿某方向移动
	 * 
	 * @param speedX
	 *            x轴速度
	 * @param speedY
	 *            y轴速度
	 * @param distance
	 *            距离
	 */
	public MoveAnimationAction(double speedX, double speedY) {
		this(speedX, speedY, 0.0, 0.0);
	}

	/**
	 * 
	 * @param speedX
	 *            x轴速度
	 * @param speedY
	 *            y轴速度
	 * @param aX
	 *            x轴加速度
	 * @param aY
	 *            y轴加速度
	 */
	public MoveAnimationAction(double speedX, double speedY, double aX, double aY) {
		this.speedX = speedX;
		this.speedY = speedY;
		this.accelerateX = aX;
		this.accelerateY = aY;
	}

	/**
	 * 沿坐标轴方向的移动
	 * 
	 * @param direction
	 *            方向
	 * @param speed
	 *            移动速度
	 * @param distance
	 *            移动距离
	 */
	public MoveAnimationAction(int direction, double speed) {
		switch (direction) {
		case UP:
			speedX = -speed;
			break;
		case DOWN:
			speedX = speed;
			break;
		case LEFT:
			speedY = -speed;
			break;
		case RIGHT:
			speedY = speed;
			break;
		}
	}

	@Override
	public void start() {
		Component c = (Component) comp;
		x = c.getX();
		y = c.getY();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		x += speedX;
		y += speedY;
		comp.moveTo((int) Math.round(x), (int) Math.round(y));

		speedX += accelerateX;
		speedY += accelerateY;
	}
}
