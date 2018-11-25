package kr.co.techpedia.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import kr.co.techpedia.member.model.service.MemberService;
import kr.co.techpedia.member.model.vo.MemberSession;
import kr.co.techpedia.member.model.vo.TpMember;

/**
 * Servlet implementation class GetMemberInfoServlet
 */
@WebServlet(name = "GetMemberInfo", urlPatterns = { "/getMemberInfo.do" })
public class GetMemberInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMemberInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberId = request.getParameter("memberId");
		
		TpMember memberInfo = new MemberService().getMemberInfo(memberId);
		
		//System.out.println("after select\n"+memberInfo);/////////////////
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(memberInfo!=null && memberInfo.getMemberActive()=='Y') {
			new Gson().toJson(memberInfo, response.getWriter());
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
