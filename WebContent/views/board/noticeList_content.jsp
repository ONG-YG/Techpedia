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
		//System.out.println("\nNoticeBoard session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nNoticeBoard memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				//String cp = request.getParameter("currPg");
				//System.out.println("cp : "+cp);//////////////
				System.out.println("board : "+request.getParameter("board"));////
				int currPg = 1;
				//if (cp!=null) {
					//currPg = Integer.parseInt(cp);
					//System.out.println("cp no : "+currPg);//////////////
				//}
				
				//System.out.println("\nNoticeBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var currPg = <%=currPg%>;
			</script>
	<%
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
	<link href="/css/noticeList.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	noticeBoardList();
	    	
	    });//$(document).ready END
        
        
        function noticeBoardList(){
			alert("currPg : "+currPg);///////////////////
           	$.ajax({
   				url : "/noticeBoardList.do",
   				data : {currPg: currPg},
   				type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					
   					if(data) {
   						
   						noticeList = [];
						for(var i=0; i<data.noticeList.length; i++) {
							var post = [data.noticeList[i].postNo,
										data.noticeList[i].ntcTitle,
										data.noticeList[i].ntcWriterName,
										data.noticeList[i].ntcDate,
										data.noticeList[i].ntcCnt];
							noticeList.push(post);
						}
						
						//var postL = $('#notice-tb tbody').html();
						var postL = '';
						for(var i=0; i<noticeList.length; i++) {
							postL += " <tr> "
										+"<td>"+noticeList[i][0]+"</td> "
										+"<td>"+noticeList[i][1]+"</td> "
										+"<td>"+noticeList[i][2]+"</td> "
										+"<td>"+noticeList[i][3]+"</td> "
										+"<td>"+noticeList[i][4]+"</td> "
									+"</tr> ";
							
						}
						$('#notice-tb tbody').html(postL);
						$('#navi').html(data.pageNavi);
   					}
   					else {
   						//alert("작성한 기술 공유 게시물이 존재하지 않습니다.");
						//location.href = "/views/main/mainpage.jsp";
   					} 
   				},
   				error : function(){
   					//console.log("ajax 통신 에러");
   					alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href = "/views/main/mainpage.jsp";
   				},
   				complete : function(){
   					//alert("complete");
   				}
   			});
        }//function END
         
    </script>
</head>
<body>
	
	<div id="noticeBoard">
	    
	    <span>공지사항 게시판</span>
	    
	    <div id="tableDiv">
		    <table id="notice-tb">
		    	<thead>
			        <tr>
		                <th>번호</th>
		                <th>제목</th>
		                <th>작성자</th>
		                <th>작성일</th>
		                <th>조회수</th>
			        </tr>
	            </thead>
	            
	            <tbody>
	            </tbody>
	            
		    </table>
	    </div>
	    <div id="bottomSpace">
            <div id="navi">
                <a href="#"><img src='/img/prev.png' id='prev_img' width='20px'></a>
                <a>1</a>
                <a>2</a>
                <a>3</a>
                <a>4</a>
                <a>5</a>
                <a href="#"><img src='/img/next.png' id='next_img' width='20px'></a>
            </div>
            <button id="writeBtn" onclick="return false;">글 쓰기</button>
        </div>
	</div>
	
</body>
</html>