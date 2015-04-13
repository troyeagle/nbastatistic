package njuse.ffff.ui.animation;

import java.util.List;
import java.util.Vector;

import njuse.ffff.ui.component.AnimationComponent;

public abstract class Animation implements IAnimation {

	protected AnimationTimer animation;
	protected AnimationAction action;

	protected List<AnimationStopListener> listenerList;

	{
		listenerList = new Vector<>();
	}

	/**
	 * ※不要直接调用此方法
	 */
	@Override
	public void bind(AnimationComponent component) {
		action.bindComponent(component);
	}

	@Override
	public void start() {
		animation.restart();
	}

	@Override
	public void setInitialDelayFrames(int delay) {
		animation.setInitialDelay(delay * AnimationTimer.MS_PER_FRAME);
	}

	@Override
	public int getFrames() {
		return animation.getInitialDelay() / AnimationTimer.MS_PER_FRAME
				+ animation.getTotalFrames();
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

	public void stop() {
		animation.stop();
	}

	protected void initTimer(int frames) {
		animation = new AnimationTimer(frames);
		animation.addStopListener(new AnimationStopListener() {

			@Override
			public void perform() {
				stopPerformed();
			}
		});
	}
}
