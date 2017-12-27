package xmu.crms.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static xmu.crms.service.EntityTestMethods.testSeminar;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xmu.crms.CourseManageApplication;
import xmu.crms.entity.Seminar;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.mapper.SeminarMapper;

/**
 * @author shin jim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
@Sql(scripts = "classpath:schema.sql")
public class SeminarServiceTest {
    @Autowired
    private SeminarService seminarService;
    @Autowired
    private SeminarMapper seminarMapper;

    @Test
    public void listSeminarByCourseId() {
        try {
            List<Seminar> seminars = seminarService.listSeminarByCourseId(new BigInteger("1"));
            assertNotNull(seminars);
            assertTrue(seminars.size() > 0);

            Seminar seminar = seminars.get(0);
            testSeminar(seminar);
        } catch (IllegalArgumentException | CourseNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteSeminarByCourseId() {
        try {
            BigInteger courseId = BigInteger.valueOf(1);
            List<Seminar> seminars = seminarService.listSeminarByCourseId(courseId);

            assertTrue(seminars.size() > 0);
            seminarService.deleteSeminarByCourseId(courseId);


            seminars = seminarService.listSeminarByCourseId(courseId);
            assertTrue(seminars.size() == 0);

        } catch (IllegalArgumentException | CourseNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSeminarBySeminarId() {
        try {
            Seminar seminar = seminarService.getSeminarBySeminarId(new BigInteger("1"));
            assertNotNull(seminar);
            testSeminar(seminar);
        } catch (SeminarNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateSeminarBySeminarId() {
        BigInteger seminarId = BigInteger.valueOf(1);
        final String testName = new Date().toString();

        Seminar seminar = seminarMapper.getSeminarBySeminarId(seminarId);
        seminar.setName(testName);
        try {
            seminarService.updateSeminarBySeminarId(seminarId, seminar);

            Seminar updatedSeminar = seminarMapper.getSeminarBySeminarId(seminarId);
            assertEquals(testName, updatedSeminar.getName());
        } catch (IllegalArgumentException | SeminarNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void deleteSeminarBySeminarId() {
        BigInteger seminarId = BigInteger.valueOf(1);
        try {
            Seminar seminar = seminarMapper.getSeminarBySeminarId(seminarId);
            assertNotNull(seminar);

            seminarService.deleteSeminarBySeminarId(seminarId);

            seminar = seminarMapper.getSeminarBySeminarId(seminarId);
            assertEquals(null, seminar);
        } catch (IllegalArgumentException | SeminarNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void insertSeminarByCourseId() {
        Seminar seminar = seminarMapper.getSeminarBySeminarId(BigInteger.valueOf(1));
        final String testName = new Date().toString();
        seminar.setId(null);
        seminar.setName(testName);
        try {
            seminarService.insertSeminarByCourseId(new BigInteger("1"), seminar);
            Seminar insertedSeminar = seminarMapper.getSeminarBySeminarId(seminar.getId());
            assertEquals(testName, insertedSeminar.getName());
        } catch (IllegalArgumentException | CourseNotFoundException e) {
            e.printStackTrace();
        }
    }

}
