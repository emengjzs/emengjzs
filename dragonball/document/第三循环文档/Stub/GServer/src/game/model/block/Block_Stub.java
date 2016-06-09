package game.model.block;

import game.model.Board_Stub;
import game.model.Position_Stub;

/**
 * ·½¿é
 * 
 * @author ×ÏË³
 * 
 */

public class Block_Stub {
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
	
	protected Position_Stub position;
	public TYPE type ;
	protected boolean toBeDel = false;
	protected int shapeFlag = 0;
	private int score = 0;
	
	public Block_Stub() {
		type = TYPE.None;
		position = new Position_Stub();
	}
	
	public Block_Stub(TYPE type) {
		this.type = type;
		position = new Position_Stub();
	}

	public Position_Stub getPosition() {
		return new Position_Stub(position.getX(), position.getY());
	}

	public void setPosition(Position_Stub p) {
		position = null;
		this.position = p;
	}

	public boolean canMatch(Block_Stub b) {
		return 	tra_list[this.type.ordinal()] == tra_list[b.type.ordinal()];
	}

	public boolean isDifPosition(Block_Stub b) {
		return !position.equals(b.position);
	}

	public String toString() {
		return position.getY() + "," + position.getX();
	}
	
	public String toStringType() {
		return "" + type.ordinal();
	}
	
	public void markEliminate(Board_Stub board) {
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
