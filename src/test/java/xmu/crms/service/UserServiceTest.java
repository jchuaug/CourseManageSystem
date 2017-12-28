package xmu.crms.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xmu.crms.CourseManageApplication;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
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
	 * 测试insertAttendanceById
	 */
	// test fail
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

	@Test
	public void updateUserByUserIdTest() throws UserNotFoundException {
		User user = new User("12345678912", "test update");
		Integer returnId=userMapper.insertUser(user);
		System.out.println(returnId);
		BigInteger id = BigInteger.valueOf(returnId);
		System.out.println("Inserted id is:" + id);
/*		User temp = userMapper.getUserByUserId(BigInteger.valueOf(1));
		user = temp;
		System.out.println("updated user is:" + user);
		user.setId(id);
		user.setPhone("12345678912");
		userService.updateUserByUserId(id, user);
		System.out.println("Testing updateUserByUserIdTest done!");*/

	}

	@Test
	public void listUserByClassIdTest() {
	}

	@Test
	public void listUserByUserNameTest() {
	}

	@Test
	public void listPresentStudentTest() {
	}

	@Test
	public void listLateStudentTest() {
	}

	@Test
	public void listAbsenceStudentTest() {
	}

	@Test
	public void listCourseByTeacherNameTest() {
	}

}
