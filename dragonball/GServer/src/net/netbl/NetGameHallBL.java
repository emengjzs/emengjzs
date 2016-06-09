package net.netbl;

import java.io.PrintWriter;
import java.util.ArrayList;

import common.Nameplate;
import common.Service;
import po.ActiveUser;
import businesslogic.bl.BattleRoomBL;
import businesslogic.bl.CoopRoomBL;
import businesslogic.bl.GameHallBL;
import net.netservice.NetGameHallService;

public class NetGameHallBL implements NetGameHallService {
	GameHallBL gameHallBL=GameHallBL.getGameHall();
	final static String NAMEPLATE=Nameplate.GameHall.toString();
	final static String NAMEPLATE2=Nameplate.BattleGameHall.toString();


//	@Override
	private void Enter(String userID, PrintWriter writer, int money, int exp,int power) {
		// TODO Auto-generated method stub
		ActiveUser user=new ActiveUser(userID,writer,money,exp,power);
		gameHallBL.addUser(user);
	}
	
	private void EnterBattleHall(String userID, PrintWriter writer, int money, int exp,int power){
		ActiveUser user=new ActiveUser(userID,writer,money,exp,power);
		gameHallBL.addBattleUser(user);
	}

//	@Override
	private void Quit(String userID, PrintWriter writer) {
		// TODO Auto-generated method stub
		ActiveUser user=new ActiveUser(userID);
		gameHallBL.deleteUser(user);
	}
	
	private void QuitBattleHall(String userID, PrintWriter writer){
		ActiveUser user=new ActiveUser(userID);
		gameHallBL.deleteBattleUser(user);
	}
	
	@SuppressWarnings("static-access")
	private void getCoopUserList(PrintWriter writer){
		ArrayList<ActiveUser> userList=gameHallBL.getCoopUserList();
		String service=Service.ReplyUserList.toString();
		
		StringBuffer br=new StringBuffer();
		for(ActiveUser user: userList){
			br.append(userInString(user));
			br.append("/");
		}
		
		String info=br.toString();
		String message=NAMEPLATE+" "+service+" "+info;
		
		writer.println(message);
		writer.flush();
	}
	
	@SuppressWarnings("static-access")
	private void getBattleUserList(PrintWriter writer){
		ArrayList<ActiveUser> userList=gameHallBL.getBattleUserList();
		String service=Service.ReplyBattleUserList.toString();
		
		StringBuffer br=new StringBuffer();
		for(ActiveUser user: userList){
			br.append(userInString(user));
			br.append("/");
		}
		
		String info=br.toString();
		String message=NAMEPLATE2+" "+service+" "+info;
		
		writer.println(message);
		writer.flush();
	}
	
	private StringBuffer userInString(ActiveUser user){
		StringBuffer br =new StringBuffer();
		br=br.append(user.getUserID());
		br=br.append('-');
		br=br.append(user.getUserState().toString());
		br=br.append('-');
		br=br.append(user.getMoney());
		br=br.append('-');
		br=br.append(user.getExp());
		br=br.append('-');
		br=br.append(user.getPower());
		
		return br;
	}
	
	private StringBuffer roomInString(CoopRoomBL room){
		StringBuffer br = new StringBuffer();
		br=br.append(room.getRoomNO());
		br=br.append('-');
		br=br.append(room.getState());
		
		return br;
	}
	
	private StringBuffer roomInStringForBattle(BattleRoomBL room){
		StringBuffer br = new StringBuffer();
		br=br.append(room.getRoomNO());
		br=br.append('-');
		br=br.append(room.getState());
		
		return br;
	}
	
	private void getCoopRoomList(PrintWriter writer){
		CoopRoomBL[] coopRoomList=gameHallBL.getCoopRoomList();
		String service=Service.ReplyCoopRoomList.toString();
		
		StringBuffer br=new StringBuffer();
		for(int i=0;i<coopRoomList.length;i++){
			br.append(roomInString(coopRoomList[i]));
			br.append("/");
		}
		
		String info=br.toString();
		String message=NAMEPLATE+" "+service+" "+info;
		System.out.println(message);
		System.out.println(writer);
		
		writer.println(message);
		writer.flush();
	}
	
	private void getBattleRoomList(PrintWriter writer){
		BattleRoomBL[] battleRoomList=gameHallBL.getBattleRoomList();
		String service=Service.ReplyBattleRoomList.toString();
		
		StringBuffer br=new StringBuffer();
		for(int i=0;i<battleRoomList.length;i++){
			br.append(roomInStringForBattle(battleRoomList[i]));
			br.append("/");
		}
		
		String info=br.toString();
		String message=NAMEPLATE2+" "+service+" "+info;
		
		writer.println(message);
		writer.flush();
	}
	
	private void enterCoopRoom(String userID,int roomNO,PrintWriter writer){
		String service=Service.ReplyEnterCoopRoom.toString();
		
		String info=gameHallBL.enterCoopRoom(userID, roomNO);
		
		String message=NAMEPLATE+" "+service+" "+info;
		
		ActiveUser user=gameHallBL.getUser(userID,"cooperation");
		
		user.sendMessage(message);
	}
	
	private void enterBattleRoom(String userID,int roomNO,PrintWriter writer){
		String service=Service.ReplyEnterBattleRoom.toString();
		
		String info=gameHallBL.enterBattleRoom(userID, roomNO);
		
		String message=NAMEPLATE2+" "+service+" "+info;
		
		ActiveUser user=gameHallBL.getUser(userID,"battle");
		
		user.sendMessage(message);
	}

	@Override
	public void selectService(String message, PrintWriter writer) {
		// TODO Auto-generated method stub
		String[] result=message.split(" ");
		
		if(result[1].equals(Service.EnterCoopGameHall.toString())){
			Enter(result[2],writer,Integer.parseInt(result[3]),Integer.parseInt(result[4]),Integer.parseInt(result[5]));
		}else if(result[1].equals(Service.LeaveCoopGameHall.toString())){
			Quit(result[2],writer);
		}else if(result[1].equals(Service.RequestUserList.toString())){
			getCoopUserList(writer);
		}else if(result[1].equals(Service.RequestCoopRoomList.toString())){
			getCoopRoomList(writer);
		}else if(result[1].equals(Service.EnterCoopRoom.toString())){
			enterCoopRoom(result[2],Integer.parseInt(result[3]),writer);
		}else if(result[1].equals(Service.EnterBattleGameHall.toString())){
			EnterBattleHall(result[2],writer,Integer.parseInt(result[3]),Integer.parseInt(result[4]),Integer.parseInt(result[5]));
		}else if(result[1].equals(Service.RequestBattleUserList.toString())){
			getBattleUserList(writer);
		}else if(result[1].equals(Service.LeaveBattleGameHall.toString())){
			QuitBattleHall(result[2],writer);
		}else if(result[1].equals(Service.RequestBattleRoomList.toString())){
			getBattleRoomList(writer);
		}else if(result[1].equals(Service.EnterBattleRoom.toString())){
			enterBattleRoom(result[2],Integer.parseInt(result[3]),writer);
		}
	}

}
