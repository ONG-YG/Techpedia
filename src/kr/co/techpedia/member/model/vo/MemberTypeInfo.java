package kr.co.techpedia.member.model.vo;

public class MemberTypeInfo {
	private String memberTypeCD;
	private String memberTypeName;
	private String compCTG;
	
	
	public MemberTypeInfo() {
		super();
	}
	public MemberTypeInfo(String memberTypeCD, String memberTypeName, String compCTG) {
		super();
		this.memberTypeCD = memberTypeCD;
		this.memberTypeName = memberTypeName;
		this.compCTG = compCTG;
	}
	
	
	public String getMemberTypeCD() {
		return memberTypeCD;
	}
	public void setMemberTypeCD(String memberTypeCD) {
		this.memberTypeCD = memberTypeCD;
	}
	public String getMemberTypeName() {
		return memberTypeName;
	}
	public void setMemberTypeName(String memberTypeName) {
		this.memberTypeName = memberTypeName;
	}
	public String getCompCTG() {
		return compCTG;
	}
	public void setCompCTG(String compCTG) {
		this.compCTG = compCTG;
	}
	
	
	@Override
	public String toString() {
		String memberTypeInfo = "-----------------------------------------------\n"
							+"memberTypeCD : "+memberTypeCD+"\n"
							+"memberTypeName : "+memberTypeName+"\n"
							+"compCTG : "+compCTG+"\n"
							+"-----------------------------------------------\n";
		
		return memberTypeInfo;
	}
}
