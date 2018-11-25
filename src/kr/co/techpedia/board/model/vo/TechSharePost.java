package kr.co.techpedia.board.model.vo;

public class TechSharePost {
	private int postNo;
	private String shrTitle;
	private String shrContent;
	private int shrWriter;
	private String shrWriterName;
	private String shrDate;
	private int shrCnt;
	
	
	
	public TechSharePost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TechSharePost(int postNo, String shrTitle, String shrContent, int shrWriter, String shrWriterName,
			String shrDate, int shrCnt) {
		super();
		this.postNo = postNo;
		this.shrTitle = shrTitle;
		this.shrContent = shrContent;
		this.shrWriter = shrWriter;
		this.shrWriterName = shrWriterName;
		this.shrDate = shrDate;
		this.shrCnt = shrCnt;
	}
	
	
	
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getShrTitle() {
		return shrTitle;
	}
	public void setShrTitle(String shrTitle) {
		this.shrTitle = shrTitle;
	}
	public String getShrContent() {
		return shrContent;
	}
	public void setShrContent(String shrContent) {
		this.shrContent = shrContent;
	}
	public int getShrWriter() {
		return shrWriter;
	}
	public void setShrWriter(int shrWriter) {
		this.shrWriter = shrWriter;
	}
	public String getShrWriterName() {
		return shrWriterName;
	}
	public void setShrWriterName(String shrWriterName) {
		this.shrWriterName = shrWriterName;
	}
	public String getShrDate() {
		return shrDate;
	}
	public void setShrDate(String shrDate) {
		this.shrDate = shrDate;
	}
	public int getShrCnt() {
		return shrCnt;
	}
	public void setShrCnt(int shrCnt) {
		this.shrCnt = shrCnt;
	}
	
	
	
	@Override
	public String toString() {
		String techSharePost = "-----------------------------------------------\n"
							+"postNo : "+postNo+"\n"
							+"shrTitle : "+shrTitle+"\n"
							+"shrContent : "+shrContent+"\n"
							+"shrWriter : "+shrWriter+"\n"
							+"shrWriterName : "+shrWriterName+"\n"
							+"shrDate : "+shrDate+"\n"
							+"shrCnt : "+shrCnt+"\n"
							+"-----------------------------------------------\n";
		
		return techSharePost;
	}
	
}
