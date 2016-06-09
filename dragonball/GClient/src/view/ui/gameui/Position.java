package view.ui.gameui;


public class Position {
	private int x = -1;
	private int y = -1;

	public Position() {
		x = -1;
		y = -1;
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}

	public int getX() {
		return x;
	}
	
	public int getRow() {
		return y;
	}
	
	public int getCol() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	
	public void set(Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public boolean equals(Position p) {
		return p.x == x && p.y == y;
	}

	public boolean isNext(Position p) {
		return ((x - p.x == 1 || x - p.x == -1) && y == p.y)
				|| ((y - p.y == 1 || y - p.y == -1) && x == p.x);
	}

	public Position toWard(Direction direction) {
		return new Position(x + Direction._x[direction.ordinal()], y
				+ Direction._y[direction.ordinal()]);
	}

	public void to(Direction direction) {
		x = x + Direction._x[direction.ordinal()];
		y = y + Direction._y[direction.ordinal()];

	}
	
	public Direction getDirection(Position p){
		for( int i = 0 ; i < 4; i++) {
			if(p.x - x == Direction._x[i] && p.y - y == Direction._y[i])
				return Direction.values()[i];
		}
		return null;
	}
	
	public void add(Position p) {
		this.x += p.x;
		this.y += p.y;
	}
	
	
	public String toString() {
		return y + "," + x;
	}
	
	public void rev() {
		int p = x;
		x = y;
		y = p;
	}
	
}
