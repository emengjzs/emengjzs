package view.ui.gameui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import view.ui.gameui.drawTile.DrawTileFactory;
import view.ui.gameui.drawTile.TipDrawTile;
import view.ui.gameui.GridStatus.State;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel {
	public static final int dimension = BoardSettings.dimension;

	private final int blockEdge;
	private final int edge;

	private GameController gameController;
	private DrawTileFactory drawFactory;

	/* 下落方向 */
	public final Direction fallDirection;
	private Direction searchDirection;

	private Tiles tiles;
	private GridStatus status;
	// private Timer AnimeTimer = new Timer();
	private boolean started = false;
	private boolean canMove = true;
	private boolean inSuper = false;
	private int super_count = -1;

	private MouseClickListener clickListener;
	private MouseDragListener motionListener;
	MouseMoveListener moveListener;
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private boolean haveLock = false;
	private int lockType = -1;
	private JComponent backGround;
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public BoardPanel(BoardSettings settings) {

		/* 载入常量设定 */
		blockEdge = settings.getBlockEdge();
		edge = settings.getEdge();
		fallDirection = settings.getFallDirection();

		/* 得出下落搜索方向 */
		initSearchDirection();

		tiles = new Tiles(settings);
		status = new GridStatus();
		drawFactory = new DrawTileFactory(settings);

		// tiles.init();
		// this.setBackground(Color.black);
		// this.setOpaque(false);
		this.setSize(edge, edge);
		this.setLocation(70, 70);
		this.setLayout(null);

		backGround = new BackGround();
		this.add(backGround);
		// MoveImage moveImg = new MoveImage();
		// this.add(moveImg);

		// clickListener = new MouseClickListener();
		// motionListener = new MouseMotionListener();
		// this.addMouseListener(clickListener);
		// this.addMouseMotionListener(motionListener);

		// JLabel gameover = new JLabel("游戏结束");
		// gameover.setFont(new Font("汉仪蝶语体简", Font.BOLD, 40));
		// gameover.setForeground(Color.BLACK);
		// gameover.setSize(300,200);
		// gameover.setLocation(143,120);
		// backGround.add(gameover);

	}

	public int getBlockEdge() {
		return this.blockEdge;
	}

	public void addListener() {

		clickListener = new MouseClickListener();
		motionListener = new MouseDragListener();
		moveListener = new MouseMoveListener();
		this.addMouseMotionListener(moveListener);
		backGround.add(moveListener.moveImg);
		//this.add(moveListener.moveImg);
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(motionListener);

	}

	public void enableMove() {
		canMove = true;
	}

	public void disableMove() {
		canMove = false;
	}

	public void removeListener() {
		if (clickListener != null)
			this.removeMouseListener(clickListener);
		if (motionListener != null)
			this.removeMouseMotionListener(motionListener);
		if (moveListener != null)
			this.removeMouseMotionListener(moveListener);
	}

	public void setGameController(GameController gc) {
		gameController = gc;
	}

	public void initTiles(Position pList[], int types[]) {
		tiles.init(pList, types);
		started = true;
		new StartAnime();
	}

	private void initSearchDirection() {
		if (fallDirection == Direction.DOWN)
			searchDirection = Direction.LEFT;
		else if (fallDirection == Direction.RIGHT)
			searchDirection = Direction.UP;
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (started) {
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					Tile t = tiles.get(i, j);
					t.draw(g, this);
				}
			}
		}

	}

	/*
	 * for (int i = 0; i < dimension; i++) { for(int j = 0; j < dimension; j++)
	 * {
	 * 
	 * } }
	 */

	/**
	 * 交换
	 * 
	 * @param p
	 * @param d
	 */
	public void markSwap(Position p, Direction d) {
		Position p2 = p.toWard(d);
		if (isOut(p) || isOut(p2) || status.get(p2) != GridStatus.State.Normal
				|| status.get(p) != GridStatus.State.Normal) {
			Debug.outputl("multiple playing!!!!!!");
			
		}
		status.set(p2, GridStatus.State.Swap);
		status.set(p, GridStatus.State.Swap);

	}

	public void reswap(Position p1, Position p2, Handler hd) {
		status.set(p2, GridStatus.State.ReSwap);
		status.set(p1, GridStatus.State.ReSwap);
		new ReSwapAnime(p1, p2, p1.getDirection(p2), hd).run();
	}

	/**
	 * 标记消除
	 * 
	 * @param polist
	 */
	public void markEliminate(ArrayList<Position> polist) {
		Iterator<Position> itr = polist.iterator();
		Position p = null;
		while (itr.hasNext()) {
			p = itr.next();
			if (status.get(p) == State.Normal || status.get(p) == State.ReSwap)
				status.set(p, State.Elim);
		}
	}

	/**
	 * 标记删除
	 */
	public void markEmpty(ArrayList<Position> delList) {
		for (Position p : delList) {
			if (status.get(p) == State.Elim) {
				status.set(p, State.Empty);
				tiles.set(p, new EmptyTile());
			}
		}
	}

	/* 标记下落区域 */
	private void markFall() {
		Direction rev_d = Direction.reverse(fallDirection);
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (status.get(i, j) == State.Empty) {
					for (Position p = new Position(j, i); !isOut(p); p
							.to(rev_d)) {
						status.set(p, State.Fall);
					}
				}
			}
		}
	}

	public boolean isOut(Position p) {
		return (p.getX() >= dimension || p.getY() >= dimension || p.getX() < 0 || p
				.getY() < 0);
	}

	/***************************************************
	 * 背景
	 * 
	 * @author jzs
	 * 
	 **************************************************/
	public class BackGround extends JComponent {
		@SuppressWarnings("unused")
		private final String path = "image/gameback.png";
		private Image backgroundImg;
		private Image superImage;
		@SuppressWarnings("unused")
		private Image[] superImages = new Image[5];

		public BackGround() {
			readImg();
			this.setSize(edge, edge);
			
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(backgroundImg, 0, 0, edge, edge, this);
			if (inSuper) {
				g.drawImage(superImage, (19 - super_count) * edge / 20 / 2,
						(19 - super_count) * edge / 20 / 2, (super_count + 1)
								* edge / 20, (super_count + 1) * edge / 20,
						this);
				// g.drawImage(superImages[super_count], 0, 0, edge, edge,
				// this);
			}

		}

		public void update(Graphics g) {
			if (backgroundImg == null) {
				readImg();
			}
			Graphics gImg = backgroundImg.getGraphics();
			paint(gImg);
			g.drawImage(backgroundImg, 0, 0, edge, edge, this);
			// System.out.println(super_count);
			// if (inSuper){
			// g.drawImage(superImages[super_count], 0, 0, edge, edge, this);
			// System.out.println(super_count);
			// }
		}

		private void readImg() {
			try {
				backgroundImg = ImageIO.read(new File("Image/gameBoard.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				superImage = ImageIO.read(new File("Image/super.png"));
			} catch (IOException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}

			// for(int i=1;i<=5;i++){
			// try {
			// superImages[i-1] = ImageIO.read(new
			// File("image/super_"+i+".png"));
			// } catch (IOException e) {
			// // TODO 自动生成的 catch 块
			// e.printStackTrace();
			// }
			// }
		}
	}

	/*****************************************************
	 * Listener
	 */

	public class MouseClickListener extends MouseInputAdapter {
		/****************************
		 * Warning******************************* 不要把成员变量作为多线程方法中的参数！！！！！
		 *****************************************************************/
		private int n = -1;
		private Position lastPos = new Position(-1, -1);
		private Position curPos = new Position(0, 0);
		private final int max = dimension - 1;

		public void mousePressed(MouseEvent e) {
			tipAnime.stopTip();
			if (!canMove)
				return;
			Position temp = new Position(Math.min(e.getX() / blockEdge, max),
					Math.min(e.getY() / blockEdge, max));
			Debug.outputl(status.get(temp).name() + " "
					+ tiles.get(temp).getImgPos().toString() + " "
					+ temp.toString());
			if (status.get(temp) != GridStatus.State.Normal)
				return;
			n = (n + 1) % 2;
			lastPos.set(curPos);
			curPos.set(temp);
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if (tiles.get(lastPos).isLock || tiles.get(curPos).isLock) {
				return;
			}
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if (curPos.equals(lastPos)) {
				if (n == 1) {
					tiles.get(curPos).unfocus();
					if (tiles.get(curPos).isItem()) {
						gameController.triggerItem(curPos);
					}
				} else
					tiles.get(curPos).focus();
				repaint();
			} else {
				tiles.get(lastPos).unfocus();
				tiles.get(curPos).focus();
				if (curPos.isNext(lastPos) && n == 1) {
					Direction d = lastPos.getDirection(curPos);
					tiles.get(curPos).unfocus();
					Debug.outputl("send message");
					gameController.swapFromUser(new Position(lastPos), d); // 不能直接把lastPos传递！！！
				} else if (n == 1) {
					n = 0;
				}
				repaint();
			}
		}

		public Position getCurPosition() {
			return new Position(curPos);
		}

		public Position getLastPostion() {
			return new Position(lastPos);
		}

		public void set_n(int n) {
			this.n = n;
		}
	}

	public class MouseDragListener extends MouseMotionAdapter {
		/*
		 * 监听鼠标拖动移动棋子
		 */
		private final int max = dimension - 1;
		Position _lastPos = new Position(-2, -2);
		Position _curPos = new Position(-1, -1);

		public void mouseDragged(MouseEvent e) {
			if (!canMove)
				return;
			tipAnime.stopTip();
			Position curPos = new Position(Math.min(e.getX() / blockEdge, max),
					Math.min(e.getY() / blockEdge, max));
			Position center = new Position(curPos.getX() * blockEdge
					+ blockEdge / 2, curPos.getY() * blockEdge + blockEdge / 2);
			// 拖动没达到一定距离，在边长80%区域内
			if (Math.abs(center.getX() - e.getX()) > 0.35 * blockEdge
					|| Math.abs(center.getY() - e.getY()) > 0.35 * blockEdge)
				return;
			Position lastPos = new Position(clickListener.getCurPosition());
			Debug.outputl("Drag:" + " " + curPos.toString() + " "
					+ lastPos.toString());
			if (curPos.equals(_curPos) && lastPos.equals(_lastPos))
				return;
			synchronized (this) {
				_curPos = curPos;
				_lastPos = lastPos;
			}
			if (status.get(curPos) != State.Normal
					|| status.get(lastPos) != State.Normal)
				return;
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if (tiles.get(lastPos).isLock || tiles.get(curPos).isLock) {
				return;
			}
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			Direction d = lastPos.getDirection(curPos);
			if (d == null)
				return;
			tiles.get(lastPos).unfocus();
			Debug.outputl("Swap:" + " " + curPos.toString() + " "
					+ lastPos.toString());
			gameController.swapFromUser(lastPos, d);
			// clickListener.set_n(0);
		}
	}

	public synchronized void showF() {
		Debug.split();
		Debug.output("   ");
		for (int j = 0; j < dimension; j++) {
			Debug.output(j + " ");
		}
		Debug.endLine();
		Debug.output("  ");
		for (int j = 0; j < dimension; j++) {
			Debug.output("--");
		}
		Debug.endLine();
		for (int i = 0; i < dimension; i++) {
			Debug.output(i + "｜");
			for (int j = 0; j < dimension; j++) {
				Debug.output(tiles.get(i, j).type.ordinal() + " ");
			}
			Debug.output("\n");
		}
	}

	/*****************************************************
	 * anime
	 */

	/**
	 * 开始动画
	 * 
	 * @author jzs
	 * 
	 */
	private class StartAnime {
		Timer timer;
		int time_gap = 9;

		public StartAnime() {
			timer = new Timer();
			timer.scheduleAtFixedRate(new AnimeTask(), 0, time_gap);
			// timer.schedule(new AnimeTask(), 0, time_gap);
		}

		public class AnimeTask extends TimerTask {
			int f = 0;
			int r = blockEdge / 8;

			public void run() {
				if (f >= blockEdge) {
					timer.cancel();
				}
				if (r > 1)
					--r;
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						Tile t = tiles.get(i, j);
						t.setImgPos(new Position(j * f, f * i));
					}
				}
				f += r;
				repaint();
			}
		}
	}

	/**
	 * 消除动画批发控制
	 * 
	 * @author jzs
	 * 
	 */
	private class EliminateAnime {
		Timer timer;
		int time_gap = 8;
		Handler handler;
		ArrayList<Position> delList = new ArrayList<Position>();

		public EliminateAnime(Handler handler) {
			this.handler = handler;
			/* 找出需要消除块 */
			for (int i = 0; i < dimension; i++) {
				for (int j = 0; j < dimension; j++) {
					if (status.get(i, j) == GridStatus.State.Elim) {
						tiles.get(i, j).setDraw(
								drawFactory.getEliminateDrawTile());
						delList.add(new Position(j, i));
					}
				}
			}
			// markEmpty(delList);
			// time_gap = time_gap + delList.size() + 5;
			timer = new Timer();
			timer.scheduleAtFixedRate(new AnimeTask(), 0, time_gap);
		}

		private class AnimeTask extends TimerTask {
			int f = 0;

			public void run() {
				if (f >= 15) {
					markEmpty(delList);
					this.cancel();
					handler.changeState(CurState.EliminateEnd);
					return;
				}
				repaint();
				f++;
			}
		}
	}

	private void logicFall() {
		Position j = new Position();
		Direction rev = Direction.reverse(fallDirection);
		for (Position i = new Position(dimension - 1, dimension - 1); !isOut(i); i
				.to(searchDirection)) {
			j.set(i);
			while (!isOut(j)) {
				if (tiles.get(j).isNull()) {
					Tile nullTile = tiles.get(j);
					for (Position k = j.toWard(rev); !isOut(k); k.to(rev)) {
						Tile t = tiles.get(k);
						if ((!t.isNull())) {
							tiles.set(j, t);
							tiles.set(k, nullTile);
							j.to(rev);
						}
					}
					break;
				}
				j.to(rev);
			}
		}
	}

	// private boolean falling = false;

	/**
	 * 下落动画批发控制
	 * 
	 * @author jzs
	 */
	private class FallAnime {
		Timer timer;
		Handler handler;
		int time_gap = 3;

		public FallAnime(Handler handler) {
			this.handler = handler;
			timer = new Timer();
			// if (falling == false) {
			// falling = true;
			timer.scheduleAtFixedRate(new AnimeTask(), 0, time_gap);
			// }
		}

		private class AnimeTask extends TimerTask {
			boolean end = false;

			public void run() {
				if (end) {
					// falling = false;
					tiles.setNomalImgPos();
					repaint();
					timer.cancel();
					handler.changeState(CurState.END);
					return;
				}
				end = true;
				for (int i = 0; i < dimension; i++) {
					for (int j = 0; j < dimension; j++) {
						if (status.get(i, j) == State.Fall) {
							Tile t = tiles.get(i, j);
							if ((!tiles.OnNomalImgPos(t)) && (!t.isNull())) {
								end = false;
								Position p = t.getImgPos();
								p.add(Direction.getVetor(fallDirection, 2));
								t.setImgPos(p);
							}
						}
					}
				}
				repaint();
			}
		}
	}

	/**
	 * 交换动画
	 * 
	 * @author jzs
	 * 
	 */
	public class SwapAnime {
		public Direction d;
		public Timer timer;
		public Tile t1;
		public Tile t2;
		public Position p1;
		public Position p2;
		public Position imgp1;
		public Position imgp2;
		public Position temp1 = null;
		public Position temp2 = null;
		int time_gap = 2;
		private Handler handler;

		public SwapAnime(Position p1, Position p2, Direction d, Handler handler) {
			this.handler = handler;
			this.d = d;
			this.p1 = p1;
			this.p2 = p2;
			t1 = tiles.get(p1);
			t2 = tiles.get(p2);
			imgp1 = new Position(blockEdge * p1.getX(), blockEdge * p1.getY());
			imgp2 = new Position(blockEdge * p2.getX(), blockEdge * p2.getY());
			temp1 = new Position(imgp1);
			temp2 = new Position(imgp2);
		}

		public void run() {
			timer = new Timer();
			timer.scheduleAtFixedRate(new AnimeTask(), 0, time_gap);
		}

		private void swapFin() {
			tiles.set(p2, t1);
			tiles.set(p1, t2);
		}

		private class AnimeTask extends TimerTask {
			int f = 0;
			int r = blockEdge / 9;

			public void run() {
				if (f >= blockEdge) {
					this.cancel();
					swapFin();
					tiles.setNomalImgPos(t1);
					tiles.setNomalImgPos(t2);
					repaint();
					status.set(p1, State.Normal);
					status.set(p2, State.Normal);
					handler.changeState(CurState.SwapEnd);
					return;
				}
				if (r > 1)
					--r;
				temp1.add(Direction.getVetor(d, r));
				temp2.add(Direction.getVetor(Direction.reverse(d), r));
				t1.setImgPos(temp1);
				t2.setImgPos(temp2);
				f += r;
				repaint();
			}
		}
	}

	// public boolean itemFin = false;
	private class ItemAnime {
		Timer timer;
		int f = 0;
		int time_gap = 5;
		Handler hd;
		@SuppressWarnings("unused")
		ArrayList<Tile> item;

		public ItemAnime(ArrayList<Tile> item) {
			this.item = item;
			// itemFin = false;
		}

		public void run(Handler hd) {
			this.hd = hd;
			timer = new Timer();
			timer.scheduleAtFixedRate(new AnimeTask(), 0, time_gap);
		}

		private class AnimeTask extends TimerTask {
			// boolean r = false;
			public void run() {
				if (f > 40) {
					timer.cancel();
					hd.changeState(CurState.ItemEnd);
					return;
				}
				++f;
				repaint();
			}
		}
	}

	/**
	 * 复交换动画
	 * 
	 * @author jzs
	 * 
	 */
	public class ReSwapAnime {
		public Direction d;
		public Timer timer;
		public Tile t1;
		public Tile t2;
		public Position p1;
		public Position p2;
		public Position imgp1;
		public Position imgp2;
		public Position temp1 = null;
		public Position temp2 = null;
		int time_gap = 4;
		Handler hd;

		public ReSwapAnime(Position p1, Position p2, Direction d, Handler hd) {
			this.d = d;
			this.p1 = new Position(p1);
			this.p2 = new Position(p2);
			t1 = tiles.get(p1);
			t2 = tiles.get(p2);
			imgp1 = new Position(blockEdge * p1.getX(), blockEdge * p1.getY());
			imgp2 = new Position(blockEdge * p2.getX(), blockEdge * p2.getY());
			temp1 = new Position(imgp1);
			temp2 = new Position(imgp2);
			this.hd = hd;
		}

		public void run() {
			timer = new Timer();
			timer.scheduleAtFixedRate(new AnimeTask(), 0, time_gap);
		}

		private class AnimeTask extends TimerTask {
			int t = 2;
			int f = 0;
			int r = blockEdge / 9;

			public void run() {
				if (t == 0) {
					tiles.setNomalImgPos(t1);
					tiles.setNomalImgPos(t2);
					if (status.get(p1) == GridStatus.State.ReSwap)
						status.set(p1, GridStatus.State.Normal);
					if (status.get(p2) == GridStatus.State.ReSwap)
						status.set(p2, GridStatus.State.Normal);
					repaint();
					this.cancel();
					
					hd.changeState(CurState.ReswapEnd);
					return;
				} else {
					oneswap();
				}
			}

			public void oneswap() {
				if (f >= blockEdge) {
					f = 0;
					r = blockEdge / 9;
					t--;
					d = Direction.reverse(d);
					return;
				}
				if (r > 1)
					--r;
				temp1.add(Direction.getVetor(d, r));
				temp2.add(Direction.getVetor(Direction.reverse(d), r));
				t1.setImgPos(temp1);
				t2.setImgPos(temp2);
				f += r;
				repaint();
			}
		}
	}

	public Handler getHandler(Position p1, Position p2,
			ArrayList<Position> delList, ArrayList<Tile> newList,
			boolean needSendFin) {
		return new Handler(p1, p2, delList, newList, needSendFin);
	}

	public Handler getHandler(Position p1, Position p2) {
		return new Handler(p1, p2);
	}

	public class Handler {
		private CurState state;
		private Position p1;
		private Position p2;
		private ArrayList<Position> delList;
		private ArrayList<Tile> newList;
		private ArrayList<Tile> newItem = new ArrayList<Tile>(2);
		private boolean needSendFin;

		public Handler(Position p1, Position p2) {
			this.p1 = p1;
			this.p2 = p2;
			state = CurState.None;
		}

		public Handler(Position p1, Position p2, ArrayList<Position> delList,
				ArrayList<Tile> newList, boolean needSendFin) {
			this.p1 = p1;
			this.p2 = p2;
			this.delList = delList;
			this.newList = newList;
			state = CurState.None;
			this.needSendFin = needSendFin;
			tipAnime.stopTip();
			Iterator<Tile> itr = newList.iterator();
			while (itr.hasNext()) {
				Tile tile = itr.next();
				if (tile.isItem()) {
					tile.isNewItem = true;
					this.newItem.add(tile);
					itr.remove();
				}
			}
		}

		public void changeState(CurState s) {
			Debug.outputl("Change from " + state.name() + " to " + s.name());
			this.state = s;
			switch (state) {
			case SwapStart:
				markSwap(p1, p1.getDirection(p2));
				SwapAnime swap = new SwapAnime(p1, p2, p1.getDirection(p2),
						this);
				swap.run();
				return;
				// break;
			case SwapEnd:
				markEliminate(delList);
				state = CurState.EliminateStart;
			case EliminateStart:
				new EliminateAnime(this);
				return;
				// break;
			case EliminateEnd:
				for (Tile tile : newItem) {
					tiles.setNomalImgPos(tile);
					tile.setDraw(drawFactory.getItemDrawTile());
					// 如果此位置有不消除的方块则会覆盖原有的！！！
					tiles.set(tile.getPosition(), tile);
					status.set(tile.getPosition(), GridStatus.State.Empty);
				}
				markFall(); // 标记下落区域
				if (newItem.size() > 0) {
					new ItemAnime(newItem).run(this);
					return;
				}
			case ItemEnd:
				logicFall();
				for (Tile tile : newList) {
					if (haveLock) {// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
						if (tile.isSame(lockType) && haveLock) {
							tile.setDraw(drawFactory.getLockDrawTile(tile
									.getDraw()));
							tile.isLock = true;
						}
					}
					// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
					tiles.set(tile.getPosition(), tile);
				}
				state = CurState.Fall;
				new FallAnime(this);
				return;
				// break;
			case Fall:
				break;
			case END:
				for (Tile t : newItem) {
					t.isNewItem = false;
				}
				status.change(State.Fall, State.Normal);
				Debug.outputl("Fall end!");
				if (needSendFin) {
					gameController.animateFinish();
				}
				gameController.endHandler();
				break;
			case Reswap:
				reswap(p1, p2, this);
				break;
			case ReswapEnd:
				gameController.endHandler(true);
				break;
			default:
				break;
			}
		}
	}

	public void tip(Position p1, Position p2) {
		tipAnime.startTip(p1, p2);
	}

	TipAnime tipAnime = new TipAnime();

	private class TipAnime {
		Tile t1;
		Tile t2;
		Timer tipTimer;
		AnimeTask task;

		public TipAnime() {
			tipTimer = new Timer();
		}

		public void stopTip() {
			if (task != null) {
				task.cancel();
				if (!t1.isLock)
					t1.setDraw(drawFactory.getBasicDrawTile());
				if (!t2.isLock)
					t2.setDraw(drawFactory.getBasicDrawTile());
				repaint();
			}
		}

		public void startTip(Position p1, Position p2) {
			if (task != null)
				task.cancel();
			t1 = tiles.get(p1);
			t2 = tiles.get(p2);
			t1.setDraw(drawFactory.getTipDrawTile(t1.getDraw()));
			t2.setDraw(drawFactory.getTipDrawTile(t2.getDraw()));
			task = new AnimeTask();
			tipTimer.scheduleAtFixedRate(task, 0, TipDrawTile.time_gap);
		}

		private class AnimeTask extends TimerTask {
			public void run() {
				repaint();
			}
		}

	}

	// 锁棋子Demo
	public void lock(int type) {
		haveLock = true;
		lockType = type;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Tile tem = tiles.get(new Position(x, y));
				if (tem.isSame(type)) {
					tem.setDraw(drawFactory.getLockDrawTile(tem.getDraw()));
					tem.isLock = true;
				}
			}
		}
	}

	public void unlock() {
		haveLock = false;
		lockType = -1;
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				Tile tem = tiles.get(new Position(x, y));
				if (tem.isLock) {
					tem.setDraw(drawFactory.getBasicDrawTile());
					tem.isLock = false;
				}
			}
		}
	}

	// MoveImage moveImage = new MoveImage();
	/*
	 * public class MoveImage extends JComponent { Position p = new
	 * Position(-60, -60); Image img = ImageLibrary.getMoveImage(); public
	 * MoveImage() { super(); this.setSize(blockEdge, blockEdge);
	 * this.setVisible(true); }
	 * 
	 * public void paintComponent(Graphics g) { this.setLocation(p.getX() ,
	 * p.getY()); super.paintComponent(g); Debug.outputl("Fuck you!");
	 * g.drawImage(img, p.getX() , p.getY() ,blockEdge , blockEdge, this); }
	 * public void setP(Position p) { this.p.set(p.getX() * blockEdge, p.getY()
	 * * blockEdge); //this.repaint(); }
	 * 
	 * public void update(Graphics g) { Graphics gImg = img.getGraphics();
	 * this.paint(gImg); g.drawImage(img, p.getX(), p.getY(), blockEdge,
	 * blockEdge, this); } }
	 */
	public class MouseMoveListener implements MouseMotionListener {
		Position p = new Position(-1, -1);
		Image img = ImageLibrary.getMoveImage();
		JLabel moveImg;

		public MouseMoveListener() {
			ImageIcon icon = new ImageIcon(img);
			icon.setImage(icon.getImage().getScaledInstance(blockEdge,
					blockEdge, Image.SCALE_DEFAULT));
			moveImg = new JLabel(icon);
			moveImg.setSize(blockEdge, blockEdge);
			moveImg.setVisible(true);
			// this.moveImg = moveImg;
		}

		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getX() / blockEdge;
			int y = e.getY() / blockEdge;
			if (p.getX() == x && y == p.getY()) {
				return;
			}
			// repaint();
			p.set(x, y);
			// Graphics g = getGraphics();
			// g.drawImage(img, 0, 0, x * blockEdge, y * blockEdge, null);
			// paint(g);
			//Debug.outputl("Move " + p.toString());
			// moveImg.setP(p);
			moveImg.setLocation(x * blockEdge, y * blockEdge);
			moveImg.repaint();
		}
	}

	// 超级模式Demo
	public void setSuperModel() {
		super_m.start_super();
	}

	public void setNormalMode() {
		super_m.stop_super();
	}

	superModel super_m = new superModel();

	private class superModel {
		Timer superTimer;
		AnimeTask task;
		int time_gap = 50;

		public superModel() {
			superTimer = new Timer();
		}

		public void stop_super() {
			inSuper = false;
			if (task != null) {
				task.cancel();
			}
		}

		public void start_super() {
			inSuper = true;
			task = new AnimeTask();
			superTimer.scheduleAtFixedRate(task, 0, time_gap);
		}

		private class AnimeTask extends TimerTask {
			public void run() {
				super_count++;
				super_count = super_count % 20;
				repaint();
			}
		}
	}
}
