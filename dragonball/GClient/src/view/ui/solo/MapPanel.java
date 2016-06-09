package view.ui.solo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.vo.UserVO;
import view.ui.myComponent.IconButton;
import view.ui.myComponent.TipRunnable;

@SuppressWarnings("serial")
public class MapPanel extends JPanel implements ActionListener {
//	JFrame mainFrame;
	UserVO user;
//	private int width = 650;
//	private int height = 400;

//	IconButton shutButton;
	IconButton forwardButton;
	IconButton backwardButton;

	JLabel message;

	JLabel[] roleImg;
	String[] role = { "乌龙", "乐平", "比克", "佛利萨", "人造人", "沙鲁", "布欧", "欧布", "神龙" };
	Integer[] star = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	ArrayList<Integer> starList = new ArrayList<Integer>();
	int lock = 7;
	int cur = -1;
	IconButton fight;

	public static void main(String[] args) {
		// new MapPanel();
	}

	public MapPanel(UserVO user) {
		this.user = user;
		this.starList = user.getStarList();
		lock = user.getStage();
//		mainFrame = new JFrame();
//		mainFrame.setIconImage(new ImageIcon("Image/logo.png").getImage());
//		mainFrame.setSize(width, height);
//		mainFrame.setLocation(360, 200);
//		mainFrame.setUndecorated(true);// 去除窗口边框
//		mainFrame.setBackground(new Color(0, 0, 0, 0)); // 将窗口变透明

		this.setOpaque(false);
		this.setLayout(null);
		
		initFrame();

//		mainFrame.setVisible(true);
	}

	private void initFrame() {
		// Container contentPane = mainFrame.getContentPane();

//		final ImageIcon backgroundImage = new ImageIcon(
//				"image/solo/toolBackground.png");
//		JPanel backgroundPanel = new JPanel() {
//			public void paintComponent(Graphics g) {
//				g.drawImage(backgroundImage.getImage(), 0, 0, width, height,
//						null);
//			}
//		};
//		backgroundPanel.setLayout(null);
//		backgroundPanel.setSize(width, height);
//		// contentPane.add(backgroundPanel);
//		mainFrame.setContentPane(backgroundPanel);

//		shutButton = new IconButton("image/solo/shut1.png",
//				"image/solo/shut2.png", "image/solo/shut3.png");
//		shutButton.addActionListener(this);
//		shutButton.setLocation(514, 30);
//		shutButton.setSize(50, 50);
//		backgroundPanel.add(shutButton);

		forwardButton = new IconButton("image/login/forward1.png",
				"image/login/forward2.png", "image/login/forward3.png");
		forwardButton.setSize(50, 50);
		forwardButton.addActionListener(this);
		forwardButton.setLocation(520, 200);
		//backgroundPanel.add(forwardButton);
		this.add(forwardButton);

		backwardButton = new IconButton("image/login/backward1.png",
				"image/login/backward2.png", "image/login/backward3.png");
		backwardButton.setLocation(70, 200);
		backwardButton.setSize(50, 50);
		backwardButton.addActionListener(this);
		//backgroundPanel.add(backwardButton);
		this.add(backwardButton);

		// //战斗地图（滚动框）
		// JPanel mapPanel = new JPanel(){
		// private static final long serialVersionUID = 1L;
		// ImageIcon mapBackgroungImage = new
		// ImageIcon("image/solo/mapBackground.png");
		// public void paintComponent(Graphics g){
		// super.paintComponent(g);
		// mapBackgroungImage.setImage(mapBackgroungImage.getImage().getScaledInstance(500,
		// height, Image.SCALE_DEFAULT));
		// g.drawImage(mapBackgroungImage.getImage(), 0, 0,
		// this.getWidth(),this.getHeight(),this);
		// }
		// };
		//
		// mapPanel.setLayout(null);
		//
		// Dimension dimension=new Dimension(width/2,height*2);
		// mapPanel.setPreferredSize(dimension);
		//
		// JScrollPane map = new JScrollPane(mapPanel);
		//
		// map.setLocation(116, 52);
		// map.setSize(388, 300);
		// map.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// backgroundPanel.add(map);
		//
		// map.getHorizontalScrollBar().setUI(null);
		// //map.getVerticalScrollBar().setUI(null);
		//
		// map.getVerticalScrollBar().setUnitIncrement(20);

		cur = lock;

		roleImg = new JLabel[9];
		for (int i = 0; i < 9; i++) {
			if (i < lock) {
				roleImg[i] = new JLabel(new ImageIcon("Image/role/" + role[i]
						+ ".png"));
			} else {
				roleImg[i] = new JLabel(new ImageIcon("Image/role/未知.png"));
			}
			roleImg[i].setSize(203, 279);
			roleImg[i].setLocation(235, 60);
		}

		message = new JLabel("<html>" + "关卡：" + cur + "<br>" + "星级："
				+ starList.get(cur - 1) + "</html>");
		message.setFont(new Font("汉仪蝶语体简", Font.BOLD, 24));
		message.setSize(120, 70);
		message.setLocation(120, 60);

		fight = new IconButton("Image/role/fight1.png",
				"Image/role/fight2.png", "Image/role/fight3.png");
		fight.setSize(65, 41);
		fight.setLocation(125, 300);
		fight.addActionListener(this);
		fight.setVisible(true);

//		mainFrame.add(roleImg[cur - 1]);
//		mainFrame.add(message);
//		mainFrame.add(fight);
		this.add(roleImg[cur - 1]);
		this.add(message);
		this.add(fight);

		if (cur == 9) {
			forwardButton.setVisible(false);
		} else if (cur == 1) {
			backwardButton.setVisible(false);
		} else {
			forwardButton.setVisible(true);
			backwardButton.setVisible(true);
		}

		if (cur > lock) {
			fight.setVisible(false);
		} else {
			fight.setVisible(true);
		}
	}

	public UserVO getUser() {
		return user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == shutButton) {
//			mainFrame.dispose();
//		}

		if (e.getSource() == forwardButton) {
//			mainFrame.remove(roleImg[cur - 1]);
//			cur++;
//			mainFrame.add(roleImg[cur - 1]);
//			message.setText("<html>" + "关卡：" + cur + "<br>" + "星级："
//					+ starList.get(cur - 1) + "</html>");
//			mainFrame.repaint();
			
			this.remove(roleImg[cur - 1]);
			cur++;
			this.add(roleImg[cur - 1]);
			message.setText("<html>" + "关卡：" + cur + "<br>" + "星级："
					+ starList.get(cur - 1) + "</html>");
			this.repaint();
		}

		if (e.getSource() == backwardButton) {
//			mainFrame.remove(roleImg[cur - 1]);
//			cur--;
//			mainFrame.add(roleImg[cur - 1]);
//			message.setText("<html>" + "关卡：" + cur + "<br>" + "星级："
//					+ starList.get(cur - 1) + "</html>");
//			mainFrame.repaint();
			
			this.remove(roleImg[cur - 1]);
			cur--;
			this.add(roleImg[cur - 1]);
			message.setText("<html>" + "关卡：" + cur + "<br>" + "星级："
					+ starList.get(cur - 1) + "</html>");
			this.repaint();
		}

		if (e.getSource() == fight) {
			if (cur != user.getStage()) {
				// user.setStage(cur);
				user.setCurrentStage(cur);
				TipRunnable success = new TipRunnable("image/tip/chooseSucceed.png", 615,
						600, 200, 30, 2);
				Thread myThread = new Thread(success);
				myThread.start();
			}
		}

		if (cur == 9) {
			forwardButton.setVisible(false);
		} else if (cur == 1) {
			backwardButton.setVisible(false);
		} else {
			forwardButton.setVisible(true);
			backwardButton.setVisible(true);
		}

		if (cur > lock) {
			fight.setVisible(false);
		} else {
			fight.setVisible(true);
		}
	}

}
