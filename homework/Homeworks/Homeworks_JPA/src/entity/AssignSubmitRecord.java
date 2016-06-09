package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Calendar;


/**
 * The persistent class for the assign_submit_record database table.
 * 
 */
@Entity
@Table(name="assign_submit_record")
@NamedQuery(name="AssignSubmitRecord.findAll", query="SELECT a FROM AssignSubmitRecord a")
public class AssignSubmitRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Transient 
	public static final byte SUBMIT = 0;
	@Transient 
	public static final byte UNSUBMIT = 1;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int srid;

	private int aid;

	@Column(name="file_size")
	private int fileSize;

	@Column(name="file_url")
	private String fileUrl;

	private int lid;

	@Lob
	private String review;

	private float score;

	private int sid;

	private byte status;
	
	private boolean view;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="submit_date")
	private Calendar submitDate;
	
	
	private int tutid;
	
	private String tname;
	
	
	private int rank;
	
	public AssignSubmitRecord() {
	}

	public int getSrid() {
		return this.srid;
	}

	public void setSrid(int srid) {
		this.srid = srid;
	}

	public int getAid() {
		return this.aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileUrl() {
		return this.fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getReview() {
		return this.review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Calendar getSubmitDate() {
		return this.submitDate;
	}

	public void setSubmitDate(Calendar submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitDateString() {
		return DateHelper.getTimeString(this.getSubmitDate());
	}

	public int getTutid() {
		return tutid;
	}
	
	public String getTname() {
		return tname;
	}

	
	public void setTutid(int tutid) {
		this.tutid = tutid;
	}

	
	public void setTname(String tname) {
		this.tname = tname;
	}

	public boolean getView() {
		return view;
	}

	public void setView(boolean view) {
		this.view = view;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	
}