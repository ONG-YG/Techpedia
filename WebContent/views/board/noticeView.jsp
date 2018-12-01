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
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
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
	<link href="/css/noticeView.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	//getNoticeGradeList();
	    	
	    });//$(document).ready END
	    
	    /* 
	    function getNoticeGradeList() {
	    	$.ajax({
				url : "/getNoticeGrdList.do",
				//data : {},
				//type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
					}
					else {
						noticeGrdList = [];
						for(var i=0; i<data.length; i++) {
							var noticeGrade = [data[i].ntcGradeCD,
											data[i].ngrdName];
							noticeGrdList.push(noticeGrade);
						}
						
						var noticeGrdList_sel = "<option value='none'>선택</option>";
						for(var i=0; i<noticeGrdList.length; i++) {
							noticeGrdList_sel += "<option value='"+noticeGrdList[i][0]+"'>"
											+noticeGrdList[i][1]+"</option>";
						}
						$('#noticeGradeSelect').html(noticeGrdList_sel);
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	     */
    </script>
</head>
<body>
	
	<div id="noticeWrite">
	    
	    <span>공지사항</span>
	    
		    <table id="notice-tb">
		    
		    	<tr id="noticeGrade_tr">
		            <th id="noticeGrade">등급</th>
	                <td>
		                <span id="noticeGrade_span">
							공지사항 등급
		                </span>
	                </td>
		        </tr>
		        <tr id="noticeTitle_tr">
		            <th id="noticeTitle">제목</th>
	                <td id="noticeTitle_td">공지사항 제목!!!</td>
		        </tr>
	            <tr id="noticeContent_tr">
		            <th id="noticeContent">내용</th>
	                <td><textarea name="content"></textarea></td>
		        </tr>
		        <tr id="fileDownload_tr">
		            <th id="noticeFile">첨부파일</th>
	                <td id="fileDownload_td">
							
	                </td>
		        </tr>
		        
		    </table>
	    	
	</div>
	
</body>
</html>