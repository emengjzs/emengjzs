package view.ui.gameui.drawTile;

import java.awt.Graphics;
import java.awt.Image;

import view.ui.gameui.BoardPanel;

public interface DrawTile {
	//draw(Graphics g, Image img, int x, int y,BoardPanel board)
	public void draw(Graphics g, Image img, int x, int y,BoardPanel board);
}
