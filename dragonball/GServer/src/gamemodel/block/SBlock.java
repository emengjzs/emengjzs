package gamemodel.block;

import gamemodel.Board;
import gamemodel.BoardSetting;
import gamemodel.Position;
/**
 * 

 * 棋子A，可与其他同色棋子消除，可通过EffectMarker实现自定义道具的效果*/
public class SBlock extends Block {
	private EffectMarker effectMarker;
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
	
	public SBlock(TYPE type) {
		this.type = typelist[type.ordinal()];
		position = new Position();
	}
	
	public void markEliminate(Board board) {
		if(toBeDel)	
			return;		
		toBeDel = true;
		board.addScore(BoardSetting.A_ITEM_SCORE);
		board.status.markAItem();
		effectMarker = new AItemEffect();
		effectMarker.mark(board, this.position);
	}
	
	public  boolean canClick() {
		return true;
	}
	
}
