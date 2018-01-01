package xmu.crms.web.VO;

import java.math.BigInteger;
import java.util.List;

public class SeminarResponseVO {
	private BigInteger id;
    private	String name;
    private String description;
    private String groupingMethod;
    private String startTime;
    private String endTime;
    private List<TopicResponseVO> topics;
	private List<ClassResponseVO> classes;

    private Integer grade;
    
    
	public Integer getGrade() {
		return grade;
	}
	public void setGrade(Integer grade) {
		this.grade = grade;
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
	public String getGroupingMethod() {
		return groupingMethod;
	}
	public void setGroupingMethod(String groupingMethod) {
		this.groupingMethod = groupingMethod;
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
	public List<TopicResponseVO> getTopics() {
		return topics;
	}
	public void setTopics(List<TopicResponseVO> topics) {
		this.topics = topics;
	}
	@Override
	public String toString() {
		return "SeminarResponseVO [id=" + id + ", name=" + name + ", description=" + description + ", groupingMethod="
				+ groupingMethod + ", startTime=" + startTime + ", endTime=" + endTime + ", topics=" + topics
				+ ", grade=" + grade + "]";
	}
    
	
}
