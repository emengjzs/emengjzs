package setup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import common.Nameplate;
import net.netbl.NetBattleRoomBL;
import net.netbl.NetErrorBL;
import net.netbl.NetSingleBL;
import net.netbl.NetStatisBL;
import net.netbl.NetGameHallBL;
import net.netbl.NetCoopRoomBL;
import net.netbl.NetUserBL;
import net.netservice.NetBattleRoomBLService;
import net.netservice.NetSingleBLService;
import net.netservice.NetStatisBLService;
import net.netservice.NetGameHallService;
import net.netservice.NetCoopRoomBLService;
import net.netservice.NetUserBLService;

public class SocketStart {
	JFrame frame;
	PrintWriter writer;
	static ArrayList<PrintWriter> writerList = new ArrayList<PrintWriter>();

	public class Handler implements Runnable {
		BufferedReader reader;
		Socket sock;

		public Handler(Socket s) {
			try {
				sock = s;
				InputStreamReader isr = new InputStreamReader(
						sock.getInputStream());
				reader = new BufferedReader(isr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			PrintWriter out = null;
			try {
				out = new PrintWriter(sock.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Server read " + message);
					// team.distibuteMessage(message);
					dealWithMessage(message, out);
				}
			} catch (Exception e) {
				System.out.println(out);
/*				NetErrorBL netError = new NetErrorBL();
				netError.removeUser(out);*/
				e.printStackTrace();
			}

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SocketStart start = new SocketStart();
		start.openFrame();
		start.go();

	}

	private void openFrame() {
		int width = 270;
		int height = 180;
		frame = new JFrame();
		frame.setSize(width, height);
		frame.setLocation(300, 300);
		frame.setTitle("服务器");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLayout(null);
		
		frame.setResizable(false);

		JLabel label = new JLabel("龙珠消消乐服务器运行中");
		label.setFont(new java.awt.Font("Serief", 1, 20));
		label.setLocation(0, height / 10);
		label.setSize(width, height / 5);
		label.setVisible(true);

		// 获得本机IP
		InetAddress addr;
		String ip = "";
		try {
			addr = InetAddress.getLocalHost();
			ip = addr.getHostAddress().toString();// 获得本机IP
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		JLabel labelIP = new JLabel("本机ip为：" + ip);
		labelIP.setFont(new java.awt.Font("Serief", 1, 20));
		labelIP.setLocation(0, 2 * height / 5);
		labelIP.setSize(width, height / 5);
		labelIP.setVisible(true);

		JButton button = new JButton("停止服务器");
		button.setLocation(width * 1 / 4, 3 * height / 5);
		button.setSize(width / 2, height / 6);
		button.setVisible(true);
		button.addActionListener(new CloseListener());

		frame.add(label);
		frame.add(labelIP);
		frame.add(button);
		frame.setVisible(true);
		frame.repaint();

	}

	public void go() {
		try {
			@SuppressWarnings("resource")
			ServerSocket serverSock = new ServerSocket(5000);
			System.out.println("server is running!");
			while (true) {
				Socket socket = serverSock.accept();
				// writer = new PrintWriter(socket.getOutputStream());

				Thread t = new Thread(new Handler(socket));
				t.start();
				// System.out.println("get a connect");
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	private void dealWithMessage(String message, PrintWriter writer) {
		String[] info = message.split(" ");
		if (info[0].equals(Nameplate.User.toString())) {
			NetUserBLService netService = new NetUserBL();
			netService.selectService(message, writer);
		} else if (info[0].equals(Nameplate.GameHall.toString())) {
			NetGameHallService netGameHallService = new NetGameHallBL();
			netGameHallService.selectService(message, writer);
		} else if (info[0].equals(Nameplate.CoopRoom.toString())) {
			NetCoopRoomBLService netRoomService = new NetCoopRoomBL();
			netRoomService.selectService(message, writer);
		} else if (info[0].equals(Nameplate.BattleRoom.toString())) {
			NetBattleRoomBLService netBattleRoomService = new NetBattleRoomBL();
			netBattleRoomService.selectService(message, writer);
		} else if (info[0].equals(Nameplate.Statis.toString())) {
			NetStatisBLService netCoopStatisService = new NetStatisBL();
			netCoopStatisService.selectService(message, writer);
		} else if (info[0].equals(Nameplate.Single.toString())) {
			NetSingleBLService netSingleService = new NetSingleBL();
			netSingleService.selectService(message, writer);
		} else if (info[0].equals(Nameplate.Error.toString())) {
			NetErrorBL netError = new NetErrorBL();
			String userID = info[1];
			netError.removeUser(userID);
		}
	}

	class CloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			System.exit(0);

		}

	}

}
