package logic;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import message.Message;
import authority.AuthorityFactory;
import authority.LessonAuthority;
import data.AssignmentDao;
import data.BaseDao;
import data.LessonDao;
import data.StudentDao;
import data.TeacherDao;
import entity.Lesson;
import entity.LessonPO;
import entity.LessonRecord;
import entity.Student;
import entity.TeacherAssign;
import entity.TuitorAssign;
import entity.User;
@Stateless
@LocalBean
public class LessonService {
	
	@EJB
	BaseDao baseDao;
	
	@EJB
	StudentDao studentDao;
	
	@EJB
	LessonDao lessonDao;
	
	@EJB
	TeacherDao teacherDao;
	
	@EJB
	AssignmentDao assignmentDao;
	
	@EJB
	AuthorityFactory authFactory;
	
	
	public LessonPO getLessonPOById(int lid) {
		return this.lessonDao.getLessonPOById(lid);
	}
	
	public List<Student> getTeacherExceptLesson(int lid) {
		return this.teacherDao.getTeacherExceptLesson(lid);
	}
	
	public List<Student> getStudentExceptLesson(int lid) {
		return this.studentDao.getStudentExceptLesson(lid);
	}
	
	public List<Student> getTuitorExceptLesson(int lid) {
		return this.studentDao.getTuitorExceptLesson(lid);
	}
	
	public List<Student> getStudent(int lid) {
		return this.studentDao.getStudentByLesson(lid);
	}
	
	//public ServiceAuthorityList getLessonAuthority(LessonPO lesson, Admin admin) {
	//	return null;
	//}
	
	
	public LessonAuthority getLessonAuthority(LessonPO lesson, User user) {
		return this.authFactory.getLessonAuthority(lesson, user);
	}
	
	
	
	
	public Message addTeachers(List<Integer> teacherIDs, int lid) {
		try {
		for(int tid : teacherIDs) {
			TeacherAssign ta = new TeacherAssign();
			ta.setLid(lid);
			ta.setTid(tid);
			boolean r = baseDao.add(ta).isSuccess();
			if(! r) throw new Exception();
		}
		} catch(Exception e) {e.printStackTrace(); return Message.AddMessage.ADD_FAILED;}
		return Message.AddMessage.ADD_SUCCESS;
	}
		

	
	
	public Message addStudents(List<Integer> studentIDs, int lid) {
		for(int sid : studentIDs) {
			LessonRecord lr = new LessonRecord();
			lr.setLid(lid);
			lr.setSid(sid);
			baseDao.add(lr);
		}
		return Message.AddMessage.ADD_SUCCESS;
	}
	
	public Message addTuitors(List<Integer> tuitorIDs, int lid) {
		for(int sid : tuitorIDs) {
			TuitorAssign ta = new TuitorAssign();
			ta.setLessonLid(lid);
			ta.setStudentSid(sid);
			baseDao.add(ta);
		}
		return Message.AddMessage.ADD_SUCCESS;
	}
	
	public Lesson getLessonById(int lid) {
		return (Lesson) this.lessonDao.getById(lid);
	}
	
	public LessonRecord getLessonRecord(int sid, int lid) {
		return lessonDao.getLessonRecordByStudentByLesson(lid, sid); 
	}
	
	
	//public Message uploadLessonScore(File excel, int lid) {
	//	
	//}
	
	public Message updateLessonRecordRank(int lid) {
		List<LessonRecord> records = this.lessonDao.getLessonRecordById(lid, "score", false);
		float score = Float.MAX_VALUE;
		int rank = 0;
		for(LessonRecord r : records) {
			if(r.getScore() < score) {
				rank ++;
			}
			r.setRank(rank);
			this.lessonDao.update(r);
		}
		return Message.UpdateMessage.UPDATE_SUCCESS;
	}
}
