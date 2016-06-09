package view.ui.gameui.drawTile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import view.ui.gameui.BoardPanel;
import view.ui.gameui.ImageLibrary;

public class LockDrawTile implements DrawTile {
	DrawTile basicdraw;
	public static BufferedImage lockImg;
	
	public LockDrawTile(DrawTile d) {
		if(lockImg == null)
			lockImg = ImageLibrary.getLockImage();
		this.basicdraw = d;
	}
	
	@Override
	public void draw(Graphics g, Image img, int x, int y, BoardPanel board) {
		basicdraw.draw(g, img, x, y, board);
		g.drawImage(lockImg, x, y, board.getBlockEdge(), board.getBlockEdge(), board);
	}
	
}
