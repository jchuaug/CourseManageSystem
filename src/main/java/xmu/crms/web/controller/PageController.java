package xmu.crms.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author yjj
 *
 */

@Controller
public class PageController {

	@RequestMapping("/test")
	public String test() {
		return "test";
	}

	
	@RequestMapping("/studentToCourse/{courseId}/class/{classId}")
	public String studentToCourse() {
		return "student/course";
	}

	@RequestMapping("/teacherToCourse/{courseId}")
	public String teacherToCourse() {
		return "teacher/course";
	}

	@RequestMapping(value = "/add_seminar/{courseId}")
	public String addSeminar() {
		return "teacher/add_seminar";
	}

	@RequestMapping(value = "/add_class/{courseId}")
	public String addClass() {
		return "teacher/add_class";
	}

	@RequestMapping(value = "/course/{courseId}/toSeminarFixed/{seminarId}")
	public String toSeminarFixed() {
		return "student/seminar_fixed_group";
	}

	@RequestMapping(value = "/course/{courseId}/toSeminarRandom/{seminarId}")
	public String toSeminarRandom() {
		return "student/seminar_random_group";
	}

	


	@RequestMapping(value = "/course/{courseId}/toFixedGroup/{classId}")
	public String getMyGroupBySeminarId() {
		return "student/fixed_group";
	}

	@RequestMapping(value = "/course/{courseId}/toClass/{classId}")
	public String toClass() {
		return "teacher/class";
	}

	@RequestMapping(value = "/course/{courseId}/toSeminar/{seminarId}")
	public String toSeminar() {
		return "teacher/seminar";
	}

	

	@RequestMapping(value = "/course/{courseId}/updateClass/{classId}")
	public String updateClass() {
		return "teacher/update_class";
	}

	@RequestMapping(value = "/course/{courseId}/deleteClass/{classId}")
	public String deleteClass() {
		return "teacher/delete_class";
	}

	

	@RequestMapping(value = "/course/{courseId}/updateSeminar/{seminarId}")
	public String updateSeminar() {
		return "teacher/update_seminar";
	}

	@RequestMapping(value = "/course/{courseId}/gradeSeminar/{seminarId}")
	public String gradeSeminar() {
		return "teacher/grade";
	}

	@RequestMapping(value = "/course/{courseId}/seminar/{seminarId}/addTopic")
	public String addTopic() {
		return "teacher/add_topic";
	}

	

	@RequestMapping(value = "/course/{courseId}/seminar/{seminarId}/topic/{topicId}")
	public String toTopic() {
		return "teacher/topic";
	}

	@RequestMapping(value = "/course/{courseId}/updateTopic/{topicId}")
	public String updateTopic() {
		return "teacher/update_topic";
	}

	

	@RequestMapping(value = "/course/{courseId}/topicFixed/{topicId}")
	public String studentToTopicFixed() {
		return "student/topic_fixed";
	}

	@RequestMapping(value = "/course/{courseId}/topicRandom/{topicId}")
	public String studentToTopicRandom() {
		return "student/topic_Random";
	}

	@RequestMapping(value = "/course/{courseId}/toGrade")
	public String toGrade() {
		return "student/grade";
	}
	
	@RequestMapping(value = "/seminar/{seminarId}/group/{gtoupId}/report")
	public String toReport() {
		return "teacher/report";
	}

}
