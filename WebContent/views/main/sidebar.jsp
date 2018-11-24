<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/css/sidebar.css" rel="stylesheet" type="text/css">
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
        <script>
            $('.menu').click(function(){
                $(this).siblings().children('ul').removeClass('showMenu');
                $(this).children('ul').toggleClass('showMenu');
            });
        </script>
		
	</div>

</body>
</html>