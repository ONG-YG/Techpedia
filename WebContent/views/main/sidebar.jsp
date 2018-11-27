<%@page import="kr.co.techpedia.member.model.vo.MemberSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%
		session = request.getSession(false);
		//System.out.println("\nmainpage session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nmainContent memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				
				//System.out.println("\nmainContent memSession check 2\n"+memSession);////////////////////////
				
				String pg = request.getParameter("board");
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				//alert("memberTypeCD : "+memberTypeCD);/////////////////////////
				var pg = '<%=pg%>';
			</script>
	<%
			}else {
	%>
			<script>
				location.href="/index.jsp";
				alert("로그인해주세요.");
			</script>
	<%
			}
		}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/css/sidebar.css" rel="stylesheet" type="text/css">
	
	<script>
		$(document).ready(function(){
			
			checkMemType();
			
		    $('.menu span').click(function(){
		    	$(this).parent().siblings().children('ul').removeClass('showMenu');
		        $(this).parent().children('ul').toggleClass('showMenu');
		        console.log( $(this).parent().attr('id') );//////////////
		    });
		    
		    $('#memberInfoMgr').click(function() {
		    	//$('#mainView').load("/views/mypage/myInfoMgr.jsp");
		    	location.href="/views/mypage/myInfoMgr.jsp";
		    });
		    $('#progTechSppt').click(function() {
		    	//$('#mainView').load("/views/mypage/myTechSppt.jsp");
		    	location.href="/views/mypage/myTechSppt.jsp";
		    });
		    $('#myTechShr').click(function() {
		    	//$('#mainView').load("/views/mypage/myTechShare.jsp");
		    	location.href="/views/mypage/myTechShare.jsp";
		    });
		    
		    
		    $('#notice-li').click(function() {
		    	//location.href="/views/main/mainpage.jsp?board=Notice";
		    	location.href="/views/board/noticeList.jsp";
		    });
		    $('#tech-support-li').click(function() {
		    	//location.href="/views/main/mainpage.jsp?board=TechSpp";
		    	location.href="/views/board/techSupportPostList.jsp";
		    });
		    $('#tech-share-li').click(function() {
		    	//location.href="/views/main/mainpage.jsp?board=TechSh";
		    	location.href="/views/board/techSharePostList.jsp";
		    	
		    });
		    
		    
		});
		
		function checkMemType() {
			if(memberTypeCD=='HP_AD') {
				//모든 메뉴 사용 가능
				//관리자페이지 활성화
				//관리자페이지 중 전체회원관리 메뉴 활성화
				$('#totalMemberMgr').css('display','block');
				$('#admin-page-li').css('display','block');
			}
			else if(memberTypeCD=='MNFE_AD') {
				//관리자페이지 활성화
				//관리자페이지 중 전체회원관리 메뉴는 활성화하지 않음
				$('#admin-page-li').css('display','block');
			}
			else {
				//관리자페이지 활성화하지 않음
			}
		}
		
	</script>
</head>
<body>

	<div id="sidebar">
		
		<ul class="menu-ul">
            <li class="menu" id="mypage-li">
                <span>마이페이지</span>
                <ul class="submenu-ul">
                    <li id="memberInfoMgr"><span>회원정보 조회/수정/탈퇴</span></li>
                    <li id="progTechSppt"><span>진행 중 기술지원</span></li>
                    <li id="myTechShr"><span>작성한 기술 공유 게시물</span></li>
                </ul>
            </li>
            <li class="menu" id="notice-li">
                <span>공지사항</span>
            </li>
            <li class="menu" id="tech-support-li">
                <span>기술 지원 게시판</span>
            </li>
            <li class="menu" id="tech-share-li">
                <span>기술 공유 게시판</span>
            </li>
            <li class="menu" id="admin-page-li">
                <span>관리자 페이지</span>
                <ul class="submenu-ul">
                    <li id="joinApprove"><span>회원가입 신청자 관리</span></li>
                    <li id="techSpptEngAssign"><span>기술 지원 엔지니어 할당</span></li>
                    <li id="mainNoticeSet"><span>메인페이지 공지 설정</span></li>
                    <li id="totalMemberMgr"><span>전체 회원 관리</span></li>
                </ul>
            </li>
		</ul>
		
	</div>

</body>
</html>