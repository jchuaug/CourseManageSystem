package xmu.crms.web.VO;

import java.math.BigInteger;

public class MySeminarResponseVO {
	private BigInteger id;
    private	String name;
    private String groupingMethod;
    private String courseName;
    
    private String startTime;
    private String endTime;
    private Integer classCalling;
    private Boolean isLeader;
    private Boolean areTopicsSeletced;
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
	public String getGroupingMethod() {
		return groupingMethod;
	}
	public void setGroupingMethod(String groupingMethod) {
		this.groupingMethod = groupingMethod;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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
	public Integer getClassCalling() {
		return classCalling;
	}
	public void setClassCalling(Integer classCalling) {
		this.classCalling = classCalling;
	}
	public Boolean getIsLeader() {
		return isLeader;
	}
	public void setIsLeader(Boolean isLeader) {
		this.isLeader = isLeader;
	}
	public Boolean getAreTopicsSeletced() {
		return areTopicsSeletced;
	}
	public void setAreTopicsSeletced(Boolean areTopicsSeletced) {
		this.areTopicsSeletced = areTopicsSeletced;
	}
    
}
