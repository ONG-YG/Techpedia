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
    <%
	String writeStart = request.getParameter("writeStart");
	System.out.println("?? "+writeStart);//////////
	
	if(writeStart==null) {
		//writeStart = "false";
		writeStart = "true";/////////////////////////
	}
	
	String auto = request.getParameter("auto");
	String newTechspp = request.getParameter("newTechspp");
	session = request.getSession(false);
	if(auto!=null && session!=null) {
		MemberSession memSession = (MemberSession)session.getAttribute("memSession");
		if(memSession!=null){
			String memberTypeCD = memSession.getMemberTypeCD();
			if(!auto.equals("0") 
					&& ( memberTypeCD.equals("HP_AD")
							|| memberTypeCD.equals("MNFE_AD"))) {
				%>
				<script>
					alert("담당 엔지니어가 자동 배정된 기술지원 게시물이 존재합니다.");
					location.href = "/views/main/mainpage.jsp";
				</script>
				<%
			}
			if(!newTechspp.equals("0") 
					&& ( memberTypeCD.equals("MNFE_AD")
							|| memberTypeCD.equals("MNFE"))) {
				%>
				<script>
					alert("확인하지 않은 기술지원 게시물이 존재합니다.");
					location.href = "/views/main/mainpage.jsp";
				</script>
				<%
			}
		}
	}
	
    %>
    <script>
    	writeStart = <%=writeStart%>;
    </script>
</head>
<body>
    
    <div id="wrapper">
        
        <jsp:include page="/views/main/header.jsp"></jsp:include>
        
        
        <div id="content">

             <jsp:include page="/views/main/mainContent.jsp"></jsp:include>
            
            
        </div>
        
        <jsp:include page="/views/main/footer.jsp"></jsp:include>
        
    </div>
    
    
</body>
</html>