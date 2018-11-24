package kr.co.techpedia.member.model.vo;

public class MemberSession {
	private int memberNo;
	private String memberId;
	private String memberTypeCD;
	private int compNo;
	private char memberActive;
	private String memberPhoto;
	
	
	
	public MemberSession() {
		super();
	}
	public MemberSession(int memberNo, String memberId, String memberTypeCD, int compNo, char memberActive,
			String memberPhoto) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberTypeCD = memberTypeCD;
		this.compNo = compNo;
		this.memberActive = memberActive;
		this.memberPhoto = memberPhoto;
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
	
	
	@Override
	public String toString() {
		String memberSession = "-----------------------------------------------\n"
							+"memberNo : "+memberNo+"\n"
							+"memberId : "+memberId+"\n"
							+"memberTypeCD : "+memberTypeCD+"\n"
							+"compNo : "+compNo+"\n"
							+"memberActive : "+memberActive+"\n"
							+"memberPhoto : "+memberPhoto+"\n"
							+"-----------------------------------------------\n";
		
		return memberSession;
	}
	
}
