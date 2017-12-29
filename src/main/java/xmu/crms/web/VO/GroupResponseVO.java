package xmu.crms.web.VO;

import java.math.BigInteger;
import java.util.List;

public class GroupResponseVO {
    private BigInteger id;
    private UserResponseVO leader;
    private String name;
    private List<UserResponseVO> members;
    private List<TopicResponseVO> topics;
    private String report;


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

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public List<TopicResponseVO> getTopics() {
        return topics;
    }

    public void setTopics(List<TopicResponseVO> topics) {
        this.topics = topics;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

}
