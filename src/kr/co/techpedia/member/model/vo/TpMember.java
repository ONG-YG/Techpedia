package kr.co.techpedia.member.model.vo;

public class TpMember {
	
	private int memberNo;
	private String memberId;
	private String memberPw;
	private String memberName;
	private String memberPrivatePhone;
	private String memberCompanyPhone;
	private String memberEmail;
	private String memberTypeCD;
	private int compNo;
	private String companyMemNo;
	private char memberActive;
	private String memberPhoto;
	private String enrollDate;
	private String approvalDate;
	
	
	public TpMember() {
		super();
	}
	public TpMember(int memberNo, String memberId, String memberPw, String memberName, String memberPrivatePhone,
			String memberCompanyPhone, String memberEmail, String memberTypeCD, int compNo, String companyMemNo,
			char memberActive, String memberPhoto, String enrollDate, String approvalDate) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberPw = memberPw;
		this.memberName = memberName;
		this.memberPrivatePhone = memberPrivatePhone;
		this.memberCompanyPhone = memberCompanyPhone;
		this.memberEmail = memberEmail;
		this.memberTypeCD = memberTypeCD;
		this.compNo = compNo;
		this.companyMemNo = companyMemNo;
		this.memberActive = memberActive;
		this.memberPhoto = memberPhoto;
		this.enrollDate = enrollDate;
		this.approvalDate = approvalDate;
	}
	
	
	
	public int getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberPw() {
		return memberPw;
	}
	public void setMemberPw(String memberPw) {
		this.memberPw = memberPw;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPrivatePhone() {
		return memberPrivatePhone;
	}
	public void setMemberPrivatePhone(String memberPrivatePhone) {
		this.memberPrivatePhone = memberPrivatePhone;
	}
	public String getMemberCompanyPhone() {
		return memberCompanyPhone;
	}
	public void setMemberCompanyPhone(String memberCompanyPhone) {
		this.memberCompanyPhone = memberCompanyPhone;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberTypeCD() {
		return memberTypeCD;
	}
	public void setMemberTypeCD(String memberTypeCD) {
		this.memberTypeCD = memberTypeCD;
	}
	public int getCompNo() {
		return compNo;
	}
	public void setCompNo(int compNo) {
		this.compNo = compNo;
	}
	public String getCompanyMemNo() {
		return companyMemNo;
	}
	public void setCompanyMemNo(String companyMemNo) {
		this.companyMemNo = companyMemNo;
	}
	public char getMemberActive() {
		return memberActive;
	}
	public void setMemberActive(char memberActive) {
		this.memberActive = memberActive;
	}
	public String getMemberPhoto() {
		return memberPhoto;
	}
	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getApprovalDate() {
		return approvalDate;
	}
	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}
	
	
	
	@Override
	public String toString() {
		String memberInfo = "-----------------------------------------------\n"
							+"memberNo : "+memberNo+"\n"
							+"memberId : "+memberId+"\n"
							+"memberPw : "+memberPw+"\n"
							+"memberName : "+memberName+"\n"
							+"memberPrivatePhone : "+memberPrivatePhone+"\n"
							+"memberCompanyPhone : "+memberCompanyPhone+"\n"
							+"memberEmail : "+memberEmail+"\n"
							+"memberTypeCD : "+memberTypeCD+"\n"
							+"compNo : "+compNo+"\n"
							+"companyMemNo : "+companyMemNo+"\n"
							+"memberActive : "+memberActive+"\n"
							+"memberPhoto : "+memberPhoto+"\n"
							+"enrollDate : "+enrollDate+"\n"
							+"approvalDate : "+approvalDate+"\n"
							+"-----------------------------------------------\n";
		
		return memberInfo;
	}
	
	
}
