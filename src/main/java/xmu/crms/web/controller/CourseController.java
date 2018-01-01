package xmu.crms.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.entity.Seminar;
import xmu.crms.entity.User;
import xmu.crms.exception.ClassesNotFoundException;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.GroupNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.service.*;
import xmu.crms.utils.JWTUtil;
import xmu.crms.utils.ModelUtils;
import xmu.crms.web.VO.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjj
 * @date 2017/12/29
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private SeminarService seminarService;

    @Autowired
    private SeminarGroupService seminarGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private ClassService classService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private GradeService gradeService;

    private final String TEACHER = "teacher";
    private final String STUDENT = "student";

    @GetMapping("")
    public ResponseEntity<List<CourseResponseVO>> getAllCourse(@RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        List<CourseResponseVO> courseResponseVOs = new ArrayList<>();

        try {
            List<Course> courses = courseService.listCourseByUserId(userId);
            for (Course course : courses) {
                List<ClassInfo> classInfos = classService.listClassByCourseId(course.getId());
                Integer numClass = classInfos.size();
                Integer numStudent = 0;
                for (ClassInfo classInfo : classInfos) {
                    numStudent += userService.listUserByClassId(classInfo.getId(), "", "").size();
                }
                courseResponseVOs.add(ModelUtils.CourseToCourseResponseVO(course, numClass, numStudent));
            }
        } catch (CourseNotFoundException ignore) {

        } catch (UserNotFoundException | ClassesNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(courseResponseVOs, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Course> addCourse(@RequestBody CourseRequestVO courseRequestVO,
                                            @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        if (type == 0) {
            new ResponseEntity<Course>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        Course course = ModelUtils.CourseRequestVOToCourse(courseRequestVO);

        try {
            User teacher = new User();
            course.setTeacher(teacher);
            BigInteger id = courseService.insertCourseByUserId(userId, course);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<Course>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Course>(course, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseVO> getCourseByCourseId(@PathVariable("courseId") BigInteger courseId,
                                                                @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }

        CourseResponseVO course = null;

        try {
            Course course2 = courseService.getCourseByCourseId(courseId);
            course = ModelUtils.CourseToCourseResponseVO(course2, null, null);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<CourseResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<CourseResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<CourseResponseVO>(course, new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable("courseId") BigInteger courseId,
                                               @RequestBody CourseRequestVO courseRequestVO, @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        if (type == 0) {
            new ResponseEntity<String>("用户权限不足（学生无法修改课程）", new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        Course course = ModelUtils.CourseRequestVOToCourse(courseRequestVO);

        try {
            Course course2 = courseService.getCourseByCourseId(courseId);
            if (course2 == null) {
                throw new CourseNotFoundException();
            }
            if (!course2.getTeacher().getId().equals(userId)) {
                return new ResponseEntity<String>("用户权限不足（无法修改他人课程）", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            course.setTeacher(course2.getTeacher());
            courseService.updateCourseByCourseId(courseId, course);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到该课程", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") BigInteger courseId,
                                               @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        if (type == 0) {
            new ResponseEntity<String>("用户权限不足（学生无法删除课程）", new HttpHeaders(), HttpStatus.FORBIDDEN);
        }

        try {
            Course course2 = courseService.getCourseByCourseId(courseId);
            if (!course2.getTeacher().getId().equals(userId)) {
                return new ResponseEntity<String>("用户权限不足（无法删除他人课程）", new HttpHeaders(), HttpStatus.BAD_REQUEST);
            }
            courseService.deleteCourseByCourseId(courseId);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("格式错误", new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<String>("找不到该课程", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>("成功", new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}/class")
    public ResponseEntity<List<ClassResponseVO>> listClassByCourseId(@PathVariable("courseId") BigInteger courseId,
                                                                     @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        List<ClassResponseVO> classInfos = new ArrayList<>();
        List<ClassInfo> classInfos2 = null;
        try {
            classInfos2 = classService.listClassByCourseId(courseId);
            for (ClassInfo classInfo : classInfos2) {
                classInfos.add(ModelUtils.ClassInfoToClassResponseVO(classInfo, null));
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<List<ClassResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<List<ClassResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<ClassResponseVO>>(classInfos, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/{courseId}/class")
    public ResponseEntity<ClassResponseVO> addClassByCourseId(@PathVariable("courseId") BigInteger courseId,
                                                              @RequestBody ClassRequestVO classRequestVO, @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        if (type == 0) {
            return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }
        ClassResponseVO classResponseVO = null;
        try {
            ClassInfo classInfo = ModelUtils.ClassRequestVOToClassInfo(classRequestVO);
            Course course = courseService.getCourseByCourseId(courseId);
            if (course == null) {
                throw new CourseNotFoundException();
            }
            if (!course.getTeacher().getId().equals(userId)) {
                return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
            }
            BigInteger id = classService.insertClassById(courseId, classInfo);
            classResponseVO = ModelUtils.ClassInfoToClassResponseVO(classInfo, null);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<ClassResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ClassResponseVO>(classResponseVO, new HttpHeaders(), HttpStatus.CREATED);

    }

    @GetMapping("/{courseId}/seminar")
    public ResponseEntity<List<SeminarResponseVO>> getSemianrByCourseId(@PathVariable("courseId") BigInteger courseId,
                                                                        @RequestParam(required = false, value = "embedGrade", defaultValue = "false") boolean embedGrade,
                                                                        @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        List<SeminarResponseVO> seminarResponseVOs = new ArrayList<>();
        if (type == 1 && embedGrade == true) {
            return new ResponseEntity<List<SeminarResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } else {
            List<Seminar> seminars;
            try {
                seminars = seminarService.listSeminarByCourseId(courseId);
                for (Seminar seminar : seminars) {
                    Integer grade = null;
                    if (embedGrade == true && type == 0) {
                        try {
                            grade = seminarGroupService.getSeminarGroupById(seminar.getId(), userId)
                                    .getFinalGrade();
                        } catch (GroupNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    seminarResponseVOs.add(ModelUtils.SeminarInfoToSeminarResponseVO(seminar, null, grade));
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return new ResponseEntity<List<SeminarResponseVO>>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
            } catch (CourseNotFoundException e) {
                e.printStackTrace();
                return new ResponseEntity<List<SeminarResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
            }

        }

        return new ResponseEntity<List<SeminarResponseVO>>(seminarResponseVOs, new HttpHeaders(), HttpStatus.OK);

    }

    @PostMapping("/{courseId}/seminar")
    public ResponseEntity<SeminarResponseVO> addSeminarByCourseId(@PathVariable("courseId") BigInteger courseId,
                                                                  @RequestBody SeminarResponseVO seminarResponseVO, @RequestHeader HttpHeaders headers) {
        String token = headers.get("Authorization").get(0);
        BigInteger userId = new BigInteger(JWTUtil.getUserId(token).toString());
        System.err.println(userId);
        String typeString = JWTUtil.getUserType(token);
        Integer type = null;
        if (TEACHER.equals(typeString)) {
            type = 1;
        } else if (STUDENT.equals(typeString)) {
            type = 0;
        }
        if (type == 0) {
            return new ResponseEntity<SeminarResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
        }
        SeminarResponseVO seminarResponseVO2 = null;
        try {
            Course course = courseService.getCourseByCourseId(courseId);
            if (course == null) {
                throw new CourseNotFoundException();
            }
            if (!course.getTeacher().getId().equals(userId)) {
                return new ResponseEntity<SeminarResponseVO>(null, new HttpHeaders(), HttpStatus.FORBIDDEN);
            }
            Seminar forSeminar = new Seminar();
            forSeminar.setCourse(course);
            Seminar seminar = ModelUtils.SeminarResponseVOToSeminar(seminarResponseVO, forSeminar);
            BigInteger id = seminarService.insertSeminarByCourseId(courseId, seminar);
            seminarResponseVO2 = ModelUtils.SeminarInfoToSeminarResponseVO(seminar, null, null);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<SeminarResponseVO>(null, new HttpHeaders(), HttpStatus.BAD_REQUEST);
        } catch (CourseNotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<SeminarResponseVO>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<SeminarResponseVO>(seminarResponseVO2, new HttpHeaders(), HttpStatus.CREATED);

    }

    @GetMapping("/{courseId}/seminar/current")
    public ResponseEntity getCurrentSeminarByCourseId(@PathVariable("courseId") BigInteger courseId) {
        List<Seminar> seminars = seminarService.getCurrentSeminar();
        return ResponseEntity.ok().body(seminars);
    }

//    @GetMapping("/{courseId}/seminar/current")
//    public ResponseEntity<List<SeminarResponseVO>> getCurrentSeminarByCourseId(@PathVariable("courseId") BigInteger courseId){
//
//          try{
//          List<Seminar> seminarList =  seminarService.listSeminarByCourseId(courseId);
//          List<SeminarResponseVO> seminarResponseVOList = new ArrayList<>();
//          for (int i=0;i<seminarList.size();i++){
//              Seminar seminar = seminarList.get(i);
//              Date end =seminar.getEndTime();
//              Date start = seminar.getStartTime();
//              //这里的true要判断当前时间是否在start和end之间
//              if (true){
//                  List<Topic> topicList = topicService.listTopicBySeminarId(seminar.getId());
//                  seminarResponseVOList.add(ModelUtils.SeminarInfoToSeminarResponseVO(seminar,topicList,null));
//              }
//          }
//          return new ResponseEntity<List<SeminarResponseVO>>(seminarResponseVOList, new HttpHeaders(), HttpStatus.OK);
//      }catch (Exception e){
//          e.printStackTrace();
//      }
//      return new ResponseEntity<List<SeminarResponseVO>>(null, new HttpHeaders(), HttpStatus.NOT_FOUND);
//    }

}
