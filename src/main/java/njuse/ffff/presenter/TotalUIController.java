package njuse.ffff.presenter;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import njuse.ffff.data.DataReadController;
import njuse.ffff.dataservice.DataReaderService;
import njuse.ffff.presenterService.TotalControlService;
import njuse.ffff.ui.MainFrame;

public class TotalUIController implements TotalControlService{
	private MainFrame frame = null;
	private ArrayList<JPanel> panelList = new ArrayList<JPanel>();//存储点击界面先后顺序的列表

	private DataReaderService dataService;
	private static TotalUIController totalController = null;
//	private static PlayerCompareController playerCompareController = null;
//	private static PlayerFilterController playerFilterController = null;
//	private static PlayerInfoController playerInfoController = null;
//	private static TeamCompareController teamCompareController = null;
//	private static TeamInfoController teamInfoController = null;

	private TotalUIController() {
		bindDataService();
//		playerCompareController = PlayerCompareController.getInstance();
//		playerFilterController = PlayerFilterController.getInstance();
//		playerInfoController = PlayerInfoController.getInstance();
//		teamCompareController = TeamCompareController.getInstance();
//		teamInfoController = TeamInfoController.getInstance();
	}

	public static TotalUIController getInstance() {
		if (totalController == null) {
			totalController = new TotalUIController();
		}
		return totalController;
	}

	/**
	 * 新建主界面框架
	 */
	public void createFrame() {
		if (frame == null) {
			frame = new MainFrame();
		}
		frame.setVisible(true);
	}

	/**
	 * 绑定数据层
	 * TODO
	 */
	private void bindDataService() {
		dataService = new DataReadController();
	}
	
	/**
	 * 返回数据服务
	 */
	public DataReadController getDataReadController(){
		return (DataReadController) dataService;
	}

	/**
	 * 界面切换准备工作
	 * TODO
	 * @param panel
	 */
	public void switchToPanel(JPanel panel) {
		frame.switchToPanel(panel);
	}
	
	/**
	 * 初始化系统
	 * TODO
	 */
	public void initSystem() {
		try {
			dataService.initialize();
			createFrame();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 向界面列表添加当前界面
	 */
	public void addCurrentPanel(JPanel panel){
		panelList.add(panel);
	}
	
	/**
	 * 获得当前显示界面
	 */
	public JPanel getCurrentPanel(){
		return panelList.get(panelList.size()-1);
	}
	
	/**
	 * 返回到上个界面
	 */
	public void backToLastPanel() {
		JPanel panel = panelList.get(panelList.size()-2);
		panelList.remove(panelList.size()-1);
		switchToPanel(panel);
	}

}
