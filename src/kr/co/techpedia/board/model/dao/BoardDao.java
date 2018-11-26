package kr.co.techpedia.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.main.model.vo.Notice;

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

	public ArrayList<TechSharePost> techShareBoardList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		
		String query = "SELECT TECH_SHR.*, "
						+ "MEMBER_NAME "
						+ "FROM TECH_SHR "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = TECH_SHR.SHR_WRITER)";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TechSharePost post = new TechSharePost();
				post.setPostNo(rset.getInt("POST_NO"));
				post.setShrTitle(rset.getString("SHR_TITLE"));
				//post.setShrContent(rset.getString("SHR_CONTENT"));
				//post.setShrWriter(rset.getInt("SHR_WRITER"));
				post.setShrWriterName(rset.getString("MEMBER_NAME"));
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

	public ArrayList<Notice> noticeBoardList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Notice> noticeList = new ArrayList<>();
		
		String query = "SELECT NOTICE.*, "
						+ "MEMBER_NAME AS WRITER_NAME "
						+ "FROM NOTICE "
						+ "JOIN TP_MEMBER ON(TP_MEMBER.MEMBER_NO = NOTICE.NTC_WRITER)";
		
		try {
			pstmt = conn.prepareStatement(query);
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
				
				//System.out.println("dao techSharePost check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return noticeList;
	}
}
