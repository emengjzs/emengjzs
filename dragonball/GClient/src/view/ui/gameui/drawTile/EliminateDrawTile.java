package view.ui.gameui.drawTile;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.ui.gameui.BoardPanel;

public class EliminateDrawTile implements DrawTile {
	private static final int frame = 6;
	private int f = 0;
	private double r = 0;
	private static BufferedImage elimilateImg[];

	static {
		elimilateImg = new BufferedImage[frame];
		try {
			for (int i = 0; i < frame; i++)
				elimilateImg[i] = ImageIO.read(new File("image/bomb_enemy_"
						+ i + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, Image img, int x, int y, BoardPanel board) {
		int edge = board.getBlockEdge();
		if (2 * f < edge) {
			g.drawImage(img, x + f, y + f, edge - 2 * f, edge - 2 * f, board);
			g.drawImage(elimilateImg[(int) (r % frame)], x, y, edge, edge,
					board);
			r += 0.5;
			f += r;
		}
	}
}
