package xmu.crms.service;

import static org.junit.Assert.assertNotNull;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xmu.crms.CourseManageApplication;
import xmu.crms.entity.Seminar;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;

/**
 * URL-pattern:prefix="/class"
 * 
 * @author ZDD、Huhui
 * @date 2017-12-04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
public class SeminarServiceTest {
	@Autowired
	private SeminarService seminarService;

	@Test
	public void listSeminarByCourseId() {
		try {
			List<Seminar> seminars = seminarService.listSeminarByCourseId(new BigInteger("1"));
			assertNotNull(seminars);

			seminars = seminarService.listSeminarByCourseId(new BigInteger("89"));
			seminars = seminarService.listSeminarByCourseId(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteSeminarByCourseId() {
		try {
			seminarService.deleteSeminarByCourseId(new BigInteger("1"));

			seminarService.deleteSeminarByCourseId(new BigInteger("89"));
			seminarService.deleteSeminarByCourseId(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getSeminarBySeminarId() {
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(new BigInteger("1"));
			assertNotNull(seminar);
			System.out.println(seminar);
			seminar = seminarService.getSeminarBySeminarId(new BigInteger("89"));
			seminar = seminarService.getSeminarBySeminarId(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateSeminarBySeminarId() {
		Seminar seminar = new Seminar();
		seminar.setName("Myseminar");
		try {

			seminarService.updateSeminarBySeminarId(new BigInteger("1"), seminar);

			seminarService.updateSeminarBySeminarId(new BigInteger("89"), seminar);
			seminarService.updateSeminarBySeminarId(null, seminar);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteSeminarBySeminarId() {
		Seminar seminar = new Seminar();
		seminar.setName("Myseminar");
		try {
			seminarService.deleteSeminarBySeminarId(new BigInteger("1"));

			seminarService.deleteSeminarBySeminarId(new BigInteger("89"));
			seminarService.deleteSeminarBySeminarId(null);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void insertSeminarByCourseId() {
		Seminar seminar = new Seminar();
		seminar.setName("Myseminar");
		seminar.setStartTime(new Date());
		seminar.setEndTime(new Date());
		try {

			BigInteger flag = seminarService.insertSeminarByCourseId(new BigInteger("1"), seminar);
			assertNotNull(flag);
			System.out.println(flag);
			flag = seminarService.insertSeminarByCourseId(new BigInteger("89"), seminar);
			flag = seminarService.insertSeminarByCourseId(null, seminar);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
