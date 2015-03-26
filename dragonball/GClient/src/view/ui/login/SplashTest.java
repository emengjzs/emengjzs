package view.ui.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

@SuppressWarnings("serial")
public class SplashTest extends JWindow{
	Graphics g = null;
	Icon icon = new ImageIcon("icons/out1.jpg");
	//Icon icon = new ImageIcon("icons/out1.jpg");
	JLabel label = new JLabel(icon);
	//Image image = Toolkit.getDefaultToolkit().createImage("icons/out1.jpg");
	Image image = Toolkit.getDefaultToolkit().createImage("image/login/shut1.png");

	public SplashTest() {
		label.setVisible(true);
		label.setForeground(Color.RED);
		this.add(label,BorderLayout.CENTER);
		this.setBounds(new Rectangle(500,300));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		int i = 0;
		g.drawImage(image,0,0,500,300,this);

		// 显示10S，消失。
		while (true) {
			i++;
			
			g.drawImage(image,0,0,500,300,this);
			g.drawString("当前进度" + i*10+"%", 50, 200);
			
			if (i == 10) {
				this.dispose();
				System.exit(0);
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		SplashTest st = new SplashTest();
	}

}