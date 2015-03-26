package game.model.block;
import game.model.Board_Stub;
import game.model.Position_Stub;

public class AItemEffect_Stub implements EffectMarker_Stub {
	
	public static final int edge = 3;	//炮炸范围3*3
	/**
	 * A道具触发效果
	 */
	@Override
	public void mark(Board_Stub board, Position_Stub p) {
		Position_Stub pp = new Position_Stub();	
		int col = p.getX();
		int row = p.getY();
		int n = edge / 2;
		for(int i = -n; i <= n; i ++) {
			for(int j = -n; j <= n; j++ ) {
				pp.set(col + j, row + i);
				if(board.isIn(pp))
					board.getBlock(pp).markEliminate(board);
			}
		}		
	}
	
}
