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
				String cp = request.getParameter("currPg");
				//System.out.println("cp : "+cp);//////////////
				
				int currPg = 1;
				if (cp!=null) {
					currPg = Integer.parseInt(cp);
				}
				memSession.setCurrBoard("Notice");
				session.setAttribute("memSession", memSession);
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
	<link href="/css/noticeList.css?ver=1" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	noticeBoardList();
	    	
	    	if(memberTypeCD!='COP') {
	    		$('#writeBtn').css('display','block');
	    	}
	    	
	    });//$(document).ready END
        
	    function move(pageNo){
	    	currPg = pageNo;
	    	noticeBoardList();
	    }

	    function writePost(){
	    	location.href="/writePost.do";
	    }
	    
        function noticeBoardList(){
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
										+"<td><a class='title_a' href='#'>"+noticeList[i][1]+"</a></td> "
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
	    	<button id="writeBtn" onclick="writePost();">글 쓰기</button>
            <div id="navi">
                <span><img src='' id='prev_img' width='20px'></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span><img src='' id='next_img' width='20px'></span>
            </div>
        </div>
	</div>
	
</body>
</html>