package view.ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.netbl.NetUserBL;
import model.netservice.NetUserBLService;
import model.vo.UserVO;
import setup.GameClient;
import view.ui.gameui.Direction;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.Music;
import view.ui.myComponent.TipRunnable;

@SuppressWarnings("serial")
public class SetGamePanel extends JPanel implements ActionListener,MouseListener {
	// JFrame mainFrame;
	UserVO user;
	static NetUserBLService userController;
	// private int width = 700;
	// private int height = 500;

	// IconButton shutButton;
	IconButton confirmButton;

	ImageIcon yesImage = new ImageIcon("image/login/yes.png");
	ImageIcon noImage = new ImageIcon("image/login/no.png");

	JLabel upToDownLabel;
	JLabel leftToRightLabel;

//	JLabel volumnOnLabel;
//	JLabel volumnOffLabel;

	JLabel effectOnLabel;
	JLabel effectOffLabel;
	
	boolean modelFlag = true;
//	boolean volumnFlag = true;
	boolean effectFlag = true;

	//Runnable tip = new setGameRunabel();

	public static void main(String[] args) {
		// new SetGamePanel();
	}

	public SetGamePanel(UserVO user) {
		// public SetGamePanel(){
		userController = NetUserBL.getUserController();
		this.user = user;
		// mainFrame = new JFrame();
		// mainFrame.setSize(width, height);
		// mainFrame.setLocation(360, 100);
		// mainFrame.setUndecorated(true);//去除窗口边框
		// mainFrame.setBackground(new Color(0,0,0,0)); //将窗口变透明

		this.setOpaque(false);
		this.setLayout(null);
		initFrame();

		// mainFrame.setVisible(true);
	}

	private void initFrame() {
		// Container contentPane = mainFrame.getContentPane();
		//
		// final ImageIcon backgroundImage = new
		// ImageIcon("image/login/background.png");
		// JPanel backgroundPanel = new JPanel(){
		// private static final long serialVersionUID = 1L;
		// public void paintComponent(Graphics g){
		// backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width,
		// height, Image.SCALE_DEFAULT));
		// g.drawImage(backgroundImage.getImage(), 0, 0, null);
		// }
		// };
		// backgroundPanel.setLayout(null);
		// backgroundPanel.setSize(width, height);
		// contentPane.add(backgroundPanel);
		// backgroundPanel.setOpaque(false);

		// shutButton = new
		// IconButton("image/login/shut1.png","image/login/shut2.png","image/login/shut3.png");
		// shutButton.addActionListener(this);
		// shutButton.setLocation(554, 44);
		// shutButton.setSize(50, 50);
		// backgroundPanel.add(shutButton);

		confirmButton = new IconButton("image/login/confirm1.png",
				"image/login/confirm2.png", "image/login/confirm3.png");
		confirmButton.addActionListener(this);
		confirmButton.setLocation(325, 382);
		confirmButton.setSize(100, 80);
		// backgroundPanel.add(confirmButton);
		this.add(confirmButton);

		// 下落模式（提示）
		JLabel modelLabel = new JLabel(new ImageIcon("image/login/model.png"));
		modelLabel.setLocation(180, 110);
		modelLabel.setSize(110, 60);
		// backgroundPanel.add(modelLabel);
		this.add(modelLabel);

		// 从上向下（提示）
		JLabel upToDown = new JLabel(new ImageIcon("image/login/upToDown.png"));
		upToDown.setLocation(330, 120);
		upToDown.setSize(70, 40);
		// backgroundPanel.add(upToDown);
		this.add(upToDown);

		// 从左向右（提示）
		JLabel leftToRight = new JLabel(new ImageIcon(
				"image/login/leftToRight.png"));
		leftToRight.setLocation(469, 120);
		leftToRight.setSize(70, 40);
		// backgroundPanel.add(leftToRight);
		this.add(leftToRight);

		// 从上向下（单选按钮）
		upToDownLabel = new JLabel();
		upToDownLabel.addMouseListener(this);
		upToDownLabel.setLocation(419, 120);
		upToDownLabel.setSize(40, 40);
		// backgroundPanel.add(upToDownLabel);
		this.add(upToDownLabel);

		// 从左向右（单选按钮）
		leftToRightLabel = new JLabel();
		leftToRightLabel.addMouseListener(this);
		leftToRightLabel.setLocation(554, 120);
		leftToRightLabel.setSize(40, 40);
		// backgroundPanel.add(leftToRightLabel);
		this.add(leftToRightLabel);

//		// 音量（提示）
//		JLabel volumnLabel = new JLabel(new ImageIcon("image/login/volumn.png"));
//		volumnLabel.setLocation(180, 180);
//		volumnLabel.setSize(110, 60);
//		// backgroundPanel.add(volumnLabel);
//		this.add(volumnLabel);
//
//		// 开（提示）
//		JLabel volumnOn = new JLabel(new ImageIcon("image/login/on.png"));
//		volumnOn.setLocation(349, 190);
//		volumnOn.setSize(40, 40);
//		// backgroundPanel.add(volumnOn);
//		this.add(volumnOn);
//
//		// 关（提示）
//		JLabel volumnOff = new JLabel(new ImageIcon("image/login/off.png"));
//		volumnOff.setLocation(491, 190);
//		volumnOff.setSize(40, 40);
//		// backgroundPanel.add(volumnOff);
//		this.add(volumnOff);
//
//		// 开（单选按钮）
//		volumnOnLabel = new JLabel();
//		volumnOnLabel.addMouseListener(this);
//		volumnOnLabel.setLocation(419, 190);
//		volumnOnLabel.setSize(40, 40);
//		// backgroundPanel.add(volumnOnLabel);
//		this.add(volumnOnLabel);
//
//		// 关（单选按钮）
//		volumnOffLabel = new JLabel();
//		volumnOffLabel.addMouseListener(this);
//		volumnOffLabel.setLocation(554, 190);
//		volumnOffLabel.setSize(40, 40);
//		// backgroundPanel.add(volumnOffLabel);
//		this.add(volumnOffLabel);

		// 音效（提示）
		JLabel soundEffectLabel = new JLabel(new ImageIcon(
				"image/login/soundEffect.png"));
		soundEffectLabel.setLocation(180, 250);
		soundEffectLabel.setSize(110, 60);
		// backgroundPanel.add(soundEffectLabel);
		this.add(soundEffectLabel);

		// 开（提示）
		JLabel effectOn = new JLabel(new ImageIcon("image/login/on.png"));
		effectOn.setLocation(349, 261);
		effectOn.setSize(40, 40);
		// backgroundPanel.add(effectOn);
		this.add(effectOn);

		// 关（提示）
		JLabel effectOff = new JLabel(new ImageIcon("image/login/off.png"));
		effectOff.setLocation(491, 261);
		effectOff.setSize(40, 40);
		// backgroundPanel.add(effectOff);
		this.add(effectOff);

		// 开（单选按钮）
		effectOnLabel = new JLabel();
		effectOnLabel.addMouseListener(this);
		effectOnLabel.setLocation(419, 261);
		effectOnLabel.setSize(40, 40);
		// backgroundPanel.add(effectOnLabel);
		this.add(effectOnLabel);

		// 关（单选按钮）
		effectOffLabel = new JLabel();
		effectOffLabel.addMouseListener(this);
		effectOffLabel.setLocation(554, 261);
		effectOffLabel.setSize(40, 40);
		// backgroundPanel.add(effectOffLabel);
		this.add(effectOffLabel);
		
		if(user.getDirection()==1){
			modelFlag=true;
		}else if(user.getDirection()==3){
			modelFlag=false;
		}
		
		if(GameClient.MusicOn){
			effectFlag = true;
		}else{
			effectFlag = false;
		}
		
		setState();

	}
	
	private void setState(){
		if(modelFlag){//从上到下
			upToDownLabel.setIcon(yesImage);
			leftToRightLabel.setIcon(noImage);
		}else{
			upToDownLabel.setIcon(noImage);
			leftToRightLabel.setIcon(yesImage);
		}

//		if(volumnFlag){
//			volumnOnLabel.setIcon(yesImage);
//			volumnOffLabel.setIcon(noImage);
//		}else{
//			volumnOnLabel.setIcon(noImage);
//			volumnOffLabel.setIcon(yesImage);
//		}
		
		if(effectFlag){
			effectOnLabel.setIcon(yesImage);
			effectOffLabel.setIcon(noImage);
		}else{
			effectOnLabel.setIcon(noImage);
			effectOffLabel.setIcon(yesImage);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// if(e.getSource()==shutButton){
		// mainFrame.dispose();
		// }
		if (e.getSource() == confirmButton) {

//			Thread myThread = new Thread(tip);
//			myThread.start();
			//判断玩家选中的3个按钮
			if(upToDownLabel.getIcon()==yesImage){
				modelFlag = true;
				user.setDirection(1);
				userController.ChangeDirection(user.getID(),
						Direction.DOWN.ordinal());
			}else{
				modelFlag = false;
				user.setDirection(3);
				userController.ChangeDirection(user.getID(),
						Direction.RIGHT.ordinal());
			}
			
//			if(volumnOnLabel.getIcon()==yesImage){
//				volumnFlag = true;
//			}else{
//				volumnFlag = false;
//			}
			
			if(effectOnLabel.getIcon()==yesImage){
				effectFlag = true;
				GameClient.MusicOn = true;
				if(GameClient.music == null){
					if(GameClient.music == null){
						GameClient.music = new Music("music/wordBGM.mp3",true);
						GameClient.music.play();
					}
				}
			}else{
				effectFlag = false;
				GameClient.MusicOn = false;
				if(GameClient.music != null){
					GameClient.music.stop();
					GameClient.music = null;
				}
			}
			
			//返回3个boolean值
			System.out.println(modelFlag);
//			System.out.println(volumnFlag);
			System.out.println(effectFlag);
			
			//提示操作成功
			TipRunnable fail = new TipRunnable("image/tip/changeSucceed.png",630, 445,200, 30,2);
			Thread myThread = new Thread(fail);
			myThread.start();
		}

	}
	
	public UserVO getUser(){
		return user;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == upToDownLabel) {
			upToDownLabel.setIcon(yesImage);
			leftToRightLabel.setIcon(noImage);
		}
		if (e.getSource() == leftToRightLabel) {
			leftToRightLabel.setIcon(yesImage);
			upToDownLabel.setIcon(noImage);
		}

//		if (e.getSource() == volumnOnLabel) {
//			volumnOnLabel.setIcon(yesImage);
//			volumnOffLabel.setIcon(noImage);
//		}
//		if (e.getSource() == volumnOffLabel) {
//			volumnOffLabel.setIcon(yesImage);
//			volumnOnLabel.setIcon(noImage);
//		}

		if (e.getSource() == effectOnLabel) {
			effectOnLabel.setIcon(yesImage);
			effectOffLabel.setIcon(noImage);
		}
		if (e.getSource() == effectOffLabel) {
			effectOffLabel.setIcon(yesImage);
			effectOnLabel.setIcon(noImage);
		}

	}

//	class setGameRunabel implements Runnable {
//
//		@Override
//		public void run() {
//			MySplashScreen window = new MySplashScreen(
//					"image/login/setGame.png");
//			window.setLocation(630, 430);
//			window.setSize(200, 30);
//			window.setVisible(true);
//
//			int i = 0;
//			while (true) {
//				i++;
//				if (i == 2) {
//					window.dispose();
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}

	@Override
	public void mouseEntered(MouseEvent e) {
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

}
