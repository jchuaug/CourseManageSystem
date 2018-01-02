package xmu.crms.entity;

import java.math.BigInteger;
import java.util.Date;
/**
 * 
* <p>Title: Course.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
public class Event {

    private BigInteger id;
    private String beanName;
    private String methodName;
    private String parameter;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

	@Override
	public String toString() {
		return "Event [id=" + id + ", beanName=" + beanName + ", methodName=" + methodName + ", parameter=" + parameter
				+ "]";
	}
}