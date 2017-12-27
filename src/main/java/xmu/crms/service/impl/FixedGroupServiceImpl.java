package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.Location;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.FixGroupNotFoundException;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.ClassInfoMapper;
import xmu.crms.mapper.FixedGroupMapper;
import xmu.crms.service.ClassService;
import xmu.crms.service.FixGroupService;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.SeminarService;
import xmu.crms.service.UserService;

/**
 * 
 * @author yjj
 * @version 2.00
 *
 */
@Service
public class FixedGroupServiceImpl implements FixGroupService {

	@Autowired
	private FixedGroupMapper fixedGroupMapper;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassService classService;
	
	@Autowired
	private SeminarService seminarService;
	
	@Autowired
	private SeminarGroupService seminarGroupService;

	@Override
	public BigInteger insertFixGroupByClassId(BigInteger classId, BigInteger userId)
			throws IllegalArgumentException, UserNotFoundException {
		if (classId == null | userId == null) {
			throw new IllegalArgumentException();
		}
		FixGroup fixGroup = new FixGroup();
		User student = userService.getUserByUserId(userId);
		if (student == null) {
			throw new UserNotFoundException();
		}
		ClassInfo classInfo = new ClassInfo();
		classInfo.setId(classId);
		fixGroup.setLeader(student);
		fixGroup.setClassInfo(classInfo);

		int flag = fixedGroupMapper.insertFixGroup(fixGroup);
		return fixGroup.getId();
	}

	@Override
	public void deleteFixGroupMemberByFixGroupId(BigInteger fixGroupId)
			throws IllegalArgumentException, FixGroupNotFoundException {
		if (fixGroupId == null) {
			throw new IllegalArgumentException();
		}
		int flag = fixedGroupMapper.deleteFixGroupMemberByFixGroupId(fixGroupId);
		if (flag == 0) {
			throw new FixGroupNotFoundException();
		}
	}

	@Override
	public void deleteFixGroupUserById(BigInteger fixGroupId, BigInteger userId)
			throws IllegalArgumentException, FixGroupNotFoundException, UserNotFoundException {
		if (fixGroupId == null | userId == null) {
			throw new IllegalArgumentException();
		}
		if (userService.getUserByUserId(userId) == null) {
			throw new UserNotFoundException();
		}
		int flag = fixedGroupMapper.deleteFixGroupUserById(fixGroupId, userId);
		if (flag == 0) {
			throw new FixGroupNotFoundException();
		}
	}

	@Override
	public List<User> listFixGroupMemberByGroupId(BigInteger groupId)
			throws IllegalArgumentException, FixGroupNotFoundException {
		if (groupId == null) {
			throw new IllegalArgumentException();
		}
		FixGroup fixGroup= fixedGroupMapper.getFixGroupByFixGroupId(groupId);
		if (fixGroup==null) {
			throw new FixGroupNotFoundException();
		}
		List<User> members=fixedGroupMapper.listFixGroupMemberByGroupId( groupId);
		
		return members;
	}

	@Override
	public List<FixGroup> listFixGroupByClassId(BigInteger classId)
			throws IllegalArgumentException, ClassesNotFoundException {
		if (classId == null) {
			throw new IllegalArgumentException();
		}
		List<FixGroup> fixGroups=fixedGroupMapper.listFixGroupByClassId( classId);
		return fixGroups;
	}

	@Override
	public void deleteFixGroupByClassId(BigInteger classId) throws IllegalArgumentException, ClassesNotFoundException {
		if (classId == null) {
			throw new IllegalArgumentException();
		}
		List< FixGroup> fixGroups=listFixGroupByClassId(classId);
		for (FixGroup fixGroup : fixGroups) {
			try {
				deleteFixGroupMemberByFixGroupId(fixGroup.getId());
			} catch (FixGroupNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int flag=fixedGroupMapper.deleteFixGroupByClassId( classId);
	}

	@Override
	public void deleteFixGroupByGroupId(BigInteger groupId) throws IllegalArgumentException, FixGroupNotFoundException {
		if (groupId == null) {
			throw new IllegalArgumentException();
		}
		deleteFixGroupMemberByFixGroupId(groupId);
		int flag= fixedGroupMapper.deleteFixGroupByGroupId(groupId);
	}

	@Override
	public void updateFixGroupByGroupId(BigInteger groupId, FixGroup fixGroupBO)
			throws IllegalArgumentException, FixGroupNotFoundException {
		if (groupId == null|fixGroupBO==null) {
			throw new IllegalArgumentException();
		}
		if (fixedGroupMapper.getFixGroupByFixGroupId(groupId)==null) {
			throw new FixGroupNotFoundException();
		}
		fixGroupBO.setId(groupId);
		int flag=fixedGroupMapper.updateFixGroupByGroupId(fixGroupBO);
		
	}

	@Override
	public List<FixGroupMember> listFixGroupByGroupId(BigInteger groupId)
			throws IllegalArgumentException, FixGroupNotFoundException {
		if (groupId == null) {
			throw new IllegalArgumentException();
		}
		List<FixGroupMember> members=fixedGroupMapper.listFixGroupByGroupId(groupId);
		if (members.isEmpty()) {
			return null;
		}else {
			return members;
		}
		
	}

	@Override
	public BigInteger insertStudentIntoGroup(BigInteger userId, BigInteger groupId) throws IllegalArgumentException,
			FixGroupNotFoundException, UserNotFoundException, InvalidOperationException {
		if (userId == null|groupId==null) {
			throw new IllegalArgumentException();
		}
		User student= userService.getUserByUserId(userId);
		FixGroup fixGroup=fixedGroupMapper.getFixGroupByFixGroupId(groupId);
		if (student==null) {
			throw new UserNotFoundException();
		}
		FixGroupMember fixGroupMemberTest =fixedGroupMapper.getFixGroupMemberById(userId,groupId);
		if (fixGroupMemberTest!=null) {
			throw new InvalidOperationException();
		}
		FixGroupMember fixGroupMember=new FixGroupMember();
		fixGroupMember.setStudent(student);
		fixGroupMember.setFixGroup(fixGroup);
		int flag=fixedGroupMapper.insertFixGroupMember(fixGroupMember);
		return fixGroupMember.getId();
	}

	@Override
	public FixGroup getFixedGroupById(BigInteger userId, BigInteger classId)
			throws IllegalArgumentException, ClassesNotFoundException, UserNotFoundException {
		if (userId == null|classId==null) {
			throw new IllegalArgumentException();
		}
		if (userService.getUserByUserId(userId)==null) {
			throw new UserNotFoundException();
		}
		if (classService.getClassByClassId(classId)==null) {
			throw new ClassesNotFoundException();
		}
		FixGroup fixGroup=fixedGroupMapper.getFixGroupById(userId,classId);
		return fixGroup;
	}

	@Override
	public void fixedGroupToSeminarGroup(BigInteger seminarId, BigInteger fixedGroupId)
			throws IllegalArgumentException, FixGroupNotFoundException, SeminarNotFoundException {
		if (seminarId == null|fixedGroupId==null) {
			throw new IllegalArgumentException();
		}
		FixGroup fixGroup=fixedGroupMapper.getFixGroupByFixGroupId(fixedGroupId);
		if (fixGroup==null) {
			throw new FixGroupNotFoundException();
		}
		if (seminarService.getSeminarBySeminarId(seminarId)==null) {
			throw new SeminarNotFoundException();
		}
		SeminarGroup seminarGroup=new SeminarGroup();
		seminarGroup.setLeader(fixGroup.getLeader());
		seminarGroup.setClassInfo(fixGroup.getClassInfo());
		BigInteger seminarGroupId= seminarGroupService.insertSeminarGroupBySeminarId(seminarId, fixGroup.getClassInfo().getId(), seminarGroup);
		List<FixGroupMember> members=listFixGroupByGroupId(fixedGroupId);
		for (FixGroupMember fixGroupMember : members) {
			try {
				seminarGroupService.insertSeminarGroupMemberById(fixGroupMember.getStudent().getId(), seminarGroupId);
			} catch (GroupNotFoundException | UserNotFoundException | InvalidOperationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
