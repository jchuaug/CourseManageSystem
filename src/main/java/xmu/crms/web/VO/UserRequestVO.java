package xmu.crms.web.VO;

public class UserRequestVO {
    private String phone;
    private String password;
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserRequestVO(String phone, String password) {
        super();
        this.phone = phone;
        this.password = password;
    }
    

}
