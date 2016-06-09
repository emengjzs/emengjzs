package view.ui.hallui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.netbl.NetBattleGameHallBL;
import model.netservice.NetBattleGameHallBLService;
import model.vo.ActiveUser;
import model.vo.UserVO;
import common.BattleRoom;
import common.RoomState;
import common.Service;
import common.UserState;
import controller.netlistenerbl.NetStatisListenerBL;
import view.ui.fight.Fight_mainFrame;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.MySplashScreen;
import view.ui.myComponent.TipRunnable;
import view.uiservice.BattleGameHallService;

public class BattleGameHallUI implements BattleGameHallService, ActionListener,
		MouseListener {
	static NetBattleGameHallBLService gameHallController;
	UserVO user;
	static ArrayList<ActiveUser> userList = new ArrayList<ActiveUser>();
	BattleRoom[] roomList;

	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	//
	IconButton backButton;

	ImageIcon availableImage = new ImageIcon("image/cooperation/available.png");
	ImageIcon fullImage = new ImageIcon("image/cooperation/full.png");
	ImageIcon gamingImage = new ImageIcon("image/cooperation/gaming.png");

	// 4个房间的状态
	JLabel tipLabel[] = new JLabel[4];

	// 4个房间

	// 每个房间的状态
	JLabel tipLabel1;
	JLabel tipLabel2;
	JLabel tipLabel3;
	JLabel tipLabel4;

	// 每个房间
	JLabel houseJLabel1;
	JLabel houseJLabel2;
	JLabel houseJLabel3;
	JLabel houseJLabel4;
	
	MySplashScreen chooseTip;
	
	int xOld = 0;
	int yOld = 0;

	public BattleGameHallUI(UserVO user) {
		System.out.println("Welcome to XXOO Game Hall, have ♂ fun");
		this.user = user;
		gameHallController = NetBattleGameHallBL.getGameHallController();
		gameHallController.Enter(user.getID(), user.getMoney(), user.getExp(),
				user.getPower());

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
		
		mainFrame.setBackground(new Color(0,0,0,0));
		mainFrame.setVisible(true);
	}

	@SuppressWarnings("serial")
	private void initFrame() {
		Container contentPane = mainFrame.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"image/cooperation/background_hall.png");
		JPanel backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
						null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);

		backButton = new IconButton("image/cooperation/back1.png",
				"image/cooperation/back2.png", "image/cooperation/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(30, 580);
		backButton.setSize(170, 65);
		backgroundPanel.add(backButton);

		for (int i = 0; i < 4; i++) {
			tipLabel[i] = new JLabel();
			if (i < 2) {
				tipLabel[i].setLocation(250 + 270 * i, 310);
			} else {
				tipLabel[i].setLocation(250 + 270 * (i - 2), 475);
			}

			tipLabel[i].setSize(120, 50);

			backgroundPanel.add(tipLabel[i]);
			// this.add(tipLabel[i]);
		}

		houseJLabel1 = new JLabel(new ImageIcon("image/cooperation/house1.png"));
		houseJLabel1.addMouseListener(this);
		houseJLabel1.setLocation(170, 253);
		houseJLabel1.setSize(210, 140);
		// this.add(houseJLabel1);
		backgroundPanel.add(houseJLabel1);

		houseJLabel2 = new JLabel(new ImageIcon("image/cooperation/house2.png"));
		houseJLabel2.addMouseListener(this);
		houseJLabel2.setLocation(440, 253);
		houseJLabel2.setSize(210, 140);
		// this.add(houseJLabel2);
		backgroundPanel.add(houseJLabel2);

		houseJLabel3 = new JLabel(new ImageIcon("image/cooperation/house3.png"));
		houseJLabel3.addMouseListener(this);
		houseJLabel3.setLocation(170, 418);
		houseJLabel3.setSize(210, 140);
		// this.add(houseJLabel3);
		backgroundPanel.add(houseJLabel3);

		houseJLabel4 = new JLabel(new ImageIcon("image/cooperation/house4.png"));
		houseJLabel4.addMouseListener(this);
		houseJLabel4.setLocation(440, 418);
		houseJLabel4.setSize(210, 140);
		// this.add(houseJLabel4);
		backgroundPanel.add(houseJLabel4);

	}

	public void changeRoomState(int roomNO, RoomState roomstate) {
		ImageIcon image;
		if (roomstate == RoomState.AVAILABLE) {
			image = availableImage;
		} else if (roomstate == RoomState.FULL) {
			image = fullImage;
		} else {
			image = gamingImage;
		}
		tipLabel[roomNO].setIcon(image);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			Quit(user.getID());
			Fight_mainFrame main = new Fight_mainFrame(user);
			NetStatisListenerBL statisController = NetStatisListenerBL
					.getStatisControllerListener();
			statisController.setBattleController(main);
			main.Request();
			mainFrame.dispose();
			// new Coop_mainFrame(user);
		}

	}

	public boolean canIn(int roomNo) {
		if (tipLabel[roomNo].getIcon() == availableImage) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int roomNO = -1;
		if (e.getSource() == houseJLabel1) {
			roomNO = 0;
		}
		if (e.getSource() == houseJLabel2) {
			roomNO = 1;
		}
		if (e.getSource() == houseJLabel3) {
			roomNO = 2;
		}
		if (e.getSource() == houseJLabel4) {
			roomNO = 3;
		}
		if (canIn(roomNO)) {
			if (user.getMoney() >= 300) {
				// TipRunnable error = new
				// TipRunnable("image/tip/moneyPlus.png", 500, 350,
				// 300, 50, 3);
				// Thread myThread = new Thread(error);
				// myThread.start();
				user.setMoney(user.getMoney() - 300);
				enterBattleRoom(user.getID(), roomNO);
				mainFrame.dispose();
			} else {
				// 提示错误信息，钱不够
				TipRunnable error = new TipRunnable(
						"image/tip/moneyNotEnough.png", 500, 350, 300, 50, 2);
				Thread myThread = new Thread(error);
				myThread.start();
			}
		}
		
		if(chooseTip!=null){
			chooseTip.dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == houseJLabel1 || e.getSource() == houseJLabel2
				|| e.getSource() == houseJLabel3
				|| e.getSource() == houseJLabel4) {
			chooseTip = new MySplashScreen(
					"image/tip/chooseTip.png");
			chooseTip.setLocation(500, 160);
			chooseTip.setSize(300, 87);
			chooseTip.setVisible(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		chooseTip.dispose();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void DoSomething() {
		requestOnlineUser();
		requestRoom();
		System.out.println("退出大厅请输入Quit");
		System.out.println("进入房间请直接输入房间号（如： 0）");
		/*
		 * String type=IOHelper.inputFromConsole("请输入行为(Quit,房间号): ");
		 * if(type.equals("Quit")){ gameHallController.Quit(user.getID());
		 * }else{ int roomNO=Integer.parseInt(type);
		 * enterBattleRoom(user.getID(),roomNO); }
		 */
	}

	private void enterBattleRoom(String userID, int roomNO) {
		gameHallController.enterBattleRoom(userID, roomNO);
		System.out.println("Enter BattleRoom");
	}

	private void requestOnlineUser() {
		gameHallController.requestUserList();
	}

	private void requestRoom() {
		gameHallController.requestBattleRoomList();
	}

	private void Quit(String userID) {
		gameHallController.Quit(userID);
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Battle Game Hall receive message: " + message);
		String[] info = message.split(" ");

		if (info[1].equals(Service.UserLogin.toString())) {
			// ActiveUser player = new
			// ActiveUser(info[2],UserState.LEISURE,Integer.parseInt(info[3]),Integer.parseInt(info[4]));
			// userList.add(player);
		} else if (info[1].equals(Service.UserQuit.toString())) {
			for (ActiveUser player : userList) {
				if (player.getUserID().equals(info[2])) {
					userList.remove(player);
					break;
				}
			}
		} else if (info[1].equals(Service.UserStateChange.toString())) {
			for (ActiveUser player : userList) {
				if (player.getUserID().equals(info[2])) {
					player.setUserState(Enum.valueOf(UserState.class, info[3]));
					break;
				}
			}
		}

		System.out.println("Current Online Player: " + userList.size());
	}

	@SuppressWarnings("static-access")
	@Override
	public void setUserList(ArrayList<ActiveUser> userList) {
		// TODO Auto-generated method stub
		this.userList = userList;
		System.out.println("Current Online Player: " + userList.size());
	}

	@Override
	public void setRoomList(BattleRoom[] roomList) {
		// TODO Auto-generated method stub
		this.roomList = roomList;
		System.out.println("Coop_Room: ");
		for (int i = 0; i < roomList.length; i++) {
			System.out.println("Coop_Room NO. " + roomList[i].getRoomNO() + " "
					+ roomList[i].getState());
			changeRoomState(roomList[i].getRoomNO(), roomList[i].getState());
		}
	}

	@Override
	public UserVO getUser() {
		// TODO Auto-generated method stub
		return user;
	}

	@Override
	public ArrayList<ActiveUser> getBattleUserList() {
		// TODO Auto-generated method stub
		return userList;
	}

}
