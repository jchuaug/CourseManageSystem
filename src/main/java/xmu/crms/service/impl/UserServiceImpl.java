package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.DO.AttendanceDO;
import xmu.crms.DO.ClassInfoDO;
import xmu.crms.DO.CourseDO;
import xmu.crms.DO.DoToEntityTool;
import xmu.crms.DO.LocationDO;
import xmu.crms.DO.SeminarDO;
import xmu.crms.dao.UserDao;
import xmu.crms.entity.Attendance;
import xmu.crms.entity.Course;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.CourseService;
import xmu.crms.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;

	/**
	 * 添加学生签到信息.
	 * <p>
	 * 根据班级id，讨论课id，学生id，经度，纬度进行签到，在方法中通过班级id，讨论课id获取当堂课发起签到的位置<br>
	 * 
	 * @author LiuAiqi
	 * @param classId
	 *            班级的id
	 * @param seminarId
	 *            讨论课的id
	 * @param userId
	 *            学生的id
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @exception IllegalArgumentException
	 *                信息不合法，id格式错误
	 * @exception ClassesNotFoundException
	 *                未找到班级
	 * @exception SeminarNotFoundException
	 *                未找到讨论课
	 */
	@Override
	public void insertAttendanceById(BigInteger classId, BigInteger seminarId, BigInteger userId, double longitude,
			double latitude) throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException {
		ClassInfoDO classInfoDO = null;
		SeminarDO seminarDO = null;
		LocationDO locationDO = null;
		AttendanceDO attendanceDO = null;
		try {
			classInfoDO = userDao.getClassInfoDoByClassId(classId);
			if (classInfoDO == null) {
				throw new ClassNotFoundException("未找到班级");
			}
			seminarDO = userDao.getSeminarDoBySeminarId(seminarId);
			if (seminarDO == null) {
				throw new SeminarNotFoundException("未找到讨论课");
			}
			locationDO = userDao.getLocationDoBySeminarIdAndClassId(seminarId, classId);
			if (classInfoDO != null && seminarDO != null && locationDO != null) {
				if (longitude == locationDO.getLongitude() && latitude == locationDO.getLatitude())
					;
				attendanceDO = new AttendanceDO(userId, seminarId, classId);
				Integer returnKey = userDao.insertAttendance(attendanceDO);
				if (returnKey !=1) {
					throw new Exception("插入失败");
				}
				System.out.println("插入考勤信息成功");
			}

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取学生签到信息.
	 * <p>
	 * 根据班级id，讨论课id获取当堂课签到信息<br>
	 * 
	 * @author LiuAiqi
	 * @param classId
	 *            班级的id
	 * @param seminarId
	 *            讨论课id
	 * @return list 当堂课签到信息
	 * @exception IllegalArgumentException
	 *                信息不合法，id格式错误
	 * @exception ClassesNotFoundException
	 *                未找到班级
	 * @exception SeminarNotFoundException
	 *                未找到讨论课
	 */
	@Override
	public List<Attendance> listAttendanceById(BigInteger classId, BigInteger seminarId)
			throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException {
		ClassInfoDO classInfoDO = userDao.getClassInfoDoByClassId(classId);
		SeminarDO seminarDO = userDao.getSeminarDoBySeminarId(seminarId);
		List<AttendanceDO> attendanceDOs = new ArrayList<AttendanceDO>();
		List<Attendance> attendances = new ArrayList<Attendance>();
		try {
			if (classInfoDO == null) {
				throw new ClassesNotFoundException("班级未找到");
			}
			if (seminarDO == null) {
				throw new SeminarNotFoundException("未找到讨论课");
			}
			attendanceDOs = userDao.listAttendanceDoByClassIdAndSeminarId(classId, seminarId);
			Iterator<AttendanceDO> iterator = attendanceDOs.iterator();
			while (iterator.hasNext()) {
				attendances.add(new DoToEntityTool().attendanceDoToAttendance(iterator.next()));
			}
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
		}
		return attendances;
	}

	/**
	 * 根据用户Id获取用户的信息.
	 * <p>
	 * 根据用户Id获取用户的信息<br>
	 * 
	 * @author qinlingyun
	 * @param userId
	 *            用户Id
	 * @return user 用户信息
	 * @see SchoolService#getSchoolBySchoolId(BigInteger schoolId)
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法，id格式错误
	 * @exception UserNotFoundException
	 *                throws when 未找到对应用户
	 */
	@Override
	public User getUserByUserId(BigInteger userId) throws IllegalArgumentException, UserNotFoundException {
		User user = null;
		Pattern pattern = Pattern.compile("[0-9]*");
		try {
			if (!(pattern.matcher(userId.toString()).matches())) {
				throw new IllegalArgumentException("信息不合法，id格式错误");
			}
			user = userDao.getUserById(userId);
			if (user == null) {
				throw new UserNotFoundException("用户不存在");
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * 根据用户名获取用户ID.
	 * <p>
	 * 根据用户名获取用户ID<br>
	 * 
	 * @author qinlingyun
	 * @param userName
	 *            用户名
	 * @return userId 用户ID
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法，id格式错误
	 * @exception UserNotFoundException
	 *                throws when 未找到对应用户
	 */
	@Override
	public List<BigInteger> listUserIdByUserName(String userName)
			throws IllegalArgumentException, UserNotFoundException {
		List<BigInteger> userIds = new ArrayList<BigInteger>();
		try {
			userIds = userDao.getUserIdByUserName(userName);
			if (userIds.isEmpty()) {
				throw new UserNotFoundException("用户名不存在");
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return userIds;
	}

	/**
	 * 根据用户ID修改用户信息.
	 * <p>
	 * 根据用户ID修改用户信息<br>
	 * 
	 * @author qinlingyun
	 * @param userId
	 *            用户Id
	 * @param user
	 *            用户信息
	 * @exception UserNotFoundException
	 *                throws when 未找到对应用户
	 */
	@Override
	public void updateUserByUserId(BigInteger userId, User user) throws UserNotFoundException {
		try {

			if (userDao.getUserById(userId) == null) {
				throw new UserNotFoundException("用户不存在");
			}
			userDao.updateUserByUserId(userId, user);
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * <p>
	 * 按班级ID、学号开头、姓名开头获取学生列表<br>
	 * 
	 * @author qinlingyun
	 * @param classId
	 *            班级ID
	 * @param numBeginWith
	 *            学号开头
	 * @param nameBeginWith
	 *            姓名开头
	 * @return list 用户列表
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法
	 * @exception ClassesNotFoundException
	 *                throws when 未找到对应班级
	 * @exception UserNotFoundException
	 *                throws when 无符合条件的学生
	 */
	@Override
	public List<User> listUserByClassId(BigInteger classId, String numBeginWith, String nameBeginWith)
			throws IllegalArgumentException, ClassesNotFoundException {
		List<User> users = new ArrayList<User>();
		try {
			if (userDao.getClassInfoDoByClassId(classId) == null) {
				throw new ClassesNotFoundException("班级不存在");
			}
			users = userDao.getUserByClassId(classId, numBeginWith, nameBeginWith);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 根据用户名获取用户列表.
	 * <p>
	 * 根据用户名获取用户列表<br>
	 * 
	 * @author qinlingyun
	 * @param userName
	 *            用户名
	 * @return list 用户列表
	 * @exception UserNotFoundException
	 *                throws when 未找到对应用户
	 */
	@Override
	public List<User> listUserByUserName(String userName) throws UserNotFoundException {
		List<User> users = new ArrayList<User>();
		try {
			users = userDao.getUserByUserName(userName);
			if (users.isEmpty()) {
				throw new UserNotFoundException("用户不存在");
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 获取讨论课所在的班级的出勤学生名单.
	 * <p>
	 * 根据ID获取讨论课所在的班级的出勤学生名单<br>
	 * 
	 * @author qinlingyun
	 * @param seminarId
	 *            讨论课ID
	 * @param classId
	 *            班级ID
	 * @return list 处于出勤状态的学生的列表
	 * @see UserService #listAttendanceById(BigInteger, BigInteger)
	 * @see UserService #getUserByUserId(BigInteger)
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法，id格式错误
	 * @exception ClassesNotFoundException
	 *                未找到班级
	 * @exception SeminarNotFoundException
	 *                未找到讨论课
	 */

	@Override
	public List<User> listPresentStudent(BigInteger seminarId, BigInteger classId)
			throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException {
		List<User> users = new ArrayList<User>();
		try {
			users = userDao.ListPresentStudent(seminarId, classId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 获取讨论课所在班级迟到学生名单.
	 * <p>
	 * 获取讨论课所在班级迟到学生名单<br>
	 * 
	 * @author qinlingyun
	 * @param seminarId
	 *            讨论课ID
	 * @param classId
	 *            班级ID
	 * @return list 处于迟到状态的学生列表
	 * @see UserService #listAttendanceById(BigInteger, BigInteger)
	 * @see UserService #getUserByUserId(BigInteger)
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法，id格式错误
	 * @exception ClassesNotFoundException
	 *                未找到班级
	 * @exception SeminarNotFoundException
	 *                未找到讨论课
	 */
	@Override
	public List<User> listAbsenceStudent(BigInteger seminarId, BigInteger classId)
			throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException {
		List<User> users = new ArrayList<User>();
		try {
			users = userDao.ListAbsenceStudent(seminarId, classId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return users;
	}

	/**
	 * 根据教师名称列出课程名称.
	 * <p>
	 * 根据教师名称列出课程名称<br>
	 * 
	 * @author yexiaona
	 * @param teacherName
	 *            教师名称
	 * @return list 课程列表
	 * @see UserService #listUserByUserName(String userName)
	 * @see CourseService #listCourseByUserId(BigInteger userId)
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法，id格式错误
	 * @exception UserNotFoundException
	 *                throws when 无对应姓名的教师
	 * @exception CourseNotFoundException
	 *                throws when 对应姓名的用户未创设任何课程
	 */
	@Override
	public List<Course> listCourseByTeacherName(String teacherName)
			throws UserNotFoundException, IllegalArgumentException, CourseNotFoundException {
		List<Course> courses = new ArrayList<>();
		List<CourseDO> courseDOs = new ArrayList<>();
		try {
			if (userDao.getUserByUserName(teacherName).isEmpty()) {
				throw new UserNotFoundException("用户不存在");
			}
			courseDOs = userDao.ListCourseDoByTeacherName(teacherName);
			if (courseDOs.isEmpty()) {
				throw new CourseNotFoundException("对应姓名的用户未创设任何课程");
			}
			Iterator<CourseDO> iterator = courseDOs.iterator();
			while (iterator.hasNext()) {
				courses.add(new DoToEntityTool().courseDoToCourse(iterator.next()));
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		} catch (CourseNotFoundException e) {
			e.printStackTrace();
		}
		return courses;
	}

	/**
	 * 获取讨论课所在班级迟到学生名单.
	 * <p>
	 * 获取讨论课所在班级迟到学生名单<br>
	 * 
	 * @author qinlingyun
	 * @param seminarId
	 *            讨论课ID
	 * @param classId
	 *            班级ID
	 * @return list 处于迟到状态的学生列表
	 * @see UserService #listAttendanceById(BigInteger, BigInteger)
	 * @see UserService #getUserByUserId(BigInteger)
	 * @exception IllegalArgumentException
	 *                throws when 信息不合法，id格式错误
	 * @exception ClassesNotFoundException
	 *                未找到班级
	 * @exception SeminarNotFoundException
	 *                未找到讨论课
	 */
	@Override
	public List<User> listLateStudent(BigInteger seminarId, BigInteger classId)
			throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException {
		List<User> users = new ArrayList<User>();
		try {
			users = userDao.ListlateStudent(seminarId, classId);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return users;
	}

}
