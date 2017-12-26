package xmu.crms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CourseManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseManageApplication.class, args);
    }

}
