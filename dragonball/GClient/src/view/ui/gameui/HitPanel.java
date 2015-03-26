package view.ui.gameui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class HitPanel extends JPanel implements ActionListener {

	private static final int ANIMATION_FRAMES = 25;
	private static final int ANIMATION_INTERVAL = 20;
	private int up = 0;
	private Point labloc;
	private Point imgloc;
	Random rb = new Random();
	float r = 5.5f;
	// ��ʵ�Ե���ʧ
	// ֡����
	private static float frameIndex = ANIMATION_FRAMES;
	// ʱ��
	private Timer timer;

	HPLabel hpLabel;
	Monster monster;
	JLabel result;
	JPanel bloodCount;
	
	int x = 300;
	int y = 20;

	public static void main(String args[]) {
		HitPanel opp = new HitPanel(-1);

		JFrame frame = new JFrame();
		frame.add(opp);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		opp.Hit(10000);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		opp.Hit(90);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		opp.Hit(90);
	}

	public HitPanel(int i) {
		hpLabel = new HPLabel();
		hpLabel.setFont(new Font("���ǵ������", Font.BOLD, 36));
		hpLabel.setForeground(Color.WHITE);
		hpLabel.setSize(200, 80);
		hpLabel.setLocation(250, 60);

		if (i == -1) {
			monster = new Monster();
		} else if (i >= 1 && i <= 9) {
			monster = new Monster(i);
		} else{
			monster = new Monster(8);
		}
		
		monster.setSize(203, 279);
		monster.setLocation(60, 15);

		labloc = hpLabel.getLocation();
		imgloc = monster.getLocation();

		result = new JLabel("���أ�");
		result.setFont(new Font("���ǵ������", Font.BOLD, 36));
		result.setForeground(Color.WHITE);
		result.setSize(120, 45);
		result.setLocation(120, 315);
		result.setVisible(false);

		final Image im1 = new ImageIcon("Image/time_1.png").getImage();
		final Image im2 = new ImageIcon("Image/time_2.png").getImage();
		bloodCount = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(im2, 0, 0, x*monster.blood/monster.max, y, null);
				g.drawImage(im1, 0, 0, x, y, null);
			}
		};
		bloodCount.setSize(x,y);
		bloodCount.setLocation(20,330);
		
		timer = new Timer(ANIMATION_INTERVAL, this);

		this.setLayout(null);
		this.add(hpLabel);
		this.add(monster);
		this.add(result);
		this.add(bloodCount);

		this.setSize(400, 400);
		this.setLocation(650, 150);
		// this.setBackground(Color.BLACK);
		this.setOpaque(false);
	}

	// -------------------���ⷽ��-------------------------------------------------------------
	// HP ��Ѫ����
	public void Hit(int Hp) {
		up = 0;
		r = 5.5f;
		// startAnime = true;
		frameIndex = ANIMATION_FRAMES;
		// labloc = hpLabel.getLocation();
		// imgloc = monster.getLocation();
		hpLabel.setText("-" + Hp);
		timer.start();
		monster.hitted(Hp);
	}

	public int getCur() {
		return monster.getCur();
	}

	// -------------------���ⷽ��-------------------------------------------------------------

	// �ر�ʱ�ӣ����³�ʼ��
	private void closeTimer() {
		if (timer != null && timer.isRunning()) {
			// startAnime = false;
			timer.stop();
			frameIndex = ANIMATION_FRAMES;
			hpLabel.setText("");
			hpLabel.setLocation(labloc);
			monster.setLocation(imgloc);
		}
	}

	// ����ʱ�Ӵ����¼�
	public void actionPerformed(ActionEvent e) {
		// ǰ��һ֡
		// if(frameIndex > 0)
		frameIndex--;
		int _x = rb.nextInt(20);
		int _y = rb.nextInt(20);
		hpLabel.setLocation(labloc.x, labloc.y - up);
		monster.setLocation(imgloc.x + _x, imgloc.y + _y);
		up += r;
		if (r > 0.4)
			r -= 0.4;
		if (frameIndex <= 0)
			// ���һ֡���رն���
			closeTimer();
		else
			// ���µ�ǰһ֡
			repaint();
	}

	public class HPLabel extends JLabel {

		public void paint(Graphics g) {
			if (timer != null && timer.isRunning()) {
				// ���ݵ�ǰ֡��ʾ��ǰ͸���ȵ��������
				float alpha = frameIndex / ANIMATION_FRAMES;
				Graphics2D g2d = (Graphics2D) g;
				g2d.setComposite(AlphaComposite.getInstance(
						AlphaComposite.SRC_OVER, alpha));
				// Renderer��Ⱦ����
				// g2d.drawString(getText(), x, y+up++);
				super.paint(g2d);
			} else {
				super.paint(g);
				// ����ǵ�һ�Σ���������ʱ��
			}
		}
	}

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>�ڲ���>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public class Monster extends JPanel {
		private static final long serialVersionUID = 1L;
		private ImageIcon loginLabelImage;
		private String path;

		private int blood;
		private int max;
		
		private boolean isCoop = false;

		private int cur = 0;
		private int[] tol_solo = { 100, 500, 1000, 5000, 10000, 30000, 50000,
				100000, 120000 };
		private int[] tol_coop = { 200, 500, 1000, 3000, 5500, 10000, 30000,
				50000 };
		private String[] coop_img = { "2����", "5����", "7����", "6����", "3����", "4����",
				"1����", "��1����" };

		// Э��ģʽ
		public Monster() {
			this.path = "Image/role/" + coop_img[cur] + ".png";
			loginLabelImage = new ImageIcon(path);
			max = tol_coop[cur];
			blood = max;
			isCoop = true;
			this.setOpaque(false);
		}

		// ������Ϸ
		public Monster(int i) {
			this.path = "Image/role/role" + i + ".png";
			loginLabelImage = new ImageIcon(path);
			max = tol_solo[i - 1];
			blood = max;
			this.setOpaque(false);
		}

		public void hitted(int hp) {
			blood -= hp;
			// //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			// if(GameClient.MusicOn){
			// Music music = new Music("music/hit.mp3",false);
			// music.play();
			// }
			// //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if (blood <= 0 && isCoop) {
				blood = 0;
				changeImg();
			} else if (blood <= 0) {
				blood = 0;
				result.setVisible(true);
			}
		}

		public int getCur() {
			return cur;
		}

		private void changeImg() {
			if (cur < 7) {
				cur++;
				this.path = "Image/role/" + coop_img[cur] + ".png";
				loginLabelImage = new ImageIcon(path);
				max = tol_coop[cur];
				blood = max;
				this.repaint();
			} else {
				cur++;
				result.setVisible(true);
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(loginLabelImage.getImage(), 0, 0, null);
		}
	}
}
