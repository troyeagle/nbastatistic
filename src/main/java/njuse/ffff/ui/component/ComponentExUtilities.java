package njuse.ffff.ui.component;

import static java.awt.AlphaComposite.SrcOver;
import static java.awt.AlphaComposite.SrcIn;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class ComponentExUtilities {

	/**
	 * 获取一个控件的父控件的透明度
	 * 
	 * @param comp
	 * @return
	 */
	public static float getParentAlpha(IComponentEx comp) {
		float alpha = 1f;
		if (comp.getAlphaDependOnParent()) {
			Container parent = (Container) comp;
			while ((parent = parent.getParent()) != null) {
				if (parent instanceof IComponentEx)
					alpha *= ((IComponentEx) parent).getAlpha();
			}
		}
		return alpha;
	}

	public static float getAlpha(IComponentEx comp) {
		return getParentAlpha(comp) * comp.getAlpha();
	}

	/**
	 * 设置画笔透明度
	 * 
	 * @param g2d
	 *            目标画笔
	 * @param alpha
	 *            透明度
	 * @return
	 */
	public static Graphics2D setGraphicsAlpha(Graphics2D g2d, float alpha) {
		g2d.setComposite(SrcOver.derive(alpha));
		return g2d;
	}

	/**
	 * 设置画笔透明度（覆盖下方区域）
	 * 
	 * @param g2d
	 *            目标画笔
	 * @param alpha
	 *            透明度
	 * @return
	 */
	public static Graphics2D setGraphicsAlphaCovered(Graphics2D g2d, float alpha) {
		g2d.setComposite(SrcIn.derive(alpha));
		return g2d;
	}
	/**
	 * 设置指定画笔抗锯齿
	 * 
	 * @param g2d
	 * @return
	 */
	public static Graphics2D setGraphicsAntiAlias(Graphics2D g2d) {
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);	// 抗锯齿
		return g2d;
	}

	/**
	 * 为指定控件绘制背景
	 * 
	 * @param comp
	 * @param g
	 */
	public static void paintBackground(IComponentEx comp, Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		// 设置画笔
		float bgAlpha = getAlpha(comp) * comp.getBackgroundAlpha();
		if (bgAlpha > 1f)
			bgAlpha = 1f;
		setGraphicsAntiAlias(g2d);
		setGraphicsAlpha(g2d, bgAlpha);
		if (comp.getBackgroundPaint() != null) {
			g2d.setPaint(comp.getBackgroundPaint());
		}

		JComponent c = (JComponent) comp;
		int width = c.getWidth();
		int height = c.getHeight();
		if (comp.getOpaque()) {
			g2d.setColor(c.getBackground());
			g2d.fillRect(0, 0, width, height);
		}
		Image bgImage = comp.getBackgroundImage();
		if (bgImage != null) {
			g2d.drawImage(bgImage, 0, 0, width, height, null);
		}
	}
}
