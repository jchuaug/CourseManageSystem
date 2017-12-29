package xmu.crms.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import xmu.crms.CourseManageApplication;
import xmu.crms.dao.AuthDao;
import xmu.crms.security.JwtUserDetails;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CourseManageApplication.class)

public class AutoDaoTest {
	
	@Autowired
	AuthDao authDao;
	
	
	@Test
	public void getUserByPhoneTest() {
		JwtUserDetails userDetails=authDao.getUserByPhone("15720335801");
		
		System.out.println(userDetails);
		
	}

}
