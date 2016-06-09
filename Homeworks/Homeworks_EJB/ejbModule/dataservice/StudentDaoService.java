package dataservice;

import java.util.List;

import javax.ejb.Local;

import message.Message;
import entity.Student;

@Local
public interface StudentDaoService extends CommonDaoService {
	public Object getStudentById(int id);

	Message addStudent(Student student);

	boolean addStudents(List<Student> students);
	
	public List<Student> getStudentByInstitute(String institute);
	
	public List<Student> getStudentByYear(int year);
}
