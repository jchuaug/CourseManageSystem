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

/**
 * 和Class有关的数据库操作
 *
 * @author yjj
 * @date 2017/12/28
 */
public interface ClassInfoMapper {
	/**
	 * 根据班级ID删除班级，返回删除条数
	 *
	 * @param id 班级id
	 * @return 删除条数
	 */
	int deleteByPrimaryKey(BigInteger id);

	/**
	 * 插入班级信息
	 *
	 * @param record 班级信息
	 *            
	 * @return 插入条数
	 */
	int insertSelective(ClassInfo record);

	/**
	 * 根据班级ID查询班级，返回班级信息
	 *
	 * @param id 班级id
	 * @return 班级信息
	 */
	ClassInfo selectClassByClassId(BigInteger id);

	/**
	 * 根据班级信息更新班级，返回更新条数
	 *
	 * @param record 班级信息
	 * @return 更新条数
	 */
	int updateByPrimaryKeySelective(ClassInfo record);

	/**
	 * 根据班级ID删除该班级所有选课信息，返回删除条数
	 *
	 * @param classId 班级id
	 * @return 删除条数
	 */
	int deleteClassSelectionByClassId(BigInteger classId);

	/**
	 * 根据课程ID查询班级，返回班级信息列表
	 *
	 * @param courseId 课程id
	 * @return ClassInfo List
	 */
	List<ClassInfo> listClassByCourseId(BigInteger courseId);

	/**
	 * 根据班级ID和学生id 删除选课信息
	 *
	 * @param userId 学生id
	 * @param classId 班级id
	 * @return 删除条数
	 */
	int deleteCourseSelectionById(@Param("userId") BigInteger userId, @Param("classId") BigInteger classId);

	/**
	 * 根据课程ID删除班级列表，返回删除条数
	 *
	 * @param courseid 课程id
	 * @return 删除条数
	 */
	int deleteClassByCourseId(BigInteger courseId);

	/**
	 * 根据UserID查询User，返回User
	 *
	 * @param id Userid
	 * @return User 信息
	 */
	User selectUserByUserId(BigInteger id);

	/**
	 * 根据课程ID查询课程，返回课程信息
	 *
	 * @param id 课程id
	 * @return 课程信息
	 */
	Course selectCourseByCourseId(BigInteger id);

	/**
	 * 根据Location信息插入Location
	 *
	 * @param location 地点信息
	 * @return 插入条数
	 */
	int insetLocation(Location location);

	/**
	 * 根据学生id查询所选所有班级，返回班级信息列表
	 *
	 * @param userId 学生id
	 * @return 班级列表
	 */
	List<ClassInfo> listClassByUserId(BigInteger userId);

	/**
	 * 结束一个班级在一个讨论课的签到
	 *
	 * @param classId 班级id
	 * @param seminarId 讨论课id
	 * @return 影响记录条数
	 */
	int endCallRollLocation(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);

	/**
	 * 得到讨论课地点
	 *
	 * @param classId 班级id
	 * @param seminarId 讨论课id
	 * @return Location
	 */
	Location getCallStatusById(@Param("classId") BigInteger classId, @Param("seminarId") BigInteger seminarId);

	/**
	 * 根据讨论课id查询讨论课信息，返回讨论课信息
	 *
	 * @param seminarId 讨论课id
	 * @return 讨论课信息
	 */
	Seminar selectSeminarBySeminarId(BigInteger id);

	/**
	 * 根据course selection插入学生选课信息，返回插入条数
	 *
	 * @param courseSelection 学生选课信息
	 * @return 插入条数
	 */
	int insertCourseSelectionById(CourseSelection courseSelection);
}