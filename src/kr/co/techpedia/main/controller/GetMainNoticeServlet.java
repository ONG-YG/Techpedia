package kr.co.techpedia.main.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.techpedia.main.model.service.MainService;
import kr.co.techpedia.main.model.vo.Notice;

/**
 * Servlet implementation class GetMainNoticeServlet
 */
@WebServlet(name = "GetMainNotice", urlPatterns = { "/getMainNotice.do" })
public class GetMainNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetMainNoticeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Notice mainNotice = new MainService().getMainNotice();
		
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		if(mainNotice!=null) {
			//response.getWriter().print(false);
			new Gson().toJson(mainNotice, response.getWriter());
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
