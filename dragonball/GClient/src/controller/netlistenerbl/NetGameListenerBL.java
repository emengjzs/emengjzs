package controller.netlistenerbl;

import controller.netlistenerservice.NetGameListenerService;
import view.ui.gameui.Controller;

public class NetGameListenerBL implements NetGameListenerService {

	static NetGameListenerBL netGameListenerBL = null;
	Controller gameView = new Controller();

	private NetGameListenerBL() {

	}

	public static NetGameListenerBL getNetGameListenerBL() {
		if (netGameListenerBL == null) {
			netGameListenerBL = new NetGameListenerBL();
		}

		return netGameListenerBL;
	}

	public void selectService(String message) {
		String[] str = message.split(" ");
		switch (str[1]) {
		case "GameStart":
			gameView.init(str[2], str[5], Integer.parseInt(str[3]),
					Integer.parseInt(str[4]));
			break;
		case "SwapSuccess":
			gameView.swapSuccess(str[2], Integer.parseInt(str[3]));
			break;
		case "SwapFail":
			gameView.swapFail(str[2], Integer.parseInt(str[3]));
			break;
		case "ItemSuccess":
			gameView.ItemSuccess(str[2], Integer.parseInt(str[3]));
			break;
		case "SecondEliminate":
			gameView.SecondEliminate(str[2], Integer.parseInt(str[3]));
			break;
		case "StartSuper":
			gameView.StartSuper(Integer.parseInt(str[2]));
			break;
		case "EndSuper":
			gameView.EndSuper(Integer.parseInt(str[2]));
			break;
		case "Tip":
			gameView.hint(str[2], Integer.parseInt(str[3]));
			break;
		case "TimeOut":
			gameView.timeOut(str[2], Integer.parseInt(str[3]));
			break;
		case "EndGame":
			gameView.endGame(message);
			break;
		case "LockB":
			gameView.lockBlock(Integer.parseInt(str[2]),
					Integer.parseInt(str[3]));
			break;
		case "EndLockB":
			gameView.unLock(Integer.parseInt(str[2]));
			break;
		case "ReplyCoopGameResult":
			gameView.showResult(message);
			break;
		case "ReplyBattleGameResult":
			gameView.showResult(message);
			break;
		case "ReplySingleGameResult":
			gameView.showResult(message);
			break;
		case "EndCombo":
			gameView.endCombo(Integer.parseInt(str[2]));
			break;
		}
	}
}
