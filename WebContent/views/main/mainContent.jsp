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
				
				System.out.println("\nmainContent memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				//alert("memberTypeCD : "+memberTypeCD);/////////////////////////
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
	<link href="/css/mainContent.css" rel="stylesheet" type="text/css">
	
	<script>
		$(document).ready(function(){
			
			checkMemType();
			
			$('#mainView').load("/views/main/main_notice.jsp");
			
		    $('.menu span').click(function(){
		    	$(this).parent().siblings().children('ul').removeClass('showMenu');
		        $(this).parent().children('ul').toggleClass('showMenu');
		    });
		    
		    $('#memberInfoMgr').click(function() {
		    	$('#mainView').load("/views/mypage/myInfoMgr.jsp");
		    });
		    
		});
		
		function checkMemType() {
			if(memberTypeCD=='HP_AD') {
				//모든 메뉴 사용 가능
			}
			else if(memberTypeCD=='MNFE_AD') {
				//관리자페이지 중 전체회원관리 메뉴 비활성화
				$('#totalMemberMgr').css('display','none');
				$('#totalMemberMgr').html('');
			}
			else {
				//관리자페이지 비활성화
				$('#admin-page-li').css('display','none');
				$('#admin-page-li').html('');
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
	
	<div id="mainView">
		<!-- 메인 공지사항 띄워주는 공간 -->
		
<%-- 		<jsp:include page="/views/main/main_notice.jsp"></jsp:include> --%>
			
	</div>

</body>
</html>