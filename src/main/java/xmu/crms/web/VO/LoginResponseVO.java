package xmu.crms.web.VO;

import xmu.crms.entity.User;

import java.math.BigInteger;

public class LoginResponseVO {
    private Integer statusCode;// 状态码
    private String msg;// 返回信息
    private BigInteger id;// 学号
    private String type;// 身份类型
    private String name;// 姓名
    private String jwt;// 返回的token

    public LoginResponseVO(int i, String msg, User user, String jwt) {
        this.statusCode = i;
        this.msg = msg;
        this.id = user.getId();
        this.type = user.getType() == 1 ? "teacher" : "student";
        this.name = user.getName();
        this.jwt = jwt;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public String toString() {
        return "LoginResponseVO [statusCode=" + statusCode + ", msg=" + msg + ", id=" + id + ", type=" + type + ", name=" + name
                + ", jwt=" + jwt + "]";
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LoginResponseVO(Integer statusCode, String msg, BigInteger id, String type, String name, String jwt) {
        super();
        this.statusCode = statusCode;
        this.msg = msg;
        this.id = id;
        this.type = type;
        this.name = name;
        this.jwt = jwt;
    }

    public LoginResponseVO(Integer statusCode, String msg) {
        super();
        this.statusCode = statusCode;
        this.msg = msg;
    }


}
