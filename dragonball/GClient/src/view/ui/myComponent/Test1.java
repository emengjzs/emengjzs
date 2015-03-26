package view.ui.myComponent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Test1{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Test1();
	}
	
	public Test1(){
		JFrame f1=new MyFrame();//**
		JButton button = new JButton("vsfb");
		button.setLocation(146, 160);
		button.setSize(197, 50);
		button.setVisible(true);
		f1.getContentPane().add(button);
	}


}
