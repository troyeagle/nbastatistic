package njuse.ffff.ui.ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import njuse.ffff.ui.component.ButtonEx;

public class TabLabel extends JPanel {

	private static final long serialVersionUID = 1L;

	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private ButtonEx button;

	private boolean isSelected;
	private int direction;
	private int lineWidth;

	public TabLabel() {
		this(null);
	}

	public TabLabel(String text) {
		this(text, BOTTOM, 2);
	}

	public TabLabel(String text, int direction, int lineWidth) {
		super(new BorderLayout());

		this.direction = direction;
		this.lineWidth = lineWidth;

		button = new ButtonEx(text);
		button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

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
		button.setBorder(BorderFactory.createEmptyBorder(y, x, y, x));
		JPanel labelArea = new JPanel(new BorderLayout(0, 0));
		labelArea.setOpaque(false);
		labelArea.add(button);

		add(labelArea);
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if (isSelected)
			setForeground(Color.WHITE);
		else
			setForeground(new Color(220, 220, 220));
		repaint();
	}

	@Override
	public void setFont(Font font) {
		super.setFont(font);
		if (button != null)
			button.setFont(font);
	}

	@Override
	public void setOpaque(boolean isOpaque) {
		super.setOpaque(isOpaque);
		if (button != null)
			button.setOpaque(isOpaque);
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		if (button != null)
			button.setForeground(fg);
	}

	public void addActionListener(ActionListener actionListener) {
		if (button != null)
			button.addActionListener(actionListener);
	}

	public boolean isSelected() {
		return isSelected;
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (isSelected) {
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

		super.paintComponent(g);
	}
}
