package xmu.crms.service;

import xmu.crms.entity.Attendance;
import xmu.crms.entity.Course;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;

import java.math.BigInteger;
import java.util.List;

/**
 * @author YeHongjie
 * @version 2.20
 */
public interface UserService {

    /**
     * 添加学生签到信息.
     * <p>根据班级id，讨论课id，学生id，经度，纬度进行签到，在方法中通过班级id，讨论课id获取当堂课发起签到的位置<br>
     *
     * @param classId   班级的id
     * @param seminarId 讨论课的id
     * @param userId    学生的id
     * @param longitude 经度
     * @param latitude  纬度
     * @return id 该记录的id
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws ClassesNotFoundException 未找到班级
     * @throws SeminarNotFoundException 未找到讨论课
     * @throws UserNotFoundException    未找到对应用户
     * @author LiuAiqi
     */
    BigInteger insertAttendanceById(BigInteger classId, BigInteger seminarId,
                                    BigInteger userId, double longitude, double latitude) throws
            IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException, UserNotFoundException;

    /**
     * 获取学生签到信息.
     * <p>根据班级id，讨论课id获取当堂课签到信息<br>
     *
     * @param classId   班级的id
     * @param seminarId 讨论课id
     * @return list 当堂课签到信息
     * @throws IllegalArgumentException 信息不合法，id格式错误
     * @throws ClassesNotFoundException 未找到班级
     * @throws SeminarNotFoundException 未找到讨论课
     * @author LiuAiqi
     */
    List<Attendance> listAttendanceById(BigInteger classId, BigInteger seminarId)
            throws IllegalArgumentException, ClassesNotFoundException,
            SeminarNotFoundException;

    /**
     * 根据用户Id获取用户的信息.
     * <p>根据用户Id获取用户的信息<br>
     *
     * @param userId 用户Id
     * @return user 用户信息
     * @throws IllegalArgumentException throws when 信息不合法，id格式错误
     * @throws UserNotFoundException    throws when 未找到对应用户
     * @author qinlingyun
     * @see SchoolService#getSchoolBySchoolId(BigInteger schoolId)
     */
    User getUserByUserId(BigInteger userId) throws IllegalArgumentException,
            UserNotFoundException;

    /**
     * 根据用户学（工）号获取用户的信息.
     * <p>根据用户学（工）号获取用户的信息<br>
     *
     * @param userNumber 用户学（工）号
     * @return user 用户信息
     * @throws IllegalArgumentException throws when 信息不合法
     * @throws UserNotFoundException    throws when 未找到对应用户
     * @author YeHongjie
     * @see SchoolService#getSchoolBySchoolId(BigInteger schoolId)
     */
    User getUserByUserNumber(String userNumber) throws IllegalArgumentException,
            UserNotFoundException;

    /**
     * 根据用户名获取用户ID.
     * <p>根据用户名获取用户ID<br>
     *
     * @param userName 用户名
     * @return userId 用户ID
     * @throws IllegalArgumentException throws when 信息不合法，id格式错误
     * @throws UserNotFoundException    throws when 未找到对应用户
     * @author qinlingyun
     */
    List<BigInteger> listUserIdByUserName(String userName) throws
            IllegalArgumentException, UserNotFoundException;

    ;

    /**
     * 根据用户ID修改用户信息.
     * <p>根据用户ID修改用户信息<br>
     *
     * @param userId 用户Id
     * @param user   用户信息
     * @throws UserNotFoundException throws when 未找到对应用户
     * @author qinlingyun
     */
    void updateUserByUserId(BigInteger userId, User user) throws
            UserNotFoundException;


    /**
     * 按班级ID、学号开头、姓名开头获取学生列表.
     * <p>按班级ID、学号开头、姓名开头获取学生列表<br>
     *
     * @param classId       班级ID
     * @param numBeginWith  学号开头
     * @param nameBeginWith 姓名开头
     * @return list 用户列表
     * @throws IllegalArgumentException throws when 信息不合法
     * @throws ClassesNotFoundException throws when 未找到对应班级
     * @throws UserNotFoundException    throws when 无符合条件的学生
     * @author qinlingyun
     */
    List<User> listUserByClassId(BigInteger classId, String numBeginWith,
                                 String nameBeginWith) throws IllegalArgumentException,
            ClassesNotFoundException, UserNotFoundException;


    /**
     * 根据用户名获取用户列表.
     * <p>根据用户名获取用户列表<br>
     *
     * @param userName 用户名
     * @return list 用户列表
     * @throws UserNotFoundException throws when 未找到对应用户
     * @author qinlingyun
     */
    List<User> listUserByUserName(String userName) throws UserNotFoundException;


    /**
     * 获取讨论课所在的班级的出勤学生名单.
     * <p>根据ID获取讨论课所在的班级的出勤学生名单<br>
     *
     * @param seminarId 讨论课ID
     * @param classId   班级ID
     * @return list 处于出勤状态的学生的列表
     * @throws IllegalArgumentException throws when 信息不合法，id格式错误
     * @throws ClassesNotFoundException 未找到班级
     * @throws SeminarNotFoundException 未找到讨论课
     * @author qinlingyun
     * @see UserService #listAttendanceById(BigInteger, BigInteger)
     * @see UserService #getUserByUserId(BigInteger)
     */
    List<User> listPresentStudent(BigInteger seminarId, BigInteger classId) throws
            IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException;


    /**
     * 获取讨论课所在班级迟到学生名单.
     * <p>获取讨论课所在班级迟到学生名单<br>
     *
     * @param seminarId 讨论课ID
     * @param classId   班级ID
     * @return list 处于迟到状态的学生列表
     * @throws IllegalArgumentException throws when 信息不合法，id格式错误
     * @throws ClassesNotFoundException 未找到班级
     * @throws SeminarNotFoundException 未找到讨论课
     * @author qinlingyun
     * @see UserService #listAttendanceById(BigInteger, BigInteger)
     * @see UserService #getUserByUserId(BigInteger)
     */
    List<User> listLateStudent(BigInteger seminarId, BigInteger classId) throws
            IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException;


    /**
     * 获取讨论课所在班级缺勤学生名单.
     * <p>获取讨论课所在班级缺勤学生名单<br>
     *
     * @param seminarId 讨论课ID
     * @param classId   班级ID
     * @return list 处于缺勤状态的学生列表
     * @throws IllegalArgumentException throws when 信息不合法，id格式错误
     * @throws ClassesNotFoundException 未找到班级
     * @throws SeminarNotFoundException 未找到讨论课
     * @see UserService #listAttendanceById(BigInteger, BigInteger)
     * @see UserService #getUserByUserId(BigInteger)
     */
    List<User> listAbsenceStudent(BigInteger seminarId, BigInteger classId) throws
            IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException;

    /**
     * 根据教师名称列出课程名称.
     * <p>根据教师名称列出课程名称<br>
     *
     * @param teacherName 教师名称
     * @return list 课程列表
     * @throws IllegalArgumentException throws when 信息不合法，id格式错误
     * @throws UserNotFoundException    throws when 无对应姓名的教师
     * @throws CourseNotFoundException  throws when 对应姓名的用户未创设任何课程
     * @author yexiaona
     * @see UserService #listUserByUserName(String userName)
     * @see CourseService #listCourseByUserId(BigInteger userId)
     */
    List<Course> listCourseByTeacherName(String teacherName) throws
            UserNotFoundException, IllegalArgumentException, CourseNotFoundException;

    /**
     * d
     * @param phone
     * @return d
     */
    User getUserByUserPhone(String phone);

    /**
     * d
     * @param code d
     * @return d
     */
    User getUserByWeChatCode(String code);

    /**
     * bind user and wechat account
     *
     * @param user user
     */
    void bindWeChatUser(User user);

    void unBindWeChatUser(BigInteger userId);
}
