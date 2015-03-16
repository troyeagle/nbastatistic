package njuse.ffff.uiservice;

import javax.swing.JPanel;

/**
 * 应用窗体Frame的接口，用于管理panel的切换
 * 
 * @author Li
 *
 */
public interface FrameService {

	/**
	 * 切换到指定的面板
	 * 
	 * @param panel
	 *            目标面板
	 */
	void switchToPanel(JPanel panel);
}
