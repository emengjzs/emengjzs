package view.ui.gameui.drawTile;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import view.ui.gameui.BoardPanel;

public class TipDrawTile implements DrawTile{	
	public static final int time_gap = 100;
	public int rc = 0xDD;
	public int gc = 0x65;
	public int bc = 0x13;
	private static float s = 3.5f;
	private int af = 0;
	DrawTile basicdraw;	
	public TipDrawTile(DrawTile d) {
		this.basicdraw = d;
	}

	@Override
	public void draw(Graphics g, Image img, int x, int y, BoardPanel board) {
		if(basicdraw != null) 
			basicdraw.draw(g, img, x, y, board);
		Graphics2D graphics = (Graphics2D) g; 
		int f = Math.abs(af);
		graphics.setStroke(new BasicStroke(s, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
		graphics.setColor(new Color( (f + 12) * rc / 20 ,(f + 12) * gc / 20,(f + 12) * bc / 20));
		graphics.drawRect(x, y, board.getBlockEdge(), board.getBlockEdge());
		++af;
		if(af > 7)
			af = -af;
	}
}