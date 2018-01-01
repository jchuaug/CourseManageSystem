package xmu.crms.web.VO;

import java.math.BigInteger;

public class GroupScore {
    private BigInteger id;
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }
}
