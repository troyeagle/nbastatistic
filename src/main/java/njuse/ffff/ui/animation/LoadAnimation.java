package njuse.ffff.ui.animation;

import njuse.ffff.ui.component.AnimationComponent;

public class LoadAnimation extends AnimationGroup {
	/** Move up */
	public static final int UP = 0;
	/** Move down */
	public static final int DOWN = 1;
	/** Move left */
	public static final int LEFT = 2;
	/** Move right */
	public static final int RIGHT = 3;
	/** Don't move */
	public static final int NULL = 4;

	private int direction;
	private AnimationComponent comp;

	private int x;
	private int y;

	private boolean isTransparent;
	private boolean isRegisted = false;

	private int directDistance = 150;	// 匀速移动距离
	private int aDistance = 85;			// 减速移动距离
	private int directFrames = 20;		// 匀速移动时间

	public LoadAnimation(int direction, AnimationComponent comp) {
		this(direction, true, comp);
	}

	public LoadAnimation(int direction, boolean isTransparent) {
		this(direction, isTransparent, null);
	}

	/**
	 * 为指定控件创建指定方向的载入动画，并指定是否有透明过渡效果
	 * 
	 * @param direction
	 * @param isTransparent
	 * @param comp
	 */
	public LoadAnimation(int direction, boolean isTransparent, AnimationComponent comp) {
		this.direction = direction;
		this.isTransparent = isTransparent;
		if (comp != null) {
			bind(comp);
		}
	}

	/**
	 * 创建指定方向的载入动画
	 * 
	 * @param direction
	 */
	public LoadAnimation(int direction) {
		this(direction, null);
	}

	@Override
	public void bind(AnimationComponent component) {
		this.comp = component;
		if (comp != null) {
			regist();
			super.bind(comp);
		}
	}

	/**
	 * 注册控件信息
	 */
	public void regist() {
		isRegisted = false;

		comp.setAnimation(this);

		if (!isRegisted) {	// 防止递归（？）时重复注册（设计缺陷）
			animations.clear();
			if (direction == NULL) {
				directDistance = 0;
				aDistance = 0;
			} else if (!isTransparent) {
				switch (direction) {
				case UP:
				case DOWN:
					directDistance = comp.getHeight();
					break;
				case LEFT:
				case RIGHT:
					directDistance = comp.getWidth();
					break;
				}
			}
			MoveAnimation move = new MoveAnimation(directDistance, direction, directFrames);
			TransparentAnimation transparent;
			if (isTransparent) {
				transparent = new TransparentAnimation(0f, 1f, directFrames);
			} else {
				transparent = new TransparentAnimation(1f, 1f, directFrames);
			}
			double speed = directDistance / (double) directFrames;
			MoveAnimation aMove = new MoveAnimation(speed, direction, aDistance);
			addAnimation(transparent);
			addAnimation(move);
			addAnimation(aMove, directFrames + 1);

			x = comp.getX();
			y = comp.getY();

			isRegisted = true;
		}
	}

	@Override
	public void start() {
		if (!isRegisted)
			regist();
		int x = this.x;
		int y = this.y;
		switch (direction) {
		case UP:
			y += directDistance + aDistance;
			break;
		case DOWN:
			y -= directDistance + aDistance;
			break;
		case LEFT:
			x += directDistance + aDistance;
			break;
		case RIGHT:
			x -= directDistance + aDistance;
			break;
		}
		comp.moveTo(x, y);
		super.start();
	}

	@Override
	protected void stopPerformed() {
		comp.moveTo(x, y);
		super.stopPerformed();
	}
}
