package view.ui;

import model.IO.IOHelper;
import model.vo.UserVO;
import controller.netlistenerbl.NetGameHallListenerBL_Stub;

public class MainUI_Stub {
	UserVO user;
	
	enum gameType{
		SINGLE,MULTIPLE,BATTLE
	}
	
	public MainUI_Stub(UserVO user){
		this.user=user;
		String type=IOHelper.inputFromConsole("请输入游戏类型(SINGLE,MULTIPLE,BATTLE): ");

		chooseMode(type);
	}
	
	private void chooseMode(String type){
		if(type.equals(gameType.SINGLE.toString())){
			
		}else if(type.equals(gameType.MULTIPLE.toString())){
			MultiGameHallUI_Stub gameHall=new MultiGameHallUI_Stub(user);
			NetGameHallListenerBL_Stub gameController=NetGameHallListenerBL_Stub.getGameHallListener();
			gameController.setController(gameHall);
			
		}else if(type.equals(gameType.BATTLE.toString())){
			
		}
	}
	
}
