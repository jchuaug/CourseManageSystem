package xmu.crms.web.VO;

import java.math.BigInteger;

/**
 * 
 * @author yjj
 *
 */
public class CourseResponseVO {
	private BigInteger id;
	private String name;
	private Integer numClass;
	private Integer numStudent;
	private String startTime;
	private String endTime;
	private String teacherName;
	private String teacherEmail;

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherEmail() {
		return teacherEmail;
	}

	public void setTeacherEmail(String teacherEmail) {
		this.teacherEmail = teacherEmail;
	}

	public Integer getNumClass() {
		return numClass;
	}

	public void setNumClass(Integer numClass) {
		this.numClass = numClass;
	}

	public Integer getNumStudent() {
		return numStudent;
	}

	public void setNumStudent(Integer numStudent) {
		this.numStudent = numStudent;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

}
