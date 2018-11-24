<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/css/mainNotice.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	getMainNotice();
	    	
	    });//$(document).ready END
        
        
        function getMainNotice(){
        	var noticeTitle = '';
			var writer = '';
			var write_date = '';
			var noticeContent = '<br>메인에 게시된 공지가 없습니다.';
			
           	$.ajax({
   				url : "/getMainNotice.do",
   				//data : {},
   				//type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					console.log(data);
   					if(data) {
   						noticeTitle = data.ntcTitle;
   						writer = data.ntcWriterName;
   						write_date = data.ntcDate;
   						noticeContent = data.ntcContent;
   						$('#writer-span').html("작성자");
   						$('#write_date-span').html("작성일");
   					}
   					else {
   						$('#writer-span').html('');
   						$('#write_date-span').html('');
   					}
   					$('#noticeTitle').html( noticeTitle );
			    	$('#writer').html( writer );
			    	$('#write_date').html( write_date);
			    	$('#noticeContent').html( noticeContent );
   				},
   				error : function(){
   					//console.log("ajax 통신 에러");
   					alert("ERROR");
   				},
   				complete : function(){
   					//alert("complete");
   				}
   			});
        }//function login() END
         
    </script>
</head>
<body>
	
	<div id="mainNotice">
	    
	    <span>메인 공지사항</span>
	    
	    <table id="notice-tb" border="1">
	        <tr>
	            <th id="noticeTitle">
	            	<!-- 공지사항 제목 -->
	            </th>
	        </tr>
	        <tr>
	            <td><span id="writer-span">작성자</span>
	                <span id="writer"></span>
	            </td>
	        </tr>
	        <tr>
	            <td><span id="write_date-span">작성일</span>
	                <span id="write_date"></span>
	            </td>
	        </tr>
	        <tr>
	            <td id="noticeContent">
	            <!-- 
					공지 내용 공지 내용 공지 내용 공지 내용 공지 내용 공지 내용<br>
					ajax로 불러온 공지내용이 여기에 보여질 것입니다.<br>
					나중에 지워지게 될 테스트용으로 작성된 공지사항 내용입니다. 가나다라마바사아자차카타파하<br>
					제조사 엔지니어 관리자와 홈페이지 관리자 모두 여기에 보여질 공지사항을 선택할 수 있습니다.<br>
					공지사항 게시물 중 오직 한 개만 이 곳에 게시될 수 있습니다.<br>
					작성일과 작성자를 표시하는 란 또한 만들어야 할 것입니다.<br>
					공지 내용은 이것으로 마치겠습니다.<br>
	                 -->
	            </td>
	        </tr>
	    </table>
	    
	</div>
	
</body>
</html>