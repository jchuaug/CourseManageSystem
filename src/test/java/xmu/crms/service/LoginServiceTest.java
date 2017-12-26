package xmu.crms.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xmu.crms.CourseManageApplication;
import xmu.crms.entity.User;
import xmu.crms.exception.UserNotFoundException;

/**
 * LoginService测试
 * 
 * @author JackeyHuang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
public class LoginServiceTest {

	@Autowired
	private LoginService loginService;

	/**
	 * 手机号登录测试
	 */
	@Test
	public void signInPhoneTest() {
		Map<String, User> testMap = new HashMap<String, User>();
		testMap.put("teacher1", new User("15720335800", "BE8791B8BE6DEC10"));
		testMap.put("teacher2", new User("15720335800", "BE8791B8BE6"));
		testMap.put("teacher3", new User("157203358001245", "BE8791B8BE6DEC10"));
		testMap.put("student1", new User("15720335803", "BE8791B8BE6DEC10"));
		testMap.put("student2", new User("15720335803", "BE8791B8BE6D"));
		testMap.put("student3", new User("157203358031545", "BE8791B8BE6DEC10"));

		for (User user : testMap.values()) {
			try {
				User resultUser = loginService.signInPhone(user);
				if (resultUser == null) {
					throw new UserNotFoundException("登录失败");
				}
				System.out.println("登录成功，返回的用信息为：" + resultUser.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 手机号注册测试
	 */
	@Test
	public void signUpPhoneTest() {
		Map<String, User> testMap = new HashMap<String, User>();
		testMap.put("teacher1", new User("15720335800", "BE8791B8BE6DEC10"));
		testMap.put("teacher2", new User("18720335800", "BE8791B8BE6"));
		testMap.put("student1", new User("15720335803", "BE8791B8BE6DEC10"));
		testMap.put("student2", new User("18720335803", "BE8791B8BE6D"));
		for (User user : testMap.values()) {
			try {
				User resultUser = loginService.signUpPhone(user);
				if (resultUser == null) {
					throw new Exception("注册失败");
				}
				System.out.println("注册成功，返回的信息为：" + resultUser.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
/**
 * 解绑教师账号测试
 */
	@Test
	public void deleteTeacherAccountTest() {
		Map<String, User> testMap = new HashMap<String, User>();
		testMap.put("teacher1", new User("15720335800", "BE8791B8BE6DEC10"));
		testMap.put("teacher2", new User("18720335800", "BE8791B8BE6"));
		testMap.put("student1", new User("15720335803", "BE8791B8BE6DEC10"));
		testMap.put("student2", new User("18720335803", "BE8791B8BE6D"));
		for (User user : testMap.values()) {
			try {
				loginService.deleteTeacherAccount(user.getId());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * 解绑学生账号测试
	 */
		@Test
		public void deleteStudentAccountTest() {
			Map<String, User> testMap = new HashMap<String, User>();
			testMap.put("teacher1", new User("15720335800", "BE8791B8BE6DEC10"));
			testMap.put("teacher2", new User("18720335800", "BE8791B8BE6"));
			testMap.put("student1", new User("15720335803", "BE8791B8BE6DEC10"));
			testMap.put("student2", new User("18720335803", "BE8791B8BE6D"));
			for (User user : testMap.values()) {
				try {
					loginService.deleteStudentAccount(user.getId());

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

}
