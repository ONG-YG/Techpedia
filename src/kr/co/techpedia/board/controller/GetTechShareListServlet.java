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
import kr.co.techpedia.board.model.vo.TechSharePost;

/**
 * Servlet implementation class GetTechShareListServlet
 */
@WebServlet(name = "GetTechShareList", urlPatterns = { "/getTechShareList.do" })
public class GetTechShareListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTechShareListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		int memberNo = Integer.parseInt( request.getParameter("memberNo") );
		
		ArrayList<TechSharePost> techSharePostL = new BoardService().getTechShareList(memberNo);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(!techSharePostL.isEmpty()) {
			new Gson().toJson(techSharePostL, response.getWriter());
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
