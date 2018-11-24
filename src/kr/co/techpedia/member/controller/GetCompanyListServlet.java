package kr.co.techpedia.member.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.techpedia.member.model.service.MemberService;
import kr.co.techpedia.member.model.vo.CompanyInfo;

/**
 * Servlet implementation class GetCompanyListServlet
 */
@WebServlet(name = "GetCompanyList", urlPatterns = { "/getCompanyList.do" })
public class GetCompanyListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCompanyListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<CompanyInfo> companyList = new MemberService().getCompanyList();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(!companyList.isEmpty()) {
			new Gson().toJson(companyList, response.getWriter());
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
