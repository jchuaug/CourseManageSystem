package xmu.crms.service;

import xmu.crms.entity.*;

import static junit.framework.TestCase.assertNotNull;

public class EntityTestMethods {
    public static void testTopic(Topic topic) {
        assertNotNull(topic);
        assertNotNull(topic.getId());
        assertNotNull(topic.getName());
        assertNotNull(topic.getDescription());
        assertNotNull(topic.getGroupNumberLimit());
        assertNotNull(topic.getGroupStudentLimit());
        assertNotNull(topic.getSerial());
        assertNotNull(topic.getSeminar());
        Seminar seminar = topic.getSeminar();
        testSeminar(seminar);
    }

    public static void testSeminar(Seminar seminar) {
        assertNotNull(seminar.getId());
        assertNotNull(seminar.getName());
        assertNotNull(seminar.getDescription());
        assertNotNull(seminar.getStartTime());
        assertNotNull(seminar.getEndTime());
        assertNotNull(seminar.getFixed());
        assertNotNull(seminar.getCourse());

        Course course = seminar.getCourse();
        testCourse(course);
    }

    public static void testCourse(Course course) {
        assertNotNull(course.getId());
        assertNotNull(course.getName());
        assertNotNull(course.getStartDate());
        assertNotNull(course.getEndDate());
        assertNotNull(course.getTeacher());
        assertNotNull(course.getDescription());
        assertNotNull(course.getReportPercentage());
        assertNotNull(course.getPresentationPercentage());
        assertNotNull(course.getFivePointPercentage());
        assertNotNull(course.getFourPointPercentage());
        assertNotNull(course.getThreePointPercentage());

        User user = course.getTeacher();
        testUser(user);

    }

    public static void testUser(User user) {
        assertNotNull(user.getId());
        assertNotNull(user.getPhone());
        // database default to null
//        assertNotNull(user.getWechatId());
//        assertNotNull(user.getOpenid());
//        assertNotNull(user.getAvatar());
        assertNotNull(user.getPassword());
        assertNotNull(user.getName());
        assertNotNull(user.getSchool());
        assertNotNull(user.getGender());
        assertNotNull(user.getType());
        assertNotNull(user.getNumber());
        assertNotNull(user.getEducation());
        assertNotNull(user.getTitle());
        assertNotNull(user.getEmail());

        School school = user.getSchool();
        testSchool(school);
    }

    public static void testSchool(School school) {
        assertNotNull(school.getId());
        assertNotNull(school.getName());
        assertNotNull(school.getProvince());
        assertNotNull(school.getCity());
    }

}
