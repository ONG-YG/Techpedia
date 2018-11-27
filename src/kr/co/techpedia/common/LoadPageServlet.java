package kr.co.techpedia.common;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class LoadPageServlet
 */
@WebServlet(name = "LoadPage", urlPatterns = { "/loadPage.do" })
public class LoadPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadPageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String mypageMenu = request.getParameter("checkMenu_Mypage");
		String adminMenu = request.getParameter("checkMenu_Admin");
		
		HttpSession session = request.getSession(false);
		MemberSession memSession = (MemberSession)session.getAttribute("memSession");
		memSession.setMypageMenu(mypageMenu);
		memSession.setAdminMenu(adminMenu);
		
		session.setAttribute("memSession", memSession);
		System.out.println(memSession);////////////
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
