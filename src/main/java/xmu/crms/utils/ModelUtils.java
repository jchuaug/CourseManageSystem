package xmu.crms.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.User;
import xmu.crms.web.VO.ClassRequestVO;
import xmu.crms.web.VO.ClassResponseVO;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.Proportion;
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
}
