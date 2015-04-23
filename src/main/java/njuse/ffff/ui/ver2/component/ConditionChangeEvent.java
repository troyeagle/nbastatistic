package njuse.ffff.ui.ver2.component;

public class ConditionChangeEvent {

	private boolean isActive;
	private Object source;

	public ConditionChangeEvent(boolean isActive, Object source) {
		this.isActive = isActive;
		this.source = source;
	}

	public boolean isActive() {
		return isActive;
	}
	
	public Object getSource() {
		return source;
	}
}
