package kr.co.techpedia.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.board.model.vo.Notice;

public class BoardDao {
	
	public ArrayList<TechSharePost> getTechShareList(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		
		String query = "SELECT TECH_SHR.*, "
						+ "MEMBER_NAME "
						+ "FROM TECH_SHR "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER) "
						+ "WHERE SHR_WRITER=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSharePost post = new TechSharePost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setShrTitle(rset.getString("SHR_TITLE"));
				//post.setShrContent(rset.getString("SHR_CONTENT"));
				//post.setShrWriter(rset.getInt("SHR_WRITER"));
				//post.setShrWriterName(rset.getString("MEMBER_NAME"));
				post.setShrDate(rset.getDate("SHR_DATE").toString());
				//post.setShrCnt(rset.getInt("SHR_CNT"));
				
				techSharePostL.add(post);
				
				//System.out.println("dao techSharePost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return techSharePostL;
	}

	public ArrayList<TechSupportPost> getTechSpptListByCopNo(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		String query = "SELECT TECH_SPPT.*, "
						+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
						+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
						+ "STAT_NAME "
						+ "FROM TECH_SPPT "
						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
						+ "JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
						+ "WHERE SPPT_WRITER=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSupportPost post = new TechSupportPost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setSpptTitle(rset.getString("SPPT_TITLE"));
				//post.setSpptContent(rset.getString("SPPT_CONTENT"));
				//post.setSpptWriter(rset.getInt("SPPT_WRITER"));
				//post.setSpptWriterName(rset.getString("SPPT_WRITERNAME"));
				//post.setSpptEng(rset.getInt(rset.getString("SPPT_ENG")));
				post.setSpptEngName(rset.getString("SPPT_ENGNAME"));
				post.setSpptDate(rset.getString("SPPT_DATE").toString());
				//Spost.setSpptDate(rset.getDate("SPPT_DATE").toString());
				//post.setSpptStatcd(rset.getString("SPPT_STATCD"));
				post.setSpptStatName(rset.getString("STAT_NAME"));
				//post.setSpptCnt(rset.getInt("SPPT_CNT"));
				post.setSpptEngck(rset.getString("SPPT_ENGCK").charAt(0));
				
				techSupportPostL.add(post);
				
				//System.out.println("dao TechSupportPost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return techSupportPostL;
	}

	public ArrayList<TechSupportPost> getTechSpptListByEngNo(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		String query = "SELECT TECH_SPPT.*, "
						+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
						+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
						+ "STAT_NAME "
						+ "FROM TECH_SPPT "
						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
						+ "JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
						+ "WHERE SPPT_ENG=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSupportPost post = new TechSupportPost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setSpptTitle(rset.getString("SPPT_TITLE"));
				//post.setSpptContent(rset.getString("SPPT_CONTENT"));
				//post.setSpptWriter(rset.getInt("SPPT_WRITER"));
				post.setSpptWriterName(rset.getString("SPPT_WRITERNAME"));
				//post.setSpptEng(rset.getInt(rset.getString("SPPT_ENG")));
				//post.setSpptEngName(rset.getString("SPPT_ENGNAME"));
				post.setSpptDate(rset.getString("SPPT_DATE").toString());
				//post.setSpptDate(rset.getDate("SPPT_DATE").toString());
				//post.setSpptStatcd(rset.getString("SPPT_STATCD"));
				post.setSpptStatName(rset.getString("STAT_NAME"));
				//post.setSpptCnt(rset.getInt("SPPT_CNT"));
				post.setSpptEngck(rset.getString("SPPT_ENGCK").charAt(0));
				
				techSupportPostL.add(post);
				
				//System.out.println("dao TechSupportPost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return techSupportPostL;
	}

	public ArrayList<TechSharePost> techShareBoardList(Connection conn, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
//		String query = "SELECT TECH_SHR.*, "
//						+ "MEMBER_NAME "
//						+ "FROM TECH_SHR "
//						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER)";
		
		String query = "SELECT * FROM "
				+ "(SELECT TECH_SHR.*, "
					+ "MEMBER_NAME AS WRITER_NAME, "
					+ "ROW_NUMBER() OVER(ORDER BY POST_NO DESC) AS R_NUM "
				+ "FROM TECH_SHR "
				+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER)"
				+ ") "
				+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSharePost post = new TechSharePost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setShrTitle(rset.getString("SHR_TITLE"));
				//post.setShrContent(rset.getString("SHR_CONTENT"));
				//post.setShrWriter(rset.getInt("SHR_WRITER"));
				post.setShrWriterName(rset.getString("WRITER_NAME"));
				post.setShrDate(rset.getDate("SHR_DATE").toString());
				post.setShrCnt(rset.getInt("SHR_CNT"));
				
				techSharePostL.add(post);
				
				//System.out.println("dao techSharePost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return techSharePostL;
	}

	public ArrayList<TechSupportPost> techSupportBoardListByCompNo(Connection conn, int compNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		String query = "SELECT TECH_SPPT.*, "
							+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
							+ "COMP_NO AS WRITER_COMPANY, "
							+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
							+ "STAT_NAME FROM TECH_SPPT "
							+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
							+ "JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
							+ "JOIN COMPANY_L ON(M1.COMP_NO = COMPANY_L.COMP_NO) "
							+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
							+ "WHERE COMP_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, compNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSupportPost post = new TechSupportPost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setSpptTitle(rset.getString("SPPT_TITLE"));
				//post.setSpptContent(rset.getString("SPPT_CONTENT"));
				//post.setSpptWriter(rset.getInt("SPPT_WRITER"));
				post.setSpptWriterName(rset.getString("SPPT_WRITERNAME"));
				//post.setSpptEng(rset.getInt(rset.getString("SPPT_ENG")));
				post.setSpptEngName(rset.getString("SPPT_ENGNAME"));
				post.setSpptDate(rset.getString("SPPT_DATE").toString());
				//Spost.setSpptDate(rset.getDate("SPPT_DATE").toString());
				//post.setSpptStatcd(rset.getString("SPPT_STATCD"));
				post.setSpptStatName(rset.getString("STAT_NAME"));
				post.setSpptCnt(rset.getInt("SPPT_CNT"));
				//post.setSpptEngck(rset.getString("SPPT_ENGCK").charAt(0));
				
				techSupportPostL.add(post);
				
				//System.out.println("dao TechSupportPost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return techSupportPostL;
	}

	public ArrayList<TechSupportPost> techSupportBoardList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		String query = "SELECT TECH_SPPT.*, "
						+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
						+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
						+ "STAT_NAME "
						+ "FROM TECH_SPPT "
						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
						+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) ";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSupportPost post = new TechSupportPost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setSpptTitle(rset.getString("SPPT_TITLE"));
				//post.setSpptContent(rset.getString("SPPT_CONTENT"));
				//post.setSpptWriter(rset.getInt("SPPT_WRITER"));
				post.setSpptWriterName(rset.getString("SPPT_WRITERNAME"));
				//post.setSpptEng(rset.getInt(rset.getString("SPPT_ENG")));
				post.setSpptEngName(rset.getString("SPPT_ENGNAME"));
				post.setSpptDate(rset.getString("SPPT_DATE").toString());
				//Spost.setSpptDate(rset.getDate("SPPT_DATE").toString());
				//post.setSpptStatcd(rset.getString("SPPT_STATCD"));
				post.setSpptStatName(rset.getString("STAT_NAME"));
				post.setSpptCnt(rset.getInt("SPPT_CNT"));
				//post.setSpptEngck(rset.getString("SPPT_ENGCK").charAt(0));
				
				techSupportPostL.add(post);
				
				//System.out.println("dao TechSupportPost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return techSupportPostL;
	}

	public ArrayList<Notice> noticeBoardList(Connection conn, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Notice> noticeList = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
				
		String query = "SELECT * FROM "
						+ "(SELECT NOTICE.*, "
							+ "MEMBER_NAME AS WRITER_NAME, "
							+ "ROW_NUMBER() OVER(ORDER BY POST_NO DESC) AS R_NUM "
						+ "FROM NOTICE "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = NOTICE.NTC_WRITER)"
						+ ") "
						+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice post = new Notice();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setNtcTitle(rset.getString("NTC_TITLE"));
				//post.setNtcContent(rset.getString("NTC_CONTENT"));
				//post.setNtcWriterNo(rset.getInt("NTC_WRITER"));
				post.setNtcWriterName(rset.getString("WRITER_NAME"));
				post.setNtcDate(rset.getDate("NTC_DATE").toString());
				//post.setNtcMainview(rset.getString("NTC_MAINVIEW").charAt(0));
				post.setNtcCnt(rset.getInt("NTC_CNT"));
				
				noticeList.add(post);
				
				//System.out.println("dao notice check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return noticeList;
	}

	public String getPageNavi(Connection conn, int currPg, int recordCountPerPage, int naviCountPerPage, String board) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//게시물의 토탈 개수
		int recordTotalCount = 0;	//초기값
		
		String query = "";
		if(board.equals("Notice")) query = "SELECT COUNT(*) AS TOTALCOUNT FROM NOTICE";
		else if(board.equals("TechSpp")) query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SPPT";
		else if(board.equals("TechSh")) query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SHR";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				recordTotalCount = rset.getInt("TOTALCOUNT");
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		int pageTotalCount = 0; //초기값
		
		// 페이지의 토탈 개수
		if(recordTotalCount%recordCountPerPage != 0) {
			pageTotalCount = recordTotalCount / recordCountPerPage +1;
		}else {
			pageTotalCount = recordTotalCount / recordCountPerPage;
		}
		
		// 에러 방지
		if(currPg<1) {
			currPg=1;
		}
		else if(currPg>pageTotalCount) {
			currPg = pageTotalCount;
		}
		
		// 시작 페이지
		int startNavi = ((currPg-1)/naviCountPerPage) * naviCountPerPage +1;
		int endNavi = startNavi + naviCountPerPage -1;
		
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		
		boolean needPrev = true;
		boolean needNext = true;
		
		if(startNavi==1) { needPrev = false; }
		if(endNavi==pageTotalCount) { needNext = false; }
		
		StringBuilder sb = new StringBuilder();
		
		// 시작페이지가 1이면 false
		// 시작페이지가 1이 아니라면 true
		if(needPrev==true) {
			//sb.append("<a id='prev_a' href='/views/board/noticeList.jsp?currPg="+(startNavi-1)+"'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
			sb.append("<a id='prev_a' href='/views/main/mainpage.jsp?board="+board+"&currPg="+(startNavi-1)+"'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
		}
		else {
			sb.append("<a id='prev_a'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
		}
		
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==currPg) {
				//sb.append("<a class='page_link' href='/views/board/noticeList.jsp?currPg="+i+"'><B>"+i+"</B></a>");
				sb.append("<a class='page_link' href='/views/main/mainpage.jsp?board="+board+"&currPg="+i+"'><B>"+i+"</B></a>");
			}
			else {
				//sb.append("<a class='page_link' href='/views/board/noticeList.jsp?currPg="+i+"'>"+i+"</a>");
				sb.append("<a class='page_link' href='/views/main/mainpage.jsp?board="+board+"&currPg="+i+"'>"+i+"</a>");
			}
		}
		
		if(needNext) {
			//sb.append("<a id='next_a' href='/views/board/noticeList.jsp?currPg="+(endNavi+1)+"'><img src='/img/next.png' id='next_img' width='20px'></a>");
			sb.append("<a id='next_a' href='/views/main/mainpage.jsp?board="+board+"&currPg="+(endNavi+1)+"'><img src='/img/next.png' id='next_img' width='20px'></a>");
		}
		else {
			sb.append("<a id='next_a'><img src='/img/next.png' id='next_img' width='20px'></a>");
		}
		
		return sb.toString();
	}
	
	
}
