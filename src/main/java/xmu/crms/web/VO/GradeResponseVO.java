package xmu.crms.web.VO;

import java.util.List;

public class GradeResponseVO {
    private Integer reportGrade;
    private Integer grade;
    private List<PresentationGrade> presentationGrade;

    public Integer getReportGrade() {
        return reportGrade;
    }

    public void setReportGrade(Integer reportGrade) {
        this.reportGrade = reportGrade;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public List<PresentationGrade> getPresentationGrade() {
        return presentationGrade;
    }

    public void setPresentationGrade(List<PresentationGrade> presentationGrade) {
        this.presentationGrade = presentationGrade;
    }
}
