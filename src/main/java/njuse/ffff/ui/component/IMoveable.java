package njuse.ffff.ui.component;

/**
 * 控制控件平移的接口
 * 
 * @author Li
 *
 */
public interface IMoveable {

	/**
	 * 将控件从当前位置横向移动x距离，纵向移动y距离
	 * 
	 * @param x
	 * @param y
	 */
	void move(int x, int y);

	/**
	 * 将控件移动到指定坐标
	 * 
	 * @param x
	 * @param y
	 */
	void moveTo(int x, int y);

	/**
	 * 获取控件x坐标
	 * 
	 * @return
	 */
	int getX();

	/**
	 * 获取控件y坐标
	 * 
	 * @return
	 */
	int getY();
}
