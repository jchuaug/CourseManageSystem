package xmu.crms.web.VO;

import java.math.BigInteger;

public class TopicResponseVO {
	private BigInteger id;
	private String name;
	private String serial;
	private String description;
	private Integer groupMemberLimit;
	private Integer groupLimit;
	private Integer groupLeft;

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Integer getGroupLeft() {
		return groupLeft;
	}

	public void setGroupLeft(Integer groupLeft) {
		this.groupLeft = groupLeft;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public Integer getGroupMemberLimit() {
		return groupMemberLimit;
	}

	public void setGroupMemberLimit(Integer groupMemberLimit) {
		this.groupMemberLimit = groupMemberLimit;
	}

	public Integer getGroupLimit() {
		return groupLimit;
	}

	public void setGroupLimit(Integer groupLimit) {
		this.groupLimit = groupLimit;
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

}
