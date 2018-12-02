<%@page import="kr.co.techpedia.board.model.vo.Notice"%>
<%@page import="kr.co.techpedia.member.model.vo.MemberSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<script>
		function accessDenied() {
			location.href="/index.jsp";
			alert("로그인해주세요.");
		}
	</script>
	<%
		session = request.getSession(false);
		//System.out.println("\nTechShareBoard session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nTechShareBoard memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				String currPg = request.getParameter("currPg");
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var currPg = <%=currPg%>;
				var postNo = -1;
			</script>
	<%
				if(!request.getParameter("postNo").equals("null")){
					int postNo = Integer.parseInt(request.getParameter("postNo"));
					%>
					<script>
						postNo = <%=postNo%>;
					</script>
					<%
				}else {
					%>
					<script>
						location.href="/views/board/readError.jsp";
					</script>
					<%
				}
			}else {			
	%>
				<script>
					accessDenied();
				</script>
	<%
			}
		}else {
	%>
		<script>
			accessDenied();
		</script>
	<%
		}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/css/techSupportPostView.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	if(postNo!=-1) {
	    		getTechSppPost();
	    	}
	    	
	    	
	    });//$(document).ready END
	    
	    function goBoard() {
	    	location.href="/views/main/mainpage.jsp?board=TechSpp&currPg="+currPg;
	    }//function END
	    
	    function rewrite() {
	    	location.href="/views/main/mainpage.jsp?board=TechSppRW&postNo="+postNo;
	    }//function END
	    
	    function deletePost() {
	    	alert("deletePost");////////////////
	    }//function END
	    
	    function getTechSppPost() {
	    	$.ajax({
				url : "/techSupportPostRead.do",
				data : {postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/views/board/readFail.jsp";
					}
					else {
						shrWriter = data.shrWriter;
						$('#techSppTitle_td').html(data.shrTitle);
						$('#techSppContent_txt').html(data.shrContent);
						$('#techSppWriter_td').html(data.shrWriterName);
						$('#techSppDate_td').html(data.shrDate);
						$('#techSppCnt_td').html(data.shrCnt+1);
						//$('#fileDownload_td').html();//첨부파일
						
						if(memberNo==data.shrWriter) {
							$('#rewrite_btn').css('display','block');
							$('#rewrite_btn').attr('onclick','rewrite();');
							$('#delete_btn').css('display','block');
							$('#delete_btn').attr('onclick','deletePost();');
						}
						
						getComments();
						
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/views/board/readFail.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	    
	    
    </script>
</head>
<body>
	
	<div id="techSppWrite">
	    
	    <span>공지사항</span>
	    
		    <table id="techSpp-tb">
		    	
		    	<tr id="techSppState_tr">
		            <th id="techSppState">진행현황</th>
	                <td>
		                <span id="techSppState_span"></span>
	                </td>
		        </tr>
		        <tr id="techSppTitle_tr">
		            <th id="techSppTitle">제목</th>
	                <td id="techSppTitle_td"></td>
		        </tr>
		        <tr id="techSppWriter_tr">
		            <th id="techSppWriter">협력사 담당자</th>
	                <td id="techSppWriter_td"></td>
		        </tr>
		        <tr id="techSppEngineer_tr">
		            <th id="techSppEngineer">협력사 담당자</th>
	                <td id="techSppEngineer_td"></td>
		        </tr>
		        <tr id="techSppDate_tr">
		            <th id="techSppDate">작성일</th>
	                <td id="techSppDate_td"></td>
		        </tr>
		        <tr id="techSppCnt_tr">
		            <th id="techSppCnt">조회수</th>
	                <td id="techSppCnt_td"></td>
		        </tr>
	            <tr id="techSppContent_tr">
		            <th id="techSppContent">내용</th>
	                <td id="techSppContent_td">
                        <textarea id="techSppContent_txt" readonly></textarea>
                    </td>
		        </tr>
		        <tr id="fileDownload_tr">
		            <th id="techSppFile">첨부파일</th>
	                <td id="fileDownload_td">
						첨부파일 다운로드 링크
	                </td>
		        </tr>
		        <tr id="button_tr">
	                <td colspan="2">
	                	<button id="goBoard_btn" onclick="goBoard();">목록</button>
	                	<button id="rewrite_btn" onclick="">수정</button>
	                	<button id="delete_btn" onclick="">삭제</button>
	                </td>
	            </tr>
		    </table>
	    	<table id="commentWrite_tb">
                <thead>
                    <tr>
                        <th>댓글 작성</th>
                        <td id="cmmWrite_td"><textarea id="cmmWrite_txt"></textarea></td>
                        <td id="cmmWrite_btn_td"><button onclick="writeCmm();">등록</button></td>
                    </tr>
                </thead>
            </table>
            <table id="commentView_tb">
                    <thead>
                    <tr>
                        <th>작성자</th>
                        <th>댓글내용</th>
                        <th>작성시간</th>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
	</div>
	
</body>
</html>