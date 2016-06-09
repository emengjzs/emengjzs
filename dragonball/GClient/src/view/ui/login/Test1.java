package view.ui.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.border.LineBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.ui.myComponent.MySplashScreen;

public class Test1 implements ActionListener{
	JFrame mainFrame;
	private int width = 700;
	private int height = 500;
	
	Runnable moveLabel = new Myrunnable();
	
	
	public static void main(String[] args) {
		new Test1();
	}
	
	public Test1(){
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(360, 100);
		
		initFrame();
		
		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		JPanel backgroundPanel = new JPanel();
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		backgroundPanel.setOpaque(false);

		JButton button = new JButton("¿ªÊ¼");
		button.setSize(50, 50);
		button.addActionListener(this);
		backgroundPanel.add(button);
		
	}
	
	public class Myrunnable implements Runnable{

		@Override
		public void run() {
//			JWindow window = new JWindow();
//			window.setLocation(500, 200);
//			window.setSize(450, 300);
//			JPanel pane = new JPanel(new BorderLayout());
//			ImageIcon pic1 = new ImageIcon("image/login/countDown.gif");
//			JLabel l = new JLabel(pic1);
//			pane.add(BorderLayout.CENTER, l);
//			pane.setBorder(new LineBorder(Color.GRAY));
//			window.setContentPane(pane);
//			window.setVisible(true);
			
			MySplashScreen window = new MySplashScreen("image/login/countDown.gif");
			window.setLocation(500, 200);
			window.setSize(450, 300);
			window.setVisible(true);
			
			int i=0;
			while(true){
				i++;
				if(i==6){
					window.dispose();
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Thread myThread = new Thread(moveLabel);
		myThread.start();
		//showTip();
	}
	
	@SuppressWarnings("unused")
	private void showTip(){
		System.out.println("vsdfhsgfm");
		JWindow window = new JWindow();
		window.setLocation(200, 20);
		window.setSize(450, 300);
		JPanel pane = new JPanel(new BorderLayout());
		ImageIcon pic1 = new ImageIcon("image/login/splash.png");
		JLabel l = new JLabel(pic1);
		pane.add(BorderLayout.CENTER, l);
		pane.setBorder(new LineBorder(Color.GRAY));
		window.setContentPane(pane);
		window.setVisible(true);
		
//		int i=0;
//		while(true){
//			i++;
//			if(i==2){
//				window.dispose();
//				//myThread.stop();
//			}
//			try {
//				Thread.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
		for(int i=0;i<10;i++){
			System.out.println("234");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//window.dispose();
	}

}
