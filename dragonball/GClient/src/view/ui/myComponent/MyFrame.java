package view.ui.myComponent;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MyFrame extends JFrame implements MouseMotionListener,
		MouseListener, ActionListener {
	JFrame myFrame;
	private int width = 1100;
	private int height = 700;

	JPanel backgroundPanel;// 背景panel

	// 拖拽窗口时点击鼠标的位置
	private int oldX;
	private int oldY;

	JPanel movePanel;
	IconButton miniButton;
	IconButton shutButton;

	public static void main(String[] args) {
		new MyFrame();
	}

	public MyFrame() {
		myFrame = new JFrame();
		this.setSize(width, height);
		this.setLocation(150, 20);
		this.setUndecorated(true);// 去除窗口边框

		initFrame();

		this.setVisible(true);
	}

	private void initFrame() {
		// Container contentPane = this.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"Imagesrc/login/background.png");
		backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
						null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		this.setContentPane(backgroundPanel);// backgroundPanel即为原contentPane

		movePanel = new JPanel();
		movePanel.setLayout(null);
		movePanel.setSize(width, height / 10);
		backgroundPanel.add(movePanel);
		movePanel.addMouseMotionListener(this);
		movePanel.addMouseListener(this);
		movePanel.setOpaque(false);

		miniButton = new IconButton("image/mini1.png", "image/mini2.png",
				"image/mini3.png");
		miniButton.addActionListener(this);
		miniButton.setLocation(950, 10);
		miniButton.setSize(50, 50);
		movePanel.add(miniButton);

		shutButton = new IconButton("image/shut1.png", "image/shut2.png",
				"image/shut3.png");
		shutButton.addActionListener(this);
		shutButton.setLocation(1010, 10);
		shutButton.setSize(50, 50);
		movePanel.add(shutButton);

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int xOnScreen = e.getXOnScreen();
		int yOnScreen = e.getYOnScreen();
		int xx = xOnScreen - oldX;
		int yy = yOnScreen - oldY;
		this.setLocation(xx, yy);

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getSource() == movePanel) {
			oldX = e.getX();
			oldY = e.getY();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == miniButton) {
			this.setExtendedState(JFrame.ICONIFIED);
		}
		if (e.getSource() == shutButton) {
			System.exit(0);
		}
	}

}
