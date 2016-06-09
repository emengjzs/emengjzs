package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the admin database table.
 * 
 */
@Entity
@NamedQuery(name="Admin.findAll", query="SELECT a FROM Admin a")
public class Admin implements Serializable, User {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String password;

	public Admin() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getID() {
		return getId();
		
	}

	@Override
	public void setID(Object ID) {
		setId(ID.toString());
		
	}
	public Role getFirstRole() {
		return Role.ADMIN;
	}
	
	public Role getSecondRole() {
		return Role.DEFAULT;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "admin";
	}

}