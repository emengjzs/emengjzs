package view.ui.login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.vo.UserVO;
import view.ui.myComponent.IconButton;

import java.awt.Font;

@SuppressWarnings("serial")
public class GameCenterPanel extends JPanel implements ActionListener{
//	JFrame mainFrame;
//	private int width = 700;
//	private int height = 500;
	
	//IconButton shutButton;
	//IconButton backButton;
	UserVO user;
	
	ImageIcon toolTipImage1 = new ImageIcon("image/login/toolTip1.png");
	ImageIcon toolTipImage2 = new ImageIcon("image/login/toolTip2.png");
	ImageIcon toolTipImage3 = new ImageIcon("image/login/toolTip3.png");
	
	IconButton toolButton1;
	IconButton toolButton2;
	IconButton toolButton3;
	JLabel toolTipLabel;
	
//	public static void main(String[] args) {
//		new GameCenterPanel();
//	}
	
	public GameCenterPanel(UserVO user){
//		mainFrame = new JFrame();
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 100);
//		mainFrame.setUndecorated(true);//去除窗口边框
//		mainFrame.setBackground(new Color(0,0,0,0)); //将窗口变透明
		this.user=user;
		
		this.setOpaque(false);
		this.setLayout(null);
		
		initFrame();
		
//		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
//		Container contentPane = mainFrame.getContentPane();
//		
//		final ImageIcon backgroundImage = new ImageIcon("image/login/background.png");
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
//		
//		shutButton = new IconButton("image/login/shut1.png","image/login/shut2.png","image/login/shut3.png");
//		shutButton.addActionListener(this);
//		shutButton.setLocation(575, 32);
//		shutButton.setSize(72, 67);
//		backgroundPanel.add(shutButton);	
		
		//以下两个Label要根据User数据更改！！
		JLabel roleLabel = new JLabel(new ImageIcon("image/login/player"+getLevel()+".png"));
		roleLabel.setLocation(110, 70);
		roleLabel.setSize(160,225);
		
		String s1="经验值："+user.getExp();
		String s2="等级："+getLevel();
		String s3="攻击力："+user.getPower();
		String s4="金钱："+user.getMoney();
		JLabel messLabel = new JLabel();
		messLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		messLabel.setText("<html>" + s1 + "<br>" + s2 + "<br>" + s3 +"<br>" + s4 +"</html>");
		messLabel.setSize(160, 120);
		messLabel.setLocation(110, 295);
		this.add(messLabel);
		
		this.add(roleLabel);
		
		JLabel toolIntroductionLabel = new JLabel("道具介绍");
		toolIntroductionLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		toolIntroductionLabel.setForeground(new Color(210, 105, 30));
		toolIntroductionLabel.setLocation(353, 70);
		toolIntroductionLabel.setSize(95, 50);
		//backgroundPanel.add(toolIntroductionLabel);
		this.add(toolIntroductionLabel);
		
		toolButton1 = new IconButton("image/login/tool1_1.png","image/login/tool1_2.png","image/login/tool1_1.png");
		toolButton1.addActionListener(this);
		toolButton1.setLocation(360, 130);
		toolButton1.setSize(70, 70);
		//backgroundPanel.add(toolButton1);
		this.add(toolButton1);
		
		toolButton2 = new IconButton("image/login/tool2_1.png","image/login/tool2_2.png","image/login/tool2_1.png");
		toolButton2.addActionListener(this);
		toolButton2.setLocation(440, 130);
		toolButton2.setSize(70, 70);
		//backgroundPanel.add(toolButton2);
		this.add(toolButton2);
		
		toolButton3 = new IconButton("image/login/tool3_1.png","image/login/tool3_2.png","image/login/tool3_1.png");
		toolButton3.addActionListener(this);
		toolButton3.setLocation(520, 130);
		toolButton3.setSize(70, 70);
		//backgroundPanel.add(toolButton3);
		this.add(toolButton3);
		
		toolTipLabel = new JLabel(toolTipImage1);
		toolTipLabel.setLocation(360, 236);
		toolTipLabel.setSize(230, 100);
		//backgroundPanel.add(toolTipLabel);
		this.add(toolTipLabel);
		
		
	}
	
	private int getLevel(){
		int exp=user.getExp();
		int level=0;
		int[] expList={0,30,80,180,400,700,1000,1300,1600,2000};
		
		for(int i=0;i<expList.length;i++){
			try{
				if(exp>=expList[i]&&exp<expList[i+1]){
					level=i;
					break;
				}
			}catch(ArrayIndexOutOfBoundsException e){
				level=expList.length-1;
			}
		}
		
		return level;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource()==shutButton){
//			mainFrame.dispose();
//		}
//		if(e.getSource()==backButton){
//			mainFrame.dispose();
//		}
		
		if(e.getSource()==toolButton1){
			toolTipLabel.setIcon(toolTipImage1);
		}
		if(e.getSource()==toolButton2){
			toolTipLabel.setIcon(toolTipImage2);
		}
		if(e.getSource()==toolButton3){
			toolTipLabel.setIcon(toolTipImage3);
		}
		
	}

}













