package view.ui.gameui;
import java.awt.Color;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CountPanel extends JPanel{
	
	JLabel tol,max;
	
	int tol_count = 0;
	int max_count = 0;
	int hit_count = 0;
	
	public CountPanel(){
		tol = new JLabel("连击数："+tol_count);
		tol.setForeground(Color.WHITE);
		tol.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		
		max = new JLabel("最大连击："+max_count);
		max.setForeground(Color.WHITE);
		max.setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
		
		tol.setSize(150,30);
		max.setSize(150,30);
		tol.setLocation(0,0);
		max.setLocation(200,0);
		

		this.add(tol);
		this.add(Box.createHorizontalStrut(20));
		this.add(max);
		this.setOpaque(false);
		this.setSize(300,30);
		this.setLocation(240, 30);
	}
	
	public void setTol(int num){
		tol_count = num;
		tol.setText("连击数："+tol_count);
		this.repaint();
	}
	
	public void setMax(int num){
		max_count = num;
		max.setText("最大连击："+max_count);
		this.repaint();
	}
	
}
