package game.model.block;

import game.model.Board_Stub;
import game.model.BoardSetting_Stub;
import game.model.Position_Stub;

public class FireBlock_Stub extends Block_Stub {
	private EffectMarker_Stub effectMarker;
	public FireBlock_Stub() {
		this.type = TYPE.FIRE;
		position = new Position_Stub();
	}
	
	public void markEliminate(Board_Stub board) {
		if(toBeDel)	
			return;		
		toBeDel = true;
		board.addScore(BoardSetting_Stub.B_ITEM_SCORE);
		effectMarker = new BItemEffect_Stub();
		effectMarker.mark(board, this.position);
	}
	
	public boolean canMatch(Block_Stub b) {
		return false;
	}
	
	public  boolean canClick() {
		return true;
	}
}
