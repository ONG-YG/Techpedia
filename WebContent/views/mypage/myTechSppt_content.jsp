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
		//System.out.println("\nmyTechSppt session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nmyTechSppt memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				String cp = request.getParameter("currPg");

				int currPg = 1;
				if (cp!=null) {
					currPg = Integer.parseInt(cp);
				}
				
				//System.out.println("\nmyTechSppt memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var currPg = <%=currPg%>;
				
				if(memberTypeCD=='COP') {
					$('#charger').html("담당 엔지니어");
				}
				else {
					$('#charger').html("협력사 담당자");
				}
				
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
	<link href="/css/myTechSppt.css?ver=1" rel="stylesheet" type="text/css">
	
	<script>
	$(document).ready(function(){
    	
		getTechSpptList();
    	
    });//$(document).ready END
    
    function move(pageNo){
    	currPg = pageNo;
    	getTechSpptList();
    }
    
    function getTechSpptList(){
		
       	$.ajax({
				url : "/getTechSpptList.do",
				data : {memberNo: memberNo, memberTypeCD: memberTypeCD, currPg: currPg},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);////////////////////
					
					if(data) {
						techSpPostList = [];
					for(var i=0; i<data.techSupportPostL.length; i++) {
						var charger = null;
						if(memberTypeCD=='COP') {
							charger = data.techSupportPostL[i].spptEngName;
						}else {
							charger = data.techSupportPostL[i].spptWriterName;
						}
						var post = [data.techSupportPostL[i].postNo,
									data.techSupportPostL[i].spptTitle,
									charger,
									data.techSupportPostL[i].spptDate,
									data.techSupportPostL[i].spptStatName,
									data.techSupportPostL[i].spptEngck];
						techSpPostList.push(post);
					}
					
					//var postL = $('#techSppt-tb').html();
					var postL = '';
					for(var i=0; i<techSpPostList.length; i++) {
						postL += " <tr> "
									+"<td>"+techSpPostList[i][0]+"</td> "
									+"<td>"+techSpPostList[i][1]+"</td> "
									+"<td>"+techSpPostList[i][2]+"</td> "
									+"<td>"+techSpPostList[i][3]+"</td> "
									+"<td>"+techSpPostList[i][4]+"</td> "
									+"<td>"+techSpPostList[i][5]+"</td> "
								+"</tr> ";
								
					}
					$('#techSppt-tb tbody').html(postL);
					$('#navi').html(data.pageNavi);
					}
					else {
						alert("담당 중인 기술 지원 게시물이 존재하지 않습니다.");
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
         
    </script>
</head>
<body>
	
	<div id="myTechSppt">
	    
	    <span>나의 기술 지원 현황</span>
	    
	    <div id="tableDiv">
		    <table id="techSppt-tb">
		    	<thead>
			        <tr>
		                <th>번호</th>
		                <th>제목</th>
		                <th id="charger">담당자</th>
		                <th>작성일</th>
		                <th>진행현황</th>
		                <th>엔지니어 확인</th>
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