package xmu.crms.web.VO;

public class WeChatLoginRequestVO {
    private String number;
    private String code;
    private SchoolRequestVO school;
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolRequestVO getSchool() {
        return school;
    }

    public void setSchool(SchoolRequestVO school) {
        this.school = school;
    }
}
