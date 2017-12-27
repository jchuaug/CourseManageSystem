package xmu.crms.web.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RedirectController {
	@RequestMapping("/")
	public String indexPage() {
		return "common/login";
	}
	
	@RequestMapping("/teacher/home")
	@RequiresRoles("teacher")
	public String teacherHome() {
		return "teacher/teacher_home";
	}
	
	@RequestMapping("/student/home")
	@RequiresRoles("student")
	public String studentHome() {
		return "student/student_home";
	}

}