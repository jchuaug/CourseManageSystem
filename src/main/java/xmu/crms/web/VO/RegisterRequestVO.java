package xmu.crms.web.VO;

public class RegisterRequestVO {
	String phone;
	String password;
	String name;
	Integer gender;
	Integer type;
	String number;
	String email;

	public RegisterRequestVO() {
		super();
	}

	@Override
	public String toString() {
		return "RegisterRequestVO [phone=" + phone + ", password=" + password + ", name=" + name + ", gender=" + gender
				+ ", type=" + type + ", number=" + number + ", email=" + email + "]";
	}

	public RegisterRequestVO(String phone, String password, String name, Integer gender, Integer type, String number,
			String email) {
		super();
		this.phone = phone;
		this.password = password;
		this.name = name;
		this.gender = gender;
		this.type = type;
		this.number = number;
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
