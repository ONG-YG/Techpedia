package kr.co.techpedia.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.co.techpedia.board.model.service.BoardService;
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
			
			// 하루 이상 엔지니어 배정되지 않은 기술지원글 담당 엔지니어 자동배정
			new BoardService().autoEngineerUpdate();
			
			int auto_result = new BoardService().checkEngAuto();
			int engChk_result = new BoardService().checkEngCK(memSession.getMemberNo());
			int [] result = {auto_result, engChk_result};

			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			new Gson().toJson(result, response.getWriter());
			//response.getWriter().print(result);
			//if(result>0) response.getWriter().print(1);
			//else response.getWriter().print(0);
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
