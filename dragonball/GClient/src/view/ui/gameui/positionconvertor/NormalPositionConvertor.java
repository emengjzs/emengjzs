package view.ui.gameui.positionconvertor;

import view.ui.gameui.Position;

public class NormalPositionConvertor implements PositionConvertor {
	private int edge;
	
	public NormalPositionConvertor(int edge) {
		this.edge = edge;
	}
	
	
	public Position getPosition(int row, int col) {
		return new Position(col, row);
	}

	public Position sendPosition(Position p) {
		return p;
	}

	public Position getInitImgPos(Position p, int offset) {
		return new Position(p.getX() * edge, offset * edge);
	}

	public int getOffSetIndex(Position p) {
		return p.getX();
	}
}