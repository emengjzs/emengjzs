package entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the tuitor_assign database table.
 * 
 */
@Entity
@Table(name="tuitor_assign")
@NamedQuery(name="TuitorAssign.findAll", query="SELECT t FROM TuitorAssign t")
public class TuitorAssign implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="lesson_lid")
	private int lessonLid;

	@Column(name="student_sid")
	private int studentSid;

	public TuitorAssign() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLessonLid() {
		return this.lessonLid;
	}

	public void setLessonLid(int lessonLid) {
		this.lessonLid = lessonLid;
	}

	public int getStudentSid() {
		return this.studentSid;
	}

	public void setStudentSid(int studentSid) {
		this.studentSid = studentSid;
	}

}