package kr.co.techpedia.board.controller;

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
 * Servlet implementation class TechSpportPostWriteServlet
 */
@WebServlet(name = "TechSpportPostWrite", urlPatterns = { "/techSpportPostWrite.do" })
public class TechSpportPostWriteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TechSpportPostWriteServlet() {
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
					&& memSession.getMemberTypeCD().equals("COP")
					&& memSession.getCurrBoard().equals("TechSpp")) {
				
				int memberNo = memSession.getMemberNo();
				
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
				
				
				// 업로드 파일의 실제 총 경로 (filePath)
				//업로드 되는  경로 + 파일이름
				//String fullFilePath = uploadPath+"\\"+fileName;
				//System.out.println("총 경로 : "+fullFilePath);
				//  \는 따로 역할이 있기 때문에 문자로\를 쓰려면 두번써줘야함
				
				
				// 파일의 크기 (length)
				//File file = new File(fullFilePath); // import java.io.File
				//long fileSize = file.length(); // 파일의 사이즈를 가져옴
				
				String title = multi.getParameter("title");
				String content = multi.getParameter("content");
				
				
				int result = new BoardService().insertTechSupport(memberNo, title, content, fileName);
				
				if(result>0) {
					//작성글 등록 완료 페이지로 리다이렉트
					response.sendRedirect("/views/board/writeSuccess.jsp");
				}else {
					//작성글 등록 실패 페이지로 리다이렉트
					response.sendRedirect("/views/board/writeFail.jsp");
				}
				
			}else {
				throw new Exception();
			}
			
			
			
			
		} catch (Exception e) {
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
