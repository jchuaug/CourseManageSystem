package xmu.crms.web.VO;

public class GroupResponseVO {
	private UserResponseVO leader;
	private UserResponseVO[] members;
	public UserResponseVO getLeader() {
		return leader;
	}
	public void setLeader(UserResponseVO leader) {
		this.leader = leader;
	}
	public UserResponseVO[] getMembers() {
		return members;
	}
	public void setMembers(UserResponseVO[] members) {
		this.members = members;
	}
	
}
