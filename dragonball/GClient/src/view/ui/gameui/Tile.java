package view.ui.gameui;

import java.awt.Graphics;

import view.ui.gameui.drawTile.BasicDrawTile;
import view.ui.gameui.drawTile.DrawTile;
import view.ui.gameui.drawTile.EmptyDrawTile;
import view.ui.gameui.drawTile.FocusDrawTile;

public class Tile {
	public static ImageLibrary imgLib = new ImageLibrary();
	public static enum Type {
		t1,
		t2,
		t3,
		t4,
		t5,
		ts1,
		ts2,
		ts3,
		ts4,
		ts5,
		bomb,
		none;
		public static int num = 11;
	}
	
	public Type type;
	protected Position pos;
	protected Position imgPos;
	protected DrawTile draw;
	public boolean isNewItem = false;
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public boolean isLock = false;
	
	public Tile(Type t) {
		this.type = t;
		this.pos = new Position();
		this.imgPos = new Position();
		draw = new BasicDrawTile();
	}
	
	public boolean isSame(int basicType) {
		return basicType - type.ordinal() == 0 || type.ordinal() - basicType == 5;
	}
	
	public Tile(int i) {
		this.type = Type.values()[i];
		this.pos = new Position();
		this.imgPos = new Position();
		draw = new BasicDrawTile();
	}

	
	public void setPosition(Position position) {
		pos.set(position);
		//imgPos.set(edge * position.getX(), edge * position.getY());
	}
	
	/*É÷ÓÃ£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡*/
	public Position getPosition () {
		return new Position(pos);
	}
	
	public void setNomalImgPos(int edge) {
		imgPos.set(edge * pos.getX(), edge * pos.getY());
	}
	
	public Position getNomalImgPos(int edge) {
		return new Position(edge * pos.getX(), edge * pos.getY());
	}
	
	public boolean OnNomalImgPos(int edge) {
		return imgPos.equals(getNomalImgPos(edge));
	}
	
	public void setImgPos(Position aniPos) {
		imgPos.set(aniPos);
	}
	public Position getImgPos() {
		return new Position(imgPos);
	}
	
	public boolean isNull() {
		return type == Type.none;
	}
	
	public void clear() {
		 type = Type.none;
		 this.pos = new Position();
		 this.imgPos = new Position();
		 draw = new EmptyDrawTile();
	}
	
	public void focus() {
		draw = new FocusDrawTile(draw);
	}
	
	public void unfocus() {
		draw = new BasicDrawTile();
	}
	
	public void setDraw(DrawTile draw) {
		this.draw = draw;
	}
	
	public DrawTile getDraw() {
		return draw;
	}
	
	public void draw(Graphics g, BoardPanel board) {		
		draw.draw(g, imgLib.getImage(this), imgPos.getX(), imgPos.getY(), board);
	}
	
	public boolean isItem() {
		if(Type.ts1.ordinal() <= type.ordinal() && type.ordinal() <= Type.bomb.ordinal())
			return true;
		return false;
	}
	
}
