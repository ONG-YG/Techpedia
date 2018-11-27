package kr.co.techpedia.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.techpedia.board.model.dao.BoardDao;
import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.board.model.vo.BoardPageData;
import kr.co.techpedia.board.model.vo.Notice;

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

	public BoardPageData techShareBoardList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수

		//ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		//techSharePostL = new BoardDao().techShareBoardList(conn);
		ArrayList<TechSharePost> techSharePostL = new BoardDao().techShareBoardList(conn, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, currPg, recordCountPerPage, naviCountPerPage, "TechSh");
		
		BoardPageData pd = null;
		if(!techSharePostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSharePostL(techSharePostL);
			pd.setPageNavi(pageNavi);
		}
		
		return pd;
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

	public BoardPageData noticeBoardList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<Notice> noticeList = new BoardDao().noticeBoardList(conn, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, currPg, recordCountPerPage, naviCountPerPage, "Notice");
		
		BoardPageData pd = null;
		if(!noticeList.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setNoticeList(noticeList);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}
}
