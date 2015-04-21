package njuse.ffff.ui.ver2;

public class UIEvent {

	private UIEventType type;

	private String message;

	private Object source;

	public UIEvent(UIEventType type) {
		this(type, null, null);
	}

	public UIEvent(UIEventType type, Object source) {
		this(type, null, source);
	}

	public UIEvent(UIEventType type, String message) {
		this(type, message, null);
	}

	public UIEvent(UIEventType type, String message, Object source) {
		this.type = type;
		this.message = message;
		this.source = source;
	}

	public UIEventType getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public Object getSource() {
		return source;
	}
}
