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

import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.Location;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.SeminarGroupTopic;
import xmu.crms.entity.Topic;
import xmu.crms.entity.User;
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
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.SeminarService;
import xmu.crms.service.TopicService;
import xmu.crms.service.UserService;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.MySeminarResponseVO;
import xmu.crms.web.VO.SeminarDetailResponseVO;
import xmu.crms.web.VO.SeminarResponseVO;
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
	
	

	

	@GetMapping("/{seminarId}")
	public ResponseEntity<SeminarResponseVO> getseminarByseminarId(@PathVariable("seminarId") BigInteger seminarId,
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
			List<Topic> topics=topicService.listTopicBySeminarId(seminarId);
			seminarResponseVO = ModelUtils.SeminarInfoToSeminarResponseVO(seminar1, topics);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<SeminarResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<SeminarResponseVO>(seminarResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{seminarId}")
	public ResponseEntity<String> updateseminar(@PathVariable("seminarId") Integer seminarId,
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
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		try {
			Seminar seminar2= ModelUtils.SeminarResponseVOToSeminar(seminar);
			seminarService.updateSeminarBySeminarId(seminar2.getId(), seminar2);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
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
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		try {
			Seminar seminarInfo1 = seminarService.getSeminarBySeminarId(seminarId);
			User teacher = courseService.getCourseByCourseId(seminarInfo1.getCourse().getId()).getTeacher();
			if (!(teacher.getId().equals(userId))) {
				return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			seminarService.deleteSeminarBySeminarId(seminarId);
		} catch (SeminarNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (CourseNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	// 未完成
	@GetMapping("/{seminarId}/my")
	public ResponseEntity<MySeminarResponseVO> getMySeminar(@PathVariable("seminarId") BigInteger seminarId,@RequestHeader HttpHeaders headers) {

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
		MySeminarResponseVO mySeminarResponseVO=null;
		try {
			Seminar seminar=seminarService.getSeminarBySeminarId(seminarId);
			SeminarGroup group=seminarGroupService.getSeminarGroupById(seminarId, userId);
			Boolean isLeader=group.getLeader().getId().equals(userId);
			Boolean areTopicsSeletced=null;
			List<SeminarGroupTopic> topics= topicService.listSeminarGroupTopicByGroupId(group.getId());
			if (topics==null) {
				areTopicsSeletced=false;
			}else if (topics.isEmpty()) {
				areTopicsSeletced=false;
			}else {
				areTopicsSeletced=true;
			}
			 mySeminarResponseVO=ModelUtils.SeminarToMySeminarResponseVO(seminar,isLeader,areTopicsSeletced);
		}  catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<MySeminarResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (SeminarNotFoundException|GroupNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<MySeminarResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}  
		return new ResponseEntity<MySeminarResponseVO>(mySeminarResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	// 未完成
	@GetMapping("/{seminarId}/detail")
	public ResponseEntity<SeminarDetailResponseVO> getSeminarDetail(@PathVariable("seminarId") BigInteger seminarId,@RequestHeader HttpHeaders headers) {
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
		SeminarDetailResponseVO seminarDetailResponseVO=null;
		try {
			Seminar seminar=seminarService.getSeminarBySeminarId(seminarId);
			User teacher=userService.getUserByUserId(courseService.getCourseByCourseId(seminar.getCourse().getId()).getTeacher().getId());
			seminarDetailResponseVO=ModelUtils.SeminarToSeminarDetailResponseVO(seminar,teacher);
		} catch (SeminarNotFoundException|CourseNotFoundException|UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<SeminarDetailResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<SeminarDetailResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<SeminarDetailResponseVO>(seminarDetailResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@DeleteMapping("/{seminarId}/student/{studentId}")
	public ResponseEntity<String> deleteSelectseminar(@PathVariable("seminarId") BigInteger seminarId,
			@PathVariable("studentId") BigInteger studentId, @RequestHeader HttpHeaders headers) {
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
		if (!userId.equals(studentId)) {
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{seminarId}/seminargroup")
	public ResponseEntity<GroupResponseVO> getseminarGroup(@PathVariable("seminarId") Integer seminarId,
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
		if (type == 1) {
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		FixGroup fixGroup = null;
		GroupResponseVO groupResponseVO = null;
		BigInteger studentId = userId;

		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(studentId, seminarId);
		 * List<FixGroupMember> students =
		 * FixGroupService.listFixGroupByGroupId(fixGroup.getId()); groupResponseVO =
		 * ModelUtils.FixGroupToGroupResponseVO(fixGroup, students); } catch
		 * (seminaresNotFoundException e) { e.printStackTrace(); } catch
		 * (UserNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalArgumentException e) {
		 * e.printStackTrace(); return new ResponseEntity<GroupResponseVO>(null, new
		 * HttpHeaders(), HttpStatus.BAD_REQUEST); }
		 */

		return new ResponseEntity<GroupResponseVO>(groupResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	// 未完成
	@PutMapping("/{seminarId}/seminargroup/resign")
	public ResponseEntity<String> groupLeaderResign(@PathVariable("seminarId") Integer seminarId,
			@RequestParam("id") BigInteger studentId, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		if (!userId.equals(studentId)) {
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		FixGroup fixGroup = null;

		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(studentId, seminarId);
		 * fixGroup.setLeader(new User()); if
		 * (!(fixGroup.getLeader().getId().equals(studentId))) { return new
		 * ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN); }
		 * FixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup); } catch
		 * (IllegalArgumentException|UserNotFoundException e) { e.printStackTrace();
		 * return new ResponseEntity<String>("成功", new HttpHeaders(),
		 * HttpStatus.BAD_REQUEST); } catch (seminaresNotFoundException e) {
		 * e.printStackTrace(); }
		 */

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{seminarId}/seminargroup/assign")
	public ResponseEntity<String> groupLeaderAssign(@PathVariable("seminarId") Integer seminarId,
			@RequestParam("id") BigInteger studentId, @RequestHeader HttpHeaders headers) {

		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}
		if (!userId.equals(studentId)) {
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		FixGroup fixGroup = null;
		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(studentId, seminarId); if
		 * (fixGroup.getLeader() != null) { return new ResponseEntity<String>("已经有组长了",
		 * new HttpHeaders(), HttpStatus.CONFLICT); } List<FixGroupMember> members =
		 * FixGroupService.listFixGroupByGroupId(fixGroup.getId()); boolean flag =
		 * false; for (FixGroupMember fixGroupMember : members) { if
		 * (fixGroupMember.getId().equals(studentId)) { flag = true; } } if (flag ==
		 * false) { return new ResponseEntity<String>("权限不足（不是该小组的成员）", new
		 * HttpHeaders(), HttpStatus.FORBIDDEN); } User leader =
		 * userService.getUserByUserId(studentId); fixGroup.setLeader(leader);
		 * FixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup); } catch
		 * (UserNotFoundException|IllegalArgumentException|seminaresNotFoundException e) {
		 * // TODO Auto-generatedcatch block e.printStackTrace(); return new
		 * ResponseEntity<String>("错误的ID格式、成为组长的学生不存在", new HttpHeaders(),
		 * HttpStatus.BAD_REQUEST); }
		 */

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{seminarId}/seminargroup/add")
	public ResponseEntity<String> addStudentToGroup(@RequestParam("groupId") Integer groupId,
			@RequestParam("id") Integer studentId, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}

		FixGroup fixGroup = null;

		/*
		 * try { List<FixGroupMember> members =
		 * FixGroupService.listFixGroupByGroupId(fixGroup.getId()); boolean flag =
		 * false; for (FixGroupMember fixGroupMember : members) { if
		 * (fixGroupMember.getId().equals(userId)) { flag = true; } } if (flag == false)
		 * { return new ResponseEntity<String>("权限不足（不是该小组的成员）", new HttpHeaders(),
		 * HttpStatus.FORBIDDEN); }
		 * 
		 * BigInteger flag = FixGroupService.insertStudentIntoGroup(studentId, groupId);
		 * } catch (UserNotFoundException | FixGroupNotFoundException |
		 * IllegalArgumentException e) { e.printStackTrace(); return new
		 * ResponseEntity<String>("错误的ID格式、成为组长的学生不存在", new HttpHeaders(),
		 * HttpStatus.BAD_REQUEST); } catch (InvalidOperationException e) {
		 * e.printStackTrace(); return new ResponseEntity<String>("待添加学生已经在小组里了", new
		 * HttpHeaders(), HttpStatus.CONFLICT); }
		 */

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{seminarId}/seminargroup/remove")
	public ResponseEntity<String> removeStudentFromGroup(@RequestParam("groupId") Integer groupId,
			@RequestParam("id") Integer studentId, @RequestHeader HttpHeaders headers) {
		String token = headers.get("Authorization").get(0);
		BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
		String typeString = JWTUtil.getUserType(token);
		Integer type = null;
		if (typeString.equals("teacher")) {
			type = 1;
		} else if (typeString.equals("student")) {
			type = 0;
		}

		FixGroup fixGroup = null;

		/*try {
			List<FixGroupMember> members = FixGroupService.listFixGroupByGroupId(fixGroup.getId());
			boolean flag = false;
			for (FixGroupMember fixGroupMember : members) {
				if (fixGroupMember.getId().equals(userId)) {
					flag = true;
				}
			}
			if (flag == false) {
				return new ResponseEntity<String>("权限不足（不是该小组的成员/组长）", new HttpHeaders(), HttpStatus.FORBIDDEN);
			}

			BigInteger flag = FixGroupService.deleteFixGroupUserById(groupId, studentId);
		} catch (UserNotFoundException | FixGroupNotFoundException | IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("错误的ID格式、成为组长的学生不存在", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (InvalidOperationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("待添加学生已经在小组里了", new HttpHeaders(), HttpStatus.CONFLICT);
		}*/

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

}
