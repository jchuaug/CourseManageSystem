package xmu.crms.mapper;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.CourseSelection;
import xmu.crms.entity.Location;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.User;

public interface ClassInfoMapper {
	int deleteByPrimaryKey(BigInteger id);

	int insertSelective(ClassInfo record);

	ClassInfo selectClassByClassId(BigInteger id);

	int updateByPrimaryKeySelective(ClassInfo record);

	int deleteClassSelectionByClassId(BigInteger classId);

	List<ClassInfo> listClassByCourseId(BigInteger courseId);

	int deleteCourseSelectionById(@Param("userId") BigInteger userId, @Param("classId") BigInteger classId);


	int deleteClassByCourseId(BigInteger courseId);

	User selectUserByUserId(BigInteger id);

	Course selectCourseByCourseId(BigInteger id);

	int insetLocation(Location location);

	List<ClassInfo> listClassByUserId(BigInteger userId);


	int endCallRollLocation( @Param("seminarId")BigInteger seminarId,@Param("classId") BigInteger classId);

	Location getCallStatusById(@Param("classId")BigInteger classId,@Param("seminarId") BigInteger seminarId);

	Seminar selectSeminarBySeminarId(BigInteger id);

	int insertCourseSelectionById(CourseSelection courseSelection);
}