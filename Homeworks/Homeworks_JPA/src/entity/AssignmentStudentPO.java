package entity;

import java.util.Calendar;

import entity.Assignment.Status;

public class AssignmentStudentPO {
	private Assignment as;
	private AssignSubmitRecord ar;
	
	public AssignmentStudentPO(Assignment as, AssignSubmitRecord ar) {
		this.setAs(as);
		this.setAr(ar);
	}

	public Assignment getAs() {
		return as;
	}

	public void setAs(Assignment as) {
		this.as = as;
	}
	public boolean getView() {
		return ar.getView();
	}

	public AssignSubmitRecord getAr() {
		return ar;
	}

	public void setAr(AssignSubmitRecord ar) {
		this.ar = ar;
	}
	
	
	public int getAid() {
		return this.as.getAid();
	}

	

	public Calendar getCheckDeadline() {
		return this.as.getCheckDeadline();
	}
	
	public String getCheckDeadlineString() {
		return this.as.getCheckDeadlineString();
	}
	

	

	public Calendar getDeadline() {
		return as.getDeadline();
	}
	
	public String getDeadlineString() {
		return as.getDeadlineString();
	}
	

	
	public String getDescription() {
		return this.as.getDescription();
	}



	public int getDifficulty() {
		return this.as.getDifficulty();
	}

	

	public String getFileType() {
		return this.as.getFileType();
	}

	

	public int getLid() {
		return this.as.getLid();
	}

	

	public String getName() {
		return this.as.getName();
	}

	

	public Calendar getPublishDate() {
		return this.as.getPublishDate();
	}
	
	public String getPublishDateString() {
		return this.as.getPublishDateString();
	}
	


	public float getScore() {
		return this.as.getScore();
	}

	

	public int getStatus() {
		return as.getStatus();
	}

	public String getSubmitDateString() {
		return ar.getSubmitDateString();
	}
	
	public Status getStatusEnum() {
		return as.getStatusEnum();
	}
	
	public int getSrid() {
		return this.ar.getSrid();
	}

	
	//public int getAid() {
	//	return this.ar.getAid();
	//}

	

	public int getFileSize() {
		return this.ar.getFileSize();
	}

	

	public String getFileUrl() {
		return this.ar.getFileUrl();
	}

	

	//public int getLid() {
	//	return this.ar.lid;
	//}

	

	public String getReview() {
		return this.ar.getReview();
	}

	

	public float getSubmitScore() {
		return this.ar.getScore();
	}

	

	public int getSid() {
		return this.ar.getSid();
	}

	

	public byte getSubmitStatus() {
		return this.ar.getStatus();
	}

	

	public Calendar getSubmitDate() {
		return this.ar.getSubmitDate();
	}

	public boolean getCanSubmitHomework() {
		return Calendar.getInstance().before(this.as.getDeadline());
	}

	public boolean getCanReviewHomework() {
		return this.as.getCanReviewHomework();
	}
	public String getSummary() {
		return this.as.getSummary();
	}
	
	public String getFile() {
		return this.as.getFile();
	}
	
	
	
	public int getTutid() {
		return ar.getTutid();
	}
	
	public String getTname() {
		return ar.getTname();
	}
	
}
