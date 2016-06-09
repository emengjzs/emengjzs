package game.model.block;

import game.model.Board_Stub;
import game.model.Direction_Stub;
import game.model.Position_Stub;

public class BItemEffect_Stub implements EffectMarker_Stub {
	
	/**
	 * B道具触发效果
	 */
	@Override
	public void mark(Board_Stub board, Position_Stub p) {
		Position_Stub pp = new Position_Stub(p.getX(), 0);
		board.getBlock(p).markEliminate(board);
		while(board.isIn(pp)) {
			board.getBlock(pp).markEliminate(board);
			pp.to(Direction_Stub.DOWN);
		}
		pp = new Position_Stub(0, p.getY());
		while(board.isIn(pp)) {
			board.getBlock(pp).markEliminate(board);
			pp.to(Direction_Stub.RIGHT);
		}
	}
	
}
