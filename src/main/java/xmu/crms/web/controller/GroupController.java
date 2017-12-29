package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.entity.Topic;
import xmu.crms.entity.User;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.TopicService;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.UserResponseVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GroupController {

    @Autowired
    SeminarGroupService seminarGroupService;

    @Autowired
    TopicService topicService;


    @RequestMapping(value = "/group/{groupID}")
    public GroupResponseVO getGroupWithID(@PathVariable Integer groupID, HttpServletRequest request) {
        GroupResponseVO group = new GroupResponseVO();
        try {
            BigInteger groupId = BigInteger.valueOf(groupID);
            SeminarGroup seminarGroup = seminarGroupService.getSeminarGroupByGroupId(groupId);

            group.setId(seminarGroup.getId().intValue());
            group.setLeader(new UserResponseVO(seminarGroup.getLeader()));

            // build member
            List<User> members = seminarGroupService.listSeminarGroupMemberByGroupId(groupId);
            List<UserResponseVO> membersResponse = new ArrayList<>();
            members.forEach(user -> {
                membersResponse.add(new UserResponseVO(user));
            });
            group.setMembers(membersResponse);

            // build topics
            List<SeminarGroupTopic> topics = topicService.listSeminarGroupTopicByGroupId(groupId);


        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
        return group;
    }

    @PutMapping(value = "/group/{groupID}")
    public void modifyGroup(@PathVariable Integer groupID, @RequestBody Group group) {
        boolean success = MockDb.modifyGroup(groupID, group);
    }

    @PostMapping(value = "/group/{groupID}/topic")
    public void chooseToopic(@PathVariable Integer groupID, @RequestBody Topic topic) {
        boolean success = MockDb.chooseTopic(groupID, topic);
    }
}