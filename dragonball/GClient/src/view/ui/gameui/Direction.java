package view.ui.gameui;

public enum Direction {
	UP, DOWN, LEFT, RIGHT;

	public static final int _x[] = { 0, 0, -1, 1 };
	public static final int _y[] = { -1, 1, 0, 0 };
	public static final Direction rev[] = { DOWN, UP, RIGHT, LEFT };

	/* 相反方向 */
	public static Direction reverse(Direction d) {
		return rev[d.ordinal()];
	}

	public static Position getVetor(Direction d, int len) {
		return new Position(_x[d.ordinal()] * len, _y[d.ordinal()] * len);
	}

	public static Direction valueOf(int ordinal) {
		if (ordinal < 0 || ordinal >= values().length) {
			throw new IndexOutOfBoundsException("Invalid ordinal");
		}
		return values()[ordinal];
	}

}