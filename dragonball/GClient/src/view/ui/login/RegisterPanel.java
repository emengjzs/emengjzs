package view.ui.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import common.ResultMessage;
import model.netbl.NetUserBL;
import model.netservice.NetUserBLService;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.TipRunnable;
import view.uiservice.RegisterService;

@SuppressWarnings("serial")
public class RegisterPanel extends JPanel implements ActionListener, RegisterService{
	//JFrame mainFrame;
	//private int width = 600;
	//private int height = 400;
	JTextField nameText;
	JPasswordField passwordText;
	JPasswordField confirmPasswordText;
	static NetUserBLService userController;
	
	//IconButton shutButton;
	IconButton confirmButton;
	
	//提示线程
//	Runnable succeedTip = new SuccessRunable();
//	Runnable failTip = new FailRunnable();
	String succeedPath = "image/tip/succeed.png";//注册成功图片的路径
	String hasRegisteredPath = "image/tip/hasRegistered.png";//账户已被注册
	String passwordNotConsistentPath = "image/tip/passwordNotConsistent.png";//两次密码不一致
	String blankPath = "image/tip/blank.png";//用信息未被填写
	
//	public static void main(String[] args) {
//		new RegisterPanel();
//	}
	
	public RegisterPanel(){
		userController=NetUserBL.getUserController();
//		mainFrame = new JFrame();
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 200);
//		mainFrame.setUndecorated(true);//去除窗口边框
//		mainFrame.setBackground(new Color(0,0,0,0)); //将窗口变透明
		
		//this.setBackground(new Color(0,0,0,0));
		this.setOpaque(false);
		this.setLayout(null);
		initFrame();
		
		//mainFrame.setVisible(true);
	}
	
	public void initFrame(){
//		Container contentPane = mainFrame.getContentPane();
//		
//		final ImageIcon backgroundImage = new ImageIcon("image/login/registerBackground.png");
//		JPanel backgroundPanel = new JPanel(){
//			private static final long serialVersionUID = 1L;
//			public void paintComponent(Graphics g){
//				//super.paint(g);
//				backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
//				g.drawImage(backgroundImage.getImage(), 0, 0, null);
//			}
//		};
//		backgroundPanel.setLayout(null);
//		backgroundPanel.setSize(width, height);
//		contentPane.add(backgroundPanel);
//		backgroundPanel.setOpaque(false);
		
//		shutButton = new IconButton("image/login/shut1.png","image/login/shut2.png","image/login/shut3.png");
//		shutButton.addActionListener(this);
//		shutButton.setLocation(477, 36);
//		shutButton.setSize(61, 57);
//		backgroundPanel.add(shutButton);
		
		confirmButton = new IconButton("image/login/confirm1.png","image/login/confirm2.png","image/login/confirm3.png");
		confirmButton.addActionListener(this);
		confirmButton.setLocation(372, 281);
		confirmButton.setSize(99, 68);
		//backgroundPanel.add(confirmButton);
		this.add(confirmButton);
		
		nameText = new JTextField();
		nameText.setBorder(null);//去除边框
		nameText.setOpaque(false);//将text背景设为透明
		nameText.setForeground(new Color(210, 105, 30));
		nameText.setFont(new Font("汉仪蝶语体简", Font.BOLD, 16));
		nameText.setLocation(370, 118);
		nameText.setSize(118, 30);
//		nameText.enableInputMethods(false);
		//backgroundPanel.add(nameText);
		this.add(nameText);
		
		passwordText = new JPasswordField();
		
		passwordText.setBorder(null);//去除边框
		passwordText.setOpaque(false);//将text背景设为透明
		passwordText.setForeground(new Color(210, 105, 30));
		passwordText.setFont(new Font("方正舒体", Font.BOLD, 16));
		passwordText.setLocation(370, 166);
		passwordText.setSize(118, 30);
		//backgroundPanel.add(passwordText);
		this.add(passwordText);
		
		confirmPasswordText = new JPasswordField();
		confirmPasswordText.setBorder(null);//去除边框
		confirmPasswordText.setOpaque(false);//将text背景设为透明
		confirmPasswordText.setForeground(new Color(210, 105, 30));
		confirmPasswordText.setFont(new Font("方正舒体", Font.BOLD, 16));
		confirmPasswordText.setLocation(370, 214);
		confirmPasswordText.setSize(118, 30);
		//backgroundPanel.add(confirmPasswordText);
		this.add(confirmPasswordText);
		
		
	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==shutButton){
//			mainFrame.dispose();
//		}
		if(e.getSource()==confirmButton){//提交
			String name = nameText.getText();
			String password = passwordText.getText();
			String confirmPassword = confirmPasswordText.getText();
			if(name.length()==0||password.length()==0||confirmPassword.length()==0){
				TipRunnable fail = new TipRunnable(blankPath,660, 445,200, 30,2);
				Thread myThread = new Thread(fail);
				myThread.start();
			}else{//信息填写完整
//				if(name.length() ==1){//账号已被注册(忽略括号内的内容)
//					TipRunnable fail = new TipRunnable(hasRegisteredPath,660, 445,200, 30,2);
//					Thread myThread = new Thread(fail);
//					myThread.start();
//					empty();
//				}else{//账号未被注册
					if(!password.equals(confirmPassword)){//两次密码不一致
						TipRunnable fail = new TipRunnable(passwordNotConsistentPath,660, 445,200, 30,2);
						Thread myThread = new Thread(fail);
						myThread.start();
						passwordText.setText("");
						confirmPasswordText.setText("");
					}else{//注册成功
						//向底层发送注册信息
						userController.Register(name, password);
						//提示成功
					}
//				}
			}
		}
		
	}
	
	public void success(){
		TipRunnable fail = new TipRunnable(succeedPath,660, 445,200, 30,2);
		Thread myThread = new Thread(fail);
		myThread.start();
		empty();
	}
	
	public void fail(){
		TipRunnable fail = new TipRunnable(hasRegisteredPath,660, 445,200, 30,2);
		Thread myThread = new Thread(fail);
		myThread.start();
		empty();
	}
	
	private void empty(){
		nameText.setText("");
		passwordText.setText("");
		confirmPasswordText.setText("");
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		String[] info=message.split(" "); 
		if(info[2].equals(ResultMessage.REGISTER_SUCCESSFULLY.toString())){
			success();
		}else if(info[2].equals(ResultMessage.REGISTER_FAILED.toString())){
			//出现重复用户
			fail();
		}
	}
	
//	class TipRunnable implements Runnable{
//		String imagePath;
//		public TipRunnable(String path){
//			this.imagePath = path;//获得图片的地址	
//		}
//		public void run() {
//			MySplashScreen window = new MySplashScreen(imagePath);
//			window.setLocation(660, 445);
//			window.setSize(200, 30);
//			window.setVisible(true);
//			
//			int i=0;
//			while(true){
//				i++;
//				if(i==2){
//					window.dispose();
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}	
//	}
	
//	class SuccessRunable implements Runnable{
//		public void run() {
//			MySplashScreen window = new MySplashScreen("image/login/succeedTip.png");
//			window.setLocation(660, 445);
//			window.setSize(200, 30);
//			window.setVisible(true);
//			
//			int i=0;
//			while(true){
//				i++;
//				if(i==2){
//					window.dispose();
//				}
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//			}
//		}	
//	}
//	
//	class FailRunnable implements Runnable{
//		public void run() {
//			MySplashScreen window = new MySplashScreen("image/login/failTip.png");
//			window.setLocation(660, 445);
//			window.setSize(200, 30);
//			window.setVisible(true);
//			
//			int i=0;
//			while(true){
//				i++;
//				if(i==2){
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
}










