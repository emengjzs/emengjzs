package view.ui.gameui;

public class GridStatus {
	public static enum State {
		Empty,
		Normal,
		Swap,
		ReSwap,
		Elim,
		Fall;
	}

	private State state[][];
	public GridStatus() {
		state = new State[Tiles.dimension][Tiles.dimension];
		for (int i = 0; i < Tiles.dimension; i++) {
			for(int j = 0; j < Tiles.dimension; j++)  {
					state[i][j] = State.Normal;
			}
		}
	}
	
	public State get(int row, int col) {
		return state[row][col];
	}
	
	public void set(int row, int col, State s) {
		state[row][col] = s;
	}
	
	public State get(Position p) {
		return state[p.getRow()][p.getCol()];
	}
	
	public void set(Position p, State s) {
		state[p.getRow()][p.getCol()] = s;
	}
	
	public void change(State olds, State news) {
		for (int i = 0; i < Tiles.dimension; i++) {
			for(int j = 0; j < Tiles.dimension; j++)  {
				if(state[i][j] == olds)
					state[i][j] = news;
			}
		}
	}
	
}
