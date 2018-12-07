package kr.co.techpedia.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.techpedia.member.model.vo.MemberSession;

/**
 * Servlet implementation class DownloadFileServlet
 */
@WebServlet(name = "DownloadFile", urlPatterns = { "/downloadFile.do" })
public class DownloadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DownloadFileServlet() {
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
			
			if(memSession!=null) {
				
				String fileName = request.getParameter("fileName");
				
				//다운로드 로직
				//1. 해당파일을 열람
				//해당파일의 정보가 있다면! 다운로드 로직을 진행
				File file = new File(getServletContext().getRealPath("/")+"uploadFile\\"+fileName); //java.io.File
				
				//2. 파일이름을 운영체제(windows)에 맞게 인코딩 해주어야함
				//String encFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				String encFileName = null;
				
				
				boolean isMSIE=request.getHeader("user-agent").indexOf("MSIE")!=-1 || request.getHeader("user-agent").indexOf("Trident")!=-1;
				System.out.println("isMSIE : "+ isMSIE);
		
				
				if(request.getHeader("user-agent").indexOf("MSIE")!=-1 || request.getHeader("user-agent").indexOf("Trident")!=-1)
				{
					encFileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
				}
				else
				{
					new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
					encFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				}
				
				//3. 파일 전송을 위한 response
				response.setContentType("application/octet-stream");
				response.setContentLengthLong(file.length());
				response.setHeader("Content-Disposition","attachment;filename=" + encFileName);
				
				//4. 파일의 내용을 읽어와야 전송할 수 있으므로 inputStream 생성
				FileInputStream fileIn = new FileInputStream(file);
				
				//5. input 스트림으로 연결된 정보를 클라이언트에게 보내기 위한
				// outputStream을 생성함
				ServletOutputStream out = response.getOutputStream();
				
				byte[] output = new byte[4096];
				
				while(fileIn.read(output, 0, 4096) != -1) {
					out.write(output,0,4096);
				}
				
				fileIn.close();
				out.close();
				
			}else {
				//세션정보가 불완전할 경우 비정상적 접근임을 알리는 페이지로 리다이렉트
				response.sendRedirect("/views/board/writeError.jsp");
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
