package njuse.ffff.ui.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.UIManager;

public class UpDownArrow implements Icon {
	/** 图表的大小。 */
	private int size = 12;

	/** 向上。 */
	public static final int UP = 0;

	/** 向下。 */
	public static final int DOWN = 1;
	
	/** 当前图标指向。 */
	private int direction;

	/**
	 * <BR>
	 * <UL>构造函数。
	 * <LI>构造函数。</LI>
	 * </UL>
	 * 
	 * @param i 图标的指向。
	 * @return 无。
	 * @throws 无。
	 */
	public UpDownArrow(int i) {
		direction = i;
	}

	/**
	 * <BR>
	 * <UL>得到图标的高。
	 * <LI></LI>
	 * </UL>
	 * 
	 * @param 无。
	 * @return 图标的高。
	 * @throws 无。
	 */
	public int getIconHeight() {
		return size;
	}

	/**
	 * <BR>
	 * <UL>得到图标的宽。
	 * <LI>得到图标的宽。</LI>
	 * </UL>
	 * 
	 * @param 无。
	 * @return 图标的高。
	 * @throws 无。
	 */
	public int getIconWidth() {
		return size;
	}

	/**
	 * <BR>
	 * <UL>绘制图标。
	 * <LI>绘制图标。</LI>
	 * </UL>
	 * 
	 * @param component。
	 * @param g 绘图对象。
	 * @param i。
	 * @param j。
	 * @return 无。
	 * @throws 无。
	 */
	public void paintIcon(Component component, Graphics g, int i, int j) {
		int k = i + size / 2;
		int l = i + 1;
		int i1 = (i + size) - 2;
		int j1 = j + 1;
		int k1 = (j + size) - 2;
		Color color = (Color) UIManager.get("controlDkShadow");
		if (direction == 0) {
			g.setColor(Color.white);
			g.drawLine(l, k1, i1, k1);
			g.drawLine(i1, k1, k, j1);
			g.setColor(color);
			g.drawLine(l, k1, k, j1);
		} else {
			g.setColor(color);
			g.drawLine(l, j1, i1, j1);
			g.drawLine(l, j1, k, k1);
			g.setColor(Color.white);
			g.drawLine(i1, j1, k, k1);
		}
	}
}
