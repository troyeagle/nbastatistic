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

	protected Color[] bgColors;
	protected Color[] fgColors;

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
			if (bgColors[1] != null)
				super.setBackground(bgColors[1]);
			if (fgColors[1] != null)
				super.setForeground(fgColors[1]);

			setMaskColor(activeColor);
			if (icons[1] != null)
				setIcon(icons[1]);
		} else {
			super.setBackground(bgColors[0]);
			super.setForeground(fgColors[0]);
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

	public void setActiveBgColor(Color c) {
		bgColors[1] = c;
		if (isActive && c != null)
			super.setBackground(c);
	}

	public void setActiveFgColor(Color c) {
		fgColors[1] = c;
		if (isActive && c != null)
			super.setForeground(c);
	}

	@Override
	public void setBackground(Color bg) {
		if (bgColors == null)
			bgColors = new Color[2];
		bgColors[0] = bg;
		super.setBackground(bg);
	}

	@Override
	public void setForeground(Color fg) {
		if (fgColors == null)
			fgColors = new Color[2];
		fgColors[0] = fg;
		super.setForeground(fg);
	}
}
