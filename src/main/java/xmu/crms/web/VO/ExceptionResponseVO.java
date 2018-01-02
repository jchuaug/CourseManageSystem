package xmu.crms.web.VO;

public class ExceptionResponseVO {
	// http 状态码
	private int code;

	// 返回信息
	private String msg;

	public ExceptionResponseVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


}
