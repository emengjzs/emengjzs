package view.ui.login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import controller.netlistenerbl.NetUserListenerBL;
import model.netbl.NetUserBL;
import model.netservice.NetUserBLService;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.Music;
import view.ui.myComponent.TipRunnable;
import view.uiservice.LoginService;

public class Login implements ActionListener, LoginService {
	// static Music m = new Music("music/login.mp3", true);
	JFrame mainFrame;
	JFrame registerFrame;
	JFrame aboutFrame;
	private int width = 1100;
	private int height = 700;

	JTextField nameText;
	JTextField passwordText;
	IconButton loginButton;
	IconButton registerButton;
	IconButton quitButton;
	IconButton aboutButton;

	// 注册界面上的按钮:
	IconButton registerShutButton;

	static NetUserBLService userController;

	// Runnable tipLabel = new MyRunnable();

	int xOld = 0;
	int yOld = 0;
	JPanel loginPanel;
	Point login_po;
	
	JPanel logoPanel;

	public static void main(String[] args) {
		new Login();
	}

	@SuppressWarnings("static-access")
	public Login() {
//		userController = NetUserBL.getUserController();
		mainFrame = new JFrame();
		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);// 去除窗口边框

		initFrame();
		mainFrame.repaint();

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
		mainFrame.getRootPane().setDefaultButton(loginButton);
		
		loginPanel.setLocation(335,0);
		logoPanel.setVisible(false);
		Music music = new Music("music/login.mp3", false);
		music.play();
		for(int i=0;i<23;i++){
			loginPanel.setLocation(335,loginPanel.getLocation().y+i);
			try {
				new Thread().sleep(20);
			} catch (InterruptedException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
		}
		logoPanel.setVisible(true);
	}
	
	public void init(){
		userController = NetUserBL.getUserController();
	}

	@SuppressWarnings("serial")
	private void initFrame() {
		JPanel backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				ImageIcon backgroundImage = new ImageIcon(
						"image/login/loginBackground.png");
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		mainFrame.setContentPane(backgroundPanel);

		logoPanel = new JPanel() {
			ImageIcon logoImage = new ImageIcon("image/login/logo.png");

			public void paintComponent(Graphics g) {
				g.drawImage(logoImage.getImage(), 0, 0, 200, 200, null);
			}
		};
		logoPanel.setOpaque(false);
		logoPanel.setLocation(460, 31);
		logoPanel.setSize(200, 200);
		logoPanel.setLayout(null);
		backgroundPanel.add(logoPanel);

		loginPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				ImageIcon loginLabelImage = new ImageIcon(
						"image/login/loginLabel.png");
				g.drawImage(loginLabelImage.getImage(), 0, 0, null);
			}
		};

		loginPanel.setOpaque(false);
		loginPanel.setSize(430, 380);
		loginPanel.setLocation(335, 250);
		loginPanel.setLayout(null);
		login_po = loginPanel.getLocation();
		backgroundPanel.add(loginPanel);

		nameText = new JTextField();
		nameText.setHorizontalAlignment(SwingConstants.CENTER);
		nameText.setBorder(null);// 去除边框
		nameText.setOpaque(false);// 将text背景设为透明
		nameText.setForeground(new Color(210, 105, 30));
		nameText.setFont(new Font("方正舒体", Font.BOLD, 24));
		nameText.setSize(210, 50);
		nameText.setLocation(150, 60);
//		nameText.enableInputMethods(false);
		loginPanel.add(nameText);

		passwordText = new JPasswordField();
		passwordText.setHorizontalAlignment(SwingConstants.CENTER);
		passwordText.setBorder(null);// 去除边框
		passwordText.setOpaque(false);// 将text背景设为透明
		passwordText.setForeground(new Color(210, 105, 30));
		passwordText.setFont(new Font("方正舒体", Font.BOLD, 24));
		passwordText.setSize(210, 50);
		passwordText.setLocation(150, 152);
		loginPanel.add(passwordText);

		loginButton = new IconButton("image/login/loginButton1.png",
				"image/login/loginButton2.png", "image/login/loginButton3.png");
		loginButton.addActionListener(this);
		loginButton.setLocation(125, 250);// 335, 250
		// loginButton.setLocation(460,500);
		loginButton.setSize(200, 60);
		loginPanel.add(loginButton);

		registerButton = new IconButton("image/login/registerButton1.png",
				"image/login/registerButton2.png",
				"image/login/registerButton3.png");
		registerButton.addActionListener(this);
		registerButton.setLocation(890, 480);
		registerButton.setSize(200, 50);
		backgroundPanel.add(registerButton);

		aboutButton = new IconButton("image/login/aboutButton1.png",
				"image/login/aboutButton2.png", "image/login/aboutButton3.png");
		aboutButton.addActionListener(this);
		aboutButton.setLocation(890, 534);
		aboutButton.setSize(200, 50);
		backgroundPanel.add(aboutButton);

		quitButton = new IconButton("image/login/quitButton1.png",
				"image/login/quitButton2.png", "image/login/quitButton3.png");
		quitButton.addActionListener(this);
		quitButton.setLocation(890, 610);
		quitButton.setSize(200, 50);
		backgroundPanel.add(quitButton);

//		 Toolkit tk = Toolkit.getDefaultToolkit();
//		 ImageIcon cursorImage = new ImageIcon("image/cursor.png");
//		 Cursor cursor = tk.createCustomCursor(cursorImage.getImage(),new
//		 Point(10,20),"stick");
//		 mainFrame.setCursor(cursor);
	}

	class Register implements ActionListener {
		private int width = 600;
		private int height = 400;

		@SuppressWarnings("serial")
		public Register() {
			registerFrame = new JFrame();
			registerFrame.setSize(width, height);
			registerFrame.setLocation(360, 200);
			registerFrame.setUndecorated(true);// 去除窗口边框
			registerFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = registerFrame.getContentPane();

			JPanel registerackgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					ImageIcon backgroundImage = new ImageIcon(
							"image/login/registerBackground.png");
					g.drawImage(backgroundImage.getImage(), 0, 0, null);
				}
			};
			registerackgroundPanel.setLayout(null);
			registerackgroundPanel.setSize(width, height);
			contentPane.add(registerackgroundPanel);
			registerackgroundPanel.setOpaque(false);

			registerShutButton = new IconButton("image/login/shut1.png",
					"image/login/shut2.png", "image/login/shut3.png");
			registerShutButton.addActionListener(this);
			registerShutButton.setLocation(477, 36);
			registerShutButton.setSize(61, 57);
			registerackgroundPanel.add(registerShutButton);

			RegisterPanel registerPanel = new RegisterPanel();
			NetUserListenerBL roomController = NetUserListenerBL
					.getUserControllerListener();
			roomController.setRegisterController(registerPanel);

			registerPanel.setSize(600, 400);
			registerackgroundPanel.add(registerPanel);

			registerFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == registerShutButton) {
				mainFrame.setEnabled(true);
				registerFrame.dispose();
			}

		}

	}

	class About implements ActionListener {
		private int width = 600;
		private int height = 400;

		@SuppressWarnings("serial")
		public About() {
			aboutFrame = new JFrame();
			aboutFrame.setSize(width, height);
			aboutFrame.setLocation(360, 200);
			aboutFrame.setUndecorated(true);// 去除窗口边框
			aboutFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = aboutFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/login/aboutUs.png");
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
			shutButton.setLocation(488, 43);
			shutButton.setSize(50, 50);
			backgroundPanel.add(shutButton);

			aboutFrame.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			mainFrame.setEnabled(true);
			aboutFrame.dispose();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginButton) {
			System.out.println("登录");
			String name = nameText.getText();
			String password = passwordText.getText();

			if (name.length() == 0 || password.length() == 0) {
				TipRunnable fail = new TipRunnable("image/tip/blank.png", 615,
						600, 200, 30, 2);
				Thread myThread = new Thread(fail);
				myThread.start();
			} else {
				// 将信息向下传
				userController.Login(name, password);
				System.out.println(name);
				System.out.println(password);
			}
		}
		if (e.getSource() == registerButton) {
			new Register();
			mainFrame.setEnabled(false);
		}
		if (e.getSource() == quitButton) {
			System.exit(0);
		}
		if (e.getSource() == aboutButton) {
			new About();
			mainFrame.setEnabled(false);
		}

	}

	@Override
	public void closeLoginFrame() {

		Music music = new Music("music/login_success.mp3", false);
		music.play();

		mainFrame.dispose();
	}

	@Override
	public void receiveMessage(String message) {
		System.out.println("Login UI Receive: " + message);
		nameOrPassError();
	}

	public void nameOrPassError() {// 闪屏提示登录信息错误
		TipRunnable fail = new TipRunnable("image/tip/loginError.png", 560,
				600, 300, 50, 2);
		Thread myThread = new Thread(fail);
		myThread.start();
		shock();
	}
	
	@SuppressWarnings("static-access")
	public void shock() {
		Random rb = new Random();
		
		for (int i = 0; i < 50; i++) {
			int _x = rb.nextInt(10);
			int _y = rb.nextInt(10);
			loginPanel.setLocation(login_po.x+_x,login_po.y+_y);
			try {
				new Thread().sleep(10);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		
		loginPanel.setLocation(login_po);
		
	}
	
}
