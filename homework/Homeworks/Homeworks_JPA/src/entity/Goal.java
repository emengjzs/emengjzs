package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the goal database table.
 * 
 */
@Entity
@NamedQuery(name="Goal.findAll", query="SELECT g FROM Goal g")
public class Goal implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int gid;
	@Lob
	private String content;

	@Temporal(TemporalType.DATE)
	private Date year;

	public Goal() {
	}

	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getYear() {
		return this.year;
	}
	
	@SuppressWarnings("deprecation")
	public String getYearStr() {
		return String.valueOf(year.getYear() + 1900);
	}
 
	public void setYear(Date year) {
		this.year = year;
	}

}