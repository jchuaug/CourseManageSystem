package xmu.crms.service;

import java.math.BigInteger;
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
import xmu.crms.mapper.UserMapper;

/**
 * LoginService测试
 *
 * @author JackeyHuang
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
public class LoginServiceTest {
    @Autowired
    UserMapper userMapper;
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
                    throw new UserNotFoundException();
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
        testMap.put("test user1", new User("133123456789", "BE8791B8BE6DEC10"));
        testMap.put("test user2", new User("135123455432", "BE8791B8BE6"));
        // the two test users bellow is already exist in database
        testMap.put("test user3", new User("15720335800", "BE8791B8BE6DEC10"));
        testMap.put("test user4", new User("15720335801", "BE8791B8BE6DEC10"));
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
        /**
         * Recover test statement
         */
        Map<String, User> recoverMap = new HashMap<>();
        testMap.put("delete test user1", userMapper.getUserByPhone("133123456789"));
        testMap.put("delete test user2", userMapper.getUserByPhone("135123455432"));
        for (User user : testMap.values()) {
            try {
                Integer returnVal = userMapper.deleteUserByUserId(user.getId());
                System.out.println("signUpPhoneTest:returnVal+" + returnVal);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Testing signUpPhoneTest end");

    }

    /**
     * 解绑教师账号测试
     */
    @Test
    public void deleteTeacherAccountTest() {
        Map<String, User> backups = new HashMap<String, User>();
        backups.put("user1", userMapper.getUserByUserId(BigInteger.valueOf(89)));
        backups.put("user2", userMapper.getUserByUserId(BigInteger.valueOf(90)));
        Map<String, BigInteger> testMap = new HashMap<String, BigInteger>();
        testMap.put("test user1", BigInteger.valueOf(89));
        testMap.put("test user2", BigInteger.valueOf(90));
        // the two users above is exist in database
        testMap.put("test user3", BigInteger.valueOf(999999999));
        for (BigInteger userId : testMap.values()) {
            try {
                loginService.deleteTeacherAccount(userId);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /*
         * recover test statement
         */

        for (User user : backups.values()) {
            try {
                Integer recoverState = userMapper.updateUserByUserId(user.getId(), user);
                System.out.println("deleteTeacherAccountTest:revoverState:" + recoverState);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("deleteTeacherAccountTest ends");
    }

    /**
     * 解绑学生账号测试
     */
    @Test
    public void deleteStudentAccountTest() {
        Map<String, User> backups = new HashMap<String, User>();
        backups.put("user1", userMapper.getUserByUserId(BigInteger.valueOf(89)));
        backups.put("user2", userMapper.getUserByUserId(BigInteger.valueOf(90)));
        Map<String, BigInteger> testMap = new HashMap<String, BigInteger>();
        testMap.put("test user1", BigInteger.valueOf(89));
        testMap.put("test user2", BigInteger.valueOf(90));
        // the two users above is exist in database
        testMap.put("test user3", BigInteger.valueOf(999999999));
        for (BigInteger userId : testMap.values()) {
            try {
                loginService.deleteTeacherAccount(userId);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /*
         * recover test statement
         */

        for (User user : backups.values()) {
            try {
                Integer recoverState = userMapper.updateUserByUserId(user.getId(), user);
                System.out.println("deleteStudentAccountTest:" + recoverState);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("deleteStudentAccountTest ends");
    }

}
