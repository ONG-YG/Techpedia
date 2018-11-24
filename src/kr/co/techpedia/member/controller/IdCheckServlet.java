package kr.co.techpedia.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.techpedia.member.model.service.MemberService;
import kr.co.techpedia.member.model.vo.MemberSession;
import kr.co.techpedia.member.model.vo.TpMember;

/**
 * Servlet implementation class IdCheckServlet
 */
@WebServlet(name = "IdCheck", urlPatterns = { "/idCheck.do" })
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IdCheckServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		
		//System.out.println(memberId);//////////////////////////////
		
		boolean result = new MemberService().checkIdUnique(memberId);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(result) {
			// 중복되는 아이디가 존재하는 경우 (아이디 사용불가)
			response.getWriter().print(false);
		}
		else {
			// 중복되는 아이디가 존재하지 않는 경우 (아이디 사용가능)
			response.getWriter().print(true);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
