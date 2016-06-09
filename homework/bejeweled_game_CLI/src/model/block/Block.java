package model.block;

import model.Board;
import model.Position;

/**
 * ·½¿é
 * 
 * @author ×ÏË³
 * 
 */

public class Block {
	public static enum TYPE {
		PURPLE, GREEN, RED, YELLOW, BLUE,
		SPURPLE, SGREEN, SRED, SYELLOW, SBLUE, FIRE, None;
	}
	
	private static final TYPE tra_list[] = {
		TYPE.PURPLE, 
		TYPE.GREEN, 
		TYPE.RED, 
		TYPE.YELLOW, 
		TYPE.BLUE,
		TYPE.PURPLE, 
		TYPE.GREEN, 
		TYPE.RED, 
		TYPE.YELLOW, 
		TYPE.BLUE, 
		null,
		null
		};
	
	protected Position position;
	public TYPE type ;
	protected boolean toBeDel = false;
	protected int shapeFlag = 0;
	private int score = 0;
	
	public Block() {
		type = TYPE.None;
		position = new Position();
	}
	
	public Block(TYPE type) {
		this.type = type;
		position = new Position();
	}

	public Position getPosition() {
		return new Position(position.getX(), position.getY());
	}

	public void setPosition(Position p) {
		this.position.set(p.getX(), p.getY());
		//position = null;
		//this.position = p;
	}

	public boolean canMatch(Block b) {
		return 	tra_list[this.type.ordinal()] == tra_list[b.type.ordinal()];
	}

	public boolean isDifPosition(Block b) {
		return !position.equals(b.position);
	}

	public String toString() {
		return position.getY() + "," + position.getX();
	}
	
	public String toStringType() {
		return "" + position.getY() + "," + position.getX() + ","
				+ type.ordinal();
	}
	
	public void markEliminate(Board board) {
		if(toBeDel)	
			return;		
		toBeDel = true;
	}
	
	public boolean toBeDel() {
		return toBeDel;
	}

	public int getShapeFlag() {
		return shapeFlag;
	}

	public void setShapeFlag(int shapeFlag) {
		if(Math.abs(shapeFlag) > Math.abs(this.shapeFlag))
			this.shapeFlag = shapeFlag;			
	}
	
	public  boolean canClick() {
		return false;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
