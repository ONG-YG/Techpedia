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
 * Servlet implementation class EnrollServlet
 */
@WebServlet(name = "Enroll", urlPatterns = { "/enroll.do" })
public class EnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EnrollServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		String memberName = request.getParameter("memberName");
		String memberPrivatePhone = request.getParameter("member_P_Phone");
		String memberCompanyPhone = request.getParameter("member_C_Phone");
		String memberEmail = request.getParameter("memberEmail");
		String memberTypeCD = request.getParameter("memberTypeCD");
		int compNo = Integer.parseInt( request.getParameter("compNo") );
		String companyMemNo = request.getParameter("compMemNo");
		
		TpMember member = new TpMember();
		
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		member.setMemberName(memberName);
		member.setMemberPrivatePhone(memberPrivatePhone);
		member.setMemberCompanyPhone(memberCompanyPhone);
		member.setMemberEmail(memberEmail);
		member.setMemberTypeCD(memberTypeCD);
		member.setCompNo(compNo);
		member.setCompanyMemNo(companyMemNo);
		
		System.out.println("enroll servlet\n"+member);//////////////////
		
		int result = new MemberService().joinMember(member);
		
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
