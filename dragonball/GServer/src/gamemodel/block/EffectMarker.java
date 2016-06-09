package gamemodel.block;

import gamemodel.Board;
import gamemodel.Position;

public interface EffectMarker {
	public void mark(Board board, Position p);
}
