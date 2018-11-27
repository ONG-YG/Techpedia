<%@page import="kr.co.techpedia.member.model.vo.MemberSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Techpedia</title>
    <link href="/css/main_common.css" rel="stylesheet" type="text/css">
    
    <script
      type="text/javascript"
        src="/resources/jquery-3.3.1.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	</script>
    
</head>
<body>
    
    <div id="wrapper">
        
        <jsp:include page="/views/main/header.jsp"></jsp:include>
        
        
        <div id="content">

<%--             <jsp:include page="/views/main/mainContent.jsp"></jsp:include> --%>

	        <jsp:include page="/views/main/sidebar.jsp"></jsp:include>
	        
	        
			<div id="mainView">
				
				<jsp:include page="/views/mypage/myTechShare_content.jsp"></jsp:include>
				
			</div>
            
            
        </div>
        
        <jsp:include page="/views/main/footer.jsp"></jsp:include>
        
    </div>
    
    
</body>
</html>