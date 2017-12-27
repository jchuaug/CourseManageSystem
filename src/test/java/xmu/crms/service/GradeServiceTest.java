package xmu.crms.service;

import static org.junit.Assert.assertNotNull;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import xmu.crms.CourseManageApplication;
import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Location;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.ClassService;

/**
 * 
 * @author yjj
 * @date 2017-12-27
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)
public class GradeServiceTest {
	@Autowired
	private GradeService gradeService;

	@Test
	public void deleteStudentScoreGroupByTopicId() {
		try {
			gradeService.deleteStudentScoreGroupByTopicId(new BigInteger("1"));
			gradeService.deleteStudentScoreGroupByTopicId(null);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Test
	public void getSeminarGroupBySeminarGroupId() {
		try {
			SeminarGroup seminarGroup = gradeService.getSeminarGroupBySeminarGroupId(new BigInteger("1"));
			System.err.println(seminarGroup);
			seminarGroup = gradeService.getSeminarGroupBySeminarGroupId(null);
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (GroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void listSeminarGradeByCourseId() {
		try {
			List<SeminarGroup> seminarGroup = gradeService.listSeminarGradeByCourseId(new BigInteger("3"),new BigInteger("1"));
			System.err.println(seminarGroup);
			seminarGroup = gradeService.listSeminarGradeByCourseId(null,null);
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
	}
	
	@Test
	public void insertGroupGradeByUserId() {
		try {
			gradeService.insertGroupGradeByUserId(new BigInteger("1"),new BigInteger("2"),new BigInteger("1"),new BigInteger("6"));
			gradeService.insertGroupGradeByUserId(null,null,null,null);
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
	}
	
	@Test
	public void updateGroupByGroupId() {
		try {
			gradeService.updateGroupByGroupId(new BigInteger("1"),new BigInteger("2"));
			gradeService.updateGroupByGroupId(null,null);
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (GroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Test
	public void countGroupGradeBySeminarId() {
		try {
			gradeService.countGroupGradeBySeminarId(new BigInteger("1"));
			
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
	}

}
