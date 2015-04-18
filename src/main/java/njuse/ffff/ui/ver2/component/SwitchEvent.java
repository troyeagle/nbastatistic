package njuse.ffff.ui.ver2.component;

public class SwitchEvent {

	private SwitchButton source;
	
	public SwitchEvent(SwitchButton source) {
		this.source = source;
	}
	
	public SwitchButton getSource() {
		return source;
	}
}
