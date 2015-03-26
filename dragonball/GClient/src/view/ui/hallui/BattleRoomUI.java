package view.ui.hallui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import common.ResultMessage;
import common.Service;
import common.UserState;
import model.netbl.NetBattleRoomBL;
import model.netservice.NetBattleRoomBLService;
import model.vo.ActiveUser;
import model.vo.UserVO;
import setup.GameClient;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.Music;
import view.ui.myComponent.MySplashScreen;
import view.ui.myComponent.TipRunnable;
import view.uiservice.BattleRoomService;

public class BattleRoomUI implements BattleRoomService, ActionListener,
		MouseListener, KeyListener {
	JFrame mainFrame;
	boolean isMaster = false;
	private int width = 1100;
	private int height = 700;
	JPanel backgroundPanel;
	// 不确定是否正确
	IconButton kick[] = new IconButton[4];

	// 鼠标移到3个button上显示的提示信息
	MySplashScreen chooseToolTip;

	// 道具原始状态
	ImageIcon toolImage_notChoose1 = new ImageIcon("image/solo/tool1_1.png");
	ImageIcon toolImage_notChoose2 = new ImageIcon("image/solo/tool1_2.png");
	ImageIcon toolImage_notChoose3 = new ImageIcon("image/solo/tool1_3.png");

	// 道具被选中后的状态
	ImageIcon toolImage_choosed1 = new ImageIcon("image/solo/tool2_1.png");
	ImageIcon toolImage_choosed2 = new ImageIcon("image/solo/tool2_2.png");
	ImageIcon toolImage_choosed3 = new ImageIcon("image/solo/tool2_3.png");

	// 玩家状态
	ImageIcon ready = new ImageIcon("image/cooperation/ready.png");
	ImageIcon roomMaster = new ImageIcon("image/cooperation/roomMaster.png");
	ImageIcon leisure = new ImageIcon("image/cooperation/leisure.png");

	// 道具按钮
	JButton toolButton1;
	JButton toolButton2;
	JButton toolButton3;

	IconButton prepareButton;
	IconButton beginButton;
	IconButton backButton;

	JLabel roleLabel[] = new JLabel[4];
	JLabel oponentLabel[] = new JLabel[4];
	JLabel roleNameLabel[] = new JLabel[4];
	JLabel oponentNameLabel[] = new JLabel[4];
	JLabel roleStateLabel[] = new JLabel[4];
	JLabel oponentStateLabel[] = new JLabel[4];

	JScrollPane sendScroll;
	JTextArea writeArea;
	JTextArea sendArea;
	IconButton sendButton;

	String path1;
	String path2;
	String path3;

	String preparePath1 = "image/cooperation/prepareGame1.png";
	String preparePath2 = "image/cooperation/prepareGame2.png";
	String preparePath3 = "image/cooperation/prepareGame3.png";

	String beginPath1 = "image/cooperation/begin1.png";
	String beginPath2 = "image/cooperation/begin2.png";
	String beginPath3 = "image/cooperation/begin3.png";

	IconButton button;

	IconButton roleKickButton[] = new IconButton[4];
	IconButton oponentKickButton[] = new IconButton[4];

	UserVO user;
	int roomNO;
	static ArrayList<ActiveUser> playerList1 = new ArrayList<ActiveUser>();
	static ArrayList<ActiveUser> playerList2 = new ArrayList<ActiveUser>();
	static NetBattleRoomBLService battleRoomController;

	int xOld = 0;
	int yOld = 0;

	public BattleRoomUI(UserVO user, int roomNO) {

		// try {
		// UIManager.setLookAndFeel(new SubstanceLookAndFeel());
		// } catch (UnsupportedLookAndFeelException e) {
		// e.printStackTrace();
		// }
		// JFrame.setDefaultLookAndFeelDecorated(true);
		// //SubstanceLookAndFeel.setCurrentWatermark(new
		// SubstanceBubblesWatermark());

		this.roomNO = roomNO;
		this.user = user;
		battleRoomController = NetBattleRoomBL.getBattleRoomController();
		System.out.println("In the XXOO ROOM, have ♂ fun");
		mainFrame = new JFrame();
		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);// 去除窗口边框

		initFrame();

		mainFrame.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xOld = e.getX();
				yOld = e.getY();
			}
		});
		mainFrame.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int xOnScreen = e.getXOnScreen();
				int yOnScreen = e.getYOnScreen();
				int xx = xOnScreen - xOld;
				int yy = yOnScreen - yOld;

				mainFrame.setLocation(xx, yy);
			}
		});

		mainFrame.setBackground(new Color(0, 0, 0, 0));
		mainFrame.setVisible(true);

		if (GameClient.MusicOn) {
			Music music = new Music("music/gate_open.mp3", false);
			music.play();
		}

		TipRunnable money = new TipRunnable("image/tip/moneyPlus.png", 500,
				350, 300, 50, 3);
		Thread myThread = new Thread(money);
		myThread.start();
	}

	@SuppressWarnings("serial")
	private void initFrame() {
		Container contentPane = mainFrame.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"image/cooperation/background_room.png");
		backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
						null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);

		JLabel vs = new JLabel(new ImageIcon("image/fight/vs.png"));
		vs.setSize(149, 112);
		vs.setLocation(70, 370);
		backgroundPanel.add(vs);

		backButton = new IconButton("image/cooperation/backtohall1.png",
				"image/cooperation/backtohall2.png",
				"image/cooperation/backtohall3.png");
		backButton.addActionListener(this);
		backButton.setLocation(25, 20);
		backButton.setSize(175, 64);
		backgroundPanel.add(backButton);

		// 对话面板
		JPanel messagePanel = new JPanel();
		messagePanel.setOpaque(false);
		messagePanel.setLocation(10, 491);
		messagePanel.setLayout(null);
		messagePanel.setSize(850, 199);
		backgroundPanel.add(messagePanel);

		// 聊天——写面板
		writeArea = new JTextArea();
		writeArea.setLineWrap(true);
		writeArea.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		writeArea.setOpaque(false);
		writeArea.addKeyListener(this);

		JScrollPane writeScroll = new JScrollPane(writeArea);
		writeScroll.setSize(685, 32);
		writeScroll.setLocation(50, 138);
		writeScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(writeScroll);

		// 聊天——显示面板
		sendArea = new JTextArea();
		sendArea.setLineWrap(true);
		sendArea.setEditable(false);
		sendArea.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		sendArea.setOpaque(false);
		sendScroll = new JScrollPane(sendArea);
		sendScroll.setOpaque(false);
		sendScroll.setSize(790, 90);
		sendScroll.setLocation(50, 40);
		sendScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(sendScroll);

		sendButton = new IconButton("image/cooperation/send1.png",
				"image/cooperation/send2.png", "image/cooperation/send3.png");
		sendButton.setLocation(735, 138);
		sendButton.setSize(100, 32);
		sendButton.addActionListener(this);
		sendButton.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO 自动生成的方法存根
				if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
					String message = writeArea.getText();
					message = message.replace("\n", "|");
					if (message.length() == 0) {
						// 发送内容为空，do nothing
					} else {
						writeArea.setText("");
						chatInRoom(message);

						int height = 10;
						Point p = new Point();
						p.setLocation(0, sendArea.getLineCount() * height);
						sendScroll.getViewport().setViewPosition(p);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO 自动生成的方法存根

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO 自动生成的方法存根

			}

		});
		messagePanel.add(sendButton);

		toolButton1 = new JButton(toolImage_notChoose1);
		toolButton1.addActionListener(this);
		toolButton1.addMouseListener(this);
		toolButton1.setLocation(900, 200);
		toolButton1.setSize(90, 90);
		backgroundPanel.add(toolButton1);

		toolButton2 = new JButton(toolImage_notChoose2);
		toolButton2.addActionListener(this);
		toolButton2.addMouseListener(this);
		toolButton2.setLocation(900, 300);
		toolButton2.setSize(90, 90);
		backgroundPanel.add(toolButton2);

		toolButton3 = new JButton(toolImage_notChoose3);
		toolButton3.addActionListener(this);
		toolButton3.addMouseListener(this);
		toolButton3.setLocation(900, 400);
		toolButton3.setSize(90, 90);
		backgroundPanel.add(toolButton3);

		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		for (int i = 0; i < 4; i++) {
			roleKickButton[i] = new IconButton("image/cooperation/kick1.png",
					"image/cooperation/kick2.png",
					"image/cooperation/kick3.png");
			roleKickButton[i].setSize(40, 40);
			roleKickButton[i].setLocation(140 + i * 140, 180);
			roleKickButton[i].addActionListener(this);
			roleKickButton[i].setVisible(false);
			backgroundPanel.add(roleKickButton[i]);
		}
		for (int i = 0; i < 4; i++) {
			oponentKickButton[i] = new IconButton(
					"image/cooperation/kick1.png",
					"image/cooperation/kick2.png",
					"image/cooperation/kick3.png");
			oponentKickButton[i].setSize(40, 40);
			oponentKickButton[i].setLocation(340 + i * 140, 350);
			oponentKickButton[i].addActionListener(this);
			oponentKickButton[i].setVisible(false);
			backgroundPanel.add(oponentKickButton[i]);
		}
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// 队友玩家状态
		for (int i = 0; i < 4; i++) {
			// roleStateLabel[i] = new JLabel(new ImageIcon(
			// "image/fight/state.png"));
			roleStateLabel[i] = new JLabel();
			roleStateLabel[i].setSize(96, 24);
			roleStateLabel[i].setLocation(60 + i * 140, 270);
			backgroundPanel.add(roleStateLabel[i]);
		}
		// 对手玩家状态
		for (int i = 0; i < 4; i++) {
			// oponentStateLabel[i] = new JLabel(new ImageIcon(
			// "image/fight/state.png"));
			oponentStateLabel[i] = new JLabel();
			oponentStateLabel[i].setSize(96, 24);
			oponentStateLabel[i].setLocation(260 + i * 140, 440);
			backgroundPanel.add(oponentStateLabel[i]);
		}

		// 队友图案
		for (int i = 0; i < 4; i++) {
			// roleLabel[i] = new JLabel();
			ImageIcon roleImg = new ImageIcon("image/cooperation/role.png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(96, 128,
					Image.SCALE_DEFAULT));
			roleLabel[i] = new JLabel(roleImg);
			roleLabel[i].setSize(96, 128);
			roleLabel[i].setLocation(60 + i * 140, 190);
			backgroundPanel.add(roleLabel[i]);
		}
		// 对手图案
		for (int i = 0; i < 4; i++) {
			// oponentLabel[i] = new JLabel();
			ImageIcon roleImg = new ImageIcon("image/cooperation/role.png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(96, 128,
					Image.SCALE_DEFAULT));
			oponentLabel[i] = new JLabel(roleImg);
			oponentLabel[i].setSize(96, 128);
			oponentLabel[i].setLocation(260 + i * 140, 360);
			backgroundPanel.add(oponentLabel[i]);
		}

		// 队友玩家昵称
		for (int i = 0; i < 4; i++) {
			roleNameLabel[i] = new JLabel();
			roleNameLabel[i].setSize(96, 20);
			roleNameLabel[i].setLocation(60 + i * 140, 320);
			roleNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			roleNameLabel[i].setFont(new Font("汉仪蝶语体简", Font.BOLD, 12));
			roleNameLabel[i].setForeground(new Color(210, 105, 30));
			backgroundPanel.add(roleNameLabel[i]);
		}

		// 对手玩家昵称
		for (int i = 0; i < 4; i++) {
			oponentNameLabel[i] = new JLabel();
			oponentNameLabel[i].setSize(96, 20);
			oponentNameLabel[i].setLocation(260 + i * 140, 490);
			oponentNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			oponentNameLabel[i].setFont(new Font("汉仪蝶语体简", Font.BOLD, 12));
			oponentNameLabel[i].setForeground(new Color(210, 105, 30));
			backgroundPanel.add(oponentNameLabel[i]);
		}

		button = new IconButton("image/cooperation/changeTeam1.png",
				"image/cooperation/changeTeam2.png",
				"image/cooperation/changeTeam3.png");
		button.setSize(155, 87);
		button.setLocation(700, 200);
		button.addActionListener(this);
		backgroundPanel.add(button);

	}

	// 在界面上显示选中的道具
	public boolean selectTool(int toolNo) {
		if (toolNo == 0) {// 道具1
			if (toolButton1.getIcon() == toolImage_choosed1) {// 图片已被选中,显示闪屏提示错误
				showError();
				return false;
			} else {
				toolButton1.setIcon(toolImage_choosed1);
				return true;
			}
		} else if (toolNo == 1) {// 道具2
			if (toolButton2.getIcon() == toolImage_choosed2) {
				showError();
				return false;
			} else {
				toolButton2.setIcon(toolImage_choosed2);
				return true;
			}
		} else {// 道具3
			if (toolButton3.getIcon() == toolImage_choosed3) {
				showError();
				return false;
			} else {
				toolButton3.setIcon(toolImage_choosed3);
				return true;
			}
		}
	}

	public void setTool(boolean b1, boolean b2, boolean b3) {
		if (b1)
			toolButton1.setIcon(toolImage_choosed1);
		else
			toolButton1.setIcon(toolImage_notChoose1);
		
		if (b2)
			toolButton2.setIcon(toolImage_choosed2);
		else
			toolButton2.setIcon(toolImage_notChoose2);
		
		if (b3)
			toolButton3.setIcon(toolImage_choosed3);
		else
			toolButton3.setIcon(toolImage_notChoose3);
	}

	@SuppressWarnings("static-access")
	private void showError() {// 重复选择道具时，显示闪屏提示错误
		MySplashScreen window = new MySplashScreen(
				"image/cooperation/error.png");
		window.setLocation(500, 350);
		window.setSize(300, 50);
		window.setVisible(true);
		try {
			new Thread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		window.dispose();
	}

	// 秉良看这里
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void moneyNotEnough() {
		TipRunnable fail = new TipRunnable("image/cooperation/moneyNotEnough.png", 450,
				450, 300, 50, 2);
		Thread myThread = new Thread(fail);
		myThread.start();
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	@SuppressWarnings("static-access")
	public void changeSuccess() {
		MySplashScreen window = new MySplashScreen(
				"image/cooperation/changeSuccess.png");
		window.setLocation(500, 350);
		window.setSize(300, 50);
		window.setVisible(true);
		try {
			new Thread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		window.dispose();
	}

	@SuppressWarnings("static-access")
	public void changeError() {
		MySplashScreen window = new MySplashScreen(
				"image/cooperation/changeError.png");
		window.setLocation(500, 350);
		window.setSize(300, 50);
		window.setVisible(true);
		try {
			new Thread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		window.dispose();
	}

	public synchronized void playerIn(int flag, String userID, int money, int exp,
			UserState userstate) {
		// flag--0:队友;1:对手

		if (flag == 0) {// 队友
			int blankSeat = -1;
			ImageIcon image = null;
			for (int i = 0; i < 4; i++) {
				if (roleNameLabel[i].getText().length() == 0) {
					blankSeat = i;
					break;
				}
			}// 至此可以检测到第（blankSeat+1）号位置为空

			roleNameLabel[blankSeat].setText(userID);// 显示ID
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				image = roomMaster;
			}
			image.setImage(image.getImage().getScaledInstance(96, 24,
					Image.SCALE_DEFAULT));
			roleStateLabel[blankSeat].setIcon(image);

			// 显示人物图案
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			ImageIcon roleImg = new ImageIcon("Image/login/player"
					+ getLevel(exp) + ".png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(96, 128,
					Image.SCALE_DEFAULT));
			roleLabel[blankSeat].setIcon(roleImg);
			// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			if (isMaster)
				roleKickButton[blankSeat].setVisible(true);

		} else {// flag==1,对手
			int blankSeat = -1;
			ImageIcon image = null;
			for (int i = 0; i < 4; i++) {
				if (oponentNameLabel[i].getText().length() == 0) {
					blankSeat = i;
					break;
				}
			}// 至此可以检测到第（blankSeat+1）号位置为空

			oponentNameLabel[blankSeat].setText(userID);// 显示ID
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				image = roomMaster;
			}
			image.setImage(image.getImage().getScaledInstance(96, 24,
					Image.SCALE_DEFAULT));
			oponentStateLabel[blankSeat].setIcon(image);

			// 显示人物图案
			ImageIcon roleImg = new ImageIcon("Image/login/player"
					+ getLevel(exp) + ".png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(96, 128,
					Image.SCALE_DEFAULT));
			oponentLabel[blankSeat].setIcon(roleImg);

			if (isMaster)
				oponentKickButton[blankSeat].setVisible(true);

		}

	}

	public synchronized void playerOut(int flag, String userID) {
		if (flag == 0) {
			int outSeat = -1;// 储存要退出的玩家所在的位置
			int lastSeat = 3;// 储存最后一个玩家所在的位置
			for (int i = 0; i < 4; i++) {
				if (roleNameLabel[i].getText().equals(userID)) {
					outSeat = i;
				}
				if (roleNameLabel[i].getText().length() == 0) {
					lastSeat = i - 1;
					break;
				}
			}

			ImageIcon roleImg = new ImageIcon("image/cooperation/role.png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(96, 128,
					Image.SCALE_DEFAULT));
			if (outSeat == lastSeat) {
				// 如果要退出的是最后一个人，则直接把最后一个位置上的内容抹去
				System.out.println(lastSeat);
				roleNameLabel[lastSeat].setText("");
				roleStateLabel[lastSeat].setIcon(null);
				roleLabel[lastSeat].setIcon(roleImg);
				roleKickButton[lastSeat].setVisible(false);
			} else {// 如果不是最后一个人，则将最后一个人位置上所有内容移到移出的位置上,再抹去最后一个位置上的内容
				roleNameLabel[outSeat].setText(roleNameLabel[lastSeat]
						.getText());
				roleStateLabel[outSeat].setIcon(roleStateLabel[lastSeat]
						.getIcon());
				roleLabel[outSeat].setIcon(roleLabel[lastSeat].getIcon());
				if (isMaster
						&& !roleNameLabel[outSeat].getText().equals(
								user.getID()))
					roleKickButton[outSeat].setVisible(true);

				roleNameLabel[lastSeat].setText("");
				roleStateLabel[lastSeat].setIcon(null);
				roleLabel[lastSeat].setIcon(roleImg);
				roleKickButton[lastSeat].setVisible(false);
			}
		} else {// 对手
			int outSeat = -1;// 储存要退出的玩家所在的位置
			int lastSeat = 3;// 储存最后一个玩家所在的位置
			for (int i = 0; i < 4; i++) {
				if (oponentNameLabel[i].getText().equals(userID)) {
					outSeat = i;
				}
				if (oponentNameLabel[i].getText().length() == 0) {
					lastSeat = i - 1;
					break;
				}
			}

			ImageIcon roleImg = new ImageIcon("image/cooperation/role.png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(96, 128,
					Image.SCALE_DEFAULT));
			if (outSeat == lastSeat) {
				// 如果要退出的是最后一个人，则直接把最后一个位置上的内容抹去
				System.out.println(lastSeat);
				oponentNameLabel[lastSeat].setText("");
				oponentStateLabel[lastSeat].setIcon(null);
				oponentLabel[lastSeat].setIcon(roleImg);
				oponentKickButton[lastSeat].setVisible(false);
			} else {// 如果不是最后一个人，则将最后一个人位置上所有内容移到移出的位置上,再抹去最后一个位置上的内容
				oponentNameLabel[outSeat].setText(oponentNameLabel[lastSeat]
						.getText());
				oponentStateLabel[outSeat].setIcon(oponentStateLabel[lastSeat]
						.getIcon());
				oponentLabel[outSeat].setIcon(oponentLabel[lastSeat].getIcon());
				if (isMaster
						&& !oponentNameLabel[outSeat].getText().equals(
								user.getID()))
					oponentKickButton[outSeat].setVisible(true);

				oponentNameLabel[lastSeat].setText("");
				oponentStateLabel[lastSeat].setIcon(null);
				oponentLabel[lastSeat].setIcon(roleImg);
				oponentKickButton[lastSeat].setVisible(false);
			}
		}

	}

	public void playerStateChange(int flag, String userID, UserState userstate) {
		if (flag == 0) {
			int seat = -1;// 记录要更换状态的位置
			ImageIcon image = null;
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				System.out.println("I'm roommaster");
				image = roomMaster;
				if (userID.equals(user.getID())) {
					roomMaster(flag);
				}
				// 当变为房主后，“准备完毕”按钮会变成“开始游戏按钮”，且多出一个“踢人”的按钮
			}
			for (int i = 0; i < 4; i++) {
				if (roleNameLabel[i].getText().equals(userID)) {
					seat = i;
					break;
				}
			}

			image.setImage(image.getImage().getScaledInstance(96, 24,
					Image.SCALE_DEFAULT));
			roleStateLabel[seat].setIcon(image);
		} else {
			int seat = -1;// 记录要更换状态的位置
			ImageIcon image = null;
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				System.out.println("I'm roommaster");
				image = roomMaster;
				if (userID.equals(user.getID())) {
					roomMaster(flag);
				}
				// 当变为房主后，“准备完毕”按钮会变成“开始游戏按钮”，且多出一个“踢人”的按钮
			}
			for (int i = 0; i < 4; i++) {
				if (oponentNameLabel[i].getText().equals(userID)) {
					seat = i;
					break;
				}
			}

			image.setImage(image.getImage().getScaledInstance(96, 24,
					Image.SCALE_DEFAULT));
			oponentStateLabel[seat].setIcon(image);
		}
	}

	public void chat(String message) {
		String[] info = message.split(":");
		int nameLength = info[0].length() + 2;
		String blank = "";

		for (int i = 0; i < nameLength; i++) {
			blank = blank + " ";
		}
		message = message.replace("|", "\n" + blank);
		sendArea.append(message);
		sendArea.append("\n");
	}

	public void roomMaster(int flag) {
		isMaster = true;
		// 可能问题：先调构造函数，这样会报空针错误
		// 改进方法：将显示“开始游戏”按钮放在本方法之后
		if (prepareButton != null) {
			backgroundPanel.remove(prepareButton);
		}
		// path1 = beginPath1;
		// path2 = beginPath2;
		// path3 = beginPath3;
		// showPrepareButton();

		beginButton = new IconButton("image/cooperation/begin1.png",
				"image/cooperation/begin2.png", "image/cooperation/begin3.png");
		beginButton.addActionListener(this);
		beginButton.setLocation(890, 550);
		beginButton.setSize(110, 110);
		backgroundPanel.add(beginButton);

		backButton.setVisible(true);
		
		showKickButton();

		if (flag == 0) {// 房主在上面
			int seat = -1;
			for (int i = 0; i < 4; i++) {
				if (roleStateLabel[i].getIcon() == roomMaster) {
					seat = i;
					break;
				}
			}
			if (seat != -1)
				roleKickButton[seat].setVisible(false);// 将房主的踢人按钮设为不可编辑

		} else {// 房主在下面
			int seat = -1;
			for (int i = 0; i < 4; i++) {
				if (oponentStateLabel[i].getIcon() == roomMaster) {
					seat = i;
					break;
				}
			}
			if (seat != -1)
				oponentKickButton[seat].setVisible(false);// 将房主的踢人按钮设为不可编辑

		}

		backgroundPanel.repaint();
	}

	public void common() {
		if (prepareButton != null) {
			backgroundPanel.remove(prepareButton);
		}
		// path1 = preparePath1;
		// path2 = preparePath2;
		// path3 = preparePath3;

		prepareButton = new IconButton("image/cooperation/prepareGame1.png",
				"image/cooperation/prepareGame2.png",
				"image/cooperation/prepareGame3.png");
		prepareButton.addActionListener(this);
		prepareButton.setLocation(890, 550);
		prepareButton.setSize(110, 110);
		backgroundPanel.add(prepareButton);

		// showPrepareButton();
		// for(int i=0;i<4;i++){
		// kick[i].setIcon(null);
		// kick[i].removeActionListener(this);
		// kick[i].setVisible(false);
		// }
		backgroundPanel.repaint();
	}

	// private void showPrepareButton() {
	// prepareButton = new IconButton(path1, path2, path3);
	// prepareButton.addActionListener(this);
	// prepareButton.setLocation(796, 491);
	// prepareButton.setSize(110, 110);
	// backgroundPanel.add(prepareButton);
	// }

	private void showKickButton() {
		// 踢人按钮
		System.out.println("Kick cp");
		// //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// for (int i = 0; i < 4; i++) {
		// roleKickButton[i] = new IconButton(
		// "image/cooperation/kick1.png",
		// "image/cooperation/kick2.png",
		// "image/cooperation/kick3.png");
		// roleKickButton[i].setSize(40, 40);
		// roleKickButton[i].setLocation(140 + i * 140, 180);
		// roleKickButton[i].addActionListener(this);
		// backgroundPanel.add(roleKickButton[i]);
		// }
		// for (int i = 0; i < 4; i++) {
		// oponentKickButton[i] = new IconButton(
		// "image/cooperation/kick1.png",
		// "image/cooperation/kick2.png",
		// "image/cooperation/kick3.png");
		// oponentKickButton[i].setSize(40, 40);
		// oponentKickButton[i].setLocation(340 + i * 140, 350);
		// oponentKickButton[i].addActionListener(this);
		// backgroundPanel.add(oponentKickButton[i]);
		// }
		// //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		for (int i = 0; i < 4; i++) {
			if (roleNameLabel[i].getText().length() != 0) {
				if (!(roleStateLabel[i].getIcon() == roomMaster))
					roleKickButton[i].setVisible(true);
			}
		}

		for (int i = 0; i < 4; i++) {
			if (oponentNameLabel[i].getText().length() != 0) {
				if (!(oponentStateLabel[i].getIcon() == roomMaster))
					roleKickButton[i].setVisible(true);
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private int getLevel(int exp) {

		int level = 0;
		int[] expList = { 0, 30, 80, 180, 400, 700, 1000, 1300, 1600, 2000 };

		for (int i = 0; i < expList.length; i++) {
			try {
				if (exp >= expList[i] && exp < expList[i + 1]) {
					level = i;
					break;
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				level = expList.length - 1;
			}
		}

		return level;
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void requestPlayerList() {
		battleRoomController.requestUserList(roomNO);
	}

	private void quit(String userID, int roomNO) {
		battleRoomController.quitBattleRoom(userID, roomNO);
	}

	private void setReady() {
		battleRoomController.setReady(user.getID(), roomNO);
	}

	private void chatInRoom(String message) {
		battleRoomController.chatInRoom(message, user.getID(), roomNO);
	}

	private void kickPlayer(String roommasterID, String userID, int roomNO) {
		battleRoomController.kickPlayer(roommasterID, userID, roomNO);
	}

	private void changeTeam() {
		String userID = user.getID();
		battleRoomController.changeTeam(userID, roomNO);
	}

	private void chooseProp(int teamNO, int propNO, int roomNO) {
		battleRoomController.chooseProp(teamNO, propNO, roomNO);

	}

	private void startGame() {
		String userID = user.getID();
		battleRoomController.initGame(userID, roomNO);

	}

	private int getTeamNO(String userID) {
		boolean found = false;
		int teamNO = -1;

		for (ActiveUser player : playerList1) {
			if (player.getUserID().equals(userID)) {
				teamNO = 1;
				found = true;
				break;
			}
		}

		if (!found) {
			for (ActiveUser player : playerList2) {
				if (player.getUserID().equals(userID)) {
					teamNO = 2;
					found = true;
					break;
				}
			}
		}

		return teamNO;
	}

	public void doSomething() {
		requestPlayerList();
		/*
		 * String type=IOHelper.inputFromConsole("请输入行为（Quit): ");
		 * if(type.equals("Quit")){ quit(user.getID(),roomNO); }else
		 * if(type.equals("Ready")){ setReady(); }else if(type.equals("Chat")){
		 * chatInRoom("Let's have fun"); }else if(type.equals("Kick")){ String
		 * userID=IOHelper.inputFromConsole("请输入玩家名称： ");
		 * kickPlayer(user.getID(),userID,roomNO); }else
		 * if(type.equals("Change")){ changeTeam(); }
		 */
		// kickPlayer的参数为除房主外的人的ID

	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Battle Coop_Room receive message: " + message);
		String[] info = message.split(" ");

		if (info[1].equals(Service.ReplyEnterBattleRoom.toString())) {
			ActiveUser player = new ActiveUser(info[2], UserState.LEISURE,
					Integer.parseInt(info[3]), Integer.parseInt(info[4]),
					Integer.parseInt(info[5]));
			String teamNO = info[5];

			if (teamNO.equals("1")) {
				playerList1.add(player);
				playerIn(0, player.getUserID(), player.getMoney(),
						player.getExp(), player.getUserState());
			} else if (teamNO.equals("2")) {
				playerList2.add(player);
				playerIn(1, player.getUserID(), player.getMoney(),
						player.getExp(), player.getUserState());
			}
		} else if (info[1].equals(Service.UserStateChange.toString())) {
			String userID = info[2];
			UserState state = Enum.valueOf(UserState.class, info[3]);
			setUserState(userID, state);
			int teamNO = getTeamNO(userID) - 1;
			System.out.println(teamNO);
			playerStateChange(teamNO, userID, state);
		} else if (info[1].equals(Service.ReplyQuitBattleRoom.toString())) {
			if (user.getID().equals(info[5])) {
				mainFrame.dispose();
			}
			removePlayer(info[2]);
		} else if (info[1].equals(Service.ReplySetReady.toString())) {
			setReady(message);
		} else if (info[1].equals(Service.ReplyChangeTeam.toString())) {
			if (info[2].equals(ResultMessage.CHANGE_TEAM_SUCCESSFULLY
					.toString())) {
				String userID = info[3];
				if (isIn(userID, playerList1)) {
					changeUser(userID, playerList1, playerList2);
					ActiveUser user = getPlayer(userID, playerList2);
					playerOut(0, userID);
					playerIn(1, user.getUserID(), user.getMoney(),
							user.getExp(), user.getUserState());
					if(userID.equals(user.getUserID())){
						
					}
				} else if (isIn(userID, playerList2)) {
					changeUser(userID, playerList2, playerList1);
					ActiveUser user = getPlayer(userID, playerList1);
					playerOut(1, userID);
					playerIn(0, user.getUserID(), user.getMoney(),
							user.getExp(), user.getUserState());
				}
				changeSuccess();
			} else {
				changeError();
			}
			//
		} else if (info[1].equals(Service.ReplyBattleProp.toString())) {
			int PropNO = Integer.parseInt(info[2]);
			selectTool(PropNO);
		} else if (info[1].equals(Service.ChatReceive.toString())) {
			String receive = "";
			for (int i = 2; i < info.length; i++) {
				receive = receive + " " + info[i];
			}
			chat(receive);
		} else if (info[1].equals(Service.GameStart.toString())) {
			mainFrame.dispose();
		} else if (info[1].equals(Service.ReplyBattlePropList.toString())) {
			String[] propMessage = info[2].split("-");
			if (propMessage[0].equals("1")) {
				selectTool(0);
			}

			if (propMessage[1].equals("1")) {
				selectTool(1);
			}

			if (propMessage[2].equals("1")) {
				selectTool(2);
			}

		} else if(info[1].equals(Service.ReplyChangePropList.toString())){
			String[] propMessage = info[2].split("-");
			boolean tool1=false;
			boolean tool2=false;
			boolean tool3=false;
			if (propMessage[0].equals("1")) {
				tool1=true;
			}

			if (propMessage[1].equals("1")) {
				tool2=true;
			}

			if (propMessage[2].equals("1")) {
				tool3=true;
			}
			setTool(tool1,tool2,tool3);
		}
		// 踢人，换队，界面跳转问题
	}

	private void changeUser(String userID, ArrayList<ActiveUser> userList1,
			ArrayList<ActiveUser> userList2) {
		for (ActiveUser user : userList1) {
			if (user.getUserID().equals(userID)
					&& user.getUserState() == UserState.LEISURE) {
				userList1.remove(user);
				userList2.add(user);
				break;
			}
		}
	}

	private boolean isIn(String userID, ArrayList<ActiveUser> userList) {
		boolean isIn = false;
		for (ActiveUser player : userList) {
			if (player.getUserID().equals(userID)) {
				isIn = true;
				break;
			}
		}

		return isIn;
	}

	private ActiveUser getPlayer(String userID, ArrayList<ActiveUser> userList) {
		ActiveUser user = null;
		for (ActiveUser player : userList) {
			if (player.getUserID().equals(userID)) {
				user = player;
				break;
			}
		}

		return user;
	}

	private void removePlayer(String userID) {
		for (ActiveUser player : playerList1) {
			if (player.getUserID().equals(userID)) {
				int teamNO = 0;
				playerOut(teamNO, userID);
				playerList1.remove(player);
				break;
			}
		}

		for (ActiveUser player : playerList2) {
			if (player.getUserID().equals(userID)) {
				int teamNO = 1;
				playerOut(teamNO, userID);
				playerList2.remove(player);
				break;
			}
		}
	}

	private void setUserState(String userID, UserState state) {
		for (ActiveUser user : playerList1) {
			if (user.getUserID().equals(userID)) {
				user.setUserState(state);
				break;
			}
		}

		for (ActiveUser user : playerList2) {
			if (user.getUserID().equals(userID)) {
				user.setUserState(state);
				break;
			}
		}
	}

	private void setReady(String message) {
		String[] info = message.split(" ");

		if (info[2].equals(ResultMessage.SET_READY_SUCCESSFULLY.toString())) {
			UserState state = UserState.READY;
			setUserState(user.getID(), state);
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public synchronized void setUserList(ArrayList<ActiveUser> userList, int teamNO) {
		// TODO Auto-generated method stub
		if (teamNO == 1) {
			this.playerList1 = userList;
			for (ActiveUser user1 : playerList1) {
				playerIn(0, user1.getUserID(), user1.getMoney(),
						user1.getExp(), user1.getUserState());
				if (user1.getUserID().equals(user.getID())
						&& (user1.getUserState() == UserState.ROOMMASTER)) {
					roomMaster(0);
				} else if (user1.getUserID().equals(user.getID())
						&& (user1.getUserState() == UserState.LEISURE)) {
					common();
				}
			}
		} else if (teamNO == 2) {
			this.playerList2 = userList;
			for (ActiveUser user1 : playerList2) {
				playerIn(1, user1.getUserID(), user1.getMoney(),
						user1.getExp(), user1.getUserState());
				if (user1.getUserID().equals(user.getID())
						&& (user1.getUserState() == UserState.ROOMMASTER)) {
					roomMaster(1);
				} else if (user1.getUserID().equals(user.getID())
						&& (user1.getUserState() == UserState.LEISURE)) {
					common();
				}
			}
		}

		System.out.println("Current Player In Coop_Room: "
				+ (playerList1.size() + playerList2.size()));
	}

	@Override
	public UserVO getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == sendButton) {
			String message = writeArea.getText();
			message = message.replace("\n", "|");
			if (message.length() == 0) {
				// 发送内容为空，do nothing
			} else {
				writeArea.setText("");
				chatInRoom(message);

				int height = 10;
				Point p = new Point();
				p.setLocation(0, sendArea.getLineCount() * height);
				sendScroll.getViewport().setViewPosition(p);
			}

		}

		if (e.getSource() == prepareButton) {
			System.out.println("abcd");
			setReady();
			backButton.setVisible(false);
		}
		if (e.getSource() == beginButton) {
			boolean canStart = true;
			if (playerList1.size() == 0 || playerList2.size() == 0) {
				canStart = false;
			}

			for (ActiveUser user : playerList1) {
				if (user.getUserState() == UserState.LEISURE) {
					canStart = false;
					break;
				}
			}

			for (ActiveUser user : playerList2) {
				if (user.getUserState() == UserState.LEISURE) {
					canStart = false;
					break;
				}
			}

			if (canStart) {
				mainFrame.dispose();
				startGame();
			} else {
				// 出错信息
				TipRunnable fail = new TipRunnable("image/tip/beginError.png",
						500, 600, 300, 50, 2);
				Thread myThread = new Thread(fail);
				myThread.start();
			}
			System.out.println("begin");
		}

		if (e.getSource() == backButton) {
			quit(user.getID(), roomNO);
			mainFrame.dispose();
		}

		if (e.getSource() == toolButton1) {
			// if(toolButton1.getIcon()==toolImage1_1){//加道具
			// toolButton1.setIcon(toolImage2_1);
			// }else{//减道具
			// toolButton1.setIcon(toolImage1_1);
			// }
			int teamNO = getTeamNO(user.getID());
			if (user.getMoney() >= 388) {
				chooseProp(teamNO, 0, roomNO);
				user.setMoney(user.getMoney()-388);
			} else {
				moneyNotEnough();
			}
		}
		if (e.getSource() == toolButton2) {
			// if(toolButton2.getIcon()==toolImage1_2){//加道具
			// toolButton2.setIcon(toolImage2_2);
			// }else{//减道具
			// toolButton2.setIcon(toolImage1_2);
			// }
			int teamNO = getTeamNO(user.getID());
			if (user.getMoney() >= 488) {
				chooseProp(teamNO, 1, roomNO);
				user.setMoney(user.getMoney()-488);
			} else {
				moneyNotEnough();
			}
		}
		if (e.getSource() == toolButton3) {
			// if(toolButton3.getIcon()==toolImage1_3){//加道具
			// toolButton3.setIcon(toolImage2_3);
			// }else{//减道具
			// toolButton3.setIcon(toolImage1_3);
			// }
			int teamNO = getTeamNO(user.getID());
			if (user.getMoney() >= 88) {
				chooseProp(teamNO, 2, roomNO);
				user.setMoney(user.getMoney()-88);
			} else {
				moneyNotEnough();
			}
		}

		if (e.getSource() == button) {
			changeTeam();
		}

		if (e.getSource() == roleKickButton[0]) {
			System.out.println("shgjsgj");
			String name = roleNameLabel[0].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == roleKickButton[1]) {
			String name = roleNameLabel[1].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == roleKickButton[2]) {
			String name = roleNameLabel[2].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == roleKickButton[3]) {
			String name = roleNameLabel[3].getText();
			kickPlayer(user.getID(), name, roomNO);
		}

		if (e.getSource() == oponentKickButton[0]) {
			String name = oponentNameLabel[0].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == oponentKickButton[1]) {
			String name = oponentNameLabel[1].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == oponentKickButton[2]) {
			String name = oponentNameLabel[2].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == oponentKickButton[3]) {
			String name = oponentNameLabel[3].getText();
			kickPlayer(user.getID(), name, roomNO);
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		chooseToolTip = new MySplashScreen("image/tip/chooseToolTip.png");
		chooseToolTip.setLocation(1000, 100);
		chooseToolTip.setSize(200, 87);
		chooseToolTip.setVisible(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// if(e.getSource()==toolButton1){
		//
		// }
		// if(e.getSource()==toolButton2){
		//
		// }
		// if(e.getSource()==toolButton3){
		//
		// }
		chooseToolTip.dispose();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO 自动生成的方法存根
		if (e.getKeyCode() == KeyEvent.VK_ENTER && e.isControlDown()) {
			String message = writeArea.getText();
			message = message.replace("\n", "|");
			if (message.length() == 0) {
				// 发送内容为空，do nothing
			} else {
				writeArea.setText("");
				chatInRoom(message);

				int height = 10;
				Point p = new Point();
				p.setLocation(0, sendArea.getLineCount() * height);
				sendScroll.getViewport().setViewPosition(p);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO 自动生成的方法存根

	}
}
