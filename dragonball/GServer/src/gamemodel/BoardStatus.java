package gamemodel;

/*��¼Board��̬��Ϣ*/
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
	
	/**����Ƿ���B���ߴ�����������BItemElim*/
	public boolean getBItemRecord() {
		boolean r = BItemElim;
		BItemElim = false;
		return r;
	}
	
}
