package xmu.crms.web.VO;

import java.math.BigInteger;

public class AttendanceRequestVO {
	private double longitude;
	private double latitude;
	private double elevation;
	private int status;
	private BigInteger seminarId;


	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}


	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public BigInteger getSeminarId() {
		return seminarId;
	}

	public void setSeminarId(BigInteger seminarId) {
		this.seminarId = seminarId;
	}
}
