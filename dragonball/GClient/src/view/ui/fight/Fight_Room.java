package view.ui.fight;

import java.awt.Container;
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

import view.ui.myComponent.IconButton;
import view.ui.myComponent.MySplashScreen;

import java.awt.Font;
import javax.swing.SwingConstants;

import common.UserState;

import java.awt.Color;

public class Fight_Room implements ActionListener {
	JFrame mainFrame;
	private int width = 1100;
	private int height = 700;
	JPanel backgroundPanel;

	// ����ԭʼ״̬
	ImageIcon toolImage_notChoose1 = new ImageIcon("image/solo/tool1_1.png");
	ImageIcon toolImage_notChoose2 = new ImageIcon("image/solo/tool1_2.png");
	ImageIcon toolImage_notChoose3 = new ImageIcon("image/solo/tool1_3.png");

	// ���߱�ѡ�к��״̬
	ImageIcon toolImage_choosed1 = new ImageIcon("image/solo/tool2_1.png");
	ImageIcon toolImage_choosed2 = new ImageIcon("image/solo/tool2_2.png");
	ImageIcon toolImage_choosed3 = new ImageIcon("image/solo/tool2_3.png");

	// ���״̬
	ImageIcon ready = new ImageIcon("image/cooperation/ready.png");
	ImageIcon roomMaster = new ImageIcon("image/cooperation/roomMaster.png");
	ImageIcon leisure = new ImageIcon("image/cooperation/leisure.png");

	// ���߰�ť
	JButton toolButton1;
	JButton toolButton2;
	JButton toolButton3;

	IconButton prepareButton;
	IconButton backButton;

	JLabel roleLabel[] = new JLabel[4];
	JLabel oponentLabel[] = new JLabel[4];
	JLabel roleNameLabel[] = new JLabel[4];
	JLabel oponentNameLabel[] = new JLabel[4];
	JLabel roleStateLabel[] = new JLabel[4];
	JLabel oponentStateLabel[] = new JLabel[4];

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

	public static void main(String[] args) {
		new Fight_Room();
	}

	public Fight_Room() {
		mainFrame = new JFrame();
		mainFrame.setSize(width, height);
		mainFrame.setLocation(150, 20);
		mainFrame.setUndecorated(true);// ȥ�����ڱ߿�

		initFrame();

		mainFrame.setVisible(true);
	}

	private void initFrame() {
		Container contentPane = mainFrame.getContentPane();

		final ImageIcon backgroundImage = new ImageIcon(
				"image/cooperation/background.png");
		backgroundPanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				backgroundImage.setImage(backgroundImage.getImage()
						.getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		backgroundPanel.setLayout(null);
		backgroundPanel.setSize(width, height);
		contentPane.add(backgroundPanel);

		JPanel rolePanel = new JPanel() {
			ImageIcon backgroundImage = new ImageIcon(
					"image/cooperation/background.png");
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				backgroundImage.setImage(backgroundImage.getImage()
						.getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		rolePanel.setLocation(796, 45);
		rolePanel.setLayout(null);
		rolePanel.setSize(270, 419);
		backgroundPanel.add(rolePanel);
		
		backButton = new IconButton("image/cooperation/back1.png","image/cooperation/back2.png","image/cooperation/back3.png");


		backButton = new IconButton("image/cooperation/back1.png",
				"image/cooperation/back2.png", "image/cooperation/back3.png");
		backButton.addActionListener(this);
		backButton.setLocation(987, 612);
		backButton.setSize(80, 80);
		backgroundPanel.add(backButton);

		// �Ի����
		JPanel messagePanel = new JPanel() {
			ImageIcon backgroundImage = new ImageIcon(
					"image/cooperation/background.png");
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
				backgroundImage.setImage(backgroundImage.getImage()
						.getScaledInstance(width, height, Image.SCALE_DEFAULT));
				g.drawImage(backgroundImage.getImage(), 0, 0, null);
			}
		};
		messagePanel.setLocation(10, 491);
		messagePanel.setLayout(null);
		messagePanel.setSize(776, 199);
		backgroundPanel.add(messagePanel);

		// ���졪��д���
		writeArea = new JTextArea();
		writeArea.setLineWrap(true);
		writeArea.setFont(new Font("���ǵ������", Font.BOLD, 18));
		writeArea.setBounds(1, 1, 660, 80);

		JScrollPane writeScroll = new JScrollPane(writeArea);
		writeScroll.setSize(660, 80);
		writeScroll.setLocation(0, 119);
		writeScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(writeScroll);

		// ���졪����ʾ���
		sendArea = new JTextArea();
		sendArea.setLineWrap(true);
		sendArea.setEditable(false);
		sendArea.setFont(new Font("���ǵ������", Font.BOLD, 18));
		sendArea.setBounds(0, 0, 770, 105);
		JScrollPane sendScroll = new JScrollPane(sendArea);
		sendScroll.setSize(770, 105);
		sendScroll.setLocation(0, 0);
		sendScroll
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		messagePanel.add(sendScroll);

		sendButton = new IconButton("image/cooperation/send1.png",
				"image/cooperation/send2.png", "image/cooperation/send3.png");
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

		// �������״̬
		for (int i = 0; i < 4; i++) {
			roleStateLabel[i] = new JLabel(new ImageIcon(
					"image/fight/state.png"));
			roleStateLabel[i].setSize(120, 30);
			roleStateLabel[i].setLocation(60 + i * 140, 170);
			backgroundPanel.add(roleStateLabel[i]);
		}
		// �������״̬
		for (int i = 0; i < 4; i++) {
			oponentStateLabel[i] = new JLabel(new ImageIcon(
					"image/fight/state.png"));
			oponentStateLabel[i].setSize(120, 30);
			oponentStateLabel[i].setLocation(60 + i * 140, 380);
			backgroundPanel.add(oponentStateLabel[i]);
		}

		// ����ͼ��
		for (int i = 0; i < 4; i++) {
			roleLabel[i] = new JLabel(new ImageIcon("image/fight/role.png"));
			roleLabel[i].setSize(120, 160);
			roleLabel[i].setLocation(60 + i * 140, 60);
			backgroundPanel.add(roleLabel[i]);
		}
		// ����ͼ��
		for (int i = 0; i < 4; i++) {
			oponentLabel[i] = new JLabel(new ImageIcon("image/fight/role.png"));
			oponentLabel[i].setSize(120, 160);
			oponentLabel[i].setLocation(60 + i * 140, 270);
			backgroundPanel.add(oponentLabel[i]);
		}

		// ��������ǳ�
		for (int i = 0; i < 4; i++) {
			roleNameLabel[i] = new JLabel("ɷ��");
			roleNameLabel[i].setSize(120, 30);
			roleNameLabel[i].setLocation(60 + i * 140, 225);
			roleNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			roleNameLabel[i].setFont(new Font("���ǵ������", Font.BOLD, 18));
			roleNameLabel[i].setForeground(new Color(210, 105, 30));
			backgroundPanel.add(roleNameLabel[i]);
		}

		// ����ǳ�
		for (int i = 0; i < 4; i++) {
			oponentNameLabel[i] = new JLabel("ɷ��");
			oponentNameLabel[i].setSize(120, 30);
			oponentNameLabel[i].setLocation(60 + i* 140, 435);
			oponentNameLabel[i].setHorizontalAlignment(SwingConstants.CENTER);
			oponentNameLabel[i].setFont(new Font("���ǵ������", Font.BOLD, 18));
			oponentNameLabel[i].setForeground(new Color(210, 105, 30));
			backgroundPanel.add(oponentNameLabel[i]);
		}

	}

	// �ڽ�������ʾѡ�еĵ���
	public boolean selectTool(int toolNo) {
		if (toolNo == 0) {// ����1
			if (toolButton1.getIcon() == toolImage_choosed1) {// ͼƬ�ѱ�ѡ��,��ʾ������ʾ����
				showError();
				return false;
			} else {
				toolButton1.setIcon(toolImage_choosed1);
				return true;
			}
		} else if (toolNo == 1) {// ����2
			if (toolButton2.getIcon() == toolImage_choosed2) {
				showError();
				return false;
			} else {
				toolButton2.setIcon(toolImage_choosed2);
				return true;
			}
		} else {// ����3
			if (toolButton3.getIcon() == toolImage_choosed3) {
				showError();
				return false;
			} else {
				toolButton3.setIcon(toolImage_choosed3);
				return true;
			}
		}
	}

	private void showError() {// �ظ�ѡ�����ʱ����ʾ������ʾ����
		MySplashScreen window = new MySplashScreen(
				"image/cooperation/error.png");
		window.setLocation(500, 200);
		window.setSize(300, 50);
		window.setVisible(true);
	}

	public void playerIn(int flag, String userID, int money, int exp,UserState userstate) {
		//flag--0:����;1:����
		
		if(flag==0){//����
			int blankSeat = -1;
			ImageIcon image = null;
			for (int i = 0; i < 4; i++) {
				if (roleNameLabel[i].getText().length() == 0) {
					blankSeat = i;
					break;
				}
			}// ���˿��Լ�⵽�ڣ�blankSeat+1����λ��Ϊ��

			roleNameLabel[blankSeat].setText(userID);// ��ʾID
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				image = roomMaster;
			}
			roleStateLabel[blankSeat].setIcon(image);

			// ��ʾ����ͼ��
		}else{//flag==1,����
			int blankSeat = -1;
			ImageIcon image = null;
			for (int i = 0; i < 4; i++) {
				if (oponentNameLabel[i].getText().length() == 0) {
					blankSeat = i;
					break;
				}
			}// ���˿��Լ�⵽�ڣ�blankSeat+1����λ��Ϊ��

			oponentNameLabel[blankSeat].setText(userID);// ��ʾID
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				image = roomMaster;
			}
			oponentNameLabel[blankSeat].setIcon(image);

			// ��ʾ����ͼ��
		}
		

	}

	public void playerOut(int flag, String userID) {
		if(flag==0){
			int outSeat = -1;// ����Ҫ�˳���������ڵ�λ��
			int lastSeat = -1;// �������һ��������ڵ�λ��
			for (int i = 0; i < 4; i++) {
				if (roleNameLabel[i].getText().equals(userID)) {
					outSeat = i;
				}
				if (roleNameLabel[i].getText().length() == 0) {
					lastSeat = i - 1;
					break;
				}
			}
			if (outSeat == lastSeat) {
				// ���Ҫ�˳��������һ���ˣ���ֱ�Ӱ����һ��λ���ϵ�����Ĩȥ
				System.out.println(lastSeat);
				roleNameLabel[lastSeat].setText("");
				roleStateLabel[lastSeat].setIcon(null);
				roleLabel[lastSeat].setIcon(null);
			} else {// ����������һ���ˣ������һ����λ�������������Ƶ��Ƴ���λ����,��Ĩȥ���һ��λ���ϵ�����
				roleNameLabel[outSeat].setText(roleNameLabel[lastSeat].getText());
				roleStateLabel[outSeat].setIcon(roleStateLabel[lastSeat].getIcon());
				roleLabel[outSeat].setIcon(roleLabel[lastSeat].getIcon());

				roleNameLabel[lastSeat].setText("");
				roleStateLabel[lastSeat].setIcon(null);
				roleLabel[lastSeat].setIcon(null);
			}
		}else{//����
			int outSeat = -1;// ����Ҫ�˳���������ڵ�λ��
			int lastSeat = -1;// �������һ��������ڵ�λ��
			for (int i = 0; i < 4; i++) {
				if (oponentNameLabel[i].getText().equals(userID)) {
					outSeat = i;
				}
				if (oponentNameLabel[i].getText().length() == 0) {
					lastSeat = i - 1;
					break;
				}
			}
			if (outSeat == lastSeat) {
				// ���Ҫ�˳��������һ���ˣ���ֱ�Ӱ����һ��λ���ϵ�����Ĩȥ
				System.out.println(lastSeat);
				oponentNameLabel[lastSeat].setText("");
				oponentStateLabel[lastSeat].setIcon(null);
				oponentLabel[lastSeat].setIcon(null);
			} else {// ����������һ���ˣ������һ����λ�������������Ƶ��Ƴ���λ����,��Ĩȥ���һ��λ���ϵ�����
				oponentNameLabel[outSeat].setText(oponentNameLabel[lastSeat].getText());
				oponentStateLabel[outSeat].setIcon(oponentStateLabel[lastSeat].getIcon());
				oponentLabel[outSeat].setIcon(oponentLabel[lastSeat].getIcon());

				oponentNameLabel[lastSeat].setText("");
				oponentStateLabel[lastSeat].setIcon(null);
				oponentLabel[lastSeat].setIcon(null);
			}
		}
		

	}

	public void playerStateChange(int flag,String userID, UserState userstate) {
		if(flag==0){
			int seat = -1;// ��¼Ҫ����״̬��λ��
			ImageIcon image = null;
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				System.out.println("I'm roommaster");
				image = roomMaster;
				roomMaster(flag);
				// ����Ϊ�����󣬡�׼����ϡ���ť���ɡ���ʼ��Ϸ��ť�����Ҷ��һ�������ˡ��İ�ť
			}
			for (int i = 0; i < 4; i++) {
				if (roleNameLabel[i].getText().equals(userID)) {
					seat = i;
					break;
				}
			}

			roleStateLabel[seat].setIcon(image);
		}else{
			int seat = -1;// ��¼Ҫ����״̬��λ��
			ImageIcon image = null;
			if (userstate == UserState.LEISURE) {
				image = leisure;
			} else if (userstate == UserState.READY) {
				image = ready;
			} else if (userstate == UserState.ROOMMASTER) {
				System.out.println("I'm roommaster");
				image = roomMaster;
				roomMaster(flag);
				// ����Ϊ�����󣬡�׼����ϡ���ť���ɡ���ʼ��Ϸ��ť�����Ҷ��һ�������ˡ��İ�ť
			}
			for (int i = 0; i < 4; i++) {
				if (oponentNameLabel[i].getText().equals(userID)) {
					seat = i;
					break;
				}
			}

			oponentStateLabel[seat].setIcon(image);
		}
	}

	public void chat(String message) {
		sendArea.append(message);
		sendArea.append("\n");
	}

	public void roomMaster(int flag) {
		// �������⣺�ȵ����캯���������ᱨ�������
		// �Ľ�����������ʾ����ʼ��Ϸ����ť���ڱ�����֮��
		if (prepareButton != null) {
			backgroundPanel.remove(prepareButton);
		}
		path1 = beginPath1;
		path2 = beginPath2;
		path3 = beginPath3;
		showPrepareButton();
		//showKickButton();

//		int seat = -1;
//		for (int i = 0; i < 4; i++) {
//			if (stateLabel[i].getIcon() == roomMaster) {
//				seat = i;
//				break;
//			}
//		}
//		kick[seat].setEnabled(false);// �����������˰�ť��Ϊ���ɱ༭
		backgroundPanel.repaint();
	}

	public void common() {
		if (prepareButton != null) {
			backgroundPanel.remove(prepareButton);
		}
		path1 = preparePath1;
		path2 = preparePath2;
		path3 = preparePath3;
		showPrepareButton();
		// for(int i=0;i<4;i++){
		// kick[i].setIcon(null);
		// kick[i].removeActionListener(this);
		// kick[i].setVisible(false);
		// }
		backgroundPanel.repaint();
	}

	private void showPrepareButton() {
		prepareButton = new IconButton(path1, path2, path3);
		prepareButton.addActionListener(this);
		prepareButton.setLocation(796, 491);
		prepareButton.setSize(110, 110);
		backgroundPanel.add(prepareButton);
	}

//	private void showKickButton() {
//		// ���˰�ť
//		for (int i = 0; i < 4; i++) {
//			kick[i] = new IconButton("image/cooperation/kick1.png",
//					"image/cooperation/kick2.png",
//					"image/cooperation/kick3.png");
//			kick[i].setSize(40, 40);
//			kick[i].setLocation(140 + i * 140, 140);
//			kick[i].addActionListener(this);
//			backgroundPanel.add(kick[i]);
//		}
//	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == prepareButton) {

		}
		if (e.getSource() == backButton) {
			mainFrame.dispose();
		}

		if (e.getSource() == toolButton1) {
			// if(toolButton1.getIcon()==toolImage1_1){//�ӵ���
			// toolButton1.setIcon(toolImage2_1);
			// }else{//������
			// toolButton1.setIcon(toolImage1_1);
			// }
		}
		if (e.getSource() == toolButton2) {
			// if(toolButton2.getIcon()==toolImage1_2){//�ӵ���
			// toolButton2.setIcon(toolImage2_2);
			// }else{//������
			// toolButton2.setIcon(toolImage1_2);
			// }
		}
		if (e.getSource() == toolButton3) {
			// if(toolButton3.getIcon()==toolImage1_3){//�ӵ���
			// toolButton3.setIcon(toolImage2_3);
			// }else{//������
			// toolButton3.setIcon(toolImage1_3);
			// }
		}
	}

}
