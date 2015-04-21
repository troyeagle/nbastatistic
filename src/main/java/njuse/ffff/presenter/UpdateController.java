package njuse.ffff.presenter;

import njuse.ffff.presenterService.UpdateService;

public class UpdateController implements UpdateService{
	private static UpdateController updateController = null;
	private static TotalUIController totalController = null;
	
	//数据层是否更新参数
	private static boolean isUpdated = false;
	
	private UpdateController() {
		totalController = TotalUIController.getInstance();
	}

	public static UpdateController getInstance() {
		if (updateController == null) {
			updateController = new UpdateController();
		}
		return updateController;
	}
	/**
	 * 数据更新后，通知界面逻辑层更新数据
	 */
	public void informUpdate() {
		dataIsUpdated();
	}
	
	/**
	 * 数据层更新后改变状态
	 */
	private void dataIsUpdated(){
		isUpdated = true;
	}
	
	/**
	 * 时时监测数据层更新状态
	 * 如果数据层更新了，就更新界面显示
	 */
	public void checkForUpdate(){
		//循环检查
		while(isUpdated==false){
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//调用更新界面显示
		totalController.refreshView();
		isUpdated = false;
	}

}
