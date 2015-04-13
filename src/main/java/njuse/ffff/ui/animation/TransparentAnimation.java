package njuse.ffff.ui.animation;


public class TransparentAnimation extends Animation {

	public TransparentAnimation(float start, float end, double timeSec) {
		int time = (int) (timeSec * 1000 / 15);
		init(start, end, time);
	}
	
	public TransparentAnimation(float start, float end, int time) {
		init(start, end, time);
	}
	
	protected void init(float start, float end, int frames) {
		initTimer(frames);
		action = new TransparentAnimationAction(start, end, frames);
		animation.addAnimationAction(action);
	}

}
