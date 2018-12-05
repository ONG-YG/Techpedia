package kr.co.techpedia.admin.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.member.model.vo.TpMember;

public class AdminDao {
	
	public int uncheckNoticeMainView(Connection conn) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE NOTICE SET NTC_MAINVIEW='N'";
		
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
	
	public int updateNoticeMainView(Connection conn, int postNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE NOTICE "
								+ "SET NTC_MAINVIEW='Y' "
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

	public ArrayList<TpMember> getEngineerList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TpMember> engineerList = new ArrayList<>();
		
		String query = "SELECT MEMBER_NO, "
							+ "MEMBER_NAME "
							+ "FROM TP_MEMBER "
							+ "WHERE MEMBER_TYPECD IN ('MNFE_AD', 'MNFE') "
							+ "AND MEMBER_ACTIVE='Y'";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TpMember memberInfo = new TpMember();
				
				memberInfo.setMemberNo(rset.getInt("MEMBER_NO"));
				//memberInfo.setMemberId(rset.getString("MEMBER_ID"));
				//memberInfo.setMemberPw(rset.getString("MEMBER_PW"));
				memberInfo.setMemberName(rset.getString("MEMBER_NAME"));
				//memberInfo.setMemberPrivatePhone(rset.getString("MEMBER_P_PHONE"));
				//memberInfo.setMemberCompanyPhone(rset.getString("MEMBER_C_PHONE"));
				//memberInfo.setMemberEmail(rset.getString("MEMBER_EMAIL"));
				//memberInfo.setMemberTypeCD(rset.getString("MEMBER_TYPECD"));
				//memberInfo.setMemberTypeName(rset.getString("MEMBER_TYPENAME"));
				//memberInfo.setCompNo(rset.getInt("COMP_NO"));
				//memberInfo.setCompName(rset.getString("COMP_NAME"));
				//memberInfo.setCompanyMemNo(rset.getString("COMPANY_MEMNO"));
				//memberInfo.setMemberActive(rset.getString("MEMBER_ACTIVE").charAt(0));
				//memberInfo.setMemberPhoto(rset.getString("MEMBER_PHOTO"));
				
				engineerList.add(memberInfo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return engineerList;
	}

	public ArrayList<TechSupportPost> engAutoList(Connection conn, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
		String query = "SELECT * FROM "
							+ "(SELECT TECH_SPPT.*, "
								+ "M1.MEMBER_NAME AS SPPT_WRITERNAME, "
								+ "COMP_NO AS WRITER_COMPANY, "
								+ "M2.MEMBER_NAME AS SPPT_ENGNAME, "
								+ "STAT_NAME, "
								+ "ROW_NUMBER() OVER(ORDER BY POST_NO DESC) AS R_NUM "
								+ "FROM TECH_SPPT "
								+ "JOIN TP_MEMBER M1 ON(M1.MEMBER_NO = TECH_SPPT.SPPT_WRITER) "
								+ "LEFT JOIN TP_MEMBER M2 ON(M2.MEMBER_NO = TECH_SPPT.SPPT_ENG) "
								+ "JOIN COMPANY_L ON(M1.COMP_NO = COMPANY_L.COMP_NO) "
								+ "JOIN SPPT_STAT ON(SPPT_STAT.SPPT_STATCD = TECH_SPPT.SPPT_STATCD) "
								+ "WHERE TECH_SPPT.SPPT_STATCD='ENG_AUTO'"
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
				//post.setSpptDate(rset.getDate("SPPT_DATE").toString());
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

	public String getPageNavi(Connection conn, 
			int currPg, 
			int recordCountPerPage, 
			int naviCountPerPage, 
			String board) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//게시물의 토탈 개수
		int recordTotalCount = 0;	//초기값
		
		String query = "";
		if(board.equals("TechSppAutoEng")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TECH_SPPT WHERE SPPT_STATCD='ENG_AUTO'";
		}
		else if(board.equals("EnrollMember")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TP_MEMBER WHERE MEMBER_ACTIVE='N' AND MEMBER_DELNO IS NULL";
		}
		else if(board.equals("totalMember")) {
			query = "SELECT COUNT(*) AS TOTALCOUNT FROM TP_MEMBER";
		}
		
		//System.out.println(query);////////////////
		
		
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
			sb.append("<span id='prev_a'><img src='/img/prev.png' id='prev_img' width='20px' onclick='move("+(startNavi-1)+");'></span>");
		}
		else {
			sb.append("<a id='prev_a'><img src='/img/prev.png' id='prev_img' width='20px'></a>");
		}
		
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==currPg) {
				sb.append("<span class='page_link' onclick='move("+i+");'><B>"+i+"</B></span>");
			}
			else {
				sb.append("<span class='page_link' onclick='move("+i+");'>"+i+"</span>");
			}
		}
		
		if(needNext) {
			sb.append("<span id='prev_a'><img src='/img/next.png' id='next_img' width='20px' onclick='move("+(endNavi+1)+");'></span>");
		}
		else {
			sb.append("<a id='next_a'><img src='/img/next.png' id='next_img' width='20px'></a>");
		}
		
		//System.out.println(sb.toString());/////////////////
		
		return sb.toString();
	}

	public int assignTechSppEng(Connection conn, int engNo, int postNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TECH_SPPT "
								+ "SET SPPT_ENG=?, "
								+ "SPPT_STATCD='ENG_CHANGED' "
								+ "WHERE POST_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, engNo);
			pstmt.setInt(2, postNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public ArrayList<TpMember> enrollMemberList(Connection conn, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TpMember> enrollMemberL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
		String query = "SELECT * FROM "
							+ "(SELECT TP_MEMBER.*, "
								+ "MEMBER_TYPENAME, "
								+ "COMP_NAME, "
								+ "ROW_NUMBER() OVER(ORDER BY ENROLL_DATE ASC) AS R_NUM "
								+ "FROM TP_MEMBER "
								+ "JOIN MEM_TYPE ON(MEM_TYPE.MEMBER_TYPECD = TP_MEMBER.MEMBER_TYPECD) "
								+ "JOIN COMPANY_L ON(COMPANY_L.COMP_NO = TP_MEMBER.COMP_NO) "
								+ "WHERE MEMBER_ACTIVE='N' AND MEMBER_DELNO IS NULL "
								+ ") "
							+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TpMember memberInfo = new TpMember();
				
				memberInfo.setMemberNo(rset.getInt("MEMBER_NO"));
				//memberInfo.setMemberId(rset.getString("MEMBER_ID"));
				//memberInfo.setMemberPw(rset.getString("MEMBER_PW"));
				memberInfo.setMemberName(rset.getString("MEMBER_NAME"));
				//memberInfo.setMemberPrivatePhone(rset.getString("MEMBER_P_PHONE"));
				//memberInfo.setMemberCompanyPhone(rset.getString("MEMBER_C_PHONE"));
				//memberInfo.setMemberEmail(rset.getString("MEMBER_EMAIL"));
				//memberInfo.setMemberTypeCD(rset.getString("MEMBER_TYPECD"));
				memberInfo.setMemberTypeName(rset.getString("MEMBER_TYPENAME"));
				//memberInfo.setCompNo(rset.getInt("COMP_NO"));
				memberInfo.setCompName(rset.getString("COMP_NAME"));
				memberInfo.setCompanyMemNo(rset.getString("COMPANY_MEMNO"));
				//memberInfo.setMemberActive(rset.getString("MEMBER_ACTIVE").charAt(0));
				//memberInfo.setMemberPhoto(rset.getString("MEMBER_PHOTO"));
				memberInfo.setEnrollDate(rset.getDate("ENROLL_DATE").toString());
				
				enrollMemberL.add(memberInfo);
				
				//System.out.println("dao TpMember check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return enrollMemberL;
	}

	public int approveMemberJoin(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TP_MEMBER SET MEMBER_ACTIVE='Y', APPROVAL_DATE=SYSDATE WHERE MEMBER_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public ArrayList<TpMember> totalMemberList(Connection conn, int currPg, int recordCountPerPage) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<TpMember> totalMemberL = new ArrayList<>();
		
		//시작 게시물 계산
		int start = currPg * recordCountPerPage - (recordCountPerPage-1);
		//끝 게시물 계산
		int end = currPg * recordCountPerPage;
		
		String query = "SELECT * FROM "
							+ "(SELECT TP_MEMBER.*, "
								+ "MEMBER_TYPENAME, "
								+ "COMP_NAME, "
								+ "ROW_NUMBER() OVER(ORDER BY ENROLL_DATE ASC) AS R_NUM "
								+ "FROM TP_MEMBER "
								+ "JOIN MEM_TYPE ON(MEM_TYPE.MEMBER_TYPECD = TP_MEMBER.MEMBER_TYPECD) "
								+ "JOIN COMPANY_L ON(COMPANY_L.COMP_NO = TP_MEMBER.COMP_NO) "
								+ ") "
							+ "WHERE R_NUM BETWEEN ? AND ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				TpMember memberInfo = new TpMember();
				
				memberInfo.setMemberNo(rset.getInt("MEMBER_NO"));
				//memberInfo.setMemberId(rset.getString("MEMBER_ID"));
				//memberInfo.setMemberPw(rset.getString("MEMBER_PW"));
				memberInfo.setMemberName(rset.getString("MEMBER_NAME"));
				//memberInfo.setMemberPrivatePhone(rset.getString("MEMBER_P_PHONE"));
				//memberInfo.setMemberCompanyPhone(rset.getString("MEMBER_C_PHONE"));
				//memberInfo.setMemberEmail(rset.getString("MEMBER_EMAIL"));
				//memberInfo.setMemberTypeCD(rset.getString("MEMBER_TYPECD"));
				memberInfo.setMemberTypeName(rset.getString("MEMBER_TYPENAME"));
				//memberInfo.setCompNo(rset.getInt("COMP_NO"));
				memberInfo.setCompName(rset.getString("COMP_NAME"));
				memberInfo.setCompanyMemNo(rset.getString("COMPANY_MEMNO"));
				memberInfo.setMemberActive(rset.getString("MEMBER_ACTIVE").charAt(0));
				memberInfo.setMemberDelNo(rset.getInt("MEMBER_DELNO"));
				//memberInfo.setMemberPhoto(rset.getString("MEMBER_PHOTO"));
				memberInfo.setEnrollDate(rset.getDate("ENROLL_DATE").toString());
				
				totalMemberL.add(memberInfo);
				
				//System.out.println("dao TpMember check\n"+post);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return totalMemberL;
	}

	public int rejectMemberJoin(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "DELETE FROM TP_MEMBER WHERE MEMBER_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

	public int setDeletedMember(Connection conn, int memberNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "UPDATE TP_MEMBER SET MEMBER_ACTIVE='N', "
										+ "MEMBER_DELNO=SEQ_MEMDELNO.NEXTVAL "
										+ "WHERE MEMBER_NO=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	
}
