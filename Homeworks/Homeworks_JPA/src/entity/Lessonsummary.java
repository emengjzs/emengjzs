package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.math.BigDecimal;


/**
 * The persistent class for the lessonsummary database table.
 * 
 */
@Entity
@NamedQuery(name="Lessonsummary.findAll", query="SELECT l FROM Lessonsummary l")
public class Lessonsummary implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal avgscore;

	private BigDecimal bad;

	private BigDecimal badrate;

	private BigDecimal good;

	private BigDecimal great;

	private String institute;

	private String lessonname;
	
	@Id
	private int lid;

	private int maxscore;

	private BigDecimal perfect;

	private double stddev;

	private BigInteger stunum;

	private String term;

	public Lessonsummary() {
	}

	public BigDecimal getAvgscore() {
		return this.avgscore;
	}

	public void setAvgscore(BigDecimal avgscore) {
		this.avgscore = avgscore;
	}

	public BigDecimal getBad() {
		return this.bad;
	}

	public void setBad(BigDecimal bad) {
		this.bad = bad;
	}

	public BigDecimal getBadrate() {
		return this.badrate;
	}

	public void setBadrate(BigDecimal badrate) {
		this.badrate = badrate;
	}

	public BigDecimal getGood() {
		return this.good;
	}

	public void setGood(BigDecimal good) {
		this.good = good;
	}

	public BigDecimal getGreat() {
		return this.great;
	}

	public void setGreat(BigDecimal great) {
		this.great = great;
	}

	public String getInstitute() {
		return this.institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getLessonname() {
		return this.lessonname;
	}

	public void setLessonname(String lessonname) {
		this.lessonname = lessonname;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getMaxscore() {
		return this.maxscore;
	}

	public void setMaxscore(int maxscore) {
		this.maxscore = maxscore;
	}

	public BigDecimal getPerfect() {
		return this.perfect;
	}

	public void setPerfect(BigDecimal perfect) {
		this.perfect = perfect;
	}

	public double getStddev() {
		return this.stddev;
	}

	public void setStddev(double stddev) {
		this.stddev = stddev;
	}

	public BigInteger getStunum() {
		return this.stunum;
	}

	public void setStunum(BigInteger stunum) {
		this.stunum = stunum;
	}

	public String getTerm() {
		return this.term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

}