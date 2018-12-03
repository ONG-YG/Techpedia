package kr.co.techpedia.admin.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.co.techpedia.common.JDBCTemplate;

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
	
	
}
