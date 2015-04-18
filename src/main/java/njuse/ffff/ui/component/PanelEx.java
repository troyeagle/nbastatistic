package njuse.ffff.ui.component;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Paint;

import javax.swing.JPanel;

import njuse.ffff.ui.animation.IAnimation;

public class PanelEx extends JPanel implements IComponentEx {

	private static final long serialVersionUID = 1L;

	private float alpha = 1f;
	private IAnimation animation;
	private Image bgImage;
	private float bgAlpha = 1f;
	private boolean isOpaque;

	private boolean alphaDependOnParent = true;    // 控件透明度是否依赖于父控件

	public PanelEx() {
	}
	
	public PanelEx(LayoutManager layout) {
		super(layout);
	}
	
	@Override
	public void setOpaque(boolean isOpaque) {
		this.isOpaque = isOpaque;
	}

	@Override
	protected void paintComponent(Graphics g) {
		float alpha = ComponentExUtilities.getAlpha(this);
		// 透明度不为0时绘制
		if (alpha != 0f) {
			// 绘制背景
			ComponentExUtilities.paintBackground(this, g);
		}
	}

	@Override
	public void setAnimation(IAnimation animation) {
		if (this.animation != null) {
			animation.stop();
		}
		if (this.animation != animation) {
			this.animation = animation;
			animation.bind(this);
		}
	}

	@Override
	public void runAnimation() {
		animation.stop();
		animation.start();
	}

	@Override
	public void moveTo(int x, int y) {
		if (x != getX() || y != getY()) {
			setLocation(x, y);
			validate();
		}
	}

	@Override
	public void setAlpha(float alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			repaint();
		}
	}

	@Override
	public float getAlpha() {
		return alpha;
	}

	@Override
	public void setAlphaDependOnParent(boolean b) {
		alphaDependOnParent = b;
	}

	@Override
	public boolean getAlphaDependOnParent() {
		return alphaDependOnParent;
	}

	@Override
	public void setBackgroundImage(Image i) {
		bgImage = i;
		repaint();
	}

	@Override
	public Image getBackgroundImage() {
		return bgImage;
	}

	@Override
	public void setFontAlpha(float alpha) {
	}

	/**
	 * 不存在此属性
	 */
	@Override
	public float getFontAlpha() {
		return 0;
	}

	@Override
	public void setBackgroundAlpha(float alpha) {
		if (bgAlpha != alpha) {
			bgAlpha = alpha;
			repaint();
		}
	}

	@Override
	public float getBackgroundAlpha() {
		return bgAlpha;
	}

	@Override
	public boolean getOpaque() {
		return isOpaque;
	}

	@Override
	public void setFontPaint(Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public Paint getFontPaint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBackgroundPaint(Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public Paint getBackgroundPaint() {
		// TODO Auto-generated method stub
		return null;
	}
}
