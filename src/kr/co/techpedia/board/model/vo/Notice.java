package kr.co.techpedia.board.model.vo;

public class Notice {
	
	private int postNo;
	private String ntcTitle;
	private String ntcContent;
	private int ntcWriterNo;
	private String ntcWriterName;
	private String ntcDate;
	private char ntcMainview;
	private int ntcCnt;
	private String ntcGradeCD;
	private String ngrdName;
	
	
	public Notice() {
		super();
	}
	public Notice(int postNo, String ntcTitle, String ntcContent, int ntcWriterNo, String ntcWriterName, String ntcDate,
			char ntcMainview, int ntcCnt, String ntcGradeCD, String ngrdName) {
		super();
		this.postNo = postNo;
		this.ntcTitle = ntcTitle;
		this.ntcContent = ntcContent;
		this.ntcWriterNo = ntcWriterNo;
		this.ntcWriterName = ntcWriterName;
		this.ntcDate = ntcDate;
		this.ntcMainview = ntcMainview;
		this.ntcCnt = ntcCnt;
		this.ntcGradeCD = ntcGradeCD;
		this.ngrdName = ngrdName;
	}




	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getNtcTitle() {
		return ntcTitle;
	}
	public void setNtcTitle(String ntcTitle) {
		this.ntcTitle = ntcTitle;
	}
	public String getNtcContent() {
		return ntcContent;
	}
	public void setNtcContent(String ntcContent) {
		this.ntcContent = ntcContent;
	}
	public int getNtcWriterNo() {
		return ntcWriterNo;
	}
	public void setNtcWriterNo(int ntcWriterNo) {
		this.ntcWriterNo = ntcWriterNo;
	}
	public String getNtcWriterName() {
		return ntcWriterName;
	}
	public void setNtcWriterName(String ntcWriterName) {
		this.ntcWriterName = ntcWriterName;
	}
	public String getNtcDate() {
		return ntcDate;
	}
	public void setNtcDate(String ntcDate) {
		this.ntcDate = ntcDate;
	}
	public char getNtcMainview() {
		return ntcMainview;
	}
	public void setNtcMainview(char ntcMainview) {
		this.ntcMainview = ntcMainview;
	}
	public int getNtcCnt() {
		return ntcCnt;
	}
	public void setNtcCnt(int ntcCnt) {
		this.ntcCnt = ntcCnt;
	}
	public String getNtcGradeCD() {
		return ntcGradeCD;
	}
	public void setNtcGradeCD(String ntcGradeCD) {
		this.ntcGradeCD = ntcGradeCD;
	}
	public String getNgrdName() {
		return ngrdName;
	}
	public void setNgrdName(String ngrdName) {
		this.ngrdName = ngrdName;
	}
	
	
	
	@Override
	public String toString() {
		String noticeInfo = "-----------------------------------------------\n"
							+"postNo : "+postNo+"\n"
							+"ntcTitle : "+ntcTitle+"\n"
							+"ntcContent : "+ntcContent+"\n"
							+"ntcWriterNo : "+ntcWriterNo+"\n"
							+"ntcWriterName : "+ntcWriterName+"\n"
							+"ntcDate : "+ntcDate+"\n"
							+"ntcMainview : "+ntcMainview+"\n"
							+"ntcCnt : "+ntcCnt+"\n"
							+"ngrdName : "+ngrdName+"\n"
							+"-----------------------------------------------\n";
		
		return noticeInfo;
	}
}
