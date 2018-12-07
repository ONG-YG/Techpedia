package kr.co.techpedia.board.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.co.techpedia.board.model.service.BoardService;
import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class NoticeReWriteServlet
 */
@WebServlet(name = "NoticeReWrite", urlPatterns = { "/noticeReWrite.do" })
public class NoticeReWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeReWriteServlet() {
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
			
			if(memSession!=null
					&& !memSession.getMemberTypeCD().equals("COP")) {
				
				
				// 최대 업로드 파일 사이즈
				int fileSizeLimit = 10 * 1024 *1024; //10MB
				
				// 업로드 될 경로
				String uploadPath = getServletContext().getRealPath("/")+"uploadFile";
				//System.out.println(uploadPath);//////////////
				
				// 파일 인코딩 타입
				String encType="UTF-8";
				
				//위 정보들을 바탕으로 파일 업로드에 사용하는 MultipartRequest 객체를 생성
				MultipartRequest multi = new MultipartRequest(request, 
																uploadPath, 
																fileSizeLimit, 
																encType, 
																new DefaultFileRenamePolicy());
				
				String fileName = multi.getFilesystemName("upfile");
				//System.out.println("파일 이름 :"+fileName);////////////
				
				int postNo = Integer.parseInt( multi.getParameter("postNo") );
				
				String noticeGrade = multi.getParameter("noticeGrade");
				String title = multi.getParameter("title");
				String content = multi.getParameter("content");
				
				
				int result = new BoardService().updateNotice(postNo, noticeGrade, title, content, fileName);
				
				if(result>0) {
					
					if(fileName!=null) {
						String originFile = multi.getParameter("originFile");
						
						//기존파일 삭제
						String deletePath = getServletContext().getRealPath("/")+"uploadFile\\"+originFile;
						
						File delFile = new File(deletePath);
						delFile.delete();
						
					}
					
					//작성글 등록 완료 페이지로 리다이렉트
					response.sendRedirect("/views/board/writeSuccess.jsp");
				}else {
					if(fileName!=null) {
						//새로 업로드한 파일 삭제
						String deletePath = getServletContext().getRealPath("/")+"uploadFile\\"+fileName;
						
						File delFile = new File(deletePath);
						
						if(delFile.exists()) {
							delFile.delete();
						}
					}
					
					//작성글 등록 실패 페이지로 리다이렉트
					response.sendRedirect("/views/board/writeFail.jsp");
				}
				
			}else {
				response.sendRedirect("/views/board/writeError.jsp");
			}
			
			
			
			
		} catch (Exception e) {
			response.sendRedirect("/views/board/writeFail.jsp");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
