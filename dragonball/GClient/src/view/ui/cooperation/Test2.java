package view.ui.cooperation;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Test2 {
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	JButton button[] = new JButton[4];
	
	public static void main(String[] args) {
		new Test2();
	}
	
	public Test2(){
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		//mainFrame.setUndecorated(true);//È¥³ý´°¿Ú±ß¿ò
		
		initFrame();
		
		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/cooperation/background.png");
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
		
		JButton button_1 = new JButton();
		button_1.setLocation(100, 100);
		button_1.setSize(100, 100);
		backgroundPanel.add(button_1);
		
		
		
		
		JLabel label = new JLabel(new ImageIcon("image/cooperation/background.png"));
		label.setSize(400, 400);
		backgroundPanel.add(label);
	}
}
