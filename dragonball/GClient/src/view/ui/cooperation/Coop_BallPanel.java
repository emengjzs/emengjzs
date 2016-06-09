package view.ui.cooperation;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.vo.UserVO;

@SuppressWarnings("serial")
public class Coop_BallPanel extends JPanel{
	JLabel ballCount;

	public Coop_BallPanel(UserVO user) {
		ballCount = new JLabel();
		ballCount.setFont(new Font("∫∫“«µ˚”ÔÃÂºÚ", Font.BOLD, 30));
		ballCount.setLocation(200,0);
		ballCount.setSize(300,400);
		
		this.add(ballCount);
		this.setOpaque(false);
		this.setLayout(null);

		ArrayList<Integer> ball = user.getBallList();
		initFrame(ball);
	}

	private void initFrame(ArrayList<Integer> ball) {
		String s0 = "<html>";
		String s1 = "“ª–«÷È:"+ball.get(0)+"<br>";
		String s2 = "∂˛–«÷È:"+ball.get(1)+"<br>";
		String s3 = "»˝–«÷È:"+ball.get(2)+"<br>";
		String s4 = "Àƒ–«÷È:"+ball.get(3)+"<br>";
		String s5 = "ŒÂ–«÷È:"+ball.get(4)+"<br>";
		String s6 = "¡˘–«÷È:"+ball.get(5)+"<br>";
		String s7 = "∆ﬂ–«÷È:"+ball.get(6)+"<br>";
		
		String total = s0+s1+s2+s3+s4+s5+s6+s7;
		ballCount.setText(total);
	}

}
