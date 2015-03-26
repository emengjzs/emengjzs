package view.ui.solo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class AchievementPanel extends JPanel implements ActionListener {
//	JFrame mainFrame;
//	private int width = 650;
//	private int height = 400;
//
//	IconButton shutButton;
//	IconButton backButton;

	JButton achievementButton1;
	JButton achievementButton2;
	JButton achievementButton3;

	// 对应3个成就的大图
	ImageIcon bigAchievementImage1 = new ImageIcon(
			"image/solo/bigAchievement1.png");
	ImageIcon bigAchievementImage2 = new ImageIcon(
			"image/solo/bigAchievement2.png");
	ImageIcon bigAchievementImage3 = new ImageIcon(
			"image/solo/bigAchievement3.png");

	// 成就的缩略图——灰色（未达成目标）
	ImageIcon smallNotGet1 = new ImageIcon("image/solo/smallAchievement1_1.png");
	ImageIcon smallNotGet2 = new ImageIcon("image/solo/smallAchievement2_1.png");
	ImageIcon smallNotGet3 = new ImageIcon("image/solo/smallAchievement3_1.png");

	// 成就的缩略图——彩色（已达成目标）
	ImageIcon smallGet1 = new ImageIcon("image/solo/smallAchievement1_2.png");
	ImageIcon smallGet2 = new ImageIcon("image/solo/smallAchievement2_2.png");
	ImageIcon smallGet3 = new ImageIcon("image/solo/smallAchievement3_2.png");

	JLabel achievementLabel;

//	public static void main(String[] args) {
//		new AchievementPanel(true, true, false);
//	}

	public AchievementPanel(boolean b1, boolean b2, boolean b3) {
//		mainFrame = new JFrame();
//		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 200);
//		mainFrame.setUndecorated(true);// 去除窗口边框
//		mainFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

		this.setOpaque(false);
		this.setLayout(null);
		initFrame(b1, b2, b3);

//		mainFrame.setVisible(true);
	}

	private void initFrame(boolean b1, boolean b2, boolean b3) {
//		Container contentPane = mainFrame.getContentPane();
//
//		final ImageIcon backgroundImage = new ImageIcon(
//				"image/solo/toolBackground.png");
//		JPanel backgroundPanel = new JPanel() {
//			public void paintComponent(Graphics g) {
//				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
//						null);
//			}
//		};
//		backgroundPanel.setLayout(null);
//		backgroundPanel.setSize(width, height);
//		contentPane.add(backgroundPanel);
//
//		shutButton = new IconButton("image/solo/shut1.png",
//				"image/solo/shut2.png", "image/solo/shut3.png");
//		shutButton.addActionListener(this);
//		shutButton.setLocation(514, 30);
//		shutButton.setSize(50, 50);
//		backgroundPanel.add(shutButton);

		// 3个特殊成就的缩略图
		if (b1)
			achievementButton1 = new JButton(smallGet1);
		else
			achievementButton1 = new JButton(smallNotGet1);
		achievementButton1.addActionListener(this);
		achievementButton1.setLocation(484, 100);
		achievementButton1.setSize(60, 60);
		achievementButton1.setContentAreaFilled(false);// 去除按钮背景色
		achievementButton1.setBorder(null);// 去除边框
		achievementButton1.setFocusPainted(false);
		achievementButton1.setBorderPainted(false);
		this.add(achievementButton1);
		//backgroundPanel.add(achievementButton1);

		if (b2)
			achievementButton2 = new JButton(smallGet2);
		else
			achievementButton2 = new JButton(smallNotGet2);
		achievementButton2.addActionListener(this);
		achievementButton2.setLocation(484, 160);
		achievementButton2.setSize(60, 60);
		achievementButton2.setContentAreaFilled(false);// 去除按钮背景色
		achievementButton2.setBorder(null);// 去除边框
		achievementButton2.setFocusPainted(false);
		achievementButton2.setBorderPainted(false);
		this.add(achievementButton2);
		//backgroundPanel.add(achievementButton2);

		if (b3)
			achievementButton3 = new JButton(smallGet3);
		else
			achievementButton3 = new JButton(smallNotGet3);
		achievementButton3.addActionListener(this);
		achievementButton3.setLocation(484, 220);
		achievementButton3.setSize(60, 60);
		achievementButton3.setContentAreaFilled(false);// 去除按钮背景色
		achievementButton3.setBorder(null);// 去除边框
		achievementButton3.setFocusPainted(false);
		achievementButton3.setBorderPainted(false);
		this.add(achievementButton3);
		//backgroundPanel.add(achievementButton3);

		// 显示成就的大图
		achievementLabel = new JLabel(new ImageIcon(
				"image/solo/initialAchievement.png"));// 先放置一张初始图片
		achievementLabel.setLocation(118, 100);
		achievementLabel.setSize(320, 180);
		this.add(achievementLabel);
		//backgroundPanel.add(achievementLabel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == shutButton) {
//			mainFrame.dispose();
//		}

		if (e.getSource() == achievementButton1) {
			achievementLabel.setIcon(bigAchievementImage1);
		}
		if (e.getSource() == achievementButton2) {
			achievementLabel.setIcon(bigAchievementImage2);
		}
		if (e.getSource() == achievementButton3) {
			achievementLabel.setIcon(bigAchievementImage3);
		}
	}

}
