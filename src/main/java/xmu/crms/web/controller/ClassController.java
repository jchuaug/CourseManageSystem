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

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.SeminarGroup;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.security.JWTUtil;
import xmu.crms.service.ClassService;
import xmu.crms.service.CourseService;
import xmu.crms.service.UserService;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.ClassRequestVO;
import xmu.crms.web.VO.ClassResponseVO;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.UserRequestVO;
import xmu.crms.web.VO.UserResponseVO;

@RestController
@RequestMapping("/class")
public class ClassController {

	@Autowired
	private ClassService classService;
	
	/*@Autowired
	private CourseService courseService;*/

	// @Autowired
	// private CourseService courseService;

	@Autowired
	private UserService userService;

	// 未完成
	@GetMapping("/")
	public List<ClassResponseVO> getAllClass(@RequestParam String courseName, @RequestParam String courseTeacher) {
		List<ClassResponseVO> classVOs = new ArrayList<>();
		List<ClassInfo> classInfos = null;
		try {
			classInfos = classService.listClassByName(courseName, courseTeacher);
			for (ClassInfo classInfo : classInfos) {
				/* Course course=courseService.getCourseByCourseId(classInfo.getCourseId()); */
				User teacher = userService.getUserByUserId(null);
				Integer numStudent = classService.getNumStudentByClassId(classInfo.getId());
				classVOs.add(ModelUtils.ClassInfoToClassResponseVO(classInfo, teacher, numStudent));
			}
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return classVOs;
	}

	@GetMapping("/{classId}")
	public ResponseEntity<ClassResponseVO> getClassByClassId(@PathVariable("classId") BigInteger classId,
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

		ClassInfo class1 = null;
		ClassResponseVO classResponseVO = null;
		try {
			class1 = classService.getClassByClassId(classId);
			Integer numStudent = classService.getNumStudentByClassId(class1.getId());
			User teacher = new User();
			classResponseVO = ModelUtils.ClassInfoToClassResponseVO(class1, teacher, numStudent);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<ClassResponseVO>(classResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}")
	public ResponseEntity<String> updateClass(@PathVariable("classId") Integer classId,
			@RequestBody ClassRequestVO classInfo,@RequestHeader HttpHeaders headers) {
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
		if (type==0) {
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		BigInteger classId2=new BigInteger(classId.toString());
		try {
			ClassInfo classInfo1=classService.getClassByClassId(classId2);
			/*User teacher=courseService.getCourseByCourseId(classInfo1.getId()).getTeacher();
			if (!(teacher.getId().equals(userId))) {
				return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
			}*/
			ClassInfo classInfo2 = ModelUtils.ClassRequestVOToClassInfo(classInfo);
			classService.updateClassByClassId(classId2, classInfo2);
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/* catch (CourseNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// 返回状态204
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{classId}")
	public ResponseEntity<String> deleteClass(@PathVariable("classId") BigInteger classId) {
		try {
			classService.deleteClassByClassId(classId);
		} catch (ClassesNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	// 未完成
	@GetMapping("/{classId}/student")
	public ResponseEntity<List<UserResponseVO>> getClassByClassId(@PathVariable("classId") BigInteger classId,
			@RequestParam("numBeginWith") String numBeginWith, @RequestParam("nameBeginWith") String nameBeginWith) {
		List<User> students = new ArrayList<>();
		List<UserResponseVO> studentVOs = new ArrayList<>();
		try {
			students = userService.listUserByClassId(classId, numBeginWith, nameBeginWith);
			for (User student : students) {
				UserResponseVO userResponseVO = ModelUtils.UserToUserResponseVO(student);
				studentVOs.add(userResponseVO);
			}
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
			// 返回404
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<List<UserResponseVO>>(studentVOs, new HttpHeaders(), HttpStatus.OK);
	}

	// 未完成
	@PostMapping("/{classId}/student")
	public ResponseEntity<String> selectClass(@PathVariable("classId") BigInteger classId) {
		try {
			BigInteger flag = classService.insertCourseSelectionById(null, classId);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("", new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping("/{classId}/student/{studentId}")
	public ResponseEntity<String> deleteSelectClass(@PathVariable("classId") BigInteger classId,
			@PathVariable("studentId") BigInteger studentId) {
		try {
			classService.deleteCourseSelectionById(studentId, classId);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{classId}/classgroup")
	public ResponseEntity<GroupResponseVO> getClassGroup(@PathVariable("classId") Integer classId) {
		FixGroup fixGroup = null;
		GroupResponseVO groupResponseVO = null;
		BigInteger userId;
		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(userId, classId);
		 * List<FixGroupMember> students =
		 * FixGroupService.listFixGroupByGroupId(fixGroup.getId()); groupResponseVO=
		 * ModelUtils.FixGroupToGroupResponseVO(fixGroup,students); } catch
		 * (ClassesNotFoundException e) { e.printStackTrace(); } catch
		 * (UserNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IllegalArgumentException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return new ResponseEntity<GroupResponseVO>(groupResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	// 未完成
	@PutMapping("/{classId}/classgroup/resign")
	public ResponseEntity<String> groupLeaderResign(@PathVariable("classId") Integer classId,
			@RequestParam("id") BigInteger studentId) {
		FixGroup fixGroup = null;
		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(studentId, classId);
		 * fixGroup.setLeader(new User());
		 * FixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup); } catch
		 * (IllegalArgumentException e) { e.printStackTrace(); } catch
		 * (ClassesNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UserNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}/classgroup/assign")
	public ResponseEntity<String> groupLeaderAssign(@PathVariable("classId") Integer classId,
			@RequestParam("id") BigInteger studentId) {
		FixGroup fixGroup = null;
		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(studentId, classId); if
		 * (fixGroup.getLeader()!=null) { return new ResponseEntity<String>("已经有组长了",new
		 * HttpHeaders(), HttpStatus.CONFLICT); } List<FixGroupMember> members=
		 * FixGroupService.listFixGroupByGroupId(fixGroup.getId()); boolean flag=false;
		 * for (FixGroupMember fixGroupMember : members) { if
		 * (fixGroupMember.getId().equals(studentId)) { flag=true; } } if (flag==false)
		 * { return new ResponseEntity<String>("权限不足（不是该小组的成员）",new HttpHeaders(),
		 * HttpStatus.FORBIDDEN); } User leader=userService.getUserByUserId(studentId);
		 * fixGroup.setLeader(leader);
		 * FixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup); } catch
		 * (IllegalArgumentException e) { e.printStackTrace(); } catch
		 * (ClassesNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UserNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); return new
		 * ResponseEntity<String>("错误的ID格式、成为组长的学生不存在",new HttpHeaders(),
		 * HttpStatus.BAD_REQUEST); }
		 */
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}/classgroup/add")
	public ResponseEntity<String> addStudentToGroup(@RequestParam("groupId") Integer groupId,
			@RequestParam("id") Integer studentId) {
		FixGroup fixGroup = null;
		/*
		 * try { fixGroup = FixGroupService.getFixedGroupById(studentId, classId); if
		 * (fixGroup.getLeader()!=null) { return new ResponseEntity<String>("已经有组长了",new
		 * HttpHeaders(), HttpStatus.CONFLICT); } List<FixGroupMember> members=
		 * FixGroupService.listFixGroupByGroupId(fixGroup.getId()); boolean flag=false;
		 * for (FixGroupMember fixGroupMember : members) { if
		 * (fixGroupMember.getId().equals(studentId)) { flag=true; } } if (flag==false)
		 * { return new ResponseEntity<String>("权限不足（不是该小组的成员）",new HttpHeaders(),
		 * HttpStatus.FORBIDDEN); } User leader=userService.getUserByUserId(studentId);
		 * fixGroup.setLeader(leader);
		 * FixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup); } catch
		 * (IllegalArgumentException e) { e.printStackTrace(); } catch
		 * (ClassesNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (UserNotFoundException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); return new
		 * ResponseEntity<String>("错误的ID格式、成为组长的学生不存在",new HttpHeaders(),
		 * HttpStatus.BAD_REQUEST); }
		 */
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}/classgroup/remove")
	public ResponseEntity<String> removeStudentFromGroup(@RequestParam("groupId") Integer groupId,
			@RequestParam("id") Integer studentId) {
		return null;
	}

}
