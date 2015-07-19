package entity;

public class AssignmentScore {
	private int sid;
	private String name;
	private String assignmentName;
	private double score;
	private int aid;
	public int getSid() {
		return sid;
	}
	public AssignmentScore(int sid, String name, String assignmentName,
			float score, int aid) {
		super();
		this.sid = sid;
		this.name = name;
		this.assignmentName = assignmentName;
		this.score = score;
		this.aid = aid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignmentName() {
		return assignmentName;
	}
	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
}
