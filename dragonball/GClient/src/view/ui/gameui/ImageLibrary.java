package view.ui.gameui;

import javax.imageio.*;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
public class ImageLibrary {
	private final BufferedImage ImageList[]; 
	public static final int typenum = Tile.Type.num;
	public ImageLibrary() {
		ImageList = new BufferedImage[typenum];
		for(int i = 0; i < typenum; i++) {
			try {
			    ImageList[i] = ImageIO.read(new File("image/block" + i + ".png"));
			    } catch (IOException e) {
			    	e.printStackTrace();
			    }
		}		
	}	
	
	public BufferedImage getImage(Tile tile) {
		return ImageList[tile.type.ordinal()];
	}
	
	public static BufferedImage getFocusImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("image/focus.png"));
		} catch (IOException e) {e.printStackTrace();}
		return img;
	}
	
	public static BufferedImage getLockImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("image/lock.png"));
		} catch (IOException e) {e.printStackTrace();}
		return img;
	}
	
	public static BufferedImage getMoveImage() {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("image/move.png"));
		} catch (IOException e) {e.printStackTrace();}
		return img;
	}
}
