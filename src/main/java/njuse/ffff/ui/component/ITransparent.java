package njuse.ffff.ui.component;

/**
 * 控制透明度的接口
 * 
 * @author Li
 *
 */
public interface ITransparent {

	/**
	 * 设置控件的透明度属性
	 * 
	 * @param alpha
	 *            透明度
	 */
	public void setAlpha(float alpha);
	
	/**
	 * 获取控件当前的透明度
	 * @return
	 */
	public float getAlpha();
}
