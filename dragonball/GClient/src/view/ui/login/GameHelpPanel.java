package view.ui.login;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.ui.myComponent.IconButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GameHelpPanel extends JPanel implements ActionListener{
//	JFrame mainFrame;
//	private int width = 700;
//	private int height = 500;
//	
//	IconButton shutButton;
//	IconButton backButton;
	
//	public static void main(String[] args) {
//		new GameHelpPanel();
//	}
	
	IconButton forwardButton;
	IconButton backwardButton;
	
	ImageIcon tip1 = new ImageIcon("");
	ImageIcon tip2 = new ImageIcon("");
	
	JLabel pictureLabel;
	
	public GameHelpPanel(){
//		mainFrame = new JFrame();
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 100);
//		mainFrame.setUndecorated(true);//ȥ�����ڱ߿�
//		mainFrame.setBackground(new Color(0,0,0,0)); //�����ڱ�͸��
		
		initFrame();
		
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
//		backgroundPanel.setForeground(new Color(210, 105, 30));
//		backgroundPanel.setLayout(null);
//		backgroundPanel.setSize(width, height);
//		contentPane.add(backgroundPanel, BorderLayout.WEST);
		//backgroundPanel.setOpaque(false);
		
		forwardButton = new IconButton("image/login/forward1.png","image/login/forward2.png","image/login/forward3.png");
		forwardButton.setSize(50, 50);
		forwardButton.addActionListener(this);
		forwardButton.setLocation(573,215);
		//backgroundPanel.add(forwardButton);
//		this.add(forwardButton);
		
		backwardButton = new IconButton("image/login/backward1.png","image/login/backward2.png","image/login/backward3.png");
		backwardButton.setLocation(66, 215);
		backwardButton.setSize(50, 50);
		backwardButton.addActionListener(this);
		//backgroundPanel.add(backwardButton);
//		this.add(backwardButton);
		
		String s1="<html>"+"��Ϸ���:"+"<br>"+"<br>";
		String s2="1����Ϸ�淨�ο����찮������Ϸ"+"<br>"+"<br>";
		String s3="2��������Ϸ���ùؿ�����Ȥζ��"+"<br>"+"<br>";
		String s4="3��Э����Ϸ�������飬�߿Ž������һ��"+"<br>"+"<br>";
		String s5="4����ս��Ϸ���뷿����֧��300��ң������޷�����"+"<br>"+"<br>";
		String toShow = s1+s2+s3+s4+s5+"</html>";
		pictureLabel = new JLabel(toShow);
		pictureLabel.setFont(new Font("���ǵ������", Font.BOLD, 20));
		pictureLabel.setLocation(110, 60);
		pictureLabel.setSize(485, 344);
		//backgroundPanel.add(pictureLabel);
		this.add(pictureLabel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==forwardButton){
			pictureLabel.setIcon(tip2);
		}
		if(e.getSource()==backwardButton){
			pictureLabel.setIcon(tip1);
		}
		
	}

}















