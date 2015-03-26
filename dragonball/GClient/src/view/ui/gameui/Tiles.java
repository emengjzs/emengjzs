package view.ui.gameui;

public class Tiles {
	private Tile tiles[][];
	public static final int dimension = BoardPanel.dimension;
	public final int blockEdge;
	public Tiles(BoardSettings settings) {
		blockEdge = settings.getBlockEdge();
		tiles = new Tile[dimension][dimension];
	}

	public void init() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				Tile t = new Tile(j % 5);
				set(i, j, t);
			}
		}
	}

	public void init(Position pList[], int types[]) {
		for (int i = 0; i < pList.length; i++) {
			set(pList[i], new Tile(types[i]));
		}
	}

	public Tile get(int row, int col) {
		return tiles[row][col];
	}

	public void set(int row, int col, Tile t) {
		tiles[row][col] = t;
		t.setPosition(new Position(col, row));
	}

	public Tile get(Position p) {
		return tiles[p.getRow()][p.getCol()];
	}

	public void set(Position p, Tile t) {
		tiles[p.getRow()][p.getCol()] = t;
		t.setPosition(new Position(p));
	}
	
	public void setNomalImgPos(Tile t) {
		t.setNomalImgPos(blockEdge);
	}
	
	/**对所有棋子设置图片位置*/
	public void setNomalImgPos() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				tiles[i][j].setNomalImgPos(blockEdge);
			}
		}
	}
	
	
	public Position getNomalImgPos(Tile t) {
		return t.getNomalImgPos(blockEdge);
	}
	
	public boolean OnNomalImgPos(Tile t) {
		return t.OnNomalImgPos(blockEdge);
	}
	
	

}
