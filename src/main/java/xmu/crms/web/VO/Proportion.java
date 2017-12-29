package xmu.crms.web.VO;

public class Proportion {

	private Integer report;
	
	private Integer presentation;
	
	private Integer c;
	
	private Integer b;
	
	private Integer a;

	public Integer getReport() {
		return report;
	}

	public void setReport(Integer report) {
		this.report = report;
	}

	public Integer getPresentation() {
		return presentation;
	}

	public void setPresentation(Integer presentation) {
		this.presentation = presentation;
	}

	public Integer getC() {
		return c;
	}

	public void setC(Integer c) {
		this.c = c;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Proportion(Integer report, Integer presentation, Integer c, Integer b, Integer a) {
		super();
		this.report = report;
		this.presentation = presentation;
		this.c = c;
		this.b = b;
		this.a = a;
	}

	public Proportion() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Proportion [report=" + report + ", presentation=" + presentation + ", c=" + c + ", b=" + b + ", a=" + a
				+ "]";
	}
	
	
	
	
}
