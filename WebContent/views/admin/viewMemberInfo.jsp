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
		MemberSession memSession = (MemberSession)session.getAttribute("memSession");
		String memberId = "";
		int memberNo = 0;
		//System.out.println("myInfoMgr session check\n"+memSession);/////////////////
		if(memSession==null) {
	%>
		<script>
			accessDenied();
		</script>
	<%
		}else {
			memberId = memSession.getMemberId();
			memberNo = Integer.parseInt( request.getParameter("mem") );
		}
	%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/viewMemberInfo.css" rel="stylesheet" type="text/css">
    
	<script
      type="text/javascript"
        src="/resources/jquery-3.3.1.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	</script>
	 
    <script>
    	var memberTypeList = [];
    	var compList = [];
    	
	    $(document).ready(function(){
	    	
	    	getMemberInfo();
	    	
	    });//$(document).ready END
	    
	    
	    
	    function getMemberInfo() {
	    	var memberNo = <%=memberNo%>;
	    	$.ajax({
				url : "/getMemberInfoByMemNo.do",
				data : {memberNo: memberNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);//////////////////////////
					if(data==false) {
						alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href = "/views/main/mainpage.jsp";
					}
					else {
						var memberId = data.memberId;
						var memberName = data.memberName;
						var memberPrivatePhone = data.memberPrivatePhone;
						var memberCompanyPhone = data.memberCompanyPhone;
						var memberEmail = data.memberEmail;
						var memberTypeName = data.memberTypeName;
						var compName = data.compName;
						var companyMemNo = data.companyMemNo;
						
						$('#memberIdInput').val( memberId );
						$('#memberNameInput').val( memberName );
			            $('#member_P_PhoneInput').val( memberPrivatePhone );
			            $('#member_C_PhoneInput').val( memberCompanyPhone );
			            $('#memberEmailInput').val( memberEmail );
			            $('#memberTypeOption').html( memberTypeName );
			            $('#compNameOption').html( compName );
			            $('#compMemNoInput').val( companyMemNo );
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
    
    <div id="wrapper">
        
        <div id="myInfoMgr-div">
                <div id="member-info-box">
                	<div>
                        <table>
                            <tr>
                                <th>ID</th>
                                <td>
                                	<input type="text" id="memberIdInput" readonly/>
                                </td>
                            </tr>
                            <!-- 
                            <tr>
                                <th>비밀번호</th>
                                <td>
                                    <input type="password" id="memberPwInput"/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="pwCheck">8~16자 영문 대 소문자, 숫자, 특수문자를  사용하여 작성해주세요.</td>
                            </tr>
                            <tr>
                                <th>비밀번호 재확인</th>
                                <td>
                                    <input type="password" id="memberPwReInput"/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="pwReCheck">비밀번호가 일치하지 않습니다.</td>
                            </tr>
                             -->
                            <tr>
                                <th>이름</th>
                                <td>
                                    <input type="text" id="memberNameInput" readonly/>
                                </td>
                            </tr>
                            <tr>
                                <th>연락처(개인)(선택)</th>
                                <td>
                                    <input type="text" id="member_P_PhoneInput" readonly/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="pPhoneCheck">숫자로만 입력해주세요.</td>
                            </tr>
                            <tr>
                                <th>연락처(회사)</th>
                                <td>
                                    <input type="text" id="member_C_PhoneInput" readonly/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="cPhoneCheck">필수입력 사항입니다.</td>
                            </tr>
                            <tr>
                                <th>이메일</th>
                                <td>
                                    <input type="text" id="memberEmailInput" readonly/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="emailCheck">필수입력 사항입니다.</td>
                            </tr>
                            <tr>
                                <th>회원구분코드</th>
                                <td>
                                    <select id="memberTypeCDSelect">
                                        <option value="none" id="memberTypeOption" selected></option>
                                        <!-- 
                                        <option value="HP_AD">홈페이지 관리자</option>
                                        <option value="MNFE_AD">제조사 엔지니어 관리자</option>
                                        <option value="MNFE">제조사 엔지니어</option>
                                        <option value="COP">협력사 직원</option>
                                         -->
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>소속기업</th>
                                <td>
                                    <select id="compNoSelect">
                                        <option value="none" id="compNameOption" selected></option>
                                        <!-- 
                                        <option value="100">BEST TECH</option>
                                        <option value="101">WE ARE THE ONE</option>
                                         -->
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <th>사번</th>
                                <td>
                                    <input type="text" id="compMemNoInput" readonly/>
                                </td>
                            </tr>
                        </table>
                        <div id="closeWindow">
                            <button id="closeWindow_btn" onclick="window.close();">닫기</button>
                        </div>
                    </div>
                </div>
        </div>
        
    </div>
    
    
</body>
</html>