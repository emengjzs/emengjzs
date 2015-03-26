package view.ui.solo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.Service;
import controller.netlistenerbl.NetSingleListenerBL;
import controller.netlistenerbl.NetUserListenerBL;
import model.netbl.NetStatisBL;
import model.netservice.NetStatisBLService;
import model.vo.CoopRecordVO;
import model.vo.SingleStatisVO;
import model.vo.SingleTenRecordVO;
import model.vo.SingleWeekRecordVO;
import model.vo.UserVO;
import view.ui.login.MainFrame;
import view.ui.myComponent.IconButton;
import view.uiservice.SingleStatisService;


@SuppressWarnings("serial")
public class Solo_mainFrame extends JFrame implements ActionListener,SingleStatisService{
	UserVO user;
	SingleStatisVO singleStatis=new SingleStatisVO();
	NetStatisBLService statisController;
	MapPanel mapPanel;
	///JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	IconButton enterGameButton;
	IconButton backButton;
	
	IconButton historyButton;
	IconButton achievementButton;
	IconButton mapButton;
	
	JLabel scoreLabel[] = new JLabel[5];
	
	int xOld = 0;
	int yOld = 0;
	
	JFrame soloHistoryFrame;
	JFrame soloAchievementFrame;
	JFrame soloMapFrame;
	
//	public static void main(String[] args) {
//	//	new Solo_mainFrame();
//	}
	
	public Solo_mainFrame(UserVO user){
	//public Solo_mainFrame(){
		this.user=user;
		statisController=NetStatisBL.getStatisController();
		
		setIconImage(new ImageIcon("Image/logo.png").getImage());
		///mainFrame = new JFrame();
		///mainFrame.setSize(width, height);
		///mainFrame.setLocation(150, 20);
		///mainFrame.setUndecorated(true);//去除窗口边框
		this.setSize(width, height);
		this.setLocation(150, 20);
		this.setUndecorated(true);
		this.setBackground(new Color(0,0,0,0));
		initFrame();
		
		///mainFrame.setVisible(true);
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
                
                Solo_mainFrame.this.setLocation(xx, yy);  
            }  
        });
		
		
		RequestStatis(user.getID());
		RequestTen(user.getID());
		RequestDaily(user.getID());
		RequestTop(user.getID());
		
	}
	
	private void RequestStatis(String userID){
		statisController.getSingleStatic(userID);
	}
	
	private void RequestTen(String userID){
		statisController.getSingleTen(userID);
	}
	
	private void RequestDaily(String userID){
		statisController.getSingleDaily(userID);
	}
	
	private void RequestTop(String userID){
		statisController.getSingleTop(userID);
	}
	
	private void initFrame(){
		///Container contentPane = mainFrame.getContentPane();
		Container contentPane = this.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/solo/background_rank.png");
		JPanel backgroundPanel = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(backgroundImage.getImage(), 0, 0, width,height,null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		
		enterGameButton = new IconButton("image/solo/enterGame1.png","image/solo/enterGame2.png","image/solo/enterGame3.png");
		enterGameButton.addActionListener(this);
		enterGameButton.setLocation(280, 600);
		enterGameButton.setSize(370, 63);
		backgroundPanel.add(enterGameButton);
		
		backButton = new IconButton("image/solo/back1.png","image/solo/back2.png","image/solo/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(30, 580);
		backButton.setSize(170, 65);
		backgroundPanel.add(backButton);
		
		
		//历史记录按钮
		historyButton = new IconButton("image/solo/history1.png","image/solo/history2.png","image/solo/history3.png");
		historyButton.addActionListener(this);
		historyButton.setLocation(850, 220);
		historyButton.setSize(245, 75);
		backgroundPanel.add(historyButton);
		
		//特殊成就按钮
		achievementButton = new IconButton("image/solo/achievement1.png","image/solo/achievement2.png","image/solo/achievement3.png");
		achievementButton.addActionListener(this);
		achievementButton.setLocation(850, 300);
		achievementButton.setSize(245, 75);
		backgroundPanel.add(achievementButton);
		
		//挑战地图按钮
		mapButton = new IconButton("image/solo/map1.png","image/solo/map2.png","image/solo/map3.png");
		mapButton.addActionListener(this);
		mapButton.setLocation(850, 380);
		mapButton.setSize(245, 75);
		backgroundPanel.add(mapButton);
		
		JPanel rankingListPanel = new JPanel(){
//			private static final long serialVersionUID = 1L;
//			ImageIcon rankingListImage = new ImageIcon("image/solo/soloBackground.png");
//			public void paintComponent(Graphics g){
//				//super.paint(g);
//				rankingListImage.setImage(rankingListImage.getImage().getScaledInstance(500, 400, Image.SCALE_DEFAULT));
//				g.drawImage(rankingListImage.getImage(), 0, 0, null);
//			}
		};
		rankingListPanel.setOpaque(false);
		rankingListPanel.setLocation(255, 160);
		rankingListPanel.setLayout(null);
		rankingListPanel.setSize(512, 402);
		backgroundPanel.add(rankingListPanel);	
		
		
		//得分最高的5次成绩
		for(int i=0;i<5;i++){
			scoreLabel[i] = new JLabel();
			scoreLabel[i].setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
			scoreLabel[i].setSize(190, 35);
			scoreLabel[i].setLocation(120, 96+i*66);
			rankingListPanel.add(scoreLabel[i]);
		}
		
	}
	
	@SuppressWarnings("unused")
	private void showRank(ArrayList<CoopRecordVO> recordList){
		for(int i=0;i<recordList.size();i++){
			scoreLabel[i].setText("");
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==enterGameButton){
			this.dispose();
			///mainFrame.dispose();
			if(mapPanel!=null){
				user=mapPanel.getUser();
			}
			Solo_prepareGame solo=new Solo_prepareGame(user);
			NetSingleListenerBL singleController=NetSingleListenerBL.getSingleListener();
			singleController.setSingleController(solo);
			solo.init();
		}
		if(e.getSource()==backButton){
			MainFrame main = new MainFrame(user);
			NetUserListenerBL userController=NetUserListenerBL.getUserControllerListener();
			userController.setMainController(main);
			main.init();
			this.dispose();
			///mainFrame.dispose();
		}
		
		if(e.getSource()==historyButton){
			//new HistoryPanel(singleStatis.getTotal(),singleStatis.getAveragePoint(),singleStatis.getHighestPoint(),singleStatis.getHighestCombo(),singleStatis.getWeekList(),singleStatis.getTenList());
			this.setEnabled(false);
			new CoopHistory(singleStatis.getTotal(),singleStatis.getAveragePoint(),singleStatis.getHighestPoint(),singleStatis.getHighestCombo(),singleStatis.getWeekList(),singleStatis.getTenList(),this);
		}
		if(e.getSource()==achievementButton){
			boolean b1 = false;
			boolean b2 = false;
			boolean b3 = false;
			if(singleStatis.getHighestPoint()>=1000000){
				b1=true;
			}
			if(singleStatis.getHighestCombo()>=20){
				b2=true;
			}
			if(singleStatis.getTotal()>=1000){
				b3=true;
			}
			this.setEnabled(false);
			new CoopAchievement(b1,b2,b3,this);
		}
		if(e.getSource()==mapButton){
			//map=new MapPanel(user);
			this.setEnabled(false);
			new CoopMap(user,this);
		}
		
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		String[] info = message.split(" ");
		
		if(info[1].equals(Service.ReplySingleStatis.toString())){
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if(info.length>=3){
				setMainStatis(info[2]);
			}
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}else if(info[1].equals(Service.ReplySingleTen.toString())){
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if(info.length>=3){
				setTen(info[2]);
			}
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}else if(info[1].equals(Service.ReplySingleDaily.toString())){
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if(info.length>=3){
				setDaily(info[2]);
			}
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}else if(info[1].equals(Service.ReplySingleTop.toString())){
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			if(info.length>=3){
				setTop(info[2]);
			}
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		}
	}
	
	private void setTop(String message){
		String[] info=message.split("/");
		
		for(int i=0;i<info.length;i++){
			scoreLabel[i].setText(info[i]);
		}
	}
	
	private void setMainStatis(String message){
		String[] info=message.split("\\|");
		int total=Integer.parseInt(info[0]);
		int highestScore=Integer.parseInt(info[1]);
		int highestCombo=Integer.parseInt(info[2]);
		int averageScore=Integer.parseInt(info[3]);
		
		singleStatis.setTotal(total);
		singleStatis.setHighestPoint(highestScore);
		singleStatis.setHighestCombo(highestCombo);
		singleStatis.setAveragePoint(averageScore);
	}
	
	private void setTen(String message){
		System.out.println(message);
		String[] info=message.split("/");
		ArrayList<SingleTenRecordVO> recordList=new ArrayList<SingleTenRecordVO>();
		
		for(int i=0;i<info.length;i++){
			String[] singleInfo=info[i].split(":");
			int gameID=Integer.parseInt(singleInfo[0]);
			int score=Integer.parseInt(singleInfo[1]);
			SingleTenRecordVO record=new SingleTenRecordVO(gameID,score);
			recordList.add(record);
		}
		
		Collections.sort(recordList);
		singleStatis.setTenList(recordList);
	}
	
	private void setDaily(String message){
		System.out.println("Daily: "+message);
		String[] info=message.split("/");
		ArrayList<SingleWeekRecordVO> recordList=new ArrayList<SingleWeekRecordVO>();
		
		for(int i=0;i<info.length;i++){
			String[] singleInfo=info[i].split(":");
			int gameID=Integer.parseInt(singleInfo[0]);
			int total=Integer.parseInt(singleInfo[1]);
			int averagePoint=Integer.parseInt(singleInfo[2]);
			
			SingleWeekRecordVO record=new SingleWeekRecordVO(gameID,total,averagePoint);
			recordList.add(record);
		}
		
		Collections.sort(recordList);
		singleStatis.setWeekList(recordList);
	}

	class CoopHistory implements ActionListener{
		private int width = 650;
		private int height = 400;
		JFrame mainFrame;
		
		public CoopHistory(int total, int average, int highestPoint, int highestCombo,
				ArrayList<SingleWeekRecordVO> recordList,
				ArrayList<SingleTenRecordVO> tenRecordList,JFrame mainFrame){
			this.mainFrame = mainFrame;
			soloHistoryFrame = new JFrame();
			soloHistoryFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
			soloHistoryFrame.setSize(width, height);
			soloHistoryFrame.setLocation(360, 200);
			soloHistoryFrame.setUndecorated(true);// 去除窗口边框
			soloHistoryFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明
			
			Container contentPane = soloHistoryFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/solo/historyBackground.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
							null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);

			IconButton shutButton = new IconButton("image/solo/shut1.png",
					"image/solo/shut2.png", "image/solo/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(514, 30);
			shutButton.setSize(50, 50);
			backgroundPanel.add(shutButton);
			
			HistoryPanel historyPanel = new HistoryPanel(singleStatis.getTotal(),singleStatis.getAveragePoint(),singleStatis.getHighestPoint(),singleStatis.getHighestCombo(),singleStatis.getWeekList(),singleStatis.getTenList());
			historyPanel.setSize(width, height);
			backgroundPanel.add(historyPanel);
			
			soloHistoryFrame.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent e) {
			mainFrame.setEnabled(true);
			soloHistoryFrame.dispose();
		}

		
	}

	class CoopMap implements ActionListener{
		private int width = 650;
		private int height = 400;
		JFrame mainFrame;
		
		public CoopMap(UserVO user,JFrame mainFrame){
			this.mainFrame = mainFrame;
			soloMapFrame = new JFrame();
			soloMapFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
			soloMapFrame.setSize(width, height);
			soloMapFrame.setLocation(360, 200);
			soloMapFrame.setUndecorated(true);// 去除窗口边框
			soloMapFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明
			
			Container contentPane = soloMapFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/solo/toolBackground.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
							null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);
			
			IconButton shutButton = new IconButton("image/solo/shut1.png",
					"image/solo/shut2.png", "image/solo/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(514, 30);
			shutButton.setSize(50, 50);
			backgroundPanel.add(shutButton);
			
			mapPanel = new MapPanel(user);
			mapPanel.setSize(width, height);
			backgroundPanel.add(mapPanel);
			
			soloMapFrame.setVisible(true);
		}
		public void actionPerformed(ActionEvent arg0) {
			mainFrame.setEnabled(true);
			soloMapFrame.dispose();
		}
		
	}

	class CoopAchievement implements ActionListener{
		private int width = 650;
		private int height = 400;
		JFrame mainFrame;
		
		public CoopAchievement(boolean b1, boolean b2, boolean b3,JFrame mainFrame){
			this.mainFrame = mainFrame;
			soloAchievementFrame = new JFrame();
			soloAchievementFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
			soloAchievementFrame.setSize(width, height);
			soloAchievementFrame.setLocation(360, 200);
			soloAchievementFrame.setUndecorated(true);// 去除窗口边框
			soloAchievementFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = soloAchievementFrame.getContentPane();

			final ImageIcon backgroundImage = new ImageIcon(
					"image/solo/toolBackground.png");
			JPanel backgroundPanel = new JPanel() {
				public void paintComponent(Graphics g) {
					g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
							null);
				}
			};
			backgroundPanel.setLayout(null);
			backgroundPanel.setSize(width, height);
			contentPane.add(backgroundPanel);

			IconButton shutButton = new IconButton("image/solo/shut1.png",
					"image/solo/shut2.png", "image/solo/shut3.png");
			shutButton.addActionListener(this);
			shutButton.setLocation(514, 30);
			shutButton.setSize(50, 50);
			backgroundPanel.add(shutButton);
			
			AchievementPanel achievementPanel = new AchievementPanel(b1,b2,b3);
			achievementPanel.setSize(width, height);
			backgroundPanel.add(achievementPanel);

			soloAchievementFrame.setVisible(true);
		}
		public void actionPerformed(ActionEvent arg0) {
			mainFrame.setEnabled(true);
			soloAchievementFrame.dispose();
		}
		
	}

}

	





