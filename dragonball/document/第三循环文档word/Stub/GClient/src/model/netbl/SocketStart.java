package model.netbl;

import java.io.*;
import java.net.Socket;

import common.Nameplate;
import controller.netlistenerbl.NetCoopRoomListenerBL_Stub;
import controller.netlistenerbl.NetGameHallListenerBL_Stub;
import controller.netlistenerservice.NetCoopRoomListenerService_Stub;
import controller.netlistenerservice.NetGameHallListenerService_Stub;

import model.netservice.NetUserBLService_Stub;

public class SocketStart {
//	String ip = "172.25.135.122";
	String host = "127.0.0.1";
	private BufferedReader reader;
	private PrintWriter writer;
	private Socket sock;
	private Thread readerThread;
	private static String buffer;

	public SocketStart() {
		setUpNetworking();
		readerThread = new Thread(new IncomingReader());
		readerThread.start();

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

		public void run() {
			String message;
			try {
				while ((message = reader.readLine()) != null) {
//					System.out.println("Client read: " + message);
					buffer=message;
//					distribution.dealWithMessage(message);
					// 向界面发送信息
					dealWithMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
	
	private void dealWithMessage(String message){
		String[] info=message.split(" ");
		
		if(info[0].equals(Nameplate.GameHall.toString())){
//			NetGameHallListenerService gameHallService=new NetGameHallListenerBL();
			NetGameHallListenerService_Stub gameHallService=NetGameHallListenerBL_Stub.getGameHallListener();
			gameHallService.selectService(message);
		}else if(info[0].equals(Nameplate.User.toString())){
			NetUserBLService_Stub userService=NetUserBL_Stub.getUserController();
			userService.selectService(message);
		}else if(info[0].equals(Nameplate.CoopRoom.toString())){
			NetCoopRoomListenerService_Stub coopRoomService=NetCoopRoomListenerBL_Stub.getCoopRoomListener();
			coopRoomService.selectService(message);
		}
	}
}
