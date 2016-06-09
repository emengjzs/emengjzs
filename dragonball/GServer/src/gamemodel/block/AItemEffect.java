package gamemodel.block;
import gamemodel.Board;
import gamemodel.Position;

public class AItemEffect implements EffectMarker {
	
	public static final int edge = 3;	//��ը��Χ3*3
	/**
	 * A���ߴ���Ч��
	 */
	@Override
	public void mark(Board board, Position p) {
		Position pp = new Position();	
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
