package entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.math.BigDecimal;


/**
 * The persistent class for the assignmentsummary database table.
 * 
 */
@Entity
@NamedQuery(name="Assignmentsummary.findAll", query="SELECT a FROM Assignmentsummary a")
public class Assignmentsummary implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private int aid;
	
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private float aiisgnmentscore;

	private double avgrate;

	private double avgscore;

	private BigDecimal bad;

	private BigDecimal good;

	private BigDecimal greate;

	private int lid;

	private float maxscore;

	private BigDecimal miss;

	private BigDecimal perfect;

	private BigInteger stunum;

	public Assignmentsummary() {
	}

	public int getAid() {
		return this.aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public float getAiisgnmentscore() {
		return this.aiisgnmentscore;
	}

	public void setAiisgnmentscore(float aiisgnmentscore) {
		this.aiisgnmentscore = aiisgnmentscore;
	}

	public double getAvgrate() {
		return this.avgrate;
	}

	public void setAvgrate(double avgrate) {
		this.avgrate = avgrate;
	}

	public double getAvgscore() {
		return this.avgscore;
	}

	public void setAvgscore(double avgscore) {
		this.avgscore = avgscore;
	}

	public BigDecimal getBad() {
		return this.bad;
	}

	public void setBad(BigDecimal bad) {
		this.bad = bad;
	}

	public BigDecimal getGood() {
		return this.good;
	}

	public void setGood(BigDecimal good) {
		this.good = good;
	}

	public BigDecimal getGreate() {
		return this.greate;
	}

	public void setGreate(BigDecimal greate) {
		this.greate = greate;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public float getMaxscore() {
		return this.maxscore;
	}

	public void setMaxscore(float maxscore) {
		this.maxscore = maxscore;
	}

	public BigDecimal getMiss() {
		return this.miss;
	}

	public void setMiss(BigDecimal miss) {
		this.miss = miss;
	}

	public BigDecimal getPerfect() {
		return this.perfect;
	}

	public void setPerfect(BigDecimal perfect) {
		this.perfect = perfect;
	}

	public BigInteger getStunum() {
		return this.stunum;
	}

	public void setStunum(BigInteger stunum) {
		this.stunum = stunum;
	}

}