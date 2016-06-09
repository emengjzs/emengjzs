package game.model.block;

import game.model.Board_Stub;
import game.model.BoardSetting_Stub;
import game.model.Position_Stub;

public class SBlock_Stub extends Block_Stub {
	private EffectMarker_Stub effectMarker;
	private static TYPE typelist[] = {TYPE.SPURPLE, 
									TYPE.SGREEN, 
									TYPE.SRED, 
									TYPE.SYELLOW, 
									TYPE.SBLUE,
									TYPE.SPURPLE, 
									TYPE.SGREEN, 
									TYPE.SRED, 
									TYPE.SYELLOW, 
									TYPE.SBLUE, 
									TYPE.None
									};
	
	public SBlock_Stub(TYPE type) {
		this.type = typelist[type.ordinal()];
		position = new Position_Stub();
	}
	
	public void markEliminate(Board_Stub board) {
		if(toBeDel)	
			return;		
		toBeDel = true;
		board.addScore(BoardSetting_Stub.A_ITEM_SCORE);
		effectMarker = new AItemEffect_Stub();
		effectMarker.mark(board, this.position);
	}
	
	public  boolean canClick() {
		return true;
	}
	
}
