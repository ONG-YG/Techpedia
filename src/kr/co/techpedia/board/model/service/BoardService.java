package kr.co.techpedia.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.techpedia.board.model.dao.BoardDao;
import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;

public class BoardService {
	
	public ArrayList<TechSharePost> getTechShareList(int memberNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		techSharePostL = new BoardDao().getTechShareList(conn, memberNo);
		
		JDBCTemplate.close(conn);
		
		return techSharePostL;
	}

	public ArrayList<TechSupportPost> getTechSpptListByCopNo(int memberNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		techSupportPostL = new BoardDao().getTechSpptListByCopNo(conn, memberNo);
		
		JDBCTemplate.close(conn);
		
		return techSupportPostL;
	}

	public ArrayList<TechSupportPost> getTechSpptListByEngNo(int memberNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		techSupportPostL = new BoardDao().getTechSpptListByEngNo(conn, memberNo);
		
		JDBCTemplate.close(conn);
		
		return techSupportPostL;
	}
}
