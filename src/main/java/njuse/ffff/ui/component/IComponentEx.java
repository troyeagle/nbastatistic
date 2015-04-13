package njuse.ffff.ui.component;

import java.awt.Image;
import java.awt.Paint;

/**
 * 扩展的控件接口，支持已定义的动画以及更多选项
 * 
 * @author Li
 *
 */
public interface IComponentEx extends AnimationComponent {

	/**
	 * 设置控件的透明度是否受父控件影响
	 * 
	 * @param b
	 */
	void setAlphaDependOnParent(boolean b);

	/**
	 * 获取控件的透明度是否受父控件影响属性
	 * 
	 * @return
	 */
	boolean getAlphaDependOnParent();

	/**
	 * 设置控件的背景图片
	 * 
	 * @param i
	 */
	void setBackgroundImage(Image i);

	/**
	 * 获取控件的背景图片
	 * 
	 * @return
	 */
	Image getBackgroundImage();

	/**
	 * 在当前控件透明度下，设置字体的透明度（即字体透明度=控件透明度*此透明度）
	 * 
	 * @param alpha
	 */
	void setFontAlpha(float alpha);

	/**
	 * 获取控件的字体透明度
	 * 
	 * @return
	 */
	float getFontAlpha();

	/**
	 * 在当前控件透明度下，设置背景的透明度（即背景透明度=控件透明度*此透明度）
	 * 
	 * @param alpha
	 */
	void setBackgroundAlpha(float alpha);

	/**
	 * 获取控件的背景透明度
	 * 
	 * @return
	 */
	float getBackgroundAlpha();

	/**
	 * 类似isOpaque()，只是为了区分两个方法
	 * 
	 * @return 是否填充背景（不影响背景图片）
	 */
	boolean getOpaque();

	/**
	 * 设置文字画笔的Paint属性
	 * 
	 * @param paint
	 *            若为<code>null</code>，则为普通画笔
	 */
	void setFontPaint(Paint paint);

	/**
	 * 获取文字画笔的Paint属性
	 * 
	 * @return
	 */
	Paint getFontPaint();

	/**
	 * 设置背景画笔的Paint属性
	 * 
	 * @param paint
	 *            若为<code>null</code>，则为普通画笔
	 */
	void setBackgroundPaint(Paint paint);

	/**
	 * 获取背景画笔的Paint属性
	 * 
	 * @return
	 */
	Paint getBackgroundPaint();
}
