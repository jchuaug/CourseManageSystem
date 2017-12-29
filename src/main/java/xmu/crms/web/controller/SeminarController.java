package xmu.crms.web.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;

import xmu.crms.entity.Attendance;
import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.Location;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupMember;
import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.entity.Topic;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.FixGroupNotFoundException;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.utils.JWTUtil;
import xmu.crms.service.ClassService;
import xmu.crms.service.CourseService;
import xmu.crms.service.FixGroupService;
import xmu.crms.service.GradeService;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.SeminarService;
import xmu.crms.service.TopicService;
import xmu.crms.service.UserService;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.AttendanceRequestVO;
import xmu.crms.web.VO.AttendanceResponseVO;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.MySeminarResponseVO;
import xmu.crms.web.VO.SeminarDetailResponseVO;
import xmu.crms.web.VO.SeminarResponseVO;
import xmu.crms.web.VO.TopicResponseVO;
import xmu.crms.web.VO.UserRequestVO;
import xmu.crms.web.VO.UserResponseVO;

@RestController
@RequestMapping("/seminar")
public class SeminarController {

	@Autowired
	private SeminarService seminarService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private FixGroupService fixGroupService;

	@Autowired
	private SeminarGroupService seminarGroupService;

	@Autowired
	private UserService userService;

	@Autowired
	private TopicService topicService;

	@Autowired
	private ClassService classService;

	@Autowired
	private GradeService gradeService;

	private final String TEACHER = "teacher";
	private final String STUDENT = "student";

	@GetMapping("/{seminarId}")
	public ResponseEntity<SeminarResponseVO> getSeminarByseminarId(@PathVariable("seminarId") BigInteger seminarId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		String username = JWTUtil.getUsername(token);

		Seminar seminar1 = null;
		SeminarResponseVO seminarResponseVO = null;
		try {
			seminar1 = seminarService.getSeminarBySeminarId(seminarId);
			List<Topic> topics = topicService.listTopicBySeminarId(seminarId);
			seminarResponseVO = ModelUtils.SeminarInfoToSeminarResponseVO(seminar1, topics);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<SeminarResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<SeminarResponseVO>(seminarResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{seminarId}")
	public ResponseEntity<String> updateSeminar(@PathVariable("seminarId") BigInteger seminarId,
			@RequestBody SeminarResponseVO seminar, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		String username = JWTUtil.getUsername(token);
		if (type == 0) {
			return new ResponseEntity<String>("权限不足", new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		try {
			Seminar forSeminar = seminarService.getSeminarBySeminarId(seminarId);
			if (!(forSeminar.getCourse().getTeacher().getId().equals(userId))) {
				return new ResponseEntity<String>("不是创建该讨论课的教师，权限不足", new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			Seminar seminar2 = ModelUtils.SeminarResponseVOToSeminar(seminar, forSeminar);
			seminarService.updateSeminarBySeminarId(seminarId, seminar2);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("没有找到讨论课", new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{seminarId}")
	public ResponseEntity<String> deleteSeminar(@PathVariable("seminarId") BigInteger seminarId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		String username = JWTUtil.getUsername(token);
		if (type == 0) {
			return new ResponseEntity<String>("权限不足", new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		try {
			Seminar seminarInfo1 = seminarService.getSeminarBySeminarId(seminarId);
			User teacher = seminarInfo1.getCourse().getTeacher();
			if (!(teacher.getId().equals(userId))) {
				return new ResponseEntity<String>("不是创建该讨论课的教师，权限不足", new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			seminarService.deleteSeminarBySeminarId(seminarId);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("没有找到讨论课", new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	// 未完成
	@GetMapping("/{seminarId}/my")
	public ResponseEntity<MySeminarResponseVO> getMySeminar(@PathVariable("seminarId") BigInteger seminarId,
			@RequestHeader HttpHeaders headers) {

		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		String username = JWTUtil.getUsername(token);
		if (type == 1) {
			return new ResponseEntity<MySeminarResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		MySeminarResponseVO mySeminarResponseVO = null;
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(seminarId);
			SeminarGroup group = seminarGroupService.getSeminarGroupById(seminarId, userId);
			Boolean isLeader = group.getLeader().getId().equals(userId);
			Boolean areTopicsSeletced = null;
			List<SeminarGroupTopic> topics = topicService.listSeminarGroupTopicByGroupId(group.getId());
			if (topics == null) {
				areTopicsSeletced = false;
			} else if (topics.isEmpty()) {
				areTopicsSeletced = false;
			} else {
				areTopicsSeletced = true;
			}
			mySeminarResponseVO = ModelUtils.SeminarToMySeminarResponseVO(seminar, isLeader, areTopicsSeletced);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<MySeminarResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (SeminarNotFoundException | GroupNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<MySeminarResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MySeminarResponseVO>(mySeminarResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{seminarId}/detail")
	public ResponseEntity<SeminarDetailResponseVO> getSeminarDetail(@PathVariable("seminarId") BigInteger seminarId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		String username = JWTUtil.getUsername(token);
		SeminarDetailResponseVO seminarDetailResponseVO = null;
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(seminarId);
			seminarDetailResponseVO = ModelUtils.SeminarToSeminarDetailResponseVO(seminar);
			System.err.println(seminar);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<SeminarDetailResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<SeminarDetailResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SeminarDetailResponseVO>(seminarDetailResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{seminarId}/topic")
	public ResponseEntity<List<TopicResponseVO>> getSeminarTopicBySeminarId(
			@PathVariable("seminarId") BigInteger seminarId, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		String username = JWTUtil.getUsername(token);
		List<TopicResponseVO> topicResponseVOs = new ArrayList<>();
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(seminarId);
			List<Topic> topics = topicService.listTopicBySeminarId(seminarId);
			for (Topic topic : topics) {
				topicResponseVOs.add(ModelUtils.TopicToTopicResponseVO(topic, null));
			}
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<List<TopicResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<TopicResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<TopicResponseVO>>(topicResponseVOs, new HttpHeaders(), HttpStatus.OK);
	}

	// wait TopicSeivce add serial 的相关操作
	@PostMapping("/{seminarId}/topic")
	public ResponseEntity<BigInteger> insertSeminarTopicBySeminarId(@PathVariable("seminarId") BigInteger seminarId,
			@RequestBody TopicResponseVO topic, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		if (type == 0) {
			return new ResponseEntity<BigInteger>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		String username = JWTUtil.getUsername(token);
		BigInteger topicId = null;
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(seminarId);
			if (!seminar.getCourse().getTeacher().getId().equals(userId)) {
				return new ResponseEntity<BigInteger>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			Topic topic2 = ModelUtils.TopicResponseVOToTopic(topic);
			topic2.setSeminar(seminar);
			topicId = topicService.insertTopicBySeminarId(seminarId, topic2);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<BigInteger>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<BigInteger>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<BigInteger>(topicId, new HttpHeaders(), HttpStatus.CREATED);
	}

	@GetMapping("/{seminarId}/group")
	public ResponseEntity<List<GroupResponseVO>> getGroup(@PathVariable("seminarId") BigInteger seminarId,
			@RequestParam("classId") String classId, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}

		List<GroupResponseVO> groupResponseVOs = new ArrayList<>();
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(seminarId);
			List<SeminarGroup> groups = seminarGroupService.listSeminarGroupBySeminarId(seminarId);

			for (SeminarGroup seminarGroup : groups) {
				System.err.println(seminarGroup);
				if (new BigInteger(classId.toString()).equals(seminarGroup.getClassInfo().getId())) {
					List<SeminarGroupTopic> topics = topicService.listSeminarGroupTopicByGroupId(seminarGroup.getId());
					groupResponseVOs.add(ModelUtils.SeminarGroupToGroupResponseVO(seminarGroup, topics, null));
				}
			}

		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<GroupResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<GroupResponseVO>>(groupResponseVOs, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{seminarId}/group/my")
	public ResponseEntity<GroupResponseVO> getMyGroup(@PathVariable("seminarId") BigInteger seminarId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		if (type == 1) {
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		GroupResponseVO groupResponseVO = null;
		try {
			Seminar seminar = seminarService.getSeminarBySeminarId(seminarId);
			SeminarGroup group = seminarGroupService.getSeminarGroupById(seminarId, userId);

			List<SeminarGroupTopic> topics = topicService.listSeminarGroupTopicByGroupId(group.getId());
			List<User> members = seminarGroupService.listSeminarGroupMemberByGroupId(group.getId());
			groupResponseVO = ModelUtils.SeminarGroupToGroupResponseVO(group, topics, members);

		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (GroupNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<GroupResponseVO>(groupResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{seminarId}/class/{classId}/attendance")
	public ResponseEntity<AttendanceResponseVO> getAttendanceBySeminarIdAndClassId(
			@PathVariable("seminarId") BigInteger seminarId, @PathVariable("classId") BigInteger classId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		AttendanceResponseVO attendanceResponseVO = null;
		try {
			Integer numPresent = userService.listPresentStudent(seminarId, classId).size();
			Integer numStudent = userService.listUserByClassId(classId, "", "").size();
			Location location = classService.getCallStatusById(classId, seminarId);
			String status = location.getStatus() == 1 ? "calling" : "notcalling";
			String group = " ";
			attendanceResponseVO = new AttendanceResponseVO(numPresent, numStudent, status, group);

		} catch (SeminarNotFoundException | ClassesNotFoundException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<AttendanceResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<AttendanceResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<AttendanceResponseVO>(attendanceResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@GetMapping("/{seminarId}/class/{classId}/attendance/present")
	public ResponseEntity<List<UserResponseVO>> getPresentStudentBySeminarIdAndClassId(
			@PathVariable("seminarId") BigInteger seminarId, @PathVariable("classId") BigInteger classId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		List<UserResponseVO> userResponseVOs = new ArrayList<>();
	
		try {
			List<User> students = userService.listPresentStudent(seminarId, classId);
			for (User user : students) {
				userResponseVOs.add(ModelUtils.UserToUserResponseVO(user));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (ClassesNotFoundException|SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} 
		

		return new ResponseEntity<List<UserResponseVO>>(userResponseVOs, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{seminarId}/class/{classId}/attendance/late")
	public ResponseEntity<List<UserResponseVO>> getLateStudentBySeminarIdAndClassId(
			@PathVariable("seminarId") BigInteger seminarId, @PathVariable("classId") BigInteger classId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		List<UserResponseVO> userResponseVOs = new ArrayList<>();
	
		try {
			List<User> students = userService.listLateStudent(seminarId, classId);
			for (User user : students) {
				userResponseVOs.add(ModelUtils.UserToUserResponseVO(user));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (ClassesNotFoundException|SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} 
		

		return new ResponseEntity<List<UserResponseVO>>(userResponseVOs, new HttpHeaders(), HttpStatus.OK);
	}
	
	@GetMapping("/{seminarId}/class/{classId}/attendance/absent")
	public ResponseEntity<List<UserResponseVO>> getAbsentStudentBySeminarIdAndClassId(
			@PathVariable("seminarId") BigInteger seminarId, @PathVariable("classId") BigInteger classId,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		List<UserResponseVO> userResponseVOs = new ArrayList<>();
	
		try {
			List<User> students = userService.listAbsenceStudent(seminarId, classId);
			for (User user : students) {
				userResponseVOs.add(ModelUtils.UserToUserResponseVO(user));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (ClassesNotFoundException|SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} 
		

		return new ResponseEntity<List<UserResponseVO>>(userResponseVOs, new HttpHeaders(), HttpStatus.OK);
	}
	
	@PutMapping("/{seminarId}/class/{classId}/attendance/{studentId}")
	public ResponseEntity<AttendanceResponseVO> insertAttendanceBySeminarIdAndClassId(
			@PathVariable("seminarId") BigInteger seminarId, @PathVariable("classId") BigInteger classId,
			@PathVariable("studentId") BigInteger studentId,@RequestBody AttendanceRequestVO attendance,
			@RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (TEACHER.equals(typeString)) {
			type = 1;
		} else if (STUDENT.equals(typeString)) {
			type = 0;
		}
		if (!userId.equals(studentId)) {
			return new ResponseEntity<AttendanceResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		AttendanceResponseVO attendanceResponseVO=new AttendanceResponseVO();
	
		try {
			BigInteger id =userService.insertAttendanceById( classId,  seminarId,
                     userId,  attendance.getLongitude(),  attendance.getLatitude());
			
		}  catch (UserNotFoundException|ClassesNotFoundException|SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<AttendanceResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<AttendanceResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		

		return new ResponseEntity<AttendanceResponseVO>(attendanceResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

}
