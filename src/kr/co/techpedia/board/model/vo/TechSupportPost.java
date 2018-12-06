package kr.co.techpedia.board.model.vo;

public class TechSupportPost {
	private int postNo;
	private String spptTitle;
	private String spptContent;
	private int spptWriter;
	private String spptWriterName;
	private int spptWriterCompNo;
	private int spptEng;
	private String spptEngName;
	private String spptDate;
	private String spptStatcd;
	private String spptStatName;
	private int spptCnt;
	private char spptEngck;
	private int cmmCnt;
	private String attName;
	
	
	public TechSupportPost() {
		super();
	}
	public TechSupportPost(int postNo, String spptTitle, String spptContent, int spptWriter, String spptWriterName,
			int spptWriterCompNo, int spptEng, String spptEngName, String spptDate, String spptStatcd,
			String spptStatName, int spptCnt, char spptEngck, int cmmCnt, String attName) {
		super();
		this.postNo = postNo;
		this.spptTitle = spptTitle;
		this.spptContent = spptContent;
		this.spptWriter = spptWriter;
		this.spptWriterName = spptWriterName;
		this.spptWriterCompNo = spptWriterCompNo;
		this.spptEng = spptEng;
		this.spptEngName = spptEngName;
		this.spptDate = spptDate;
		this.spptStatcd = spptStatcd;
		this.spptStatName = spptStatName;
		this.spptCnt = spptCnt;
		this.spptEngck = spptEngck;
		this.cmmCnt = cmmCnt;
		this.attName = attName;
	}
	
	
	
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getSpptTitle() {
		return spptTitle;
	}
	public void setSpptTitle(String spptTitle) {
		this.spptTitle = spptTitle;
	}
	public String getSpptContent() {
		return spptContent;
	}
	public void setSpptContent(String spptContent) {
		this.spptContent = spptContent;
	}
	public int getSpptWriter() {
		return spptWriter;
	}
	public void setSpptWriter(int spptWriter) {
		this.spptWriter = spptWriter;
	}
	public String getSpptWriterName() {
		return spptWriterName;
	}
	public void setSpptWriterName(String spptWriterName) {
		this.spptWriterName = spptWriterName;
	}
	public int getSpptWriterCompNo() {
		return spptWriterCompNo;
	}
	public void setSpptWriterCompNo(int spptWriterCompNo) {
		this.spptWriterCompNo = spptWriterCompNo;
	}
	public int getSpptEng() {
		return spptEng;
	}
	public void setSpptEng(int spptEng) {
		this.spptEng = spptEng;
	}
	public String getSpptEngName() {
		return spptEngName;
	}
	public void setSpptEngName(String spptEngName) {
		this.spptEngName = spptEngName;
	}
	public String getSpptDate() {
		return spptDate;
	}
	public void setSpptDate(String spptDate) {
		this.spptDate = spptDate;
	}
	public String getSpptStatcd() {
		return spptStatcd;
	}
	public void setSpptStatcd(String spptStatcd) {
		this.spptStatcd = spptStatcd;
	}
	public String getSpptStatName() {
		return spptStatName;
	}
	public void setSpptStatName(String spptStatName) {
		this.spptStatName = spptStatName;
	}
	public int getSpptCnt() {
		return spptCnt;
	}
	public void setSpptCnt(int spptCnt) {
		this.spptCnt = spptCnt;
	}
	public char getSpptEngck() {
		return spptEngck;
	}
	public void setSpptEngck(char spptEngck) {
		this.spptEngck = spptEngck;
	}
	public int getCmmCnt() {
		return cmmCnt;
	}
	public void setCmmCnt(int cmmCnt) {
		this.cmmCnt = cmmCnt;
	}
	public String getAttName() {
		return attName;
	}
	public void setAttName(String attName) {
		this.attName = attName;
	}
	
	
	
	@Override
	public String toString() {
		String techSupportPost = "-----------------------------------------------\n"
							+"postNo : "+postNo+"\n"
							+"spptTitle : "+spptTitle+"\n"
							+"spptContent : "+spptContent+"\n"
							+"spptWriter : "+spptWriter+"\n"
							+"spptWriterName : "+spptWriterName+"\n"
							+"spptWriterCompNo : "+spptWriterCompNo+"\n"
							+"spptEng : "+spptEng+"\n"
							+"spptEngName : "+spptEngName+"\n"
							+"spptDate : "+spptDate+"\n"
							+"spptStatcd : "+spptStatcd+"\n"
							+"spptStatName : "+spptStatName+"\n"
							+"spptCnt : "+spptCnt+"\n"
							+"spptEngck : "+spptEngck+"\n"
							+"cmmCnt : "+cmmCnt+"\n"
							+"attName : "+attName+"\n"
							+"-----------------------------------------------\n";
		
		return techSupportPost;
	}
	
}
