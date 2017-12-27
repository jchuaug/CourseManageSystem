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
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
@Sql(scripts = "classpath:schema.sql")
public class UserServiceTest {
    @Autowired
    UserService userService;

    /**
     * 测试insertAttendanceById
     */
    @Test
    public void insertAttendanceByIdTest() {
        BigInteger classId = BigInteger.valueOf(1);
        BigInteger seminarId = BigInteger.valueOf(1);
        BigInteger userId = BigInteger.valueOf(11);
        double longitude = 10.1;
        double latitude = 12.1;
        try {
            userService.insertAttendanceById(classId, seminarId, userId, longitude, latitude);
        } catch (IllegalArgumentException | ClassesNotFoundException | SeminarNotFoundException
                | UserNotFoundException e) {

            e.printStackTrace();
        }
    }

    @Test
    public void listAttendanceByIdTest() {
        BigInteger classId = BigInteger.valueOf(2);
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
    public void getUserByUserIdTest() {
    }

    @Test
    public void listUserIdByUserNameTest() {
    }

    @Test
    public void updateUserByUserIdTest() {
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
