package gamemodel;

import gamemodel.block.Block;
import gamemodel.block.FireBlock;
import gamemodel.block.SBlock;

import java.util.ArrayList;
import java.util.Iterator;



public class Board {
	private Block blocks[][];
	private RandomBase rb;												// ��������ɿ�����
	// private BoardSetting setting;
	// private GridStatus girdStatus[][];
	private static int length = BoardSetting.length; 					// ��
	private static int height = BoardSetting.height; 					// ��
	private final static int MATCH_NUM = BoardSetting.MATCH_NUM; 		// ������
	private int typeNum = BoardSetting.typeNum;
	private ScoreController sc;			
	public  BoardStatus status;											//��̬��Ϣ��
	private final static int MAX_DEATH_LOOP = 5; 						// ��ѭ���ж�����
	private final static int N_ITEM_KEY = 1; 							// ���ܵ���0
	private final static int A_ITEM_KEY = 2; 							// A token
	private final static int B_ITEM_KEY_1 = 3; 						// B token-1
	private final static int B_ITEM_KEY_2 = 4;						// B token-2
	
	/**������״������������
	 * 3 None
	 * 4 A
	 * 5 B
	 * 3-3 A
	 * 3-4 B
	 * 4-4... B
	 * 
	 */
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
	
	/****
	 * �������ӿ�ͨ�������������
	 * @author jzs
	 *
	 */
	private class DeadChecker implements Checker {
		public boolean check(Block origin, Block temp) {
			return (origin.canMatch(temp) && origin.isDifPosition(temp));
		}
	}
	
	/**
	 * ��������ֱ�������������
	 * @author jzs
	 *
	 */
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
		status = new BoardStatus();
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
	
	/**
	 * ��λ��p����Ϊ��
	 * @param p λ��
	 */
	private  void setEmpty(Position p) {
		// blocks[p.getY()][p.getX()].position.setX(-1);
		// blocks[p.getY()][p.getX()].position.setY(-1);
		blocks[p.getY()][p.getX()] = null;
	}
	
	/**����̨������Ϣ����ӿ�*/
	public interface Viewer {
		public void output(Block b);
	}
	
	/**����̨������Ϣ���
	  ��ʾÿ�β�������λ�ü��������*/
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
	
	/**����̨������Ϣ���
	  ��ʾ�����������*/
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
	
	/**
	 * ���ڵ���
	 * �Կ���̨��ʾ�����������
	 */
	public void show() {
		show(typeViewer);
	}
	
	/**���ڵ���
	 * �Կ���̨��ʾ��������������������߲������λ��
	 */
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
			Debug.output(i + "��");
			for (int j = 0; j < length; j++) {
				viewer.output(getBlock(i, j));
			}
			Debug.output("\n");
		}
	}
	
	/**
	 * ��������ϢתΪ���紫���õ��ַ�����Ϣ
	 * @return  String �ַ��� ��ʽ����һ��������ö�������ڶ���������ö����
	 * @eg		 1,2... �����һ�е�һ������Ϊ1�����ӣ���һ�еڶ���Ϊ2�� �������
	 */
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
	
	/**
	 * �ж�λ���Ƿ������̷�Χ��
	 * @param p λ�� Position
	 * @return boolean �жϽ��
	 */
	public boolean isIn(Position p) {		
		return !(p.getX() >= length  || p.getY() >= height || p.getX() < 0 || p.getY() < 0);
	}
	
	/**
	 * ��ʼ������
	 */
	public void start() {
		int end = typeNum;
		int t = 0;
		// �����ȡ
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
						// �����������
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
	
	/**
	 * �ж����������Ƿ�ﵽ���������Ҫ��3
	 * @param ArrayList<Block> matchList ����ͬɫ����������
	 * @return	boolean �жϽ��
	 */
	private boolean isMatch(ArrayList<Block> matchList) {
		return matchList.size() >= MATCH_NUM;
	}
	
	/**
	 * ������������λ�ã������Ӳ���Ϊͬһ��λ��
	 * @param Block block1 ����1
	 * @param Block block2 ����2
	 */
	@SuppressWarnings("unused")
	private void swap(Block block1, Block block2) {
		Position p1 = block1.getPosition();
		Position p2 = block2.getPosition();
		this.setBlock(p2, block1);
		this.setBlock(p1, block2);
	}
	
	/**
	 * ������������λ�ã������Ӳ���Ϊͬһ��λ��
	 * @param Position p1 ����λ��1
	 * @param Position p2 ����λ��2
	 */
	public void swap(Position p1, Position p2) {
		Block block1 = this.getBlock(p1);
		Block block2 = this.getBlock(p2);
		this.setBlock(p2, block1);
		this.setBlock(p1, block2);
		// this.set(x, y, b)
	}

	/**
	 * ���һ��������ˮƽ�����Ƿ������
	 * @param block ����
	 * @param origin ԭʼ����λ��
	 * @param checker �����
	 * @return ��������
	 */
	private ArrayList<Block> checkHorizontal(Block block, Position origin, Checker checker) {
		ArrayList<Block> result = new ArrayList<Block>(8);
		if (block == null)
			return result;
		// �����㷨
		// Position origin = block.getPosition();
		int row = origin.getY();
		int col = origin.getX() - 1;
		// ������
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
		col = origin.getX() + 1;// ����ԭλ���¿�ʼ
		result.add(block);
		// ���Ҽ��
		while (col < Board.length) {
			Block b = this.getBlock(row, col);
			if (b == null)
				break;
			else if (checker.check(block, b)) {
				result.add(b);
				col++;
			}
			// ����������
			else
				break;
		}
		return result;
	}

	/**
	 * ���һ�������ڴ�ֱ�����Ƿ������
	 * @param block
	 * @param origin
	 * @param checker
	 * @return ��������
	 */
	private ArrayList<Block> checkVertical(Block block, Position origin,
			Checker checker) {
		ArrayList<Block> result = new ArrayList<Block>(8);
		if (block == null)
			return result;
		// Position origin = block.getPosition();
		int row = origin.getY() - 1;
		int col = origin.getX();
		// ���ϼ��
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
		row = origin.getY() + 1;// ����ԭλ���¿�ʼ
		result.add(block);
		// ���¼��
		while (row < Board.height) {
			Block b = this.getBlock(row, col);
			if (b == null)
				break;
			else if (checker.check(block, b)) {
				result.add(b);
				row++;
			}
			// ����������
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
	 * ��ⵥ�����Ƿ����ͨ������������ 
	 * @return Direction ���� �������ӿ�ͨ�������ƶ����ﵽ��������
	 */
	private Direction checkSwapToMatch(Block b) {
		// ��ÿ��������
		Position point;
		for (Direction d : Direction.values()) {
			point = b.getPosition().toWard(d); // �ƶ�һλ
			if (isIn(point)) { // ��������
				if (isMatch(checkHorizontal(b, point, deadchecker))
						|| isMatch(checkVertical(b, point, deadchecker))) {
					// ������
					//GameController.d = d;
					return d;
				}
			}
		}
		return null;
	}

	/**
	 * ���ּ��
	 * @return
	 */
	private boolean checkDead() {
		return getTip() == null;
	}
	
	/**
	 * �õ���ʾ
	 * @return Tip ��ʾ
	 */
	public Tip getTip() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				int row = (i + lastTipPosition.getY()) % height;
				Block b = getBlock(row, j);
				if(b == null) 
					continue;
				Direction d = checkSwapToMatch(b);
				//if (b != null  && (isMatch(checkHorizontal(b))
				//				|| isMatch(checkVertical(b)) 
				//				|| d != null)) {
				if(d != null){
					lastTipPosition.set(j, row);
					return new Tip(new Position(j, row), d);
				}
			}
		}
		return null;
	}
	
	/**
	 * �õ���������
	 * @param min �л���������С��
	 * @param max �л��������ϴ���
	 * @return
	 */
	private int getShapeKey(int min, int max) {
		for (int i = 0; i < KEY_INDEX.length; i++) {
			if (min == KEY_INDEX[i][0] && max == KEY_INDEX[i][1]) {
				return KEY_INDEX[i][2];
			}
		}
		return min == 0 ? B_ITEM_KEY_1 : B_ITEM_KEY_2;
	}

	/**
	 * �жϲ������߲���ǵ���
	 * ��ĳ������Ϊ���ĵ㣬��������������õ���ʶ��
	 * �����������б�ʶ����Ƚϱ�ʶ��С��ֻ�и߱�ʶ���ܸ��ǵͱ�ʶ
	 * @param Verlist
	 * @param Horlist
	 * @param b
	 */
	private void checkShape(ArrayList<Block> Verlist, ArrayList<Block> Horlist,
			Block b) {
		// ����ﵽ����������
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
	
	/**
	 * �ж������Ƿ��п���������
	 * @return ��������
	 */
	public ArrayList<Block> checkInstable() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < length; j++) {
				Block b = getBlock(i, j);
				ArrayList<Block> horlist = this.checkHorizontal(b);
				ArrayList<Block> verlist = this.checkVertical(b);
				if (!isMatch(horlist) && !isMatch(verlist))
					continue;
				this.checkShape(verlist, horlist, b); // ȷ����״
				// markEliminate(verlist); //���������
				// markEliminate(horlist); //���������
			}
		}
		return getDeadBlockList();
	}
	
	/**
	 * ��������
	 * ���������λ�û�����仯
	 * @return �²������Ӷ���
	 */
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
						if(isMatch(checkHorizontal(block)) || isMatch(checkVertical(block)) )
							num ++;
						list.add(block);
					}
					break;	//TEST!
				}
			}
		}
		int loop = 0;
		boolean isDead = false;
		while ((isDead = checkDead()) == true || num > 3) {
			if(num > 3 && !isDead) {
				loop++;
				if(loop > MAX_DEATH_LOOP) {
					break;
				}	
			}
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
			this.checkShape(verlist, horlist, getBlock(pp)); // ȷ����״
			// markEliminate(verlist); //���������
			// markEliminate(horlist); //���������
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
	 * ������Ϸʱ�����е��ߴ���
	 * @return ��������
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
	
	/**
	 * ��������������������
	 * @param deadBlockList ��������
	 * @return ArrayList<Block> ���߶���
	 */
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
				if(b.type == Block.TYPE.FIRE){}
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
