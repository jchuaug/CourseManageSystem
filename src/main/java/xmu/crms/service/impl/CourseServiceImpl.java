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
import java.util.ArrayList;
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
//        Integer courseId = courseMapper.insertCourseByUserId(course);
        courseMapper.insertCourseByUserId(course);

        return course.getId();
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
            List<ClassInfo> classInfoList = new ArrayList<ClassInfo>();
            for (int i=0;i<courseList.size();i++){
                BigInteger course_id = courseList.get(i).getId();
                classInfoList.addAll(classService.listClassByCourseId(course_id));
            }
            return classInfoList;
        }catch (CourseNotFoundException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClassInfo> listClassByTeacherName(String teacherName) {
        try{
            //需要考虑老师同名的情况？
            List<BigInteger> userIds = userService.listUserIdByUserName(teacherName);

            List<ClassInfo> classInfoList = new ArrayList<>();
            for (int i=0;i<userIds.size();i++){
                //第i个老师的所有课程
                List<Course> courseList = this.listCourseByUserId(userIds.get(i));
                for (int j=0;j<courseList.size();j++){
                    BigInteger courseId = courseList.get(j).getId();
                    classInfoList.addAll(classService.listClassByCourseId(courseId));
                }
            }
            return classInfoList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ClassInfo> listClassByName(String courseName, String teacherName) throws UserNotFoundException, CourseNotFoundException {
        List<ClassInfo> classInfoList = new ArrayList<>();
        List<ClassInfo> classInfoList1 = this.listClassByCourseName(courseName);
        if (classInfoList1 == null)
            throw new CourseNotFoundException();
        List<ClassInfo> classInfoList2 = this.listClassByTeacherName(teacherName);
        if (classInfoList2 == null)
            throw new UserNotFoundException();

        //取交集
        classInfoList1.retainAll(classInfoList2);

        return classInfoList1;
    }

//    这个函数标准组已经删除
//    private List<ClassInfo> listClassByUserId(BigInteger userId) throws IllegalArgumentException, CourseNotFoundException, ClassNotFoundException {
//        BigInteger courseId = this.listCourseByUserId(userId).get(0).getId();
//        return classService.listClassByCourseId(courseId);
//    }
}
