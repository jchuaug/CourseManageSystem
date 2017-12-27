package xmu.crms.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.Attendance;
import xmu.crms.entity.Course;
import xmu.crms.entity.Location;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.UserMapper;
import xmu.crms.service.CourseService;
import xmu.crms.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserMapper userMapper;

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
				throw new IllegalArgumentException();
			}
			user = userMapper.getUserByUserId(userId);
			if (user == null) {
				throw new UserNotFoundException();
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
		List<User> users = new ArrayList<User>();
		List<BigInteger> ids = new ArrayList<>();
		try {
			users = userMapper.listUsersByName(userName);
			if (users.isEmpty()) {
				throw new UserNotFoundException();
			}
			Iterator<User> iterator = users.iterator();
			while (iterator.hasNext()) {
				ids.add(iterator.next().getId());
			}

		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return ids;
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

			if (userMapper.getUserByUserId(userId) == null) {
				throw new UserNotFoundException();
			}
			userMapper.updateUserByUserId(userId, user);
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
			if (userMapper.getClassByClassId(classId) == null) {
				throw new ClassesNotFoundException();
			}
			users = userMapper.listUserByClassId(classId, numBeginWith, nameBeginWith);
			if (users.isEmpty()) {
				throw new UserNotFoundException();
			}
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
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
			users = userMapper.listUsersByName(userName);
			if (users.isEmpty()) {
				throw new UserNotFoundException();
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
			users = userMapper.listPresentStudent(seminarId, classId);
			if (users.isEmpty()) {
				throw new UserNotFoundException();
			}
		} catch (UserNotFoundException e) {
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
			users = userMapper.listAbsenceStudentById(seminarId, classId);
			if (users.isEmpty()) {
				throw new UserNotFoundException();
			}
		} catch (UserNotFoundException e) {
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
		try {
			if (userMapper.listUsersByName(teacherName).isEmpty()) {
				throw new UserNotFoundException();
			}
			courses = userMapper.listCourseByTeacherName(teacherName);
			if (courses.isEmpty()) {
				throw new CourseNotFoundException();
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
			users = userMapper.listLateStudent(seminarId, classId);
			if (users.isEmpty()) {
				throw new UserNotFoundException();
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return users;
	}

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
	 * @return id 该记录的id
	 * @exception IllegalArgumentException
	 *                信息不合法，id格式错误
	 * @exception ClassesNotFoundException
	 *                未找到班级
	 * @exception SeminarNotFoundException
	 *                未找到讨论课
	 * @exception UserNotFoundException
	 *                未找到对应用户
	 */
	@Override
	public BigInteger insertAttendanceById(BigInteger classId, BigInteger seminarId, BigInteger userId,
			double longitude, double latitude)
			throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException, UserNotFoundException {
		BigInteger insertedRows = null;
		try {
			if (userMapper.getClassByClassId(classId) == null) {
				throw new ClassesNotFoundException();
			}
			if (userMapper.getSeminarBySeminarId(seminarId) == null) {
				throw new SeminarNotFoundException();
			}
			Location location = userMapper.getLocationBySeminarIdAndClassId(seminarId, classId);
			Integer status = -1;
			if (!((location.getLatitude() - latitude) > 30 && location.getLongitude() - longitude > 30)) {
				throw new InvalidOperationException();
			}
			status = 1;
			insertedRows = userMapper.insertAttendanceById(classId, seminarId, userId, status);
			if (insertedRows == null) {
				throw new InvalidOperationException();
			}

		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
		} catch (InvalidOperationException e) {
			e.printStackTrace();
		}

		return insertedRows;
	}

	@Override
	public User getUserByUserNumber(String userNumber) throws IllegalArgumentException, UserNotFoundException {
		User user = null;
		try {
			user = userMapper.getUserByNumber(userNumber);
			if (user == null) {
				throw new UserNotFoundException();
			}
		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<Attendance> listAttendanceById(BigInteger classId, BigInteger seminarId)
			throws IllegalArgumentException, ClassesNotFoundException, SeminarNotFoundException {
		List<Attendance> attendances = new ArrayList<Attendance>();
		try {
			if (userMapper.getClassByClassId(classId) == null) {
				throw new ClassesNotFoundException();
			}
			if (userMapper.getSeminarBySeminarId(seminarId) == null) {
				throw new SeminarNotFoundException();
			}
			attendances = userMapper.listAttendanceByClassIdAndSeminarId(classId, seminarId);
			if (attendances.isEmpty()) {
				throw new Exception();
			}
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return attendances;
	}

}
