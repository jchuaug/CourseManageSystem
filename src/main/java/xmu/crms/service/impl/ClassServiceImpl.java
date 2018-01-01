package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.CourseSelection;
import xmu.crms.entity.Location;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.ClassInfoMapper;
import xmu.crms.service.ClassService;
import xmu.crms.service.FixGroupService;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.SeminarService;

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

	@Autowired
	private SeminarGroupService seminarGroupService;
	
	@Autowired
	private SeminarService seminarService;

	@Autowired
	private FixGroupService fixGroupService;

	@Override
	public void deleteClassSelectionByClassId(BigInteger classId) {
		classInfoMapper.deleteClassSelectionByClassId(classId);
	}

	@Override
	public List<ClassInfo> listClassByCourseId(BigInteger courseId) throws CourseNotFoundException {
		if (classInfoMapper.selectCourseByCourseId(courseId) == null) {
			throw new CourseNotFoundException();
		}
		List<ClassInfo> classList = classInfoMapper.listClassByCourseId(courseId);
		return classList;
	}

	@Override
	public ClassInfo getClassByClassId(BigInteger classId) throws ClassesNotFoundException {
		ClassInfo classInfo = classInfoMapper.selectClassByClassId(classId);
		if (classInfo == null) {
			throw new ClassesNotFoundException();
		}
		return classInfo;
	}

	@Override
	public void updateClassByClassId(BigInteger classId, ClassInfo classInfo) throws ClassesNotFoundException {
		ClassInfo classFound = classInfoMapper.selectClassByClassId(classId);
		if (classFound == null) {
			throw new ClassesNotFoundException();
		}
		classInfo.setId(classId);
		classInfoMapper.updateByPrimaryKeySelective(classInfo);
	}

	@Override
	public void deleteClassByClassId(BigInteger classId) throws ClassesNotFoundException {
		ClassInfo classFound = classInfoMapper.selectClassByClassId(classId);
		if (classFound == null) {
			throw new ClassesNotFoundException();
		}
		deleteClassSelectionByClassId(classId);
		fixGroupService.deleteFixGroupByClassId(classId);

		int flag = classInfoMapper.deleteByPrimaryKey(classId);
		if (flag == 0) {
			throw new ClassesNotFoundException();
		}
	}

	@Override
	public BigInteger insertCourseSelectionById(BigInteger userId, BigInteger classId)
			throws UserNotFoundException, ClassesNotFoundException {
		ClassInfo classInfo = classInfoMapper.selectClassByClassId(classId);
		if (classInfo == null) {
			throw new ClassesNotFoundException();
		}
		User student = classInfoMapper.selectUserByUserId(userId);
		if (student == null) {
			throw new UserNotFoundException();
		}
		CourseSelection courseSelection = new CourseSelection();
		courseSelection.setClassInfo(classInfo);
		courseSelection.setStudent(student);
		classInfoMapper.insertCourseSelectionById(courseSelection);

		return courseSelection.getId();
	}

	@Override
	public void deleteCourseSelectionById(BigInteger userId, BigInteger classId)
			throws UserNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.selectUserByUserId(userId) == null) {
			throw new UserNotFoundException();
		}
		if (classInfoMapper.selectClassByClassId(classId) == null) {
			throw new ClassesNotFoundException();
		}
		classInfoMapper.deleteCourseSelectionById(userId, classId);
	}

	@Override
	public Location getCallStatusById(BigInteger classId, BigInteger seminarId) throws SeminarNotFoundException {
		Location location = classInfoMapper.getCallStatusById(classId, seminarId);
		Seminar seminar=seminarService.getSeminarBySeminarId(seminarId);
		if (seminar == null) {
			throw new SeminarNotFoundException();
		}
		return location;
	}

	@Override
	public BigInteger insertClassById(BigInteger courseId, ClassInfo classInfo) throws CourseNotFoundException {
		Course course = classInfoMapper.selectCourseByCourseId(courseId);
		if (course == null) {
			throw new CourseNotFoundException();
		}
		if (classInfo.getCourse() == null) {
			classInfo.setCourse(course);
		}
		classInfoMapper.insertSelective(classInfo);
		return classInfo.getId();
	}

	@Override
	public void deleteClassByCourseId(BigInteger courseId) throws CourseNotFoundException {
		Course course = classInfoMapper.selectCourseByCourseId(courseId);
		if (course == null) {
			throw new CourseNotFoundException();
		}
		List<ClassInfo> classInfos = listClassByCourseId(courseId);
		for (ClassInfo classInfo : classInfos) {
			deleteClassSelectionByClassId(classInfo.getId());
			try {
				fixGroupService.deleteFixGroupByClassId(classInfo.getId());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (ClassesNotFoundException e) {
				e.printStackTrace();
			}
		}
		classInfoMapper.deleteClassByCourseId(courseId);
	}

	@Override
	public BigInteger callInRollById(Location location) throws SeminarNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.selectClassByClassId(location.getClassInfo().getId()) == null) {
			throw new ClassesNotFoundException();
		}
		if (classInfoMapper.selectSeminarBySeminarId(location.getSeminar().getId()) == null) {
			throw new SeminarNotFoundException();
		}
		classInfoMapper.insetLocation(location);
		return location.getId();
	}

	@Override
	public void endCallRollById(BigInteger seminarId, BigInteger classId)
			throws SeminarNotFoundException, ClassesNotFoundException {
		if (classInfoMapper.selectClassByClassId(classId) == null) {
			throw new ClassesNotFoundException();
		}
		if (classInfoMapper.selectSeminarBySeminarId(seminarId) == null) {
			throw new SeminarNotFoundException();
		}
		classInfoMapper.endCallRollLocation(seminarId, classId);

	}

	@Override
	public List<ClassInfo> listClassByUserId(BigInteger userId)
			throws IllegalArgumentException, ClassesNotFoundException {
		if (userId == null) {
			throw new IllegalArgumentException();
		}
		List<ClassInfo> list = classInfoMapper.listClassByUserId(userId);
		if (list == null) {
			throw new ClassesNotFoundException();
		}
		return list;
	}

}
