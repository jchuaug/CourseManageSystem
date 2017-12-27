package xmu.crms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import xmu.crms.entity.Course;
import xmu.crms.entity.User;
import xmu.crms.service.impl.CourseServiceImpl;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * @author caistrong
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {
    @Autowired
    CourseServiceImpl courseService;

    @Test
    public void listCourseByUserId() throws Exception {
        BigInteger userId = new BigInteger("1");
        List<Course> courseList = courseService.listCourseByUserId(userId);
        assertNotNull(courseList);
    }

    @Test
    public void insertCourseByUserId() throws Exception{
        BigInteger userId = new BigInteger("1");
        Course course = new Course();
        course.setName("OOAD2");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2017-09-10");
        Date endDate = format.parse("2018-02-01");
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        User teacher = new User();
        teacher.setId(userId);
        course.setTeacher(teacher);
        course.setDescription("xxx");
        course.setReportPercentage(50);
        course.setPresentationPercentage(50);
        course.setFivePointPercentage(10);
        course.setFourPointPercentage(30);
        course.setThreePointPercentage(60);
        BigInteger t = courseService.insertCourseByUserId(userId,course);

        assertNotNull(t);
    }

    @Test
    public void getCourseByCourseId() throws Exception {
        BigInteger courseId = new BigInteger("1");
        Course course = courseService.getCourseByCourseId(courseId);
        assertNotNull(course);
    }

    @Test
    public void updateCourseByCourseId() throws Exception {
        BigInteger courseId = new BigInteger("1");
        BigInteger userId = new BigInteger("1");
        Course course = new Course();
        course.setName("OOAD4");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        course.setId(courseId);
        Date startDate = format.parse("2017-09-10");
        Date endDate = format.parse("2018-02-01");
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        User teacher = new User();
        teacher.setId(userId);
        course.setTeacher(teacher);
        course.setDescription("xxx");
        course.setReportPercentage(50);
        course.setPresentationPercentage(50);
        course.setFivePointPercentage(10);
        course.setFourPointPercentage(30);
        course.setThreePointPercentage(60);
        courseService.updateCourseByCourseId(courseId,course);
        //名称是否更新成功
        assertEquals("OOAD4",courseService.getCourseByCourseId(courseId).getName());
    }

    @Test
    public void deleteCourseByCourseId() throws Exception {
        BigInteger courseId = new BigInteger("2");
        courseService.deleteCourseByCourseId(courseId);
        assertEquals(null,courseService.getCourseByCourseId(courseId));
    }

    @Test
    public void listCourseByCourseName() throws Exception {
        String courseName = "课程1";
        List<Course> courseList = courseService.listCourseByCourseName(courseName);
        assertNotNull(courseList);
    }
//    @Test
//    public void listClassByCourseName() throws Exception {
//        String courseName = "课程1";
//        List<ClassInfo> classList = courseService.listClassByCourseName(courseName);
//        assertNotNull(classList);
//    }

}
