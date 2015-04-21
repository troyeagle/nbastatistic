package njuse.ffff.ui.ver2;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class UIEventManager {

	private static Map<UIEventType, Vector<UIEventListener>> listeners;

	static {
		listeners = new HashMap<>();
		for (UIEventType e : UIEventType.values()) {
			listeners.put(e, new Vector<>());
		}
	}

	public static void addListener(UIEventListener l, UIEventType eventType) {
		listeners.get(eventType).add(l);
	}

	public static void removeListener(UIEventListener l, UIEventType eventType) {
		listeners.get(eventType).remove(l);
	}

	/**
	 * 通知事件发生
	 * 
	 * @param eventType
	 */
	public static void notify(UIEventType eventType) {
		notify(eventType, null, null);
	}

	/**
	 * 通知事件发生
	 * 
	 * @param eventType
	 * @param message
	 */
	public static void notify(UIEventType eventType, String message) {
		notify(eventType, message, null);
	}

	/**
	 * 通知事件发生
	 * 
	 * @param eventType
	 * @param source
	 */
	public static void notify(UIEventType eventType, Object source) {
		notify(eventType, null, source);
	}

	/**
	 * 通知事件发生
	 * 
	 * @param eventType
	 * @param message
	 * @param source
	 */
	public static void notify(UIEventType eventType, String message, Object source) {
		UIEvent e = new UIEvent(eventType, message, source);

		Vector<UIEventListener> list = listeners.get(UIEventType.ALL);
		list.forEach(l -> {
			l.actionPerformed(e);
		});

		if (eventType != UIEventType.ALL) {
			list = listeners.get(eventType);
			list.forEach(l -> {
				l.actionPerformed(e);
			});
		}
	}

}
