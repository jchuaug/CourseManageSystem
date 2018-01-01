package xmu.crms.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xmu.crms.entity.*;
import xmu.crms.exception.*;
import xmu.crms.mapper.SeminarGroupMapper;
import xmu.crms.mapper.SeminarMapper;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.TopicService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @author zw
 * @date 2017/12/20 0020
 * @time 14:19
 * @email 493703217@qq.com
 */
// todo refactor using service instead mapper
@Service
public class SeminarGroupServiceImpl implements SeminarGroupService {
    @Autowired(required = false)
    SeminarGroupMapper seminarGroupMapper;

    @Autowired(required = false)
    TopicService topicService;

    @Autowired(required = false)
    SeminarMapper seminarMapper;

    @Override
    public List<User> listSeminarGroupMemberByGroupId(BigInteger groupId) throws IllegalArgumentException, GroupNotFoundException {
        List<User> users = new ArrayList<User>();
        List<SeminarGroupMember> seminarGroupMembers = seminarGroupMapper.listSeminarGroupMemberByGroupId(groupId);
        for (SeminarGroupMember seminarGroupMember : seminarGroupMembers) {
            users.add(seminarGroupMember.getStudent());
        }
        return users;
    }

    @Override
    public BigInteger insertSeminarGroupMemberByGroupId(BigInteger groupId, SeminarGroupMember seminarGroupMember) {
        return BigInteger.valueOf(seminarGroupMapper.insertSeminarGroupMemberByGroupId(groupId, seminarGroupMember));
    }


    @Override
    public SeminarGroup getSeminarGroupById(BigInteger seminarId, BigInteger userId) throws IllegalArgumentException, GroupNotFoundException {
        BigInteger groupId;
        if ((groupId = seminarGroupMapper.getSeminarGroupIdBySeminarIdAndUserId(seminarId, userId)) == null) {
            throw new GroupNotFoundException();
        }
        return seminarGroupMapper.getSeminarGroupByGroupId(groupId);
    }

    @Override
    public void automaticallyAllotTopic(BigInteger seminarId) throws IllegalArgumentException, SeminarNotFoundException, GroupNotFoundException {
        //获得seminar下所有topic （topic）
        List<Topic> topics = topicService.listTopicBySeminarId(seminarId);
        //获得所有的选了话题的小组
        List<SeminarGroup> seminarGroupsHasTopic = new ArrayList<SeminarGroup>();
        for (Topic topic : topics) {
            List<SeminarGroup> seminarGroupsHasThisTopic = null;
            try {
                seminarGroupsHasThisTopic = listGroupByTopicId(topic.getId());
                System.out.println("Topicid:" + topic.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (SeminarGroup seminarGroup : seminarGroupsHasThisTopic) {
                seminarGroupsHasTopic.add(seminarGroup);
            }
        }
        System.out.println("seminarGroupsHasTopic:" + seminarGroupsHasTopic.size());
        //通过seminarid找到所有的seminargroup （seminar gourp）
        List<SeminarGroup> allSeminarGroups;
        allSeminarGroups = seminarGroupMapper.listSeminarGroupBySeminarId(seminarId);
        //通过seminargroupid找到所有的没有话题的组 （seminargourptopic）
        System.out.println("allSeminarGroups:" + allSeminarGroups.size());
        List<SeminarGroup> seminarGroupsHasNoTopic = new ArrayList<SeminarGroup>();
        for (SeminarGroup seminarGroup : allSeminarGroups) {
            int judge = 0;
            for (SeminarGroup seminarGroup1 : seminarGroupsHasTopic) {

                if (seminarGroup.getId().equals(seminarGroup1.getId())) {
                    judge = 1;
                    break;
                }
            }
            if (judge == 0) {
                seminarGroupsHasNoTopic.add(seminarGroup);
            }

        }
        seminarGroupsHasNoTopic = allSeminarGroups;
        //%2 seminargourptopic
        int j = 0;
        for (int i = 0; i < seminarGroupsHasNoTopic.size(); i++) {

            for (; j < topics.size(); ) {
                if (seminarGroupMapper.listGroupIdByTopicId(topics.get(j).getId()).size() >= topics.get(j).getGroupNumberLimit()) {
                    j = (j + 1) % topics.size();
                } else {
                    seminarGroupMapper.insertSeminarGroupTopicByTopicIdAndSeminarGroupId(topics.get(j).getId(), seminarGroupsHasNoTopic.get(i).getId());
                    j = (j + 1) % topics.size();
                    break;
                }
            }
        }

    }


    @Override
    public void assignLeaderById(BigInteger groupId, BigInteger userId) throws IllegalArgumentException, UserNotFoundException, GroupNotFoundException, InvalidOperationException {
        if (seminarGroupMapper.getSeminarGroupByGroupId(groupId) == null) {
            throw new GroupNotFoundException();
        }
        if (seminarGroupMapper.getUserIdByUserId(userId) == null) {
            throw new UserNotFoundException();
        }
        if (seminarGroupMapper.getSeminarGroupLeaderByGroupId(groupId) != null) {
            throw new InvalidOperationException();
        }

        try {
            seminarGroupMapper.deleteSeminarGroupMemberByuId(groupId, userId);
        } catch (Exception ignored) {

        }
        seminarGroupMapper.assignLeaderById(groupId, userId);
    }

    @Override
    public void automaticallyGrouping(BigInteger seminarId, BigInteger classId) throws IllegalArgumentException, SeminarNotFoundException {
        //get all studentid by seminarid and classid  <attendence>
        List<BigInteger> studentIdList = seminarGroupMapper.listStudentIdBySeminarIdAndClassId(seminarId, classId);
        //find the smallest groupnumber limit in topic
        List<Topic> topics = topicService.listTopicBySeminarId(seminarId);
        System.out.println(topics);
        int smallestlimit = 1000;
        int groupnumberlimit;
        for (Topic topic : topics) {
            groupnumberlimit = topic.getGroupStudentLimit();
            if (groupnumberlimit < smallestlimit) {
                smallestlimit = groupnumberlimit;
            }
        }
        int groupnumber = studentIdList.size() / smallestlimit;
        System.out.println("studentIdList:" + studentIdList.size());
        System.out.println("groupnumber:" + groupnumber);
        System.out.println("smallestlimit:" + smallestlimit);
        if ((studentIdList.size() % smallestlimit) != 0) {
            groupnumber++;
        }
        //add group number in seminar_group
        for (int i = 0; i < groupnumber; i++) {
            SeminarGroup seminarGroup = new SeminarGroup();
            ClassInfo classInfo = new ClassInfo();
            classInfo.setId(classId);
            seminarGroup.setClassInfo(classInfo);
            BigInteger groupId = insertSeminarGroupBySeminarId(seminarId, seminarGroup);
            //add this student  in seminargroup member (set groupid for each student)
//            for (int j = i * smallestlimit; (j < (i+1)*smallestlimit) && ((i * smallestlimit + j) < studentIdList.size()); j++) {
            for (int j = 0; j < smallestlimit && (j + i * smallestlimit) < studentIdList.size(); j++) {
                try {
                    insertSeminarGroupMemberById(studentIdList.get(j + i * smallestlimit), groupId);
                } catch (GroupNotFoundException e) {
                    e.printStackTrace();
                } catch (UserNotFoundException e) {
                    e.printStackTrace();
                } catch (InvalidOperationException e) {
                    e.printStackTrace();
                }
            }
        }
        Seminar seminar = new Seminar();
        seminar = seminarMapper.getSeminarBySeminarId(seminarId);

    }

    @Override
    public void deleteSeminarGroupMemberById(BigInteger groupId, BigInteger userId) {
        seminarGroupMapper.deleteSeminarGroupMemberByuId(groupId, userId);
    }


    @Override
    public void deleteSeminarGroupByGroupId(BigInteger seminarGroupId) throws IllegalArgumentException {
        seminarGroupMapper.deleteSeminarGroupByGroupId(seminarGroupId);
    }

    @Override
    public void deleteSeminarGroupBySeminarId(BigInteger seminarId) throws IllegalArgumentException {
        List<SeminarGroup> seminarGroups = seminarGroupMapper.listSeminarGroupBySeminarId(seminarId);
        for (SeminarGroup seminarGroup : seminarGroups) {
            seminarGroupMapper.deleteSeminarGroupMemberBySeminarGroupId(seminarGroup.getId());
        }
        seminarGroupMapper.deleteSeminarGroupBySeminarId(seminarId);
    }

    @Override
    public BigInteger insertSeminarGroupBySeminarId(BigInteger seminarId, BigInteger classId, SeminarGroup seminarGroup) throws IllegalArgumentException {
        // todo implement
        return null;
    }

    @Override
    public void deleteSeminarGroupMemberBySeminarGroupId(BigInteger seminarGroupId) {
        seminarGroupMapper.deleteSeminarGroupMemberBySeminarGroupId(seminarGroupId);
    }


    @Override
    public SeminarGroup getSeminarGroupByGroupId(BigInteger groupId) throws IllegalArgumentException, GroupNotFoundException {
        if (seminarGroupMapper.getSeminarGroupByGroupId(groupId) == null) {
            throw new GroupNotFoundException();
        }
        return seminarGroupMapper.getSeminarGroupByGroupId(groupId);
    }


    @Override
    public BigInteger getSeminarGroupLeaderById(BigInteger userId, BigInteger seminarId) throws IllegalArgumentException {
        BigInteger groupId;
        groupId = seminarGroupMapper.getSeminarGroupIdBySeminarIdAndUserId(seminarId, userId);
        return seminarGroupMapper.getSeminarGroupLeaderByGroupId(groupId);
    }


    @Override
    public BigInteger getSeminarGroupLeaderByGroupId(BigInteger groupId) throws IllegalArgumentException, GroupNotFoundException {
        if (seminarGroupMapper.getSeminarGroupByGroupId(groupId) == null) {
            throw new GroupNotFoundException();
        }
        return seminarGroupMapper.getSeminarGroupByGroupId(groupId).getLeader().getId();
    }


    //todo delete method
    public BigInteger insertSeminarGroupBySeminarId(BigInteger seminarId, SeminarGroup seminarGroup) throws IllegalArgumentException {
        Seminar seminar = new Seminar();
        seminar.setId(seminarId);
        seminarGroup.setSeminar(seminar);
        seminarGroupMapper.insertSeminarGroupBySeminarId(seminarGroup);
        System.out.println(seminarGroup.getId());
        return seminarGroup.getId();
    }

    @Override
    public List<SeminarGroup> listSeminarGroupBySeminarId(BigInteger seminarId) throws IllegalArgumentException, SeminarNotFoundException {
        if (seminarGroupMapper.getSeminarIdBySeminarId(seminarId) == null) {
            throw new SeminarNotFoundException();
        }
        return seminarGroupMapper.listSeminarGroupBySeminarId(seminarId);
    }


    @Override
    public BigInteger insertSeminarGroupMemberById(BigInteger userId, BigInteger groupId) throws IllegalArgumentException, GroupNotFoundException, UserNotFoundException, InvalidOperationException {
        if (seminarGroupMapper.getSeminarGroupById(groupId) == null) {
            throw new GroupNotFoundException();
        }
        if (seminarGroupMapper.getUserById(userId) == null) {
            throw new UserNotFoundException();
        }
        if (seminarGroupMapper.getSeminarGroupMemberByStudentIdAndSeminarGroupId(userId, groupId) != null) {
            throw new InvalidOperationException();
        }
        seminarGroupMapper.insertSeminarGroupMemberById(userId, groupId);

        //todo return id
        return null;
    }


    @Override
    public List<SeminarGroup> listSeminarGroupIdByStudentId(BigInteger userId) throws IllegalArgumentException {
        List<SeminarGroupMember> seminarGroupMembers = seminarGroupMapper.listSeminarGroupIdByStudentId(userId);
        List<SeminarGroup> seminarGroups = new ArrayList<>();
        for (SeminarGroupMember seminarGroupMember : seminarGroupMembers) {
            seminarGroups.add(seminarGroupMember.getSeminarGroup());
        }
        return seminarGroups;
    }


    /**
     *按seminarId获取SeminarGroup.
     *
     * @param seminarId 讨论课id
     * @return List<SeminarGroup>
     * @author YellowPure
     * @date 22:50 2017/12/22
     */
//      @Override
//      public List<SeminarGroup> listSeminarGroupBySeminarId(BigInteger seminarId) throws IllegalArgumentException, SeminarNotFoundException {
//            if(seminarGroupMapper.getSeminarById(seminarId)==null){
//                  throw new SeminarNotFoundException("未找到讨论课");
//            }
//            return seminarGroupMapper.listSeminarGroupBySeminarId(seminarId);
//      }

    /**
     * 根据话题Id获得选择该话题的所有小组的信息.
     *
     * @param topicId 话题Id
     * @return List<SeminarGroup>
     * @author YellowPure
     * @date 22:51 2017/12/22
     */
    @Override
    public List<SeminarGroup> listGroupByTopicId(BigInteger topicId) throws IllegalArgumentException {
        List<BigInteger> seminarGroupId = new ArrayList<>();
        seminarGroupId = seminarGroupMapper.listGroupIdByTopicId(topicId);
        SeminarGroup seminarGroup = new SeminarGroup();
        List<SeminarGroup> seminarGroups = new ArrayList<>();
        for (int i = 0; i < seminarGroupId.size(); i++) {
            seminarGroup = seminarGroupMapper.getSeminarGroupByGroupId(seminarGroupId.get(i));
            seminarGroups.add(seminarGroup);
        }
        return seminarGroups;
    }


    @Override
    public BigInteger insertTopicByGroupId(BigInteger groupId, BigInteger topicId) throws IllegalArgumentException, GroupNotFoundException, TopicNotFoundException {
        if (seminarGroupMapper.getSeminarGroupById(groupId) == null) {
            throw new GroupNotFoundException();
        }

        SeminarGroup seminarGroup = seminarGroupMapper.getSeminarGroupByGroupId(groupId);
        BigInteger classId = seminarGroup.getClassInfo().getId();
        List<BigInteger> groupIDs = seminarGroupMapper.listGroupIdByTopicId(topicId);

        int groupLimit = topicService.getTopicByTopicId(topicId).getGroupNumberLimit();
        int groupCount = topicService.getSelectedGroupCount(classId, topicId);

        List<SeminarGroupTopic> alreadySelectedTopics = topicService.listSeminarGroupTopicByGroupId(groupId);

        // if already choose this topic
        for (SeminarGroupTopic topic : alreadySelectedTopics) {
            if (Objects.equals(topic.getTopic().getId(), topicId)) {
                throw new TopicNotFoundException();
            }
        }

        // can't choose this
        if (groupLimit <= groupCount) {
            throw new TopicNotFoundException();
        }

        seminarGroupMapper.insertSeminarGroupTopicByTopicIdAndSeminarGroupId(topicId, groupId);
        //todo return id
        return null;
    }


    @Override
    public void resignLeaderById(BigInteger groupId, BigInteger userId) throws IllegalArgumentException, GroupNotFoundException {
        if (seminarGroupMapper.getSeminarGroupById(groupId) == null) {
            throw new GroupNotFoundException();
        }
        try {
            seminarGroupMapper.insertSeminarGroupMemberById(userId, groupId);
        } catch (Exception ignore) {

        }
        seminarGroupMapper.resignLeaderById(groupId, userId);
    }

    @Override
    public void deleteTopic(BigInteger topicId, BigInteger groupId) {
        seminarGroupMapper.deleteTopic(topicId, groupId);
    }
}
