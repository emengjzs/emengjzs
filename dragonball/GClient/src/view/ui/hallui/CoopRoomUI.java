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

import common.Service;
import common.UserState;
import model.netbl.NetCoopRoomBL;
import model.netservice.NetCoopRoomBLService;
import model.vo.ActiveUser;
import model.vo.UserVO;
import setup.GameClient;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.Music;
import view.ui.myComponent.MySplashScreen;
import view.ui.myComponent.TipRunnable;
import view.uiservice.CoopRoomService;

public class CoopRoomUI implements CoopRoomService, ActionListener,
		MouseListener,KeyListener {

	UserVO user;
	boolean isMaster = false;

	int roomNO;
	static ArrayList<ActiveUser> playerList = new ArrayList<ActiveUser>();
	static NetCoopRoomBLService coopRoomController;

	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	JPanel backgroundPanel;

	// ����ԭʼ״̬
	ImageIcon toolImage_notChoose1 = new ImageIcon("image/solo/tool1_1.png");
	ImageIcon toolImage_notChoose2 = new ImageIcon("image/solo/tool1_2.png");
	ImageIcon toolImage_notChoose3 = new ImageIcon("image/solo/tool1_3.png");

	// ���߱�ѡ�к��״̬
	ImageIcon toolImage_choosed1 = new ImageIcon("image/solo/tool2_1.png");
	ImageIcon toolImage_choosed2 = new ImageIcon("image/solo/tool2_2.png");
	ImageIcon toolImage_choosed3 = new ImageIcon("image/solo/tool2_3.png");

	// ���״̬
	ImageIcon ready = new ImageIcon("image/cooperation/ready.png");
	ImageIcon roomMaster = new ImageIcon("image/cooperation/roomMaster.png");
	ImageIcon leisure = new ImageIcon("image/cooperation/leisure.png");

	// ���߰�ť
	JButton toolButton1;
	JButton toolButton2;
	JButton toolButton3;

	// ����Ƶ�3��button����ʾ����ʾ��Ϣ
	MySplashScreen chooseToolTip;

	IconButton prepareButton;// ���͵ġ�׼����Ϸ����ť
	IconButton beginButton;// �����ġ���ʼ��Ϸ����ť
	IconButton backButton;

	JLabel roleLabel[] = new JLabel[4];
	JLabel nameLabel[] = new JLabel[4];
	JLabel stateLabel[] = new JLabel[4];

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

	IconButton kick[] = new IconButton[4];
	
	int xOld = 0;
	int yOld = 0;

	public CoopRoomUI(UserVO user, int roomNO) {
		this.roomNO = roomNO;
		this.user = user;
		coopRoomController = NetCoopRoomBL.getCoopRoomController();
		System.out.println("In the XXOO ROOM, have �� fun");
		mainFrame = new JFrame();
		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);// ȥ�����ڱ߿�

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

		mainFrame.setBackground(new Color(0,0,0,0));
		mainFrame.setVisible(true);

		if (GameClient.MusicOn) {
			Music music = new Music("music/gate_open.mp3", false);
			music.play();
		}
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

		backButton = new IconButton("image/cooperation/backtohall1.png",
				"image/cooperation/backtohall2.png",
				"image/cooperation/backtohall3.png");
		backButton.addActionListener(this);
		backButton.setLocation(25, 20);
		backButton.setSize(175, 64);
		backgroundPanel.add(backButton);

		// �Ի����
		JPanel messagePanel = new JPanel();
		messagePanel.setOpaque(false);
		messagePanel.setLocation(10, 491);
		messagePanel.setLayout(null);
		messagePanel.setSize(850, 199);
		backgroundPanel.add(messagePanel);

		writeArea = new JTextArea();
		writeArea.setLineWrap(true);
		writeArea.setFont(new Font("���ǵ������", Font.BOLD, 18));
		writeArea.setOpaque(false);
		writeArea.addKeyListener(this);

		JScrollPane writeScroll = new JScrollPane(writeArea);
		writeScroll.setSize(685, 32);
		writeScroll.setLocation(50, 138);
		writeScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(writeScroll);

		sendArea = new JTextArea();
		sendArea.setEditable(false);
		sendArea.setFont(new Font("���ǵ������", Font.BOLD, 18));
		sendArea.setOpaque(false);
		sendScroll = new JScrollPane(sendArea);
		sendScroll.setOpaque(false);
		sendScroll.setSize(790, 125);
		sendScroll.setLocation(50, 5);
		sendScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		sendScroll.validate();
		messagePanel.add(sendScroll);

		sendButton = new IconButton("image/cooperation/send1.png",
				"image/cooperation/send2.png", "image/cooperation/send3.png");
		sendButton.setLocation(735, 138);
		sendButton.setSize(100, 32);
		sendButton.addActionListener(this);
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

		// ���״̬
		for (int i = 0; i < 4; i++) {
			// stateLabel[i] = new JLabel(new ImageIcon(
			// "image/cooperation/state.png"));
			stateLabel[i] = new JLabel();
			stateLabel[i].setSize(120, 30);
			stateLabel[i].setLocation(100 + i * 170, 350);
			backgroundPanel.add(stateLabel[i]);
		}

		// ������
		for (int i = 0; i < 4; i++) {
			ImageIcon roleImg = new ImageIcon("image/cooperation/role.png");
			roleImg.setImage(roleImg.getImage().getScaledInstance(120, 160,
					Image.SCALE_DEFAULT));
			roleLabel[i] = new JLabel(roleImg);
			roleLabel[i].setSize(120, 160);
			roleLabel[i].setLocation(100 + i * 170, 240);
			backgroundPanel.add(roleLabel[i]);
		}

		// ����ǳ����
		for (int i = 0; i < 4; i++) {
			nameLabel[i] = new JLabel();
			nameLabel[i].setLocation(100 + i * 170, 400);
			nameLabel[i].setSize(120, 30);
			nameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel[i].setFont(new Font("���ǵ������", Font.BOLD, 18));
			nameLabel[i].setForeground(new Color(210, 105, 30));
			backgroundPanel.add(nameLabel[i]);
		}

		for (int i = 0; i < 4; i++) {
			kick[i] = new IconButton("image/cooperation/kick1.png",
					"image/cooperation/kick2.png",
					"image/cooperation/kick3.png");
			kick[i].setSize(40, 40);
			kick[i].setLocation(205 + i * 170, 220);
			kick[i].addActionListener(this);
			kick[i].setVisible(false);
			backgroundPanel.add(kick[i]);
		}

	}

	// �ڽ�������ʾѡ�еĵ���
	public boolean selectTool(int toolNo) {
		if (toolNo == 0) {// ����1
			if (toolButton1.getIcon() == toolImage_choosed1) {// ͼƬ�ѱ�ѡ��,��ʾ������ʾ����
				showError();
				return false;
			} else {
				toolButton1.setIcon(toolImage_choosed1);
				return true;
			}
		} else if (toolNo == 1) {// ����2
			if (toolButton2.getIcon() == toolImage_choosed2) {
				showError();
				return false;
			} else {
				toolButton2.setIcon(toolImage_choosed2);
				return true;
			}
		} else {// ����3
			if (toolButton3.getIcon() == toolImage_choosed3) {
				showError();
				return false;
			} else {
				toolButton3.setIcon(toolImage_choosed3);
				return true;
			}
		}
	}

	// ����������
	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void moneyNotEnough() {
		TipRunnable fail = new TipRunnable("image/cooperation/moneyNotEnough.png", 450,
				450, 300, 50, 2);
		Thread myThread = new Thread(fail);
		myThread.start();
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	@SuppressWarnings("static-access")
	private void showError() {// �ظ�ѡ�����ʱ����ʾ������ʾ����
		MySplashScreen window = new MySplashScreen(
				"image/cooperation/error.png");
		window.setLocation(500, 350);
		window.setSize(300, 50);
		window.setVisible(true);
		try {
			new Thread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		window.dispose();
	}

	public synchronized void playerIn(String userID, int money, int exp, UserState userstate) {
		int blankSeat = -1;
		ImageIcon image = null;

		for (int i = 0; i < 4; i++) {
			if (nameLabel[i].getText().length() == 0) {
				blankSeat = i;
				break;
			}
		}// ���˿��Լ�⵽�ڣ�blankSeat+1����λ��Ϊ��

		nameLabel[blankSeat].setText(userID);// ��ʾID
		// if(blankSeat==0){//��ʾ״̬
		// stateLabel[0].setIcon(roomMaster);
		// }else{
		// stateLabel[blankSeat].setIcon(leisure);
		// }
		if (userstate == UserState.LEISURE) {
			image = leisure;
		} else if (userstate == UserState.READY) {
			image = ready;
		} else if (userstate == UserState.ROOMMASTER) {
			image = roomMaster;
		}
		stateLabel[blankSeat].setIcon(image);

		// ��ʾ����ͼ��
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		ImageIcon roleImg = new ImageIcon("Image/login/player" + getLevel(exp)
				+ ".png");
		roleImg.setImage(roleImg.getImage().getScaledInstance(120, 160,
				Image.SCALE_DEFAULT));
		roleLabel[blankSeat].setIcon(roleImg);
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		if (isMaster)
			kick[blankSeat].setVisible(true);

		System.out.println(blankSeat + 1);
	}

	public synchronized void playerOut(String userID) {
		int outSeat = -1;// ����Ҫ�˳���������ڵ�λ��
		int lastSeat = 3;// �������һ��������ڵ�λ��
		for (int i = 0; i < 4; i++) {
			if (nameLabel[i].getText().equals(userID)) {
				outSeat = i;
			}
			if (nameLabel[i].getText().length() == 0) {
				lastSeat = i - 1;
				break;
			}
		}

		ImageIcon roleImg = new ImageIcon("image/cooperation/role.png");
		roleImg.setImage(roleImg.getImage().getScaledInstance(120, 160,
				Image.SCALE_DEFAULT));
		if (outSeat == lastSeat) {
			// ���Ҫ�˳��������һ���ˣ���ֱ�Ӱ����һ��λ���ϵ�����Ĩȥ
			System.out.println(lastSeat);
			nameLabel[lastSeat].setText("");
			stateLabel[lastSeat].setIcon(null);
			roleLabel[lastSeat].setIcon(roleImg);
			kick[lastSeat].setVisible(false);
		} else {// ����������һ���ˣ������һ����λ�������������Ƶ��Ƴ���λ����,��Ĩȥ���һ��λ���ϵ�����
			nameLabel[outSeat].setText(nameLabel[lastSeat].getText());
			stateLabel[outSeat].setIcon(stateLabel[lastSeat].getIcon());
			roleLabel[outSeat].setIcon(roleLabel[lastSeat].getIcon());
			if (isMaster && !nameLabel[outSeat].getText().equals(user.getID()))
				kick[outSeat].setVisible(true);

			nameLabel[lastSeat].setText("");
			stateLabel[lastSeat].setIcon(null);
			roleLabel[lastSeat].setIcon(roleImg);
			kick[lastSeat].setVisible(false);
		}

	}

	public void playerStateChange(String userID, UserState userstate) {
		int seat = -1;// ��¼Ҫ����״̬��λ��
		ImageIcon image = null;
		if (userstate == UserState.LEISURE) {
			image = leisure;
		} else if (userstate == UserState.READY) {
			image = ready;
		} else if (userstate == UserState.ROOMMASTER) {
			// System.out.println("I'm roommaster");
			image = roomMaster;
			if (userID.equals(user.getID())) {
				roomMaster();
			}
			// ����Ϊ�����󣬡�׼����ϡ���ť���ɡ���ʼ��Ϸ��ť�����Ҷ��һ�������ˡ��İ�ť
		}

		for (int i = 0; i < 4; i++) {
			if (nameLabel[i].getText().equals(userID)) {
				seat = i;
				break;
			}
		}
		stateLabel[seat].setIcon(image);
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

	public void roomMaster() {
		isMaster = true;
		// �������⣺�ȵ����캯���������ᱨ�������
		// �Ľ�����������ʾ����ʼ��Ϸ����ť���ڱ�����֮��
		if (prepareButton != null || beginButton != null) {
			backgroundPanel.remove(prepareButton);
		}
		// path1 = beginPath1;
		// path2 = beginPath2;
		// path3 = beginPath3;
		beginButton = new IconButton("image/cooperation/begin1.png",
				"image/cooperation/begin2.png", "image/cooperation/begin3.png");
		beginButton.addActionListener(this);
		beginButton.setLocation(890, 550);
		beginButton.setSize(110, 110);
		backgroundPanel.add(beginButton);

		backButton.setVisible(true);
		
		// showPrepareButton();
		showKickButton();

		int seat = -1;
		for (int i = 0; i < 4; i++) {
			if (stateLabel[i].getIcon() == roomMaster) {
				seat = i;
				break;
			}
		}
		// kick[seat].setEnabled(false);// �����������˰�ť��Ϊ���ɱ༭
		kick[seat].setVisible(false);// �����������˰�ť��Ϊ���ɱ༭
		backgroundPanel.repaint();
	}

	public void common() {
		if (beginButton != null || prepareButton != null) {
			backgroundPanel.remove(beginButton);
		}
		prepareButton = new IconButton("image/cooperation/prepareGame1.png",
				"image/cooperation/prepareGame2.png",
				"image/cooperation/prepareGame3.png");
		prepareButton.addActionListener(this);
		prepareButton.setLocation(890, 550);
		prepareButton.setSize(110, 110);
		backgroundPanel.add(prepareButton);
		// path1 = preparePath1;
		// path2 = preparePath2;
		// path3 = preparePath3;
		// showPrepareButton();

		backgroundPanel.repaint();
	}

	// private void showPrepareButton(){
	// prepareButton = new IconButton(path1,path2,path3);
	// prepareButton.addActionListener(this);
	// prepareButton.setLocation(796, 491);
	// prepareButton.setSize(110, 110);
	// backgroundPanel.add(prepareButton);
	// }

	private void showKickButton() {
		// ���˰�ť
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// for (int i = 0; i < 4; i++) {
		// kick[i] = new IconButton("image/cooperation/kick1.png",
		// "image/cooperation/kick2.png",
		// "image/cooperation/kick3.png");
		// kick[i].setSize(40, 40);
		// kick[i].setLocation(205 + i * 170, 220);
		// kick[i].addActionListener(this);
		// backgroundPanel.add(kick[i]);
		// }
		// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		for (int i = 0; i < 4; i++) {
			if (nameLabel[i].getText().length() != 0) {
				if (!(stateLabel[i].getIcon() == roomMaster))
					kick[i].setVisible(true);
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

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == sendButton) {
			String message = writeArea.getText();
			message = message.replace("\n", "|");
			if (message.length() == 0) {
				// ��������Ϊ�գ�do nothing
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
			setReady();
			System.out.println("preapre");
			backButton.setVisible(false);
		}
		if (e.getSource() == beginButton) {
			boolean canStart = true;
			for (ActiveUser user : playerList) {
				if (user.getUserState().equals(UserState.LEISURE)) {
					canStart = false;
					break;
				}
			}
			if (canStart) {
				mainFrame.dispose();
				startGame();
			} else {
				// ������Ϣ
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
			// if(toolButton1.getIcon()==toolImage1_1){//�ӵ���
			// toolButton1.setIcon(toolImage2_1);
			// }else{//������
			// toolButton1.setIcon(toolImage1_1);
			// }
			if(user.getMoney()>=388){
				chooseProp(0, roomNO);
				user.setMoney(user.getMoney()-388);
			}else{
				moneyNotEnough();
			}
		}
		if (e.getSource() == toolButton2) {
			// if(toolButton2.getIcon()==toolImage1_2){//�ӵ���
			// toolButton2.setIcon(toolImage2_2);
			// }else{//������
			// toolButton2.setIcon(toolImage1_2);
			// }
			if(user.getMoney()>=488){
				chooseProp(1, roomNO);
				user.setMoney(user.getMoney()-488);
			}else{
				moneyNotEnough();
			}
		}
		if (e.getSource() == toolButton3) {
			// if(toolButton3.getIcon()==toolImage1_3){//�ӵ���
			// toolButton3.setIcon(toolImage2_3);
			// }else{//������
			// toolButton3.setIcon(toolImage1_3);
			// }
			if(user.getMoney()>=88){
				chooseProp(2, roomNO);
				user.setMoney(user.getMoney()-88);
			}else{
				moneyNotEnough();
			}
		}

		if (e.getSource() == kick[0]) {
			String name = nameLabel[0].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == kick[1]) {
			System.out.println("Kick 1");
			String name = nameLabel[1].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == kick[2]) {
			String name = nameLabel[2].getText();
			kickPlayer(user.getID(), name, roomNO);
		}
		if (e.getSource() == kick[3]) {
			String name = nameLabel[3].getText();
			kickPlayer(user.getID(), name, roomNO);
		}

	}

	private void requestPlayerList() {
		coopRoomController.requestUserList(roomNO);
	}

	private void setReady() {
		coopRoomController.setReady(user.getID(), roomNO);
	}

	private void chatInRoom(String message) {
		coopRoomController.chatInRoom(message, user.getID(), roomNO);
	}

	private void kickPlayer(String roommasterID, String userID, int roomNO) {
		coopRoomController.kickPlayer(roommasterID, userID, roomNO);
	}

	private void quit(String userID, int roomNO) {
		coopRoomController.quitCoopRoom(userID, roomNO);
	}

	private void chooseProp(int propNO, int roomNO) {
		coopRoomController.chooseProp(propNO, roomNO, user.getID());
	}

	private void startGame() {
		coopRoomController.initGame(user.getID(), roomNO);
	}

	public void doSomething() {

		requestPlayerList();
		coopRoomController.requestPropList(roomNO);
		/*
		 * String type = IOHelper.inputFromConsole("��������Ϊ��Quit): "); if
		 * (type.equals("Quit")) { quit(user.getID(), roomNO); } if
		 * (type.startsWith("Start")) { coopRoomController.initGame(false,
		 * false, false, user.getID(), roomNO); }
		 */
		// setReady();
		// chatInRoom("Let's have fun");
		// kickPlayer();
		// kickPlayer�Ĳ���Ϊ����������˵�ID

	}

	@SuppressWarnings("unused")
	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Coop Coop_Room receive message: " + message);
		String[] info = message.split(" ");

		if (info[1].equals(Service.ReplyEnterCoopRoom.toString())) {
			if(playerList.size()!=0){
				ActiveUser player = new ActiveUser(info[2], UserState.LEISURE,
						Integer.parseInt(info[3]), Integer.parseInt(info[4]),
						Integer.parseInt(info[5]));
				playerList.add(player);
				playerIn(player.getUserID(), player.getMoney(), player.getExp(),
						player.getUserState());
			}
			// if userID != self userID ���÷���
		} else if (info[1].equals(Service.ReplyQuitCoopRoom.toString())) {
			if (user.getID().equals(info[5])) {
				mainFrame.dispose();
			}
			for (ActiveUser player : playerList) {
				if (player.getUserID().equals(info[2])) {
					playerList.remove(player);
					playerOut(info[2]);
					break;
				}
			}
		} else if (info[1].equals(Service.ChatReceive.toString())) {
			String receive = "";
			for (int i = 2; i < info.length; i++) {
				receive = receive + " " + info[i];
			}
			chat(receive);
		} else if (info[1].equals(Service.UserStateChange.toString())) {
			UserState state = Enum.valueOf(UserState.class, info[3]);

			for (ActiveUser user : playerList) {
				if (info[2].equals(user.getUserID())) {
					user.setUserState(state);
					playerStateChange(info[2], state);
					System.out.println("State Change!");
				}
			}
		} else if (info[1].equals(Service.ReplyChooseProp.toString())) {
			int PropNO = Integer.parseInt(info[2]);
			selectTool(PropNO);
		} else if (info[1].equals(Service.ReplyCoopGameResult.toString())) {
			int money = Integer.parseInt(info[2]);
			int exp = Integer.parseInt(info[3]);
			int upgrade = Integer.parseInt(info[4]);
			String userID = info[5];

			if (userID.equals(user.getID())) {
				user.setMoney(user.getMoney() + money);
				user.setExp(user.getExp() + exp);
			}

			for (ActiveUser user : playerList) {
				if (user.getUserID().equals(userID)) {
					user.setMoney(user.getMoney() + money);
					user.setExp(user.getExp() + exp);
				}
			}
			// ���upgradeΪ0������������������upgradeֵ
		} else if (info[1].equals(Service.GameStart.toString())) {
			mainFrame.dispose();
		} else if (info[1].equals(Service.ReplyCoopProp.toString())) {
			String[] propInfo = info[2].split("-");
			if (propInfo[0].equals("1")) {
				selectTool(0);
			}

			if (propInfo[1].equals("1")) {
				selectTool(1);
			}

			if (propInfo[2].equals("1")) {
				selectTool(2);
			}
		}
		/*
		 * else if(info[1].equals(Service.ReplySetReady.toString())){ for
		 * (ActiveUser user1 : playerList) { if
		 * (user.getID().equals(user1.getUserID())) {
		 * user1.setUserState(UserState.READY);
		 * playerStateChange(info[2],UserState.READY); } } }
		 */
		// QuitCoopRoom

	}

	@SuppressWarnings("static-access")
	@Override
	public synchronized void  setUserList(ArrayList<ActiveUser> userList) {
		// TODO Auto-generated method stub
		this.playerList = userList;
		for (ActiveUser user1 : userList) {
			playerIn(user1.getUserID(), user1.getMoney(), user1.getExp(),
					user1.getUserState());
			if (user1.getUserID().equals(user.getID())
					&& (user1.getUserState() == UserState.ROOMMASTER)) {
				roomMaster();
			} else if (user1.getUserID().equals(user.getID())
					&& (user1.getUserState() == UserState.LEISURE)) {
				common();
			}
		}
		System.out.println("Current Player In Coop_Room: " + userList.size());
	}

	@Override
	public ArrayList<ActiveUser> getPlayerList() {
		// TODO Auto-generated method stub
		return playerList;
	}

	@Override
	public UserVO getUser() {
		// TODO Auto-generated method stub
		return user;
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
		// TODO �Զ����ɵķ������
		if(e.getKeyCode()== KeyEvent.VK_ENTER && e.isControlDown()){
			String message = writeArea.getText();
			message = message.replace("\n", "|");
			if (message.length() == 0) {
				// ��������Ϊ�գ�do nothing
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
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO �Զ����ɵķ������
		
	}
}
