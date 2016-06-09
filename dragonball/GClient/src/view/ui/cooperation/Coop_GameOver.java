package view.ui.cooperation;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.ui.myComponent.IconButton;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Coop_GameOver implements ActionListener{
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	JLabel scoreLabel;
	JLabel moneyLabel;
	
	IconButton backButton;
	
	JLabel player[] = new JLabel[4];//Íæ¼Ò
	JLabel dragon[] = new JLabel[7];//Ð°¶ñÁú
	public static void main(String[] args) {
		new Coop_GameOver();
	}
	
	public Coop_GameOver(){
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);//È¥³ý´°¿Ú±ß¿ò
		
		initFrame();
		
		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/solo/background.png");
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
	
		
		
		for(int i=0;i<4;i++){
			player[i] = new JLabel(new ImageIcon("image/cooperation/role2.png"));
			player[i].setSize(150, 206);
			if(i<2){
				player[i].setLocation(76+i*160, 72);
			}else{
				player[i].setLocation(76+(i-2)*160, 288);
			}
			backgroundPanel.add(player[i]);
		}
		
		
		JLabel score = new JLabel("µÃ·Ö£º");
		score.setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 28));
		score.setLocation(76, 516);
		score.setSize(103, 50);
		backgroundPanel.add(score);
		
		scoreLabel = new JLabel("114246");
		scoreLabel.setLocation(236, 516);
		scoreLabel.setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 28));
		scoreLabel.setSize(128, 50);
		backgroundPanel.add(scoreLabel);
		
		JLabel money = new JLabel("½ðÇ®£º");
		money.setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 28));
		money.setLocation(76, 576);
		money.setSize(103, 50);
		backgroundPanel.add(money);
		
		moneyLabel = new JLabel("2352546");
		moneyLabel.setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 28));
		moneyLabel.setLocation(236, 580);
		moneyLabel.setSize(128, 50);
		backgroundPanel.add(moneyLabel);
		
		backButton = new IconButton("image/cooperation/back1.png","image/cooperation/back2.png","image/cooperation/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(575, 580);
		backButton.setSize(80, 80);
		backgroundPanel.add(backButton);
		
		
		
		for(int i=0;i<7;i++){
			dragon[i] = new JLabel(new ImageIcon("image/cooperation/role3.png"));
			dragon[i].setSize(120, 165);
			if(i==0){
				dragon[i].setLocation(780, 50);
			}else if(i<4){
				dragon[i].setLocation(650+130*(i-1), 225);
			}else{
				dragon[i].setLocation(650+130*(i-4), 400);
			}
			backgroundPanel.add(dragon[i]);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mainFrame.dispose();
		
	}

}






















