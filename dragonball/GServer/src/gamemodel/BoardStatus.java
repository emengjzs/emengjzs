package gamemodel;

/*记录Board动态信息*/
public class BoardStatus {
	private boolean AItemElim;
	private boolean BItemElim;
	
	public BoardStatus() {
		AItemElim = false;
		BItemElim = false;
	}
	
	public void markAItem() {
		if(!AItemElim)
			AItemElim = true;
	}
	
	public void markBItem() {
		if(!BItemElim)
			BItemElim = true;
	}
	
	/**检测是否有B道具触发，并重置BItemElim*/
	public boolean getBItemRecord() {
		boolean r = BItemElim;
		BItemElim = false;
		return r;
	}
	
}
