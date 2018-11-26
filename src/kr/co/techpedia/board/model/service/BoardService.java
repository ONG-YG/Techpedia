package kr.co.techpedia.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.techpedia.board.model.dao.BoardDao;
import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.main.model.vo.Notice;

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

	public ArrayList<TechSharePost> techShareBoardList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		techSharePostL = new BoardDao().techShareBoardList(conn);
		
		JDBCTemplate.close(conn);
		
		return techSharePostL;
	}

	public ArrayList<TechSupportPost> techSupportBoardListByCompNo(int compNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		techSupportPostL = new BoardDao().techSupportBoardListByCompNo(conn, compNo);
		
		JDBCTemplate.close(conn);
		
		return techSupportPostL;
	}

	public ArrayList<TechSupportPost> techSupportBoardList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		techSupportPostL = new BoardDao().techSupportBoardList(conn);
		
		JDBCTemplate.close(conn);
		
		return techSupportPostL;
	}

	public ArrayList<Notice> noticeBoardList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Notice> noticeList = new ArrayList<>();
		noticeList = new BoardDao().noticeBoardList(conn);
		
		JDBCTemplate.close(conn);
		
		return noticeList;
	}
}
