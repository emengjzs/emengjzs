package service;

import javax.ejb.Local;
import message.Message;
import entity.Course;

@Local
public interface CourseServiceRemote {
	public Message addCourse(Course course);
}
