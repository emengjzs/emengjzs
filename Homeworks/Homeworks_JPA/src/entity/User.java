package entity;

public interface User {
	public Object getID();
	public void setID(Object ID);
	public String getPassword();
	public String getName();
	public void setPassword(String password);
	public Role getFirstRole();
	public Role getSecondRole();
}
