package view.ui.gameui;

import java.awt.Graphics;

import view.ui.gameui.drawTile.EmptyDrawTile;


public class EmptyTile extends Tile {
	
	/*make Empty tile*/
	public EmptyTile() {
		super(Type.none);
		draw = new EmptyDrawTile();
	}
	
	
	//public void unfocus() {
	//	draw = new EmptyDrawTile();
	//}
	
	public void draw(Graphics g, BoardPanel board) {		
	}
	
}
