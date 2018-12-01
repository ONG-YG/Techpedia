package kr.co.techpedia.board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class WritePostServlet
 */
@WebServlet(name = "WritePost", urlPatterns = { "/writePost.do" })
public class WritePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WritePostServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession(false);
		
		try {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			
			boolean chk = true;
			String writeStart = "false";
			
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				String currBoard = memSession.getCurrBoard();
				
				if(currBoard.equals("Notice")) {
					if(!memberTypeCD.equals("COP")) {
						//글작성가능
						chk = false;
						//writeStart = "true";
					}
				}
				else if(currBoard.equals("TechSpp")) {
					if(memberTypeCD.equals("COP")) {
						//글작성가능
						chk = false;
						//writeStart = "true";
					}
				}
				else if(currBoard.equals("TechSh")) {
					System.out.println("we are here");////////////
					//모두 글 작성 가능
					chk = false;
//					writeStart = "true";
//					
//					RequestDispatcher view = request.getRequestDispatcher("views/main/mainpage.jsp?board=TechShW");
//					request.setAttribute("writeStart", writeStart);
//					view.forward(request, response);
				}
				
				if(chk==false) {
					writeStart = "true";
					
					RequestDispatcher view = request.getRequestDispatcher("views/main/mainpage.jsp?board="+currBoard+"W");
					request.setAttribute("writeStart", writeStart);
					view.forward(request, response);
				}
			}
			
			if(chk==true) {
				throw new Exception();
			}
		} catch (Exception e) {
			//에러 response sendredirect 로 error페이지로 이동 거기서
			//mainpage로 이동하고 alert로 비정상적 접근이라고 띄워주기
			response.sendRedirect("/views/board/writeError.jsp");
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
