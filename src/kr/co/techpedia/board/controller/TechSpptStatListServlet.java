package kr.co.techpedia.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.co.techpedia.board.model.service.BoardService;
import kr.co.techpedia.board.model.vo.SupportState;
import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class TechSpptStatListServlet
 */
@WebServlet(name = "TechSpptStatList", urlPatterns = { "/techSpptStatList.do" })
public class TechSpptStatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TechSpptStatListServlet() {
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
				
				String memberTypeCD = request.getParameter("memberTypeCD");
				
				String superviser = "";
				if(memberTypeCD.equals("COP")) superviser = "PARTNER";
				else superviser = "ENGINEER";
				ArrayList<SupportState> SupportStateL = new BoardService().getSupportStateList(superviser);
				
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				
				if(!SupportStateL.isEmpty()) {
					new Gson().toJson(SupportStateL, response.getWriter());
				}
				else {
					response.getWriter().print(false);
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
