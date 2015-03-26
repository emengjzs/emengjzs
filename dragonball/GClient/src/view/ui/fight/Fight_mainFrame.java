package view.ui.fight;

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

import controller.netlistenerbl.NetBattleGameHallListenerBL;
import controller.netlistenerbl.NetUserListenerBL;
import model.netbl.NetStatisBL;
import model.netservice.NetStatisBLService;
import model.vo.*;
import view.ui.hallui.BattleGameHallUI;
import view.ui.login.MainFrame;
import view.ui.myComponent.IconButton;
import view.uiservice.BattleStatisService;

public class Fight_mainFrame implements ActionListener, BattleStatisService {
	UserVO user;
	NetStatisBLService statisController;
	BattleStatisVO battleStatis;
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;

	IconButton enterGameButton;
	IconButton backButton;

	JLabel scoreLabel[] = new JLabel[5];
	JLabel nameLabel[] = new JLabel[5];// ÅÅÐÐ°ñÍæ¼ÒêÇ³Æ£ºÖ»Ð´Á½·½·¿Ö÷êÇ³Æ£¬ÇÒ×¢Ã÷ÄÄ·½Ê¤Àû--**VS**(Ê¤)
	
	int xOld = 0;
	int yOld = 0;

	public static void main(String[] args) {
		// new Fight_mainFrame();
	}

	public Fight_mainFrame(UserVO user) {
		// public Fight_mainFrame(){
		this.user = user;
		statisController = NetStatisBL.getStatisController();
		mainFrame = new JFrame();
		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);// È¥³ý´°¿Ú±ß¿ò

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
	}

	public void Request() {
		RequestStatis(user.getID());
	}

	private void RequestStatis(String userID) {
		statisController.getBattleStatic(userID);
	}

	@SuppressWarnings("serial")
	private void initFrame() {
		Container contentPane = mainFrame.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"image/fight/background_rank.png");
		JPanel backgroundPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
						null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);

		// ÅÅÐÐ°ñËùÔÚpanel
		JPanel rankingListPanel = new JPanel();
		rankingListPanel.setOpaque(false);
		rankingListPanel.setLocation(255, 160);
		rankingListPanel.setLayout(null);
		rankingListPanel.setSize(512, 402);
		backgroundPanel.add(rankingListPanel);

		// ÅÅÐÐ°ñêÇ³Æ
		for (int i = 0; i < 5; i++) {
			nameLabel[i] = new JLabel("");
			nameLabel[i].setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 18));
			nameLabel[i].setSize(283, 40);
			nameLabel[i].setLocation(75, 96 + i * 66);
			rankingListPanel.add(nameLabel[i]);
		}

		// ÅÅÐÐ°ñ·ÖÊý
		for (int i = 0; i < 5; i++) {
			scoreLabel[i] = new JLabel("");
			scoreLabel[i].setFont(new Font("ººÒÇµûÓïÌå¼ò", Font.BOLD, 18));
			scoreLabel[i].setSize(120, 40);
			scoreLabel[i].setLocation(335, 96 + i * 66);
			rankingListPanel.add(scoreLabel[i]);
		}

		enterGameButton = new IconButton("image/fight/enterGame1.png",
				"image/fight/enterGame2.png", "image/fight/enterGame3.png");
		enterGameButton.addActionListener(this);
		enterGameButton.setLocation(280, 600);
		enterGameButton.setSize(370, 63);
		backgroundPanel.add(enterGameButton);

		backButton = new IconButton("image/fight/back1.png",
				"image/fight/back2.png", "image/fight/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(30, 580);
		backButton.setSize(170, 65);
		backgroundPanel.add(backButton);
	}

	@SuppressWarnings("unused")
	private void showRank(ArrayList<BattleRecordVO> recordList) {
		for (int i = 0; i < 5; i++) {
			nameLabel[i].setText("");
			scoreLabel[i].setText("");
		}

		ArrayList<Integer> scoreList = new ArrayList<Integer>();
		ArrayList<BattleSortRecordVO> battleList=new ArrayList<BattleSortRecordVO>();
		for (int i = 0; i < 5; i++) {
			try {
				BattleRecordVO record = recordList.get(i);
				ArrayList<ActiveUser> userList1 = record.getPlayerList1();
				ArrayList<ActiveUser> userList2 = record.getPlayerList2();
				String message = "";
				String message1 = "";
				String message2 = "";
				int point = 0;
				int point1 = record.getPoint1();
				int point2 = record.getPoint2();
				int teamNO = 0;
				for (ActiveUser user1 : userList1) {
					if (user1.getUserID().equals(user.getID())) {
						teamNO = 1;
					}
					message1 = message1 + user1.getUserID() + " ";
				}
				for (ActiveUser user2 : userList2) {
					if (user2.getUserID().equals(user.getID())) {
						teamNO = 2;
					}
					message2 = message2 + user2.getUserID() + " ";
				}
				if (teamNO == 1) {
					message = message1;
					point = point1;
					
				} else if (teamNO == 2) {
					message = message2;
					point = point2;
				}
				BattleSortRecordVO vo=new BattleSortRecordVO(point,message);
				battleList.add(vo);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}

		Collections.sort(battleList);
		for (int i = 0; i < battleList.size(); i++) {
			try {
				scoreLabel[i].setText(battleList.get(i).getScore() + "");
				nameLabel[i].setText(battleList.get(i).getMessage());
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == enterGameButton) {
			BattleGameHallUI gameHall = new BattleGameHallUI(user);
			NetBattleGameHallListenerBL gameController = NetBattleGameHallListenerBL
					.getGameHallListener();
			gameController.setController(gameHall);
			gameHall.DoSomething();
			mainFrame.dispose();
		}
		if (e.getSource() == backButton) {
			MainFrame main = new MainFrame(user);
			NetUserListenerBL userController = NetUserListenerBL
					.getUserControllerListener();
			userController.setMainController(main);
			main.init();
			mainFrame.dispose();
		}

	}

	@Override
	public void receiveMessage(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);
		String[] info = message.split("\\|");
		try {
			int highestScore = Integer.parseInt(info[0]);
			String gameRecord = info[1];

			String[] singleRecord = gameRecord.split("=");
			ArrayList<BattleRecordVO> recordList = new ArrayList<BattleRecordVO>();
			for (int i = 0; i < singleRecord.length; i++) {
				ArrayList<ActiveUser> playerList1 = new ArrayList<ActiveUser>();
				ArrayList<ActiveUser> playerList2 = new ArrayList<ActiveUser>();
				String[] single = singleRecord[i].split(":");
				int gameID = Integer.parseInt(single[0]);
				int point1 = Integer.parseInt(single[3]);
				int point2 = Integer.parseInt(single[4]);

				String teamInfo1 = single[1];
				System.out.print("Team Info1: "+teamInfo1+" ");
				System.out.println("point1: "+point1);
				String teamInfo2 = single[2];
				System.out.print("Team Info2: "+teamInfo2+" ");
				System.out.println("point2: "+point2);

				String[] team1 = teamInfo1.split("/");
				for (int j = 0; j < team1.length; j++) {
					ActiveUser user = new ActiveUser(team1[j]);
					playerList1.add(user);
				}

				String[] team2 = teamInfo2.split("/");
				for (int j = 0; j < team2.length; j++) {
					ActiveUser user = new ActiveUser(team2[j]);
					playerList2.add(user);
				}

				BattleRecordVO record = new BattleRecordVO(gameID, point1,
						point2, playerList1, playerList2);
				recordList.add(record);
			}
			showRank(recordList);
			battleStatis = new BattleStatisVO(highestScore, recordList);
		} catch (ArrayIndexOutOfBoundsException e) {

		}

	}

}
