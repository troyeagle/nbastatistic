package njuse.ffff.presenter;

import njuse.ffff.presenterService.TotalControlService;

public class NBAMain {
	public static void main(String[] args) {
		TotalControlService service = TotalUIController.getInstance();
		service.initSystem();
	}
}
