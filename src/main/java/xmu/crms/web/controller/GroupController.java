package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.entity.User;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.TopicService;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.TopicResponseVO;
import xmu.crms.web.VO.UserRequestVO;
import xmu.crms.web.VO.UserResponseVO;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
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


            // build member
            List<User> members = seminarGroupService.listSeminarGroupMemberByGroupId(groupId);
            List<UserResponseVO> membersResponse = new ArrayList<>();
            //todo members may include leader
            members.forEach(user -> {
                membersResponse.add(new UserResponseVO(user));
            });

            // build topics
            List<SeminarGroupTopic> topics = topicService.listSeminarGroupTopicByGroupId(groupId);
            List<TopicResponseVO> topicResponses = new ArrayList<>();
            topics.forEach(topic -> {
                topicResponses.add(TopicResponseVO.simpleTopic(topic.getTopic()));
            });

            group.setId(seminarGroup.getId());
            group.setLeader(new UserResponseVO(seminarGroup.getLeader()));
            //todo what's the name
            group.setName(seminarGroup.getId().toString());
            group.setMembers(membersResponse);
            group.setTopics(topicResponses);
            group.setReport(seminarGroup.getReport());

        } catch (GroupNotFoundException e) {
            e.printStackTrace();
        }
        return group;
    }

    /**
     * leader resign
     *
     * @param groupID group id
     * @param user    leader
     */
    @PutMapping(value = "/group/{groupID}/resign")
    public ResponseEntity leaderResign(@PathVariable Integer groupID, @RequestBody UserRequestVO user) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        try {
            SeminarGroup seminarGroup = seminarGroupService.getSeminarGroupByGroupId(groupId);
            seminarGroup.setLeader(null);
            seminarGroupService.resignLeaderById(groupId, BigInteger.valueOf(user.getId()));
        } catch (GroupNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.ok(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidOperationException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

//    @PostMapping(value = "/group/{groupID}/topic")
//    public void chooseToopic(@PathVariable Integer groupID, @RequestBody Topic topic) {
//        boolean success = MockDb.chooseTopic(groupID, topic);
//    }
}
