package gamemodel;

import gamemodel.block.Block;

import java.util.ArrayList;

/**���Ը߲��������ּ��ѹ������
 * ͨ���Զ�������Ϸʵ�����ּ��*/
public class GameController implements Runnable{
	public static Direction d;
	private Board board;
	ScoreController sc;
	mode m;
	int n = 0;

	// private ArrayList<Block> eliminatelist; // ��������

	public GameController() {
		board = new Board(9, 9);
		sc = new ScoreController();
		board.setScoreController(sc);
	}

	public void move(int row, int col, Direction d) {	
		ArrayList<Block> eliminatelist  = null;
		long a = System.currentTimeMillis();
		Position p = new Position(col, row);
		synchronized(board) {
		if (board.isIn(p)) {
			if (d == null) {
				eliminatelist = board.checkMatchAfterItem(row, col);
			} else {
				Position pp = p.toWard(d);
				if (board.isIn(pp)) {
					board.swap(p, pp);
					eliminatelist = board.checkMatchAfterSwap(row, col, d);
					if (eliminatelist.size() == 0)
						board.swap(p, pp);
				}
			}
			if (eliminatelist.size() > 0) {
				board.showF();
				board.elimilate(eliminatelist);
				sc.addUp(); // �ϼƱ��ε÷֣�����
				board.fall();
				eliminatelist = null;
				while ((eliminatelist = board.checkInstable()).size() > 0) {
					board.show();
					board.showF();
					board.elimilate(eliminatelist);
					sc.addUp(); // �ϼƱ��ε÷֣�����
					board.fall();
					eliminatelist = null;
				}
				Debug.output("swap success!\n");
			}
			else {Debug.output("swap failed!\n");}
			board.show();
			System.out.println("ִ�к�ʱ : " + (System.currentTimeMillis() - a) / 1000f
					+ " �� ");
		}
		}
		
	}

	public void run() {
		
		start();
	}
	
	public void start() {
		String str;
		while (true) {
			str = m.input();
			if (str.equals("quit")) {
				break;
			}
			if (str.startsWith("swap")) {
				String s[] = str.split(" ");
				switch (s.length) {
				case 4:
					move(Integer.parseInt(s[1]), Integer.parseInt(s[2]),
							Direction.valueOf(s[3].toUpperCase()));
					break;
				case 3:
					move(Integer.parseInt(s[1]), Integer.parseInt(s[2]), null);
				}
				//board.show();
			}
		}
	}

	public static void main(String args[]) {
		Debug.outputl("============================================================");
		Debug.outputl("��ʽ�� swap row col [up|down|left|right]");
		Debug.outputl("��ʾ��row�е�col���ϵĿ�����/��/��/�ҷ����ϵĿ���н�����ֱ�Ӵ���");
		Debug.outputl("eg: swap 1 1 up");

		GameController controller = new GameController();
		String mode = Debug.input("�������ģʽ�� y/n  ");
		if (mode.equals("y"))
			controller.m = controller.new Auto();
		else
			controller.m = controller.new Normal();
		controller.board.start();// ���������֣��ȶ������
		controller.board.show();		
		Thread t1 = new Thread(controller);
		//Thread t2 = new Thread(controller);
		t1.start();
		//t2.start();
	}

	private interface mode {
		public String input();
	}

	private class Normal implements mode {
		public String input() {
			return Debug.input();
		}
	}

	private class Auto implements mode {
		public String input() {
			try {
				Thread.sleep(50);
			} catch (InterruptedException ie) {
			}
			Tip tip = board.getTip();
			String str = "swap " + tip.p1.getY() + " " + tip.p1.getX() + " " + tip.d;
			Debug.outputl(str);
			return str;
		}
	}

}
