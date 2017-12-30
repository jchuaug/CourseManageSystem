package xmu.crms.web.VO;

import java.math.BigInteger;

public class PresentationGrade {
    private Integer topicId;
    private Integer grade;

    public PresentationGrade(BigInteger id, int score) {
        this.topicId = id.intValue();
        this.grade = score;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
