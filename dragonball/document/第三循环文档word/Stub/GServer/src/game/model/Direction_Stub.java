package game.model;

public enum Direction_Stub {
	UP, DOWN, LEFT, RIGHT;

	public static final int _x[] = { 0, 0, -1, 1 };
	public static final int _y[] = { -1, 1, 0, 0 };
	public static final Direction_Stub rev[] = {DOWN, UP, RIGHT, LEFT};
	
	/*相反方向*/
	public Direction_Stub reverse(Direction_Stub d) {
		return rev[d.ordinal()];		
	}
}