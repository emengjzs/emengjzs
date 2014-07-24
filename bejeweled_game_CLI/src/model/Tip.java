package model;

public class Tip {
	public Position p1;
	public Direction d;	
	
	public Tip(Position p1, Direction d) {
		this.p1 = p1;
		this.d = d;
	}
	
	public Position getAnotherPosition(){
		return p1.toWard(d);
	}
}
