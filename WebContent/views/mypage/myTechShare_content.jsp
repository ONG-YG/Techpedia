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
		//System.out.println("\nmyTechShare session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nmyTechShare memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				String cp = request.getParameter("currPg");

				int currPg = 1;
				if (cp!=null) {
					currPg = Integer.parseInt(cp);
				}
				
				//System.out.println("\nmyTechShare memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var currPg = <%=currPg%>;
				//alert("memberTypeCD : "+memberTypeCD);/////////////////////////
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
	<link href="/css/myTechShare.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	getTechShareList();
	    	
	    });//$(document).ready END
        
	    function move(pageNo){
	    	currPg = pageNo;
	    	getTechShareList();
	    }
	    
	    function rewritePost(postNo){
	    	alert("rewritePost");/////////
	    }
	    
        function delPost(postNo){
	    	alert("delPost");//////////
	    }
	    
        function getTechShareList(){
			
           	$.ajax({
   				url : "/getTechShareList.do",
   				data : {memberNo: memberNo, currPg: currPg},
   				type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					
   					if(data) {
   						//techShPostList = [];
   						var postL = '';
						for(var i=0; i<data.techSharePostL.length; i++) {
							postL += " <tr> "
								+"<td>"+data.techSharePostL[i].postNo+"</td> "
								+"<td>"
								+"<a class='title_a' target='_blank' href='/views/main/mainpage.jsp?board=TechShR&currPg="+currPg+"&postNo="+data.techSharePostL[i].postNo+"'>"
									+ data.techSharePostL[i].shrTitle +"</a>"
								+"</td> "
								+"<td>"+data.techSharePostL[i].shrDate+"</td> "
								+"<td> "
									+"<button id='rewrite-btn' onclick='rewritePost("+data.techSharePostL[i].postNo+");'>수정</button> "
								+"</td> "
								+"<td> "
									+"<button id='delete-btn' onclick='delPost("+data.techSharePostL[i].postNo+");'>삭제</button> "
								+"</td> "
							+"</tr> ";
						}
						
						//$('#techShare-tb').html(postL);
						$('#techShare-tb tbody').html(postL);
						$('#navi').html(data.pageNavi);
   					}
   					else {
						var emptyPageNavi ="<span><img src='' id='prev_img' width='20px'></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span><img src='' id='next_img' width='20px'></span>";
						$('#techShare-tb tbody').html('');
						$('#navi').html(emptyPageNavi);
   						alert("작성한 기술 공유 게시물이 존재하지 않습니다.");
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
	    
	    <span>나의 기술 공유 게시물</span>
	    
	    <div id="tableDiv">
		    <table id="techShare-tb">
		    	<thead>
			        <tr>
		                <th>번호</th>
		                <th>제목</th>
		                <th>작성일</th>
		                <th>수정</th>
		                <th>삭제</th>
			        </tr>
	            </thead>
	            
	            <tbody>
	            </tbody>
	            
		    </table>
	    </div>
		<div id="bottomSpace">
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