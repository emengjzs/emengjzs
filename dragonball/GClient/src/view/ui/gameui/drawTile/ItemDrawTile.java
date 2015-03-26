package view.ui.gameui.drawTile;

import java.awt.Graphics;
import java.awt.Image;

import view.ui.gameui.BoardPanel;

public class ItemDrawTile implements DrawTile {
	DrawTile basicdraw;
	private int f;
	private double r = 0;
	
	public ItemDrawTile(int edge) {
		f = edge / 2;
	}
	
	
	
	public void draw(Graphics g, Image img, int x, int y, BoardPanel board) {
		int edge = board.getBlockEdge();
		if (f > 0) {
			g.drawImage(img, x + f , y  + f, edge - 2 * f, edge - 2 * f, board);
			r += 0.1;
			f -= r;
		}
		else {
			g.drawImage(img, x , y , edge , edge, board);
			//board.itemFin = true;
		}
	}
}
