package logic;

import helper.IntHelper;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.apache.commons.fileupload.FileItem;

import data.AssignmentDao;
import data.StudentDao;
import message.Message;
import entity.AssignSubmitRecord;
import entity.AssignSubmitRecordPO;
import entity.Assignment;
import entity.AssignmentStudentPO;
import entity.AssignmentScore;
import entity.Student;
import entity.User;
import filechecker.FileTypeChecker;

/**
 * Session Bean implementation class AssignmentService
 */
@Stateless
@LocalBean
public class AssignmentService {
	
	@EJB
	FileTypeChecker fchecker;
	
	@EJB
	AssignmentDao assignmentDao;
	
	@EJB
	FileStoreController fcontroller;
	
	@EJB
	StudentDao studentDao;

	
    /**
     * Default constructor. 
     */
    public AssignmentService() {
        // TODO Auto-generated constructor stub
    }
    
    public Message publishAssignment(Assignment as) {
    	as.setStatus(Assignment.Status.RUN);
    	as.setPublishDate(Calendar.getInstance());
    	Message m = assignmentDao.add(as);
    	if(m.isSuccess()) {
	    	List<Student> students = this.getUnsubmitStudents(as);
			for(Student s : students) {
				AssignSubmitRecord ar = new AssignSubmitRecord();
				ar.setAid(as.getAid());
				ar.setLid(as.getLid());
				ar.setSid(s.getSid());
				ar.setView(false);
				ar.setSubmitDate(Calendar.getInstance());
				ar.setStatus(AssignSubmitRecord.UNSUBMIT);
				this.assignmentDao.add(ar);
			}
    	}
    	return m ;
    	
    	//return r ? Message.AddMessage.ADD_SUCCESS : Message.AddMessage.ADD_FAILED;
    }
    
    public Message updateAssignment(Assignment as) {
    	return  assignmentDao.update(as);
    	//return r ? Message.UpdateMessage.UPDATE_SUCCESS : Message.UpdateMessage.UPDATE_FAILED;
    }
    
    
    public Message submitSample(FileItem item, int aid) {

    	Assignment as = (Assignment) this.assignmentDao.getById(aid);
    	if(as == null) {
    		return Message.AddMessage.ADD_FORBID;
    	}
    	Message m = this.fcontroller.saveSample(item, as);
    	if(m.isSuccess()) {
    		return this.assignmentDao.update(as);
    	}
    	return m;
    }
    
    public Message submitHomework(FileItem item, int sid, int aid) {
    	
    	
    	Assignment as = (Assignment) this.assignmentDao.getById(aid);
    	if(as == null) {
    		return Message.AddMessage.ADD_FORBID;
    	}
    	//if(as.getFileType())
    	AssignSubmitRecord ar = this.fcontroller.saveFile(item, as, sid);
    	if(ar == null)  {
    		return Message.AddMessage.ADD_FAILED;
    	}
    	File homework = this.fcontroller.getFile(ar);
    	Message m;
    	if(! this.fchecker.getSuffix(homework).equals(as.getFileType())) {
    		m = Message.Msg.INVALID_TYPE;
    	}
    	
    	else {
    		m = this.fchecker.check(homework).getMessage();
    	}
    	if(! m.isSuccess()) {
    		this.fcontroller.deleteFile(ar);
    		return m;
    	}
    	ar.setStatus(AssignSubmitRecord.SUBMIT);
    	return this.addAssignmentRecord(ar);
    }
    
    
    private Message addAssignmentRecord(AssignSubmitRecord ar) {
    	AssignSubmitRecord origin= this.assignmentDao.getSubmitRecord(ar.getSid(),ar.getAid());
    	if(origin != null) {
    		origin.setFileUrl(ar.getFileUrl());
    		origin.setSubmitDate(ar.getSubmitDate());
    		origin.setStatus(AssignSubmitRecord.SUBMIT);
    		return  this.assignmentDao.update(origin);
    		//return n ? Message.UpdateMessage.UPDATE_SUCCESS : Message.UpdateMessage.UPDATE_FAILED;
    	}
    	
    	else {
    	ar.setSubmitDate(Calendar.getInstance());
    	return assignmentDao.add(ar);  	
    	
    	//return r ? Message.AddMessage.ADD_SUCCESS : Message.AddMessage.ADD_FAILED;
    	}
    }
    
    public Message updateSummary(int aid, String s) {
    	Assignment as = (Assignment) this.assignmentDao.getById(aid);
    	if(as != null) {
    		as.setSummary(s);
    		return this.assignmentDao.update(as);
    	}
    	return Message.UpdateMessage.UPDATE_FORBID;
    }
    
    public Message prepareTeacherReview(int aid) {
    	Assignment as = (Assignment) this.assignmentDao.getById(aid);
    	if(as != null) {
    		as.setStatus(Assignment.Status.TEACHER_REVIEW);
    		return this.assignmentDao.update(as);
    	}
    	return Message.UpdateMessage.UPDATE_FORBID;
    }
    
    public Message teacherReview(int aid, boolean pass) {
    	Assignment as = (Assignment) this.assignmentDao.getById(aid);
    	if(as != null) {
    		if(pass) {
    			as.setStatus(Assignment.Status.SCORE_PUBLISH) ;
    			this.updateAssignmentRank(as.getAid());
    		}
    		else 
    			as.setStatus(Assignment.Status.TUITOR_REREVIEW);
    		return this.assignmentDao.update(as);
    	}
    	return Message.UpdateMessage.UPDATE_FORBID;
    }
    
    
    public Message review(User reviewer, int srid, float score, String review ) {
    	AssignSubmitRecord ar = (AssignSubmitRecord) this.assignmentDao.getById(AssignSubmitRecord.class, srid);
    	if(ar != null) {
    		ar.setScore(score);
    		ar.setReview(review);
    		ar.setView(true);
    		ar.setTutid(IntHelper.toInt(reviewer.getID().toString()));
    		ar.setTname(reviewer.getName());
    		return this.assignmentDao.update(ar);
    		//return r ? Message.UpdateMessage.UPDATE_SUCCESS : Message.UpdateMessage.UPDATE_FAILED;
    	}
    	return Message.UpdateMessage.UPDATE_FORBID;
    }
    
    public List<AssignSubmitRecordPO> getSubmitRecords(int aid) {
    	return this.assignmentDao.getSubmitRecordPOs(aid);
    }
    
    public void getSubmitRecords() {
    	
    }
    
    public void getSubmitRecordByStudentID() {
    	
    }
    
    public List<Assignment> getAssignments(int lid) {
		List<Assignment> aslist =  this.assignmentDao.getByLesson(lid);
		checkAssignment(aslist);
		return aslist;
		
	}
    
    public List<AssignmentStudentPO> getAssignmentsForStudent(int sid, int lid) {
    	List<AssignmentStudentPO> list = this.assignmentDao.getAssignmentForStudent(sid, lid);
    	
    	for(AssignmentStudentPO p : list) {
    		checkAssignment(p.getAs());
    	}
    	return list;
    }
    
    private List<Student> getUnsubmitStudents(Assignment as) {
    	return this.studentDao.getStudentExceptAssignment(as);
    }
    
    private void checkAssignment(List<Assignment> aslist) {
    	for(Assignment as : aslist) {
			checkAssignment(as);
		}
    }
    
    private void checkAssignment(Assignment as){
    Calendar now = Calendar.getInstance();
    if(as.getStatusEnum().equals(Assignment.Status.RUN)) {
    	
		if(now.after(as.getDeadline())) {
			as.setStatus(Assignment.Status.TUITOR_REVIEW);
			this.assignmentDao.update(as);
							
		} 
    }
	else if(as.getStatusEnum().equals(Assignment.Status.TUITOR_REVIEW) &&
			Calendar.getInstance().after(as.getCheckDeadline())) {
			as.setStatus(Assignment.Status.TEACHER_REVIEW);
			this.assignmentDao.update(as);
		}
    }
    
    public List<AssignmentScore> getAllScores(int lid) {
    	return this.assignmentDao.getAllScore(lid);
    }
    
    
    public Message updateAssignmentRank(int aid) {
    	List<AssignSubmitRecord> records = this.assignmentDao.getSubmitRecordsWithDesc(aid);
    	int rank = 0;
    	float score = Float.MAX_VALUE;
    	for(AssignSubmitRecord r : records) {
    		if(r.getScore() < score) {
    			rank ++;
    			score = r.getScore();
    		}
    		r.setRank(rank);
    		this.assignmentDao.update(r);
    	}
    	return Message.UpdateMessage.UPDATE_SUCCESS;
    }
    
    public List getRecentAssignment(int lid) {
    	return assignmentDao.getRecent(lid);
    }
}
