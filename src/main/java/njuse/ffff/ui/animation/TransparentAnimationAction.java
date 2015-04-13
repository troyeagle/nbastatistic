package njuse.ffff.ui.animation;

import java.awt.event.ActionEvent;

public class TransparentAnimationAction extends AnimationAction {

	private float start;
	private float end;
	private float step;
	private float current;
	
	public TransparentAnimationAction(float start, float end, int frames) {
		float step = (end - start) / frames;
		init(start, end, step);
	}
	
	private void init(float start, float end, float step) {
		this.start = start;
		this.end = end;
		this.step = step;
	}
	
	public TransparentAnimationAction(float start, float end, float step) {
		int frames = Math.abs((int) ((end - start) / step));
		step = (end - start) / frames;
		init(start, end, step);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		current += step;
		if (step > 0 && current > end || step < 0 && current < end)
			current = end;
		comp.setAlpha(current);
	}

	@Override
	public void start() {
		current = start;
	}

}
