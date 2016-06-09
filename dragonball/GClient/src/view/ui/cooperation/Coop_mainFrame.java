package view.ui.cooperation;

import java.awt.Color;
import java.awt.Container;
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

import controller.netlistenerbl.NetCoopGameHallListenerBL;
import controller.netlistenerbl.NetUserListenerBL;
import model.netbl.NetStatisBL;
import model.netservice.NetStatisBLService;
import model.vo.ActiveUser;
import model.vo.CoopRecordSortVO;
import model.vo.CoopRecordVO;
import model.vo.CoopStatisVO;
import model.vo.UserVO;
import view.ui.hallui.CoopGameHallUI;
import view.ui.login.MainFrame;
import view.ui.myComponent.IconButton;
import view.uiservice.CoopStatisService;

import java.awt.Font;

public class Coop_mainFrame implements ActionListener,CoopStatisService{
	UserVO user;
	CoopStatisVO coopStatis;
	NetStatisBLService statisController;
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	IconButton enterGameButton;
	IconButton backButton;
	IconButton ballButton;
	IconButton historyButton;
	JLabel nameLabel[];
	JLabel scoreLabel[];
	
	int xOld = 0;
	int yOld = 0;
	
	JFrame coopHistoryFrame;
	
	//public static void main(String[] args) {
	//	new Coop_mainFrame();
	//}
	
	public Coop_mainFrame(UserVO user){
	//public Coop_mainFrame(){
		
		this.user = user;
		statisController=NetStatisBL.getStatisController();
		
		mainFrame = new JFrame();
		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);//去除窗口边框
		
		initFrame();
		
		mainFrame.addMouseListener(new MouseAdapter() {  
            @Override  
            public void mousePressed(MouseEvent e) {  
                xOld = e.getX();  
                yOld = e.getY();
            }  
        });  
		mainFrame.addMouseMotionListener(new MouseMotionAdapter() {  
            @Override  
            public void mouseDragged(MouseEvent e) {  
                int xOnScreen = e.getXOnScreen();  
                int yOnScreen = e.getYOnScreen();  
                int xx = xOnScreen - xOld;  
                int yy = yOnScreen - yOld;
                
                mainFrame.setLocation(xx, yy);  
            }  
        });
		
		mainFrame.setBackground(new Color(0,0,0,0));
		mainFrame.setVisible(true);
		RequestStatis(user.getID());
	}
	
	private void RequestStatis(String userID){
		statisController.getStatistics(userID);
	}
	
	@SuppressWarnings("serial")
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/cooperation/background_rank.png");
		JPanel backgroundPanel = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		
		enterGameButton = new IconButton("image/cooperation/enterGame1.png","image/cooperation/enterGame2.png","image/cooperation/enterGame3.png");
		enterGameButton.addActionListener(this);
		enterGameButton.setLocation(280, 600);
		enterGameButton.setSize(370, 63);
		backgroundPanel.add(enterGameButton);
		
		backButton = new IconButton("image/cooperation/back1.png","image/cooperation/back2.png","image/cooperation/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(30, 580);
		backButton.setSize(170, 65);
		backgroundPanel.add(backButton);
		
		//排行榜所在panel
		JPanel rankingListPanel = new JPanel();
		rankingListPanel.setOpaque(false);
		rankingListPanel.setLocation(255, 160);
		rankingListPanel.setLayout(null);
		rankingListPanel.setSize(512, 402);
		backgroundPanel.add(rankingListPanel);	
		
		nameLabel = new JLabel[5];//排行榜中玩家昵称
		for(int i=0;i<5;i++){
			nameLabel[i] = new JLabel("");
			nameLabel[i].setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
			nameLabel[i].setSize(283, 40);
			nameLabel[i].setLocation(75, 96+i*66);
			rankingListPanel.add(nameLabel[i]);
		}
		
		scoreLabel = new JLabel[5];//排行榜中玩家分数
		for(int i=0;i<5;i++){
			scoreLabel[i] = new JLabel("");
			scoreLabel[i].setFont(new Font("汉仪蝶语体简", Font.BOLD, 18));
			scoreLabel[i].setSize(120, 40);
			scoreLabel[i].setLocation(335, 96+i*66);
			rankingListPanel.add(scoreLabel[i]);
			
		}
		
		//七星珠
		ballButton = new IconButton("image/cooperation/ball1.png", "image/cooperation/ball2.png", "image/cooperation/ball3.png");
		ballButton.addActionListener(this);
		ballButton.setLocation(850, 250);
		ballButton.setSize(245, 76);
		backgroundPanel.add(ballButton);
		
		
		//历史记录按钮
		historyButton = new IconButton("image/cooperation/history1.png", "image/cooperation/history2.png", "image/cooperation/history3.png");
		historyButton.addActionListener(this);
		historyButton.setLocation(850, 340);
		historyButton.setSize(245, 76);
		backgroundPanel.add(historyButton);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==enterGameButton){
			mainFrame.dispose();
			CoopGameHallUI gameHall=new CoopGameHallUI(user);
			NetCoopGameHallListenerBL gameController=NetCoopGameHallListenerBL.getGameHallListener();
			gameController.setController(gameHall);
			gameHall.DoSomething();
//			new Coop_prepareGame();
		}
		if(e.getSource()==backButton){
			MainFrame main = new MainFrame(user);
			NetUserListenerBL userController=NetUserListenerBL.getUserControllerListener();
			userController.setMainController(main);
			main.init();
			mainFrame.dispose();
		}
		if(e.getSource()==historyButton){
			new CoopHistory(coopStatis.getTotal(),coopStatis.getAveragePoint(),coopStatis.getHighestPoint(),coopStatis.getHighestCombo(),coopStatis.getRecordList());
			mainFrame.setEnabled(false);
			//new Coop_HistoryPanel(coopStatis.getTotal(),coopStatis.getAveragePoint(),coopStatis.getHighestPoint(),coopStatis.getHighestCombo(),coopStatis.getRecordList());
		}
		if(e.getSource()==ballButton){
			new CoopBall(user);
			mainFrame.setEnabled(false);
		}
	}
	
	@SuppressWarnings("unused")
	private void showRank(ArrayList<CoopRecordVO> recordList){
		for(int i=0;i<5;i++){
			nameLabel[i].setText("");
			scoreLabel[i].setText("");
		}
		
		ArrayList<Integer> scoreList=new ArrayList<Integer>();
		ArrayList<CoopRecordSortVO> coopRecordList=new ArrayList<CoopRecordSortVO>();
		for(int i=0;i<5;i++){
			try{
				CoopRecordVO record=recordList.get(i);
				CoopRecordSortVO re=new CoopRecordSortVO(record.getPoint(),record.getUserList());
				coopRecordList.add(re);
	/*			String message="";
				for(ActiveUser user:userList){
					message=message+user.getUserID()+" ";
				}
				scoreList.add(-1*record.getPoint());
				nameLabel[i].setText(message);
//				scoreLabel[i].setText(record.getPoint()+"");*/
			}catch(IndexOutOfBoundsException e){
				break;
			}
		}
		
		Collections.sort(coopRecordList);
		for(int i=0;i<coopRecordList.size();i++){
			try{
				scoreLabel[i].setText(""+coopRecordList.get(i).getScore());
				ArrayList<ActiveUser> userList=coopRecordList.get(i).getUserList();
				String message="";
				for(ActiveUser user:userList){
					message=message+user.getUserID()+" ";
				}
				nameLabel[i].setText(message);
			}catch(ArrayIndexOutOfBoundsException e){
				e.printStackTrace();
			}
		}
	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		String info[]=message.split("\\|");
		System.out.println(info[0]);
		
		try{
			int total=Integer.parseInt(info[0]);
			int highestPoint=Integer.parseInt(info[1]);
			int highestCombo=Integer.parseInt(info[2]);
			int averagePoint=Integer.parseInt(info[3]);
			String coopRecordMessage=info[4];
			String coopInfo[]=coopRecordMessage.split("=");
			ArrayList<CoopRecordVO> recordList=new ArrayList<CoopRecordVO>();
			for(int i=0;i<coopInfo.length;i++){
				String singleInfo=coopInfo[i];
				String[] single=singleInfo.split(":");
				
				int gameID=Integer.parseInt(single[0]);
				int point=Integer.parseInt(single[3]);
				
				String userInfo=single[2];
				
				String[] singleUser=userInfo.split("/");
				ArrayList<ActiveUser> userList=new ArrayList<ActiveUser>();
				for(int j=0;j<singleUser.length;j++){
					System.out.println(singleUser[j]);
					ActiveUser user=new ActiveUser(singleUser[j]);
					userList.add(user);
				}
				CoopRecordVO coopRecord=new CoopRecordVO(gameID,point,userList);
				recordList.add(coopRecord);
			}
			showRank(recordList);
			coopStatis=new CoopStatisVO(total,highestPoint,highestCombo,averagePoint,recordList);
		}catch(ArrayIndexOutOfBoundsException e){
			
		}
		
	}

	
	class CoopHistory implements ActionListener{
		private int width = 650;
		private int height = 400;
		
		@SuppressWarnings("serial")
		public CoopHistory(int total, int average, int highestPoint,
				int highestCombo, ArrayList<CoopRecordVO> recordList){
			coopHistoryFrame = new JFrame();
			coopHistoryFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
			coopHistoryFrame.setSize(width, height);
			coopHistoryFrame.setLocation(360, 200);
			coopHistoryFrame.setUndecorated(true);// 去除窗口边框
			coopHistoryFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = coopHistoryFrame.getContentPane();

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
			
			Coop_HistoryPanel coop_HistoryPanel = new Coop_HistoryPanel(coopStatis.getTotal(),coopStatis.getAveragePoint(),coopStatis.getHighestPoint(),coopStatis.getHighestCombo(),coopStatis.getRecordList());
			coop_HistoryPanel.setSize(width, height);
			backgroundPanel.add(coop_HistoryPanel);
			
			coopHistoryFrame.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent arg0) {
			mainFrame.setEnabled(true);
			coopHistoryFrame.dispose();
		}
		
	}
	
	
	class CoopBall implements ActionListener{
		private int width = 650;
		private int height = 400;
		
		@SuppressWarnings("serial")
		public CoopBall(UserVO user){
			coopHistoryFrame = new JFrame();
			coopHistoryFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
			coopHistoryFrame.setSize(width, height);
			coopHistoryFrame.setLocation(360, 200);
			coopHistoryFrame.setUndecorated(true);// 去除窗口边框
			coopHistoryFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

			Container contentPane = coopHistoryFrame.getContentPane();

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
			
			Coop_BallPanel coop_BallPanel = new Coop_BallPanel(user);
			coop_BallPanel.setSize(width, height);
			backgroundPanel.add(coop_BallPanel);
			
			coopHistoryFrame.setVisible(true);
		}
		
		public void actionPerformed(ActionEvent arg0) {
			mainFrame.setEnabled(true);
			coopHistoryFrame.dispose();
		}
		
	}
}











