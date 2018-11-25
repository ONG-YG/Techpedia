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
import kr.co.techpedia.board.model.vo.TechSupportPost;

/**
 * Servlet implementation class GetTechShareListServlet
 */
@WebServlet(name = "GetTechSpptList", urlPatterns = { "/getTechSpptList.do" })
public class GetTechSpptListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTechSpptListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		int memberNo = Integer.parseInt( request.getParameter("memberNo") );
		String memberTypeCD = request.getParameter("memberTypeCD");
		
		ArrayList<TechSupportPost> techSupportPostL = new ArrayList<>();
		
		if(memberTypeCD.equals("COP")) {
			//로그인한 사람이 협력사 직원인 경우
			techSupportPostL = new BoardService().getTechSpptListByCopNo(memberNo);
		}
		else {
			//로그인한 사람이 제조사 직원인 경우
			techSupportPostL = new BoardService().getTechSpptListByEngNo(memberNo);
		}
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(!techSupportPostL.isEmpty()) {
			new Gson().toJson(techSupportPostL, response.getWriter());
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
