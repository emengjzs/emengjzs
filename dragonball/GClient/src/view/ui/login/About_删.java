package view.ui.login;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.ui.myComponent.IconButton;



import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class About_删 implements ActionListener{
	JFrame mainFrame;
	private int width = 600;
	private int height = 400;
	
	public static void main(String[] args) {
		new About_删();
	}
	
	public About_删(){
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(360, 200);
		mainFrame.setUndecorated(true);//去除窗口边框
		mainFrame.setBackground(new Color(0,0,0,0)); //将窗口变透明
		
		initFrame();
		
		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/login/aboutUs.png");
		JPanel backgroundPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		backgroundPanel.setOpaque(false);
		
		JLabel team = new JLabel("小组：大保天天健");
		team.setLocation(324, 66);
		team.setSize(120, 30);
		backgroundPanel.add(team);
		
		JLabel teamMate = new JLabel("组员：陈俭军    121250010");
		teamMate.setLocation(324, 106);
		teamMate.setSize(170, 30);
		backgroundPanel.add(teamMate);
		
		JLabel chenpeng = new JLabel("陈    鹏    121250013");
		chenpeng.setLocation(363, 146);
		chenpeng.setSize(175, 30);
		backgroundPanel.add(chenpeng);
		
		JLabel jiangbingliang = new JLabel("蒋秉良    121250058");
		jiangbingliang.setLocation(363, 186);
		jiangbingliang.setSize(151, 30);
		backgroundPanel.add(jiangbingliang);
		
		JLabel jiaozishun = new JLabel("焦紫顺    121250063");
		jiaozishun.setLocation(363, 226);
		jiaozishun.setSize(151, 30);
		backgroundPanel.add(jiaozishun);
		
		JLabel lichunyu = new JLabel("李淳雨    121250066");
		lichunyu.setLocation(363, 266);
		lichunyu.setSize(131, 30);
		backgroundPanel.add(lichunyu);
		
		JLabel lihaojun = new JLabel("李豪俊    121250067");
		lihaojun.setLocation(363, 306);
		lihaojun.setSize(131,30);
		backgroundPanel.add(lihaojun);
		
		IconButton shutButton = new IconButton("image/login/shut1.png","image/login/shut2.png","image/login/shut3.png");
		shutButton.addActionListener(this);
		shutButton.setLocation(488, 43);
		shutButton.setSize(50, 50);
		backgroundPanel.add(shutButton);
		
		
		
	}

	class MyLabel extends JLabel{
		private static final long serialVersionUID = 1L;

		public MyLabel(String str){
			this.setFont(new Font("宋体", Font.BOLD, 16));
			this.setForeground(new Color(210, 105, 30));
			this.setHorizontalAlignment(SwingConstants.CENTER);
			this.setText(str);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		mainFrame.dispose();
	}
}













