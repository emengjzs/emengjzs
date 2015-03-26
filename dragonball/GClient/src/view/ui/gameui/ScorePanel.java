package view.ui.gameui;

import javax.swing.JPanel;


//import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
	@SuppressWarnings("unused")
	private GameController gc;
//	private Color leftShadowColor  = new Color(0xF5F5F5);
//	private Color rightShadowColor = new Color(0xEFD9C0);
//	private Color NormalColor = new Color(0x5AA2C7);
	private Color SuperColor = new Color(197, 90, 17 );
	private GameLabel scorelabel;
	private AddScoreAnime anime;
	private int score = 0;
	
	public ScorePanel() {
		setSize(160, 60);
		setLocation(50, 10);
		scorelabel = new GameLabel(String.valueOf(score));
//		scorelabel.setFont(scorelabel.getFont().deriveFont(40f));
		scorelabel.setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 45));
		scorelabel.setForeground(Color.WHITE);
		//scorelabel.setOpaque(false);
//		setBackground(null);
		add(scorelabel);
		//setOpaque(false);
		anime = new AddScoreAnime();
//		setNormalMode();
		
		this.setOpaque(false);
//		this.setBackground(new Color(0,0,0,0));
	}
	
	public void setGameController(GameController gc) {
		this.gc = gc;
	}
	
	public void setNormalMode() {
		//scorelabel.setLeftShadow(1, 1, leftShadowColor);
		//scorelabel.setRightShadow(1, 2, rightShadowColor);
		scorelabel.setForeground(Color.WHITE);
		repaint();
	}
	
	public void setSuperMode() {
		//scorelabel.setLeftShadow(1, 1, leftShadowColor);
		//scorelabel.setRightShadow(1, 2, rightShadowColor);
		scorelabel.setForeground(SuperColor);
		repaint();
	}
	
	public void addScore(int s) {
		
		anime.addScore(s);
	}
	/*
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 =(Graphics2D)g;	
		this.paintChildren(g2);
		//scorelabel.paintComponent(g2);
		//g2.drawImage(backImage,0,0,null);
		//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3f));
		//super.paintComponent(g);
	}
	*/
	public int getScore() {
		return score;
	}
	
	private class AddScoreAnime {		
		Timer timer;
		int time_gap = 8;
		//int addScore = 0;
		int per = 4;
		
		public AddScoreAnime() {
			timer = new Timer();
		}
		
		public synchronized void addScore(int s) {
			score += s;
			//addScore+=s;
			timer.scheduleAtFixedRate(new AnimeTask(s), 0, time_gap);
		}
		
		public class AnimeTask extends TimerTask {	
			int s;
			public AnimeTask(int s){ this.s = s; }
			public  void  run() {
				if(s <= 0) {					
					scorelabel.setText(String.valueOf(Integer.parseInt(scorelabel.getText())+s));					
					repaint();
					this.cancel();
					return;
				}
				scorelabel.setText(String.valueOf(Integer.parseInt(scorelabel.getText())+per));
				s-=per;
				repaint();
			}
		}
	}


	
}
