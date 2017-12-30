package xmu.crms.web.VO;

import java.util.List;

public class GradeRequestVO {
    private Integer reportGrade;
    private List<PresentationGrade> presentationGrades;

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
}
