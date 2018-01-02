package xmu.crms.entity;

import java.math.BigInteger;
/**
 * 
* <p>Title: Course.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
public class Topic {
	private BigInteger id;
	private String serial;
	private String name;
	private String description;
	private Integer groupNumberLimit;
	private Integer groupStudentLimit;
	private Seminar seminar;
	
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getGroupNumberLimit() {
		return groupNumberLimit;
	}
	public void setGroupNumberLimit(Integer groupNumberLimit) {
		this.groupNumberLimit = groupNumberLimit;
	}
	public Integer getGroupStudentLimit() {
		return groupStudentLimit;
	}
	public void setGroupStudentLimit(Integer groupStudentLimit) {
		this.groupStudentLimit = groupStudentLimit;
	}
	public Seminar getSeminar() {
		return seminar;
	}
	public void setSeminar(Seminar seminar) {
		this.seminar = seminar;
	}
	@Override
	public String toString() {
		return "Topic [id=" + id + ", name=" + name + ", description=" + description + ", groupNumberLimit="
				+ groupNumberLimit + ", groupStudentLimit=" + groupStudentLimit + ", seminar=" + seminar + "]";
	}
	
}
