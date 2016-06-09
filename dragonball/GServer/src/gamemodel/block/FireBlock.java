package gamemodel.block;

import gamemodel.Board;
import gamemodel.BoardSetting;
import gamemodel.Position;

/**
 * ����B������������������������ͨ��EffectMarkerʵ���Զ�����ߵ�Ч��*/
public class FireBlock extends Block {
	private EffectMarker effectMarker;
	public FireBlock() {
		this.type = TYPE.FIRE;
		position = new Position();
	}
	
	public void markEliminate(Board board) {
		if(toBeDel)	
			return;		
		toBeDel = true;
		board.status.markBItem();
		board.addScore(BoardSetting.B_ITEM_SCORE);
		effectMarker = new BItemEffect();
		effectMarker.mark(board, this.position);
	}
	
	public boolean canMatch(Block b) {
		return false;
	}
	
	public  boolean canClick() {
		return true;
	}
}
