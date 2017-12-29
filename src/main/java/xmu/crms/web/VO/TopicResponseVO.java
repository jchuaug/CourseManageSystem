package xmu.crms.web.VO;

import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.entity.Topic;

import java.math.BigInteger;

public class TopicResponseVO {
    private BigInteger id;
    private String name;
    private String serial;
    private String description;
    private Integer groupMemberLimit;
    private Integer groupLimit;
    private Integer groupLeft;

    public TopicResponseVO() {
    }

    /**
     * request url /group/{groupId}
     */
    public static TopicResponseVO simpleTopic(Topic topic) {
        TopicResponseVO responseVO = new TopicResponseVO();
        responseVO.setDescription(topic.getDescription());
        responseVO.setName(topic.getName());
        responseVO.setId(topic.getId());
        return responseVO;
    }

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
