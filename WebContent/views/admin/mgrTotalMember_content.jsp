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
		//System.out.println("\nNoticeBoard session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nNoticeBoard memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				String cp = request.getParameter("currPg");
				//System.out.println("cp : "+cp);//////////////
				
				int currPg = 1;
				if (cp!=null) {
					currPg = Integer.parseInt(cp);
				}
				session.setAttribute("memSession", memSession);
				//System.out.println("\nNoticeBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var currPg = <%=currPg%>;
				if(memberTypeCD!='HP_AD') {
					alert("홈페이지 관리자만 사용가능한 기능입니다.");
					location.href="/index.jsp";
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
	<link href="/css/mgrTotalMember_content.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	history.pushState({},"", "/views/main/mainpage.jsp");
	    	
	    	if(memberTypeCD=='HP_AD') {
	    		totalMemberList();
	    	}
	    	
	    });//$(document).ready END
        
	    function move(pageNo){
	    	currPg = pageNo;
	    	totalMemberList();
	    }
	    
        function totalMemberList(){
           	$.ajax({
   				url : "/totalMemberList.do",
   				data : {currPg: currPg},
   				type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					
   					if(data) {
   						//memberList = [];
   						var postL = '';
						for(var i=0; i<data.memberList.length; i++) {
							var detail_btn = "<button class='detail_btn' "
											+"onclick='viewMemberInfo("+data.memberList[i].memberNo+");'>보기</button>";
							var delete_btn = "<button class='delete_btn' "
												+"onclick='setDeletedMember("+data.memberList[i].memberNo+");'>탈퇴</button>";
							if(data.memberList[i].memberActive=='N'
							&& data.memberList[i].memberDelNo!=null) {
								delete_btn = "<button class='alreadyDeleted_btn' "
												+"onclick='alreadyDeleted();'>탈퇴</button>";
							}
							postL += " <tr> "
										+"<td>"+data.memberList[i].memberTypeName+"</td> "
										+"<td>"+data.memberList[i].compName+"</td> "
										+"<td>"+data.memberList[i].companyMemNo+"</td> "
										+"<td>"+data.memberList[i].memberName+"</td> "
										+"<td>"+data.memberList[i].enrollDate+"</td> "
										+"<td>"+detail_btn+"</td> "
										+"<td>"+delete_btn+"</td> "
									+"</tr> ";
						}
						$('#totalMember-tb tbody').html(postL);
						$('#navi').html(data.pageNavi);
   					}
   					else {
						var emptyPageNavi ="<span><img src='' id='prev_img' width='20px'></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span></span>"
							                +"<span><img src='' id='next_img' width='20px'></span>";
						$('#totalMember-tb tbody').html('');
						$('#navi').html(emptyPageNavi);
						//location.href = "/views/main/mainpage.jsp";
   					} 
   				},
   				error : function(){
   					//console.log("ajax 통신 에러");
   					alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href = "/views/main/mainpage.jsp";
   				},
   				complete : function(){
   					//alert("complete");
   				}
   			});
        }//function END
        
        function viewMemberInfo(memberNo){
        	
        	window.open("/views/admin/viewMemberInfo.jsp?mem="+memberNo,
        				"_blank", "width=600,height=600");
        	
        }//function END
        
        function alreadyDeleted(){
        	alert("이미 탈퇴한 상태의 회원입니다.");
        }
        
        function setDeletedMember(memberNo){
        	
        	var chk = confirm("정말 탈퇴 처리하시겠습니까?");
        	
        	if(chk) {
        		
        		$.ajax({
       				url : "/setDeletedMember.do",
       				data : {memberNo: memberNo},
       				type : "post",
       				success : function(data){
       					//console.log("정상 처리 완료");
       					//alert("success");
       					//console.log(data);////////////////////
       					if(data) {
       						alert("성공적으로 탈퇴 처리를 완료했습니다.");
       						totalMemberList();
       					}
       					else {
       						alert("처리 도중 오류가 발생했습니다.");
       					} 
       				},
       				error : function(){
       					//console.log("ajax 통신 에러");
       					alert("처리 도중 오류가 발생했습니다.");
       				},
       				complete : function(){
       					//alert("complete");
       				}
       			});
            	
        	}
           	
        }//function END
    </script>
</head>
<body>
	
	<div id="totalMember">
	    
	    <span>전체 회원 목록</span>
	    
	    <div id="tableDiv">
		    <table id="totalMember-tb">
		    	<thead>
			        <tr>
		                <th>회원구분</th>
		                <th>회사이름</th>
		                <th>사번</th>
		                <th>신청자이름</th>
		                <th>가입신청일</th>
		                <th>상세정보</th>
		                <th>탈퇴</th>
			        </tr>
	            </thead>
	            
	            <tbody>
	            </tbody>
	            
		    </table>
	    </div>
	    <div id="bottomSpace">
            <div id="navi">
                <span><img src='' id='prev_img' width='20px'></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                <span><img src='' id='next_img' width='20px'></span>
            </div>
        </div>
	</div>
	
</body>
</html>