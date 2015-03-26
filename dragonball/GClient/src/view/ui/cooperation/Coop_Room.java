package view.ui.cooperation;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import common.UserState;

import view.ui.myComponent.IconButton;
import view.ui.myComponent.MySplashScreen;

public class Coop_Room implements ActionListener{
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	
	// ����ԭʼ״̬
	ImageIcon toolImage_notChoose1 = new ImageIcon("image/solo/tool1_1.png");
	ImageIcon toolImage_notChoose2 = new ImageIcon("image/solo/tool1_2.png");
	ImageIcon toolImage_notChoose3 = new ImageIcon("image/solo/tool1_3.png");

	// ���߱�ѡ�к��״̬
	ImageIcon toolImage_choosed1 = new ImageIcon("image/solo/tool2_1.png");
	ImageIcon toolImage_choosed2 = new ImageIcon("image/solo/tool2_2.png");
	ImageIcon toolImage_choosed3 = new ImageIcon("image/solo/tool2_3.png");
	
	//���״̬
	ImageIcon ready = new ImageIcon("image/cooperation/ready.png");
	ImageIcon roomMaster = new ImageIcon("image/cooperation/roomMaster.png");
	ImageIcon leisure = new ImageIcon("image/cooperation/leisure.png");
	
	//���߰�ť
	JButton toolButton1;
	JButton toolButton2;
	JButton toolButton3;
	
	IconButton prepareButton;
	IconButton backButton;
	
	JLabel roleLabel[] = new JLabel[4];
	JLabel nameLabel[] = new JLabel[4];
	JLabel stateLabel[] = new JLabel[4];
	
	JTextArea writeArea;
	JTextArea sendArea;
	IconButton sendButton;
	
	String path1;
	String path2;
	String path3;
	
	String preparePath1 = "image/cooperation/prepare1.png";
	String preparePath2 = "image/cooperation/prepare2.png";
	String preparePath3 = "image/cooperation/prepare3.png";
	
	String beginPath1 = "image/cooperation/begin1.png";
	String beginPath2 = "image/cooperation/begin2.png";
	String beginPath3 = "image/cooperation/begin3.png";
	
	IconButton kick[] = new IconButton[4];
	public static void main(String[] args) {
		new Coop_Room();
	}
	
	public Coop_Room(){
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);//ȥ�����ڱ߿�
		
		initFrame();
		
		mainFrame.setVisible(true);
	}
	
	private void initFrame(){
		Container contentPane = mainFrame.getContentPane();
		
		final ImageIcon backgroundImage = new ImageIcon("image/cooperation/background_hall.png");
		JPanel backgroundPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);
		
		JPanel rolePanel = new JPanel(){
			ImageIcon backgroundImage = new ImageIcon("image/cooperation/background.png");
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		rolePanel.setLocation(796, 45);
		rolePanel.setLayout(null);
		rolePanel.setSize(270, 419);
		backgroundPanel.add(rolePanel);
		
//		prepareButton = new IconButton(path1,path2,path3);
//		prepareButton.addActionListener(this);
//		prepareButton.setLocation(796, 491);
//		prepareButton.setSize(110, 110);
		//backgroundPanel.add(prepareButton);
		
		backButton = new IconButton("image/cooperation/back1.png","image/cooperation/back2.png","image/cooperation/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(987, 612);
		backButton.setSize(80, 80);
		backgroundPanel.add(backButton);
		
		//�Ի����
		JPanel messagePanel = new JPanel(){
			ImageIcon backgroundImage = new ImageIcon("image/cooperation/background.png");
			private static final long serialVersionUID = 1L;
			public void paintComponent(Graphics g){
				backgroundImage.setImage(backgroundImage.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		messagePanel.setLocation(10, 491);
		messagePanel.setLayout(null);
		messagePanel.setSize(776, 199);
		backgroundPanel.add(messagePanel);
		
//		writeArea = new JTextArea(){
//			ImageIcon backgroundImage1 = new ImageIcon("image/cooperation/chatBackground1.png");
//			private static final long serialVersionUID = 1L;
//			public void paintComponent(Graphics g){
//				backgroundImage1.setImage(backgroundImage1.getImage().getScaledInstance(660, 80, Image.SCALE_DEFAULT));
//				g.drawImage(backgroundImage1.getImage(), 0, 0, null);
//			}
//		};
		writeArea = new JTextArea();
		writeArea.setLineWrap(true);
		writeArea.setFont(new Font("���ǵ������", Font.BOLD, 18));
		writeArea.setBounds(1, 1, 660, 80);
		
		JScrollPane writeScroll = new JScrollPane(writeArea);
		writeScroll.setSize(660, 80);
		writeScroll.setLocation(0, 119);
		writeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(writeScroll);
		
//		sendArea = new JTextArea(){
//			ImageIcon backgroundImage2 = new ImageIcon("image/cooperation/chatBackground2.png");
//			private static final long serialVersionUID = 1L;
//			public void paintComponent(Graphics g){
//				backgroundImage2.setImage(backgroundImage2.getImage().getScaledInstance(770, 105, Image.SCALE_DEFAULT));
//				g.drawImage(backgroundImage2.getImage(), 0, 0, null);
//			}
//		};
		sendArea = new JTextArea();
		sendArea.setLineWrap(true);
		sendArea.setEditable(false);
		sendArea.setFont(new Font("���ǵ������", Font.BOLD, 18));
		sendArea.setBounds(0, 0, 770, 105);
		JScrollPane sendScroll = new JScrollPane(sendArea);
		sendScroll.setSize(770, 105);
		sendScroll.setLocation(0, 0);
		sendScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(sendScroll);
		
		sendButton = new IconButton("image/cooperation/send1.png","image/cooperation/send2.png","image/cooperation/send3.png");
		sendButton.setLocation(684, 136);
		sendButton.setSize(80, 40);
		sendButton.addActionListener(this);
		messagePanel.add(sendButton);
		
		toolButton1 = new JButton(toolImage_notChoose1);
		toolButton1.addActionListener(this);
		toolButton1.setLocation(672, 56);
		toolButton1.setSize(90, 90);
		backgroundPanel.add(toolButton1);
		
		toolButton2 = new JButton(toolImage_notChoose2);
		toolButton2.addActionListener(this);
		toolButton2.setLocation(672, 181);
		toolButton2.setSize(90, 90);
		backgroundPanel.add(toolButton2);
		
		toolButton3 = new JButton(toolImage_notChoose3);
		toolButton3.addActionListener(this);
		toolButton3.setLocation(672, 304);
		toolButton3.setSize(90, 90);
		backgroundPanel.add(toolButton3);
		
		
		
		//���״̬
		for(int i=0;i<4;i++){
			stateLabel[i] = new JLabel(new ImageIcon("image/cooperation/state.png"));
			stateLabel[i].setSize(120, 30);
			stateLabel[i].setLocation(60+i*140, 250);
			backgroundPanel.add(stateLabel[i]);
		}
		
		//���˰�ť
		for(int i=0;i<4;i++){
			kick[i] = new IconButton("image/cooperation/kick1.png","image/cooperation/kick2.png","image/cooperation/kick3.png");
			kick[i].setSize(40, 40);
			kick[i].setLocation(140+i*140, 140);
			backgroundPanel.add(kick[i]);
		}
		
		//������
		for(int i=0;i<4;i++){
			roleLabel[i] = new JLabel(new ImageIcon("image/cooperation/role.png"));
			roleLabel[i].setSize(120, 160);
			roleLabel[i].setLocation(60+i*140, 140);
			backgroundPanel.add(roleLabel[i]);
		}
		
		//����ǳ����
		for(int i=0;i<4;i++){
			nameLabel[i] = new JLabel();
			nameLabel[i].setLocation(60+i*140, 310);
			nameLabel[i].setSize(120, 30);
			nameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel[i].setFont(new Font("���ǵ������", Font.BOLD, 18));
			nameLabel[i].setForeground(new Color(210, 105, 30));
			backgroundPanel.add(nameLabel[i]);
		}
		
	}
	
	//�ڽ�������ʾѡ�еĵ���
	public boolean selectTool(int toolNo){
		if(toolNo==0){//����1
			if(toolButton1.getIcon()==toolImage_choosed1){//ͼƬ�ѱ�ѡ��,��ʾ������ʾ����
				showError();
				return false;
			}else{
				toolButton1.setIcon(toolImage_choosed1);
				return true;
			}
		}else if(toolNo==1){//����2
			if(toolButton2.getIcon()==toolImage_choosed2){
				showError();
				return false;
			}else{
				toolButton2.setIcon(toolImage_choosed2);
				return true;
			}
		}else{//����3
			if(toolButton3.getIcon()==toolImage_choosed3){
				showError();
				return false;
			}else{
				toolButton3.setIcon(toolImage_choosed3);
				return true;
			}
		}
	}
	private void showError(){//�ظ�ѡ�����ʱ����ʾ������ʾ����
		MySplashScreen window = new MySplashScreen("image/cooperation/error.png");
		window.setLocation(500, 200);
		window.setSize(300, 50);
		window.setVisible(true);
	}
	
	public void playerIn(String userID,int money,int exp,UserState userstate){
		int blankSeat = -1;
		ImageIcon image = null;
		for(int i=0;i<4;i++){
			if(nameLabel[i].getText().length()==0){
				blankSeat = i;
				break;
			}
		}//���˿��Լ�⵽�ڣ�blankSeat+1����λ��Ϊ��
		
		
		nameLabel[blankSeat].setText(userID);//��ʾID
//		if(blankSeat==0){//��ʾ״̬
//			stateLabel[0].setIcon(roomMaster);
//		}else{
//			stateLabel[blankSeat].setIcon(leisure);
//		}
		if(userstate==UserState.LEISURE){
			image = leisure;
		}else if(userstate==UserState.READY){
			image = ready;
		}else if(userstate==UserState.ROOMMASTER){
			image = roomMaster;
		}
		stateLabel[blankSeat].setIcon(image);
		
		//��ʾ����ͼ��
		
		System.out.println(blankSeat+1);
	}
	
	
	
	public void playerOut(String userID){
		int outSeat = -1;//����Ҫ�˳���������ڵ�λ��
		int lastSeat = -1;//�������һ��������ڵ�λ��
		for(int i=0;i<4;i++){
			if(nameLabel[i].getText().equals(userID)){
				outSeat = i;
			}
			if(nameLabel[i].getText().length()==0){
				lastSeat=i-1;
				break;
			}
			
			if(outSeat==lastSeat){//���Ҫ�˳��������һ���ˣ���ֱ�Ӱ����һ��λ���ϵ�����Ĩȥ
				nameLabel[lastSeat].setText("");
				stateLabel[lastSeat].setIcon(null);
				roleLabel[lastSeat].setIcon(null);
			}else{//����������һ���ˣ������һ����λ�������������Ƶ��Ƴ���λ����,��Ĩȥ���һ��λ���ϵ�����
				nameLabel[outSeat].setText(nameLabel[lastSeat].getText());
				stateLabel[outSeat].setIcon(stateLabel[lastSeat].getIcon());
				roleLabel[outSeat].setIcon(roleLabel[lastSeat].getIcon());
				
				nameLabel[lastSeat].setText("");
				stateLabel[lastSeat].setIcon(null);
				roleLabel[lastSeat].setIcon(null);
			}
		}
	}
	
	public void playerStateChange(String userID, UserState userstate){
		int seat = -1;//��¼Ҫ����״̬��λ��
		ImageIcon image = null;
		if(userstate==UserState.LEISURE){
			image = leisure;
		}else if(userstate==UserState.READY){
			image = ready;
		}else if(userstate==UserState.ROOMMASTER){
			image = roomMaster;
			roomMaster();
			//����Ϊ�����󣬡�׼����ϡ���ť���ɡ���ʼ��Ϸ��ť�����Ҷ��һ�������ˡ��İ�ť
		}
		for(int i=0;i<4;i++){
			if(nameLabel[i].getText().equals(userID)){
				seat=i;
				break;
			}
		}
		
		stateLabel[seat].setIcon(image);
	}
	
	public void chat(String message){
		sendArea.append(message);
		sendArea.append("\n");
	}

	public void roomMaster(){
		//�������⣺�ȵ����캯���������ᱨ�������
		//�Ľ�����������ʾ����ʼ��Ϸ����ť���ڱ�����֮��
		path1 = beginPath1;
		path2 = beginPath2;
		path3 = beginPath3;
		
		int seat =-1;
		for(int i=0;i<4;i++){
			if(stateLabel[i].getIcon()==roomMaster){
				seat = i;
				break;
			}
		}
		kick[seat].setEnabled(false);//�����������˰�ť��Ϊ���ɱ༭
	}
	
	public void common(){
		path1 = preparePath1;
		path2 = preparePath2;
		path3 = preparePath3;	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==sendButton){
			String message = writeArea.getText();
			if(message.length()==0){
				//��������Ϊ�գ�do nothing
			}else{
				sendArea.append(message);
				sendArea.append("\n");
				writeArea.setText("");
			}
			
		}
		
		if(e.getSource()==prepareButton){
			
		}
		if(e.getSource()==backButton){
			mainFrame.dispose();
		}
		
		if(e.getSource()==toolButton1){
//			if(toolButton1.getIcon()==toolImage1_1){//�ӵ���
//				toolButton1.setIcon(toolImage2_1);
//			}else{//������
//				toolButton1.setIcon(toolImage1_1);
//			}
		}
		if(e.getSource()==toolButton2){
//			if(toolButton2.getIcon()==toolImage1_2){//�ӵ���
//				toolButton2.setIcon(toolImage2_2);
//			}else{//������
//				toolButton2.setIcon(toolImage1_2);
//			}
		}
		if(e.getSource()==toolButton3){
//			if(toolButton3.getIcon()==toolImage1_3){//�ӵ���
//				toolButton3.setIcon(toolImage2_3);
//			}else{//������
//				toolButton3.setIcon(toolImage1_3);
//			}
		}
		
	}

}
