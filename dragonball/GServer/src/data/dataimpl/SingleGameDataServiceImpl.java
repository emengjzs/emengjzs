package data.dataimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import po.DailyRecordPO;
import po.GameRecordPO;
import data.dataservice.SingleGameDataService;

public class SingleGameDataServiceImpl implements SingleGameDataService {

	private Connection conn = null;
	Statement stmt = null;
	@Override
	public void InsertGameRecord(String userID, int point, int combo) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("insert into [Single](id,gameDate,point,combo) values('" + userID + "','" + getCurrentDate()
					+ "','" + point + "','" +  combo + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
	}

	@Override
	public int getHighestPoint(String id) {
		// TODO Auto-generated method stub
		init();
		int highestScore = -1;
		try {
			ResultSet rs = stmt.executeQuery("Select point from [Single] where point>=ALL(select point from [Single] where id ='"+id+"')");
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
	public int getHighestCombo(String id) {
		// TODO Auto-generated method stub
		init();
		int highestCombo = -1;
		try {
			ResultSet rs = stmt.executeQuery("Select combo from [Single] where combo>=ALL(select combo from [Single] where id ='"+id+"')");
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
	public int getTotalGame(String id) {
		// TODO Auto-generated method stub
		init();
		int totalGame = -1;
		try {
			ResultSet rs = stmt.executeQuery("Select count(*) as total from [Single] where id ='"+id+"'");
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
	public int getAverageScore(String id) {
		// TODO Auto-generated method stub
		init();
		int averageScore = 0;
		try {
			ResultSet rs = stmt.executeQuery("Select count(*) as total, sum(point) as totalScore from [Single] where id ='"+id+"'");
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
	public ArrayList<GameRecordPO> getRecentTen(String id) {
		// TODO Auto-generated method stub
		init();
		ArrayList<GameRecordPO> gameList = new ArrayList<GameRecordPO>();
		ArrayList<GameRecordPO> tenRecordList = new ArrayList<GameRecordPO>(); 
		try {
			ResultSet rs = stmt.executeQuery("Select * from [Single] where id ='"+id+"'");
			while (rs.next()) {
				int gameID=rs.getInt("gameID");
				String date=rs.getString("gameDate");
				int point = rs.getInt("point");
				int combo = rs.getInt("combo");
				GameRecordPO gameRecord = new GameRecordPO(gameID,date,point,combo);
				gameList.add(gameRecord);
			}
			Collections.sort(gameList);
			try{
				for(int i=0;i<10;i++){
					tenRecordList.add(gameList.get(i));
					System.out.println(tenRecordList.get(i).getGameID());
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
	
	@Override
	public ArrayList<GameRecordPO> getTopFive(String id) {
		// TODO Auto-generated method stub
		init();
		ArrayList<GameRecordPO> gameList = new ArrayList<GameRecordPO>();
		ArrayList<GameRecordPO> topList = new ArrayList<GameRecordPO>(); 
		try {
			ResultSet rs = stmt.executeQuery("Select * from [Single] where id ='"+id+"' order by point DESC");
			while (rs.next()) {
				int gameID=rs.getInt("gameID");
				String date=rs.getString("gameDate");
				int point = rs.getInt("point");
				int combo = rs.getInt("combo");
				GameRecordPO gameRecord = new GameRecordPO(gameID,date,point,combo);
				gameList.add(gameRecord);
			}
			try{
				for(int i=0;i<5;i++){
					topList.add(gameList.get(i));
				}
			}catch(IndexOutOfBoundsException e){
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
		return topList;
	}

	@Override
	public ArrayList<DailyRecordPO> getDailyRecord(String id) {
		// TODO Auto-generated method stub
		init();
		ArrayList<DailyRecordPO> gameList = new ArrayList<DailyRecordPO>();
		ArrayList<DailyRecordPO> weekList = new ArrayList<DailyRecordPO>();
		try {
			ResultSet rs = stmt.executeQuery("Select count(gameDate) as total, gameDate, sum(point) as totalPoint, sum(combo) as totalCombo from [Single] where id ='"+id+"' Group by gameDate");
			while (rs.next()) {
				String date=rs.getString("gameDate");
				int total=rs.getInt("total");
				int averagePoint=rs.getInt("totalPoint")/total;
				int averageCombo=rs.getInt("totalCombo")/total;
				DailyRecordPO dailyRecord = new DailyRecordPO(date, total, averagePoint, averageCombo);
				gameList.add(dailyRecord);
			}
			int weekday=getWeekOfDate();
			Collections.sort(gameList);
			try{
				for(int i=0;i<weekday-1;i++){
					boolean found=false;
					for(int j=0;j<i;j++){
						DailyRecordPO record=gameList.get(j);
						if(getIntervalDays(record.getDate())==i){
							weekList.add(gameList.get(i));
							found=true;
							break;
						}
					}
					if(!found){
						String date=getDistanceDate(i);
						DailyRecordPO record = new DailyRecordPO(date, 0, 0, 0);
						weekList.add(record);
					}
				}
			}catch(IndexOutOfBoundsException e){
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Finish();
		return gameList;
	}
	
	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		return df.format(new Date());
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
	
    private int getWeekOfDate() {
    	Date date=new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
 
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
 
        return w;
    }
    
    private int getIntervalDays(String startDay){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date startDate=null;
    	try {
			startDate = format.parse(startDay);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	Date endDate=new Date();
    	long s1=startDate.getTime();
    	long s2=endDate.getTime();
    	long e=s2-s1;
    	return (int)(e/(1000*60*60*24));
    }
    
    private String getDistanceDate(int i){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	Date endDate=new Date();
    	long s1=endDate.getTime();
    	long s2=s1-i*1000*60*60*24;
    	
    	Date date=new Date(s2);
    	
    	String dateString=format.format(date);
    	
    	return dateString;
    	
    }

}
