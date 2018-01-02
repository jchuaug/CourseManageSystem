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
public class SeminarGroupTopic {
	private BigInteger id;
	private Topic topic;
	private SeminarGroup seminarGroup;
	private Integer presentationGrade;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public SeminarGroup getSeminarGroup() {
		return seminarGroup;
	}
	public void setSeminarGroup(SeminarGroup seminarGroup) {
		this.seminarGroup = seminarGroup;
	}
	public Integer getPresentationGrade() {
		return presentationGrade;
	}
	public void setPresentationGrade(Integer presentationGrade) {
		this.presentationGrade = presentationGrade;
	}
	@Override
	public String toString() {
		return "SeminarGroupTopic [id=" + id + ", topic=" + topic + ", seminarGroup=" + seminarGroup
				+ ", presentationGrade=" + presentationGrade + "]";
	}

}
