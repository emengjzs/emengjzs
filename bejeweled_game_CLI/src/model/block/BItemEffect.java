package model.block;

import model.Board;
import model.Direction;
import model.Position;

public class BItemEffect implements EffectMarker {
	
	/**
	 * B道具触发效果
	 */
	@Override
	public void mark(Board board, Position p) {
		Position pp = new Position(p.getX(), 0);
		board.getBlock(p).markEliminate(board);
		while(board.isIn(pp)) {
			board.getBlock(pp).markEliminate(board);
			pp.to(Direction.DOWN);
		}
		pp = new Position(0, p.getY());
		while(board.isIn(pp)) {
			board.getBlock(pp).markEliminate(board);
			pp.to(Direction.RIGHT);
		}
	}
	
}
