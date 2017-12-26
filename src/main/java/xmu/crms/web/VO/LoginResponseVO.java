package xmu.crms.web.VO;

import java.math.BigInteger;

public class LoginResponseVO {
    private Integer code;// 状态码
    private String msg;// 返回信息
    private BigInteger id;// 学号
    private String type;// 身份类型
    private String name;// 姓名
    private String jwt;// 返回的token

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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
        return "LoginResponseVO [code=" + code + ", msg=" + msg + ", id=" + id + ", type=" + type + ", name=" + name
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

    public LoginResponseVO(Integer code, String msg, BigInteger id, String type, String name, String jwt) {
        super();
        this.code = code;
        this.msg = msg;
        this.id = id;
        this.type = type;
        this.name = name;
        this.jwt = jwt;
    }

    public LoginResponseVO(Integer code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }
    

}
