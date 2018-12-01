package kr.co.techpedia.board.model.vo;

public class NoticeGrade {
	
	private String ntcGradeCD;
	private String ngrdName;
	
	
	
	public NoticeGrade() {
		super();
	}
	public NoticeGrade(String ntcGradeCD, String ngrdName) {
		super();
		this.ntcGradeCD = ntcGradeCD;
		this.ngrdName = ngrdName;
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
		String noticeGrade = "-----------------------------------------------\n"
							+"ntcGradeCD : "+ntcGradeCD+"\n"
							+"ngrdName : "+ngrdName+"\n"
							+"-----------------------------------------------\n";
		
		return noticeGrade;
	}
}
