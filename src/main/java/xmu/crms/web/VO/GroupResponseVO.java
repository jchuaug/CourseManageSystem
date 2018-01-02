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
    
    private Integer presentationGrade;
    private Integer reportGrade;
    private Integer grade;


    public Integer getPresentationGrade() {
		return presentationGrade;
	}

	public void setPresentationGrade(Integer presentationGrade) {
		this.presentationGrade = presentationGrade;
	}

	public Integer getReportGrade() {
		return reportGrade;
	}

	public void setReportGrade(Integer reportGrade) {
		this.reportGrade = reportGrade;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
