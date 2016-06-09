package entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.*;

import debug.Debug;



/**
 * The persistent class for the assignment database table.
 * 
 */
@Entity
@NamedQuery(name="Assignment.findAll", query="SELECT a FROM Assignment a")
public class Assignment implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static enum Status {
		RUN("进行中"),
		TUITOR_REVIEW("批改中"),
		TEACHER_REVIEW("审核成绩中"),
		SCORE_PUBLISH("成绩发布"),
		TUITOR_REREVIEW("重批中");
		
		private String info;
		
		public String getInfo() {
			return info;
		}
		
		private Status(String info) {
			this.info = info;
		}
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int aid;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="check_deadline")
	private Calendar checkDeadline;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar deadline;

	@Lob
	private String description;

	private int difficulty;

	@Column(name="file_type")
	private String fileType;

	private int lid;
	
	private int status;
	
	private String name;
	
	@Lob
	private String summary;
	
	private String file;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="publish_date")
	private Calendar publishDate;

	private float score;

	public Assignment() {
	}

	public int getAid() {
		return this.aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public Calendar getCheckDeadline() {
		return this.checkDeadline;
	}
	
	public String getCheckDeadlineString() {
		return DateHelper.getTimeString(this.checkDeadline);
	}
	

	public void setCheckDeadline(Calendar checkDeadline) {
		this.checkDeadline = checkDeadline;
	}

	public Calendar getDeadline() {
		return this.deadline;
	}
	
	public String getDeadlineString() {
		return DateHelper.getTimeString(this.deadline);
	}
	

	public void setDeadline(Calendar deadline) {
		this.deadline = deadline;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDifficulty() {
		return this.difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getPublishDate() {
		return this.publishDate;
	}
	
	public String getPublishDateString() {
		return DateHelper.getTimeString(this.publishDate);
	}
	
	public void setPublishDate(Calendar publishDate) {
		this.publishDate = publishDate;
	}

	public float getScore() {
		return this.score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public Status getStatusEnum() {
		if(this.status >= Status.values().length) {
			Debug.print("Invalid Assignment Status!!!");
			this.status = 0;
			return Status.RUN;
		}
		return Status.values()[this.status];
	}
	
	public void setStatus(Status s) {
		this.status = s.ordinal();
	}
	
	
	public boolean getCanReviewHomework() {
		boolean r =   
				this.status == Status.TUITOR_REREVIEW.ordinal() || 
						this.status == Status.TUITOR_REVIEW.ordinal();
		Debug.print("可以改作业?" + r );
		return r;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}