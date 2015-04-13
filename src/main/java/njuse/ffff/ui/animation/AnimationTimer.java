package njuse.ffff.ui.animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.Timer;

/**
 * 用于动画计时
 * 
 * @author Li
 *
 */
public class AnimationTimer extends Timer {

	private static final long serialVersionUID = 1L;

	/** 每帧的时间 */
	public static final int MS_PER_FRAME = 15;

	private int timeCount;	// 总次数
	private int frames;		// 计数器

	private List<AnimationAction> actionList;
	private ActionListener actionRunner;

	private List<AnimationStopListener> listeners;

	/*
	 * 初始化
	 */
	{
		actionList = new Vector<>();
		listeners = new Vector<>();
		actionRunner = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (timeCount == 0) {
					for (AnimationAction action : actionList)
						action.start();
				}
				for (ActionListener action : actionList) {
					action.actionPerformed(e);
				}
				incTimer();
			}

		};

	}

	public void addAnimationAction(AnimationAction action) {
		actionList.add(action);
	}

	public AnimationTimer(int frames) {
		super(MS_PER_FRAME, null);
		addActionListener(actionRunner);
		this.frames = frames;
	}

	public int getTimes() {
		return timeCount;
	}

	public int getTotalFrames() {
		return frames;
	}

	@Override
	public void start() {
		super.start();
	}

	@Override
	public void restart() {
		timeCount = 0;
		start();
	}

	/**
	 * 计数器递增
	 */
	private void incTimer() {
		timeCount++;
		if (frames <= timeCount) {	// 到达指定次数
			this.stop();
		}
	}

	public void addStopListener(AnimationStopListener l) {
		listeners.add(l);
	}

	@Override
	public void stop() {
		stopPerformed();
		if (isRunning())	// 如果正在执行，则停止
			super.stop();
	}

	/**
	 * 停止时执行的动作
	 */
	public void stopPerformed() {
		for (AnimationStopListener l : listeners) {
			l.perform();
		}
	}
}
