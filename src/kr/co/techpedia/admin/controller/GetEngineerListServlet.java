package kr.co.techpedia.admin.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.techpedia.admin.model.service.AdminService;
import kr.co.techpedia.member.model.vo.TpMember;

/**
 * Servlet implementation class GetEngineerListServlet
 */
@WebServlet(name = "GetEngineerList", urlPatterns = { "/getEngineerList.do" })
public class GetEngineerListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEngineerListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		ArrayList<TpMember> engineerList = new AdminService().getEngineerList();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(!engineerList.isEmpty()) {
			new Gson().toJson(engineerList, response.getWriter());
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
