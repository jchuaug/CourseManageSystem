package xmu.crms.web.controller;

import org.bouncycastle.jcajce.provider.symmetric.TEA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xmu.crms.entity.*;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.FixGroupNotFoundException;
import xmu.crms.exception.InvalidOperationException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.ClassService;
import xmu.crms.service.FixGroupService;
import xmu.crms.service.UserService;
import xmu.crms.utils.JWTUtil;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjj
 * @date 2017/12/28
 */
@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    private ClassService classService;

    @Autowired
    private FixGroupService fixGroupService;

    @Autowired
    private UserService userService;

    private final String STUDENT = "student";
    private final String TEACHER = "teacher";

    @GetMapping("")
    public ResponseEntity<List<ClassResponseVO>> getAllClass(@RequestParam(required = false) String courseName,
                                                             @RequestParam(required = false) String courseTeacher, @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());

        List<ClassResponseVO> classVOs = new ArrayList<>();
        List<ClassInfo> classInfos = null;
        try {
            classInfos = classService.listClassByUserId(userId);

            for (ClassInfo classInfo : classInfos) {

                Integer numStudent = userService.listUserByClassId(classInfo.getId(), "", "").size();
                classVOs.add(ModelUtils.classInfoToClassResponseVO(classInfo, numStudent));
            }
        } catch (UserNotFoundException | ClassesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<List<ClassResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<List<ClassResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<ClassResponseVO>>(classVOs, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<ClassResponseVO>> getAllClass(@RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());

        List<ClassResponseVO> classVOs = new ArrayList<>();
        List<ClassInfo> classInfos = null;
        try {
            classInfos = classService.listClassByCourseId(null);
            for (ClassInfo classInfo : classInfos) {

                if (classInfo != null) {
                    classVOs.add(ModelUtils.classInfoToClassResponseVO(classInfo, null));
                }
            }
        } catch (CourseNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return new ResponseEntity<List<ClassResponseVO>>(classVOs, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{classId}")
    public ResponseEntity<ClassResponseVO> getClassByClassId(@PathVariable("classId") BigInteger classId,
                                                             @RequestHeader HttpHeaders headers) {

        ClassInfo class1 = null;
        ClassResponseVO classResponseVO = null;
        try {
            class1 = classService.getClassByClassId(classId);
            Integer numStudent = userService.listUserByClassId(classId, "", "").size();
            classResponseVO = ModelUtils.classInfoToClassResponseVO(class1, numStudent);
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
        String type = JWTUtil.getUserType(token);
        if (STUDENT.equals(type)) {
            return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        BigInteger classId2 = new BigInteger(classId.toString());
        try {
            ClassInfo classInfo1 = classService.getClassByClassId(classId2);
            User teacher = classInfo1.getCourse().getTeacher();
            if (!(teacher.getId().equals(userId))) {
                return new ResponseEntity<String>("不是创建该班级的教师，权限不足", new HttpHeaders(), HttpStatus.FORBIDDEN);
            }

            ClassInfo classInfo2 = ModelUtils.ClassRequestVOToClassInfo(classInfo);
            classService.updateClassByClassId(classId2, classInfo2);
        } catch (ClassesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到班级", new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{classId}")
    public ResponseEntity<String> deleteClass(@PathVariable("classId") BigInteger classId,
                                              @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        String type = JWTUtil.getUserType(token);
        if (STUDENT.equals(type)) {
            return new ResponseEntity<String>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        BigInteger classId2 = new BigInteger(classId.toString());
        try {
            ClassInfo classInfo1 = classService.getClassByClassId(classId2);
            User teacher = classInfo1.getCourse().getTeacher();
            if (!(teacher.getId().equals(userId))) {
                return new ResponseEntity<String>("不是创建本班级的教师，没有权限", new HttpHeaders(), HttpStatus.FORBIDDEN);
            }
            classService.deleteClassByClassId(classId);
        } catch (ClassesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到该班级", new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    // 未完成
    @GetMapping("/{classId}/student")
    public ResponseEntity<List<UserResponseVO>> listStudentByNameAndId(@PathVariable("classId") BigInteger classId,
                                                                       @RequestParam("numBeginWith") String numBeginWith, @RequestParam("nameBeginWith") String nameBeginWith,
                                                                       @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        
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
                                              @RequestHeader HttpHeaders headers) {

        String token = headers.get("Authorization").get(0);
        BigInteger studentId = new BigInteger(JWTUtil.getUserId(token).toString());

        try {
            List<ClassInfo> classInfos = classService.listClassByUserId(studentId);
            for (ClassInfo classInfo : classInfos) {
                if (classInfo.getId().equals(classId)) {
                    return new ResponseEntity<String>("已选过该课程", new HttpHeaders(), HttpStatus.CONFLICT);
                }
            }
            System.err.println(studentId);
            System.err.println(classId);
            classService.insertCourseSelectionById(studentId, classId);

        } catch (ClassesNotFoundException | UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到课程或学生", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("选课成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{classId}/student/{studentId}")
    public ResponseEntity<String> deleteSelectClass(@PathVariable("classId") BigInteger classId,
                                                    @PathVariable("studentId") BigInteger studentId, @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        if (!userId.equals(studentId)) {
            return new ResponseEntity<String>("非本人操作", new HttpHeaders(), HttpStatus.FORBIDDEN);
        }
        try {
            classService.deleteCourseSelectionById(studentId, classId);
        } catch (ClassesNotFoundException | UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到班级或学生", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{classId}/classgroup")
    public ResponseEntity<GroupResponseVO> getClassGroup(@PathVariable("classId") BigInteger classId,
                                                         @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        String type = JWTUtil.getUserType(token);
        if (TEACHER.equals(type)) {
            return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        FixGroup fixGroup = null;
        GroupResponseVO groupResponseVO = null;
        BigInteger studentId = userId;

        try {
            fixGroup = fixGroupService.getFixedGroupById(studentId, classId);
            if (fixGroup != null) {
                List<FixGroupMember> students = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
                groupResponseVO = ModelUtils.FixGroupToGroupResponseVO(fixGroup, students);
            } else {
                return new ResponseEntity<GroupResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
            }

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
                                                    @RequestBody String sId, @RequestHeader HttpHeaders headers) {
        BigInteger studentId = new BigInteger(sId);
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());

        if (!userId.equals(studentId)) {
            return new ResponseEntity<String>("非本人操作", new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        FixGroup fixGroup = null;

        try {
            fixGroup = fixGroupService.getFixedGroupById(studentId, classId);

            if (!(fixGroup.getLeader().getId().equals(studentId))) {
                return new ResponseEntity<String>("权限不足，不是该小组成员", new HttpHeaders(), HttpStatus.FORBIDDEN);
            }
            fixGroup.setLeader(new User());
            fixGroupService.updateFixGroupByGroupId(fixGroup.getId(), fixGroup);
        } catch (IllegalArgumentException | UserNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (ClassesNotFoundException | FixGroupNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到小组", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{classId}/classgroup/assign")
    public ResponseEntity<String> groupLeaderAssign(@PathVariable("classId") BigInteger classId,
                                                    @RequestBody String sId, @RequestHeader HttpHeaders headers) {
        BigInteger studentId = new BigInteger(sId);
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
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
            return new ResponseEntity<String>("该小组不存在", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{classId}/classgroup/add")
    public ResponseEntity<String> addStudentToGroup(@PathVariable("classId") BigInteger classId,
                                                    @RequestParam("id") String sId, @RequestHeader HttpHeaders headers) {
        BigInteger studentId = new BigInteger(sId);
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());

        try {
            if (fixGroupService.getFixedGroupById(studentId, classId) != null) {
                System.err.println(fixGroupService.getFixedGroupById(studentId, classId));
                return new ResponseEntity<String>("待添加学生已经在小组里了", new HttpHeaders(), HttpStatus.CONFLICT);
            }
            FixGroup fixGroup = fixGroupService.getFixedGroupById(userId, classId);
            System.err.println(fixGroup);
            if (fixGroup==null) {
				fixGroupService.insertFixGroupByClassId(classId, userId);
				fixGroup = fixGroupService.getFixedGroupById(userId, classId);
			}
            List<FixGroupMember> members = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
            boolean flag = false;
            for (FixGroupMember fixGroupMember : members) {
                if (fixGroupMember.getStudent().getId().equals(userId)) {
                    flag = true;
                }
            }
            if (flag == false) {
                return new ResponseEntity<String>("权限不足（不是该小组的成员）", new HttpHeaders(), HttpStatus.FORBIDDEN);
            }

            fixGroupService.insertStudentIntoGroup(studentId, fixGroup.getId());
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

        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{classId}/classgroup/remove")
    public ResponseEntity<String> removeStudentFromGroup(@PathVariable("classId") BigInteger classId,
                                                         @RequestParam("id") String sId, @RequestHeader HttpHeaders headers) {

        BigInteger studentId = new BigInteger(sId);
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());

        try {
            FixGroup fixGroup = fixGroupService.getFixedGroupById(userId, classId);
            List<FixGroupMember> members = fixGroupService.listFixGroupByGroupId(fixGroup.getId());
            boolean flag = false;
            for (FixGroupMember fixGroupMember : members) {
                if (fixGroupMember.getStudent().getId().equals(userId)) {
                    flag = true;
                }
            }
            if (flag == false) {
                return new ResponseEntity<String>("权限不足（不是该小组的成员/组长）", new HttpHeaders(), HttpStatus.FORBIDDEN);
            }
            if (fixGroupService.getFixedGroupById(studentId, classId).equals(fixGroup)) {
                return new ResponseEntity<String>("待移除学生不在小组里", new HttpHeaders(), HttpStatus.NOT_FOUND);
            }

            fixGroupService.deleteFixGroupUserById(fixGroup.getId(), studentId);
        } catch (UserNotFoundException | FixGroupNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("错误的ID格式、待移除的学生不存在", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (ClassesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("未找到班级", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{classId}/seminar")
    public ResponseEntity getAllSeminar(@PathVariable BigInteger classId) {
        List<Seminar> seminars = classService.listSeminarsByClassId(classId);
        List<SeminarResponseVO> responseVOS = new ArrayList<>();
        for (Seminar seminar : seminars) {
            responseVOS.add(ModelUtils.SeminarInfoToSeminarResponseVO(seminar));
        }
        return ResponseEntity.ok().body(responseVOS);
    }
}
