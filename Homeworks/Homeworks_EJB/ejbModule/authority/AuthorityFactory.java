package authority;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import authorityimp.AdminLessonAuthority;
import authorityimp.NoneLessonAuthority;
import authorityimp.StudentLessonAuthority;
import authorityimp.TeacherLessonAuthority;
import data.LessonDao;
import entity.LessonPO;
import entity.Student;
import entity.Teacher;
import entity.User;
@Stateless
@LocalBean
public class AuthorityFactory {
	@EJB
	LessonDao lessonDao;
	
	public LessonAuthority getLessonAuthority(int lid, User user) {
		LessonPO l = (LessonPO) this.lessonDao.getLessonPOById(lid);
		return getLessonAuthority(l,user);
	}
	
	public LessonAuthority getLessonAuthority(LessonPO l, User user) {
		//LessonPO l = (LessonPO) this.lessonDao.getById(lid);
		if(user instanceof entity.Admin) {
			return new AdminLessonAuthority(l);
		}
		if(user instanceof entity.Teacher) {
			return new TeacherLessonAuthority((Teacher) user, l);
		}
		if(user instanceof entity.Student) {
			StudentLessonAuthority n = new StudentLessonAuthority((Student) user, l);
			n.setIsOwnLesson(lessonDao.hasStudentRecord(l.getLid(), ((entity.Student) user).getSid()));
			return n;
		}
		return new NoneLessonAuthority();
	}
}
