package xmu.crms.service;

import java.math.BigInteger;
import java.util.List;

import xmu.crms.exception.*;
import xmu.crms.entity.*;


/**
 * @author CaoXingmei
 * @version 2.20
 */
public interface CourseService {
    /**
     * 按userId获取与当前用户相关联的课程列表.
     * <p>老师与他相关联的课程列表<br>
     *
     * @param userId 用户Id
     * @return null 课程列表
     * @throws IllegalArgumentException userId格式错误时抛出
     * @author ZhouZhongjun
     * @throws CourseNotFoundException
     */
    List<Course> listCourseByUserId(BigInteger userId) throws IllegalArgumentException, CourseNotFoundException;


    /**
     * 按userId创建课程.
     * <p>按userId创建课程<br>
     *
     * @param userId 用户Id
     * @param course 课程信息
     * @return courseId 新建课程的id
     * @throws IllegalArgumentException userId格式错误时抛出
     * @author ZhouZhongjun
     */
    BigInteger insertCourseByUserId(BigInteger userId, Course course) throws IllegalArgumentException;


    /**
     * 按courseId获取课程 .
     * <p>按courseId获取课程 <br>
     *
     * @param courseId 课程Id
     * @return course
     * @throws IllegalArgumentException courseId格式错误时抛出
     * @throws CourseNotFoundException  未找到课程
     * @author ZhouZhongjun
     */
    Course getCourseByCourseId(BigInteger courseId) throws IllegalArgumentException, CourseNotFoundException;


    /**
     * 传入courseId和course信息修改course信息.
     * <p>传入courseId和course信息修改course信息 <br>
     *
     * @param courseId 课程Id
     * @param course   课程信息
     * @throws CourseNotFoundException 未找到课程
     * @author ZhouZhongjun
     */
    void updateCourseByCourseId(BigInteger courseId, Course course);


    /**
     * 按courseId删除课程.
     * <p>先根据courseID删除Seminar 和 class,然后再将course的信息删除<br>
     *
     * @param courseId 课程Id
     * @throws IllegalArgumentException courseId格式错误时抛出
     * @author ZhouZhongjun
     * @see SeminarService #deleteSemiarByCourseId(BigInteger courseId)
     * @see ClassService   #deleteClassByCourseId(BigInteger courseId)
     */
    void deleteCourseByCourseId(BigInteger courseId) throws IllegalArgumentException;


    /**
     * 根据课程名称获取课程列表.
     * <p>根据课程名称获取课程列表<br>
     *
     * @param courseName 课程名称
     * @return list 课程列表
     * @author YeXiaona
     */
    List<Course> listCourseByCourseName(String courseName);


    /**
     * 按课程名称获取班级列表.
     * <p>根据课程名获取课程ID，通过课程ID获取班级列表<br>
     *
     * @param courseName 课程名称
     * @return list 班级列表
     * @author YeXiaona
     * @see CourseService #listCourseByCourseName(String courseName)
     * @see ClassService #listClassByCourseId(BigInteger courseId)
     * @throws CourseNotFoundException
     */
    List<ClassInfo> listClassByCourseName(String courseName) throws CourseNotFoundException;


    /**
     * 按教师名称获取班级列表.
     * <p>根据教师名称获取课程ID，通过课程ID获取班级列表<br>
     *
     * @param teacherName 教师名称
     * @return list 班级列表
     * @author YeXiaona
     * @see UserService #listUserIdByUserName(String userName)
     * @see CourseService #listCourseByUserId(BigInteger)
     * @see ClassService #listClassByCourseId(BigInteger)
     * @throws ClassesNotFoundException
     * @throws UserNotFoundException
     */
    List<ClassInfo> listClassByTeacherName(String teacherName) throws UserNotFoundException,ClassesNotFoundException;

    /**
     * 按课程名称和教师名称获取班级列表.
     * <p> 根据课程名和教师名获取课程ID，通过课程ID获取班级列表;若课程名和班级名均不为空，取交集<br>
     *
     * @param courseName  课程名称
     * @param teacherName 教师名称
     * @return List 班级列表
     * @throws UserNotFoundException   无此姓名的教师
     * @throws CourseNotFoundException 无此名称的课程
     * @author yexiaona
     * @see CourseService #listClassByCourseName(String courseName)
     * @see CourseService #listClassByTeacherName(String teacherName)
     */
    List<ClassInfo> listClassByName(String courseName, String teacherName) throws
            UserNotFoundException, CourseNotFoundException;

    /**
     * do
     * @param id d
     * @return d
     */
    List<ClassInfo> listClassByCourseId(BigInteger id);
}

