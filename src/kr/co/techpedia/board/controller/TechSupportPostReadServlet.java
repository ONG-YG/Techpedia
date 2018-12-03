package kr.co.techpedia.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.co.techpedia.board.model.service.BoardService;
import kr.co.techpedia.board.model.vo.TechSupportPost;
import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class TechSupportPostReadServlet
 */
@WebServlet(name = "TechSupportPostRead", urlPatterns = { "/techSupportPostRead.do" })
public class TechSupportPostReadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TechSupportPostReadServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		int postNo = Integer.parseInt( request.getParameter("postNo") );
		
		try {
			HttpSession session = request.getSession(false);
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			if(memSession!=null) {
				
				TechSupportPost post = new BoardService().getOneTechSupportPost(postNo);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				
				if(post!=null) {
					int result = new BoardService().addReadCnt("TECH_SPPT", "SPPT_CNT", postNo);
					if(result>0) new Gson().toJson(post, response.getWriter());
					else throw new Exception();
				}
				else {
					throw new Exception();
				}
				
			}else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			response.getWriter().print(false);
			//response.sendRedirect("/views/board/readFail.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
