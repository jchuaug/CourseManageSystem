package xmu.crms.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xmu.crms.CourseManageApplication;
import xmu.crms.entity.Course;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.UserMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
public class UserServiceTest {
	@Autowired
	UserService userService;
	@Autowired
	UserMapper userMapper;

	/**
	 * test failed problem:can't get id after insert operation,it turns out to 1 all
	 * the time
	 * 
	 * 
	 * fixed the return value problem by jackey on 2017/12/29
	 */

	@Test
	public void insertAttendanceByIdTest() {
		BigInteger classId = BigInteger.valueOf(1);
		BigInteger seminarId = BigInteger.valueOf(1);
		BigInteger userId = BigInteger.valueOf(11);
		double longitude = 10.1;
		double latitude = 12.1;
		BigInteger classId2 = BigInteger.valueOf(4);
		BigInteger seminarId2 = BigInteger.valueOf(6);
		BigInteger userId2 = BigInteger.valueOf(11);
		double longitude2 = 10.1;
		double latitude2 = 12.1;
		try {
			userService.insertAttendanceById(classId, seminarId, userId, longitude, latitude);
			userService.insertAttendanceById(classId2, seminarId2, userId2, longitude2, latitude2);
		} catch (IllegalArgumentException | ClassesNotFoundException | SeminarNotFoundException
				| UserNotFoundException e) {

			e.printStackTrace();
		}
	}

	/**
	 * test success
	 */
	@Test
	public void listAttendanceByIdTest() {
		BigInteger classId = BigInteger.valueOf(1);
		BigInteger seminarId = BigInteger.valueOf(3);
		List<xmu.crms.entity.Attendance> attendances = new ArrayList<>();
		try {
			attendances = userService.listAttendanceById(classId, seminarId);
		} catch (IllegalArgumentException | ClassesNotFoundException | SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(attendances.toString());

	}

	/**
	 * test success
	 */

	@Test
	public void getUserByUserIdTest() throws IllegalArgumentException, UserNotFoundException {
		BigInteger[] idArray = { BigInteger.valueOf(1), BigInteger.valueOf(2), BigInteger.valueOf(33),
				BigInteger.valueOf(999), BigInteger.valueOf(99999), BigInteger.valueOf(5), BigInteger.valueOf(2),
				BigInteger.valueOf(1) };
		for (BigInteger id : idArray) {
			User user = userService.getUserByUserId(id);
			System.out.println(user);
		}

		System.out.println("Testing getUserByUserIdTest done!");
	}

	/**
	 * test success
	 */
	@Test
	public void listUserIdByUserNameTest() throws UserNotFoundException {

		String[] names = { "邱明", "王美红", "吴清强", "吴清", "美红", "学生1", "不存在" };
		List<User> users = new ArrayList<>();
		for (String name : names) {

			users = userService.listUserByUserName(name);
			System.out.println(users);
		}
		System.out.println("Testing listUserIdByUserNameTest done!");
	}

	/**
	 * test success
	 */
	@Test
	public void updateUserByUserIdTest() throws UserNotFoundException {
		User user = userMapper.getUserByUserId(BigInteger.valueOf(1));
		User tempUser = user;// backups
		System.out.println("user going to be update is:" + user);
		BigInteger id = user.getId();
		user.setWechatId("test1");
		userService.updateUserByUserId(id, user);
		User newUser = userMapper.getUserByUserId(BigInteger.valueOf(1));
		System.out.println("updated wecaht id is:" + newUser.getWechatId());
		userService.updateUserByUserId(id, tempUser);// recover
		System.out.println("Testing updateUserByUserIdTest done!");

	}

	/**
	 * test success
	 */

	@Test
	public void listUserByClassIdTest() {
		List<User> users = new ArrayList<>();
		List<User> users2 = new ArrayList<>();
		List<User> users3 = new ArrayList<>();
		List<User> users4 = new ArrayList<>();
		try {
			users = userService.listUserByClassId(BigInteger.valueOf(1), "243", "学");
			System.out.println(users);
			users = userService.listUserByClassId(BigInteger.valueOf(4), "243", "学");
			System.out.println(users2);
			users = userService.listUserByClassId(BigInteger.valueOf(1), "00243", "学");
			System.out.println(users3);
			users = userService.listUserByClassId(BigInteger.valueOf(1), "243", "8学");
			System.out.println(users4);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Testing listUserByClassIdTest done!");
	}

	/**
	 * test success
	 */
	@Test
	public void listUserByUserNameTest() {
		List<User> user1 = new ArrayList<>();
		List<User> user2 = new ArrayList<>();
		List<User> user3 = new ArrayList<>();
		List<User> user4 = new ArrayList<>();

		try {
			user1 = userService.listUserByUserName("邱明");
			System.out.println(user1);
			user2 = userService.listUserByUserName("明");
			System.out.println(user2);
			user3 = userService.listUserByUserName("秋明");
			System.out.println(user3);
			user4 = userService.listUserByUserName("学生1");
			System.out.println(user4);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Testing listUserByUserNameTest done!");
	}

	/**
	 * test success
	 */
	@Test
	public void listPresentStudentTest() {
		List<User> users1 = new ArrayList<>();
		List<User> users2 = new ArrayList<>();
		List<User> users3 = new ArrayList<>();
		List<User> users4 = new ArrayList<>();

		try {
			users1 = userService.listPresentStudent(BigInteger.valueOf(3), BigInteger.valueOf(1));
			System.out.println("user1:" + users1);
			users1 = userService.listPresentStudent(BigInteger.valueOf(4), BigInteger.valueOf(1));
			System.out.println("user2:" + users2);
			users1 = userService.listPresentStudent(BigInteger.valueOf(3), BigInteger.valueOf(4));
			System.out.println("user3:" + users3);
			users1 = userService.listPresentStudent(BigInteger.valueOf(6), BigInteger.valueOf(6));
			System.out.println("user4:" + users4);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Testing listPresentStudentTest done!");

	}

	/**
	 * test success
	 */
	@Test
	public void listLateStudentTest() {
		List<User> users1 = new ArrayList<>();
		List<User> users2 = new ArrayList<>();
		List<User> users3 = new ArrayList<>();
		List<User> users4 = new ArrayList<>();

		try {
			users1 = userService.listLateStudent(BigInteger.valueOf(3), BigInteger.valueOf(1));
			System.out.println("user1:" + users1);
			users1 = userService.listLateStudent(BigInteger.valueOf(4), BigInteger.valueOf(1));
			System.out.println("user2:" + users2);
			users1 = userService.listLateStudent(BigInteger.valueOf(3), BigInteger.valueOf(4));
			System.out.println("user3:" + users3);
			users1 = userService.listLateStudent(BigInteger.valueOf(6), BigInteger.valueOf(6));
			System.out.println("user4:" + users4);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Testing listLateStudentTest done!");

	}

	/**
	 * test success
	 */
	@Test
	public void listAbsenceStudentTest() {
		List<User> users1 = new ArrayList<>();
		List<User> users2 = new ArrayList<>();
		List<User> users3 = new ArrayList<>();
		List<User> users4 = new ArrayList<>();

		try {
			users1 = userService.listAbsenceStudent(BigInteger.valueOf(3), BigInteger.valueOf(1));
			System.out.println("user1:" + users1);
			users1 = userService.listAbsenceStudent(BigInteger.valueOf(4), BigInteger.valueOf(1));
			System.out.println("user2:" + users2);
			users1 = userService.listAbsenceStudent(BigInteger.valueOf(3), BigInteger.valueOf(4));
			System.out.println("user3:" + users3);
			users1 = userService.listAbsenceStudent(BigInteger.valueOf(6), BigInteger.valueOf(6));
			System.out.println("user4:" + users4);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Testing listAbsenceStudentTest done!");
	}

	/**
	 * test success
	 */
	@Test
	public void listCourseByTeacherNameTest() {
		String[] names = { "邱明", "王美红", "学生1", "学生2" };
		for (String name : names) {
			List<Course> courses = new ArrayList<>();
			try {
				courses = userService.listCourseByTeacherName(name);
				System.out.println(name + "'s courses are:" + courses);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UserNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CourseNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Testing listCourseByTeacherNameTest done!");
	}

}
