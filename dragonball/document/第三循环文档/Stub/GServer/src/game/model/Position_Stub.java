package game.model;

public class Position_Stub {
	private int x = -1;
	private int y = -1;

	public Position_Stub() {
		x = -1;
		y = -1;
	}

	public Position_Stub(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Position_Stub(Position_Stub p) {
		this.x = p.x;
		this.y = p.y;
	}

	public int getX() {
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

	public boolean equals(Position_Stub p) {
		return p.getX() == x && p.getY() == y;
	}

	public boolean isNext(Position_Stub p) {
		return ((x - p.getX() == 1 || x - p.getX() == -1) && y == p.y)
				|| ((y - p.getY() == 1 || y - p.getY() == -1) && x == p.x);
	}

	public Position_Stub toWard(Direction_Stub direction) {
		/*
		 * switch (direction) { case UP: return new Position(x, y - 1); case
		 * DOWN: return new Position(x, y + 1); case LEFT: return new Position(x
		 * - 1, y); case RIGHT: return new Position(x + 1, y); }
		 */
		return new Position_Stub(x + Direction_Stub._x[direction.ordinal()], y
				+ Direction_Stub._y[direction.ordinal()]);
	}

	public void to(Direction_Stub direction) {
		x = x + Direction_Stub._x[direction.ordinal()];
		y = y + Direction_Stub._y[direction.ordinal()];

	}

}
