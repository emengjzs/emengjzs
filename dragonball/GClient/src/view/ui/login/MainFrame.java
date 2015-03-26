package view.ui.login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import common.Service;
import controller.netlistenerbl.NetStatisListenerBL;
import model.netbl.NetUserBL;
import model.netservice.NetUserBLService;
import model.vo.UserVO;
import setup.GameClient;
import view.ui.cooperation.Coop_mainFrame;
import view.ui.fight.Fight_mainFrame;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.Music;
import view.ui.solo.Solo_mainFrame;
import view.uiservice.MainService;

public class MainFrame implements ActionListener, MainService {
	static NetUserBLService userController;
	ArrayList<Integer> starList = new ArrayList<Integer>();
	ArrayList<Integer> ballList = new ArrayList<Integer>();
	String finalStageState;
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	UserVO user;

	IconButton houseButton;
	IconButton skyButton;
	IconButton starButton;

	IconButton gameCenterButton;
	IconButton setGameButton;
	IconButton manageAccountButton;
	IconButton gameHelpButton;
	IconButton quitGameButton;

	JFrame gameCenterFrame;
	JFrame setGameFrame;
	JFrame manageAccountFrame;
	JFrame gameHelpFrame;
	JFrame changePasswordFrame;

	int xOld = 0;
	int yOld = 0;
	// public static void main(String[] args) {
	// new MainFrame();
	// }

	public MainFrame(UserVO user) {
		// public MainFrame(){
		userController = NetUserBL.getUserController();
		this.user = user;
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
		
		mainFrame.setVisible(true);

		if (GameClient.MusicOn) {
			if (GameClient.music == null) {
				GameClient.music = new Music("music/wordBGM.mp3", true);
				GameClient.music.play();
			}
		}
		
		mainFrame.setBackground(new Color(0,0,0,0));

	}

	public void init() {
		userController.getStage(user.getID());
		userController.getUserBall(user.getID());
	}

	@SuppressWarnings("serial")
	private void initFrame() {
		Container contentPane = mainFrame.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"image/login/mainBackground.png");
		JPanel backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);

		houseButton = new IconButton("image/login/house1.png",
				"image/login/house2.png", "image/login/house3.png");
		houseButton.addActionListener(this);
		houseButton.setLocation(70, 550);
		houseButton.setSize(230, 121);
		backgroundPanel.add(houseButton);

		skyButton = new IconButton("image/login/sky1.png",
				"image/login/sky2.png", "image/login/sky3.png");
		skyButton.addActionListener(this);
		skyButton.setLocation(190, 190);
		skyButton.setSize(230, 121);
		backgroundPanel.add(skyButton);

		starButton = new IconButton("image/login/star1.png",
				"image/login/star2.png", "image/login/star3.png");
		starButton.addActionListener(this);
		starButton.setLocation(633, 413);
		starButton.setSize(230, 121);
		backgroundPanel.add(starButton);

		gameCenterButton = new IconButton("image/login/gameCenter1.png",
				"image/login/gameCenter2.png", "image/login/gameCenter3.png");
		gameCenterButton.addActionListener(this);
		gameCenterButton.setLocation(850, 20);
		gameCenterButton.setSize(240, 78);
		backgroundPanel.add(gameCenterButton);

		setGameButton = new IconButton("image/login/setGame1.png",
				"image/login/setGame2.png", "image/login/setGame3.png");
		setGameButton.addActionListener(this);
		setGameButton.setLocation(850, 100);
		setGameButton.setSize(240, 78);
		backgroundPanel.add(setGameButton);

		manageAccountButton = new IconButton("image/login/manageAccount1.png",
				"image/login/manageAccount2.png",
				"image/login/manageAccount3.png");
		manageAccountButton.addActionListener(this);
		manageAccountButton.setLocation(850, 180);
		manageAccountButton.setSize(240, 78);
		backgroundPanel.add(manageAccountButton);

		gameHelpButton = new IconButton("image/login/gameHelp1.png",
				"image/login/gameHelp2.png", "image/login/gameHelp3.png");
		gameHelpButton.addActionListener(this);
		gameHelpButton.setLocation(850, 260);
		gameHelpButton.setSize(240, 78);
		backgroundPanel.add(gameHelpButton);

		quitGameButton = new IconButton("image/login/quitGame1.png",
				"image/login/quitGame2.png", "image/login/quitGame3.png");
		quitGameButton.addActionListener(this);
		quitGameButton.setLocation(850, 600);
		quitGameButton.setSize(240, 78);
		backgroundPanel.add(quitGameButton);

	}

	class GameCenter implements ActionListener {
		private int width = 700;
		private int height = 500;

		@SuppressWarnings("serial")
		public GameCenter() {
			gameCenterFrame = new JFrame();
			gameCenterFrame.setSize(width, height);
			gameCenterFrame.setLocation(360, 100);
			gameCenterFrame.setUndecorated(true);// 去除窗口边框
			gameCenterFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = gameCenterFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/login/background.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width,
							height, null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);
			backgroundPanel.setOpaque(false);

			IconButton shutButton = new IconButton("image/login/shut1.png",
					"image/login/shut2.png", "image/login/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(575, 32);
			shutButton.setSize(72, 67);
			backgroundPanel.add(shutButton);

			GameCenterPanel gameCenterPanel = new GameCenterPanel(user);
			gameCenterPanel.setSize(600, 400);
			backgroundPanel.add(gameCenterPanel);

			gameCenterFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.setEnabled(true);
			gameCenterFrame.dispose();
		}

	}

	class SetGame implements ActionListener {
		private int width = 700;
		private int height = 500;
		UserVO user;

		@SuppressWarnings("serial")
		public SetGame(UserVO user) {
			this.user = user;

			setGameFrame = new JFrame();
			setGameFrame.setSize(width, height);
			setGameFrame.setLocation(360, 100);
			setGameFrame.setUndecorated(true);// 去除窗口边框
			setGameFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = setGameFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/login/background.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width,
							height, null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);
			backgroundPanel.setOpaque(false);

			SetGamePanel setGamePanel = new SetGamePanel(user);
			setGamePanel.setSize(700, 500);
			backgroundPanel.add(setGamePanel);

			IconButton shutButton = new IconButton("image/login/shut1.png",
					"image/login/shut2.png", "image/login/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(554, 44);
			shutButton.setSize(50, 50);
			backgroundPanel.add(shutButton);

			setGameFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			mainFrame.setEnabled(true);
			setGameFrame.dispose();
		}

	}

	class ManageAccount implements ActionListener {
		private int width = 700;
		private int height = 500;
		UserVO user;

		IconButton shutButton;
		IconButton changePasswordButton;

		@SuppressWarnings("serial")
		public ManageAccount(UserVO user) {
			this.user = user;

			manageAccountFrame = new JFrame();
			manageAccountFrame.setSize(width, height);
			manageAccountFrame.setLocation(360, 100);
			manageAccountFrame.setUndecorated(true);// 去除窗口边框
			manageAccountFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = manageAccountFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/login/manageAccountBackground.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width,
							height, null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);
			backgroundPanel.setOpaque(false);

			ManageAccountPanel manageAccountPanel = new ManageAccountPanel(user);
			manageAccountPanel.setSize(700, 500);
			backgroundPanel.add(manageAccountPanel);

			shutButton = new IconButton("image/login/shut1.png",
					"image/login/shut2.png", "image/login/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(575, 32);
			shutButton.setSize(72, 67);
			backgroundPanel.add(shutButton);

			changePasswordButton = new IconButton(
					"image/login/changePassword1.png",
					"image/login/changePassword2.png",
					"image/login/changePassword3.png");
			changePasswordButton.addActionListener(this);
			changePasswordButton.setLocation(102, 197);
			changePasswordButton.setSize(150, 50);
			backgroundPanel.add(changePasswordButton);

			manageAccountFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == shutButton) {
				mainFrame.setEnabled(true);
				manageAccountFrame.dispose();
			}
			if (e.getSource() == changePasswordButton) {
				new ChangePassword(user);
				manageAccountFrame.setEnabled(false);
			}
		}

	}

	class GameHelp implements ActionListener {
		private int width = 700;
		private int height = 500;

		@SuppressWarnings("serial")
		public GameHelp() {
			gameHelpFrame = new JFrame();
			gameHelpFrame.setSize(width, height);
			gameHelpFrame.setLocation(360, 100);
			gameHelpFrame.setUndecorated(true);// 去除窗口边框
			gameHelpFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = gameHelpFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/login/background.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width,
							height, null);
				}
			};
			backgroundPanel.setForeground(new Color(210, 105, 30));
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);
			backgroundPanel.setOpaque(false);

			GameHelpPanel gameHelpPanel = new GameHelpPanel();
			gameHelpPanel.setSize(700, 500);
			backgroundPanel.add(gameHelpPanel);

			IconButton shutButton = new IconButton("image/login/shut1.png",
					"image/login/shut2.png", "image/login/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(575, 32);
			shutButton.setSize(72, 67);
			backgroundPanel.add(shutButton);

			gameHelpFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent arg0) {
			mainFrame.setEnabled(true);
			gameHelpFrame.dispose();
		}

	}

	class ChangePassword implements ActionListener {
		private int width = 460;
		private int height = 280;
		UserVO user;

		@SuppressWarnings("serial")
		public ChangePassword(UserVO user) {
			this.user = user;
			changePasswordFrame = new JFrame();
			changePasswordFrame.setSize(width, height);
			changePasswordFrame.setLocation(480, 250);
			changePasswordFrame.setUndecorated(true);// 去除窗口边框
			changePasswordFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = changePasswordFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/login/changePasswordBackground.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width,
							height, null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);
			backgroundPanel.setOpaque(false);

			ChangePasswordPanel changePasswordPanel = new ChangePasswordPanel(
					user);
			changePasswordPanel.setSize(width, height);
			backgroundPanel.add(changePasswordPanel);

			IconButton shutButton = new IconButton("image/login/shut1.png",
					"image/login/shut2.png", "image/login/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(378, 10);
			shutButton.setSize(72, 67);
			backgroundPanel.add(shutButton);

			changePasswordFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent arg0) {
			manageAccountFrame.setEnabled(true);
			changePasswordFrame.dispose();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == houseButton) {
			Solo_mainFrame main = new Solo_mainFrame(user);
			NetStatisListenerBL statisController = NetStatisListenerBL
					.getStatisControllerListener();
			statisController.setSingleController(main);
			main.setVisible(true);
			mainFrame.dispose();

//			Runnable toSolo = new ToSoloRunabel();
//			Thread myThread = new Thread(toSolo);
//			myThread.start();

		}
		if (e.getSource() == skyButton) {
			Coop_mainFrame main = new Coop_mainFrame(user);
			NetStatisListenerBL statisController = NetStatisListenerBL
					.getStatisControllerListener();
			statisController.setCoopController(main);

			// Runnable toCoop = new ToCoopRunnable();

			mainFrame.dispose();
		}
		if (e.getSource() == starButton) {
			Fight_mainFrame main = new Fight_mainFrame(user);
			main.Request();
			NetStatisListenerBL statisController = NetStatisListenerBL
					.getStatisControllerListener();
			statisController.setBattleController(main);
			mainFrame.dispose();
		}

		if (e.getSource() == gameCenterButton) {
			new GameCenter();
			mainFrame.setEnabled(false);
		}
		if (e.getSource() == setGameButton) {
			new SetGame(user);
			mainFrame.setEnabled(false);
		}
		if (e.getSource() == manageAccountButton) {
			new ManageAccount(user);
			mainFrame.setEnabled(false);
		}
		if (e.getSource() == gameHelpButton) {
			new GameHelp();
			mainFrame.setEnabled(false);
		}
		if (e.getSource() == quitGameButton) {
			System.exit(0);
		}

	}

	class ToSoloRunabel implements Runnable {

		public void run() {

			Solo_mainFrame main = new Solo_mainFrame(user);
			main.setVisible(false);
			NetStatisListenerBL statisController = NetStatisListenerBL
					.getStatisControllerListener();
			statisController.setSingleController(main);
			
			mainFrame.setEnabled(false);
			
			System.out.println("线程开始");
//			MySplashScreen window = new MySplashScreen("image/tip/loading.png");
//			window.setLocation(660, 445);
//			window.setSize(200, 30);
//			window.setVisible(true);
			for (int i = 0; i < 5; i++) {
				// panel_y = panel_y-10;
				// secondPanel.setLocation(panel_x, panel_y);

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
//			window.dispose();
			main.setVisible(true);
			mainFrame.dispose();

		}

	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		String[] info = message.split(" ");
		if (info[1].equals(Service.ReplyStageList.toString())) {
			int stage = Integer.parseInt(info[2]);
			user.setStage(stage);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>"+stage);
			finalStageState = info[3];
			String starInfo = info[4];

			starList = new ArrayList<Integer>();
			String[] singleStarInfo = starInfo.split("-");

			for (int i = 0; i < singleStarInfo.length; i++) {
				starList.add(Integer.parseInt(singleStarInfo[i]));
			}
			user.setStarList(starList);
		} else if(info[1].equals(Service.ReplyBallList.toString())){
			ballList=new ArrayList<Integer>();
			String ballString=info[2];
			String[] ballInfo=ballString.split("-");
			
			for(int i=0;i<ballInfo.length;i++){
				ballList.add(Integer.parseInt(ballInfo[i]));
			}
			user.setBallList(ballList);
		}
	}

}
