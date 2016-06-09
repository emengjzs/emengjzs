package entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;



/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable, User {
	private static final long serialVersionUID = 1L;

	@Id
	private int sid;

	@Temporal(TemporalType.DATE)
	@Column(name="admission_year")
	private Calendar admissionYear;

	private String institute;

	private String name;

	private String password;
	
	@Column(name="is_tuitor", nullable = false, columnDefinition = "BIT(1)")
	private boolean isTuitor;
	
	public boolean getIsTuitor() {
		return isTuitor;
	}
	
	
	public boolean isTuitor() {
		return isTuitor;
	}
	
	public void setIsTuitor(boolean isTuitor) {
		this.isTuitor = isTuitor;
	}


	public void setTuitor(boolean isTuitor) {
		this.isTuitor = isTuitor;
	}

	public Student() {
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Calendar getAdmissionYear() {
		return this.admissionYear;
	}
	
	public void  setYear(int year) {
		if(this.admissionYear == null) {
			this.admissionYear = Calendar.getInstance();
		}
		this.admissionYear.set(year, 0, 1);
	}
	
	public String getYear() {
		return this.admissionYear == null ? "0" : String.valueOf(this.admissionYear.get(Calendar.YEAR));
	}

	public void setAdmissionYear(Calendar admissionYear) {
		this.admissionYear = admissionYear;
	}

	public String getInstitute() {
		return this.institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public Object getID() {
		return this.getSid();
	}

	@Override
	public void setID(Object ID) {
		if(ID instanceof String) {
		try { 
				this.setSid(Integer.parseInt((String) ID));
		} catch(Exception e) {
			this.sid = -1;
		}
		}
		else if(ID instanceof Integer) {
			this.sid = (int) ID;
		}
	}
	
	public Role getFirstRole() {
		return Role.STUDENT;
	}
	
	public Role getSecondRole() {
		return this.isTuitor() ? Role.TUITOR : Role.DEFAULT;
	}
}