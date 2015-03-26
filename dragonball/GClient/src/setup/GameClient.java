package setup;

import controller.netlistenerbl.NetUserListenerBL;
import view.ui.login.Login;
import view.ui.myComponent.Music;



public class GameClient {

	/**
	 * @param args
	 */
	
	public static boolean MusicOn = true;
	public static Music music;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			Login login=new Login();
			login.init();
			NetUserListenerBL roomController=NetUserListenerBL.getUserControllerListener();
			roomController.setController(login);
		} catch(Exception e){
			//e.printStackTrace();
		}
		
	}

}
