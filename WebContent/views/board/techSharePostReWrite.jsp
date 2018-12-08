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
				String currBoard = memSession.getCurrBoard();
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var currBoard = '<%=currBoard%>';
				var postNo = -1;
				
				if(!writeStart || currBoard!='TechSh') {
					location.href="/views/board/writeError.jsp";
				}
			</script>
	<%
				if(!request.getParameter("postNo").equals("null")){
					int postNo = Integer.parseInt(request.getParameter("postNo"));
					%>
					<script>
						postNo = <%=postNo%>;
						$('#postNo_hidden').val(postNo);
					</script>
					<%
				}else {
					%>
					<script>
						location.href="/views/board/readError.jsp";
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
		}else {
	%>
		<script>
			accessDenied();
		</script>
	<%
		}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/css/techSharePostWrite.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	getTechShPost();
	    	
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
		
	    function registerPost(){
	    	var tit = $('#titleInput').val();
	    	var con = $('#content_txt').val();
	    	
	    	var fileNameSplit = $('#fileInput_span').text().split('.');
	    	var fileType = fileNameSplit[fileNameSplit.length-1];
    		var res = fileType.match(/(txt|doc|hwp|jpg|png|zip)$/i);
    		
	    	if(res || $('#fileInput').val()=='') {
	    		if(tit=='' || con=='') {
		    		alert("제목과 내용을 모두 입력해주세요.");
		    	}
		    	else {
		    		$('#writeForm').submit();
		    	}
	    	}
	    	else {
	    		alert("업로드 가능한 파일 형식이 아닙니다.");
	    	}
	    	
	    	return false;
	    }//function END
	    
	    function getTechShPost() {
	    	$.ajax({
				url : "/techSharePostRead.do",
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
						$('#titleInput').val(data.shrTitle);
						$('#content_txt').val(data.shrContent);
						$('#fileInput_span').text(data.attName);//기존 첨부파일
						$('#originFile_hidden').val(data.attName);//기존 첨부파일(servlet에 보낼 값)
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
	
	<div id="techShWrite">
	    
	    <span>기술 공유 게시물 수정</span>
	    <form action="/techSharePostReWrite.do" method="post" enctype="multipart/form-data" id="writeForm">
	    
		    <table id="techSh-tb">
		        <tr id="techShTitle_tr">
		            <th id="techShTitle">제목</th>
	                <td><input type="text" name="title" id="titleInput"></td>
		        </tr>
	            <tr id="techShContent_tr">
		            <th id="techShContent">내용</th>
	                <td><textarea name="content" id="content_txt"></textarea></td>
		        </tr>
		        <tr id="fileUpload_tr">
		            <th id="techShFile">파일첨부</th>
	                <td id="fileUpload_td">
							<button id="fileInput_btn" onclick="return chooseFile();">파일선택</button>
							<div id="fileInput_div">
								<span id="fileInput_span">선택된 파일 없음</span>
								<input type="file" name="upfile" id="fileInput" accept=".txt, .doc, .hwp, .jpg, .png, .zip" />
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
	                <td><button id="register_btn" onclick="return registerPost();">수정</button></td>
	            </tr>
		    </table>
	    	<input type="hidden" name="postNo" id="postNo_hidden" value="" />
	    	<input type="hidden" name="originFile" id="originFile_hidden" value="" />
	    </form>
	</div>
	
</body>
</html>
