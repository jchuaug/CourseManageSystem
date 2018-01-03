package xmu.crms.web.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @author JackeyHuang
 *
 */
@Controller
public class RedirectController {
	/**
	 * login page
	 * @return
	 */
	@RequestMapping("/")
	public String indexPage() {
		return "common/login";
	}
	@RequestMapping("/register")
	public String registerPage() {
		return "common/register";
	}
	/**
	 * student page
	 * @return
	 */
	@RequiresRoles("student")
	@RequestMapping("/student/home")
	public String studentHome() {
		return "student/student_home";
	}
	@RequestMapping("/student/personInfo")
	public String studentInfo() {
		return "student/student_info";
	}
	@RequestMapping("/student/personInfo/alter")
	public String studentInfoAlter() {
		return "student/student_info_update";
	}
	@RequestMapping("/student/courses")
	public String studentCourse() {
		return "student/student_home";
	}
	@RequestMapping("/student/course/select")
	public String studentCourseSelect() {
		return "student/student_choose_course";
	}
	/**
	 * teacher page
	 * @return
	 */
	@RequiresRoles("teacher")
	@RequestMapping("/teacher/home")
	public String teacherHome() {
		return "teacher/teacher_home";
	}
	@RequestMapping("/teacher/personInfo")
	public String teacherInfo() {
		return "teacher/teacher_info";
	}
	@RequestMapping("/teacher/personInfo/alter")
	public String teacherInfoAlter() {
		return "teacher/teacher_info_update";
	}
	@RequestMapping("/teacher/courses")
	public String teacherCourse() {
		return "teacher/teacher_home";
	}
	@RequestMapping("/teacher/courses/create")
	public String teacherCourseCreate() {
		return "teacher/teacher_create_course";
	}
	
	
	

}
