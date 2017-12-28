package xmu.crms.web.controller;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.FixGroup;
import xmu.crms.entity.FixGroupMember;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.FixGroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.utils.JWTUtil;
import xmu.crms.service.ClassService;
import xmu.crms.service.CourseService;
import xmu.crms.service.FixGroupService;
import xmu.crms.service.UserService;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.ClassRequestVO;
import xmu.crms.web.VO.ClassResponseVO;
import xmu.crms.web.VO.GroupResponseVO;
import xmu.crms.web.VO.UserResponseVO;

@RestController
@RequestMapping("/class")
public class ClassController {

	@Autowired
	private ClassService classService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private FixGroupService fixGroupService;

	@Autowired
	private UserService userService;

	// 未完成
	/*
	 * @GetMapping("/") public List<ClassResponseVO> getAllClass(@RequestParam
	 * String courseName, @RequestParam String courseTeacher) {
	 * List<ClassResponseVO> classVOs = new ArrayList<>(); List<ClassInfo>
	 * classInfos = null; try { classInfos =
	 * classService.listClassByName(courseName, courseTeacher); for (ClassInfo
	 * classInfo : classInfos) { Course
	 * course=courseService.getCourseByCourseId(classInfo.getCourseId()); User
	 * teacher = userService.getUserByUserId(null); Integer numStudent =
	 * classService.getNumStudentByClassId(classInfo.getId());
	 * classVOs.add(ModelUtils.ClassInfoToClassResponseVO(classInfo, teacher,
	 * numStudent)); } } catch (UserNotFoundException e) { e.printStackTrace(); }
	 * catch (CourseNotFoundException e) { e.printStackTrace(); }
	 * 
	 * return classVOs; }
	 */

	@GetMapping("/{classId}")
	public ResponseEntity<ClassResponseVO> getClassByClassId(@PathVariable("classId") BigInteger classId,
			@RequestHeader HttpHeaders headers) {
		System.err.println(headers);
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
			Integer numStudent = userService.listUserByClassId(classId, "", "").size();
			classResponseVO = ModelUtils.ClassInfoToClassResponseVO(class1, class1.getCourse().getTeacher(),
					numStudent);
		} catch (ClassesNotFoundException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<ClassResponseVO>(classResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}")
	public ResponseEntity<String> updateClass(@PathVariable("classId") BigInteger classId,
			@RequestBody ClassRequestVO classInfo, @RequestHeader HttpHeaders headers) {
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
		BigInteger classId2 = new BigInteger(classId.toString());
		try {
			ClassInfo classInfo1 = classService.getClassByClassId(classId2);
			User teacher = classInfo1.getCourse().getTeacher();
			if (!(teacher.getId().equals(userId))) {
				return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
			}

			ClassInfo classInfo2 = ModelUtils.ClassRequestVOToClassInfo(classInfo);
			classService.updateClassByClassId(classId2, classInfo2);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/{classId}")
	public ResponseEntity<String> deleteClass(@PathVariable("classId") BigInteger classId,
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

		BigInteger classId2 = new BigInteger(classId.toString());
		try {
			ClassInfo classInfo1 = classService.getClassByClassId(classId2);
			User teacher = classInfo1.getCourse().getTeacher();
			if (!(teacher.getId().equals(userId))) {
				return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			classService.deleteClassByClassId(classId);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	// 未完成
	@GetMapping("/{classId}/student")
	public ResponseEntity<List<UserResponseVO>> listStudentByNameAndId(@PathVariable("classId") BigInteger classId,
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
		} catch (IllegalArgumentException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<List<UserResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<UserResponseVO>>(studentVOs, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping("/{classId}/student")
	public ResponseEntity<String> selectClass(@PathVariable("classId") BigInteger classId,
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
		String username = JWTUtil.getUsername(token);
		if (!userId.equals(studentId)) {
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		try {
			BigInteger flag = classService.insertCourseSelectionById(studentId, classId);
			List<ClassInfo> classInfos = classService.listClassByUserId(userId);
			for (ClassInfo classInfo : classInfos) {
				if (classInfo.getId().equals(classId)) {
					return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.CONFLICT);
				}
			}
		} catch (ClassesNotFoundException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<String>("", new HttpHeaders(), HttpStatus.CREATED);
	}

	@DeleteMapping("/{classId}/student/{studentId}")
	public ResponseEntity<String> deleteSelectClass(@PathVariable("classId") BigInteger classId,
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

		try {
			classService.deleteCourseSelectionById(studentId, classId);
		} catch (ClassesNotFoundException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{classId}/classgroup")
	public ResponseEntity<GroupResponseVO> getClassGroup(@PathVariable("classId") BigInteger classId,
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

		try {
			fixGroup = fixGroupService.getFixedGroupById(studentId, classId);
			List<FixGroupMember> students = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
			groupResponseVO = ModelUtils.FixGroupToGroupResponseVO(fixGroup, students);
		} catch (ClassesNotFoundException | FixGroupNotFoundException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<GroupResponseVO>(groupResponseVO, new HttpHeaders(), HttpStatus.OK);
	}

	// 未完成
	@PutMapping("/{classId}/classgroup/resign")
	public ResponseEntity<String> groupLeaderResign(@PathVariable("classId") BigInteger classId,
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

		try {
			fixGroup = fixGroupService.getFixedGroupById(studentId, classId);
			fixGroup.setLeader(new User());
			if (!(fixGroup.getLeader().getId().equals(studentId))) {
				return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			fixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup);
		} catch (IllegalArgumentException | UserNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (ClassesNotFoundException | FixGroupNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}/classgroup/assign")
	public ResponseEntity<String> groupLeaderAssign(@PathVariable("classId") BigInteger classId,
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
			return new ResponseEntity<String>("非本人操作", new HttpHeaders(), HttpStatus.FORBIDDEN);
		}

		FixGroup fixGroup = null;

		try {
			fixGroup = fixGroupService.getFixedGroupById(studentId, classId);
			if (fixGroup.getLeader() != null) {
				return new ResponseEntity<String>("已经有组长了", new HttpHeaders(), HttpStatus.CONFLICT);
			}
			List<FixGroupMember> members = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
			boolean flag = false;
			for (FixGroupMember fixGroupMember : members) {
				if (fixGroupMember.getId().equals(studentId)) {
					flag = true;
				}
			}
			if (flag == false) {
				return new ResponseEntity<String>("权限不足（不是该小组的成员）", new HttpHeaders(), HttpStatus.FORBIDDEN);
			}
			User leader = userService.getUserByUserId(studentId);
			fixGroup.setLeader(leader);
			fixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup);
		} catch (UserNotFoundException | IllegalArgumentException | ClassesNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("错误的ID格式、成为组长的学生不存在", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (FixGroupNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("改小组不存在", new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}/classgroup/add")
	public ResponseEntity<String> addStudentToGroup(@PathVariable("classId") BigInteger classId,
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

		try {
			FixGroup fixGroup = fixGroupService.getFixedGroupById(userId, classId);
			List<FixGroupMember> members = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
			boolean flag = false;
			for (FixGroupMember fixGroupMember : members) {
				if (fixGroupMember.getId().equals(userId)) {
					flag = true;
				}
			}
			if (flag == false) {
				return new ResponseEntity<String>("权限不足（不是该小组的成员）", new HttpHeaders(), HttpStatus.FORBIDDEN);
			}

			BigInteger flag2 = fixGroupService.insertStudentIntoGroup(studentId, fixGroup.getId());
		} catch (UserNotFoundException | FixGroupNotFoundException | IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("错误的ID格式、成为组长的学生不存在", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (InvalidOperationException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("待添加学生已经在小组里了", new HttpHeaders(), HttpStatus.CONFLICT);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("找不到班级", new HttpHeaders(), HttpStatus.NOT_FOUND);

		}

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

	@PutMapping("/{classId}/classgroup/remove")
	public ResponseEntity<String> removeStudentFromGroup(@PathVariable("classId") BigInteger classId,
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

		
		try {
			FixGroup fixGroup=fixGroupService.getFixedGroupById(userId, classId);
			List<FixGroupMember> members = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
			boolean flag = false;
			for (FixGroupMember fixGroupMember : members) {
				if (fixGroupMember.getId().equals(userId)) {
					flag = true;
				}
			}
			if (flag == false) {
				return new ResponseEntity<String>("权限不足（不是该小组的成员/组长）", new HttpHeaders(), HttpStatus.FORBIDDEN);
			}

			fixGroupService.deleteFixGroupUserById(fixGroup.getId(), studentId);
		} catch (UserNotFoundException | FixGroupNotFoundException | IllegalArgumentException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("错误的ID格式、待移除的学生不存在", new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (ClassesNotFoundException e) {
			e.printStackTrace();
			return new ResponseEntity<String>("未找到班级", new HttpHeaders(), HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
	}

}
