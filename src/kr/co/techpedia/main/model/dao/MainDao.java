package kr.co.techpedia.main.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.board.model.vo.Notice;

public class MainDao {
	public Notice getMainNotice(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Notice mainNotice = null;
		
		String query = "SELECT POST_NO, "
							+ "NTC_TITLE, "
							+ "NTC_CONTENT, "
							+ "NTC_WRITER, "
							+ "NTC_DATE, "
							+ "NTC_MAINVIEW, "
							+ "NTC_CNT, "
							+ "MEMBER_NAME "
							+ "FROM NOTICE "
							+ "JOIN TP_MEMBER ON(NTC_WRITER=MEMBER_NO) "
							+ "WHERE NTC_MAINVIEW='Y'";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				mainNotice = new Notice();
				//mainNotice.setPostNo(rset.getInt("POST_NO"));
				mainNotice.setNtcTitle(rset.getString("NTC_TITLE"));
				mainNotice.setNtcContent(rset.getString("NTC_CONTENT"));
				//mainNotice.setNtcWriterNo(rset.getInt("NTC_WRITER"));
				mainNotice.setNtcWriterName(rset.getString("MEMBER_NAME"));
				mainNotice.setNtcDate(rset.getDate("NTC_DATE").toString());
				//mainNotice.setNtcMainview(rset.getString("NTC_MAINVIEW").charAt(0));
				//mainNotice.setNtcCnt(rset.getInt("NTC_CNT"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return mainNotice;
	}
}
