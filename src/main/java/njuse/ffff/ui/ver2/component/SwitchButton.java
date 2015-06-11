package njuse.ffff.ui.ver2.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import njuse.ffff.ui.component.ButtonEx;

public class SwitchButton extends ButtonEx {

	private static final long serialVersionUID = 1L;

	private boolean isActive;
	private boolean clickCanelEnabled;

	private Color activeColor;

	protected Icon[] icons;

	protected List<ConditionChangeListener> listeners;

	public SwitchButton() {
		this(null, null);
	}

	public SwitchButton(String text) {
		this(text, null);
	}

	public SwitchButton(Icon icon) {
		this(null, icon);
	}

	public SwitchButton(String text, Icon icon) {
		super(text, icon);
		icons = new Icon[2];
		icons[0] = icon;

		listeners = new ArrayList<>();

		activeColor = new Color(255, 255, 255, 192);

		addActionListener(e -> {
			setActive(!isActive || !clickCanelEnabled);
		});
	}

	public void setActive(boolean b) {
		if (isActive != b) {
			setStatus(b);
			ConditionChangeEvent e = new ConditionChangeEvent(b, this);
			listeners.forEach(l -> {
				l.actionPerformed(e);
			});
		}
	}

	void setStatus(boolean b) {
		if (isActive = b) {
			setMaskColor(activeColor);
			if (icons[1] != null)
				setIcon(icons[1]);
		} else {
			setMaskColor(null);
			setIcon(icons[0]);
		}
	}

	public void setActiveIcon(Icon icon) {
		icons[1] = icon;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActiveColor(Color c) {
		activeColor = c;
		repaint();
	}

	public Color getActiveColor() {
		return activeColor;
	}

	public void setClickCancelEnable(boolean b) {
		this.clickCanelEnabled = b;
	}

	public void addConditionChangeListener(ConditionChangeListener l) {
		listeners.add(l);
	}
}
