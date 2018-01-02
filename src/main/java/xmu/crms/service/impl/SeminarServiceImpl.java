package xmu.crms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xmu.crms.entity.*;
import xmu.crms.exception.*;
import xmu.crms.mapper.SeminarMapper;
import xmu.crms.service.FixGroupService;
import xmu.crms.service.SeminarGroupService;
import xmu.crms.service.SeminarService;
import xmu.crms.service.TopicService;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo SeminarServiceImpl
 *
 * @author drafting_dreams
 * @date 2017/12/24
 */
@Service
public class SeminarServiceImpl implements SeminarService {

    @Autowired
    SeminarMapper seminarMapper;

    @Autowired
    SeminarGroupService seminarGroupService;

    @Autowired
    FixGroupService fixGroupService;

    @Autowired
    TopicService topicService;


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
        if (seminarId == null) {
            throw new IllegalArgumentException();
        }
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
        seminarMapper.insertSeminarByCourseId(seminar);
        if (seminar.getFixed()) {
            // insert into event table
//            fixGroupService.fixedGroupToSeminarGroup(courseId);
        }
        //todo
        return null;
    }

    @Override
    public Seminar getCurrentSeminar(BigInteger courseId) {
        return seminarMapper.getCurrentSeminar(courseId);
    }

    @Override
    public void randomGrouping(BigInteger seminarId, BigInteger classId) {
        List<User> users = seminarMapper.getAllAttendanceStudent(seminarId, classId);
        List<Topic> topics = topicService.listTopicBySeminarId(seminarId);
        List<List<User>> groups = new ArrayList<>();
        List<Topic> topicSelect = new ArrayList<>();
        int topicIndex = 0;

        List<User> group = null;
        for (int i = 0; i != users.size(); ++i) {
            if (i % 3 == 0) {
                group = new ArrayList<>();
                groups.add(group);
                topicSelect.add(topics.get(topicIndex % topics.size()));
                topicIndex += 1;
            }

            assert group != null;
            group.add(users.get(i));
        }

        groups.forEach(oneGroup -> {
            SeminarGroup seminarGroup = new SeminarGroup();
            seminarGroup.setClassInfo(new ClassInfo((classId)));
            seminarGroup.setSeminar(new Seminar(seminarId));
            BigInteger insertedId = seminarGroupService.insertSeminarGroup(seminarGroup);

            for (User user : oneGroup) {
                try {
                    seminarGroupService.insertSeminarGroupMemberById(user.getId(), insertedId);
                } catch (GroupNotFoundException | UserNotFoundException | InvalidOperationException e) {
                    e.printStackTrace();
                }
            }

            try {
                seminarGroupService.insertTopicByGroupId(insertedId, topics.get(groups.indexOf(oneGroup)).getId());
            } catch (GroupNotFoundException | TopicNotFoundException e) {
                e.printStackTrace();
            }
        });
    }
}
