package model.block;

import model.Board;
import model.BoardSetting;
import model.Position;

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
