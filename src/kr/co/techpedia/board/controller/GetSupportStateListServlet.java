package kr.co.techpedia.board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.techpedia.board.model.service.BoardService;
import kr.co.techpedia.board.model.vo.SupportState;

/**
 * Servlet implementation class GetSupportStateListServlet
 */
@WebServlet(name = "GetSupportStateList", urlPatterns = { "/getSupportStateList.do" })
public class GetSupportStateListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSupportStateListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String memberTypeCD = request.getParameter("memberTypeCD");
		String superviser = null;
		
		if(memberTypeCD.equals("COP")) {
			superviser = "PARTNER";
		}else {
			superviser = "ENGINEER";
		}
		
		ArrayList<SupportState> supportStateList = new BoardService().getSupportStateList(superviser);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(!supportStateList.isEmpty()) {
			new Gson().toJson(supportStateList, response.getWriter());
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
