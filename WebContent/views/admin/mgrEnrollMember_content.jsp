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
				if(memberTypeCD!='HP_AD' && memberTypeCD!='MNFE_AD') {
					alert("관리자만 사용가능한 기능입니다.");
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
	<link href="/css/mgrEnrollMember_content.css" rel="stylesheet" type="text/css">
	
	<script>
	    $(document).ready(function(){
	    	
	    	if(memberTypeCD=='HP_AD' || memberTypeCD=='MNFE_AD') {
	    		enrollMemberList();
	    	}
	    	
	    });//$(document).ready END
        
	    function move(pageNo){
	    	currPg = pageNo;
	    	enrollMemberList();
	    }
	    
        function enrollMemberList(){
           	$.ajax({
   				url : "/enrollMemberList.do",
   				data : {currPg: currPg},
   				type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					
   					if(data) {
   						memberList = [];
						for(var i=0; i<data.memberList.length; i++) {
							var post = [data.memberList[i].memberTypeName,
										data.memberList[i].compName,
										data.memberList[i].companyMemNo,
										data.memberList[i].memberName,
										data.memberList[i].enrollDate,
										data.memberList[i].memberNo];
							memberList.push(post);
						}
						
						//var postL = $('#enrollApprove-tb tbody').html();
						var postL = '';
						for(var i=0; i<memberList.length; i++) {
							var detail_btn = "<button class='detail_btn' "
											+"onclick='viewMemberInfo("+memberList[i][5]+");'>보기</button>";
							var approve_btn = "<button class='approve_btn' "
												+"onclick='setApprove("+memberList[i][5]+");'>설정</button>";
							postL += " <tr> "
										+"<td>"+memberList[i][0]+"</td> "
										+"<td>"+memberList[i][1]+"</td> "
										+"<td>"+memberList[i][2]+"</td> "
										+"<td>"+memberList[i][3]+"</td> "
										+"<td>"+memberList[i][4]+"</td> "
										+"<td>"+detail_btn+"</td> "
										+"<td>"+approve_btn+"</td> "
									+"</tr> ";
						}
						$('#enrollApprove-tb tbody').html(postL);
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
						$('#enrollApprove-tb tbody').html('');
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
        
        function setApprove(memberNo){
        	
           	$.ajax({
   				url : "/approveMemberJoin.do",
   				data : {memberNo: memberNo},
   				type : "post",
   				success : function(data){
   					//console.log("정상 처리 완료");
   					//alert("success");
   					//console.log(data);////////////////////
   					if(data) {
   						alert("성공적으로 가입 승인 처리를 완료했습니다.");
   						enrollMemberList();
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
        	
        }//function END
    </script>
</head>
<body>
	
	<div id="enrollApprove">
	    
	    <span>회원가입 신청자 목록</span>
	    
	    <div id="tableDiv">
		    <table id="enrollApprove-tb">
		    	<thead>
			        <tr>
		                <th>회원구분</th>
		                <th>회사이름</th>
		                <th>사번</th>
		                <th>신청자이름</th>
		                <th>가입신청일</th>
		                <th>상세정보</th>
		                <th>승인/거절</th>
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