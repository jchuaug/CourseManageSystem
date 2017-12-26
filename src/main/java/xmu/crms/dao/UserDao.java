package xmu.crms.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import xmu.crms.DO.AttendanceDO;
import xmu.crms.DO.ClassInfoDO;
import xmu.crms.DO.CourseDO;
import xmu.crms.DO.LocationDO;
import xmu.crms.DO.SeminarDO;
import xmu.crms.entity.User;

/**
 * UserDao
 * 
 * @author JackeyHuang
 *
 */
public interface UserDao {
	/**
	 * 通过手机号获取用户信息
	 * 
	 * @param phone
	 * @return
	 */
	User getUserByPhone(String phone);

	/**
	 * 通过用户Id获取用户信息
	 * 
	 * @param id
	 * @return
	 */

	User getUserById(BigInteger id);

	/**
	 * 根据用户id删除用户
	 * 
	 * @param id
	 * @return
	 */
	Integer deleteUserAccount(BigInteger id);

	/**
	 * 根据classId获取class_info表的信息
	 * 
	 * @param classId
	 * @return
	 */
	ClassInfoDO getClassInfoDoByClassId(BigInteger classId);

	/**
	 * 根据seminarId获取seminar表中的内容
	 * 
	 * @param seminarId
	 * @return
	 */
	SeminarDO getSeminarDoBySeminarId(BigInteger seminarId);

	/**
	 * 根据讨论课id和课堂id获取地点信息
	 * 
	 * @param seminarId
	 * @return
	 */
	LocationDO getLocationDoBySeminarIdAndClassId(@Param("seminarId") BigInteger seminarId,
			@Param("classId") BigInteger classId);

	/**
	 * 插入考勤信息
	 * 
	 * @param attendanceDO
	 * @return
	 */
	Integer insertAttendance(AttendanceDO attendanceDO);

	/**
	 * 根据courseId获取course表信息
	 * 
	 * @param courseId
	 * @return
	 */
	CourseDO getCourseDoByCourseId(BigInteger courseId);

	/**
	 * 通过课堂id和讨论课id获取讨论课信息
	 * 
	 * @param courseId
	 * @param seminarId
	 * @return
	 */
	List<AttendanceDO> listAttendanceDoByClassIdAndSeminarId(@Param("classId") BigInteger classId,
			@Param("seminarId") BigInteger seminarId);

	/**
	 * 根据用户名获取用户ID.
	 * 
	 * @param userName
	 * @return
	 */
	List<BigInteger> getUserIdByUserName(String userName);

	/**
	 * 根据用户id更新用户信息
	 * 
	 * @param userId
	 * @param user
	 */
	void updateUserByUserId(@Param("userId") BigInteger userId, @Param("user") User user);

	/**
	 * 按班级ID、学号开头、姓名开头获取学生列表.
	 * 
	 * @param classId
	 * @param numBeginWith
	 * @param nameBeginWith
	 * @return
	 */
	List<User> getUserByClassId(@Param("classId") BigInteger classId, @Param("numBeginWith") String numBeginWith,
			@Param("nameBeginWith") String nameBeginWith);

	/**
	 * 根据用户名查找用户
	 * 
	 * @param userName
	 * @return
	 */
	List<User> getUserByUserName(String userName);

	/**
	 * 获取讨论课所在的出勤学生名单
	 * 
	 * @param seminarId
	 * @param classId
	 * @return
	 */
	List<User> ListPresentStudent(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);

	/**
	 * 获取讨论课所在的缺勤学生名单
	 * 
	 * @param seminarId
	 * @param classId
	 * @return
	 */
	List<User> ListAbsenceStudent(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);

	/**
	 * 根据教师名获取课程信息
	 * 
	 * @param teacherName
	 * @return
	 */
	List<CourseDO> ListCourseDoByTeacherName(String teacherName);

	/**
	 * 获取讨论课迟到学生名单
	 * 
	 * @param seminarId
	 * @param classId
	 * @return
	 */
	List<User> ListlateStudent(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);
}
