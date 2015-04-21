package njuse.ffff.ui.ver2.component;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;

public class TabLabelButton extends SwitchButton {

	private static final long serialVersionUID = 1L;

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private int direction;
	private int lineWidth;

	public TabLabelButton() {
		this(null);
	}

	public TabLabelButton(String text) {
		this(text, BOTTOM, 2);
	}

	public TabLabelButton(String text, int direction, int lineWidth) {
		super(text);
		this.direction = direction;
		this.lineWidth = lineWidth;

		int x = 0;
		int y = 0;
		switch (direction) {
		case TOP:
		case BOTTOM:
			y = lineWidth;
			break;
		default:
			x = lineWidth;
		}
		setBorder(BorderFactory.createEmptyBorder(y, x, y, x));
		setActiveColor(new Color(0, 0, 0, 0));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (isActive()) {
			Graphics g2 = g.create();
			int lineX = 0, lineY = 0, lineW = getWidth(), lineH = getHeight();
			switch (direction) {
			default:	// bottom
				lineY = this.getHeight() - lineWidth;
			case TOP:
				lineH = lineWidth;
				break;
			case RIGHT:
				lineX = this.getWidth() - lineWidth;
			case LEFT:
				lineW = lineWidth;
				break;
			}
			g2.setColor(getForeground());
			g2.fillRect(lineX, lineY, lineW, lineH);
		}
	}
}
