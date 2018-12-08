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
	<link href="/css/noticeList.css" rel="stylesheet" type="text/css">
	
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
   						//noticeList = [];
   						var postL = '';
						for(var i=0; i<data.noticeList.length; i++) {
							
							var grade = data.noticeList[i].ngrdName;
							var tdTag = "<td class='"+data.noticeList[i].ntcGradeCD+"'>";
							var cmmCnt = data.noticeList[i].cmmCnt;
							var cmmCnt_span = "<span class='cmmCnt'></span>";
							if (cmmCnt!=0) cmmCnt_span = "<span class='cmmCnt'>["+cmmCnt+"]</span>";
							postL += " <tr> "
										+"<td>"+data.noticeList[i].postNo+"</td> "
										+ tdTag +data.noticeList[i].ngrdName+"</td> "
										+"<td>"
											+"<a class='title_a' href='/views/main/mainpage.jsp?board=NoticeR&currPg="+currPg+"&postNo="+data.noticeList[i].postNo+"'>"
											+ data.noticeList[i].ntcTitle +"</a>" + cmmCnt_span
										+ "</td> "
										+"<td>"+data.noticeList[i].ntcWriterName+"</td> "
										+"<td>"+data.noticeList[i].ntcDate+"</td> "
										+"<td>"+data.noticeList[i].ntcCnt+"</td> "
									+"</tr> ";
						}
						
						$('#notice-tb tbody').html(postL);
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
						$('#notice-tb tbody').html('');
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
       				url : "/searchNoticeBoardList.do",
       				data : {currPg: currPg, searchArea: searchArea, searchKeyword: searchKeyword},
       				type : "post",
       				success : function(data){
       					//console.log("정상 처리 완료");
       					//alert("success");
       					//console.log(data);////////////////////
       					
       					if(data) {
       						//noticeList = [];
       						var postL = '';
    						for(var i=0; i<data.noticeList.length; i++) {
    							
    							var grade = data.noticeList[i].ngrdName;
    							var tdTag = "<td class='"+data.noticeList[i].ntcGradeCD+"'>";
    							var cmmCnt = data.noticeList[i].cmmCnt;
    							var cmmCnt_span = "<span class='cmmCnt'></span>";
    							if (cmmCnt!=0) cmmCnt_span = "<span class='cmmCnt'>["+cmmCnt+"]</span>";
    							postL += " <tr> "
    										+"<td>"+data.noticeList[i].postNo+"</td> "
    										+ tdTag +data.noticeList[i].ngrdName+"</td> "
    										+"<td>"
    											+"<a class='title_a' href='/views/main/mainpage.jsp?board=NoticeR&currPg="+currPg+"&postNo="+data.noticeList[i].postNo+"'>"
    											+ data.noticeList[i].ntcTitle +"</a>" + cmmCnt_span
    										+ "</td> "
    										+"<td>"+data.noticeList[i].ntcWriterName+"</td> "
    										+"<td>"+data.noticeList[i].ntcDate+"</td> "
    										+"<td>"+data.noticeList[i].ntcCnt+"</td> "
    									+"</tr> ";
    						}
    						
    						$('#notice-tb tbody').html(postL);
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
    						$('#notice-tb tbody').html('');
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
	
	<div id="noticeBoard">
	    
	    <span>공지사항 게시판</span>
	    
	    <div id="tableDiv">
		    <table id="notice-tb">
		    	<thead>
			        <tr>
		                <th>번호</th>
		                <th>구분</th>
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
	       			<input type="radio" name="searchArea" value="NTC_TITLE" checked/>제목
	        		<input type="text" name="searchKeyword" id="searchKeyword"/>
	        		<button id="search_btn" onclick="return search(1);">검색</button>
	        	</form>
        	</div>
        </div>
	</div>
	
</body>
</html>