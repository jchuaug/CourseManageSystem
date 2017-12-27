package xmu.crms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xmu.crms.entity.Course;
import xmu.crms.entity.Seminar;
import xmu.crms.exception.CourseNotFoundException;
import xmu.crms.exception.SeminarNotFoundException;
import xmu.crms.mapper.SeminarMapper;
import xmu.crms.service.SeminarService;

import java.math.BigInteger;
import java.util.List;

/**
 * Demo SeminarServiceImpl
 *
 * @author drafting_dreams
 * @date 2017/12/24
 */
public class SeminarServiceImpl implements SeminarService {

    // todo deleted later
    @Autowired(required = false)
    SeminarMapper seminarMapper;


    @Override
    public List<Seminar> listSeminarByCourseId(BigInteger courseId) throws IllegalArgumentException, CourseNotFoundException {
        if (seminarMapper.getCourseById(courseId) == null) {
            throw new CourseNotFoundException();
        }
        return seminarMapper.listSeminarByCourseId(courseId);
    }


    @Override
    public void deleteSeminarByCourseId(BigInteger courseId) throws IllegalArgumentException, CourseNotFoundException {
        if (seminarMapper.getCourseById(courseId) == null) {
            throw new CourseNotFoundException();
        }

        List<Seminar> seminars = listSeminarByCourseId(courseId);
        for (int i = 0; i < seminars.size(); i++) {
            seminarMapper.deleteTopicBySeminarId(seminars.get(i).getId());
            seminarMapper.deleteSeminarGroupBySeminarId(seminars.get(i).getId());
        }

        seminarMapper.deleteSeminarByCourseId(courseId);

    }


    @Override
    public Seminar getSeminarBySeminarId(BigInteger seminarId) throws IllegalArgumentException, SeminarNotFoundException {
        if (seminarMapper.getSeminarBySeminarId(seminarId) == null) {
            throw new SeminarNotFoundException();
        }
        return seminarMapper.getSeminarBySeminarId(seminarId);
    }

    @Override
    public void updateSeminarBySeminarId(BigInteger seminarId, Seminar seminar) throws IllegalArgumentException, SeminarNotFoundException {
        if (seminarMapper.getSeminarBySeminarId(seminarId) == null) {
            throw new SeminarNotFoundException();
        }
        seminar.setId(seminarId);
        seminarMapper.updateSeminarBySeminarId(seminar);
    }

    @Override
    public void deleteSeminarBySeminarId(BigInteger seminarId) throws IllegalArgumentException, SeminarNotFoundException {
        if (seminarMapper.getSeminarBySeminarId(seminarId) == null) {
            throw new SeminarNotFoundException();
        }
        //删除讨论课包含的topic信息和小组信息
        seminarMapper.deleteTopicBySeminarId(seminarId);
        seminarMapper.deleteSeminarGroupBySeminarId(seminarId);
        //通过seminarId删除讨论课
        seminarMapper.deleteSeminarBySeminarId(seminarId);
    }

    @Override
    public BigInteger insertSeminarByCourseId(BigInteger courseId, Seminar seminar) throws IllegalArgumentException, CourseNotFoundException {
        if (seminarMapper.getCourseById(courseId) == null) {
            throw new CourseNotFoundException();
        }
        Course course = new Course();
        course.setId(courseId);
        seminar.setCourse(course);
        return seminarMapper.insertSeminarByCourseId(seminar);
    }
}
