package xmu.crms.entity;

import java.math.BigInteger;
/**
 * 
* <p>Title: Attendance.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
public class Attendance {
	private BigInteger id;
	private User student;
	private ClassInfo classInfo;
	private Seminar seminar;
	private Integer attendanceStatus;

	public Attendance() {
		super();
	}

	public Attendance(BigInteger id, User student, ClassInfo classInfo, Seminar seminar, Integer attendanceStatus) {
		super();
		this.id = id;
		this.student = student;
		this.classInfo = classInfo;
		this.seminar = seminar;
		this.attendanceStatus = attendanceStatus;
	}

	public Attendance(User student, ClassInfo classInfo, Seminar seminar, Integer attendanceStatus) {
		super();
		this.student = student;
		this.classInfo = classInfo;
		this.seminar = seminar;
		this.attendanceStatus = attendanceStatus;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public ClassInfo getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}

	public Seminar getSeminar() {
		return seminar;
	}

	public void setSeminar(Seminar seminar) {
		this.seminar = seminar;
	}

	public Integer getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(Integer attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	@Override
	public String toString() {
		return "Attendance [id=" + id + ", student=" + student + ", classInfo=" + classInfo + ", seminar=" + seminar
				+ ", attendanceStatus=" + attendanceStatus + "]";
	}

}
