package view.ui.login;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;

import model.netbl.NetUserBL;
import model.netservice.NetUserBLService;
import model.vo.UserVO;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.TipRunnable;



@SuppressWarnings("serial")
public class ChangePasswordPanel extends JPanel implements ActionListener{
//	JFrame mainFrame;
	UserVO user;
	static NetUserBLService userController;
//	private int width = 460;
//	private int height = 280;
	
//	IconButton shutButton;
	IconButton confirmButton;
	JPasswordField passwordText;
	JPasswordField confirmPasswordText;
	
//	public static void main(String[] args) {
//		new ChangePasswordPanel();
//	}
	
	public ChangePasswordPanel(UserVO user){
	//public ChangePasswordPanel(){
		userController=NetUserBL.getUserController();
		this.user=user;
//		mainFrame = new JFrame();
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(480, 250);
//		mainFrame.setUndecorated(true);//去除窗口边框
//		mainFrame.setBackground(new Color(0,0,0,0)); //将窗口变透明
//		
		this.setOpaque(false);
		this.setLayout(null);
		initFrame();
//		
//		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
//		Container contentPane = mainFrame.getContentPane();
//		
//		final ImageIcon backgroundImage = new ImageIcon("image/login/changePasswordBackground.png");
//		JPanel backgroundPanel = new JPanel(){
//			private static final long serialVersionUID = 1L;
//			public void paintComponent(Graphics g){
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
//		shutButton.setLocation(378, 10);
//		shutButton.setSize(72, 67);
//		backgroundPanel.add(shutButton);
		
		confirmButton = new IconButton("image/login/confirm1.png","image/login/confirm2.png","image/login/confirm3.png");
		confirmButton.addActionListener(this);
		confirmButton.setLocation(221, 193);
		confirmButton.setSize(89, 77);
		//backgroundPanel.add(confirmButton);
		this.add(confirmButton);
		
		passwordText = new JPasswordField();
		passwordText.setBorder(null);//去除边框
		passwordText.setOpaque(false);//将text背景设为透明
		passwordText.setForeground(new Color(210, 105, 30));
		passwordText.setFont(new Font("方正舒体", Font.BOLD, 16));
		passwordText.setLocation(200, 69);
		passwordText.setSize(130, 30);
		//backgroundPanel.add(passwordText);
		this.add(passwordText);
		
		confirmPasswordText = new JPasswordField();
		confirmPasswordText.setBorder(null);//去除边框
		confirmPasswordText.setOpaque(false);//将text背景设为透明
		confirmPasswordText.setForeground(new Color(210, 105, 30));
		confirmPasswordText.setFont(new Font("方正舒体", Font.BOLD, 16));
		confirmPasswordText.setLocation(200, 134);
		confirmPasswordText.setSize(130, 30);
		//backgroundPanel.add(confirmPasswordText);
		this.add(confirmPasswordText);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==shutButton){
//			mainFrame.dispose();
//		}
		if(e.getSource()==confirmButton){
			char pw[] = passwordText.getPassword();
			String password = new String(pw);
			char conpw[] = confirmPasswordText.getPassword();
			String confirmPassword = new String(conpw);
			
			if(password.length()==0||confirmPassword.length()==0){//信息未填写完整
				TipRunnable fail = new TipRunnable("image/tip/blank.png",630, 430,200, 30,2);
				Thread myThread = new Thread(fail);
				myThread.start();
			}else{
				if(!password.equals(confirmPassword)){//两个密码不匹配
					TipRunnable fail = new TipRunnable("image/tip/passwordNotConsistent.png",630, 430,200, 30,2);
					Thread myThread = new Thread(fail);
					myThread.start();
					passwordText.setText("");
					confirmPasswordText.setText("");
				}else{//修改密码
					//提示成功
					TipRunnable fail = new TipRunnable("image/tip/changeSucceed.png",630, 430,200, 30,2);
					Thread myThread = new Thread(fail);
					myThread.start();
					
					String userID=user.getID();
					userController.ChangePassowrd(userID, password);
					
					passwordText.setText("");
					confirmPasswordText.setText("");
				}
			}
			
			
//			if(password.equals(confirmPassword)&&(!password.equals(""))){
//				String userID=user.getID();
//				userController.ChangePassowrd(userID, password);
//				//可以修改
//				
//			}else{
//				//不能修改
//			}

		}
		
	}
	
	

}
