package kr.co.techpedia.member.model.vo;

public class CompanyInfo {
	private int compNo;
	private String compName;
	private String compCTG;
	
	
	public CompanyInfo() {
		super();
	}
	public CompanyInfo(int compNo, String compName, String compCTG) {
		super();
		this.compNo = compNo;
		this.compName = compName;
		this.compCTG = compCTG;
	}
	
	
	public int getCompNo() {
		return compNo;
	}
	public void setCompNo(int compNo) {
		this.compNo = compNo;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public String getCompCTG() {
		return compCTG;
	}
	public void setCompCTG(String compCTG) {
		this.compCTG = compCTG;
	}
	
	@Override
	public String toString() {
		String companyInfo = "-----------------------------------------------\n"
							+"compNo : "+compNo+"\n"
							+"compName : "+compName+"\n"
							+"compCTG : "+compCTG+"\n"
							+"-----------------------------------------------\n";
		
		return companyInfo;
	}
}
