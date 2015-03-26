package data.dataimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import po.ActiveUser;
import po.CoopRecordPO;
import data.dataservice.CoopGameDataService;

public class CoopGameDataServiceImpl implements CoopGameDataService {
	private Connection conn = null;
	Statement stmt = null;
	int TEAM_MEMBER=4;
	@Override
	public void InsertCooperation(ArrayList<ActiveUser> playerList, int point,
			int combo) {
		// TODO Auto-generated method stub
		init();
		int offset=TEAM_MEMBER-playerList.size();

		try {
			String sql="insert into Cooperation ([date],id1,id2,id3,id4,point,combo) values('"+getCurrentDate()+"','";
			for(ActiveUser player: playerList){
				sql+=player.getUserID()+"','";
			}
			for(int i=0;i<offset;i++){
				sql+=null+"','";
			}
			sql+= point + "','" +  combo + "')";
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Finish();
		System.out.println("Insert Succeed!");
	}

	@Override
	public int getCooperationTotal(String id) {
		// TODO Auto-generated method stub
		init();
		int totalGame = -1;
		try {
			ResultSet rs = stmt.executeQuery("Select count(*) as total from Cooperation where id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"'");
			while (rs.next()) {
				totalGame=rs.getInt("total");
				System.out.println(totalGame);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
		return totalGame;
	}

	@Override
	public int getCooperationAverageScore(String id) {
		// TODO Auto-generated method stub
		init();
		int averageScore = 0;
		try {
			ResultSet rs = stmt.executeQuery("Select count(*) as total, sum(point) as totalScore from Cooperation where id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"'");
			while (rs.next()) {
				int totalScore=rs.getInt("totalScore");
				int total=rs.getInt("total");
				averageScore=totalScore/total;
				System.out.println(averageScore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(ArithmeticException e){
			averageScore = 0;
		}
		System.out.println("Insert Succeed!");
		Finish();
		return averageScore;
	}

	@Override
	public int getCooperationHighestPoint(String id) {
		// TODO Auto-generated method stub
		init();
		int highestScore = -1;
		try {
			ResultSet rs = stmt.executeQuery("Select point from Cooperation where point>=ALL(select point from Cooperation where id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"')and (id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"') ");
			while (rs.next()) {
				highestScore= rs.getInt("point");
				System.out.println(highestScore);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
		return highestScore;
	}

	@Override
	public int getCooperationHighestCombo(String id) {
		// TODO Auto-generated method stub
		init();
		int highestCombo = -1;
		try {
			ResultSet rs = stmt.executeQuery("Select combo from Cooperation where combo>=ALL(select combo from Cooperation where id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"')and (id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"') ");
			while (rs.next()) {
				highestCombo= rs.getInt("combo");
				System.out.println(highestCombo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
		return highestCombo;
	}

	@Override
	public ArrayList<CoopRecordPO> getCooperationRecentTen(String id) {
		// TODO Auto-generated method stub
		init();
		ArrayList<CoopRecordPO> gameList = new ArrayList<CoopRecordPO>();
		ArrayList<CoopRecordPO> tenRecordList = new ArrayList<CoopRecordPO>(); 
		try {
			ResultSet rs = stmt.executeQuery("Select * from Cooperation where id1 ='"+id+"' or id2 ='"+id+"' or id3 ='"+id+"' or id4= '"+id+"'");
			while (rs.next()) {
				ArrayList<ActiveUser> userList=new ArrayList<ActiveUser>();
				int gameID=rs.getInt("gameID");
				String date=rs.getString("date");
				int point = rs.getInt("point");
				String id1=rs.getString("id1");
				String id2=rs.getString("id2");
				String id3=rs.getString("id3");
				String id4=rs.getString("id4");
				if(!id1.equals("null")){
					System.out.println("id1: "+id1);
					ActiveUser user=new ActiveUser(id1);
					userList.add(user);
				}
				
				if(!id2.equals("null")){
					ActiveUser user=new ActiveUser(id2);
					userList.add(user);
				}
				
				if(!id3.equals("null")){
					ActiveUser user=new ActiveUser(id3);
					userList.add(user);
				}
				
				if(!id4.equals("null")){
					ActiveUser user=new ActiveUser(id4);
					userList.add(user);
				}
				System.out.println(userList.size());
				CoopRecordPO coopRecord = new CoopRecordPO(gameID,date,userList,point);
				coopRecord.getSize();
				gameList.add(coopRecord);
			}
			Collections.sort(gameList);
			try{
				for(int i=0;i<10;i++){
					tenRecordList.add(gameList.get(i));
					System.out.println("This is"+tenRecordList.get(i).getGameID());
				}
			}catch(IndexOutOfBoundsException e){
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
		return tenRecordList;
	}
	
	private void init(){
		
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String url="jdbc:odbc:Driver={MicroSoft Access Driver (*.mdb)};DBQ=DragonBall.mdb";
			conn = DriverManager.getConnection(url);
			
			stmt=conn.createStatement();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	private void Finish() {
		// TODO Auto-generated method stub
		try {
			if (conn != null)
				stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Finish Succeed!");

	}
	
	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		return df.format(new Date());
	}

}
