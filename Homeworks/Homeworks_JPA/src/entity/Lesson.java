package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Calendar;


/**
 * The persistent class for the lesson database table.
 * 
 */
@Entity
@NamedQuery(name="Lesson.findAll", query="SELECT l FROM Lesson l")
public class Lesson implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient 
	public static final int READY = 0;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int lid;

	private int cid;

	@Temporal(TemporalType.DATE)
	@Column(name="end_date")
	private Calendar endDate;

	private int no;

	@Temporal(TemporalType.DATE)
	@Column(name="start_date")
	private Calendar startDate;

	private int status;

	private String term;

	@Column(name="term_id")
	private int termId;
	
	@Column(name="score_description")
	private String scoreDescription;

	public String getScoreDescription() {
		return scoreDescription;
	}

	public void setScoreDescription(String scoreDescription) {
		this.scoreDescription = scoreDescription;
	}

	public Lesson() {
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getCid() {
		return this.cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
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

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public int getTermId() {
		return this.termId;
	}

	public void setTermId(int termId) {
		this.termId = termId;
	}

}