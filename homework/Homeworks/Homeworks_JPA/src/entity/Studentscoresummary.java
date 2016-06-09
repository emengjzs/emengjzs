package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the studentscoresummary database table.
 * 
 */
@Entity
@NamedQuery(name="Studentscoresummary.findAll", query="SELECT s FROM Studentscoresummary s")
public class Studentscoresummary implements Serializable {
	private static final long serialVersionUID = 1L;

	private String coursename;

	private String institute;

	private int lid;

	private int rank;
	@Id
	private int rid;

	private double score;
	
	@Column(name="homeworkscore")
	private double homeworkScore;
	
	@Column(name="examscore")
	private double examScore; 
	
	private int sid;

	private String studentname;

	private String term;

	public Studentscoresummary() {
	}

	public String getCoursename() {
		return this.coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	public String getInstitute() {
		return this.institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getRank() {
		return this.rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getRid() {
		return this.rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public double getScore() {
		return this.score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getHomeworkScore() {
		return homeworkScore;
	}

	public void setHomeworkScore(double homeworkScore) {
		this.homeworkScore = homeworkScore;
	}

	public double getExamScore() {
		return examScore;
	}

	public void setExamScore(double examScore) {
		this.examScore = examScore;
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getStudentname() {
		return this.studentname;
	}

	public void setStudentname(String studentname) {
		this.studentname = studentname;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	public double getGPA() {
		return score / 20.0;
	}
	
}