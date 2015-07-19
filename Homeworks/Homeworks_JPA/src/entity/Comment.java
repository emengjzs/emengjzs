package entity;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Calendar;


/**
 * The persistent class for the comment database table.
 * 
 */
@Entity
@NamedQuery(name="Comment.findAll", query="SELECT c FROM Comment c")
public class Comment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int comid;

	private int aid;

	private String conntent;

	private int sid;
	
	
	@Column(name="toSid")
	private int to;
	
	
	
	private String fromName;
	
	private String toName;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timestamp;

	public Comment() {
	}
	
	

	public int getComid() {
		return this.comid;
	}

	public void setComid(int comid) {
		this.comid = comid;
	}

	public int getAid() {
		return this.aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public String getConntent() {
		return this.conntent;
	}

	public void setConntent(String conntent) {
		this.conntent = conntent;
	}

	public int getSid() {
		return this.sid;
	}
	
	public String getTime() {
		return DateHelper.getTimeString(this.timestamp);
	}
	

	public void setSid(int sid) {
		this.sid = sid;
	}

	public Calendar getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Calendar timestamp) {
		this.timestamp = timestamp;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

}