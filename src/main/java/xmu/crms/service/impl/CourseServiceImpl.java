package xmu.crms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.UserNotFoundException;
import xmu.crms.mapper.CourseMapper;
import xmu.crms.service.ClassService;
import xmu.crms.service.CourseService;
import xmu.crms.service.SeminarService;
import xmu.crms.service.UserService;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caistrong
 */
@Component
public class CourseServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;
//    todo 合并时将false去掉
    @Autowired(required = false)
ClassService classService;
    @Autowired(required = false)
    UserService userService;
    @Autowired(required = false)
    SeminarService seminarService;


    @Override
    public List<Course> listCourseByUserId(BigInteger userId) throws IllegalArgumentException, CourseNotFoundException {
        if(!(userId.intValue() > 0)) {
            throw new IllegalArgumentException("用户ID格式错误！");
        }
        List<Course> courseList = courseMapper.listCourseByUserId(userId);
        if(courseList == null) {
            throw new CourseNotFoundException();
        }
        return courseList;
    }


    @Override
    public BigInteger insertCourseByUserId(BigInteger userId,Course course) throws IllegalArgumentException {
        if(!(userId.intValue() > 0)) {
            throw new IllegalArgumentException("用户ID格式错误！");
        }
        course.getTeacher().setId(userId);
        Integer courseId = courseMapper.insertCourseByUserId(course);
        return BigInteger.valueOf(courseId);
}

    @Override
    public Course getCourseByCourseId(BigInteger courseId) throws IllegalArgumentException, CourseNotFoundException {
        if(!(courseId.intValue() > 0)) {
            throw new IllegalArgumentException("课程ID格式错误！");
        }
        Course course = courseMapper.getCourseByCourseId(courseId);
//        if(course == null) {
//            throw new CourseNotFoundException();
//        }
        return course;
    }

    @Override
    public void updateCourseByCourseId(BigInteger courseId, Course course) {
        course.setId(courseId);
        courseMapper.updateCourseByCourseId(course);
    }

    @Override
    public void deleteCourseByCourseId(BigInteger courseId) throws IllegalArgumentException {
        if(!(courseId.intValue() > 0)) {
            throw new IllegalArgumentException("用户ID格式错误！");
        }
//        todo Seminar实现后将下行代码注释去掉
//        删除课程前先将该课程的seminar去掉
//        seminarService.deleteSeminarByCourseId(courseId);
        int matchDeleteLines = courseMapper.deleteCourseByCourseId(courseId);
    }

    @Override
    public List<Course> listCourseByCourseName(String courseName) {
        List<Course> courseList = courseMapper.listCourseByCourseName(courseName);
        return courseList;
    }

    @Override
    public List<ClassInfo> listClassByCourseName(String courseName) {
        try {
            List<Course> courseList = courseMapper.listCourseByCourseName(courseName);
            BigInteger course_id = courseList.get(0).getId();
//        todo 下面的classService还没实现
            List<ClassInfo> classInfoList = classService.listClassByCourseId(course_id);
            return classInfoList;
        }catch (CourseNotFoundException e){
            e.printStackTrace();
        }
//        todo 合并时将下两行删除
//        List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
//        return classInfoList;
        return null;
    }

    @Override
    public List<ClassInfo> listClassByTeacherName(String teacherName) {
        try{
            BigInteger userId = userService.listUserIdByUserName(teacherName).get(0);
            return this.listClassByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClassInfo> listClassByName(String courseName, String teacherName) throws UserNotFoundException, CourseNotFoundException {
        //todo add method
        return null;
    }

    private List<ClassInfo> listClassByUserId(BigInteger userId) throws IllegalArgumentException, CourseNotFoundException, ClassNotFoundException {
        BigInteger courseId = this.listCourseByUserId(userId).get(0).getId();
        return classService.listClassByCourseId(courseId);
    }
}
