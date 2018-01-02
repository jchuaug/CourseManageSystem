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
import xmu.crms.exception.TopicNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.GradeMapper;
import xmu.crms.service.GradeService;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.TopicService;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shin-jim
 */
@RestController
public class GroupController {

    @Autowired
    SeminarGroupService seminarGroupService;

    @Autowired
    TopicService topicService;

    @Autowired
    GradeService gradeService;

    @Autowired
    GradeMapper gradeMapper;

    @RequestMapping(value = "/group/{groupID}")
    public ResponseEntity<GroupResponseVO> getGroupWithID(@PathVariable Integer groupID, HttpServletRequest request) {
        GroupResponseVO group = new GroupResponseVO();
        try {
            BigInteger groupId = BigInteger.valueOf(groupID);
            SeminarGroup seminarGroup = seminarGroupService.getSeminarGroupByGroupId(groupId);


            // build member
            List<User> members = seminarGroupService.listSeminarGroupMemberByGroupId(groupId);
            List<UserResponseVO> membersResponse = new ArrayList<>();
            //todo members may include leader
            members.forEach(user -> membersResponse.add(ModelUtils.UserToUserResponseVO(user)));

            // build topics
            List<SeminarGroupTopic> topics = topicService.listSeminarGroupTopicByGroupId(groupId);
            List<TopicResponseVO> topicResponses = new ArrayList<>();
            topics.forEach(topic -> {
                topicResponses.add(TopicResponseVO.simpleTopic(topic.getTopic()));
            });

            group.setId(seminarGroup.getId());
            group.setLeader(ModelUtils.UserToLeader(seminarGroup.getLeader()));
            //todo what's the name
            group.setName(seminarGroup.getId().toString());
            group.setMembers(membersResponse);
            group.setTopics(topicResponses);
            group.setReport(seminarGroup.getReport());
            group.setPresentationGrade(seminarGroup.getPresentationGrade());
            group.setReportGrade(seminarGroup.getReportGrade());
            group.setGrade(seminarGroup.getFinalGrade());

        } catch (GroupNotFoundException e) {
            return new ResponseEntity<GroupResponseVO>(null, null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<GroupResponseVO>(group, null, HttpStatus.OK);
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
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (InvalidOperationException e) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/group/{groupID}/assign")
    public ResponseEntity becomeLeader(@PathVariable Integer groupID, @RequestBody UserRequestVO user) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        try {
            seminarGroupService.assignLeaderById(groupId, BigInteger.valueOf(user.getId()));
        } catch (GroupNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (InvalidOperationException e) {
            return new ResponseEntity(HttpStatus.MULTI_STATUS);
        }
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/group/{groupID}/add")
    public ResponseEntity addMember(@PathVariable Integer groupID, @RequestBody UserResponseVO user) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        try {
            seminarGroupService.insertSeminarGroupMemberById(user.getId(), groupId);
        } catch (GroupNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (UserNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (InvalidOperationException e) {
            return new ResponseEntity(HttpStatus.MULTI_STATUS);
        }
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/group/{groupID}/remove")
    public ResponseEntity removeMember(@PathVariable Integer groupID, @RequestBody UserResponseVO user) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        seminarGroupService.deleteSeminarGroupMemberById(groupId, user.getId());
        return ResponseEntity.ok(HttpStatus.MULTI_STATUS);
    }

    @PostMapping(value = "/group/{groupID}/topic")
    public ResponseEntity chooseTopic(@PathVariable Integer groupID, @RequestBody TopicRequestVO topic) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        try {
            seminarGroupService.insertTopicByGroupId(groupId, topic.getId());
        } catch (GroupNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } catch (TopicNotFoundException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/group/{groupID}/topic/{topicID}")
    public ResponseEntity deleteChoosenTopic(@PathVariable Integer groupID, @PathVariable Integer topicID) {
        BigInteger topicId = BigInteger.valueOf(topicID);
        BigInteger groupId = BigInteger.valueOf(groupID);
        seminarGroupService.deleteTopic(topicId, groupId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/group/{groupID}/grade")
    public ResponseEntity<GradeResponseVO> getGrade(@PathVariable Integer groupID) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        GradeResponseVO responseVO = new GradeResponseVO();

        try {
            SeminarGroup seminarGroup = seminarGroupService.getSeminarGroupByGroupId(groupId);
            responseVO.setGrade(seminarGroup.getFinalGrade());
            responseVO.setReportGrade(seminarGroup.getReportGrade());

            List<SeminarGroupTopic> seminarGroupTopics = gradeMapper.listSeminarGroupTopic(seminarGroup.getId());
            List<PresentationGrade> presentationGrades = new ArrayList<>();

            for (SeminarGroupTopic seminarGroupTopic : seminarGroupTopics) {
                int score = seminarGroupTopic.getSeminarGroup().getPresentationGrade();
                presentationGrades.add(new PresentationGrade(seminarGroupTopic.getTopic().getId(), score));
            }

            responseVO.setPresentationGrade(presentationGrades);
        } catch (GroupNotFoundException ignore) {
        }
        return new ResponseEntity<>(responseVO, null, HttpStatus.OK);
    }

    @PutMapping(value = "/group/{groupID}/grade/report")
    public ResponseEntity setReportGrade(@PathVariable Integer groupID, @RequestBody GradeRequestVO grade) {
        BigInteger groupId = BigInteger.valueOf(groupID);
        try {
            gradeService.updateGroupByGroupId(groupId, BigInteger.valueOf(grade.getReportGrade()));
        } catch (GroupNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/group/{groupID}/grade/presentation/{studentID}")
    public ResponseEntity setGradeOnOthers(@PathVariable Integer groupID, @PathVariable Integer studentID,
                                           @RequestBody GradeRequestVO grade) {

        BigInteger groupId = BigInteger.valueOf(groupID);
        BigInteger studentId = BigInteger.valueOf(studentID);

        grade.getGroups().forEach(groupScore -> {
            gradeService.insertGroupGradeByUserId(studentId, groupScore.getId(), groupScore.getScore());
        });

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
