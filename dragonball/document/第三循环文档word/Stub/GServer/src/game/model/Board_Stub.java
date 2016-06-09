package game.model;

import java.util.ArrayList;
import java.util.Iterator;
import game.model.block.Block_Stub;
import game.model.block.FireBlock_Stub;
import game.model.block.SBlock_Stub;

public class Board_Stub {
	private Block_Stub blocks[][];
	// private BoardSetting setting;
	// private GridStatus girdStatus[][];
	private static int length = BoardSetting_Stub.length; // 列
	private static int height = BoardSetting_Stub.height; // 行
	private final static int MATCH_NUM = BoardSetting_Stub.MATCH_NUM; // 消除数
	private int typeNum = BoardSetting_Stub.typeNum;
	private ScoreController_Stub sc = new ScoreController_Stub();
	private final static int MAX_DEATH_LOOP = 5; // 死循环判定上限
	private final static int N_ITEM_KEY = 1; // 不能等于0
	private final static int A_ITEM_KEY = 2; // A token
	private final static int B_ITEM_KEY = 3; // B token

	private final int KEY_INDEX[][] = { { 0, 0, 0, 0 }, { 0, 3, N_ITEM_KEY },
			{ 0, 4, A_ITEM_KEY }, { 0, 5, B_ITEM_KEY }, { 3, 3, A_ITEM_KEY },
			{ 3, 4, B_ITEM_KEY }, { 3, 5, B_ITEM_KEY }, { 4, 4, B_ITEM_KEY } };

	public void setScoreController(ScoreController_Stub sc) {
		this.sc = sc;
	}

	private Checker deadchecker = new DeadChecker();
	private Checker matchchecker = new MatchChecker();

	private interface Checker {
		public boolean check(Block_Stub origin, Block_Stub temp);
	}

	private interface Viewer {
		public void output(Block_Stub b);
	}

	private class TypeViewer implements Viewer {
		public void output(Block_Stub b) {
			if (b == null)
				Debug.output("N ");
			else
				Debug.output(b.type.ordinal() + " ");
		}
	}

	private TypeViewer viewer = new TypeViewer();

	private class DelViewer implements Viewer {
		public void output(Block_Stub b) {
			if (b.getShapeFlag() == 0 && b.toBeDel())
				Debug.output("N ");
			else if (b.getShapeFlag() < 0)
				Debug.output(b.getShapeFlag() + 9 + " ");
			else
				Debug.output(b.getShapeFlag() + " ");
		}
	}

	private class DeadChecker implements Checker {
		public boolean check(Block_Stub origin, Block_Stub temp) {
			return (origin.canMatch(temp) && origin.isDifPosition(temp));
		}
	}

	private class MatchChecker implements Checker {
		public boolean check(Block_Stub origin, Block_Stub temp) {
			return origin.canMatch(temp);
		}
	}

	public Board_Stub(int lenth, int height) {
		blocks = new Block_Stub[Board_Stub.height][Board_Stub.length];
		// girdStatus = new GridStatus[Board.height][Board.length];
	}

	public Block_Stub getBlock(int row, int col) {
		return blocks[row][col];
	}

	public Block_Stub getBlock(Position_Stub p) {
		return blocks[p.getY()][p.getX()];
	}

	public void setBlock(int row, int col, Block_Stub b) {
		blocks[row][col] = b;
		// girdStatus[row][col] = GridStatus.Normal;
		if (b != null)
			b.setPosition(new Position_Stub(col, row));

	}

	public void setBlock(Position_Stub p, Block_Stub b) {
		setBlock(p.getY(), p.getX(), b);
	}

	public void setEmpty(Position_Stub p) {
		// blocks[p.getY()][p.getX()].position.setX(-1);
		// blocks[p.getY()][p.getX()].position.setY(-1);
		blocks[p.getY()][p.getX()] = null;
	}

	public void show() {
		show(viewer);
	}

	public void show(Viewer viewer) {
		Debug.split();
		sc.output();
		Debug.output("   ");
		for (int j = 0; j < length; j++) {
			Debug.output(j + " ");
		}
		Debug.endLine();
		Debug.output("  ");
		for (int j = 0; j < length; j++) {
			Debug.output("--");
		}
		Debug.endLine();
		for (int i = 0; i < height; i++) {
			Debug.output(i + "｜");
			for (int j = 0; j < length; j++) {
				viewer.output(getBlock(i, j));
			}
			Debug.output("\n");
		}
	}

	public void showF() {
		show(new DelViewer());
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				if (getBlock(i, j) == null)
					result += "N,";
				else
					result = result + getBlock(i, j).type.ordinal() + ",";
			}
			result += " ";
		}
		return result;
	}

	public boolean isIn(Position_Stub p) {
		return !(p.getX() < 0 || p.getY() < 0 || p.getX() >= length || p.getY() >= height);
	}

	public void start() {
		int end = typeNum;
		int t = 0;
		// 随机抽取
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				int loop = 0;
				Block_Stub b = null;
				do {
					loop++;
					if (loop > MAX_DEATH_LOOP) {
						blocks = new Block_Stub[height][length];
						i = 0;
						j = 0;
					}
					// 增大可消除数
					// if(RandomBase.get(50)) {
					t = RandomBase_Stub.getNextInt(end);
					// }
					b = new Block_Stub(BoardSetting_Stub.type[t]);
					setBlock(i, j, b);
				} while (isMatch(checkHorizontal(b))
						|| isMatch(checkVertical(b)));
			}
		}

	}

	public boolean isMatch(ArrayList<Block_Stub> matchList) {
		return matchList.size() >= MATCH_NUM;
	}

	public void swap(Block_Stub block1, Block_Stub block2) {
		Position_Stub p1 = block1.getPosition();
		Position_Stub p2 = block2.getPosition();
		this.setBlock(p2, block1);
		this.setBlock(p1, block2);
	}

	public void swap(Position_Stub p1, Position_Stub p2) {
		Block_Stub block1 = this.getBlock(p1);
		Block_Stub block2 = this.getBlock(p2);
		this.setBlock(p2, block1);
		this.setBlock(p1, block2);
		// this.set(x, y, b)
	}

	// 水平方向
	private ArrayList<Block_Stub> checkHorizontal(Block_Stub block, Position_Stub origin,
			Checker checker) {
		ArrayList<Block_Stub> result = new ArrayList<Block_Stub>();
		if (block == null)
			return result;
		// 弱智算法
		// Position origin = block.getPosition();
		int row = origin.getY();
		int col = origin.getX() - 1;
		// 向左检测
		while (col >= 0) {
			Block_Stub b = this.getBlock(row, col);
			if (b == null)
				break;
			else if (checker.check(block, b)) {
				result.add(b);
				col--;
			} else
				break;
		}
		col = origin.getX() + 1;// 回来原位重新开始
		result.add(block);
		// 向右检测
		while (col < Board_Stub.length) {
			Block_Stub b = this.getBlock(row, col);
			if (b == null)
				break;
			else if (checker.check(block, b)) {
				result.add(b);
				col++;
			}
			// 断链，结束
			else
				break;
		}
		return result;
	}

	// 垂直方向
	private ArrayList<Block_Stub> checkVertical(Block_Stub block, Position_Stub origin,
			Checker checker) {
		ArrayList<Block_Stub> result = new ArrayList<Block_Stub>();
		if (block == null)
			return result;
		// 弱智算法
		// Position origin = block.getPosition();
		int row = origin.getY() - 1;
		int col = origin.getX();
		// 向上检测
		while (row >= 0) {
			// Debug.output(col);
			Block_Stub b = this.getBlock(row, col);
			if (b == null)
				break;
			else if (checker.check(block, b)) {
				result.add(b);
				row--;
			} else
				break;
		}
		row = origin.getY() + 1;// 回来原位重新开始
		result.add(block);
		// 向下检测
		while (row < Board_Stub.height) {
			Block_Stub b = this.getBlock(row, col);
			if (b == null)
				break;
			else if (checker.check(block, b)) {
				result.add(b);
				row++;
			}
			// 断链，结束
			else
				break;
		}
		// Debug.output(result.size());
		return result;
	}

	public ArrayList<Block_Stub> checkHorizontal(Block_Stub block) {
		return checkHorizontal(block, block.getPosition(), matchchecker);
	}

	public ArrayList<Block_Stub> checkVertical(Block_Stub block) {
		return checkVertical(block, block.getPosition(), matchchecker);
	}

	/**
	 * 检测单个块是否可以通过交换而消除
	 * 
	 * @return boolean result 结果
	 */
	public boolean checkSwapToMatch(Block_Stub b) {
		// 对每个方向检测
		Position_Stub point;
		for (Direction_Stub d : Direction_Stub.values()) {
			point = b.getPosition().toWard(d); // 移动一位
			if (isIn(point)) { // 在区域内
				if (isMatch(checkHorizontal(b, point, deadchecker))
						|| isMatch(checkVertical(b, point, deadchecker))) {
					// 可消除
					GameController_Stub.d = d;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 死局检测
	 * 
	 * @return
	 */
	private boolean checkDead() {
		return getTip() == null;
	}

	public Position_Stub getTip() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block_Stub b = getBlock(i, j);
				if (b != null
						&& (isMatch(checkHorizontal(b))
								|| isMatch(checkVertical(b)) || checkSwapToMatch(b))) {
					return new Position_Stub(j, i);
				}
			}
		}
		return null;
	}

	private int getShapeKey(int min, int max) {
		for (int i = 0; i < KEY_INDEX.length; i++) {
			if (min == KEY_INDEX[i][0] && max == KEY_INDEX[i][1]) {
				return KEY_INDEX[i][2];
			}
		}
		return B_ITEM_KEY;
	}

	private void checkShape(ArrayList<Block_Stub> Verlist, ArrayList<Block_Stub> Horlist,
			Block_Stub b) {
		// 必须达到可消除条件
		int count = 0;
		if (!isMatch(Verlist)) {
			Verlist.clear();
			count++;
		}
		if (!isMatch(Horlist)) {
			Horlist.clear();
			count++;
		}
		if (count == 2)
			return;
		int max = Math.max(Verlist.size(), Horlist.size());
		int min = Math.min(Verlist.size(), Horlist.size());
		int key = getShapeKey(min, max);

		if (Math.abs(b.getShapeFlag()) < key) {
			b.setScore(ScoreController_Stub.calScore(min)
					+ ScoreController_Stub.calScore(max));
			b.setShapeFlag(key);
			setShapeFlag(Verlist, -key);
			setShapeFlag(Horlist, -key);
		}
	}

	private void setShapeFlag(ArrayList<Block_Stub> list, int flag) {
		for (Block_Stub temp : list) {
			temp.setShapeFlag(flag);
			temp.markEliminate(this);
		}
	}

	public ArrayList<Block_Stub> checkInstable() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block_Stub b = getBlock(i, j);
				ArrayList<Block_Stub> horlist = this.checkHorizontal(b);
				ArrayList<Block_Stub> verlist = this.checkVertical(b);
				if (!isMatch(horlist) && !isMatch(verlist))
					continue;
				this.checkShape(verlist, horlist, b); // 确定形状
				// markEliminate(verlist); //标记消除块
				// markEliminate(horlist); //标记消除快
			}
		}
		return getDeadBlockList();
	}

	public ArrayList<Block_Stub> fall() {
		ArrayList<Block_Stub> list = new ArrayList<Block_Stub>();
		for (int i = 0; i < length; i++) {
			for (int j = height - 1; j >= 0; j--) {
				if (blocks[j][i] == null) {
					int start = j;
					for (int k = j - 1; k >= 0; k--) {
						if (blocks[k][i] != null) {
							setBlock(start--, i, blocks[k][i]);
							blocks[k][i] = null;
						}
					}
					for (int k = start; k >= 0; k--) {
						Block_Stub block = new Block_Stub(
								Block_Stub.TYPE.values()[RandomBase_Stub
										.getNextInt(typeNum)]);
						setBlock(k, i, block);
						list.add(block);
					}
				}
			}
		}
		while (checkDead()) {
			Iterator<Block_Stub> itr = list.iterator();
			while (itr.hasNext()) {
				itr.next().type = BoardSetting_Stub.type[RandomBase_Stub
						.getNextInt(typeNum)];
			}
		}
		return list;
	}

	public ArrayList<Block_Stub> checkMatchAfterSwap(int row, int col) {
		Block_Stub b = getBlock(row, col);
		if (b.canClick())
			b.markEliminate(this);
		return getDeadBlockList();
	}

	public ArrayList<Block_Stub> checkMatchAfterSwap(int row, int col, Direction_Stub d) {
		Position_Stub p = new Position_Stub(col, row);
		Position_Stub ps[] = { p, p.toWard(d) };
		for (Position_Stub pp : ps) {
			ArrayList<Block_Stub> horlist = checkHorizontal(getBlock(pp));
			ArrayList<Block_Stub> verlist = checkVertical(getBlock(pp));
			this.checkShape(verlist, horlist, getBlock(pp)); // 确定形状
			// markEliminate(verlist); //标记消除块
			// markEliminate(horlist); //标记消除快
		}
		return getDeadBlockList();
	}

	/*
	 * private void markEliminate(ArrayList<Block> elist) { for(Block b : elist)
	 * { b.markEliminate(this); } }
	 */

	private ArrayList<Block_Stub> getDeadBlockList() {
		ArrayList<Block_Stub> list = new ArrayList<Block_Stub>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block_Stub b = getBlock(i, j);
				if (b.toBeDel()) {
					list.add(b);
				}
			}
		}
		return list;
	}

	public void addScore(int s) {
		sc.addCurrentRoundScore(s);
	}

	public ArrayList<Block_Stub> elimilate(ArrayList<Block_Stub> deadBlockList) {
		ArrayList<Block_Stub> itemList = new ArrayList<Block_Stub>(2);
		if (deadBlockList == null) {
			return itemList;
		}
		Iterator<Block_Stub> itr = deadBlockList.iterator();
		while (itr.hasNext()) {
			Block_Stub b = itr.next();
			if (b.toBeDel()) {
				if (b.getShapeFlag() > 0) {
					sc.addCurrentRoundScore(b.getScore());
				}
				if (b.getShapeFlag() == A_ITEM_KEY) {
					Block_Stub itemBlock = new SBlock_Stub(b.type);
					setBlock(b.getPosition(), itemBlock);
					itemList.add(itemBlock);
				} else if (b.getShapeFlag() == B_ITEM_KEY) {
					Block_Stub itemBlock = new FireBlock_Stub();
					setBlock(b.getPosition(), itemBlock);
					itemList.add(itemBlock);
				} else {
					setEmpty(b.getPosition());
				}
				itr.remove();
				b = null;
			}
		}
		return itemList;
	}

}
