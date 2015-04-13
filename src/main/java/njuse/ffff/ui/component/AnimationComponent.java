package njuse.ffff.ui.component;

import njuse.ffff.ui.animation.IAnimation;

/**
 * 控件接口，可执行动画的控件都需要实现此接口
 * 
 * @author Li
 *
 */
public interface AnimationComponent extends IMoveable, ITransparent {

	/**
	 * 为控件指定动画
	 * 
	 * @param animation
	 *            需要添加的动画
	 */
	void setAnimation(IAnimation animation);

	/**
	 * 执行动画
	 */
	void runAnimation();

	/**
	 * 获取控件宽度
	 * 
	 * @return
	 */
	int getWidth();

	/**
	 * 获取控件高度
	 * 
	 * @return
	 */
	int getHeight();

}
