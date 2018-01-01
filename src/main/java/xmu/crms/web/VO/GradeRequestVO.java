package xmu.crms.web.VO;

import java.util.List;

public class GradeRequestVO {
    private Integer reportGrade;
    private List<PresentationGrade> presentationGrades;
    private List<GroupScore> groups;

    public Integer getReportGrade() {
        return reportGrade;
    }

    public void setReportGrade(Integer reportGrade) {
        this.reportGrade = reportGrade;
    }

    public List<PresentationGrade> getPresentationGrades() {
        return presentationGrades;
    }

    public void setPresentationGrades(List<PresentationGrade> presentationGrades) {
        this.presentationGrades = presentationGrades;
    }

    public List<GroupScore> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupScore> groups) {
        this.groups = groups;
    }
}
