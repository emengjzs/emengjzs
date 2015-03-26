package view.ui.myComponent;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class MySplashScreen  extends JWindow{
	private static final long serialVersionUID = 1L;
	
	public MySplashScreen(){
		this.setBackground(new Color(0,0,0,0));
	}

	public MySplashScreen(String imagePath){
		JPanel panel = new JPanel(new BorderLayout());
		ImageIcon icon = new ImageIcon(imagePath);
		JLabel label = new JLabel(icon);
		
		panel.add(BorderLayout.CENTER, label);
		this.setContentPane(panel);
	}

}
