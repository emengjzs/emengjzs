package view.ui.solo;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.Service;
import controller.netlistenerbl.NetStatisListenerBL;
import model.netbl.NetSingleBL;
import model.netservice.NetSingleBLService;
import model.vo.UserVO;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.TipRunnable;
import view.uiservice.SingleService;

import java.awt.Font;
import java.awt.Color;

public class Solo_prepareGame implements ActionListener, MouseListener,
		SingleService {
	static NetSingleBLService singleController;
	UserVO user;
	JFrame mainFrame;
	int prop1;
	int prop2;
	int prop3;
	private int width = 1100;
	private int height = 700;

	IconButton beginButton;
	IconButton backButton;
	// IconButton backHomeButton;

	// 道具原始状态
	ImageIcon toolImage1_1 = new ImageIcon("image/solo/tool1_1.png");
	ImageIcon toolImage1_2 = new ImageIcon("image/solo/tool1_2.png");
	ImageIcon toolImage1_3 = new ImageIcon("image/solo/tool1_3.png");

	// 道具被选中后的状态
	ImageIcon toolImage2_1 = new ImageIcon("image/solo/tool2_1.png");
	ImageIcon toolImage2_2 = new ImageIcon("image/solo/tool2_2.png");
	ImageIcon toolImage2_3 = new ImageIcon("image/solo/tool2_3.png");

	JLabel toolLabel1;
	JLabel toolLabel2;
	JLabel toolLabel3;

	JLabel toolNum1;
	JLabel toolNum2;
	JLabel toolNum3;
	
	int xOld = 0;
	int yOld = 0;

	public static void main(String[] args) {
		// new Solo_prepareGame();
	}

	public Solo_prepareGame(UserVO user) {
		this.user = user;
		singleController = NetSingleBL.getSingleController();
		singleController.Enter(user.getID(), user.getCurrentStage());
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
		mainFrame.setBackground(new Color(0,0,0,0));
	}

	public void init() {
		singleController.RequestProp(user.getID());
	}

	@SuppressWarnings("serial")
	private void initFrame() {
		Container contentPane = mainFrame.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"image/solo/background.png");
		JPanel backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				// super.paint(g);
				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
						null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		// <<<<<<< .mine

		beginButton = new IconButton("image/solo/begin1.png",
				"image/solo/begin2.png", "image/solo/begin3.png");
		beginButton.addActionListener(this);
		beginButton.setLocation(520, 540);
		beginButton.setSize(110, 110);
		backgroundPanel.add(beginButton);
		// =======
		//
		// enterGameButton = new IconButton("image/solo/enterGame1.png",
		// "image/solo/enterGame2.png", "image/solo/enterGame3.png");
		// enterGameButton.addActionListener(this);
		// enterGameButton.setLocation(512, 560);
		// enterGameButton.setSize(120, 60);
		// backgroundPanel.add(enterGameButton);
		// >>>>>>> .r275

		backButton = new IconButton("image/solo/back1.png",
				"image/solo/back2.png", "image/solo/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(30, 580);
		backButton.setSize(170, 65);
		backgroundPanel.add(backButton);

		// 道具所在的panel
		JPanel toolPanel = new JPanel() {
			ImageIcon toolBackgroundImage = new ImageIcon(
					"image/solo/toolBackground.png");
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				// super.paint(g);
				toolBackgroundImage.setImage(toolBackgroundImage.getImage()
						.getScaledInstance(500, 300, Image.SCALE_DEFAULT));
				g.drawImage(toolBackgroundImage.getImage(), 0, 0, null);
			}
		};
		toolPanel.setLocation(150, 190);
		toolPanel.setSize(500, 300);
		toolPanel.setLayout(null);
		backgroundPanel.add(toolPanel);

		toolLabel1 = new JLabel(toolImage1_1);
		toolLabel1.addMouseListener(this);
		toolLabel1.setLocation(99, 65);
		toolLabel1.setSize(90, 90);
		toolPanel.add(toolLabel1);

		toolNum1 = new JLabel();
		toolNum1.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		toolNum1.setForeground(new Color(255, 0, 0));
		toolNum1.setLocation(159, 226);
		toolNum1.setSize(30, 30);
		toolPanel.add(toolNum1);

		JLabel toolDescription1 = new JLabel(new ImageIcon(
				"image/solo/toolDescription1.png"));
		toolDescription1.setLocation(99, 165);
		toolDescription1.setSize(90, 70);
		toolPanel.add(toolDescription1);

		JLabel left1 = new JLabel("剩余：");
		left1.setForeground(new Color(0, 0, 0));
		left1.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		left1.setLocation(99, 225);
		left1.setSize(57, 30);
		toolPanel.add(left1);

		toolLabel2 = new JLabel(toolImage1_2);
		toolLabel2.addMouseListener(this);
		toolLabel2.setLocation(214, 65);
		toolLabel2.setSize(90, 90);
		toolPanel.add(toolLabel2);

		toolNum2 = new JLabel();
		toolNum2.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		toolNum2.setForeground(new Color(255, 0, 0));
		toolNum2.setLocation(274, 226);
		toolNum2.setSize(30, 30);
		toolPanel.add(toolNum2);

		JLabel toolDescription2 = new JLabel(new ImageIcon(
				"image/solo/toolDescription2.png"));
		toolDescription2.setLocation(214, 165);
		toolDescription2.setSize(90, 70);
		toolPanel.add(toolDescription2);

		JLabel left2 = new JLabel("剩余：");
		left2.setForeground(new Color(0, 0, 0));
		left2.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		left2.setLocation(214, 225);
		left2.setSize(57, 30);
		toolPanel.add(left2);

		toolLabel3 = new JLabel(toolImage1_3);
		toolLabel3.addMouseListener(this);
		toolLabel3.setLocation(326, 65);
		toolLabel3.setSize(90, 90);
		toolPanel.add(toolLabel3);

		toolNum3 = new JLabel();
		toolNum3.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		toolNum3.setForeground(new Color(255, 0, 0));
		toolNum3.setLocation(386, 226);
		toolNum3.setSize(30, 30);
		toolPanel.add(toolNum3);

		JLabel toolDescription3 = new JLabel(new ImageIcon(
				"image/solo/toolDescription3.png"));
		toolDescription3.setLocation(326, 165);
		toolDescription3.setSize(90, 70);
		toolPanel.add(toolDescription3);

		JLabel left3 = new JLabel("剩余：");
		left3.setForeground(new Color(0, 0, 0));
		left3.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		left3.setLocation(326, 225);
		left3.setSize(57, 30);
		toolPanel.add(left3);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == beginButton) {
			System.out.println("Game------------Start");
			mainFrame.dispose();
			singleController.initGame(user.getID());

		}
		if (e.getSource() == backButton) {
			singleController.Leave(user.getID());
			Solo_mainFrame main = new Solo_mainFrame(user);
			NetStatisListenerBL statisController = NetStatisListenerBL
					.getStatisControllerListener();
			statisController.setSingleController(main);
			main.setVisible(true);
			mainFrame.dispose();
			// new Solo_mainFrame();
		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == toolLabel1) {
			int propNO = 1;
			if (prop1 > 1 || user.getMoney() >= 388) {
				if (toolLabel1.getIcon() == toolImage1_1) {// 加道具
					toolLabel1.setIcon(toolImage2_1);
					if(prop1>0){
						prop1--;
					}
					toolNum1.setText(prop1 + "");
					if(prop1==0){
						user.setMoney(user.getMoney()-388);
					}
					singleController.BuyProp(user.getID(), propNO);
				}
			}else{
				moneyNotEnough();
			}
		}
		if (e.getSource() == toolLabel2) {
			int propNO = 2;
			if (prop2 > 1 || user.getMoney() >= 488
					&& toolLabel2.getIcon() == toolImage1_2) {
				if (toolLabel2.getIcon() == toolImage1_2) {// 加道具
					toolLabel2.setIcon(toolImage2_2);
					if(prop2>0){
						prop2--;
					}
					toolNum2.setText(prop2 + "");
					if(prop2==0){
						user.setMoney(user.getMoney()-488);
					}
					singleController.BuyProp(user.getID(), propNO);
					
				}
			}else{
				moneyNotEnough();
			}
		}
		if (e.getSource() == toolLabel3) {
			int propNO = 3;
			if (prop3 > 1 || user.getMoney() >= 88
					&& toolLabel3.getIcon() == toolImage1_3) {
				if (toolLabel3.getIcon() == toolImage1_3) {// 加道具
					toolLabel3.setIcon(toolImage2_3);
					if(prop3>0){
						prop3--;
					}
					toolNum3.setText(prop3 + "");
					if(prop3==0){
						user.setMoney(user.getMoney()-88);
					}
					singleController.BuyProp(user.getID(), propNO);
				}
			}else{
				moneyNotEnough();
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		String[] info = message.split(" ");
		if (info[1].equals(Service.ReplySingleProp.toString())) {
			toolNum1.setText(info[2]);
			prop1 = Integer.parseInt(info[2]);
			toolNum2.setText(info[3]);
			prop2 = Integer.parseInt(info[3]);
			toolNum3.setText(info[4]);
			prop3 = Integer.parseInt(info[4]);
		}
	}

}
