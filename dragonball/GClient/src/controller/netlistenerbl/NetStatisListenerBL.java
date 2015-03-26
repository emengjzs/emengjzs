package controller.netlistenerbl;

import common.Service;
import view.uiservice.BattleStatisService;
import view.uiservice.CoopStatisService;
import view.uiservice.SingleStatisService;
import controller.netlistenerservice.NetStatisListenerService;

public class NetStatisListenerBL implements NetStatisListenerService {
	static CoopStatisService coopController = null;
	static SingleStatisService singleController = null;
	static BattleStatisService battleController = null;

	static NetStatisListenerBL statisListenerBL = null;

	private NetStatisListenerBL() {

	}

	public static NetStatisListenerBL getStatisControllerListener() {
		if (statisListenerBL == null) {
			statisListenerBL = new NetStatisListenerBL();
		}

		return statisListenerBL;
	}

	@SuppressWarnings("static-access")
	public void setCoopController(CoopStatisService coopController) {
		this.coopController = coopController;
	}
	
	@SuppressWarnings("static-access")
	public void setSingleController(SingleStatisService singleController) {
		this.singleController = singleController;
	}
	
	@SuppressWarnings("static-access")
	public void setBattleController(BattleStatisService battleController) {
		this.battleController = battleController;
	}
	@Override
	public void selectService(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");

		if (info[1].equals(Service.ReplyCoopStatis.toString())) {
			coopController.receiveMessage(info[2]);
		} else if(info[1].equals(Service.ReplySingleStatis.toString())){
			singleController.receiveMessage(message);
		} else if(info[1].equals(Service.ReplySingleTen.toString())){
			singleController.receiveMessage(message);
		} else if(info[1].equals(Service.ReplySingleDaily.toString())){
			singleController.receiveMessage(message);
		} else if(info[1].equals(Service.ReplyBattleStatis.toString())){
			battleController.receiveMessage(info[2]);
		} else if(info[1].equals(Service.ReplySingleTop.toString())){
			singleController.receiveMessage(message);
		}
	}

}
