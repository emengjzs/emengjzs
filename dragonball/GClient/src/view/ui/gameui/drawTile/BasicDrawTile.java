package view.ui.gameui.drawTile;

import java.awt.Graphics;
import java.awt.Image;

import view.ui.gameui.BoardPanel;

public class BasicDrawTile implements DrawTile {
	
	public void draw(Graphics g, Image img, int x, int y, BoardPanel board) {
		g.drawImage(img, x, y, board.getBlockEdge(), board.getBlockEdge(), board);
	}

}
