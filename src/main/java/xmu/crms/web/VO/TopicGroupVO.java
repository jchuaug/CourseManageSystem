package xmu.crms.web.VO;

import java.math.BigInteger;

/**
 * Demo TopicGroupVO
 *
 * @author drafting_dreams
 * @date 2017/12/29
 */
public class TopicGroupVO {
    private BigInteger id;
    private String name;

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

    public TopicGroupVO() {}

    public TopicGroupVO(BigInteger id, String name) {
        this.id = id;
        this.name = name;
    }
}
