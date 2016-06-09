package model.block;

import model.Board;
import model.Position;

public interface EffectMarker {
	public void mark(Board board, Position p);
}
