package kr.co.techpedia.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.techpedia.board.model.service.BoardService;
import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class WriteCommentServlet
 */
@WebServlet(name = "WriteComment", urlPatterns = { "/writeComment.do" })
public class WriteCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteCommentServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession(false);
		
		try {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			
			if(memSession!=null) {
				
				int memberNo = memSession.getMemberNo();
				String boardCD = request.getParameter("boardCD");
				int postNo = Integer.parseInt(request.getParameter("postNo"));
				String comment = request.getParameter("comment");
				
				int result = new BoardService().insertComment(comment, memberNo, postNo, boardCD);
				
				if(result>0) {
					response.getWriter().print(true);
				}else {
					throw new Exception();
				}
				
			}else {
				//세션정보가 불완전할 경우 비정상적 접근임을 알리는 페이지로 리다이렉트
				response.sendRedirect("/views/board/writeError.jsp");
			}
			
		} catch (Exception e) {
			response.getWriter().print(false);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
