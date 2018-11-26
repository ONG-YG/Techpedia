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
				
				System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				
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
	<link href="/css/techSharePostList.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	techShareBoardList();
	    	
	    });//$(document).ready END
        
        
        function techShareBoardList(){
			
           	$.ajax({
   				url : "/techShareBoardList.do",
   				//data : {memberNo: memberNo},
   				//type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					
   					if(data) {
   						techShPostList = [];
						for(var i=0; i<data.length; i++) {
							var post = [data[i].postNo,
										data[i].shrTitle,
										data[i].shrWriterName,
										data[i].shrDate,
										data[i].shrCnt];
							techShPostList.push(post);
						}
						
						var postL = $('#techShare-tb').html();
						for(var i=0; i<techShPostList.length; i++) {
							postL += " <tr> "
										+"<td>"+techShPostList[i][0]+"</td> "
										+"<td>"+techShPostList[i][1]+"</td> "
										+"<td>"+techShPostList[i][2]+"</td> "
										+"<td>"+techShPostList[i][3]+"</td> "
										+"<td>"+techShPostList[i][4]+"</td> "
									+"</tr> ";
						}
						$('#techShare-tb').html(postL);
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
	
	<div id="myTechShare">
	    
	    <span>기술 공유 게시판</span>
	    
	    <table id="techShare-tb">
	        <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일</th>
                <th>조회수</th>
	        </tr>
            
            
	    </table>
	    
	</div>
	
</body>
</html>