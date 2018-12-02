package kr.co.techpedia.board.model.vo;

public class Comment {
	
	private int cmmNo;
	private String cmmContent;
	private int cmmWriterNo;
	private String cmmWriterName;
	private String cmmDate;
	private int cmmParent;
	private String brdCode;
	private char ansSelected;
	
	
	public Comment() {
		super();
	}
	public Comment(int cmmNo, String cmmContent, int cmmWriterNo, String cmmWriterName, String cmmDate, int cmmParent,
			String brdCode, char ansSelected) {
		super();
		this.cmmNo = cmmNo;
		this.cmmContent = cmmContent;
		this.cmmWriterNo = cmmWriterNo;
		this.cmmWriterName = cmmWriterName;
		this.cmmDate = cmmDate;
		this.cmmParent = cmmParent;
		this.brdCode = brdCode;
		this.ansSelected = ansSelected;
	}
	
	
	public int getCmmNo() {
		return cmmNo;
	}
	public void setCmmNo(int cmmNo) {
		this.cmmNo = cmmNo;
	}
	public String getCmmContent() {
		return cmmContent;
	}
	public void setCmmContent(String cmmContent) {
		this.cmmContent = cmmContent;
	}
	public int getCmmWriterNo() {
		return cmmWriterNo;
	}
	public void setCmmWriterNo(int cmmWriterNo) {
		this.cmmWriterNo = cmmWriterNo;
	}
	public String getCmmWriterName() {
		return cmmWriterName;
	}
	public void setCmmWriterName(String cmmWriterName) {
		this.cmmWriterName = cmmWriterName;
	}
	public String getCmmDate() {
		return cmmDate;
	}
	public void setCmmDate(String cmmDate) {
		this.cmmDate = cmmDate;
	}
	public int getCmmParent() {
		return cmmParent;
	}
	public void setCmmParent(int cmmParent) {
		this.cmmParent = cmmParent;
	}
	public String getBrdCode() {
		return brdCode;
	}
	public void setBrdCode(String brdCode) {
		this.brdCode = brdCode;
	}
	public char getAnsSelected() {
		return ansSelected;
	}
	public void setAnsSelected(char ansSelected) {
		this.ansSelected = ansSelected;
	}
	
	
	
	@Override
	public String toString() {
		String comment = "-----------------------------------------------\n"
							+"cmmNo : "+cmmNo+"\n"
							+"cmmContent : "+cmmContent+"\n"
							+"cmmWriterNo : "+cmmWriterNo+"\n"
							+"cmmWriterName : "+cmmWriterName+"\n"
							+"cmmDate : "+cmmDate+"\n"
							+"cmmParent : "+cmmParent+"\n"
							+"brdCode : "+brdCode+"\n"
							+"ansSelected : "+ansSelected+"\n"
							+"-----------------------------------------------\n";
		
		return comment;
	}
}