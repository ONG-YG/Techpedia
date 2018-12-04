package kr.co.techpedia.admin.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.techpedia.member.model.service.MemberService;
import kr.co.techpedia.member.model.vo.TpMember;

/**
 * Servlet implementation class GetMemberInfoByMemNoServlet
 */
@WebServlet(name = "GetMemberInfoByMemNo", urlPatterns = { "/getMemberInfoByMemNo.do" })
public class GetMemberInfoByMemNoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMemberInfoByMemNoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		int memberNo = Integer.parseInt( request.getParameter("memberNo") );
		
		//TpMember memberInfo = new AdminService().getMemberInfoByMemNo(memberNo);
		TpMember memberInfo = new MemberService().getMemberInfo(memberNo);
		
		
		//System.out.println("after select\n"+memberInfo);/////////////////
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(memberInfo!=null) {
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
