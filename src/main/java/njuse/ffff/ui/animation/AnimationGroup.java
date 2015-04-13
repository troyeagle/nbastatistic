package njuse.ffff.ui.animation;

import java.util.List;
import java.util.Vector;

import njuse.ffff.ui.component.AnimationComponent;

/**
 * 动画组，用于组合动画
 * 
 * @author Li
 *
 */
public class AnimationGroup implements IAnimation {

	protected int stopCount;	// 已停止的动画计数

	protected List<IAnimation> animations;

	protected List<AnimationStopListener> listenerList;
	private int delay;

	{
		animations = new Vector<>();
		listenerList = new Vector<>();
	}

	@Override
	public void bind(AnimationComponent component) {
		for (IAnimation animation : animations) {
			animation.bind(component);
		}
	}

	/**
	 * 添加一个动画
	 * 
	 * @param animation
	 */
	public void addAnimation(IAnimation animation) {
		addAnimation(animation, 0);
	}

	/**
	 * 添加一个动画并指定启动延迟（单位：帧）
	 * 
	 * @param animation
	 * @param delay
	 */
	public void addAnimation(IAnimation animation, int delay) {
		if (!animations.contains(animation)) {
			animation.setInitialDelayFrames(delay);
			animations.add(animation);
			animation.addStopListener(new AnimationStopListener() {
				@Override
				public void perform() {
					if (++stopCount == animations.size()) {
						stopPerformed();
					}
				}
			});
		} else {
			IAnimation a = animations.get(animations.indexOf(animation));
			a.setInitialDelayFrames(delay);
		}
	}

	@Override
	public void start() {
		stopCount = 0;
		new Thread() {
			@Override
			public void run() {
				try {
					sleep(delay * AnimationTimer.MS_PER_FRAME);
				} catch (InterruptedException e) {
				}
				for (IAnimation animation : animations) {
					animation.start();
				}
			}
		}.start();
	}

	/**
	 * 设置动画组的启动延迟（单位：帧）
	 */
	@Override
	public void setInitialDelayFrames(int delay) {
		this.delay = delay;
	}

	/**
	 * 获取启动延迟
	 */
	@Override
	public int getFrames() {
		int aFrames = 0;
		for (IAnimation a : animations) {
			int frames = a.getFrames();
			if (frames > aFrames)
				aFrames = frames;
		}
		return aFrames + delay;
	}

	@Override
	public int getTime() {
		return getFrames() * AnimationTimer.MS_PER_FRAME;
	}

	@Override
	public void addStopListener(AnimationStopListener l) {
		listenerList.add(l);
	}

	protected void stopPerformed() {
		for (AnimationStopListener l : listenerList) {
			l.perform();
		}
	}

	@Override
	public void stop() {
		for (IAnimation a : animations) {
			a.stop();
		}
	}
}
