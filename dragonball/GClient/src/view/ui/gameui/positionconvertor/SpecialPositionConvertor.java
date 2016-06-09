package view.ui.gameui.positionconvertor;

import view.ui.gameui.Position;


public class SpecialPositionConvertor implements PositionConvertor {
	private int edge;
	
	public SpecialPositionConvertor(int edge) {
		this.edge = edge;
	}
	
	public Position getPosition(int row, int col) {
		return new Position(row, col);
	}
	
	public Position sendPosition(Position p) {
		p.rev();
		return p;
	}
	
	public Position getInitImgPos(Position p, int offset) {
		return new Position(offset * edge, p.getY() * edge);
	}
	
	public int getOffSetIndex(Position p) {
		return p.getY();
	}
}