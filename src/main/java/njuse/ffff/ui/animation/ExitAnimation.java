package njuse.ffff.ui.animation;

import njuse.ffff.ui.component.AnimationComponent;

/**
 * 此类存在一些问题：结束后控件暂时无法归位
 * 
 * @author Li
 *
 */
public class ExitAnimation extends AnimationGroup {
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

	private int distance = 150;
	private int frames = 20;

	public ExitAnimation(int direction) {
		this(direction, null);
	}

	public ExitAnimation(int direction, boolean isTransparent) {
		this(direction, isTransparent, null);
	}

	public ExitAnimation(int direction, AnimationComponent comp) {
		this(direction, true, comp);
	}

	public ExitAnimation(int direction, boolean isTransparent, AnimationComponent comp) {
		this.direction = direction;
		this.isTransparent = isTransparent;
		this.comp = comp;
		if (comp != null) {
			regist();
		}
	}

	public void regist() {
		animations.clear();

		if (direction == NULL) {
			distance = 0;
		} else if (!isTransparent) {
			switch (direction) {
			case UP:
			case DOWN:
				distance = comp.getHeight();
				break;
			case LEFT:
			case RIGHT:
				distance = comp.getWidth();
				break;
			}
		}
		MoveAnimation move = new MoveAnimation(distance, direction, frames);
		TransparentAnimation transparent;
		if (isTransparent) {
			transparent = new TransparentAnimation(1f, 0f, frames);
		} else {
			transparent = new TransparentAnimation(1f, 1f, frames);
		}
		addAnimation(transparent);
		addAnimation(move);

		registLocation(comp.getX(), comp.getY());

		comp.setAnimation(this);

		isRegisted |= true;
	}

	public void registLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public void start() {
		if (!isRegisted)
			regist();
		comp.moveTo(x, y);
		super.start();
	}

	@Override
	protected void stopPerformed() {
		comp.moveTo(x, y);
		super.stopPerformed();
	}
}
