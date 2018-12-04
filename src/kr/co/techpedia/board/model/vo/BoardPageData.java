package kr.co.techpedia.board.model.vo;

import java.util.ArrayList;

import kr.co.techpedia.member.model.vo.TpMember;

public class BoardPageData {
	private ArrayList<Notice> noticeList;
	private ArrayList<TechSharePost> techSharePostL;
	private ArrayList<TechSupportPost> techSupportPostL;
	private ArrayList<TpMember> memberList;
	private String pageNavi;
	
	
	public BoardPageData() {
		super();
	}
	public BoardPageData(ArrayList<Notice> noticeList, ArrayList<TechSharePost> techSharePostL,
			ArrayList<TechSupportPost> techSupportPostL, ArrayList<TpMember> memberList, String pageNavi) {
		super();
		this.noticeList = noticeList;
		this.techSharePostL = techSharePostL;
		this.techSupportPostL = techSupportPostL;
		this.memberList = memberList;
		this.pageNavi = pageNavi;
	}
	
	
	public ArrayList<Notice> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(ArrayList<Notice> noticeList) {
		this.noticeList = noticeList;
	}
	public ArrayList<TechSharePost> getTechSharePostL() {
		return techSharePostL;
	}
	public void setTechSharePostL(ArrayList<TechSharePost> techSharePostL) {
		this.techSharePostL = techSharePostL;
	}
	public ArrayList<TechSupportPost> getTechSupportPostL() {
		return techSupportPostL;
	}
	public void setTechSupportPostL(ArrayList<TechSupportPost> techSupportPostL) {
		this.techSupportPostL = techSupportPostL;
	}
	public String getPageNavi() {
		return pageNavi;
	}
	public void setPageNavi(String pageNavi) {
		this.pageNavi = pageNavi;
	}
	public ArrayList<TpMember> getMemberList() {
		return memberList;
	}
	public void setMemberList(ArrayList<TpMember> memberList) {
		this.memberList = memberList;
	}
	
	
	
	@Override
	public String toString() {
		String boardPageData = "-----------------------------------------------\n"
							+"noticeList \n"+noticeList+"\n\n"
							+"techSharePostL \n"+techSharePostL+"\n\n"
							+"techSupportPostL \n"+techSupportPostL+"\n\n"
							+"memberList \n"+memberList+"\n\n"
							+"pageNavi : "+pageNavi+"\n"
							+"-----------------------------------------------\n";
		
		return boardPageData;
	}
}
