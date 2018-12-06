package kr.co.techpedia.member.controller;

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
 * Servlet implementation class GetContactInfoServlet
 */
@WebServlet(name = "GetContactInfo", urlPatterns = { "/getContactInfo.do" })
public class GetContactInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetContactInfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		try {
			int memberNo = Integer.parseInt( request.getParameter("memberNo") );
			
			TpMember contactInfo = new MemberService().getContactInfo(memberNo);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			if(contactInfo!=null) {
				new Gson().toJson(contactInfo, response.getWriter());
			}
			else {
				response.getWriter().print(false);
			}
			
		} catch (Exception e) {
			response.sendRedirect("/views/member/abnormalAccess.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
