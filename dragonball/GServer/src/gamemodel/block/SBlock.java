package gamemodel.block;

import gamemodel.Board;
import gamemodel.BoardSetting;
import gamemodel.Position;
/**
 * 

 * ����A����������ͬɫ������������ͨ��EffectMarkerʵ���Զ�����ߵ�Ч��*/
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
