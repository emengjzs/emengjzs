package gamemodel;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	public static final int _x[] = { 0, 0, -1, 1 };
	public static final int _y[] = { -1, 1, 0, 0 };
	public static final Direction rev[] = { DOWN, UP, RIGHT, LEFT };

	/* 相反方向 */
	public Direction reverse(Direction d) {
		return rev[d.ordinal()];
	}
	

}