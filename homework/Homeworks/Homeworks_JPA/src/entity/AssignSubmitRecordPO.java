package entity;

public class AssignSubmitRecordPO {
	private Student student;
	private AssignSubmitRecord assignSubmitRecord;
	
	public AssignSubmitRecordPO() {
		
	}
	
	

	
	public AssignSubmitRecordPO(Student s, AssignSubmitRecord as) {
		this.student = s;
		this.assignSubmitRecord = as;
	}
	
	
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public AssignSubmitRecord getAssignSubmitRecord() {
		return assignSubmitRecord;
	}
	public void setAssignSubmitRecord(AssignSubmitRecord assignSubmitRecord) {
		this.assignSubmitRecord = assignSubmitRecord;
	}
	
	
	
}
