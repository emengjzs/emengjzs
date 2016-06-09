package game.model;

import java.util.ArrayList;

import game.model.block.Block_Stub;

public class GameController_Stub {
	public static  Direction_Stub d;
	private Board_Stub board;
	ScoreController_Stub sc;
	int n = 0;
	//private ArrayList<Block> eliminatelist; // 消除队列

	public GameController_Stub() {
		board = new Board_Stub(9, 9);
		sc = new ScoreController_Stub();
		board.setScoreController(sc);
	}

	public void move(int row, int col, Direction_Stub d) {
		ArrayList<Block_Stub> eliminatelist = new ArrayList<Block_Stub>();
		long a = System.currentTimeMillis();
		Position_Stub p = new Position_Stub(col, row);	
		if (board.isIn(p)) {
			if(d == null) {
				eliminatelist = board.checkMatchAfterSwap(row, col);
			}
			else {
				Position_Stub pp = p.toWard(d);
				if(board.isIn(pp)) {
					board.swap(p, pp);
					eliminatelist = board.checkMatchAfterSwap(row, col, d);
					if(eliminatelist.size() == 0)
						board.swap(p, pp);
				}		
			}
			if (eliminatelist.size() > 0) {
				board.showF();
				board.elimilate(eliminatelist);
				sc.addUp();		//合计本次得分！！！
				board.fall();
				eliminatelist = null;
				while ((eliminatelist = board.checkInstable()).size() > 0) {
					board.show();
					board.showF();
					board.elimilate(eliminatelist);
					sc.addUp();		//合计本次得分！！！
					board.fall();
					eliminatelist = null;
				}				
				Debug.output("swap success!\n");
			} 
		}
		System.out.println("执行耗时 : " + (System.currentTimeMillis() - a) /
				1000f + " 秒 ");
	}

	public void start() {
		String mode = Debug.input("开启外挂模式？ y/n  ");
		board.start();// 产生不死局，稳定的棋局
		board.show();
		String str;
		while (true) {	
			if(mode.equals("y")) 
				str = new Auto().input() ;
			else
				str = new Normal().input();
			if (str.equals("quit")) {
				break;
			}
			if (str.startsWith("swap")) {
				String s[] = str.split(" ");
				switch(s.length) {
				case 4: move(Integer.parseInt(s[1]), Integer.parseInt(s[2]),
						Direction_Stub.valueOf(s[3].toUpperCase()));
						break;
				case 3: move(Integer.parseInt(s[1]), Integer.parseInt(s[2]), null);
				}			
				board.show();
			}
		}
	}

	public static void main(String args[]) {
		Debug.outputl("============================================================");
		Debug.outputl("格式： swap row col [up|down|left|right]");
		Debug.outputl("表示第row行第col列上的块与上/下/左/右方向上的块进行交换或直接触发");
		Debug.outputl("eg: swap 1 1 up");
		
		GameController_Stub controller = new GameController_Stub();
		controller.start();
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
			try { Thread.sleep (1000) ;
			} catch (InterruptedException ie){}
			Position_Stub p = board.getTip();
			String str = "swap " + p.getY() + " "  + p.getX() + " " + d;
			Debug.outputl(str);
			return str;
		}
	}
	
}
