package view.ui.gameui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.netlistenerbl.NetUserListenerBL;
import model.netbl.NetErrorBL;
import model.netbl.NetUserBL;
import model.netservice.NetErrorBLService;
import model.netservice.NetUserBLService;
import model.vo.UserVO;
import setup.GameClient;
import view.ui.login.MainFrame;
import view.ui.myComponent.IconButton;
import view.uiservice.GameResultService;

@SuppressWarnings("serial")
public class gameOver extends JFrame implements GameResultService{
	String path1 = "Image/gameOver/gameOver_main.png";
	//1
	String path2 = "Image/gameOver/gameOver_over.png";
	//2
	String path3 = "Image/gameOver/gameOver_victory.png";
	//3
	String path4 = "Image/gameOver/gameOver_defeat.png";
	//4
	String path5 = "Image/gameOver/gameOver_draw.png";
	Image im1,im2,im3,im4,im5;
	JLabel messLabel;
	
	IconButton confirm;
	
	static NetUserBLService userController;
	static NetErrorBLService errorController;
	UserVO user;
	
//	boolean isVictory = true;
//	boolean Need = false;
	int kind = 1;
	
	int xOld = 0;
	int yOld = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new gameOver();
	}
	
	public gameOver(final int kind,final String[] result){
		if(GameClient.MusicOn){
			if(GameClient.music!=null){
				GameClient.music.restart();
				GameClient.music.play();
			}
		}
		
		this.kind=kind;
		userController = NetUserBL.getUserController();
		errorController=NetErrorBL.getErrorController();
		
		readImg();
		
		String confirm1  = "Image/gameOver/gameOver_confirm1.png";
		String confirm2  = "Image/gameOver/gameOver_confirm2.png";
		String confirm3  = "Image/gameOver/gameOver_confirm3.png";
		confirm = new IconButton(confirm1,confirm2,confirm3);
		confirm.addActionListener(new Listener());
		confirm.setLocation(350,520);
		confirm.setSize(400,100);		
		
		this.setContentPane(new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(im1,0,0,this.getWidth(),this.getHeight(),this);
				
				switch(kind){
				case 1:g.drawImage(im2,280,45,540,218,this);break;
				case 2:g.drawImage(im3,280,45,540,218,this);break;
				case 3:g.drawImage(im4,280,45,540,218,this);break;
				case 4:g.drawImage(im5,280,45,540,218,this);break;
				}
				
//				g.drawImage(im4,280,45,540,218,this);
				
//				if(Need){
//					if(isVictory){
//						g.drawImage(im2,280,45,540,218,this);
//					}else{
//						g.drawImage(im3,280,45,540,218,this);
//					}
//				}

			}
		});
		this.getContentPane().setBackground(null);

		messLabel = new JLabel();//("<html>" +"得分："+"<br>"+"<br>"+"经验："+"<br>"+"金币："+"<br>"+""+"</html>");
		messLabel.setFont(new Font("汉仪蝶语体简", Font.BOLD, 38));
		messLabel.setSize(500,400);
		messLabel.setLocation(320,190);
		
		for(int i=0;i<5;i++){
			if(result[i].equals("")){
				result[i]="无";
			}
		}
		
		String s1="<html>";
		String s2="得分："+result[0]+"<br>";
		String s3="经验："+result[1]+"<br>";
		String s4="金币："+result[2]+"<br>";
		String s5="道具："+result[3]+"<br>";
		String s6="奖励："+result[4]+"<br>";
		
		String toShow = s1+s2+s3+s4+s5+s6;
		messLabel.setText(toShow);
		
		this.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mousePressed(MouseEvent e) {  
                xOld = e.getX();  
                yOld = e.getY();
            }  
        });  
		this.addMouseMotionListener(new MouseMotionAdapter() {  
            @Override  
            public void mouseDragged(MouseEvent e) {  
                int xOnScreen = e.getXOnScreen();  
                int yOnScreen = e.getYOnScreen();  
                int xx = xOnScreen - xOld;  
                int yy = yOnScreen - yOld;
                
                gameOver.this.setLocation(xx, yy);  
            }  
        });
		
		
		
		confirm.setVisible(false);
		
		this.setLayout(null);
		this.add(confirm);
		this.add(messLabel);
		
		this.setSize(1100, 700);
		this.setUndecorated(true);//去除窗口边框
		this.setBackground(new Color(0,0,0,0)); //将窗口变透明
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		this.setIconImage(new ImageIcon("Image/logo.png").getImage());
	}
	
	public void requestUser(String userID){
		errorController.Leave(userID);
		userController.getUserInfo(userID);
	}
	
//	public void paintComponent(Graphics g){
//		//super.paint(g);
//		g.drawImage(im1,0,0,this.getWidth(),this.getHeight(),this);
//		
//		if(isVictory){
//			g.drawImage(im2,280,45,540,218,this);
//		}else{
//			g.drawImage(im3,280,45,540,218,this);
//		}
//	}
	
	public void readImg(){
		try {
			im1= ImageIO.read(new File(path1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			im2= ImageIO.read(new File(path2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			im3= ImageIO.read(new File(path3));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			im4= ImageIO.read(new File(path4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			im5= ImageIO.read(new File(path5));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public class Listener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO 自动生成的方法存根
			System.out.println("OK");
			MainFrame main = new MainFrame(user);
			NetUserListenerBL userController=NetUserListenerBL.getUserControllerListener();
			userController.setMainController(main);
			main.init();
			gameOver.this.dispose();
		}
		
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println("Result Panel receive: " +message);
		String[] info = message.split("-");
		UserVO user = new UserVO(info[0], info[1] ,Integer.parseInt(info[2]), Integer.parseInt(info[3]),Integer.parseInt(info[4]),Integer.parseInt(info[5]));
		this.user=user;
		confirm.setVisible(true);
	}


}
