package view.ui.hallui;

import model.vo.UserVO;
import controller.netlistenerbl.NetBattleGameHallListenerBL;
import controller.netlistenerbl.NetCoopGameHallListenerBL;

public class MainUI {
	UserVO user;
	
	enum gameType{
		SINGLE,MULTIPLE,BATTLE
	}
	
	public MainUI(UserVO user){
		this.user=user;
//		String type=IOHelper.inputFromConsole("请输入游戏类型(SINGLE,MULTIPLE,BATTLE): ");

//		chooseMode(type);
	}
	
	@SuppressWarnings("unused")
	private void chooseMode(String type){
		if(type.equals(gameType.SINGLE.toString())){
			
		}else if(type.equals(gameType.MULTIPLE.toString())){
			CoopGameHallUI gameHall=new CoopGameHallUI(user);
			NetCoopGameHallListenerBL gameController=NetCoopGameHallListenerBL.getGameHallListener();
			gameController.setController(gameHall);
			gameHall.DoSomething();
			
		}else if(type.equals(gameType.BATTLE.toString())){
			BattleGameHallUI gameHall=new BattleGameHallUI(user);
			NetBattleGameHallListenerBL gameController = NetBattleGameHallListenerBL.getGameHallListener();
			gameController.setController(gameHall);
			gameHall.DoSomething();
		}
	}
	
}
