package kr.co.techpedia.main.model.service;

import java.sql.Connection;

import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.main.model.dao.MainDao;
import kr.co.techpedia.main.model.vo.Notice;

public class MainService {
	
	public Notice getMainNotice() {
		Connection conn = JDBCTemplate.getConnection();
		
		Notice mainNotice = new MainDao().getMainNotice(conn);
		
		JDBCTemplate.close(conn);
		
		return mainNotice;
	}
}
