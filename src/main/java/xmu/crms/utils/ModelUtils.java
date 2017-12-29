package xmu.crms.utils;

import java.lang.reflect.Array;
import java.security.acl.Group;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupMember;
import xmu.crms.entity.SeminarGroupTopic;
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
	public static ClassResponseVO ClassInfoToClassResponseVO(ClassInfo classInfo, User teacher, Integer numStudent) {
		Course course = classInfo.getCourse();
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
		UserResponseVO userResponseVO = new UserResponseVO(user);
		userResponseVO.setId(user.getId());
		userResponseVO.setName(user.getName());
		userResponseVO.setNumber(user.getNumber());
		return userResponseVO;
	}

	public static GroupResponseVO FixGroupToGroupResponseVO(FixGroup fixGroup, List<FixGroupMember> students) {
		GroupResponseVO groupResponseVO = new GroupResponseVO();
		groupResponseVO.setLeader(UserToUserResponseVO(fixGroup.getLeader()));
		List<UserResponseVO> studentVOs = new ArrayList<>();
		for (FixGroupMember fixGroupMember : students) {
			User student = fixGroupMember.getStudent();
			if (!(student.getId().equals(fixGroup.getLeader().getId()))) {
				studentVOs.add(ModelUtils.UserToUserResponseVO(student));
			}
		}
		groupResponseVO.setMembers(studentVOs);
		return groupResponseVO;
	}

	public static SeminarResponseVO SeminarInfoToSeminarResponseVO(Seminar seminar, List<Topic> topics) {
		SeminarResponseVO responseVO = new SeminarResponseVO();
		responseVO.setId(seminar.getId());
		responseVO.setName(seminar.getName());
		responseVO.setDescription(seminar.getDescription());
		responseVO.setGroupingMethod(seminar.getFixed() == true ? "fixed" : "random");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(seminar.getStartTime());
		responseVO.setStartTime(time);
		time = sdf.format(seminar.getEndTime());
		responseVO.setEndTime(time);
		List<TopicResponseVO> topicResponseVOs = new ArrayList<>();
		for (Topic topic : topics) {
			TopicResponseVO topicResponseVO = TopicToTopicResponseVO(topic, null);
			topicResponseVOs.add(topicResponseVO);
		}
		responseVO.setTopics(topicResponseVOs);
		return responseVO;
	}

	public static TopicResponseVO TopicToTopicResponseVO(Topic topic, Integer groupLeft) {
		TopicResponseVO topicResponseVO = new TopicResponseVO();
		topicResponseVO.setId(topic.getId());
		topicResponseVO.setName(topic.getName());
		topicResponseVO.setDescription(topic.getDescription());
		topicResponseVO.setGroupMemberLimit(topic.getGroupStudentLimit());
		topicResponseVO.setGroupLimit(topic.getGroupNumberLimit());
		topicResponseVO.setGroupLeft(groupLeft);
		return topicResponseVO;
	}

	public static Seminar SeminarResponseVOToSeminar(SeminarResponseVO seminar, Seminar forSeminar) {
		Seminar seminar2 = new Seminar();
		seminar2.setId(seminar.getId());
		seminar2.setName(seminar.getName());
		seminar2.setDescription(seminar.getDescription());
		Boolean fixed = null;
		if (seminar.getGroupingMethod() == "fixed") {
			fixed = true;
		} else if (seminar.getGroupingMethod() == "random") {
			fixed = false;
		}
		seminar2.setFixed(fixed);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dstr = seminar.getStartTime();
		try {
			java.util.Date date = sdf.parse(dstr);
			seminar2.setStartTime(date);
			dstr = seminar.getEndTime();
			date = sdf.parse(dstr);
			seminar2.setEndTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		seminar2.setCourse(forSeminar.getCourse());
		seminar2.setFixed(forSeminar.getFixed());
		return seminar2;
	}

	public static MySeminarResponseVO SeminarToMySeminarResponseVO(Seminar seminar, Boolean isLeader,
			Boolean areTopicsSeletced) {
		MySeminarResponseVO mySeminarResponseVO = new MySeminarResponseVO();
		mySeminarResponseVO.setId(seminar.getId());
		mySeminarResponseVO.setName(seminar.getName());
		String groupingMethod = null;
		if (seminar.getFixed() == null) {
			groupingMethod = null;
		} else if (seminar.getFixed() == true) {
			groupingMethod = "fixed";
		} else if (seminar.getFixed() == false) {
			groupingMethod = "random";
		}
		mySeminarResponseVO.setGroupingMethod(groupingMethod);
		mySeminarResponseVO.setCourseName(seminar.getCourse().getName());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(seminar.getStartTime());
		mySeminarResponseVO.setStartTime(time);
		time = sdf.format(seminar.getEndTime());
		mySeminarResponseVO.setEndTime(time);
		mySeminarResponseVO.setIsLeader(isLeader);
		mySeminarResponseVO.setAreTopicsSeletced(areTopicsSeletced);
		return mySeminarResponseVO;
	}

	public static SeminarDetailResponseVO SeminarToSeminarDetailResponseVO(Seminar seminar) {
		User teacher = seminar.getCourse().getTeacher();
		SeminarDetailResponseVO seminarDetailResponseVO = new SeminarDetailResponseVO();
		seminarDetailResponseVO.setId(seminar.getId());
		seminarDetailResponseVO.setName(seminar.getName());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time = sdf.format(seminar.getStartTime());
		seminarDetailResponseVO.setStartTime(time);
		time = sdf.format(seminar.getEndTime());
		seminarDetailResponseVO.setEndTime(time);
		seminarDetailResponseVO.setTeacherName(teacher.getName());
		seminarDetailResponseVO.setTeacherEmail(teacher.getEmail());
		return seminarDetailResponseVO;
	}

	public static Topic TopicResponseVOToTopic(TopicResponseVO topic) {
		Topic topic2 = new Topic();
		topic2.setName(topic.getName());
		topic2.setDescription(topic.getDescription());
		topic2.setGroupNumberLimit(topic.getGroupLimit());
		topic2.setGroupStudentLimit(topic.getGroupMemberLimit());
		topic2.setSerial(topic.getSerial());
		return topic2;
	}

	public static GroupResponseVO SeminarGroupToGroupResponseVO(SeminarGroup seminarGroup,
			List<SeminarGroupTopic> topics, List<User> seminarGroupMembers) {
		GroupResponseVO groupResponseVO = new GroupResponseVO();
		groupResponseVO.setLeader(ModelUtils.UserToUserResponseVO(seminarGroup.getLeader()));
		List<UserResponseVO> members = new ArrayList<>();
		if (seminarGroupMembers != null) {
			for (User seminarGroupMember : seminarGroupMembers) {
				members.add(UserToUserResponseVO(seminarGroupMember));
			}
		}
		groupResponseVO.setMembers(members);
		groupResponseVO.setId(seminarGroup.getId());
		groupResponseVO.setName(seminarGroup.getId().toString());
		List<TopicResponseVO> topicResponseVOs = new ArrayList<>();
		for (SeminarGroupTopic seminarGroupTopic : topics) {
			topicResponseVOs.add(TopicToTopicResponseVO(seminarGroupTopic.getTopic(), null));
		}
		groupResponseVO.setTopics(topicResponseVOs);
		return groupResponseVO;
	}
}
