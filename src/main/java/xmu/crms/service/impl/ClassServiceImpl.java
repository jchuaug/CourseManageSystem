package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.Location;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.ClassInfoMapper;
import xmu.crms.service.ClassService;

/**
 * 
 * @author yjj
 * @version 2.00
 *
 */
@Service
public class ClassServiceImpl implements ClassService {
	@Autowired
	private ClassInfoMapper classInfoMapper;

	/*
	 * @Autowired private FixGroupService FixGroupService;
	 */

	@Override
	public void deleteClassSelectionByClassId(BigInteger classId) {
		int flag = classInfoMapper.deleteClassSelectionByClassId(classId);
	}

	
	private List<Course> listCourseByTeacherNameAndCourseName(String teacherName, String courseName)
			throws UserNotFoundException, CourseNotFoundException {
		List<Course> courseList = new ArrayList<>();
		List<User> list = classInfoMapper.listTeacherByTeacherName(teacherName);
		if (list == null) {
			throw new UserNotFoundException();
		}
		for (User userInfo : list) {
			List<Course> courses = classInfoMapper.listCourseByCourseNameAndTeacherId(courseName, userInfo.getId());
			courseList.addAll(courses);
		}
		if (courseList.isEmpty()) {
			throw new CourseNotFoundException();
		}
		return courseList;
	}

	@Override
	public List<ClassInfo> listClassByCourseId(BigInteger courseId) throws CourseNotFoundException {
		if (classInfoMapper.getCourseById(courseId) == null) {
			throw new CourseNotFoundException();
		}
		List<ClassInfo> classList = classInfoMapper.listClassByCourseId(courseId);
		return classList;
	}

	@Override
	public ClassInfo getClassByClassId(BigInteger classId) throws ClassesNotFoundException {
		ClassInfo classInfo = classInfoMapper.selectByPrimaryKey(classId);
		if (classInfo == null) {
			throw new ClassesNotFoundException();
		}
		return classInfo;
	}

	@Override
	public void updateClassByClassId(BigInteger classId, ClassInfo classInfo) throws ClassesNotFoundException {
		ClassInfo classFound = classInfoMapper.selectByPrimaryKey(classId);
		if (classFound == null) {
			throw new ClassesNotFoundException();
		}
		classInfo.setId(classId);
		int flag = classInfoMapper.updateByPrimaryKeySelective(classInfo);
	}

	@Override
	public void deleteClassByClassId(BigInteger classId) throws ClassesNotFoundException {
		ClassInfo classFound = classInfoMapper.selectByPrimaryKey(classId);
		if (classFound == null) {
			throw new ClassesNotFoundException();
		}
		deleteClassSelectionByClassId(classId);
		// FixGroupService.deleteFixGroupByClassId( classId);
		// SeminarGroupService.deleteSeminarGroupByClaaId(classId);
		int flag = classInfoMapper.deleteByPrimaryKey(classId);
		if (flag == 0) {
			throw new ClassesNotFoundException();
		}
	}

	@Override
	public BigInteger insertCourseSelectionById(BigInteger userId, BigInteger classId)
			throws UserNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.selectByPrimaryKey(classId) != null) {
			throw new ClassesNotFoundException();
		}
		if (classInfoMapper.getStudentById(userId) == null) {
			throw new UserNotFoundException();
		}
		int flag = classInfoMapper.insertCourseSelectionById(userId, classId);
		if (flag == 0) {
			return null;
		}

		return classInfoMapper.getCourseSelectionId(userId, classId);
	}

	@Override
	public void deleteCourseSelectionById(BigInteger userId, BigInteger classId)
			throws UserNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.getStudentById(userId) == null) {
			throw new UserNotFoundException();
		}
		if (classInfoMapper.selectByPrimaryKey(classId) == null) {
			throw new ClassesNotFoundException();
		}
		int flag = classInfoMapper.deleteCourseSelectionById(userId, classId);
	}

	@Override
	public Location getCallStatusById(BigInteger classId, BigInteger seminarId) throws SeminarNotFoundException {
		Location location = classInfoMapper.getCallStatusById(classId, seminarId);
		if (location == null) {
			throw new SeminarNotFoundException();
		}
		return location;
	}

	@Override
	public BigInteger insertClassById(BigInteger courseId, ClassInfo classInfo) throws CourseNotFoundException {
		Course course = classInfoMapper.getCourseById(courseId);
		if (course == null) {
			throw new CourseNotFoundException();
		}
		if (classInfo.getCourse() == null) {
			classInfo.setCourse(course);
		}
		int flag = classInfoMapper.insertSelective(classInfo);
		return classInfo.getId();
	}

	@Override
	public void deleteClassByCourseId(BigInteger courseId) throws CourseNotFoundException {
		Course course = classInfoMapper.getCourseById(courseId);
		if (course == null) {
			throw new CourseNotFoundException();
		}
		List<ClassInfo> classInfos = listClassByCourseId(courseId);
		for (ClassInfo classInfo : classInfos) {
			deleteClassSelectionByClassId(classInfo.getId());
			// FixGroupService.deleteFixGroupByClassId(classInfo.getId());
		}
		int flag = classInfoMapper.deleteClassByCourseId(courseId);
	}

	@Override
	public BigInteger callInRollById(Location location) throws SeminarNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.selectByPrimaryKey(location.getClassInfo().getId()) == null) {
			throw new ClassesNotFoundException();
		}
		int flag = classInfoMapper.insetLocation(location);
		return location.getId();
	}

	@Override
	public void endCallRollById(BigInteger seminarId,BigInteger classId) throws SeminarNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.selectByPrimaryKey(seminarId) == null) {
			throw new ClassesNotFoundException();
		}
		int flag = classInfoMapper.endCallRollLocation(seminarId,classId);

	}

	@Override
	public List<ClassInfo> listClassByUserId(BigInteger userId)
			throws IllegalArgumentException, ClassesNotFoundException {
		List<ClassInfo> list = classInfoMapper.listClassByUserId(userId);
		if (list == null) {
			throw new ClassesNotFoundException();
		}
		return list;
	}

}
