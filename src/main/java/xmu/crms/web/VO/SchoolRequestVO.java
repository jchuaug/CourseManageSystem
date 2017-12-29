package xmu.crms.web.VO;

/**
 *
 * @author caistrong
 *
 */

public class SchoolRequestVO {
    private String name;
    private String province;
    private String city;

    public SchoolRequestVO(String name, String province, String city) {
        this.name = name;
        this.province = province;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
