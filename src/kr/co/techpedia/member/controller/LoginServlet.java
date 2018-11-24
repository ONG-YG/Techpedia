package kr.co.techpedia.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "Login", urlPatterns = { "/login.do" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		TpMember member = new TpMember();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		//System.out.println(member);
		
		member = new MemberService().selectOneMember(member);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(member!=null && member.getMemberActive()=='Y') {
			MemberSession memSession = new MemberSession();
			memSession.setMemberNo(member.getMemberNo());
			memSession.setMemberId(member.getMemberId());
			memSession.setMemberTypeCD(member.getMemberTypeCD());
			memSession.setCompNo(member.getCompNo());
			memSession.setMemberActive(member.getMemberActive());
			memSession.setMemberPhoto(member.getMemberPhoto());
			
			//System.out.println(memSession);
			
			HttpSession session = request.getSession(true);
			session.setAttribute("memSession", memSession);
			
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
