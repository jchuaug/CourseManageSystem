package xmu.crms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = {"xmu.crms.mapper","xmu.crms.dao"})

public class CourseManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseManageApplication.class, args);
    }

}
