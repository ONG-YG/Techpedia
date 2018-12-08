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
				String cp = request.getParameter("currPg");
				//System.out.println("cp : "+cp);//////////////
				
				int currPg = 1;
				if (cp!=null) {
					currPg = Integer.parseInt(cp);
				}
				memSession.setCurrBoard("TechSh");
				session.setAttribute("memSession", memSession);
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
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
	<link href="/css/techSharePostList.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	techShareBoardList();
	    	
	    	
	    });//$(document).ready END
	    
	    function move(pageNo){
	    	currPg = pageNo;
	    	techShareBoardList();
	    }
	    
	    function writePost(){
	    	location.href="/writePost.do";
	    }
	    
        function techShareBoardList(){
			
           	$.ajax({
   				url : "/techShareBoardList.do",
   				data : {currPg: currPg},
   				type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					
   					if(data) {
   						//techShPostList = [];
   						var postL = '';
						for(var i=0; i<data.techSharePostL.length; i++) {
							var cmmCnt = data.techSharePostL[i].cmmCnt;
							var cmmCnt_span = "<span class='cmmCnt'></span>";
							if (cmmCnt!=0) cmmCnt_span = "<span class='cmmCnt'>["+cmmCnt+"]</span>";
							postL += " <tr> "
										+"<td>"+data.techSharePostL[i].postNo+"</td> "
										+"<td>"
										+"<a class='title_a' href='/views/main/mainpage.jsp?board=TechShR&currPg="+currPg+"&postNo="+data.techSharePostL[i].postNo+"'>"
											+data.techSharePostL[i].shrTitle +"</a>" + cmmCnt_span
										+"</td> "
										+"<td>"+data.techSharePostL[i].shrWriterName+"</td> "
										+"<td>"+data.techSharePostL[i].shrDate+"</td> "
										+"<td>"+data.techSharePostL[i].shrCnt+"</td> "
									+"</tr> ";
						}
						
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

	    function search(currPg) {
        	var searchArea = $('#searchArea_form input[name=searchArea]:checked').val();
        	var searchKeyword = $('#searchKeyword').val();
        	
        	if(searchArea==null) {
        		alert("검색 조건을 선택해주세요.");///////////
        	}
        	else if(searchKeyword=='') {
        		alert("검색어를 입력해주세요.");
        	}
	    	else {
	    		
	    		$.ajax({
	   				url : "/searchTechShareBoardList.do",
	   				data : {currPg: currPg, searchArea: searchArea, searchKeyword: searchKeyword},
	   				type : "post",
	   				success : function(data){
	   					//console.log("정상 처리 완료");
	   					//alert("success");
	   					//console.log(data);////////////////////
	   					
	   					if(data) {
	   						//techShPostList = [];
	   						var postL = '';
							for(var i=0; i<data.techSharePostL.length; i++) {
								var cmmCnt = data.techSharePostL[i].cmmCnt;
								var cmmCnt_span = "<span class='cmmCnt'></span>";
								if (cmmCnt!=0) cmmCnt_span = "<span class='cmmCnt'>["+cmmCnt+"]</span>";
								postL += " <tr> "
											+"<td>"+data.techSharePostL[i].postNo+"</td> "
											+"<td>"
											+"<a class='title_a' href='/views/main/mainpage.jsp?board=TechShR&currPg="+currPg+"&postNo="+data.techSharePostL[i].postNo+"'>"
												+data.techSharePostL[i].shrTitle +"</a>" + cmmCnt_span
											+"</td> "
											+"<td>"+data.techSharePostL[i].shrWriterName+"</td> "
											+"<td>"+data.techSharePostL[i].shrDate+"</td> "
											+"<td>"+data.techSharePostL[i].shrCnt+"</td> "
										+"</tr> ";
							}
							
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
	    		
	    	}
	    	
	    	return false;
	    }//function END
	    
    </script>
</head>
<body>
	
	<div id="TechShareBoard">
	    
	    <span>기술 공유 게시판</span>
	    
	    <div id="tableDiv">
		    <table id="techShare-tb">
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
        <div id="search_div">
        	<div>
	       		<form id="searchArea_form">
	       			<input type="radio" name="searchArea" value="WRITER_NAME" />작성자
	       			<input type="radio" name="searchArea" value="SHR_TITLE" checked/>제목
	        		<input type="text" name="searchKeyword" id="searchKeyword"/>
	        		<button id="search_btn" onclick="return search(1);">검색</button>
	        	</form>
        	</div>
        </div>
	</div>
	
</body>
</html>