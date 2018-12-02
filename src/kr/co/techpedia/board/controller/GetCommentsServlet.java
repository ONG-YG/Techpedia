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
import kr.co.techpedia.board.model.vo.Comment;

/**
 * Servlet implementation class GetCommentsServlet
 */
@WebServlet(name = "GetComments", urlPatterns = { "/getComments.do" })
public class GetCommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommentsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String board = request.getParameter("board");
		int postNo = Integer.parseInt( request.getParameter("postNo") );
		
		try {
			ArrayList<Comment> commentList = new BoardService().getCommentList(board, postNo);
			
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			
			if(!commentList.isEmpty()) {
				new Gson().toJson(commentList, response.getWriter());
			}
			else {
				throw new Exception();
			}
		} catch (Exception e) {
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
