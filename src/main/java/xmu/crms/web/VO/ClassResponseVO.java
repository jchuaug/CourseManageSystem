package xmu.crms.web.VO;

import java.math.BigInteger;

public class ClassResponseVO {
	private BigInteger id;

	private String name;

	private Integer numStudent;

	private String time;

	private String site;

	private String courseName;

	private String courseTeacher;

	


	public ClassResponseVO(BigInteger id, String name, Integer numStudent, String time, String site, String courseName,
			String courseTeacher) {
		super();
		this.id = id;
		this.name = name;
		this.numStudent = numStudent;
		this.time = time;
		this.site = site;
		this.courseName = courseName;
		this.courseTeacher = courseTeacher;
	}

	@Override
	public String toString() {
		return "ClassVO [id=" + id + ", name=" + name + ", numStudent=" + numStudent + ", time=" + time + ", site="
				+ site + ", courseName=" + courseName + ", courseTeacher=" + courseTeacher + "]";
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

	public Integer getNumStudent() {
		return numStudent;
	}

	public void setNumStudent(Integer numStudent) {
		this.numStudent = numStudent;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseTeacher() {
		return courseTeacher;
	}

	public void setCourseTeacher(String courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public ClassResponseVO() {
		super();
	}

	


}