package xmu.crms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * 
* <p>Title: CourseManageApplication.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
@EnableScheduling
@MapperScan(basePackages = { "xmu.crms.mapper" })
@SpringBootApplication
public class CourseManageApplication {
	public static void main(String[] args) {
		SpringApplication.run(CourseManageApplication.class, args);
	}

}
