package xmu.crms.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.Location;
import xmu.crms.entity.User;

public interface ClassInfoDao {
	int deleteByPrimaryKey(BigInteger id);

	int insertSelective(ClassInfo record);

	ClassInfo selectByPrimaryKey(BigInteger id);

	int updateByPrimaryKeySelective(ClassInfo record);

	int deleteClassSelectionByClassId(BigInteger classId);

	List<ClassInfo> listClassByCourseId(BigInteger courseId);

	int insertCourseSelectionById(@Param("userId") BigInteger userId, @Param("classId") BigInteger classId);

	int deleteCourseSelectionById(@Param("userId") BigInteger userId, @Param("classId") BigInteger classId);

	BigInteger getCourseSelectionId(@Param("userId") BigInteger userId, @Param("classId") BigInteger classId);

	int deleteClassByCourseId(BigInteger courseId);

	int updateScoreRule(ClassInfo proportions);

	Integer getNumStudentByClassId(BigInteger id);

	List<User> listTeacherByTeacherName(String teacherName);

	List<Course> listCourseByCourseNameAndTeacherId(@Param("courseName") String courseName,
			@Param("teacherId") BigInteger teacherId);

	User getTeacherById(BigInteger id);

	Course getCourseById(BigInteger id);

	int deleteScoreRuleById(BigInteger classId);

	Location getCallStatusById(@Param("classId") BigInteger classId, @Param("seminarId") BigInteger seminarId);

	int insetLocation(Location location);

	int updateLocation(Location location);

	List<ClassInfo> listClassByUserId(BigInteger userId);

	User getStudentById(BigInteger id);
}