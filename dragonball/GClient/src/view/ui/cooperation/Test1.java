package view.ui.cooperation;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Test1 implements ActionListener{

	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	JButton button[] = new JButton[4];
	
	public static void main(String[] args) {
		new Test1();
	}
	
	public Test1(){
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
		
		
		
		for(int i=0;i<4;i++){
			button[i] = new JButton();
			button[i].setSize(50, 50);
			button[i].setLocation(50+100*i, 200);
			button[i].addActionListener(this);
			backgroundPanel.add(button[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button[0]){
			System.out.println(0);
		}else if(e.getSource()==button[1]){
			System.out.println(1);
		}else if(e.getSource()==button[2]){
			System.out.println(2);
		}else{
			System.out.println(3);
		}
	}

}











