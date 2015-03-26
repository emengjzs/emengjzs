package view.ui.cooperation;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.RoomState;
import view.ui.myComponent.IconButton;


//游戏房间列表
public class Coop_prepareGame implements ActionListener, MouseListener{
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	IconButton backButton;
	
	ImageIcon availableImage = new ImageIcon("image/cooperation/available.png");
	ImageIcon fullImage = new ImageIcon("image/cooperation/full.png");
	ImageIcon gamingImage = new ImageIcon("image/cooperation/gaming.png");
	
	//4个房间的状态
	JLabel tipLabel[] = new JLabel[4];
	
	//每个房间的状态
	JLabel tipLabel1;
	JLabel tipLabel2;
	JLabel tipLabel3;
	JLabel tipLabel4;
	
	//每个房间
	JLabel houseJLabel1;
	JLabel houseJLabel2;
	JLabel houseJLabel3;
	JLabel houseJLabel4;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Coop_prepareGame c = new Coop_prepareGame();
		
	}
	
	public Coop_prepareGame(){
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);//去除窗口边框
		
		initFrame();
		mainFrame.setVisible(true);
		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
	}
		
	@SuppressWarnings("serial")
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/cooperation/background.png");
		JPanel backgroundPanel = new JPanel(){
			public void paintComponent(Graphics g){
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		
		backButton =new IconButton("image/cooperation/back1.png","image/cooperation/back2.png","image/cooperation/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(790, 528);
		backButton.setSize(80, 80);
		backgroundPanel.add(backButton);
		
		
		for(int i=0;i<4;i++){
			tipLabel[i] = new JLabel();
			if(i<2){
				tipLabel[i].setLocation(550+270*i, 212);
			}else{
				tipLabel[i].setLocation(550+270*(i-2), 405);
			}
			
			tipLabel[i].setSize(120, 50);
			
			
			backgroundPanel.add(tipLabel[i]);
		}
			
		houseJLabel1 = new JLabel(new ImageIcon("image/cooperation/house1.png"));
		houseJLabel1.addMouseListener(this);
		houseJLabel1.setLocation(471, 153);
		houseJLabel1.setSize(210, 140);
		backgroundPanel.add(houseJLabel1);
		
		houseJLabel2 = new JLabel(new ImageIcon("image/cooperation/house2.png"));
		houseJLabel2.addMouseListener(this);
		houseJLabel2.setLocation(741, 153);
		houseJLabel2.setSize(210, 140);
		backgroundPanel.add(houseJLabel2);
		
		houseJLabel3 = new JLabel(new ImageIcon("image/cooperation/house3.png"));
		houseJLabel3.addMouseListener(this);
		houseJLabel3.setLocation(471, 346);
		houseJLabel3.setSize(210, 140);
		backgroundPanel.add(houseJLabel3);
		
		houseJLabel4 = new JLabel(new ImageIcon("image/cooperation/house4.png"));
		houseJLabel4.addMouseListener(this);
		houseJLabel4.setLocation(741, 346);
		houseJLabel4.setSize(210, 140);
		backgroundPanel.add(houseJLabel4);
		
	}
	
	public void changeRoomState(int roomNO,RoomState roomstate){
		ImageIcon image;
		if(roomstate==RoomState.AVAILABLE){
			image = availableImage;
		}else if(roomstate==RoomState.FULL){
			image = fullImage;	
		}else{
			image = gamingImage;
		}
		tipLabel[roomNO-1].setIcon(image);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==backButton){
			mainFrame.dispose();
//			new Coop_mainFrame();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==houseJLabel1){
			System.out.println("1");
		}
		if(e.getSource()==houseJLabel2){
			System.out.println("2");
		}
		if(e.getSource()==houseJLabel3){
			System.out.println("3");
		}
		if(e.getSource()==houseJLabel4){
			System.out.println("4");
		}
		
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
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}












