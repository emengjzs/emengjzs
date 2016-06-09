package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the course database table.
 * 
 */
@Entity
@NamedQuery(name="Course.findAll", query="SELECT c FROM Course c")
public class Course implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer cid;

	private String institute;

	private String name;

	public Course() {
	}

	public Integer getCid() {
		return this.cid;
	}

	public void setCid(Integer cid) {
		
		this.cid = cid;
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

}