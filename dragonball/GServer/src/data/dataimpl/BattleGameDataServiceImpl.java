package data.dataimpl;

import gamemodel.Debug;

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
import po.BattleRecordPO;
import data.dataservice.BattleGameDataService;

public class BattleGameDataServiceImpl implements BattleGameDataService {
	private Connection conn = null;
	Statement stmt = null;
	int TEAM_MEMBER=4;
	@Override
	public void InsertBattle(ArrayList<ActiveUser> playerList1,
			ArrayList<ActiveUser> playerList2, int point1, int point2) {
		// TODO Auto-generated method stub
		init();
		int offset1=TEAM_MEMBER-playerList1.size();
		int offset2=TEAM_MEMBER-playerList2.size();

		try {
			String sql="insert into Battle ([date],oneid1,oneid2,oneid3,oneid4,twoid1,twoid2,twoid3,twoid4,point1,point2) values('"+getCurrentDate()+"','";
			for(ActiveUser player: playerList1){
				sql+=player.getUserID()+"','";
			}
			for(int i=0;i<offset1;i++){
				sql+="null"+"','";
			}
			for(ActiveUser player: playerList2){
				sql+=player.getUserID()+"','";
			}
			for(int i=0;i<offset2;i++){
				sql+="null"+"','";
			}
			sql+= point1 + "','" +  point2 + "')";
			Debug.outputl("Battle sql = " + sql);
			stmt.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Finish();
		System.out.println("Insert Succeed!");
	}

	@Override
	public int getBattleHighestPoint(String id) {
		// TODO Auto-generated method stub
		init();
		int highestScore = -1;
		int Score1=-1;
		int Score2=-1;
		try {
			ResultSet rs = stmt.executeQuery("Select point1 from Battle where point1>=ALL(select point1 from Battle where oneid1 ='"+id+"' or oneid2 ='"+id+"' or oneid3 ='"+id+"' or oneid4= '"+id+"') and (oneid1 ='"+id+"' or oneid2 ='"+id+"' or oneid3 ='"+id+"' or oneid4= '"+id+"') ");
			while (rs.next()) {
				Score1= rs.getInt("point1");
			}
			ResultSet rs1 = stmt.executeQuery("Select point2 from Battle where point2>=ALL(select point2 from Battle where twoid1 ='"+id+"' or twoid2 ='"+id+"' or twoid3 ='"+id+"' or twoid4= '"+id+"')and (twoid1 ='"+id+"' or twoid2 ='"+id+"' or twoid3 ='"+id+"' or twoid4= '"+id+"') ");
			while (rs1.next()) {
				Score2= rs1.getInt("point2");
			}
			if(Score1>Score2){
				highestScore=Score1;
			}else{
				highestScore=Score2;
			}
			System.out.println(highestScore);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
		return highestScore;
	}

	@Override
	public ArrayList<BattleRecordPO> getBattleRecentTen(String id) {
		// TODO Auto-generated method stub
		init();
		ArrayList<BattleRecordPO> gameList = new ArrayList<BattleRecordPO>();
		ArrayList<BattleRecordPO> tenRecordList = new ArrayList<BattleRecordPO>(); 
		try {
			ResultSet rs = stmt.executeQuery("Select * from Battle where oneid1 ='"+id+"' or oneid2 ='"+id+"' or oneid3 ='"+id+"' or oneid4= '"+id+"'or twoid1 ='"+id+"' or twoid2 ='"+id+"' or twoid3 ='"+id+"' or twoid4= '"+id+"'");
			while (rs.next()) {
				ArrayList<ActiveUser> userList1=new ArrayList<ActiveUser>();
				ArrayList<ActiveUser> userList2=new ArrayList<ActiveUser>();
				int gameID=rs.getInt("gameID");
				String date=rs.getString("date");
				int point1 = rs.getInt("point1");
				int point2 = rs.getInt("point2");
				String oneid1=rs.getString("oneid1");
				String oneid2=rs.getString("oneid2");
				String oneid3=rs.getString("oneid3");
				String oneid4=rs.getString("oneid4");
				String twoid1=rs.getString("twoid1");
				String twoid2=rs.getString("twoid2");
				String twoid3=rs.getString("twoid3");
				String twoid4=rs.getString("twoid4");
				if(!oneid1.equals("null")){
					ActiveUser user=new ActiveUser(oneid1);
					userList1.add(user);
				}
				
				if(!oneid2.equals("null")){
					ActiveUser user=new ActiveUser(oneid2);
					userList1.add(user);
				}
				
				if(!oneid3.equals("null")){
					ActiveUser user=new ActiveUser(oneid3);
					userList1.add(user);
				}
				
				if(!oneid4.equals("null")){
					ActiveUser user=new ActiveUser(oneid4);
					userList1.add(user);
				}
				if(!twoid1.equals("null")){
					ActiveUser user=new ActiveUser(twoid1);
					userList2.add(user);
				}
				
				if(!twoid2.equals("null")){
					ActiveUser user=new ActiveUser(twoid2);
					userList2.add(user);
				}
				
				if(!twoid3.equals("null")){
					ActiveUser user=new ActiveUser(twoid3);
					userList2.add(user);
				}
				
				if(!twoid4.equals("null")){
					ActiveUser user=new ActiveUser(twoid4);
					userList2.add(user);
				}
				BattleRecordPO battleRecord = new BattleRecordPO(gameID,date,userList1,userList2,point1,point2);
				gameList.add(battleRecord);
			}
			Collections.sort(gameList);
			try{
				for(int i=0;i<10;i++){
					tenRecordList.add(gameList.get(i));
					tenRecordList.get(i).print();
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
