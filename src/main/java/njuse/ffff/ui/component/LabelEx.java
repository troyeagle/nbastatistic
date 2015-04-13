package njuse.ffff.ui.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;

import javax.swing.Icon;
import javax.swing.JLabel;

import njuse.ffff.ui.animation.IAnimation;

public class LabelEx extends JLabel implements IComponentEx {

	private static final long serialVersionUID = 1L;

	private float alpha = 1f;		// 控件的透明度
	private IAnimation animation;	// 控件绑定的动画
	private Image bgImage;			// 背景图片
	private float fntAlpha = 1f;	// 字体透明度
	private float bgAlpha = 1f;		// 背景透明度

	private boolean alphaDependOnParent = true;    // 控件透明度是否依赖于父控件

	/**
	 * 无参的构造方法
	 */
	public LabelEx() {
		this("", null, LEADING);
	}

	public LabelEx(String text) {
		this(text, null, LEADING);
	}

	public LabelEx(Icon image) {
		this(null, image, CENTER);
	}

	public LabelEx(String text, Icon image, int horizontalAlignment) {
		super(text, image, horizontalAlignment);

		initLook();
	}

	public LabelEx(Icon image, int horizontalAlignment) {
		this(null, image, horizontalAlignment);
	}

	public LabelEx(String text, int horizontalAlignment) {
		this(text, null, horizontalAlignment);
	}

	/**
	 * 初始化控件外观
	 */
	private void initLook() {
		setBorder(null);		// 默认无边框（待定
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		// 设置画笔
		float alpha = ComponentExUtilities.getAlpha(this);

		// 透明度不为0时绘制
		if (alpha != 0f) {
			// 绘制背景
			ComponentExUtilities.paintBackground(this, g);

			// 绘制文字（和图标？）
			ComponentExUtilities.setGraphicsAntiAlias(g2d);
			float fontAlpha = alpha * fntAlpha;
			ComponentExUtilities.setGraphicsAlpha(g2d, fontAlpha);

			super.paintComponent(g2d);
		}
	}

	@Override
	public void setAlpha(float alpha) {
		if (this.alpha != alpha) {
			this.alpha = alpha;
			if (this.alpha == 0f) {
				setVisible(false);
			} else {
				setVisible(true);
				repaint();
			}
		}
	}

	@Override
	public float getAlpha() {
		return alpha;
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
	public void setBackgroundImage(Image i) {
		bgImage = i;
		repaint();
	}

	@Override
	public void setBackgroundAlpha(float alpha) {
		if (alpha != bgAlpha) {
			bgAlpha = alpha;
			repaint();
		}
	}

	@Override
	public Image getBackgroundImage() {
		return bgImage;
	}

	@Override
	public float getBackgroundAlpha() {
		return bgAlpha;
	}

	@Override
	public void setFontPaint(Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackgroundPaint(Paint paint) {
		// TODO Auto-generated method stub

	}

	@Override
	public Paint getFontPaint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Paint getBackgroundPaint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFontAlpha(float alpha) {
		fntAlpha = alpha;
	}

	@Override
	public float getFontAlpha() {
		return fntAlpha;
	}

	@Override
	public void setAlphaDependOnParent(boolean b) {
		if (alphaDependOnParent != b) {
			alphaDependOnParent = b;
			repaint();
		}
	}

	@Override
	public boolean getAlphaDependOnParent() {
		return alphaDependOnParent;
	}

	@Override
	public void moveTo(int x, int y) {
		if (x != getX() || y != getY()) {
			setLocation(x, y);
			validate();
		}
	}

	@Override
	public int getX() {
		return super.getX();
	}

	@Override
	public int getY() {
		return super.getY();
	}

	@Override
	public boolean getOpaque() {
		return isOpaque();
	}

}
