package kr.co.techpedia.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.techpedia.board.model.service.BoardService;
import kr.co.techpedia.board.model.vo.BoardPageData;

/**
 * Servlet implementation class NoticeBoardListServlet
 */
@WebServlet(name = "NoticeBoardList", urlPatterns = { "/noticeBoardList.do" })
public class NoticeBoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeBoardListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		int currPg = Integer.parseInt( request.getParameter("currPg") );
		System.out.println("currPg Servlet : "+currPg);
		
		//ArrayList<Notice> noticeList = new BoardService().noticeBoardList(currPg);
		BoardPageData pd = new BoardService().noticeBoardList(currPg);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(pd!=null) {
			new Gson().toJson(pd, response.getWriter());
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
