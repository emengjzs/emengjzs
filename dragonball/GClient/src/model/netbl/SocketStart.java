package model.netbl;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import view.ui.myComponent.TipRunnable;
import common.Nameplate;
import common.Service;
import controller.netlistenerbl.NetBattleGameHallListenerBL;
import controller.netlistenerbl.NetBattleRoomListenerBL;
import controller.netlistenerbl.NetCoopRoomListenerBL;
import controller.netlistenerbl.NetCoopGameHallListenerBL;
import controller.netlistenerservice.*;
import controller.netlistenerbl.*;

public class SocketStart {
	// String ip = "172.25.135.122";
	String host; //= "127.0.0.1";
	private BufferedReader reader;
	private PrintWriter writer;
	private static Socket sock;
	private Thread readerThread;
	@SuppressWarnings("unused")
	private static String buffer;

	static SocketStart socketStart;

	@SuppressWarnings("resource")
	private SocketStart() {
		try {
			Scanner sc = new Scanner(new File("ip.txt"));
			host = sc.nextLine();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setUpNetworking();
		readerThread = new Thread(new IncomingReader());
		readerThread.start();
		

	}

	public static SocketStart getSocket() {
		if (socketStart == null) {
			socketStart = new SocketStart();
		}

		return socketStart;
	}

	private void setUpNetworking() {
		try {
			sock = new Socket(host, 5000);
			InputStreamReader streamReader = new InputStreamReader(
					sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		try {
			writer.println(message);// 向服务器发送信息
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public class IncomingReader implements Runnable {

		@SuppressWarnings("static-access")
		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Client read: " + message);
					buffer = message;
					// distribution.dealWithMessage(message);
					// 向界面发送信息
					dealWithMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				TipRunnable error = new TipRunnable("image/tip/netError.png", 500, 350,
						300, 50, 3);
				Thread myThread = new Thread(error);
				myThread.start();
				
				try {
					new Thread().sleep(1500);
				} catch (InterruptedException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				
				System.exit(0);
			}

		}

	}

	private void dealWithMessage(String message) {
		String[] info = message.split(" ");

		if (info[0].equals(Nameplate.GameHall.toString())) {
			// NetCoopGameHallListenerService gameHallService=new
			// NetCoopGameHallListenerBL();
			NetCoopGameHallListenerService gameHallService = NetCoopGameHallListenerBL
					.getGameHallListener();
			gameHallService.selectService(message);
		} else if (info[0].equals(Nameplate.User.toString())) {
			NetUserListenerService userService = NetUserListenerBL
					.getUserControllerListener();
			userService.selectService(message);
		} else if (info[0].equals(Nameplate.CoopRoom.toString())) {
			NetCoopRoomListenerService coopRoomService = NetCoopRoomListenerBL
					.getCoopRoomListener();
			coopRoomService.selectService(message);
		} else if (info[0].equals(Nameplate.BattleGameHall.toString())) {
			NetBattleGameHallListenerService gameHallService = NetBattleGameHallListenerBL
					.getGameHallListener();
			gameHallService.selectService(message);
		} else if (info[0].equals(Nameplate.BattleRoom.toString())) {
			NetBattleRoomListenerService battleRoomService = NetBattleRoomListenerBL
					.getBattleRoomListener();
			battleRoomService.selectService(message);
		} else if (info[0].equals(Service.Game.toString())) {
			NetGameListenerService gameService = NetGameListenerBL
					.getNetGameListenerBL();
			gameService.selectService(message);
		} else if (info[0].equals(Nameplate.Statis.toString())) {
			NetStatisListenerService statisService = NetStatisListenerBL
					.getStatisControllerListener();
			statisService.selectService(message);
		} else if (info[0].equals(Nameplate.Single.toString())){
			NetSingleListenerService singleService=NetSingleListenerBL.getSingleListener();
			singleService.selectService(message);
		}
	}
}
