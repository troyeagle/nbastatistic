package njuse.ffff.presenter;

import njuse.ffff.presenterService.TotalControlService;

public class NBAMain {
	public static void main(String[] args) {
//		ControllerService service = UIController.getInstance();
//		service.initSystem();
		TotalControlService service = TotalUIController.getInstance();
		service.initSystem();
	}
}
