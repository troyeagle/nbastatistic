package njuse.ffff.ui.animation;

import njuse.ffff.ui.component.AnimationComponent;

/**
 * 动画接口
 * 
 * @author Li
 *
 */
public interface IAnimation {

	/**
	 * 将动画绑定控件
	 * 
	 * @param component
	 *            需要执行动画的控件
	 */
	public void bind(AnimationComponent component);

	/**
	 * 执行动画
	 */
	public void start();
	
	/**
	 * 结束动画（手动
	 */
	public void stop();

	/**
	 * 设置启动延迟（帧）
	 * @param delay
	 */
	public void setInitialDelayFrames(int delay);

	/**
	 * 获取运行帧数
	 * 
	 * @return
	 */
	public int getFrames();

	/**
	 * 获取运行时间（ms）
	 * 
	 * @return
	 */
	public int getTime();

	/**
	 * 添加动画结束监听
	 * 
	 * @param l
	 */
	public void addStopListener(AnimationStopListener l);
}
