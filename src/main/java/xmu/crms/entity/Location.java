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
public class Location {
	private BigInteger id;
	private ClassInfo classInfo;
	private Seminar seminar;
	private Double longitude;
	private Double latitude;
	private Integer status;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public ClassInfo getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(ClassInfo classInfo) {
		this.classInfo = classInfo;
	}
	public Seminar getSeminar() {
		return seminar;
	}
	public void setSeminar(Seminar seminar) {
		this.seminar = seminar;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Location() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Location(BigInteger id, ClassInfo classInfo, Seminar seminar, Double longitude, Double latitude,
			Integer status) {
		super();
		this.id = id;
		this.classInfo = classInfo;
		this.seminar = seminar;
		this.longitude = longitude;
		this.latitude = latitude;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Location [id=" + id + ", classInfo=" + classInfo + ", seminar=" + seminar + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", status=" + status + "]";
	}

}
