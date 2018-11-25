package kr.co.techpedia.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.techpedia.member.model.service.MemberService;
import kr.co.techpedia.member.model.vo.TpMember;

/**
 * Servlet implementation class UpdateMemberServlet
 */
@WebServlet(name = "UpdateMember", urlPatterns = { "/updateMember.do" })
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateMemberServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberPrivatePhone = request.getParameter("member_P_Phone");
		String memberCompanyPhone = request.getParameter("member_C_Phone");
		String memberEmail = request.getParameter("memberEmail");
		
		TpMember member = new TpMember();
		
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberPrivatePhone(memberPrivatePhone);
		member.setMemberCompanyPhone(memberCompanyPhone);
		member.setMemberEmail(memberEmail);
		
		//System.out.println("update servlet\n"+member);//////////////////
		
		int result = new MemberService().updateMember(member);
		
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
