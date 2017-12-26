package xmu.crms.dao;

import java.math.BigInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xmu.crms.CourseManageApplication;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;

/**
 * UserDao测试
 * 
 * @author JackeyHuang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
public class UserDaoTest {

	@Autowired
	UserDao userDao;

	/**
	 * 测试通过手机号获取用户信息
	 */
	@Test
	public void getUserByPhoneTest() {
		String[] phones = { "15720335875", "15720335800", "15720335815", "123456", "1572033581512" };
		User user = null;
		for (String phone : phones) {
			try {
				user = userDao.getUserByPhone(phone);
				System.out.println(user.toString());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * 测试通过用户Id获取用户信息
	 */
	@Test
	public void getUserByIdTest() {
		BigInteger[] userIds = { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3),
				BigInteger.valueOf(999) };

		for (BigInteger userId : userIds) {
			try {
				User user = userDao.getUserById(userId);
				if (user == null) {
					throw new UserNotFoundException("用户未找到");
				}
				System.out.println(user);

			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 测试根据用户id删除用户
	 */
	@Test
	public void deleteUserAccountTest() {
		BigInteger[] userIds = { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(3),
				BigInteger.valueOf(999) };

		for (BigInteger userId : userIds) {
			try {
				Integer status = userDao.deleteUserAccount(userId);
				if (status == 0) {
					System.out.println("usersdao:删除失败，用户不存在");
					throw new UserNotFoundException("用户不存在");
				}
				System.out.println("usersdao:成功删除一条记录");
			} catch (UserNotFoundException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 测试根据classId获取class_info表的信息
	 */
	@Test
	public void getClassInfoDoByClassIdTest() {
	}

	/**
	 * 测试根据seminarId获取seminar表中的内容
	 */
	@Test
	public void getSeminarDoBySeminarIdTest() {
	}

	/**
	 * 测试根据讨论课id和课堂id获取地点信息
	 */
	@Test
	public void getLocationDoBySeminarIdAndClassIdTest() {
	}

	/**
	 * 测试插入考勤信息
	 */
	@Test
	public void insertAttendanceTest() {
	}

	/**
	 * 测试根据courseId获取course表信息
	 */
	@Test
	public void getCourseDoByCourseIdTest() {
	}

	/**
	 * 测试通过课堂id和讨论课id获取讨论课信息
	 */
	@Test
	public void listAttendanceDoByClassIdAndSeminarIdTest() {
	}

	/**
	 * 测试根据用户名获取用户ID.
	 */
	@Test
	public void getUserIdByUserNameTest() {
	}

	/**
	 * 测试根据用户id更新用户信息
	 */
	@Test
	public void updateUserByUserIdTest() {
	}

	/**
	 * 测试按班级ID、学号开头、姓名开头获取学生列表.
	 */
	@Test
	public void getUserByClassIdTest() {
	}

	/**
	 * 测试根据用户名查找用户
	 */
	@Test
	public void getUserByUserNameTest() {
	}

	/**
	 * 测试获取讨论课所在的出勤学生名单
	 */
	@Test
	public void ListPresentStudentTest() {
	}

	/**
	 * 测试获取讨论课所在的缺勤学生名单
	 */
	@Test
	public void ListAbsenceStudentTest() {
	}

	/**
	 * 测试根据教师名获取课程信息
	 */
	@Test
	public void ListCourseDoByTeacherNameTest() {
	}

	/**
	 * 测试获取讨论课迟到学生名单
	 */
	@Test
	public void ListlateStudentTest() {
	}

}
