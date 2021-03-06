package model;

import java.util.ArrayList;
import java.util.Iterator;

import model.block.Block;
import model.block.FireBlock;
import model.block.SBlock;

public class Board {
	private Block blocks[][];
	private RandomBase rb;												// 随机数生成控制器
	// private BoardSetting setting;
	// private GridStatus girdStatus[][];
	private static int length = BoardSetting.length; 					// 列
	private static int height = BoardSetting.height; 					// 行
	private final static int MATCH_NUM = BoardSetting.MATCH_NUM; 		// 消除数
	private int typeNum = BoardSetting.typeNum;
	private ScoreController sc;			
	
	private final static int MAX_DEATH_LOOP = 5; 						// 死循环判定上限
	private final static int N_ITEM_KEY = 1; 							// 不能等于0
	private final static int A_ITEM_KEY = 2; 							// A token
	private final static int B_ITEM_KEY_1 = 3; 						// B token-1
	private final static int B_ITEM_KEY_2 = 4;						// B token-2
	
	private final int KEY_INDEX[][] = {{ 0, 0, 0}, 
										 { 0, 3, N_ITEM_KEY   },
										 { 0, 4, A_ITEM_KEY   }, 
										 { 0, 5, B_ITEM_KEY_1 }, 
										 { 3, 3, A_ITEM_KEY   },
										 { 3, 4, B_ITEM_KEY_1 }, 
										 { 3, 5, B_ITEM_KEY_2 }, 
										 { 4, 4, B_ITEM_KEY_1 }
										 };

	private Position lastTipPosition;
	
	public void setScoreController(ScoreController sc) {
		this.sc = sc;
	}


	/************************* checker ******************************/
	private interface Checker {
		public boolean check(Block origin, Block temp);
	}

	private class DeadChecker implements Checker {
		public boolean check(Block origin, Block temp) {
			return (origin.canMatch(temp) && origin.isDifPosition(temp));
		}
	}

	private class MatchChecker implements Checker {
		public boolean check(Block origin, Block temp) {
			return origin.canMatch(temp);
		}
	}
	
	private Checker deadchecker = new DeadChecker();
	private Checker matchchecker = new MatchChecker();

	
	public Board(int lenth, int height) {
		blocks = new Block[Board.height][Board.length];
		lastTipPosition = new Position(0,0);
		rb = new RandomBase();
		// girdStatus = new GridStatus[Board.height][Board.length];
	}

	public Block getBlock(int row, int col) {
		return blocks[row][col];
	}

	public Block getBlock(Position p) {
		return blocks[p.getY()][p.getX()];
	}

	public void setBlock(int row, int col, Block b) {
		blocks[row][col] = b;
		if (b != null)
			b.setPosition(new Position(col, row));
	}

	public void setBlock(Position p, Block b) {
		setBlock(p.getY(), p.getX(), b);
	}

	public void setEmpty(Position p) {
		// blocks[p.getY()][p.getX()].position.setX(-1);
		// blocks[p.getY()][p.getX()].position.setY(-1);
		blocks[p.getY()][p.getX()] = null;
	}
	
	public interface Viewer {
		public void output(Block b);
	}
	
	private class DelViewer implements Viewer {
		public void output(Block b) {
			if (b.getShapeFlag() == 0 && b.toBeDel())
				Debug.output("N ");
			else if (b.getShapeFlag() < 0)
				Debug.output(b.getShapeFlag() + 9 + " ");
			else
				Debug.output(b.getShapeFlag() + " ");
		}
	}
	
	private class TypeViewer implements Viewer {
		public void output(Block b) {
			if (b == null)
				Debug.output("N ");
			else
				Debug.output(b.type.ordinal() + " ");
		}
	}
	
	private  Viewer typeViewer = new TypeViewer();
	private  Viewer delViewer  = new DelViewer();
	
	public void show() {
		show(typeViewer);
	}

	public void showF() {
		show(delViewer);
	}	
	
	private void show(Viewer viewer) {
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

	public String toString() {
		String result = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				if (getBlock(i, j) == null)
					result += "N,";
				else
					result = result + getBlock(i, j).type.ordinal() + ",";
			}
			
		}
		return result;
	}

	public boolean isIn(Position p) {		
		return !(p.getX() >= length  || p.getY() >= height || p.getX() < 0 || p.getY() < 0);
	}

	public void start() {
		int end = typeNum;
		int t = 0;
		// 随机抽取
		do {
			//Debug.outputl("Dead!");
			for (int i = 0; i < height; i++) {
				for (int j = 0; j < length; j++) {
					int loop = 0;
					Block b = null;
					do {
						loop++;
						if (loop > MAX_DEATH_LOOP) {
							Debug.outputl("loop!");
							blocks = new Block[height][length];
							i = 0;
							j = 0;
						}
						// 增大可消除数
						// if(RandomBase.get(50)) {
						t = rb.getNextInt(end);
						// }
						b = new Block(BoardSetting.type[t]);
						setBlock(i, j, b);
					} while (isMatch(checkHorizontal(b))
							|| isMatch(checkVertical(b)));
				}
			}
		} while(checkDead());
	}

	public boolean isMatch(ArrayList<Block> matchList) {
		return matchList.size() >= MATCH_NUM;
	}

	public void swap(Block block1, Block block2) {
		Position p1 = block1.getPosition();
		Position p2 = block2.getPosition();
		this.setBlock(p2, block1);
		this.setBlock(p1, block2);
	}

	public void swap(Position p1, Position p2) {
		Block block1 = this.getBlock(p1);
		Block block2 = this.getBlock(p2);
		this.setBlock(p2, block1);
		this.setBlock(p1, block2);
		// this.set(x, y, b)
	}

	// 水平方向
	private ArrayList<Block> checkHorizontal(Block block, Position origin, Checker checker) {
		ArrayList<Block> result = new ArrayList<Block>(8);
		if (block == null)
			return result;
		// 弱智算法
		// Position origin = block.getPosition();
		int row = origin.getY();
		int col = origin.getX() - 1;
		// 向左检测
		while (col >= 0) {
			Block b = this.getBlock(row, col);
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
		while (col < Board.length) {
			Block b = this.getBlock(row, col);
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
	private ArrayList<Block> checkVertical(Block block, Position origin,
			Checker checker) {
		ArrayList<Block> result = new ArrayList<Block>(8);
		if (block == null)
			return result;
		// 弱智算法
		// Position origin = block.getPosition();
		int row = origin.getY() - 1;
		int col = origin.getX();
		// 向上检测
		while (row >= 0) {
			// Debug.output(col);
			Block b = this.getBlock(row, col);
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
		while (row < Board.height) {
			Block b = this.getBlock(row, col);
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

	private ArrayList<Block> checkHorizontal(Block block) {
		return checkHorizontal(block, block.getPosition(), matchchecker);
	}

	private ArrayList<Block> checkVertical(Block block) {
		return checkVertical(block, block.getPosition(), matchchecker);
	}

	/**
	 * 检测单个块是否可以通过交换而消除 
	 * @return boolean result 结果
	 */
	private Direction checkSwapToMatch(Block b) {
		// 对每个方向检测
		Position point;
		for (Direction d : Direction.values()) {
			point = b.getPosition().toWard(d); // 移动一位
			if (isIn(point)) { // 在区域内
				if (isMatch(checkHorizontal(b, point, deadchecker))
						|| isMatch(checkVertical(b, point, deadchecker))) {
					// 可消除
					//GameController.d = d;
					return d;
				}
			}
		}
		return null;
	}

	/**
	 * 死局检测
	 * @return
	 */
	private boolean checkDead() {
		return getTip() == null;
	}

	public Tip getTip() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				int row = (i + lastTipPosition.getY()) % height;
				Block b = getBlock(row, j);
				Direction d = checkSwapToMatch(b);
				//if (b != null  && (isMatch(checkHorizontal(b))
				//				|| isMatch(checkVertical(b)) 
				//				|| d != null)) {
				if(b != null && d != null){
					lastTipPosition.set(j, row);
					return new Tip(new Position(j, row), d);
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
		return min == 0 ? B_ITEM_KEY_1 : B_ITEM_KEY_2;
	}

	private void checkShape(ArrayList<Block> Verlist, ArrayList<Block> Horlist,
			Block b) {
		// 必须达到可消除条
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
			b.setScore(ScoreController.calScore(min)
					+ ScoreController.calScore(max));
			b.setShapeFlag(key);
			setShapeFlag(Verlist, -key);
			setShapeFlag(Horlist, -key);
		}
	}

	private void setShapeFlag(ArrayList<Block> list, int flag) {
		for (Block temp : list) {
			temp.setShapeFlag(flag);
			temp.markEliminate(this);
		}
	}

	public ArrayList<Block> checkInstable() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block b = getBlock(i, j);
				ArrayList<Block> horlist = this.checkHorizontal(b);
				ArrayList<Block> verlist = this.checkVertical(b);
				if (!isMatch(horlist) && !isMatch(verlist))
					continue;
				this.checkShape(verlist, horlist, b); // 确定形状
				// markEliminate(verlist); //标记消除块
				// markEliminate(horlist); //标记消除快
			}
		}
		return getDeadBlockList();
	}

	public ArrayList<Block> fall() {
		int num = 0;
		ArrayList<Block> list = new ArrayList<Block>();
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
						Block block = new Block(
								BoardSetting.type[rb.getNextInt(typeNum)]);
						setBlock(k, i, block);
						if(isMatch(checkHorizontal(block)) || isMatch(checkVertical(block)) ){
							num ++;
						}
						list.add(block);
					}
					break;	//TEST!
				}
			}
		}
		while (checkDead() || num > 1) {
			num = 0;
			Iterator<Block> itr = list.iterator();
			while (itr.hasNext()) {
				Block b = itr.next();
				b.type = BoardSetting.type[rb.getNextInt(typeNum)];
				if(isMatch(checkHorizontal(b)) || isMatch(checkVertical(b)) ){
					num ++;
				}
			}
		}
		return list;
	}

	public ArrayList<Block> checkMatchAfterItem(int row, int col) {
		Block b = getBlock(row, col);
		if (b.canClick())
			b.markEliminate(this);
		return getDeadBlockList();
	}

	public ArrayList<Block> checkMatchAfterSwap(int row, int col, Direction d) {
		Position p = new Position(col, row);
		Position ps[] = { p, p.toWard(d) };
		for (Position pp : ps) {
			ArrayList<Block> horlist = checkHorizontal(getBlock(pp));
			ArrayList<Block> verlist = checkVertical(getBlock(pp));
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

	private ArrayList<Block> getDeadBlockList() {
		ArrayList<Block> list = new ArrayList<Block>();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block b = getBlock(i, j);
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
	
	/**
	 * 结束游戏时将所有道具触发
	 * @return 消除队列
	 */
	public ArrayList<Block> endup() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block b = getBlock(i, j);
				if(b.canClick())
					b.markEliminate(this);
			}
		}
		return getDeadBlockList();
	}
	
	
	public ArrayList<Block> elimilate(ArrayList<Block> deadBlockList) {
		ArrayList<Block> itemList = new ArrayList<Block>(2);
		if (deadBlockList == null) {
			return itemList;
		}
		Iterator<Block> itr = deadBlockList.iterator();
		while (itr.hasNext()) {
			Block b = itr.next();
			b = this.getBlock(b.getPosition());
			if (b.toBeDel()) {
				if (b.getShapeFlag() > 0) {
					sc.addCurrentRoundScore(b.getScore());
				}
				if (b.getShapeFlag() == A_ITEM_KEY) {
					Block itemBlock = new SBlock(b.type);
					setBlock(b.getPosition(), itemBlock);
					itemList.add(itemBlock);
				} else if (b.getShapeFlag() == B_ITEM_KEY_1 || b.getShapeFlag() == B_ITEM_KEY_2) {
					Block itemBlock = new FireBlock();
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
