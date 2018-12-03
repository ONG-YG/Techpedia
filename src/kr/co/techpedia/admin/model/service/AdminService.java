package kr.co.techpedia.admin.model.service;

import java.sql.Connection;

import kr.co.techpedia.admin.model.dao.AdminDao;
import kr.co.techpedia.common.JDBCTemplate;

public class AdminService {

	public int updateNoticeMainView(int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int result_uncheck = new AdminDao().uncheckNoticeMainView(conn);
		int result_check = new AdminDao().updateNoticeMainView(conn, postNo);
		
		if(result_uncheck>0 && result_check>0) result = 1;
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

}
