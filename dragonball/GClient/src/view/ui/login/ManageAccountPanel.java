package view.ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.vo.UserVO;
import view.ui.myComponent.IconButton;

import java.awt.Font;

@SuppressWarnings("serial")
public class ManageAccountPanel extends JPanel implements ActionListener{
//	JFrame mainFrame;
	UserVO user;
//	private int width = 700;
//	private int height = 500;
	
//	IconButton shutButton;
//	IconButton backButton;
	
	IconButton changePasswordButton;
	JLabel nameLabel;
	
//	public static void main(String[] args) {
//		new ManageAccountPanel();
//	}
	
	public ManageAccountPanel(UserVO user){
	//public ManageAccountPanel(){
		this.user=user;
//		mainFrame = new JFrame();
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 100);
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
//		final ImageIcon backgroundImage = new ImageIcon("image/login/manageAccountBackground.png");
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
//		shutButton.setLocation(575, 32);
//		shutButton.setSize(72, 67);
//		backgroundPanel.add(shutButton);
//		
//		backButton = new IconButton("image/login/back1.png","image/login/back2.png","image/login/back3.png");
//		backButton.addActionListener(this);
//		backButton.setLocation(289, 384);
//		backButton.setSize(100, 80);
//		backgroundPanel.add(backButton);
		
//		changePasswordButton = new IconButton("image/login/changePassword1.png","image/login/changePassword2.png","image/login/changePassword3.png");
//		changePasswordButton.addActionListener(this);
//		changePasswordButton.setLocation(102, 197);
//		changePasswordButton.setSize(150, 50);
		//backgroundPanel.add(changePasswordButton);
		//this.add(changePasswordButton);
		
		nameLabel = new JLabel(user.getID());
		nameLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 20));
		nameLabel.setLocation(183, 116);
		nameLabel.setSize(150,34);
		//backgroundPanel.add(nameLabel);
		this.add(nameLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==shutButton){
//			mainFrame.dispose();
//		}
//		if(e.getSource()==backButton){
//			mainFrame.dispose();
//		}
		
//		if(e.getSource()==changePasswordButton){
//			new ChangePasswordPanel(user);
//		}
	}

}


















