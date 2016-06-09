package data.dataimpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import po.UserPO;
import data.dataservice.UserDataService;

public class UserDataServiceImpl implements UserDataService {
	private Connection conn = null;
	Statement stmt = null;
	@Override
	public void Insert(UserPO po) {
		// TODO Auto-generated method stub
		init();
		String id = po.getID();
		String password = po.getPassword();
		double money = po.getMoney();
		double exp = po.getExp();
		int direction = po.getDirection();
		int power=po.getPower();
		System.out.println(id + password + money + exp);

		try {
			stmt.execute("insert into User values('" + id + "','" + password
					+ "','" + money + "','" +  exp + "','" +  direction+ "','" +  power+ "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
	}

	@Override
	public void Delete(String id) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("delete from User where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Delete Succeed!");
		Finish();
	}

	@Override
	public UserPO FindByID(String id) {
		// TODO Auto-generated method stub
		init();
		UserPO po = null;
		String id0 = "'" + id + "'";
		try {
			ResultSet rs = stmt.executeQuery("select * from User where id="
					+ id0);
			while (rs.next()) {
				String password = rs.getString("password");
				int money = rs.getInt("cash");
				int exp = rs.getInt("exp");
				int direction = rs.getInt("direction");
				int power=rs.getInt("power");
				po = new UserPO(id,password,money,exp,direction,power);
				System.out.println(id + password + money + exp );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Find Succeed!");
		Finish();
		return po;
	}
	
	public void UpdatePassword(String id, String password){
		init();
		try {
			stmt.execute("update User set password='" + password + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}
	
	public void UpdateMoney(String id, double money){
		init();
		try {
			stmt.execute("update User set cash=cash+'" + money + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}
	
	public void UpdateExp(String id, double exp){
		init();
		try {
			stmt.execute("update User set exp=exp+'" + exp + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
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

	@Override
	public void InsertProp(String id) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("insert into Prop values('" + id + "','" + 2
					+ "','" + 2 + "','" +  2 + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
	}

	@Override
	public void DeleteProp(String id) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("delete from Prop where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Delete Succeed!");
		Finish();
	}

	@Override
	public void UpdateProp1(String id, int prop1) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update Prop set prop1=prop1+'" + prop1 + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public void UpdateProp2(String id, int prop2) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update Prop set prop2=prop2+'" + prop2 + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public void UpdateProp3(String id, int prop3) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update Prop set prop3=prop3+'" + prop3 + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public void InsertRival(String id) {
		// TODO Auto-generated method stub
		init();
		//pass notpass current lock
		try {
			stmt.execute("insert into Rival values('" + id + "','"+  1 + "','"+ "current"+ "','"+ 0+"')");	
			for(int i=2;i<=8;i++){
				stmt.execute("insert into Rival values('" + id + "','"+  i + "','"+ "notpass"+ "','"+ 0+"')");	
			}
			stmt.execute("insert into Rival values('" + id + "','"+  9 + "','"+ "lock"+ "','"+ 0+"')");	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
		Finish();
	}

	@Override
	public void DeleteRival(String id) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("delete from Rival where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Delete Succeed!");
		Finish();
	}

	@Override
	public void UpdateStage(String id, int stage,String state) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update Rival set state='" + state + "' where id='" + id + "' and stage="+ stage );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public void UpdateDirection(String id, int direction) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update User set direction='" + direction + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public int FindRival(String id) {
		// TODO Auto-generated method stub
		init();
		int stage = -1;
		try {
			ResultSet rs = stmt.executeQuery("select stage from Rival where id='" + id + "'");
			while (rs.next()) {
				stage= rs.getInt("stage");
				System.out.println(stage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return stage;
	}

	@Override
	public int FindDirection(String id) {
		// TODO Auto-generated method stub
		init();
		int direction = 0;
		try {
			ResultSet rs = stmt.executeQuery("select direction from User where id='" + id + "'");
			while (rs.next()) {
				direction= rs.getInt("direction");
				System.out.println(direction);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return direction;
	}

	@Override
	public void UpdatePower(String id, int power) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update User set power=power+'" + power + "' where id='" + id + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public void UpdateEvaluate(String id, int stage, int evaluate) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update Rival set evaluate='" + evaluate + "' where id='" + id + "' and stage="+ stage);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();		
	}

	@Override
	public String FindStageState(String id, int stage) {
		// TODO Auto-generated method stub
		init();
		String message = "";
		System.out.println("select state from Rival  where id='" + id + "' and stage='"+ stage+ "'");
		try {
			ResultSet rs = stmt.executeQuery("select state from Rival  where id='" + id + "' and stage="+ stage);
			while (rs.next()) {
				message= rs.getString("state");
				System.out.println(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return message;
	}

	@Override
	public void InsertDragonBall(String id) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("insert into Ball values('" + id + "','" + 0
					+ "','" + 0 + "','" +  0 + "','" + 0 + "','" + 0 + "','" + 0 + "','" + 0 + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Insert Succeed!");
	}

	@Override
	public void UpdateDragonBall(String id, int ballNO, int number) {
		// TODO Auto-generated method stub
		init();
		try {
			stmt.execute("update Ball set ball"+ballNO+"=ball"+ballNO+"+'" + number + "' where userID='" + id + "'");
			System.out.println("update Ball set ball"+ballNO+"=ball"+ballNO+"'" + number + "' where id='" + id + "'");	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
	}

	@Override
	public boolean canUnLock(String id) {
		// TODO Auto-generated method stub
		boolean canUnLock=true;
		init();
		try {
			ResultSet rs = stmt.executeQuery("select* from Ball  where userID='" + id +  "'");
			while (rs.next()) {
				int ball1= rs.getInt("ball1");
				int ball2= rs.getInt("ball2");
				int ball3= rs.getInt("ball3");
				int ball4= rs.getInt("ball4");
				int ball5= rs.getInt("ball5");
				int ball6= rs.getInt("ball6");
				int ball7= rs.getInt("ball7");
				if(ball1==0||ball2==0||ball3==0||ball4==0||ball5==0||ball6==0||ball7==0){
					canUnLock=false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return canUnLock;
	}

	@Override
	public int FindProp1(String id) {
		// TODO Auto-generated method stub
		init();
		int prop1 = 0;
		try {
			ResultSet rs = stmt.executeQuery("select prop1 from Prop where id='" + id + "'");
			while (rs.next()) {
				prop1= rs.getInt("prop1");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return prop1;
	}

	@Override
	public int FindProp2(String id) {
		// TODO Auto-generated method stub
		init();
		int prop2 = 0;
		try {
			ResultSet rs = stmt.executeQuery("select prop2 from Prop where id='" + id + "'");
			while (rs.next()) {
				prop2= rs.getInt("prop2");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return prop2;
	}

	@Override
	public int FindProp3(String id) {
		// TODO Auto-generated method stub
		init();
		int prop3 = 0;
		try {
			ResultSet rs = stmt.executeQuery("select prop3 from Prop where id='" + id + "'");
			while (rs.next()) {
				prop3= rs.getInt("prop3");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return prop3;
	}

	@Override
	public int FindStageStar(String id, int stage) {
		// TODO Auto-generated method stub
		init();
		int star = -1;
		System.out.println("select evaluate from Rival  where id='" + id + "' and stage='"+ stage+ "'");
		try {
			ResultSet rs = stmt.executeQuery("select evaluate from Rival  where id='" + id + "' and stage="+ stage);
			while (rs.next()) {
				star= rs.getInt("evaluate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return star;
	}

	@Override
	public String FindBall(String id) {
		// TODO Auto-generated method stub
		String ballString="";
		init();
		try {
			ResultSet rs = stmt.executeQuery("select* from Ball  where userID='" + id +  "'");
			while (rs.next()) {
				int ball1= rs.getInt("ball1");
				int ball2= rs.getInt("ball2");
				int ball3= rs.getInt("ball3");
				int ball4= rs.getInt("ball4");
				int ball5= rs.getInt("ball5");
				int ball6= rs.getInt("ball6");
				int ball7= rs.getInt("ball7");
				ballString=ball1+"-"+ball2+"-"+ball3+"-"+ball4+"-"+ball5+"-"+ball6+"-"+ball7;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Update Succeed!");
		Finish();
		return ballString;
	}

}
