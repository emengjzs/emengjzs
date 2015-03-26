package view.ui.gameui;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ComboPanel extends JPanel{
	@SuppressWarnings("unused")
	private GameController gc;
	int count = 0;
	Image[] comboImages = new Image[6];
	
	public ComboPanel(){
		readImage();
		this.setLayout(null);
		this.setSize(22, 450);
		this.setLocation(48,70);
		this.setOpaque(false);
		repaint();
	}
	
	public void setGameController(GameController gc) {
		this.gc = gc;
	}
	
	public void changeCount(int i){
		count = i;
		repaint();
	}
	
	private void readImage(){
		for(int i=0;i<=3;i++){
			try {
				comboImages[i] = ImageIO.read(new File("Image/combo"+i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		g.drawImage(comboImages[count], 0, 0, 22, 450, this);
	}
}
