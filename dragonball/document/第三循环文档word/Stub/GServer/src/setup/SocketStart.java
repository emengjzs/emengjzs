package setup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import common.Nameplate;

import net.netbl.NetGameHallBL_Stub;
import net.netbl.NetRoomBL_Stub;
import net.netbl.NetUserBL_Stub;
import net.netservice.NetGameHallService_Stub;
import net.netservice.NetRoomBLService_Stub;
import net.netservice.NetUserBLService_Stub;

public class SocketStart {
	PrintWriter writer;
	static ArrayList<PrintWriter> writerList=new ArrayList<PrintWriter>();

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
			PrintWriter out=null;
			try {
				out =new PrintWriter(sock.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String message;
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("Server read " + message);
//					team.distibuteMessage(message);
					dealWithMessage(message,out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SocketStart().go();

	}

	public void go() {
		try {
			ServerSocket serverSock = new ServerSocket(5000);
			System.out.println("server is running!");
			while (true) {
				Socket socket = serverSock.accept();
//				writer = new PrintWriter(socket.getOutputStream());


				Thread t = new Thread(new Handler(socket));
				t.start();
//				System.out.println("get a connect");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void dealWithMessage(String message,PrintWriter writer){
		String[] info=message.split(" ");
		
		if(info[0].equals(Nameplate.User.toString())){
			NetUserBLService_Stub netService=new NetUserBL_Stub();
			netService.selectService(message, writer);
		}else if(info[0].equals(Nameplate.GameHall.toString())){
			NetGameHallService_Stub netGameHallService_Stub=new NetGameHallBL_Stub();
			netGameHallService_Stub.selectService(message, writer);
		}else if(info[0].equals(Nameplate.CoopRoom.toString())){
			NetRoomBLService_Stub netRoomService=new NetRoomBL_Stub();
			netRoomService.selectService(message, writer);
		}
	}

}
