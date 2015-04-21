package njuse.ffff.presenterService;

import javax.swing.JPanel;


public interface TotalControlService {
	//初始化系统
	public void initSystem();
	//转换界面
	public void switchToPanel(JPanel panel);
	//回退到上个界面
	public void backToLastPanel();
}
