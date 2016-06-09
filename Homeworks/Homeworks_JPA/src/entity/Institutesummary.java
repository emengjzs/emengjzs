package entity;

import java.math.BigDecimal;

public class Institutesummary {
	String institute;
	String term;
	int count;
	double maxscore;
	double avgscore;
	int perfect;
	int great;
	
	int index;
	
	public int getIndex() {
		return (int) (perfect * 3 + great * 2 + good *1 - bad * 2 + count / 2 + (avgscore -30) * 10);
	}
	public void setIndex(int index) {
		this.index = index;
	}
	int good;
	int bad;
	double avgrate;
	public Institutesummary(String institute, String term, long count,
			int maxscore, double avgscore, BigDecimal perfect, BigDecimal great, BigDecimal good,
			BigDecimal bad, double avgrate) {
		super();
		this.institute = institute;
		this.term = term;
		this.count = (int) count;
		this.maxscore = maxscore;
		this.avgscore = avgscore;
		this.perfect = perfect.intValue();
		this.great = great.intValue();
		this.good = good.intValue();
		this.bad = bad.intValue();
		this.avgrate = avgrate;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getMaxscore() {
		return maxscore;
	}
	public void setMaxscore(double maxscore) {
		this.maxscore = maxscore;
	}
	public double getAvgscore() {
		return avgscore;
	}
	public void setAvgscore(double avgscore) {
		this.avgscore = avgscore;
	}
	public int getPerfect() {
		return perfect;
	}
	public void setPerfect(int perfect) {
		this.perfect = perfect;
	}
	public int getGreat() {
		return great;
	}
	public void setGreat(int great) {
		this.great = great;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getBad() {
		return bad;
	}
	public void setBad(int bad) {
		this.bad = bad;
	}
	public double getAvgrate() {
		return avgrate;
	}
	public void setAvgrate(double avgrate) {
		this.avgrate = avgrate;
	}
	
	
}
