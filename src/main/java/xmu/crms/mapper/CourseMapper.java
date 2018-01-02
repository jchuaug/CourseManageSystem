package xmu.crms.mapper;

import org.springframework.stereotype.Component;
import xmu.crms.entity.ClassInfo;
import xmu.crms.entity.Course;

import java.math.BigInteger;
import java.util.List;

/**
 * @author caistrong
 */
@Component
public interface CourseMapper {
    /**
     * listCourseByTeacherId
     * 
     * @param userId
     * @return
     */
    List<Course> listCourseByTeacherId(BigInteger userId);

    /**
     * listCourseByStudentId
     * 
     * @param userId
     * @return
     */
    List<Course> listCourseByStudentId(BigInteger userId);

    /**
     * insertCourseByUserId
     * 
     * @param course
     * @return
     */
    Integer insertCourseByUserId(Course course);

    /**
     * 
     * @param courseId
     * @return
     */
    Course getCourseByCourseId(BigInteger courseId);

    /**
     * 
     * @param course
     * @return
     */
    int updateCourseByCourseId(Course course);

    /**
     * 
     * @param courseId
     * @return
     */
    int deleteCourseByCourseId(BigInteger courseId);

    /**
     * 
     * @param courseName
     * @return
     */
    List<Course> listCourseByCourseName(String courseName);

    /**
     * 
     * @param id
     * @return
     */

    List<ClassInfo> getClassesByCourseId(BigInteger id);
    // List<ClassInfo> listClassByCourseName(String courseName);
    // List<ClassInfo> listClassByTeacherName(String teacherName);
    // List<ClassInfo> listClassByUserId(BigInteger userId);
}
