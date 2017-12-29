package xmu.crms.web.VO;

import java.math.BigInteger;
import java.util.List;

public class GroupResponseVO {
	private BigInteger id;
	private String name;
	private UserResponseVO leader;
	private List<UserResponseVO> members;
	private List<TopicResponseVO> topics;

	public List<TopicResponseVO> getTopics() {
		return topics;
	}

	public void setTopics(List<TopicResponseVO> topics) {
		this.topics = topics;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	

	public UserResponseVO getLeader() {
		return leader;
	}

	public void setLeader(UserResponseVO leader) {
		this.leader = leader;
	}

	public List<UserResponseVO> getMembers() {
		return members;
	}

	public void setMembers(List<UserResponseVO> members) {
		this.members = members;
	}

	

}
