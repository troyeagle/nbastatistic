package njuse.ffff.ui.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;

import javax.swing.Icon;
import javax.swing.JButton;

import njuse.ffff.ui.animation.IAnimation;

/**
 * 扩展的Button控件
 *
 * @author Li
 */
public class ButtonEx extends JButton implements IComponentEx {

	private static final long serialVersionUID = 1L;

	private float alpha = 1f;		// 控件的透明度
	private IAnimation animation;	// 控件绑定的动画
	private Image bgImage;			// 背景图片
	private float fntAlpha = 1f;	// 字体透明度
	private float bgAlpha = 1f;		// 背景透明度
	private boolean isOpaque;

	private boolean alphaDependOnParent = true;    // 控件透明度是否依赖于父控件

	private IButtonExListener listener;

	private Color bgMask;        // 背景遮罩
	private Color fntMask;        // 文字遮罩

	/**
	 * 无参的构造方法
	 */
	public ButtonEx() {
		this(null, null);
	}

	public ButtonEx(String text) {
		this(text, null);
	}

	public ButtonEx(Icon icon) {
		this(null, icon);
	}

	public ButtonEx(String text, Icon icon) {
		super(text, icon);

		initLook();

		// 初始化listener
		setButtonExListener(new BasicButtonExListener(this));
	}

	/**
	 * 初始化控件外观
	 */
	private void initLook() {
		setBorder(null);		// 默认无边框（待定
		setFocusPainted(false);	// 默认不绘制焦点
		setRolloverEnabled(false);
		setContentAreaFilled(false);
		isOpaque = true;
	}

	@Override
	public void setOpaque(boolean isOpaque) {
		this.isOpaque = isOpaque;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		Graphics2D gMask = (Graphics2D) g.create();    // 遮罩层画笔

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

			ComponentExUtilities.setGraphicsAntiAlias(gMask);
			ComponentExUtilities.setGraphicsAlpha(gMask, alpha * 0.5f);
			// 绘制背景遮罩
			if (bgMask != null && isEnabled()) {
				gMask.setColor(bgMask);
				gMask.fillRect(0, 0, getWidth(), getHeight());
			}
			super.paintComponent(g2d);
			// 绘制遮罩
			if (fntMask != null && isEnabled()) {
				gMask.setColor(fntMask);
				gMask.fillRect(0, 0, getWidth(), getHeight());
			}
			
			if (!isEnabled()) {
				paintUnable(gMask);
			}
		}
	}
	
	protected void paintUnable(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
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
	public boolean getOpaque() {
		return isOpaque;
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

	/**
	 * 设置控制按钮鼠标行为效果（移入/移出/按下等）的listener
	 *
	 * @param buttonExListener
	 */
	public void setButtonExListener(IButtonExListener buttonExListener) {
		if (listener != null) {
			removeMouseListener(listener);
			removeMouseMotionListener(listener);
			removeMouseWheelListener(listener);
		}
		listener = buttonExListener;
		if (listener != null) {
			addMouseListener(listener);
			addMouseMotionListener(listener);
			addMouseWheelListener(listener);
		}
	}
	
	public void setBackgroundMaskColor(Color c) {
		bgMask = c;
		repaint();
	}

	public void setMaskColor(Color c) {
		fntMask = c;
		repaint();
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
}
