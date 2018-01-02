package xmu.crms.web.VO;

import java.math.BigInteger;

public class CourseRequestVO {
	private BigInteger id;
	private String name;
	private String description;
	private String startTime;
	private String endTime;

	private Proportion proportions;

	@Override
	public String toString() {
		return "CourseRequestVO [id=" + id + ", name=" + name + ", description=" + description + ", startTime="
				+ startTime + ", endTime=" + endTime + ", proportions=" + proportions + "]";
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

	public Proportion getProportions() {
		return proportions;
	}

	public void setProportions(Proportion proportions) {
		this.proportions = proportions;
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
