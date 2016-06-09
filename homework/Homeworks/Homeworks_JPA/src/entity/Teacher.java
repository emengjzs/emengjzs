package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the teacher database table.
 * 
 */
@Entity
@NamedQuery(name="Teacher.findAll", query="SELECT t FROM Teacher t")
public class Teacher implements Serializable, User {
	private static final long serialVersionUID = 1L;

	@Id
	private int tid;

	private String institute;

	@Column(name="is_manager")
	private boolean isManager;

	private String name;

	private String password;

	public Teacher() {
	}

	public int getTid() {
		return this.tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public String getInstitute() {
		return this.institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public boolean isManager() {
		return this.isManager;
	}

	public void setIsManager(boolean isManager) {
		this.isManager = isManager;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Object getID() {
		return this.getTid();
	}

	@Override
	public void setID(Object ID) {
		if(ID instanceof String) {
			try {
			this.setTid(Integer.parseInt((String) ID));
			} catch(Exception e) {this.tid = -1;}
		}
		else if(ID instanceof Integer) {
			this.tid = (int) ID;
		}
	}
	
	public Role getFirstRole() {
		return Role.TEACHER;
	}
	
	public Role getSecondRole() {
		return this.isManager() ? Role.CHARGE : Role.DEFAULT;
	}
}