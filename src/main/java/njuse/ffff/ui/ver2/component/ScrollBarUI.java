package njuse.ffff.ui.ver2.component;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ScrollBarUI extends BasicScrollBarUI {

	@Override
	protected JButton createDecreaseButton(int orientation) {
		JButton btn = super.createDecreaseButton(orientation);
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setBackground(new Color(255, 255, 255, 64));
		btn.setFocusPainted(false);
		return btn;
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		JButton btn = super.createIncreaseButton(orientation);
		btn.setBorder(BorderFactory.createEmptyBorder());
		btn.setBackground(new Color(255, 255, 255, 64));
		btn.setFocusPainted(false);
		return btn;
	}

}
