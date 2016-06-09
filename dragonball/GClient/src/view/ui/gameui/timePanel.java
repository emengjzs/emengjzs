package view.ui.gameui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import setup.GameClient;
import view.ui.myComponent.Music;

@SuppressWarnings("serial")
public class timePanel extends JPanel {
	int count;
	Image im1;
	Image im2;
	Timer timer;
	JLabel label;
	count_animate animate;

	public timePanel() {
		this.setSize(1080, 30);
		this.setLocation(10, 667);

		Font font = new Font("Œ¢»Ì—≈∫⁄", Font.BOLD, 20);
		try {
			im2 = ImageIO.read(new File("Image/time_2.png"));
			im1 = ImageIO.read(new File("Image/time_1.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		label = new JLabel("60");
		label.setForeground(Color.WHITE);
		label.setFont(font);
		label.setSize(100, 25);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setLocation(this.getWidth() / 2 - 50, 3);
		animate = new count_animate();

		this.setLayout(null);
		this.add(label);
		this.add(animate);

		// this.setOpaque(false);
		// this.setBackground(new Color(0,0,0,0));
	}

	public void start() {
		timer = new Timer();
		timer.scheduleAtFixedRate(new Task(), 0, 100);
	}

	public class Task extends TimerTask {
		public void run() {
			repaint();
			if (count % 10 == 0) {
				label.setText("" + (60 - count / 10));
			}
			if(count==500){
				if(GameClient.MusicOn){
					Music music = new Music("music/game_begin.mp3",false);
					music.play();
				}
			}
			if (count >= 600) {
				timer.cancel();
				label.setText("”Œœ∑Ω· ¯£°");
//				if(GameClient.MusicOn){
//					Music music = new Music("music/game_begin.mp3",false);
//					music.play();
//				}
			} else {
				count++;
			}
		}
	}

	public class count_animate extends JPanel {

		int x, y;
		float gap;

		public count_animate() {
			this.setBackground(Color.BLACK);
			this.setSize(1080, 30);
			x = this.getWidth();
			y = this.getHeight();
			gap = (float) x / 600;
			count = 0;
		}

		public void paint(Graphics g) {
			super.paint(g);
			g.drawImage(im2, 0, 2, (int) (x - gap * count), y * 9 / 10, this);
			g.drawImage(im1, 0, 0, x, y, this);
		}
	}

}
