package xmu.crms.entity;

import java.math.BigInteger;
/**
 * 
* <p>Title: Course.java<／p>
* <p>Description: <／p>
* <p>Copyright: Copyright (c) 2018<／p>
 * @author Jackey
 * @date 2018年1月3日
 */
public class School {
	private BigInteger id;
	private String name;
	private String province;
	private String city;

	public School() {
	};

	public School(BigInteger id, String name, String province, String city) {
		super();
		this.id = id;
		this.name = name;
		this.province = province;
		this.city = city;
	}

	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", province=" + province + ", city=" + city + "]";
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
