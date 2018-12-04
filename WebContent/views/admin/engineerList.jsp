<%@page import="kr.co.techpedia.member.model.vo.MemberSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%
		MemberSession memSession = (MemberSession)session.getAttribute("memSession");
		//System.out.println("engineerList page session check\n"+memSession);/////////////////
		if(memSession==null) {
	%>
		<script>
			location.href="/index.jsp";
			alert("로그인해주세요.");
		</script>
	<%
		}else {
			String memberTypeCD = memSession.getMemberTypeCD();
			if(memberTypeCD.equals("HP_AD")
					|| memberTypeCD.equals("MNFE_AD")) {
				String postNo = request.getParameter("postNo");
				if(postNo==null) {
					response.sendRedirect("/views/admin/engineerListError.jsp");
				}else {
					%>
					<script>
					var postNo = <%=postNo%>;
					</script>
					<%
				}
			}else {
				%>
				<script>
				alert("관리자만 사용가능한 기능입니다.");
				location.href="/index.jsp";
				</script>
				<%
			}
		}
	%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Techpedia</title>
    <link href="/css/engineerList.css" rel="stylesheet" type="text/css">
    
    <script
      type="text/javascript"
        src="/resources/jquery-3.3.1.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	</script>
	
    <script>
    	var memberTypeList = [];
    	
        var checkTechSppEngineer = false;
        
	    $(document).ready(function(){
	    	
	    	getEngineerList(postNo);
	    	check();
	    	
            $(document).change(function(){
	    		//console.log("mouseup");
	    		check();
	    	});
	    	
	    });//$(document).ready END
	    
	    
	    function getEngineerList() {
	    	$.ajax({
				url : "/getEngineerList.do",
				//data : {},
				//type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log("memberTypeList data");
					//console.log(data);
					if(data==false) {
						alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp"
						window.close();
					}
					else {
						engineerList = [];
						for(var i=0; i<data.length; i++) {
							var engineer = [data[i].memberNo,
											data[i].memberName];
							engineerList.push(engineer);
						}
						
						var engListOption = "<option value='none'>선택</option>";
						for(var i=0; i<engineerList.length; i++) {
							engListOption += "<option value='"+engineerList[i][0]+"'>"
													+"회원번호["+engineerList[i][0]+"] "
													+engineerList[i][1]+"</option>";
						}
						$('#techSppEngineerSelect').html(engListOption);
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
					window.close();
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	    
	    
	    function check() {
            //alert("check start");
            var techSppEngineerSelect = document.getElementById("techSppEngineerSelect").value;
            
            //회원구분코드 선택 확인
            //var checkTechSppEngineer = false;
            if(techSppEngineerSelect!="none") {
                checkTechSppEngineer = true;
                $('#techSppEngineerCheck').attr('style','display: none');
            }else {
                checkTechSppEngineer = false;
            }
            //console.log("checkTechSppEngineer "+checkTechSppEngineer);
            //console.log("----");
            
            
			if(checkTechSppEngineer) {
				$('#assignSubmit').css('background-color','rgb(0,0,65)');
                $('#assignSubmit').css('color','white');
                $('#assignSubmit').attr('onclick','return assignEngineer('+postNo+');');
			}else {
				$('#assignSubmit').css('background-color','gainsboro');
                $('#assignSubmit').css('color','dimgray');
                $('#assignSubmit').attr('onclick','return false;');
			}
			
		}//function END
        
        function assignEngineer(postNo) {
            var techSppEngineerNO = $('#techSppEngineerSelect').val();
             
            $.ajax({
				url : "/assignTechSppEng.do",
				data : {engNo: techSppEngineerNO, postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//console.log(data);///////////
					//alert("success");
					if(data) {
						alert("성공적으로 엔지니어 배정이 완료되었습니다.")
						location.href="/index.jsp";
						window.close();
						window.opener.techSpptBoardList();
					}
					else {
						alert("엔지니어 배정에 실패하였습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
						window.close();
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("처리 도중 오류가 발생했습니다. 다시 시도해주세요.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
					window.close();
				},
				complete : function(){
					//alert("complete");
				}
			});
            
        }//function END
        
    </script>
    
</head>
<body>
    
	<div id="content">
		<div id="engineerSelect_div">
			<div>
		        <table>
					<tr>
					    <th>회원구분코드</th>
					    <td>
					        <select name="techSppEngineer" id="techSppEngineerSelect">
					            <option value="none">선택</option>
				            </select>
				        </td>
				    </tr>
				</table>
				<div id="assign">
			    	<button id="assignSubmit" onclick="return false;">배정</button>
		        </div>
	        </div>
		</div>
	</div>
    
    
</body>
</html>