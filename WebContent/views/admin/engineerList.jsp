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
			String memberTypeCD = memSession.getMemberTypeCD();
	%>
		<script>
			location.href="/index.jsp";
			alert("로그인해주세요.");
		</script>
	<%
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
    	
        var checkMemberTypeCD = false;
        
	    $(document).ready(function(){
	    	
	    	getMemberTypeList();
	    	check();
	    	
            $('#enrollSubmit').click(function() {
                check();
                if(!checkMemberTypeCD) {
                    $('#memberTypeCDCheck').attr('style','display: table');
                }
            });
            
            $(document).change(function(){
	    		//console.log("mouseup");
	    		check();
	    	});
	    	
	    	$(document).keyup(function(){
	    		//console.log("keyup");
	    		check();
	    	});
            
            $('#memberTypeCDSelect').mouseup(function() {
                if(!checkMemberTypeCD) {
                    $('#memberTypeCDCheck').attr('style','display: table');
                }
                else {
                	//ajax로 compNoSelect받아오기//////////////////
                	getCompanyList();
                }
            });
            
	    });//$(document).ready END
	    
	    
	    function getMemberTypeList() {
	    	$.ajax({
				url : "/getMemberTypeList.do",
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
						location.href="/index.jsp";
					}
					else {
						memberTypeList = [];
						for(var i=0; i<data.length; i++) {
							var memberType = [data[i].memberTypeCD,
											data[i].memberTypeName,
											data[i].compCTG];
							memberTypeList.push(memberType);
						}
						
						var memTypeList = "<option value='none'>선택</option>";
						for(var i=0; i<memberTypeList.length; i++) {
							memTypeList += "<option value='"+memberTypeList[i][0]
											+"' class='"+memberTypeList[i][2]+"'>"
											+memberTypeList[i][1]+"</option>";
						}
						$('#memberTypeCDSelect').html(memTypeList);
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	    
	    function check() {
            //alert("check start");
            var memberTypeCDSelect = document.getElementById("memberTypeCDSelect").value;
            
            //회원구분코드 선택 확인
            //var checkMemberTypeCD = false;
            if(memberTypeCDSelect!="none") {
                checkMemberTypeCD = true;
                $('#memberTypeCDCheck').attr('style','display: none');
            }else {
                checkMemberTypeCD = false;
            }
            //console.log("checkMemberTypeCD "+checkMemberTypeCD);
            //console.log("----");
            
            
			if(checkMemberTypeCD) {
				$('#enrollSubmit').css('background-color','rgb(0,0,65)');
                $('#enrollSubmit').css('color','white');
                $('#enrollSubmit').attr('onclick','return enroll();');
			}else {
				$('#enrollSubmit').css('background-color','gainsboro');
                $('#enrollSubmit').css('color','dimgray');
                $('#enrollSubmit').attr('onclick','return false;');
			}
			
		}//function END
        
        function enroll() {
            var memberTypeCD = $('#memberTypeCDSelect').val();
            /* 
            $.ajax({
				url : "/enroll.do",
				data : {memberId: memberId,
	            		memberPw: memberPw,
	            		memberName: memberName,
	            		member_P_Phone: member_P_Phone,
	            		member_C_Phone: member_C_Phone,
	            		memberEmail: memberEmail,
	            		memberTypeCD: memberTypeCD,
	            		compNo: compNo,
	            		compMemNo: compMemNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//console.log(data);///////////
					//alert("success");
					if(data) {
						alert("성공적으로 가입 신청이 완료되었습니다.\n"
								+"관리자의 승인 후 가입이 완료됩니다.")
						location.href = "/index.jsp";
					}
					else {
						alert("가입 신청에 실패하였습니다.\n"
								+"입력하신 내용을 다시 한번확인해주세요.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("가입 도중 오류가 발생했습니다. 다시 시도해주세요.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
				},
				complete : function(){
					//alert("complete");
				}
			});
             */
        }//function END
        
    </script>
    
</head>
<body>
    
	<div id="content">
		<div id="login-box">
			<div>
		        <table>
					<tr>
					    <th>회원구분코드</th>
					    <td>
					        <select name="memberTypeCD" id="memberTypeCDSelect">
					            <option value="none">선택</option>
					            <!-- 
								<option value="HP_AD">홈페이지 관리자</option>
								<option value="MNFE_AD">제조사 엔지니어 관리자</option>
								<option value="MNFE">제조사 엔지니어</option>
								<option value="COP">협력사 직원</option>
								 -->
				            </select>
				        </td>
				    </tr>
				</table>
				<div id="enroll">
			    	<button id="enrollSubmit" onclick="return false;">가입</button>
		        </div>
	        </div>
		</div>
	</div>
    
    
</body>
</html>