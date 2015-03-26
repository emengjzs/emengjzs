package view.ui.myComponent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import setup.GameClient;

//抽象一个容纳图片和监听的JLabel
public class IconButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 1L;

	String path1;
	String path2;
	String path3;
	ImageIcon icon1;
	ImageIcon icon2;
	ImageIcon icon3;

	public IconButton(String path1, String path2, String path3) {
		this.path1 = path1;
		this.path2 = path2;
		this.path3 = path3;
		icon1 = new ImageIcon(path1);
		icon2 = new ImageIcon(path2);
		icon3 = new ImageIcon(path3);

		this.addMouseListener(this);

		this.setContentAreaFilled(false);// 去除按钮背景色
		this.setBorder(null);// 去除边框

		// this.setFocusable(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		// this.setOpaque(false);

		this.setIcon(icon1);// 设置初始图片
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		this.setIcon(icon2);
		if (GameClient.MusicOn) {
			Music music = new Music("music/button_enter.mp3", false);
			music.play();
		}
	}

	@Override
	public void mouseExited(MouseEvent e0) {
		this.setIcon(icon1);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.setIcon(icon3);
		if (GameClient.MusicOn) {
			new Music("music/button_click.mp3", false).play();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		this.setIcon(icon2);
	}

}
