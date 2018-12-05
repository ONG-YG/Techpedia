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
				memSession.setCurrBoard("TechSpp");
				session.setAttribute("memSession", memSession);
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
		
		if(memberTypeCD=='COP') {
    		$('#writeBtn').css('display','block');
    	}
    	
    });//$(document).ready END
    
    function move(pageNo){
    	currPg = pageNo;
    	techSpptBoardList();
    }

    function writePost(){
    	location.href="/writePost.do";
    }
    
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
						//techSpPostList = [];
						var postL = '';
						for(var i=0; i<data.techSupportPostL.length; i++) {
							var engName = data.techSupportPostL[i].spptEngName;
							if (engName==null) {
								engName = '(배정되지 않음)';
							}
							
							var newPost = "<span></span>";
							if (engName=='(배정되지 않음)') {
								newPost = "<span class='newPost'>new</span>";
							}

							var cmmCnt = data.techSupportPostL[i].cmmCnt;
							var cmmCnt_span = "<span class='cmmCnt'></span>";
							if (cmmCnt!=0) cmmCnt_span = "<span class='cmmCnt'>["+cmmCnt+"]</span>";
							
							postL += " <tr> "
										+"<td>"+data.techSupportPostL[i].postNo+"</td> "
										+"<td>"+data.techSupportPostL[i].spptStatName+"</td> "
										+"<td>"
										+"<a class='title_a' href='/views/main/mainpage.jsp?board=TechSppR&currPg="+currPg+"&postNo="+data.techSupportPostL[i].postNo+"'>"
											+data.techSupportPostL[i].spptTitle +"</a>" + cmmCnt_span + newPost
										+"</td> "
										+"<td>"+data.techSupportPostL[i].spptWriterName+"</td> "
										+"<td>"+engName+"</td> "
										+"<td>"+data.techSupportPostL[i].spptDate+"</td> "
										+"<td>"+data.techSupportPostL[i].spptCnt+"</td> "
									+"</tr> ";
						}
						
						$('#techSppt-tb tbody').html(postL);
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
						$('#techSppt-tb tbody').html('');
						$('#navi').html(emptyPageNavi);
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