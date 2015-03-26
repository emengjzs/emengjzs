package controller.netlistenerbl;

import view.uiservice.SingleService;
import common.Service;
import controller.netlistenerservice.NetSingleListenerService;

public class NetSingleListenerBL implements NetSingleListenerService {
	static SingleService singleController = null;
	
	static NetSingleListenerBL singleListenerBL = null;

	private NetSingleListenerBL() {

	}

	public static NetSingleListenerBL getSingleListener() {
		if (singleListenerBL == null) {
			singleListenerBL = new NetSingleListenerBL();
		}

		return singleListenerBL;
	}
	
	@SuppressWarnings("static-access")
	public void setSingleController(SingleService singleController) {
		this.singleController= singleController;
	}
	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");
		
		if (info[1].equals(Service.ReplySingleGameResult.toString())) {
//			receiveMessage(message);
		} else if(info[1].equals(Service.ReplySingleProp.toString())){
			singleController.receiveMessage(message);
		}
	}

}
