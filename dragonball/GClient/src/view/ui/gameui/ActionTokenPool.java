package view.ui.gameui;


public class ActionTokenPool {	
	public final static int num = 2;
	private boolean[] mTokenInused;

	public ActionTokenPool() {
		mTokenInused = new boolean[num];
		for(int i = 0; i < 4; i++) {
			mTokenInused[i] = false;
		}
	}
	
	public synchronized int takeToken() {
		for(int i = 0; i < num; i++){
			if (!mTokenInused[i]){
				mTokenInused[i] = true;
				return i;
			}
		}
		return -1;
	}
	
	public synchronized void resetToken() {
		for(int i = 0; i < num; i++)
		{
			mTokenInused[i] = false;
		}		
	}
	
	public synchronized void freeToken(int token) {
		if(token >= 0 && token < num) 
			mTokenInused[token] = false;
	}
}
