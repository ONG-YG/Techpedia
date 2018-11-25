package kr.co.techpedia.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.techpedia.member.model.service.MemberService;

/**
 * Servlet implementation class SignOutServlet
 */
@WebServlet(name = "SignOut", urlPatterns = { "/signOut.do" })
public class SignOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignOutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		System.out.println("signOut servlet\n"+memberId);//////////////////
		System.out.println("signOut servlet\n"+memberPw);//////////////////
		
		int result = new MemberService().deActivateMember(memberId, memberPw);
		
		if(result>0) {
			response.getWriter().print(true);
		}
		else {
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
