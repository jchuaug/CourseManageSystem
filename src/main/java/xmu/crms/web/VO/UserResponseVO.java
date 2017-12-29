package xmu.crms.web.VO;

import xmu.crms.entity.User;

import java.math.BigInteger;

public class UserResponseVO {
    private BigInteger id;
    private String name;
    private String number;

    public UserResponseVO(User user) {
        id = user.getId();
        name = user.getName();
    }

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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
