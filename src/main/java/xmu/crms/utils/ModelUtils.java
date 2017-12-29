package xmu.crms.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.Topic;
import xmu.crms.entity.User;
import xmu.crms.web.VO.ClassRequestVO;
import xmu.crms.web.VO.ClassResponseVO;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.MySeminarResponseVO;
import xmu.crms.web.VO.Proportion;
import xmu.crms.web.VO.SeminarDetailResponseVO;
import xmu.crms.web.VO.SeminarResponseVO;
import xmu.crms.web.VO.TopicResponseVO;
import xmu.crms.web.VO.UserResponseVO;

public class ModelUtils {
	public static ClassResponseVO ClassInfoToClassResponseVO(ClassInfo classInfo, User teacher,
			Integer numStudent) {
		Course course=classInfo.getCourse();
		ClassResponseVO classVO = new ClassResponseVO(classInfo.getId(), classInfo.getName(), numStudent,
				classInfo.getClassTime(), classInfo.getSite(), course.getName(), teacher.getName());
		return classVO;

	}

	public static ClassInfo ClassRequestVOToClassInfo(ClassRequestVO classRequestVO) {
		Proportion proportion = classRequestVO.getProportions();
		ClassInfo classInfo = new ClassInfo(classRequestVO.getId(), classRequestVO.getName(), null,
				classRequestVO.getSite(), classRequestVO.getTime(), null, proportion.getReport(),
				proportion.getPresentation(), proportion.getA(), proportion.getB(), proportion.getC());
		return classInfo;
	}
	
	public static UserResponseVO UserToUserResponseVO(User user) {
		UserResponseVO userResponseVO=new UserResponseVO();
		userResponseVO.setId(user.getId());
		userResponseVO.setName(user.getName());
		userResponseVO.setNumber(user.getNumber());
		return userResponseVO;
	}

	public static GroupResponseVO FixGroupToGroupResponseVO(FixGroup fixGroup, List<FixGroupMember> students) {
		GroupResponseVO groupResponseVO=new GroupResponseVO();
		groupResponseVO.setLeader(UserToUserResponseVO(fixGroup.getLeader()));
		List<UserResponseVO> studentVOs=new ArrayList<>();
		for (FixGroupMember fixGroupMember : students) {
			User student=fixGroupMember.getStudent();
			if (!(student.getId().equals(fixGroup.getLeader().getId()))) {
				studentVOs.add(ModelUtils.UserToUserResponseVO(student));
			}
		}
		UserResponseVO[] members=(UserResponseVO[])studentVOs.toArray(new UserResponseVO[studentVOs.size()]) ;
		groupResponseVO.setMembers(members);
		return groupResponseVO;
	}
	
	public static SeminarResponseVO SeminarInfoToSeminarResponseVO(Seminar seminar,List<Topic> topics) {
		SeminarResponseVO responseVO=new SeminarResponseVO();
		responseVO.setId(seminar.getId());
		responseVO.setName(seminar.getName());
		responseVO.setDescription(seminar.getDescription());
		responseVO.setGroupingMethod(seminar.getFixed()==true?"fixed":"random");
		responseVO.setStartTime(seminar.getStartTime().toString());
		responseVO.setEndTime(seminar.getEndTime().toString());
		List<TopicResponseVO> topicResponseVOs=new ArrayList<>();
		for (Topic topic : topics) {
			TopicResponseVO topicResponseVO=TopicToTopicResponseVO(topic);
			topicResponseVOs.add(topicResponseVO);
		}
		responseVO.setTopics(topicResponseVOs);
		return responseVO;
	}
	public static TopicResponseVO TopicToTopicResponseVO(Topic topic) {
		TopicResponseVO topicResponseVO=new TopicResponseVO();
		topicResponseVO.setId(topic.getId());
		topicResponseVO.setName(topic.getName());
		return topicResponseVO;
	}
	public static Seminar SeminarResponseVOToSeminar(SeminarResponseVO seminar) {
		Seminar seminar2=new Seminar();
		seminar2.setId(seminar.getId());
		seminar2.setName(seminar.getName());
		seminar2.setDescription(seminar.getDescription());
		Boolean fixed=null;
		if (seminar.getGroupingMethod()=="fixed") {
			fixed=true;
		}else if (seminar.getGroupingMethod()=="random") {
			fixed=false;
		}
		seminar2.setFixed(fixed);
		seminar2.setStartTime(new Date(seminar.getStartTime()));
		seminar2.setEndTime(new Date(seminar.getEndTime()));
		return seminar2;
	}

	public static MySeminarResponseVO SeminarToMySeminarResponseVO(Seminar seminar,Boolean isLeader,Boolean areTopicsSeletced) {
		MySeminarResponseVO mySeminarResponseVO=new MySeminarResponseVO();
		mySeminarResponseVO.setId(seminar.getId());
		mySeminarResponseVO.setName(seminar.getName());
		String groupingMethod = null;
		if (seminar.getFixed()==null) {
			groupingMethod=null;
		}else if (seminar.getFixed()==true) {
			groupingMethod="fixed";
		}else if (seminar.getFixed()==false) {
			groupingMethod="random";
		}
		mySeminarResponseVO.setGroupingMethod(groupingMethod);
		mySeminarResponseVO.setCourseName(seminar.getCourse().getName());
		mySeminarResponseVO.setStartTime(seminar.getStartTime().toString());
		mySeminarResponseVO.setEndTime(seminar.getEndTime().toString());
		mySeminarResponseVO.setIsLeader(isLeader);
		mySeminarResponseVO.setAreTopicsSeletced(areTopicsSeletced);
		return mySeminarResponseVO;
	}

	public static SeminarDetailResponseVO SeminarToSeminarDetailResponseVO(Seminar seminar, User teacher) {
		SeminarDetailResponseVO seminarDetailResponseVO=new SeminarDetailResponseVO();
		seminarDetailResponseVO.setId(seminar.getId());
		seminarDetailResponseVO.setName(seminar.getName());
		seminarDetailResponseVO.setStartTime(seminar.getStartTime().toString());
		seminarDetailResponseVO.setEndTime(seminar.getEndTime().toString());
		return null;
	}
}
