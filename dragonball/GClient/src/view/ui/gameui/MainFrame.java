package view.ui.gameui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import setup.GameClient;
import view.ui.myComponent.Music;

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	private int width = 1100;
	private int height = 700;
	
	GameController main = null;
	GameController minor = null;
	
	timePanel time = null;
	
	int xOld = 0;
	int yOld = 0;
//	Listener1 l1 = new Listener1();
//	Listener2 l2 = new Listener2();
	
	public MainFrame(BoardPanel boardPanel) {
//		setTitle("DrangonBall");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setLayout(null);
//		setResizable(false);
		
		this.setUndecorated(true);
		
//		this.addMouseListener(l1);  
//		this.addMouseMotionListener(l2);
		
		GameGround gameground = new GameGround();
		add(gameground);
		add(boardPanel);
		
		time = new timePanel();
		add(time);
	}
	
	/*
	 * 添加构造方法
	 */
	public MainFrame() {
		setTitle("DrangonBall");
		setIconImage(new ImageIcon("Image/logo.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1100, 700);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setUndecorated(true);
		this.setBackground(new Color(0,0,0,0));
		
//		this.addMouseListener(new MouseAdapter() {  
//            @Override  
//            public void mousePressed(MouseEvent e) {  
//                xOld = e.getX();  
//                yOld = e.getY();
//            }  
//        });  
//		this.addMouseMotionListener(new MouseMotionAdapter() {  
//            @Override  
//            public void mouseDragged(MouseEvent e) {  
//                int xOnScreen = e.getXOnScreen();  
//                int yOnScreen = e.getYOnScreen();  
//                int xx = xOnScreen - xOld;  
//                int yy = yOnScreen - yOld;
//                
//                MainFrame.this.setLocation(xx, yy);  
//            }  
//        });
		
		GameGround gameground = new GameGround();
		this.setContentPane((gameground));
		
		time = new timePanel();
		add(time);
	}
	
	public void setMainController(GameController main){
		this.main = main;
		main.setLocation(23, 85);
		this.add(main.getScore());
		this.add(main.getCombo());
		this.add(main.getBoard());
		this.add(main.getCount());
		if(!main.getMode().toString().equals("Battle")){
			this.add(main.getHit());
		}
	}
	
	public void setMinorController(GameController minor){
		this.minor = minor;
		minor.setLocation(620,155);
		this.add(minor.getBoard());
		this.add(minor.getScore());
	}
	
	public void game_Start(){
		this.repaint();
		this.setVisible(true);
	}

	/**
	 * 准备游戏
	 */
	public void game_Ready(){
		ReadyRunnable end = new ReadyRunnable("image/count.gif", 500, 350,
				150, 150, 6, this);
		end.run();
	}
	
	public void game_End(){
		main.getBoard().removeListener();
	}
	
	/*
	 * 到此结束
	 */
	
	public class GameGround extends JComponent {
		private final String path = "image/Design_8.png";//"image/gameground.png";
		private Image gamegroundImg;

		public GameGround() {
			this.setSize(width , height);
			readImg();
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(gamegroundImg, 0, 0, width, height, this);
		}

		public void update(Graphics g) {
			if (gamegroundImg == null) {
				readImg();
			}
			Graphics gImg = gamegroundImg.getGraphics();
			paint(gImg);
			g.drawImage(gamegroundImg, 0, 0, width, height, null);
		}

		private void readImg() {
			try {
				gamegroundImg = ImageIO.read(new File(path));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class Listener1 extends MouseAdapter{ 
		
        public void mousePressed(MouseEvent e) {  
            xOld = e.getX();  
            yOld = e.getY();
        }  
    }
	
	public class Listener2  extends MouseMotionAdapter{  

        public void mouseDragged(MouseEvent e) {  
            int xOnScreen = e.getXOnScreen();  
            int yOnScreen = e.getYOnScreen();  
            int xx = xOnScreen - xOld;  
            int yy = yOnScreen - yOld;
            
            MainFrame.this.setLocation(xx, yy);  
        }  
    }
	
	public class ReadyRunnable {
		MainFrame mainFrame;
		String imagePath;
		int loc_x;
		int loc_y;
		int width;
		int height;
		int delay;
		Timer timer;
		ReadySplashScreen window;
		public ReadyRunnable(String path, int loc_x, int loc_y, int width,
				int height, int time, MainFrame mainFrame) {
			this.imagePath = path;//获得图片的地址	
			this.loc_x = loc_x;
			this.loc_y = loc_y;
			this.width = width;
			this.height = height;
			this.delay = time;
			this.mainFrame = mainFrame;
		}
		
		public void  run() {
			window = new ReadySplashScreen(imagePath);
			window.setLocation(loc_x, loc_y);
			window.setSize(width, height);
			window.setVisible(true);
			//window.set
//			window.label.setOpaque(false);
			timer = new Timer();
			timer.schedule(new endTask(), delay * 1000);
		}
		
		public class endTask extends TimerTask {

			@Override
			public void run() {
				if(window != null) {
					window.dispose();
					window = null;
				}
				if (GameClient.MusicOn) {
					Music music = new Music("music/game_begin.mp3", false);
					music.play();
					Music main = new Music("music/main.mp3", false);
					main.play();
				}
				main.getBoard().addListener();
				time.start();
				main.endReady();	//通知游戏开始
				timer.cancel();
			}
			
		}
		
	}
	
	public class ReadySplashScreen  extends JWindow{
//		private static final long serialVersionUID = 1L;
		private JLabel label;
		private JPanel panel;
		public ReadySplashScreen(){
			this.setBackground(new Color(0,0,0,0));
		}

		public ReadySplashScreen(String imagePath){
			this.setBackground(new Color(0,0,0,0));
			panel = new JPanel(new BorderLayout());
			label = new JLabel();
			label.setIcon(new ImageIcon(imagePath));
			panel.setOpaque(false);
			panel.add(BorderLayout.CENTER, label);
			this.setContentPane(panel);
		}

	}
}
