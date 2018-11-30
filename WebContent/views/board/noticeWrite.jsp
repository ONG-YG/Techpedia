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
				
				if(!writeStart) {
					location.href="/views/board/writeError.jsp";
				}
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
	<link href="/css/noticeWrite.css?ver=1" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	$('#fileInput').click(function(){
	    		if(event.currentTarget==this) {
	    			return false;
	    		}
	    	});
	    	$('#fileInput').change(function(){
	    		var filepath = $('#fileInput').val();
	    		var filename = filepath.replace("C:\\fakepath\\","");
	    		$('#fileInput_span').text(filename);
	    		//console.log(filepath);///////////
	    		//console.log(filename);///////////
	    	});
            
	    });//$(document).ready END
	    
	    function chooseFile(){
	    	$('#fileInput').click();
	    	return false;
	    }
	    function resetUpload(){
	    	$('#fileInput_span').text("선택된 파일 없음");
	    }
	    
    </script>
</head>
<body>
	
	<div id="noticeWrite">
	    
	    <span>공지사항 작성</span>
	    <form action="/noticeWrite.do" method="post" enctype="multipart/form-data">
	    
		    <table id="notice-tb">
		    
		    	<tr id="noticeGrade_tr">
		            <th id="noticeGrade">등급</th>
	                <td>
		                <select name="noticeGrade" id="noticeGradeSelect">
		                    <option value="none">선택</option>
		                    
		                    <option value="EMC">긴급</option>
		                    <option value="NM">일반</option>
		                    <option value="IMP">중요</option>
		                    
		                </select>
	                </td>
		        </tr>
		        <tr id="noticeTitle_tr">
		            <th id="noticeTitle">제목</th>
	                <td><input type="text" id="titleInput"></td>
		        </tr>
	            <tr id="noticeContent_tr">
		            <th id="noticeContent">내용</th>
	                <td><textarea></textarea></td>
		        </tr>
		        <tr id="fileUpload_tr">
		            <th id="noticeFile">파일첨부</th>
	                <td id="fileUpload_td">
	                	
							<button id="fileInput_btn" onclick="return chooseFile();">파일선택</button>
							<div id="fileInput_div">
								<span id="fileInput_span">선택된 파일 없음</span>
								<input type="file" name="upfile" id="fileInput"/>
							</div>
							<input type="submit" value="파일첨부" class="upload_btn"/>
							<input type="reset" value="취소" class="upload_btn" onclick="resetUpload();"/>
						
	                </td>
		        </tr>
	            <tr id="register_tr">
	                <th></th>
	                <td><button id="register_btn">등록</button></td>
	            </tr>
		    </table>
	    
	    </form>
	</div>
	
</body>
</html>