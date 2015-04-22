package njuse.ffff.ui.ver2.component;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;

import njuse.ffff.ui.component.ButtonEx;

public class SwitchButton extends ButtonEx {

	private static final long serialVersionUID = 1L;

	private boolean isActive;
	private boolean clickCanelEnabled;

	private Color activeColor;

	protected Icon[] icons;

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

		activeColor = new Color(255, 255, 255, 192);

		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (clickCanelEnabled) {
					setActive(!isActive);
				} else {
					setActive(true);
				}
			}
		});
	}

	public void setActive(boolean b) {
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
}
