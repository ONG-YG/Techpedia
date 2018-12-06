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
				int compNo = memSession.getCompNo();
				String currBoard = memSession.getCurrBoard();
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var compNo = <%=compNo%>;
				var currBoard = '<%=currBoard%>';
				
				if(!writeStart || memberTypeCD!='COP' || currBoard!='TechSpp') {
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
	<link href="/css/techSupportPostWrite.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	getTechSppPost();
	    	
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
	    }//function END
	    
	    function resetUpload(){
	    	$('#fileInput_span').text("선택된 파일 없음");
	    	$('#fileInput').val('');
	    	
	    	return false;
	    }//function END

	    function getTechSppPost() {
	    	$.ajax({
				url : "/techSupportPostRead.do",
				data : {postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/views/board/readFail.jsp";
					}
					else {
						var writerCompNo = data.spptWriterCompNo;
						
						if(writerCompNo!=compNo && memberTypeCD=='COP') {
							location.href="/views/board/abnormalAccess.jsp";
						}
						else {
							$('#titleInput').val(data.spptTitle);
							$('#content_txt').val(data.spptContent);
							$('#fileInput_span').text(data.attName);//첨부파일
							
						}
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/views/board/readFail.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
    </script>
</head>
<body>
	
	<div id="techSppWrite">
	    
	    <span>기술 지원 요청글 수정</span>
	    <form action="/techSpportPostWrite.do" method="post" enctype="multipart/form-data">
	    
		    <table id="techSpp-tb">
		        <tr id="techSppTitle_tr">
		            <th id="techSppTitle">제목</th>
	                <td><input type="text" name="title" id="titleInput"></td>
		        </tr>
	            <tr id="techSppContent_tr">
		            <th id="techSppContent">내용</th>
	                <td><textarea name="content" id="content_txt"></textarea></td>
		        </tr>
		        <tr id="fileUpload_tr">
		            <th id="techSppFile">파일첨부</th>
	                <td id="fileUpload_td">
							<button id="fileInput_btn" onclick="return chooseFile();">파일선택</button>
							<div id="fileInput_div">
								<span id="fileInput_span">선택된 파일 없음</span>
								<input type="file" name="upfile" id="fileInput"/>
							</div>
							<!-- <input type="submit" value="파일첨부" class="upload_btn"/> -->
							<!-- <input type="reset" value="취소" class="upload_btn" onclick="resetUpload();"/> -->
							<button class="upload_btn" onclick="return resetUpload();">취소</button>
	                </td>
		        </tr>
		        <tr id="fileCondition_tr">
		        	<th></th>
		        	<td>첨부파일의 최대 크기는 10MB이며, [txt, doc, hwp, jpg, png, zip] 형식만 업로드 가능합니다.</td>
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
