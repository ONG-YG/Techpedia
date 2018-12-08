package kr.co.techpedia.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.techpedia.board.model.dao.BoardDao;
import kr.co.techpedia.board.model.vo.TechSharePost;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.board.model.vo.BoardPageData;
import kr.co.techpedia.board.model.vo.Comment;
import kr.co.techpedia.board.model.vo.Notice;
import kr.co.techpedia.board.model.vo.NoticeGrade;
import kr.co.techpedia.board.model.vo.SupportState;

public class BoardService {
	
	public BoardPageData getTechShareList(int memberNo, int currPg) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TechSharePost> techSharePostL
			= new BoardDao().getTechShareList(conn, memberNo, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"myTechShare", 
														-1, memberNo);

		BoardPageData pd = null;
		if(!techSharePostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSharePostL(techSharePostL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public BoardPageData getTechSpptListByCompMemNo(int memberNo, int currPg) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		//ArrayList<TechSupportPost> techSupportPostL = new BoardDao().getTechSpptListByCompMemNo(conn, memberNo);
		ArrayList<TechSupportPost> techSupportPostL
			= new BoardDao().getTechSpptListByCompMemNo(conn, memberNo, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"myTechSppt-C", 
														-1, memberNo);
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
		pd = new BoardPageData();
		pd.setTechSupportPostL(techSupportPostL);
		pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public BoardPageData getTechSpptListByEngNo(int memberNo, int currPg) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		//ArrayList<TechSupportPost> techSupportPostL = new BoardDao().getTechSpptListByEngNo(conn, memberNo);
		ArrayList<TechSupportPost> techSupportPostL
			= new BoardDao().getTechSpptListByEngNo(conn, memberNo, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"myTechSppt-E", 
														-1, memberNo);
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
		pd = new BoardPageData();
		pd.setTechSupportPostL(techSupportPostL);
		pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public BoardPageData techShareBoardList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		//ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		//techSharePostL = new BoardDao().techShareBoardList(conn);
		ArrayList<TechSharePost> techSharePostL = new BoardDao().techShareBoardList(conn, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSh", 
														-1, -1);
		
		BoardPageData pd = null;
		if(!techSharePostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSharePostL(techSharePostL);
			pd.setPageNavi(pageNavi);
		}
		
		return pd;
	}

	public BoardPageData techSupportBoardListByCompNo(int compNo, int currPg) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TechSupportPost> techSupportPostL
			= new BoardDao().techSupportBoardListByCompNo(conn, compNo, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSpp", 
														compNo, -1);
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSupportPostL(techSupportPostL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public BoardPageData techSupportBoardList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TechSupportPost> techSupportPostL = new BoardDao().techSupportBoardList(conn, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSpp", 
														-1, -1);
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSupportPostL(techSupportPostL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public BoardPageData noticeBoardList(int currPg) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<Notice> noticeList = new BoardDao().noticeBoardList(conn, currPg, recordCountPerPage);
		String pageNavi = new BoardDao().getPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"Notice", 
														-1, -1);
		
		BoardPageData pd = null;
		if(!noticeList.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setNoticeList(noticeList);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public ArrayList<NoticeGrade> getNoticeGrdList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<NoticeGrade> noticeGradeList = new BoardDao().getNoticeGrdList(conn);
		
		JDBCTemplate.close(conn);
		
		return noticeGradeList;
	}

	public int insertNotice(int memberNo, String noticeGrade, String title, String content, String fileName) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int postNo = new BoardDao().getNoticePostNo(conn);
		
		if(postNo!=0) {
			int insertResult = new BoardDao().insertNoticeContent(conn, postNo, memberNo, noticeGrade, title, content);
			int uploadResult = 1;
			if(fileName!=null) {
				uploadResult = new BoardDao().uploadFile(conn, fileName, postNo, "NTC");
			}
			
			if(insertResult>0 && uploadResult>0) {
				JDBCTemplate.commit(conn);
				result = 1;
			}else {
				JDBCTemplate.rollback(conn);
			}
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public ArrayList<SupportState> getSupportStateList(String superviser) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<SupportState> supportStateList = new BoardDao().getSupportStateList(conn, superviser);
		
		JDBCTemplate.close(conn);
		
		return supportStateList;
	}

	public int insertTechSupport(int memberNo, String title, String content, String fileName) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int postNo = new BoardDao().getTechSpptPostNo(conn);
		
		if(postNo!=0) {
			int insertResult = new BoardDao().insertTechSupport(conn, postNo, memberNo, title, content);
			int uploadResult = 1;
			if(fileName!=null) {
				uploadResult = new BoardDao().uploadFile(conn, fileName, postNo, "SPPT");
			}
			
			if(insertResult>0 && uploadResult>0) {
				JDBCTemplate.commit(conn);
				result = 1;
			}else {
				JDBCTemplate.rollback(conn);
			}
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int insertTechShare(int memberNo, String title, String content, String fileName) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int postNo = new BoardDao().getTechSharePostNo(conn);
		
		if(postNo!=0) {
			int insertResult = new BoardDao().insertTechShare(conn, postNo, memberNo, title, content);
			int uploadResult = 1;
			if(fileName!=null) {
				uploadResult = new BoardDao().uploadFile(conn, fileName, postNo, "SHR");
			}
			
			if(insertResult>0 && uploadResult>0) {
				JDBCTemplate.commit(conn);
				result = 1;
			}else {
				JDBCTemplate.rollback(conn);
			}
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public Notice getOneNotice(int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		Notice post = new BoardDao().getOneNotice(conn, postNo);
		
		JDBCTemplate.close(conn);
		
		return post;
	}

	public int addReadCnt(String tableName, String columName, int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().addReadCnt(conn, tableName, columName, postNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public ArrayList<Comment> getCommentList(String boardCD, int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Comment> commentList = new BoardDao().getCommentList(conn, boardCD, postNo);
		
		JDBCTemplate.close(conn);
		
		return commentList;
	}

	public int insertComment(String comment, int memberNo, int postNo, String boardCD) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().insertComment(conn, comment, memberNo, postNo, boardCD);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deleteComment(int cmmNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().deleteComment(conn, cmmNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int updateComment(int cmmNo, String commentRe) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().updateComment(conn, cmmNo, commentRe);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public TechSharePost getOneTechSharePost(int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		TechSharePost post = new BoardDao().getOneTechSharePost(conn, postNo);
		
		JDBCTemplate.close(conn);
		
		return post;
	}

	public int checkAnswerComment(int cmmNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().checkAnswerComment(conn, cmmNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public TechSupportPost getOneTechSupportPost(int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		TechSupportPost post = new BoardDao().getOneTechSupportPost(conn, postNo);
		
		JDBCTemplate.close(conn);
		
		return post;
	}

	public int autoEngineerUpdate() {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().autoEngineerUpdate(conn);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int checkEngAuto() {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().checkEngAuto(conn);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int checkEngCK(int memberNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().checkEngCK(conn, memberNo);
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int insertAnswerCmm(String sppStatSelect, String comment, int memberNo, int postNo, String boardCD) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int stat_result = new BoardDao().updateSpptState(conn, postNo, sppStatSelect);
		int cmm_result = new BoardDao().insertComment(conn, comment, memberNo, postNo, boardCD);
		
		if(stat_result>0 && cmm_result>0) result = 1;
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int setTechSpptEngck(int postNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().setTechSpptEngck(conn, postNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int takeChargeOfTechSpp(int postNo, int memberNo) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new BoardDao().takeChargeOfTechSpp(conn, postNo, memberNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deletePost(int postNo, String boardCD) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		String tableName = "";
		if(boardCD.equals("NTC")) tableName = "NOTICE";
		else if(boardCD.equals("SHR")) tableName = "TECH_SHR";
		else if(boardCD.equals("SPPT")) tableName = "TECH_SPPT";
		else return 0;
		
		int postDel_result = new BoardDao().deletePost(conn, postNo, tableName);
		
		int cmmCnt = new BoardDao().getCommentCnt(conn, postNo, boardCD);
		int cmmDel_result = new BoardDao().deleteTotalComment(conn, postNo, boardCD);
		
		int attCnt = new BoardDao().getAttCnt(conn, postNo, boardCD);
		int attDel_result = new BoardDao().deleteAttFiles(conn, postNo, boardCD);
		
		if(postDel_result>0 
				&& (cmmDel_result>0 || cmmCnt==0) 
				&& (attDel_result>0 || attCnt==0)) result = 1;
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int updateNotice(int postNo, String noticeGrade, String title, String content, String fileName) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int updateResult = new BoardDao().updateNoticeContent(conn, postNo, noticeGrade, title, content);
		
		int uploadResult = 1;
		int attCnt = 0;
		int attDel_result = 1;
		if(fileName!=null) {
			// 기존 파일 대신 새로 첨부한 파일이 있을 경우
			// DB에서 기존에 첨부되어있던 파일 정보 삭제
			attCnt = new BoardDao().getAttCnt(conn, postNo, "NTC");
			attDel_result = new BoardDao().deleteAttFiles(conn, postNo, "NTC");
			
			// 새로 첨부한 파일 정보 DB에 입력
			uploadResult = new BoardDao().uploadFile(conn, fileName, postNo, "NTC");
		}
		
		if(updateResult>0 && uploadResult>0
				 && (attCnt==0 || attDel_result>0) ) {
			JDBCTemplate.commit(conn);
			result = 1;
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public String getAttFileName(int postNo, String boardCD) {
		Connection conn = JDBCTemplate.getConnection();
		
		String attFile = new BoardDao().getAttFileName(conn, postNo, boardCD);
		
		JDBCTemplate.close(conn);
		
		return attFile;
	}

	public int updateTechShare(int postNo, String title, String content, String fileName) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int updateResult = new BoardDao().updateTechShare(conn, postNo, title, content);
		
		int uploadResult = 1;
		int attCnt = 0;
		int attDel_result = 1;
		if(fileName!=null) {
			// 기존 파일 대신 새로 첨부한 파일이 있을 경우
			// DB에서 기존에 첨부되어있던 파일 정보 삭제
			attCnt = new BoardDao().getAttCnt(conn, postNo, "SHR");
			attDel_result = new BoardDao().deleteAttFiles(conn, postNo, "SHR");
			
			// 새로 첨부한 파일 정보 DB에 입력
			uploadResult = new BoardDao().uploadFile(conn, fileName, postNo, "SHR");
		}
		
		if(updateResult>0 && uploadResult>0
				 && (attCnt==0 || attDel_result>0) ) {
			JDBCTemplate.commit(conn);
			result = 1;
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int updateTechSupport(int postNo, String title, String content, String fileName) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		
		int updateResult = new BoardDao().updateTechSupport(conn, postNo, title, content);
		
		int uploadResult = 1;
		int attCnt = 0;
		int attDel_result = 1;
		if(fileName!=null) {
			// 기존 파일 대신 새로 첨부한 파일이 있을 경우
			// DB에서 기존에 첨부되어있던 파일 정보 삭제
			attCnt = new BoardDao().getAttCnt(conn, postNo, "SPPT");
			attDel_result = new BoardDao().deleteAttFiles(conn, postNo, "SPPT");
			
			// 새로 첨부한 파일 정보 DB에 입력
			uploadResult = new BoardDao().uploadFile(conn, fileName, postNo, "SPPT");
		}
		
		if(updateResult>0 && uploadResult>0
				 && (attCnt==0 || attDel_result>0) ) {
			JDBCTemplate.commit(conn);
			result = 1;
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}

	public BoardPageData searchNoticeBoardList(int currPg, String searchArea, String searchKeyword) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<Notice> noticeList = new BoardDao().searchNoticeBoardList(conn, currPg, recordCountPerPage, 
																					searchArea, searchKeyword);
		String pageNavi = new BoardDao().getSearchPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"Notice", 
														-1, -1,
														searchArea, searchKeyword);
		
		BoardPageData pd = null;
		if(!noticeList.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setNoticeList(noticeList);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}
	
	public BoardPageData searchTechShareBoardList(int currPg, String searchArea, String searchKeyword) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		//ArrayList<TechSharePost> techSharePostL = new ArrayList<>();
		//techSharePostL = new BoardDao().techShareBoardList(conn);
		ArrayList<TechSharePost> techSharePostL = new BoardDao().searchTechShareBoardList(conn, currPg, recordCountPerPage, 
																								searchArea, searchKeyword);
		String pageNavi = new BoardDao().getSearchPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSh", 
														-1, -1,
														searchArea, searchKeyword);
		
		BoardPageData pd = null;
		if(!techSharePostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSharePostL(techSharePostL);
			pd.setPageNavi(pageNavi);
		}
		
		return pd;
	}
	
	public BoardPageData searchTechSupportBoardListByCompNo(int compNo, int currPg, String searchArea,
			String searchKeyword) {
		Connection conn = JDBCTemplate.getConnection();

		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TechSupportPost> techSupportPostL
			= new BoardDao().searchTechSupportBoardListByCompNo(conn, compNo, currPg, recordCountPerPage, 
																		searchArea, searchKeyword);
		String pageNavi = new BoardDao().getSearchPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSpp", 
														compNo, -1,
														searchArea, searchKeyword);
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSupportPostL(techSupportPostL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public BoardPageData searchTechSupportBoardList(int currPg, String searchArea, String searchKeyword) {
		Connection conn = JDBCTemplate.getConnection();
		
		int recordCountPerPage = 10;	// 게시물 개수
		int naviCountPerPage = 5;		// navi 개수
		
		ArrayList<TechSupportPost> techSupportPostL = new BoardDao().searchTechSupportBoardList(conn, currPg, recordCountPerPage, 
																								searchArea, searchKeyword);
		
		String pageNavi = new BoardDao().getSearchPageNavi(conn, 
														currPg, 
														recordCountPerPage, 
														naviCountPerPage, 
														"TechSpp", 
														-1, -1,
														searchArea, searchKeyword);
		
		BoardPageData pd = null;
		if(!techSupportPostL.isEmpty() && !pageNavi.isEmpty()) {
			pd = new BoardPageData();
			pd.setTechSupportPostL(techSupportPostL);
			pd.setPageNavi(pageNavi);
		}
		
		JDBCTemplate.close(conn);
		
		return pd;
	}
	
}
