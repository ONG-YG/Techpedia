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
				
				if(!writeStart || memberTypeCD=='COP') {
					location.href="/views/board/writeError.jsp";
				}
			</script>
	<%
				if(request.getParameter("postNo")!=null){
					int postNo = Integer.parseInt(request.getParameter("postNo"));
					%>
					<script>
						postNo = <%=postNo%>;
					</script>
					<%
				}else {
					%>
					<script>
						location.href="/views/board/writeError.jsp";
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
	<link href="/css/noticeReWrite.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	if(memberTypeCD!='COP') {
	    		getNoticeGradeList();
	    		if(postNo!=-1) getNoticePost();
	    	}
	    	
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
							noticeGrdList_sel += "<option value='"+noticeGrdList[i][0]+"' "
											+"id='"+noticeGrdList[i][0]+"'>"
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

	    function getNoticePost() {
	    	$.ajax({
				url : "/noticeRead.do",
				data : {postNo: posNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/views/board/readFail.jsp";
					}
					else {
						if( memberNo!=data.ntcWriterNo ) {
							location.href="/views/board/writeError.jsp";
						}
						$('#noticeGradeSelect').children().attr('selected', false);
						$('#'+data.ntcGradeCD).attr('selected', true);
						$('#titleInput').val(data.ntcTitle);
						$('#noticeContent_txt').html(data.ntcContent);
						//$('#fileDownload_td').html();//첨부파일
						
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
	    
	    
	    function chooseFile(){
	    	$('#fileInput').click();
	    	
	    	return false;
	    }//function END
	    
	    function resetUpload(){
	    	$('#fileInput_span').text("선택된 파일 없음");
	    	$('#fileInput').val('');
	    	
	    	return false;
	    }//function END
	    
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
		                    <option value='none'>선택</option>
		                    <!-- 
		                    <option value='EMC'>긴급</option>
		                    <option value='NM'>일반</option>
		                    <option value='IMP'>중요</option>
		                     -->
		                </select>
	                </td>
		        </tr>
		        <tr id="noticeTitle_tr">
		            <th id="noticeTitle">제목</th>
	                <td><input type="text" name="title" id="titleInput"></td>
		        </tr>
	            <tr id="noticeContent_tr">
		            <th id="noticeContent">내용</th>
	                <td><textarea name="content" id="noticeContent_txt"></textarea></td>
		        </tr>
		        <tr id="fileUpload_tr">
		            <th id="noticeFile">파일첨부</th>
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