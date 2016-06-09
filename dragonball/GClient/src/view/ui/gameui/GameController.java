package view.ui.gameui;

import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import javax.swing.JPanel;

import common.Mode;
import model.netbl.NetGameBL;
import model.netservice.NetGameService;
import setup.GameClient;
import view.ui.gameui.BoardPanel.Handler;
import view.ui.gameui.positionconvertor.NormalPositionConvertor;
import view.ui.gameui.positionconvertor.PositionConvertor;
import view.ui.gameui.positionconvertor.SpecialPositionConvertor;
import view.ui.myComponent.Music;
import view.ui.myComponent.TipRunnable;

public class GameController implements GameServiceForNetwork,
		GameServiceForUser {
	private BlockingQueue<Handler> SwapQueue = new ArrayBlockingQueue<Handler>(
			1);
	private PositionConvertor positionConvertor;
	private BoardPanel boardPanel; // 棋盘面板
	private ScorePanel scorePanel; // 分数面板
	private ComboPanel comboPanel; // 连击面板
	private CountPanel countPanel; // 计数面板

	private HitPanel hitPanel;
	private int stage = -1;

	private NetGameService sendService; // 发送服务器
	private Mode mode; // 模式？？
	private String id; // ??
	private boolean isEnd = false;
	private ArrayList<String> idlist = new ArrayList<String>();
	private ArrayList<Integer> numlist = new ArrayList<Integer>();

	BoardSettings settings;

	/* 用户请求调用交换 */
	public void swapFromUser(Position p, Direction d) {
		Debug.outputl("swap from user");
		Position p2 = p.toWard(d);
		Position p1 = positionConvertor.sendPosition(p);
		p2 = positionConvertor.sendPosition(p2);
		sendService.swap(p1, p2);

	}

	public void triggerItem(Position p) {
		sendService.triggerItem(positionConvertor.sendPosition(p));
	}

	/* 通知动画结束 */
	public void animateFinish() {
		sendService.animateFinish();
	}
	
	public void endReady() {
		sendService.timeStart();
	}

	/* 初始化 */
	public void init(String message, int teamId, String mode) {
		/** 从服务器或数据库建立设定，请添上 */

		/** 根据设定建立棋盘 */
		boardPanel = new BoardPanel(settings);
		scorePanel = new ScorePanel();
		comboPanel = new ComboPanel();

		countPanel = new CountPanel();

		/* 设定方向 */
		Direction fallDirection = settings.getFallDirection();

		/* 载入坐标转换器 */
		if (fallDirection == Direction.DOWN)
			positionConvertor = new NormalPositionConvertor(
					settings.getBlockEdge());
		else if (fallDirection == Direction.RIGHT)
			positionConvertor = new SpecialPositionConvertor(
					settings.getBlockEdge());

		/* 载入棋盘纳入控制 */
		boardPanel.setGameController(this);
		scorePanel.setGameController(this);
		comboPanel.setGameController(this);

		// MainFrame gameFrame = new MainFrame(boardPanel);
		// gameFrame.add(scorePanel);
		// 修改处

		String[] str1 = message.split("-");
		int roomNO = Integer.parseInt(str1[1]);
		this.mode = Mode.valueOf(mode);
		this.id = str1[2];
		sendService = new NetGameBL(this.mode, roomNO, id, teamId);

		if (!mode.toString().equals("Battle")) {
			String[] str2 = str1[3].split("~");
			for (int i = 0; i < str2.length; i++) {
				String[] str3 = str2[i].split("=");
				idlist.add(str3[0]);
				numlist.add(Integer.parseInt(str3[1]));
			}
			if (mode.equals("Single")) {
				stage = Integer.parseInt(str1[4]);
				// stage = 9;
			}
		}

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if (mode.equals("Single")) {
			hitPanel = new HitPanel(stage);
		} else if (mode.equals("Coop")) {
			hitPanel = new HitPanel(-1);
		} else {
			hitPanel = null;
		}
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		String[] str = str1[0].split(",");
		int length = str.length;
		int[] types = new int[length]; // 类型
		Position pList[] = new Position[length]; // 位置

		int n = 0;
		for (int i = 0; i < BoardSettings.dimension; i++) {
			for (int j = 0; j < BoardSettings.dimension; j++) {
				pList[n] = positionConvertor.getPosition(i, j);
				types[n] = Integer.parseInt(str[n]);
				++n;
			}
		}
		// gameFrame.repaint();
		// gameFrame.setVisible(true);
		// 修改处

		boardPanel.initTiles(pList, types);
		pList = null;

		// boardPanel.setSuperModel();
	}

	public void endHandler() {
		SwapQueue.poll();
		boardPanel.enableMove();
		boardPanel.showF();
	}
	
	public void endHandler(boolean t) {
		if(isEnd) {
			sendService.triggerAllItem();
		}
		SwapQueue.poll();
		boardPanel.enableMove();
		//boardPanel.showF();
	}
	
	/* 交换成功处理 */
	@SuppressWarnings("unused")
	public void swapSuccess(String message) {
		boardPanel.disableMove();
		String[] str = message.split("-");
		ArrayList<Position> swap = PosToList(str[0]); // 交换位置
		ArrayList<Position> delList = PosToList(str[1]); // 消除列表
		ArrayList<Tile> newList = TypeToList(str[2]); // 生成列表
		int score = Integer.parseInt(str[3]);
		String a = str[4];
		boolean isNeedFin = str[5].equals(id);
		Handler hd = boardPanel.getHandler(swap.get(0), swap.get(1), delList,
				newList, isNeedFin);

		comboPanel.changeCount(Integer.parseInt(str[6]));
		countPanel.setTol(Integer.parseInt(str[7]));
		countPanel.setMax(Integer.parseInt(str[8]));
		try {
			SwapQueue.put(hd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hd.changeState(CurState.SwapStart);
		scorePanel.addScore(score);

		for (int i = 0; i < idlist.size(); i++) {
			if (idlist.get(i).equals(str[5])) {
				hitPanel.Hit(numlist.get(i) * score / 10);
			}
		}
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(GameClient.MusicOn){
			Music music = new Music("music/hit.mp3",false);
			music.play();
		}
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		
	}

	/* 交换失败处理 */
	public void swapFail(String message) {
		// SwapAnime anime = SwapQueue.poll();
		// if(anime == null) return;
		String[] str = message.split("-");
		ArrayList<Position> swap = PosToList(str[0]);
		Handler hd = boardPanel.getHandler(swap.get(0), swap.get(1));
		try {
			SwapQueue.put(hd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hd.changeState(CurState.Reswap);
		//if(isEnd){
		//	sendService.triggerAllItem();
		//}
	}

	@SuppressWarnings("unused")
	public void ItemSuccess(String message) {
		boardPanel.disableMove();
		//boardPanel.
		String[] str = message.split("-");
		ArrayList<Position> delList = PosToList(str[0]);
		ArrayList<Tile> newList = TypeToList(str[1]);
		int score = Integer.parseInt(str[2]);
		String a = str[3];
		boolean isNeedFin = str[4].equals(id);
		comboPanel.changeCount(Integer.parseInt(str[5]));
		countPanel.setTol(Integer.parseInt(str[6]));
		countPanel.setMax(Integer.parseInt(str[7]));
		Handler hd = boardPanel.getHandler(null, null, delList, newList,
				isNeedFin);
		try {
			SwapQueue.put(hd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hd.changeState(CurState.SwapEnd);
		scorePanel.addScore(score);

		for (int i = 0; i < idlist.size(); i++) {
			if (idlist.get(i).equals(str[4])) {
				hitPanel.Hit(numlist.get(i) * score / 10);
			}
		}
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(GameClient.MusicOn){
			Music music = new Music("music/bomb.mp3",false);
			music.play();
		}
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	}

	@SuppressWarnings("unused")
	public void SecondEliminate(String message) {
		boardPanel.disableMove();
		String[] str = message.split("-");
		ArrayList<Position> delList = PosToList(str[0]);
		ArrayList<Tile> newList = TypeToList(str[1]);
		int score = Integer.parseInt(str[2]);
		String a = str[3];
		boolean isNeedFin = str[4].equals(id);
		Handler hd = boardPanel.getHandler(null, null, delList, newList,
				isNeedFin);
		try {
			SwapQueue.put(hd);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hd.changeState(CurState.SwapEnd);
		scorePanel.addScore(score);

		for (int i = 0; i < idlist.size(); i++) {
			if (idlist.get(i).equals(str[4])) {
				hitPanel.Hit(numlist.get(i) * score / 10);
			}
		}
		
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		if(GameClient.MusicOn){
			Music music = new Music("music/hit.mp3",false);
			music.play();
		}
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	}

	public void hint(String message) {
		ArrayList<Position> tip = PosToList(message);
		boardPanel.tip(tip.get(0), tip.get(1));
	}

	Music superMusic;	
	public void StartSuper() {
		scorePanel.setSuperMode();
		boardPanel.setSuperModel();
		if(GameClient.MusicOn){
			superMusic = new Music("music/super_redefine.mp3",false);
			superMusic.play();
		}
	}

	public void EndSuper() {
		scorePanel.setNormalMode();
		boardPanel.setNormalMode();
//		if(superMusic != null) superMusic.stop();
	}

	private ArrayList<Position> PosToList(String str) {
		ArrayList<Position> coor_new = new ArrayList<Position>();
		String[] tileStr = str.split(":");
		for (int i = 0; i < tileStr.length; i++) {
			String[] info = tileStr[i].split(",");
			Position Position = positionConvertor.getPosition(
					Integer.parseInt(info[0]), Integer.parseInt(info[1]));
			coor_new.add(Position);
		}
		return coor_new;
	}

	private ArrayList<Tile> TypeToList(String str) {
		// 位置栈
		int beginPosition[] = new int[BoardPanel.dimension];
		ArrayList<Tile> til_new = new ArrayList<Tile>();
		String[] tileStr = str.split(":");
		for (int i = 0; i < tileStr.length; i++) {
			String[] info = tileStr[i].split(",");
			Tile tile = new Tile(Integer.parseInt(info[2])); // 类型
			Position pos = positionConvertor.getPosition(
					Integer.parseInt(info[0]), Integer.parseInt(info[1]));
			int index = positionConvertor.getOffSetIndex(pos);
			--beginPosition[index];
			tile.setPosition(pos); // 位置
			tile.setImgPos(positionConvertor.getInitImgPos(pos,
					beginPosition[index]));
			til_new.add(tile);
		}
		return til_new;
	}

	// 添加处
	public void addSetting(BoardSettings settings) {
		this.settings = settings;
	}

	public void setLocation(int x, int y) {
		Point p1 = boardPanel.getLocation();
		boardPanel.setLocation(p1.x + x, p1.y + y);

		Point p2 = scorePanel.getLocation();
		scorePanel.setLocation(p2.x + x, p2.y + y);

		Point p3 = comboPanel.getLocation();
		comboPanel.setLocation(p3.x + x, p3.y + y);

		Point p4 = countPanel.getLocation();
		countPanel.setLocation(p4.x + x, p4.y + y);
	}

	public BoardPanel getBoard() {
		return boardPanel;
	}

	public ScorePanel getScore() {
		return scorePanel;
	}

	public ComboPanel getCombo() {
		return comboPanel;
	}

	public JPanel getCount() {
		return countPanel;
	}

	public JPanel getHit() {
		return hitPanel;
	}

	public Mode getMode() {
		return mode;
	}

	/** 游戏结束处理 */
	public void EndGame(String message) {
		isEnd = true;
		/* 有动画 */
		if (SwapQueue.isEmpty() && message.equals(id)) {
			sendService.triggerAllItem();
		}
		boardPanel.removeListener();
		TipRunnable end = new TipRunnable("image/tip/gameover.png", 500, 350,
				300, 50, 3);
		Thread myThread = new Thread(end);
		myThread.start();
		
		if (mode.toString().equals("Coop")) {
			sendService.sendStage(hitPanel.getCur());
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	/* 锁颜色处理 */
	public void LockBlock(int type) {
		boardPanel.lock(type);
	}

	public void unLock() {
		boardPanel.unlock();
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public void endCombo() {
		comboPanel.changeCount(0);
		countPanel.setTol(0);
	}

}
