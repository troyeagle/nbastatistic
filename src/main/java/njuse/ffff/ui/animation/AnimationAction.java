package njuse.ffff.ui.animation;

import java.awt.event.ActionListener;

import njuse.ffff.ui.component.AnimationComponent;

public abstract class AnimationAction implements ActionListener {

	protected AnimationComponent comp;
	protected int times;
	protected int timeCount;

	/**
	 * 开始前的准备工作（init？
	 */
	public abstract void start();
	
	public void bindComponent(AnimationComponent comp) {
		this.comp = comp;
	}
	
}
