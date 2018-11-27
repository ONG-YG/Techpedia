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
		//System.out.println("\nTechSupportBoard session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nTechSupportBoard memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				int compNo = memSession.getCompNo();
				String cp = request.getParameter("currPg");
				//System.out.println("cp : "+cp);//////////////
				
				int currPg = 1;
				if (cp!=null) {
					currPg = Integer.parseInt(cp);
				}
				
				//System.out.println("\nTechSupportBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var compNo = <%=compNo%>;
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
	<link href="/css/techSupportPostList.css" rel="stylesheet" type="text/css">
	
	<script>
	$(document).ready(function(){
    	
		techSpptBoardList();
    	
    });//$(document).ready END
    
    
    function techSpptBoardList(){
		
       	$.ajax({
				url : "/techSupportBoardList.do",
				data : {memberTypeCD: memberTypeCD, compNo: compNo, currPg: currPg},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);////////////////////
					
					if(data) {
						techSpPostList = [];
					for(var i=0; i<data.length; i++) {
						var engName = data[i].spptEngName;
						
						if (engName==null) {
							engName = '(배정되지 않음)';
						}
						var post = [data[i].postNo,
									data[i].spptStatName,
									data[i].spptTitle,
									data[i].spptWriterName,
									engName,
									data[i].spptDate,
									data[i].spptCnt];
						techSpPostList.push(post);
					}
					
					//var postL = $('#techSppt-tb').html();
					var postL = '';
					for(var i=0; i<techSpPostList.length; i++) {
						postL += " <tr> "
									+"<td>"+techSpPostList[i][0]+"</td> "
									+"<td>"+techSpPostList[i][1]+"</td> "
									+"<td>"+techSpPostList[i][2]+"</td> "
									+"<td>"+techSpPostList[i][3]+"</td> "
									+"<td>"+techSpPostList[i][4]+"</td> "
									+"<td>"+techSpPostList[i][5]+"</td> "
									+"<td>"+techSpPostList[i][6]+"</td> "
								+"</tr> ";
					}
					$('#techSppt-tb tbody').html(postL);
					}
					else {
						//alert("담당 중인 기술 지원 게시물이 존재하지 않습니다.");
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
	
	<div id="TechSpptBoard">
	    
	    <span>기술 지원 게시판</span>
	    
	    <div id="tableDiv">
		    <table id="techSppt-tb">
		    	<thead>
			        <tr>
		                <th>번호</th>
		                <th>진행현황</th>
		                <th>제목</th>
		                <th>협력사 담당자</th>
		                <th>담당 엔지니어</th>
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
        </div>s
	</div>
	
</body>
</html>