package kr.co.techpedia.member.model.vo;

public class MemberSession {
	private int memberNo;
	private String memberId;
	private String memberTypeCD;
	private int compNo;
	private char memberActive;
	private String memberPhoto;
	private String mypageMenu;
	private String adminMenu;
	
	
	public MemberSession() {
		super();
	}
	public MemberSession(int memberNo, String memberId, String memberTypeCD, int compNo, char memberActive,
			String memberPhoto, String mypageMenu, String adminMenu) {
		super();
		this.memberNo = memberNo;
		this.memberId = memberId;
		this.memberTypeCD = memberTypeCD;
		this.compNo = compNo;
		this.memberActive = memberActive;
		this.memberPhoto = memberPhoto;
		this.mypageMenu = mypageMenu;
		this.adminMenu = adminMenu;
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
	public String getMypageMenu() {
		return mypageMenu;
	}
	public void setMypageMenu(String mypageMenu) {
		this.mypageMenu = mypageMenu;
	}
	public String getAdminMenu() {
		return adminMenu;
	}
	public void setAdminMenu(String adminMenu) {
		this.adminMenu = adminMenu;
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
							+"mypageMenu : "+mypageMenu+"\n"
							+"adminMenu : "+adminMenu+"\n"
							+"-----------------------------------------------\n";
		
		return memberSession;
	}
	
}
