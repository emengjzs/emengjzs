package entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the teacher_assign database table.
 * 
 */
@Entity
@Table(name="teacher_assign")
@NamedQuery(name="TeacherAssign.findAll", query="SELECT t FROM TeacherAssign t")
public class TeacherAssign implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="course_name")
	private String courseName;

	private int lid;

	private int tid;

	public TeacherAssign() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourseName() {
		return this.courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getLid() {
		return this.lid;
	}

	public void setLid(int lid) {
		this.lid = lid;
	}

	public int getTid() {
		return this.tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

}