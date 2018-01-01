package xmu.crms.web.VO;

import java.math.BigInteger;
import java.util.List;

public class SeminarDetailResponseVO {
    private BigInteger id;
    private String name;
    private String startTime;
    private String endTime;
    private String site;
    private String teacherName;
    private String teacherEmail;
    private String course;
    private String groupingMethod;
    private List<ClassResponseVO> classes;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherEmail() {
        return teacherEmail;
    }

    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }


    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse() {
        return course;
    }

    public void setGroupingMethod(String groupingMethod) {
        this.groupingMethod = groupingMethod;
    }

    public String getGroupingMethod() {
        return groupingMethod;
    }

    public List<ClassResponseVO> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassResponseVO> classes) {
        this.classes = classes;
    }
}
