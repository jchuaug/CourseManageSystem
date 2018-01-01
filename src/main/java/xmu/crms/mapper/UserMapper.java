package xmu.crms.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xmu.crms.entity.*;

import java.math.BigInteger;
import java.util.List;

/**
 * UserDao
 *
 * @author JackeyHuang
 */
@Mapper
public interface UserMapper {

    /**
     * 微信登录
     *
     * @param openid
     * @return
     */
    User getUserByOpenId(String openid);

    /**
     * 手机号登录
     *
     * @param phone
     * @return
     */
    User getUserByPhone(String phone);

    /**
     * 通过手机号获取用户信息
     *
     * @param phone
     * @return
     */
    User getUserByUserPhone(String phone);

    /**
     * 通过用户Id获取用户信息
     *
     * @param id
     * @return
     */

    User getUserByUserId(BigInteger id);

    /**
     * 根据学号/教工号查找用户信息
     *
     * @param userNumber
     * @return
     */
    User getUserByNumber(String number);

    /**
     * 根据用户id解绑,置空手机号
     *
     * @param id
     * @return
     */
    Integer unbindUserById(BigInteger id);

    /**
     * 根据classId获取class信息
     *
     * @param classId
     * @return
     */
    /**
     * 根据用户名获取用户.
     *
     * @param userName
     * @return
     */
    List<User> listUsersByName(String name);

    /**
     * 按班级ID、学号开头、姓名开头获取学生列表.
     *
     * @param classId
     * @param numBeginWith
     * @param nameBeginWith
     * @return
     */
    List<User> listUserByClassId(@Param("classId") BigInteger classId, @Param("numberBeginWith") String numberBeginWith,
                                 @Param("nameBeginWith") String nameBeginWith);

    /**
     * 根据用户id更新用户信息
     *
     * @param userId
     * @param user
     * @return
     */
    Integer updateUserByUserId(@Param("id") BigInteger id, @Param("user") User user);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Integer insertUser(User user);

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    Integer deleteUserByUserId(BigInteger id);

    /**
     * 根据学校编号查找学校
     *
     * @param id
     * @return
     */
    School getSchoolById(BigInteger id);

    /**
     * 根据学校名查找学校
     *
     * @param id
     * @return
     */
    School getSchoolByName(String name);

    /**
     * 根据courseId获取course信息
     *
     * @param courseId
     * @return
     */
    Course getCourseByCourseId(BigInteger id);

    /**
     * 根据教师名获取课程信息
     *
     * @param id
     * @return
     */
    List<Course> listCourseByTeacherId(String id);

    /**
     * 根据学生名获取课程信息
     *
     * @param id
     * @return
     */
    List<Course> listCoursesByStudentId(String id);

    /**
     * 根据id查找班级信息
     *
     * @param classId
     * @return
     */
    ClassInfo getClassByClassId(BigInteger classId);

    /**
     * 根据讨论课id和课堂id获取签到地点
     *
     * @param seminarId
     * @return
     */
    Location getLocationBySeminarIdAndClassId(@Param("seminarId") BigInteger seminarId,
                                              @Param("classId") BigInteger classId);

    /**
     * 插入考勤信息
     *
     * @param attendanceDO
     * @return
     */
//	Integer insertAttendanceById(@Param("classId") BigInteger classId, @Param("seminarId") BigInteger seminarId,
//			@Param("userId") BigInteger userId, @Param("status") Integer status);
    Integer insertAttendanceById(Attendance attendance);

    /**
     * 获取讨论课所在的缺勤学生名单 status==2
     *
     * @param seminarId
     * @param classId
     * @return
     */
    List<User> listAbsenceStudentById(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);

    /**
     * 获取讨论课所在的出勤学生名单 status==0
     *
     * @param seminarId
     * @param classId
     * @return
     */
    List<User> listPresentStudent(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);

    /**
     * 获取讨论课迟到学生名单
     *
     * @param seminarId
     * @param classId
     * @return
     */
    List<User> listLateStudent(@Param("seminarId") BigInteger seminarId, @Param("classId") BigInteger classId);

    /**
     * 根据教师名获取课程信息
     *
     * @param teacherName
     * @return
     */
    List<Course> listCourseByTeacherName(String name);

    /**
     * 根据seminarId获取seminar信息
     *
     * @param seminarId
     * @return
     */
    Seminar getSeminarBySeminarId(BigInteger seminarId);

    /**
     * 通过课堂id和讨论课id获取讨论课信息
     *
     * @param courseId
     * @param seminarId
     * @return
     */
    List<Attendance> listAttendanceByClassIdAndSeminarId(@Param("classId") BigInteger classId,
                                                         @Param("seminarId") BigInteger seminarId);

    void bindOpenIdWithUser(User user);

    void deleteOpenId(BigInteger userId);
}
