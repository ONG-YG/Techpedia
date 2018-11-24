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
		//System.out.println("\nmainpage session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			System.out.println("\nmainpage(header) memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				
				System.out.println("\nmainpage(header) memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				//alert("memberTypeCD : "+memberTypeCD);/////////////////////////
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
	<link href="/css/header.css" rel="stylesheet" type="text/css">

	<script>   
		$(document).ready(function(){
			//
		});
		
		function goMain(){
		    //location.href="/views/main/mainpage.html";
		    location.href="/views/main/mainpage.jsp";
		}
		
		function logout(){
		    location.href="/logout.do";
		}
	</script>
</head>
<body>
	<div id="header">
		<span id="logout" onclick="logout();">로그아웃</span>
		<div id="mainTitle">
			<strong onclick="goMain();">Techpedia</strong>
		</div>
	</div>
</body>
</html>