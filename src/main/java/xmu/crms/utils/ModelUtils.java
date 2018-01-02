package xmu.crms.utils;

import xmu.crms.entity.*;
import xmu.crms.web.VO.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yjj
 * @date 2017/12/29
 */
public class ModelUtils {

    public static ClassResponseVO classInfoToClassResponseVO(ClassInfo classInfo, Integer numStudent) {
        User teacher = classInfo.getCourse().getTeacher();
        Course course = classInfo.getCourse();
        ClassResponseVO classVO = new ClassResponseVO(classInfo.getId(), classInfo.getName(), numStudent,
                classInfo.getClassTime(), classInfo.getSite(), course.getName(), teacher.getName());
        Proportion proportion = new Proportion();
        proportion.setPresentation(classInfo.getPresentationPercentage());
        proportion.setReport(classInfo.getReportPercentage());
        proportion.setA(classInfo.getFivePointPercentage());
        proportion.setB(classInfo.getFourPointPercentage());
        proportion.setC(classInfo.getThreePointPercentage());
        classVO.setProportions(proportion);
        return classVO;

    }

    public static ClassInfo ClassRequestVOToClassInfo(ClassRequestVO classRequestVO) {
        Proportion proportion = classRequestVO.getProportions();
        ClassInfo classInfo = new ClassInfo(classRequestVO.getId(), classRequestVO.getName(), null,
                classRequestVO.getSite(), classRequestVO.getTime(), null, proportion.getReport(),
                proportion.getPresentation(), proportion.getA(), proportion.getB(), proportion.getC());
        return classInfo;
    }

    public static UserResponseVO UserToUserResponseVO(User user) {
        UserResponseVO userResponseVO = new UserResponseVO();
        userResponseVO.setId(user.getId());
        userResponseVO.setName(user.getName());
        userResponseVO.setNumber(user.getNumber());
        userResponseVO.setPhone(user.getPhone());
        userResponseVO.setSchool(user.getSchool().getName());
        return userResponseVO;
    }

    public static UserResponseVO UserToLeader(User user) {
        if (user == null) {
            return null;
        }
        UserResponseVO userResponseVO = new UserResponseVO();
        userResponseVO.setId(user.getId());
        userResponseVO.setName(user.getName());
        return userResponseVO;
    }

    public static GroupResponseVO FixGroupToGroupResponseVO(FixGroup fixGroup, List<FixGroupMember> students) {
        GroupResponseVO groupResponseVO = new GroupResponseVO();
        groupResponseVO.setLeader(UserToUserResponseVO(fixGroup.getLeader()));
        List<UserResponseVO> studentVOs = new ArrayList<>();
        for (FixGroupMember fixGroupMember : students) {
            User student = fixGroupMember.getStudent();
            if (!(student.getId().equals(fixGroup.getLeader().getId()))) {
                studentVOs.add(ModelUtils.UserToUserResponseVO(student));
            }
        }
        groupResponseVO.setMembers(studentVOs);
        return groupResponseVO;
    }

    public static SeminarResponseVO SeminarInfoToSeminarResponseVO(Seminar seminar, List<Topic> topics, Integer grade) {
        SeminarResponseVO responseVO = new SeminarResponseVO();
        responseVO.setId(seminar.getId());
        responseVO.setName(seminar.getName());
        responseVO.setDescription(seminar.getDescription());
        if (seminar.getFixed() != null) {
            responseVO.setGroupingMethod(seminar.getFixed() == true ? "fixed" : "random");
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(seminar.getStartTime());
        responseVO.setStartTime(time);
        time = sdf.format(seminar.getEndTime());
        responseVO.setEndTime(time);
        List<TopicResponseVO> topicResponseVOs = new ArrayList<>();
        if (topics != null) {
            for (Topic topic : topics) {
                TopicResponseVO topicResponseVO = TopicToTopicResponseVO(topic, null);
                topicResponseVOs.add(topicResponseVO);
            }
        }
        if (grade != null) {
            responseVO.setGrade(grade);
        }
        responseVO.setTopics(topicResponseVOs);
        return responseVO;
    }

    public static TopicResponseVO TopicToTopicResponseVO(Topic topic, Integer groupLeft) {
        TopicResponseVO topicResponseVO = new TopicResponseVO();
        topicResponseVO.setId(topic.getId());
        topicResponseVO.setName(topic.getName());
        topicResponseVO.setDescription(topic.getDescription());
        topicResponseVO.setGroupMemberLimit(topic.getGroupStudentLimit());
        topicResponseVO.setGroupLimit(topic.getGroupNumberLimit());
        topicResponseVO.setGroupLeft(groupLeft);
        return topicResponseVO;
    }

    public static Seminar SeminarResponseVOToSeminar(SeminarResponseVO seminar, Seminar forSeminar) {
        Seminar seminar2 = new Seminar();
        seminar2.setId(seminar.getId());
        seminar2.setName(seminar.getName());
        seminar2.setDescription(seminar.getDescription());
        Boolean fixed = null;
        if (seminar.getGroupingMethod() != null) {
            if (seminar.getGroupingMethod().equals("fixed")) {
                fixed = true;
            } else if (seminar.getGroupingMethod().equals("random")) {
                fixed = false;
            }
        }

        seminar2.setFixed(fixed);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dstr = seminar.getStartTime();
        try {
            java.util.Date date = sdf.parse(dstr);
            seminar2.setStartTime(date);
            dstr = seminar.getEndTime();
            date = sdf.parse(dstr);
            seminar2.setEndTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (forSeminar != null) {
            seminar2.setCourse(forSeminar.getCourse());
        }

        return seminar2;
    }

    public static MySeminarResponseVO SeminarToMySeminarResponseVO(Seminar seminar, Boolean isLeader,
                                                                   Boolean areTopicsSeletced) {
        MySeminarResponseVO mySeminarResponseVO = new MySeminarResponseVO();
        mySeminarResponseVO.setId(seminar.getId());
        mySeminarResponseVO.setName(seminar.getName());
        String groupingMethod = null;
        if (seminar.getFixed() == null) {
            groupingMethod = null;
        } else if (seminar.getFixed() == true) {
            groupingMethod = "fixed";
        } else if (seminar.getFixed() == false) {
            groupingMethod = "random";
        }
        mySeminarResponseVO.setGroupingMethod(groupingMethod);
        mySeminarResponseVO.setCourseName(seminar.getCourse().getName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(seminar.getStartTime());
        mySeminarResponseVO.setStartTime(time);
        time = sdf.format(seminar.getEndTime());
        mySeminarResponseVO.setEndTime(time);
        mySeminarResponseVO.setIsLeader(isLeader);
        mySeminarResponseVO.setAreTopicsSeletced(areTopicsSeletced);
        return mySeminarResponseVO;
    }

    public static SeminarDetailResponseVO SeminarToSeminarDetailResponseVO(Seminar seminar) {
        User teacher = seminar.getCourse().getTeacher();
        SeminarDetailResponseVO seminarDetailResponseVO = new SeminarDetailResponseVO();
        seminarDetailResponseVO.setId(seminar.getId());
        seminarDetailResponseVO.setName(seminar.getName());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = sdf.format(seminar.getStartTime());
        seminarDetailResponseVO.setStartTime(time);
        time = sdf.format(seminar.getEndTime());
        seminarDetailResponseVO.setEndTime(time);
        seminarDetailResponseVO.setTeacherName(teacher.getName());
        seminarDetailResponseVO.setTeacherEmail(teacher.getEmail());
        return seminarDetailResponseVO;
    }

    public static Topic TopicResponseVOToTopic(TopicResponseVO topic) {
        Topic topic2 = new Topic();
        topic2.setName(topic.getName());
        topic2.setDescription(topic.getDescription());
        topic2.setGroupNumberLimit(topic.getGroupLimit());
        topic2.setGroupStudentLimit(topic.getGroupMemberLimit());
        topic2.setSerial(topic.getSerial());
        return topic2;
    }

    public static GroupResponseVO SeminarGroupToGroupResponseVO(SeminarGroup seminarGroup,
                                                                List<SeminarGroupTopic> topics, List<User> seminarGroupMembers) {
        GroupResponseVO groupResponseVO = new GroupResponseVO();
        if (seminarGroup.getLeader() != null) {
            groupResponseVO.setLeader(ModelUtils.UserToUserResponseVO(seminarGroup.getLeader()));
        }
        List<UserResponseVO> members = new ArrayList<>();
        if (seminarGroupMembers != null) {
            for (User seminarGroupMember : seminarGroupMembers) {
                members.add(UserToUserResponseVO(seminarGroupMember));
            }
        }
        groupResponseVO.setMembers(members);
        groupResponseVO.setId(seminarGroup.getId());
        groupResponseVO.setName(seminarGroup.getId().toString());

        if (topics != null) {
            List<TopicResponseVO> topicResponseVOs = new ArrayList<>();
            for (SeminarGroupTopic seminarGroupTopic : topics) {
                topicResponseVOs.add(TopicToTopicResponseVO(seminarGroupTopic.getTopic(), null));
            }
            groupResponseVO.setTopics(topicResponseVOs);
        }

        return groupResponseVO;
    }

    public static CourseResponseVO CourseToCourseResponseVO(Course course, Integer numClass, Integer numStudent) {
        CourseResponseVO courseResponseVO = new CourseResponseVO();
        courseResponseVO.setId(course.getId());
        courseResponseVO.setName(course.getName());
        courseResponseVO.setDescription(course.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        if(course.getStartDate()!=null&&course.getEndDate()!=null) {
        	String time = sdf.format(course.getStartDate());
        	courseResponseVO.setStartTime(time);
            time = sdf.format(course.getEndDate());
            courseResponseVO.setEndTime(time);
        }
        courseResponseVO.setNumClass(numClass);
        courseResponseVO.setNumStudent(numStudent);
        if (course.getTeacher() != null) {
            courseResponseVO.setTeacherEmail(course.getTeacher().getEmail());
            courseResponseVO.setTeacherName(course.getTeacher().getName());
        }
        return courseResponseVO;
    }

    public static Course CourseRequestVOToCourse(CourseRequestVO courseRequestVO) {
        Course course = new Course();
        course.setName(courseRequestVO.getName());
        course.setDescription(courseRequestVO.getDescription());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dstr = courseRequestVO.getStartTime();
        java.util.Date date;
        try {
            date = sdf.parse(dstr);
            course.setStartDate(date);
            date = sdf.parse(courseRequestVO.getEndTime());
            course.setEndDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Proportion proportions = courseRequestVO.getProportions();
        course.setReportPercentage(proportions.getReport());
        course.setPresentationPercentage(proportions.getPresentation());
        course.setFivePointPercentage(proportions.getA());
        course.setFourPointPercentage(proportions.getB());
        course.setThreePointPercentage(proportions.getC());
        return course;
    }


    public static SeminarGradeResponseVO SeminarGroupToSeminarGradeResponseVO(SeminarGroup seminarGroup) {
        if (seminarGroup == null) {
            return null;
        }
        SeminarGradeResponseVO seminarGradeResponseVO = new SeminarGradeResponseVO();
        seminarGradeResponseVO.setSeminarName(seminarGroup.getSeminar().getName());
        seminarGradeResponseVO.setGroupName(seminarGroup.getId().toString());
        seminarGradeResponseVO.setLeaderName(seminarGroup.getLeader().getName());
        seminarGradeResponseVO.setPresentationGrade(seminarGroup.getPresentationGrade());
        seminarGradeResponseVO.setReportGrade(seminarGroup.getReportGrade());
        seminarGradeResponseVO.setGrade(seminarGroup.getFinalGrade());
        return seminarGradeResponseVO;
    }

    public static ClassResponseVO classInfoToClassResponseVO(ClassInfo classInfo) {
        ClassResponseVO classVO = new ClassResponseVO(classInfo.getId(), classInfo.getName(), classInfo.getClassTime());
        return classVO;
    }

    public static SeminarDetailResponseVO seminarInfoToSeminarDetailResponseVO(Seminar seminar) {
        SeminarDetailResponseVO responseVO = new SeminarDetailResponseVO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        responseVO.setEndTime(sdf.format(seminar.getEndTime()));
        responseVO.setStartTime(sdf.format(seminar.getStartTime()));
        responseVO.setId(seminar.getId());
        responseVO.setName(seminar.getName());
        responseVO.setCourse(seminar.getCourse().getName());
        responseVO.setGroupingMethod(seminar.getFixed() ? "fixed" : "random");
        return responseVO;
    }

}
