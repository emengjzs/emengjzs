package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the lesson_record database table.
 * 
 */
@Entity
@Table(name="lesson_record")
@NamedQuery(name="LessonRecord.findAll", query="SELECT l FROM LessonRecord l")
public class LessonRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer rid = null;

	@Column(name="course_name")
	private String courseName;

	private int lid;

	private int sid;

	private int rank;
	
	private double score;
	
	@Column(name="homework_score")
	private double homeworkScore;
	
	@Column(name="exam_score")
	private double examkScore;
	
	
	public double getHomeworkScore() {
		return homeworkScore;
	}
	
	public  double getExamScore() {
		return examkScore; 
	}
	
	public void setExamScore(double s) {
		examkScore = s; 
	}
	
	
	public void setHomeworkScore(double homeworkScore) {
		this.homeworkScore = homeworkScore;
	}

	public LessonRecord() {
	}

	public int getRid() {
		return this.rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}