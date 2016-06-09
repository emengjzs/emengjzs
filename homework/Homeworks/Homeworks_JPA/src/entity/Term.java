package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Calendar;



/**
 * The persistent class for the term database table.
 * 
 */
@Entity
@NamedQuery(name="Term.findAll", query="SELECT t FROM Term t")
public class Term implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient 
	public static final int PLAN = 0;
	@Transient 
	public static final int RUN = 1;
	@Transient 
	public static final int OVER= 2;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Calendar endDate;

	private int no;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Calendar startDate;

	private int year;

	private int status;

	public Term() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Calendar getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

	public int getNo() {
		return this.no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Calendar getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	
	public String getStartDateString() {
		return DateHelper.getDateString(startDate);
	}
	
	
	public String getEndDateString() {
		return DateHelper.getDateString(endDate);
	}
	
	
	public int getYear() {
		return this.year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}