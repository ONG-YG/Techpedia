package kr.co.techpedia.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.board.model.vo.Comment;
import kr.co.techpedia.board.model.vo.Notice;
import kr.co.techpedia.board.model.vo.NoticeGrade;
import kr.co.techpedia.board.model.vo.SupportState;

public class BoardDao {
	
	public ArrayList<TechSharePost> getTechShareList(Connection conn, int memberNo, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
//		String query_old = "SELECT TECH_SHR.*, "
//						+ "MEMBER_NAME "
//						+ "FROM TECH_SHR "
//						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER) "
//						+ "WHERE SHR_WRITER=?";
		
		String query = "SELECT * FROM "
						+ "(SELECT TECH_SHR.*, "
							+ "MEMBER_NAME AS WRITER_NAME, "
							+ "ROW_NUMBER() OVER(ORDER BY POST_NO DESC) AS R_NUM "
						+ "FROM TECH_SHR "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER) "
						+ "WHERE SHR_WRITER=?"
						+ ") "
						+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSharePost post = new TechSharePost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setShrTitle(rset.getString("SHR_TITLE"));
				//post.setShrContent(rset.getString("SHR_CONTENT"));
				//post.setShrWriter(rset.getInt("SHR_WRITER"));
				//post.setShrWriterName(rset.getString("WRITER_NAME"));
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
	
	public ArrayList<TechSupportPost> getTechSpptListByCompMemNo(Connection conn, int memberNo, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
//		String query_old = "SELECT TECH_SPPT.*, "
//						+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
//						+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
//						+ "STAT_NAME "
//						+ "FROM TECH_SPPT "
//						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
//						+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
//						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
//						+ "WHERE SPPT_WRITER=?";
		
		String query = "SELECT * FROM "
						+ "(SELECT TECH_SPPT.*, "
							+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
							+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
							+ "STAT_NAME, "
							+ "ROW_NUMBER() OVER(ORDER BY POST_NO DESC) AS R_NUM "
							+ "FROM TECH_SPPT "
							+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
							+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
							+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
							+ "WHERE SPPT_WRITER=?"
						+ ") "
						+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
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
	
	public ArrayList<TechSupportPost> getTechSpptListByEngNo(Connection conn, int memberNo, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
//		String query_old = "SELECT TECH_SPPT.*, "
//						+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
//						+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
//						+ "STAT_NAME "
//						+ "FROM TECH_SPPT "
//						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
//						+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
//						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
//						+ "WHERE SPPT_ENG=?";
		
		String query = "SELECT * FROM "
						+ "(SELECT TECH_SPPT.*, "
							+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
							+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
							+ "STAT_NAME, "
							+ "ROW_NUMBER() OVER(ORDER BY POST_NO DESC) AS R_NUM "
							+ "FROM TECH_SPPT "
							+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
							+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
							+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
							+ "WHERE SPPT_ENG=?"
						+ ") "
						+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
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
					+ "NVL(CMM_CNT,0) AS CMM_CNT, "
					+ "ROW_NUMBER() OVER(ORDER BY TECH_SHR.POST_NO DESC) AS R_NUM "
				+ "FROM TECH_SHR "
				+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER)"
				+ "LEFT JOIN ("
						+ "SELECT POST_NO, "
						+ "COUNT(POST_NO) AS CMM_CNT FROM COMMENTS "
						+ "WHERE BRD_CODE='SHR' "
						+ "GROUP BY POST_NO) CMM "
				+ "ON (TECH_SHR.POST_NO = CMM.POST_NO)"
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
				post.setCmmCnt(rset.getInt("CMM_CNT"));
				
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
	
	public ArrayList<TechSupportPost> techSupportBoardListByCompNo(Connection conn, int compNo, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
//		String query_old = "SELECT TECH_SPPT.*, "
//							+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
//							+ "COMP_NO AS WRITER_COMPANY, "
//							+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
//							+ "STAT_NAME FROM TECH_SPPT "
//							+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
//							+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
//							+ "JOIN COMPANY_L ON(M1.COMP_NO = COMPANY_L.COMP_NO) "
//							+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
//							+ "WHERE COMP_NO=?";
		
		String query = "SELECT * FROM "
							+ "(SELECT TECH_SPPT.*, "
								+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
								+ "COMP_NO AS WRITER_COMPANY, "
								+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
								+ "STAT_NAME, "
								+ "NVL(CMM_CNT,0) AS CMM_CNT, "
								+ "ROW_NUMBER() OVER(ORDER BY TECH_SPPT.POST_NO DESC) AS R_NUM "
								+ "FROM TECH_SPPT "
								+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
								+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
								+ "JOIN COMPANY_L ON(M1.COMP_NO = COMPANY_L.COMP_NO) "
								+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
								+ "LEFT JOIN ("
										+ "SELECT POST_NO, "
										+ "COUNT(POST_NO) AS CMM_CNT FROM COMMENTS "
										+ "WHERE BRD_CODE='SPPT' "
										+ "GROUP BY POST_NO) CMM "
								+ "ON (TECH_SPPT.POST_NO = CMM.POST_NO)"
								+ "WHERE COMP_NO=?"
								+ ") "
							+ "WHERE R_NUM BETWEEN ? AND ?";
		
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, compNo);
			pstmt.setInt(2, start);
			pstmt.setInt(3, end);
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
				//post.setSpptDate(rset.getDate("SPPT_DATE").toString());
				//post.setSpptStatcd(rset.getString("SPPT_STATCD"));
				post.setSpptStatName(rset.getString("STAT_NAME"));
				post.setSpptCnt(rset.getInt("SPPT_CNT"));
				//post.setSpptEngck(rset.getString("SPPT_ENGCK").charAt(0));
				post.setCmmCnt(rset.getInt("CMM_CNT"));
				
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
	
	public ArrayList<TechSupportPost> techSupportBoardList(Connection conn, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();

		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
//		String query_old = "SELECT TECH_SPPT.*, "
//						+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
//						+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
//						+ "STAT_NAME "
//						+ "FROM TECH_SPPT "
//						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
//						+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
//						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) ";
		
		String query = "SELECT * FROM "
							+ "(SELECT TECH_SPPT.*, "
								+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
								+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
								+ "STAT_NAME, "
								+ "NVL(CMM_CNT,0) AS CMM_CNT, "
								+ "ROW_NUMBER() OVER(ORDER BY TECH_SPPT.POST_NO DESC) AS R_NUM "
								+ "FROM TECH_SPPT "
								+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
								+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
								+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
								+ "LEFT JOIN ("
										+ "SELECT POST_NO, "
										+ "COUNT(POST_NO) AS CMM_CNT FROM COMMENTS "
										+ "WHERE BRD_CODE='SPPT' "
										+ "GROUP BY POST_NO) CMM "
								+ "ON (TECH_SPPT.POST_NO = CMM.POST_NO)"
								+ ") "
							+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
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
				post.setCmmCnt(rset.getInt("CMM_CNT"));
				
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
							+ "NGRD_NAME, "
							+ "NVL(CMM_CNT,0) AS CMM_CNT, "
							+ "ROW_NUMBER() OVER(ORDER BY NOTICE.POST_NO DESC) AS R_NUM "
						+ "FROM NOTICE "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = NOTICE.NTC_WRITER) "
						+ "JOIN NTC_GRD_L ON(NOTICE.NTC_GRADECD = NTC_GRD_L.NTC_GRADECD)"
						+ "LEFT JOIN ("
								+ "SELECT POST_NO, "
								+ "COUNT(POST_NO) AS CMM_CNT FROM COMMENTS "
								+ "WHERE BRD_CODE='NTC' "
								+ "GROUP BY POST_NO) CMM "
						+ "ON (NOTICE.POST_NO = CMM.POST_NO)"
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
				post.setNtcMainview(rset.getString("NTC_MAINVIEW").charAt(0));
				post.setNtcCnt(rset.getInt("NTC_CNT"));
				post.setNtcGradeCD(rset.getString("NTC_GRADECD"));
				post.setNgrdName(rset.getString("NGRD_NAME"));
				post.setCmmCnt(rset.getInt("CMM_CNT"));
				
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
	
	public String getPageNavi(Connection conn, 
									int currPg, 
									int recordCountPerPage, 
									int naviCountPerPage, 
									String board,
									int compNo, 
									int memberNo) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//게시물의 토탈 개수
		int recordTotalCount = 0;	//초기값
		
		String query = "";
		if(board.equals("Notice")) query = "SELECT COUNT(*) AS TOTALCOUNT FROM NOTICE";
		else if(board.equals("TechSh") && memberNo==-1) query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SHR";
		else if(board.equals("TechSpp") && compNo!=-1) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SPPT "
						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
						+ "JOIN COMPANY_L ON(M1.COMP_NO = COMPANY_L.COMP_NO) "
						+ "WHERE M1.COMP_NO=?";
		}
		else if(board.equals("TechSpp") && compNo==-1) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SPPT";
		}
		else if(board.equals("myTechShare") && memberNo!=-1) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SHR WHERE SHR_WRITER=?";
		}
		else if(board.equals("myTechSppt-C") && memberNo!=-1) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SPPT WHERE SPPT_WRITER=?";
		}
		else if(board.equals("myTechSppt-E") && memberNo!=-1) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SPPT WHERE SPPT_ENG=?";
		}
		//System.out.println(query);////////////////
		
		
		try {
			pstmt = conn.prepareStatement(query);
			if(board.equals("TechSpp") && compNo!=-1) pstmt.setInt(1, compNo);
			if(( board.equals("myTechShare") 
					|| board.equals("myTechSppt-C") 
					|| board.equals("myTechSppt-E") )
					&& memberNo!=-1) pstmt.setInt(1, memberNo);
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
		
		//if( board.equals("myTechSppt-C") || board.equals("myTechSppt-E") ) board = "myTechSppt";
		//String link = "/views/main/mainpage.jsp?board="+board+"&";
		
		if(needPrev==true) {
			//sb.append("<a id='prev_a' href='/views/board/noticeList.jsp?currPg="+(startNavi-1)+"'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
			if( board.equals("myTechShare") || board.equals("myTechSppt") )
				sb.append("<span id='prev_a'><img src='/img/prev.png' id='prev_img' width='20px' onclick='move("+(startNavi-1)+");'></span>");
			//else sb.append("<a id='prev_a' href='"+link+"currPg="+(startNavi-1)+"'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
			else sb.append("<span id='prev_a'><img src='/img/prev.png' id='prev_img' width='20px' onclick='move("+(startNavi-1)+");'></span>");
		}
		else {
			sb.append("<a id='prev_a'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
		}
		
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==currPg) {
				//sb.append("<a class='page_link' href='/views/board/noticeList.jsp?currPg="+i+"'><B>"+i+"</B></a>");
				if( board.equals("myTechShare") || board.equals("myTechSppt") )
					sb.append("<span class='page_link' onclick='move("+i+");'><B>"+i+"</B></span>");
				//else sb.append("<a class='page_link' href='"+link+"currPg="+i+"'><B>"+i+"</B></a>");
				else sb.append("<span class='page_link' onclick='move("+i+");'><B>"+i+"</B></span>");
			}
			else {
				//sb.append("<a class='page_link' href='/views/board/noticeList.jsp?currPg="+i+"'>"+i+"</a>");
				if( board.equals("myTechShare") || board.equals("myTechSppt") )
					sb.append("<span class='page_link' onclick='move("+i+");'>"+i+"</span>");
				//else sb.append("<a class='page_link' href='"+link+"currPg="+i+"'>"+i+"</a>");
				else sb.append("<span class='page_link' onclick='move("+i+");'>"+i+"</span>");
			}
		}
		
		if(needNext) {
			//sb.append("<a id='next_a' href='/views/board/noticeList.jsp?currPg="+(endNavi+1)+"'><img src='/img/next.png' id='next_img' width='20px'></a>");
			if( board.equals("myTechShare") || board.equals("myTechSppt") )
				sb.append("<span id='prev_a'><img src='/img/next.png' id='next_img' width='20px' onclick='move("+(endNavi+1)+");'></span>");
			//else sb.append("<a id='next_a' href='"+link+"currPg="+(endNavi+1)+"'><img src='/img/next.png' id='next_img' width='20px'></a>");
			else sb.append("<span id='prev_a'><img src='/img/next.png' id='next_img' width='20px' onclick='move("+(endNavi+1)+");'></span>");
		}
		else {
			sb.append("<a id='next_a'><img src='/img/next.png' id='next_img' width='20px'></a>");
		}
		
		//System.out.println(sb.toString());/////////////////
		
		return sb.toString();
	}
	
	public ArrayList<NoticeGrade> getNoticeGrdList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<NoticeGrade> noticeGradeList = new ArrayList<>();
		
		String query = "SELECT * FROM NTC_GRD_L";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				NoticeGrade noticeGrd = new NoticeGrade();
				noticeGrd.setNtcGradeCD(rset.getString("NTC_GRADECD"));
				noticeGrd.setNgrdName(rset.getString("NGRD_NAME"));
				noticeGradeList.add(noticeGrd);
				//System.out.println("dao noticeGrd check\n"+noticeGrd);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return noticeGradeList;
	}
	
	public int getNoticePostNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int postNo = 0;
		
		String query = "SELECT SEQ_NTC_POSTNO.NEXTVAL AS POSTNO FROM DUAL";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				postNo = rset.getInt("POSTNO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return postNo;
	}
	
	public int insertNoticeContent(Connection conn, int postNo, int memberNo, String noticeGrade, String title, String content) {
		PreparedStatement pstmt = null;
		int insertResult = 0;
		
		String query = "INSERT INTO NOTICE VALUES (?,"
													+ "?,"
													+ "?,"
													+ "?," 
													+ "SYSDATE," 
													+ "'N',"
													+ "0,"
													+ "?" 
													+ ")";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setInt(4, memberNo);
			pstmt.setString(5, noticeGrade);
			insertResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return insertResult;
	}
	
	public int uploadFile(Connection conn, String fileName, int postNo, String boardCode) {
		PreparedStatement pstmt = null;
		int uploadResult = 0;
		
		String query = "INSERT INTO ATTCH_FILE VALUES (SEQ_ATT.NEXTVAL,"
													+ "?,"
													+ "?,"
													+ "?"
													+ ")";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, fileName);
			pstmt.setInt(2, postNo);
			pstmt.setString(3, boardCode);
			uploadResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return uploadResult;
	}
	
	public ArrayList<SupportState> getSupportStateList(Connection conn, String superviser) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<SupportState> supportStateList = new ArrayList<>();
		
		String query = "SELECT * FROM SPPT_STAT WHERE STAT_SUPERVISER=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, superviser);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				SupportState supportState = new SupportState();
				supportState.setSpptStatCD(rset.getString("SPPT_STATCD"));
				supportState.setStatName(rset.getString("STAT_NAME"));
				//supportState.setStatSuperviser(rset.getString("STAT_SUPERVISER"));
				
				supportStateList.add(supportState);
				//System.out.println("dao supportState check\n"+supportState);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return supportStateList;
	}
	
	public int getTechSpptPostNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int postNo = 0;
		
		String query = "SELECT SEQ_SPPT_POSTNO.NEXTVAL AS POSTNO FROM DUAL";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				postNo = rset.getInt("POSTNO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return postNo;
	}
	
	public int insertTechSupport(Connection conn, int postNo, int memberNo, String title, String content) {
		PreparedStatement pstmt = null;
		int insertResult = 0;
		
		String query = "INSERT INTO TECH_SPPT VALUES (?,"
													+ "?,"
													+ "?,"
													+ "?,"
													+ "NULL,"
													+ "SYSDATE,"
													+ "'NEW',"
													+ "0,"
													+ "'N'"
													+ ")";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setInt(4, memberNo);
			insertResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return insertResult;
	}
	
	public int getTechSharePostNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int postNo = 0;
		
		String query = "SELECT SEQ_SHR_POSTNO.NEXTVAL AS POSTNO FROM DUAL";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				postNo = rset.getInt("POSTNO");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return postNo;
	}
	
	public int insertTechShare(Connection conn, int postNo, int memberNo, String title, String content) {
		PreparedStatement pstmt = null;
		int insertResult = 0;
		
		String query = "INSERT INTO TECH_SHR VALUES (?,"
													+ "?,"
													+ "?,"
													+ "?,"
													+ "SYSDATE,"
													+ "0"
													+ ")";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			pstmt.setInt(4, memberNo);
			insertResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return insertResult;
	}

	public Notice getOneNotice(Connection conn, int postNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice post = null;
			
		String query = "SELECT NOTICE.*, "
							+ "MEMBER_NAME AS WRITER_NAME, "
							+ "ATT_NAME, "
							+ "NGRD_NAME "
						+ "FROM NOTICE "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = NOTICE.NTC_WRITER) "
						+ "JOIN NTC_GRD_L ON(NOTICE.NTC_GRADECD = NTC_GRD_L.NTC_GRADECD) "
						+ "LEFT JOIN "
							+ "(SELECT * FROM ATTCH_FILE WHERE BRD_CODE='NTC') ATTCH_FILE "
							+ "ON(NOTICE.POST_NO=ATTCH_FILE.POST_NO) "
						+ "WHERE NOTICE.POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				post = new Notice();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setNtcTitle(rset.getString("NTC_TITLE"));
				post.setNtcContent(rset.getString("NTC_CONTENT"));
				post.setNtcWriterNo(rset.getInt("NTC_WRITER"));
				post.setNtcWriterName(rset.getString("WRITER_NAME"));
				post.setNtcDate(rset.getDate("NTC_DATE").toString());
				//post.setNtcMainview(rset.getString("NTC_MAINVIEW").charAt(0));
				post.setNtcCnt(rset.getInt("NTC_CNT"));
				post.setNtcGradeCD(rset.getString("NTC_GRADECD"));
				post.setNgrdName(rset.getString("NGRD_NAME"));
				post.setAttName(rset.getString("ATT_NAME"));
				
				//System.out.println("dao notice check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return post;
	}

	public int addReadCnt(Connection conn, String tableName, String columName, int postNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE "+tableName+" SET "+columName+"="+columName+"+1 WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			//pstmt.setString(1, tableName);
			//pstmt.setString(2, columName);
			//pstmt.setString(3, columName);
			pstmt.setInt(1, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Comment> getCommentList(Connection conn, String boardCD, int postNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Comment> commentList = new ArrayList<>();
		
		String query = "SELECT COMMENTS.*, "
							+ "MEMBER_NAME AS WRITER_NAME "
							+ "FROM COMMENTS "
							+ "JOIN TP_MEMBER ON (COMMENTS.CMM_WRITER = TP_MEMBER.MEMBER_NO) "
							+ "WHERE BRD_CODE=? AND POST_NO=? "
							+ "ORDER BY CMM_DATE DESC";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardCD);
			pstmt.setInt(2, postNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Comment comment = new Comment();
				comment.setCmmNo(rset.getInt("CMM_NO"));
				comment.setCmmContent(rset.getString("CMM_CONTENT"));
				comment.setCmmWriterNo(rset.getInt("CMM_WRITER"));
				comment.setCmmWriterName(rset.getString("WRITER_NAME"));
				comment.setCmmDate(rset.getString("CMM_DATE"));
				comment.setCmmParent(rset.getInt("CMM_PARENT"));
				comment.setBrdCode(rset.getString("BRD_CODE"));
				comment.setAnsSelected(rset.getString("ANS_SELECTED").charAt(0));
				
				commentList.add(comment);
				//System.out.println("dao comment check\n"+comment);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return commentList;
	}

	public int insertComment(Connection conn, String comment, int memberNo, int postNo, String boardCD) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO COMMENTS VALUES(SEQ_CMMNO.NEXTVAL, ?, ?, SYSDATE, NULL, ?, ?, 'N')";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, comment);
			pstmt.setInt(2, memberNo);
			pstmt.setInt(3, postNo);
			pstmt.setString(4, boardCD);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteComment(Connection conn, int cmmNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM COMMENTS WHERE CMM_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cmmNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updateComment(Connection conn, int cmmNo, String commentRe) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE COMMENTS SET CMM_CONTENT=? WHERE CMM_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, commentRe);
			pstmt.setInt(2, cmmNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public TechSharePost getOneTechSharePost(Connection conn, int postNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		TechSharePost post = null;
			
		String query = "SELECT TECH_SHR.*, "
							+ "ATT_NAME, "
							+ "MEMBER_NAME AS WRITER_NAME "
						+ "FROM TECH_SHR "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER) "
						+ "LEFT JOIN "
							+ "(SELECT * FROM ATTCH_FILE WHERE BRD_CODE='SHR') ATTCH_FILE "
							+ "ON(TECH_SHR.POST_NO=ATTCH_FILE.POST_NO) "
						+ "WHERE TECH_SHR.POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				post = new TechSharePost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setShrTitle(rset.getString("SHR_TITLE"));
				post.setShrContent(rset.getString("SHR_CONTENT"));
				post.setShrWriter(rset.getInt("SHR_WRITER"));
				post.setShrWriterName(rset.getString("WRITER_NAME"));
				post.setShrDate(rset.getDate("SHR_DATE").toString());
				post.setShrCnt(rset.getInt("SHR_CNT"));
				post.setAttName(rset.getString("ATT_NAME"));
				
				//System.out.println("dao TechSharePost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return post;
	}

	public int checkAnswerComment(Connection conn, int cmmNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE COMMENTS SET ANS_SELECTED='Y' WHERE CMM_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, cmmNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public TechSupportPost getOneTechSupportPost(Connection conn, int postNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		TechSupportPost post = null;
			
		String query = "SELECT TECH_SPPT.*, "
							+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
							+ "M1.COMP_NO AS SPPT_WRITERCOMPNO, "
							+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
							+ "STAT_NAME, "
							+ "ATT_NAME "/////////////////
							//+ "ROW_NUMBER() OVER(ORDER BY TECH_SPPT.POST_NO DESC) AS R_NUM "
						+ "FROM TECH_SPPT "
						+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
						+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
						+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
						+ "LEFT JOIN "
							+ "(SELECT * FROM ATTCH_FILE WHERE BRD_CODE='SPPT') ATTCH_FILE "
							+ "ON(TECH_SPPT.POST_NO=ATTCH_FILE.POST_NO) "
						+ "WHERE TECH_SPPT.POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				post = new TechSupportPost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setSpptTitle(rset.getString("SPPT_TITLE"));
				post.setSpptContent(rset.getString("SPPT_CONTENT"));
				post.setSpptWriter(rset.getInt("SPPT_WRITER"));
				post.setSpptWriterName(rset.getString("SPPT_WRITERNAME"));
				post.setSpptWriterCompNo(rset.getInt("SPPT_WRITERCOMPNO"));
				if(rset.getString("SPPT_ENG")!=null) {
					post.setSpptEng(rset.getInt("SPPT_ENG"));
				}else {
					post.setSpptEng(-1);
				}
				post.setSpptEngName(rset.getString("SPPT_ENGNAME"));
				post.setSpptDate(rset.getString("SPPT_DATE").toString());
				//post.setSpptDate(rset.getDate("SPPT_DATE").toString());
				post.setSpptStatcd(rset.getString("SPPT_STATCD"));
				post.setSpptStatName(rset.getString("STAT_NAME"));
				post.setSpptCnt(rset.getInt("SPPT_CNT"));
				post.setSpptEngck(rset.getString("SPPT_ENGCK").charAt(0));
				post.setAttName(rset.getString("ATT_NAME"));
				
				//System.out.println("dao TechSupportPost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return post;
	}

	public int autoEngineerUpdate(Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TECH_SPPT "
						+ "SET SPPT_ENG=100001 , "
						+ "SPPT_STATCD='ENG_AUTO' "
						+ "WHERE SPPT_DATE<SYSDATE-1 AND SPPT_ENG IS NULL";
		
		try {
			pstmt = conn.prepareStatement(query);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int checkEngAuto(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = "SELECT * FROM TECH_SPPT WHERE SPPT_STATCD='ENG_AUTO'";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int checkEngCK(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = "SELECT * FROM TECH_SPPT WHERE SPPT_ENG=? AND SPPT_ENGCK='N'";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = 1;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int updateSpptState(Connection conn, int postNo, String sppStatSelect) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TECH_SPPT SET SPPT_STATCD=? "
										+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, sppStatSelect);
			pstmt.setInt(2, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int setTechSpptEngck(Connection conn, int postNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TECH_SPPT SET SPPT_ENGCK='Y' "
										+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int takeChargeOfTechSpp(Connection conn, int postNo, int memberNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TECH_SPPT SET SPPT_ENG=?, SPPT_STATCD='ENG_ASSIGNED' "
																+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deletePost(Connection conn, int postNo, String tableName) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM "+tableName+" WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteTotalComment(Connection conn, int postNo, String boardCD) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM COMMENTS WHERE BRD_CODE=? AND POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardCD);
			pstmt.setInt(2, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int deleteAttFiles(Connection conn, int postNo, String boardCD) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM ATTCH_FILE WHERE BRD_CODE=? AND POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardCD);
			pstmt.setInt(2, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int getCommentCnt(Connection conn, int postNo, String boardCD) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int cnt = 0;
		
		String query = "SELECT COUNT(*) AS CNT FROM COMMENTS "
							+ "WHERE BRD_CODE=? AND POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardCD);
			pstmt.setInt(2, postNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				cnt = rset.getInt("CNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return cnt;
	}

	public int getAttCnt(Connection conn, int postNo, String boardCD) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int cnt = 0;
		
		String query = "SELECT COUNT(*) AS CNT FROM ATTCH_FILE "
								+ "WHERE BRD_CODE=? AND POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardCD);
			pstmt.setInt(2, postNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				cnt = rset.getInt("CNT");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return cnt;
	}

	public int updateNoticeContent(Connection conn, int postNo, String noticeGrade, String title, String content) {
		PreparedStatement pstmt = null;
		int updateResult = 0;
		
		String query = "UPDATE NOTICE SET "
										+ "NTC_TITLE=?, "
										+ "NTC_CONTENT=?, "
										+ "NTC_GRADECD=? "
										+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, noticeGrade);
			pstmt.setInt(4, postNo);
			updateResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return updateResult;
	}

	public String getAttFileName(Connection conn, int postNo, String boardCD) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String attFile = null;
		
		String query = "SELECT ATT_NAME FROM ATTCH_FILE "
								+ "WHERE BRD_CODE=? AND POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardCD);
			pstmt.setInt(2, postNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				attFile = rset.getString("ATT_NAME");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return attFile;
	}

	public int updateTechShare(Connection conn, int postNo, String title, String content) {
		PreparedStatement pstmt = null;
		int updateResult = 0;
		
		String query = "UPDATE TECH_SHR SET "
										+ "SHR_TITLE=?, "
										+ "SHR_CONTENT=? "
										+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, postNo);
			updateResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return updateResult;
	}

	public int updateTechSupport(Connection conn, int postNo, String title, String content) {
		PreparedStatement pstmt = null;
		int updateResult = 0;
		
		String query = "UPDATE TECH_SPPT SET "
										+ "SPPT_TITLE=?, "
										+ "SPPT_CONTENT=?, "
										+ "SPPT_ENGCK='N' "
										+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setInt(3, postNo);
			updateResult = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return updateResult;
	}
	
	
}
