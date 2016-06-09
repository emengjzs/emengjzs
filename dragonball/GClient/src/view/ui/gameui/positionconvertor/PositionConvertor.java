package view.ui.gameui.positionconvertor;

import view.ui.gameui.Position;


public interface PositionConvertor {
	public Position getPosition(int row, int col);
	public Position sendPosition(Position p);
	public int getOffSetIndex(Position p);
	public Position getInitImgPos(Position p, int offset);
}
