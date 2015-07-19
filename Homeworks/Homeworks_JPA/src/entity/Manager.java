package entity;

public class Manager implements User {

	@Override
	public Object getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setID(Object ID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return "0";
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "总教务处";
	}

	@Override
	public void setPassword(String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Role getFirstRole() {
		// TODO Auto-generated method stub
		return Role.PRIME;
	}

	@Override
	public Role getSecondRole() {
		// TODO Auto-generated method stub
		return Role.DEFAULT;
	}
	
}
