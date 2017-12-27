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
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.Location;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.FixGroupNotFoundException;
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
public class FixedGroupServiceTest {
	@Autowired
	private FixGroupService fixGroupService;

	@Test
	public void insertFixGroupByClassId() {
		try {
			BigInteger flag=  fixGroupService.insertFixGroupByClassId(new BigInteger("1"),new BigInteger("3"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteFixGroupMemberByFixGroupId() {
		try {
			fixGroupService.deleteFixGroupMemberByFixGroupId(new BigInteger("1"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteFixGroupUserById() {
		try {
			fixGroupService.deleteFixGroupUserById(new BigInteger("2"),new BigInteger("8"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listFixGroupMemberByGroupId() {
		try {
			List<User> students= fixGroupService.listFixGroupMemberByGroupId(new BigInteger("1"));
			System.out.println(students);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void listFixGroupByClassId() {
		try {
			List<FixGroup> fixGroups= fixGroupService.listFixGroupByClassId(new BigInteger("1"));
			System.out.println(fixGroups);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteFixGroupByClassId() {
		try {
			fixGroupService.deleteFixGroupByClassId(new BigInteger("1"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteFixGroupByGroupId() {
		try {
			fixGroupService.deleteFixGroupByGroupId(new BigInteger("1"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateFixGroupByGroupId() {
		FixGroup fixGroup=new FixGroup();
		ClassInfo classInfo=new ClassInfo();
		classInfo.setId(new BigInteger("2"));
		fixGroup.setClassInfo(classInfo);
		User leader=new User();
		leader.setId(new BigInteger("4"));
		fixGroup.setLeader(leader);
		try {
			fixGroupService.updateFixGroupByGroupId(new BigInteger("1"),fixGroup);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void listFixGroupByGroupId() {
		try {
			List<FixGroupMember> list=fixGroupService.listFixGroupByGroupId(new BigInteger("1"));
			System.out.println(list);
			list=fixGroupService.listFixGroupByGroupId(new BigInteger("20"));
			System.out.println(list);
			list=fixGroupService.listFixGroupByGroupId(null);
			System.out.println(list);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertStudentIntoGroup() {
		try {
			BigInteger flag=fixGroupService.insertStudentIntoGroup(new BigInteger("23"),new BigInteger("1"));
			System.out.println(flag);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getFixedGroupById() {
		try {
			FixGroup flag=fixGroupService.getFixedGroupById(new BigInteger("6"),new BigInteger("1"));
			System.out.println(flag);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void fixedGroupToSeminarGroup() {
		
		//等待SeminarGroupService 暂时无法测试
		try {
			fixGroupService.fixedGroupToSeminarGroup(new BigInteger("1"),new BigInteger("1"));
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FixGroupNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SeminarNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
