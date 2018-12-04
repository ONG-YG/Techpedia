package kr.co.techpedia.admin.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.techpedia.admin.model.dao.AdminDao;
import kr.co.techpedia.board.model.vo.BoardPageData;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.member.model.vo.TpMember;

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

	public ArrayList<TpMember> getEngineerList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<TpMember> engineerList = new AdminDao().getEngineerList(conn);
		
		JDBCTemplate.close(conn);		
		
		return engineerList;
	}

	public BoardPageData engAutoList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TechSupportPost> techSupportPostL
			= new AdminDao().engAutoList(conn, currPg, recordCountPerPage);
		String pageNavi = new AdminDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSppAutoEng");
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSupportPostL(techSupportPostL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public int assignTechSppEng(int engNo, int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new AdminDao().assignTechSppEng(conn, engNo, postNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public BoardPageData enrollMemberList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TpMember> enrollMemberL
			= new AdminDao().enrollMemberList(conn, currPg, recordCountPerPage);
		String pageNavi = new AdminDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"EnrollMember");
		
		BoardPageData pd = null;
		if(!enrollMemberL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setMemberList(enrollMemberL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}
	
	public int approveMemberJoin(int memberNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new AdminDao().approveMemberJoin(conn, memberNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public BoardPageData totalMemberList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TpMember> totalMemberL
			= new AdminDao().totalMemberList(conn, currPg, recordCountPerPage);
		String pageNavi = new AdminDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"totalMember");
		
		BoardPageData pd = null;
		if(!totalMemberL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setMemberList(totalMemberL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}
	
}
