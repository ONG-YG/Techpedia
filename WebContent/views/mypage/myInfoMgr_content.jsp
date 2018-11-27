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
		//System.out.println("myInfoMgr session check\n"+memSession);/////////////////
		if(memSession==null) {
	%>
		<script>
			accessDenied();
		</script>
	<%
		}else {
			memberId = memSession.getMemberId();
		}
	%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="/css/myInfoMgr.css?ver=1" rel="stylesheet" type="text/css">
    
	<!-- 
	<script
      type="text/javascript"
        src="/resources/jquery-3.3.1.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	</script>
	  -->
	 
    <script>
    	var memberTypeList = [];
    	var compList = [];
    	
        var checkPw = false;
        var checkRePw = false;
        var checkPhoneP = false;
        var checkPhoneC = false;
        var checkEmail = false;
        
	    $(document).ready(function(){
	    	
	    	getMemberInfo();
	    	//check();
	    	
            $('#updateSubmit').click(function() {
                check();
                if(!checkPw) {
                    $('#pwCheck').attr('style','display: table');
                }
                if(checkPw && !checkRePw) {
                    $('#pwReCheck').attr('style','display: table');
                }else {
                    $('#pwReCheck').attr('style','display: none');
                }
                if(checkPw && !checkRePw) {
                    $('#pwReCheck').attr('style','display: table');
                }
                if(!checkPhoneP) {
                    $('#pPhoneCheck').attr('style','display: table');
                }
                if(!checkPhoneC) {
                    $('#cPhoneCheck').attr('style','display: table');
                }
                if(!checkEmail) {
                    $('#emailCheck').attr('style','display: table');
                }
            });
	    	
	    	$('#signOut').click(function() {
                check();
                if(!checkRePw) {
                    alert("비밀번호 재입력란을 확인해주세요.");
                }
            });
            
            $('#myInfoMgr-div').change(function(){
	    		//console.log("mouseup");
	    		check();
	    	});
	    	
	    	$('#myInfoMgr-div').keyup(function(){
	    		//console.log("keyup");
	    		check();
	    	});
            
            $('#memberPwInput').blur(function() {
            	check();
                if(!checkPw) {
                    $('#pwCheck').attr('style','display: table');
                }
                if(checkPw && !checkRePw) {
                    $('#pwReCheck').attr('style','display: table');
                }else {
                    $('#pwReCheck').attr('style','display: none');
                }
            });
            $('#memberPwReInput').blur(function() {
            	check();
            	if(!checkPw) {
                    $('#pwCheck').attr('style','display: table');
                }
                if(checkPw && !checkRePw) {
                    $('#pwReCheck').attr('style','display: table');
                }
            });
            
            $('#member_P_PhoneInput').blur(function() {
            	check();
                if(!checkPhoneP) {
                    $('#pPhoneCheck').attr('style','display: table');
                }
            });
            $('#member_C_PhoneInput').blur(function() {
            	check();
                if(!checkPhoneC) {
                    $('#cPhoneCheck').attr('style','display: table');
                }
            });
            $('#memberEmailInput').blur(function() {
            	check();
                if(!checkEmail) {
                    $('#emailCheck').attr('style','display: table');
                }
            });
            
            
	    });//$(document).ready END
	    
	    
	    
	    function getMemberInfo() {
	    	var memberId = '<%=memberId%>';
	    	$.ajax({
				url : "/getMemberInfo.do",
				data : {memberId: memberId},
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
						var memberPw = data.memberPw;
						var memberName = data.memberName;
						var memberPrivatePhone = data.memberPrivatePhone;
						var memberCompanyPhone = data.memberCompanyPhone;
						var memberEmail = data.memberEmail;
						var memberTypeName = data.memberTypeName;
						var compName = data.compName;
						var companyMemNo = data.companyMemNo;
						
						$('#memberIdInput').val( memberId );
						$('#memberPwInput').val( memberPw );
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
        
	    function check() {
            var memberPwInput = document.getElementById("memberPwInput").value;
            var memberPwReInput = document.getElementById("memberPwReInput").value;
            var member_P_PhoneInput = document.getElementById("member_P_PhoneInput").value;
            var member_C_PhoneInput = document.getElementById("member_C_PhoneInput").value;
            var memberEmailInput = document.getElementById("memberEmailInput").value;
            
            //비밀번호 확인
            //var checkPw = false;
            var patt_Pw1 = new RegExp("[a-zA-Z]+");
            var patt_Pw2 = new RegExp("^.{8,16}$");
		   	var res_Pw1 = patt_Pw1.test( memberPwInput );
            var res_Pw2 = patt_Pw2.test( memberPwInput );
            
            if(res_Pw1 && res_Pw2) {
                checkPw = true;
                //$('#pwCheck').attr('style','display: table');
                $('#pwCheck').html("사용가능한 비밀번호입니다.");
                $('#pwCheck').css('color','limegreen');
            }else {
                checkPw = false;
                $('#pwCheck').attr('style','display: table');
                $('#pwCheck').html("8~16자 영문 대 소문자, 숫자, 특수문자를  사용하여 작성해주세요.");
                $('#pwCheck').css('color','red');
            }
            //console.log("res_Pw1 "+res_Pw1);
            //console.log("res_Pw2 "+res_Pw2);
            //console.log("checkPw "+checkPw);
            //console.log("----");
            
            //비밀번호 재입력 일치 확인
            //var checkRePw = false;
            if(memberPwInput==memberPwReInput) {
                checkRePw = true;
                $('#pwReCheck').attr('style','display: table');
                $('#pwReCheck').html("비밀번호가 일치합니다.");
                $('#pwReCheck').css('color','limegreen');
            }else {
                checkRePw = false;
                if(checkPw && memberPwReInput!="") {
                	$('#pwReCheck').attr('style','display: table');
                }
                $('#pwReCheck').html("비밀번호가 일치하지 않습니다.");
                $('#pwReCheck').css('color','red');
            }
            //console.log("checkRePw "+checkRePw);
            //console.log("----");
            
            
            //개인 연락처 확인
            //var checkPhoneC = false;
			var patt_P1 = new RegExp("^[0-9]+$");
		   	var patt_P2 = new RegExp("^[0-9]{9,30}$");
		   	var res_P1 = patt_P1.test( member_P_PhoneInput );
		   	var res_P2 = patt_P2.test( member_P_PhoneInput );
		   	
            if(member_P_PhoneInput!="" && !res_P1) {
                checkPhoneP = false;
                $('#pPhoneCheck').attr('style','display: table');
                $('#pPhoneCheck').html("숫자로만 입력해주세요.");
                $('#pPhoneCheck').css('color','red');
            }
            else if(member_P_PhoneInput!="" && !res_P2) {
                checkPhoneP = false;
                $('#pPhoneCheck').attr('style','display: table');
                $('#pPhoneCheck').html("연락처는 9자리 이상 30자리 이하로 입력해주세요.");
                $('#pPhoneCheck').css('color','red');
            }
            else {
                checkPhoneP = true;
                $('#pPhoneCheck').attr('style','display: none');
            }
            //console.log("checkPhoneP "+checkPhoneP);
            //console.log("----");
            
            //회사 연락처 확인
            //var checkPhoneC = false;
			var patt_C1 = new RegExp("^[0-9]+$");
		   	var patt_C2 = new RegExp("^[0-9]{9,30}$");
		   	var res_C1 = patt_C1.test( member_C_PhoneInput );
		   	var res_C2 = patt_C2.test( member_C_PhoneInput );
		   	
		   	
            if(member_C_PhoneInput=="") {
				checkPhoneC = false;
				$('#cPhoneCheck').html("필수입력 사항입니다.");
				$('#cPhoneCheck').css('color','red');
            }
            else if(!res_C1) {
				checkPhoneC = false;
				$('#cPhoneCheck').attr('style','display: table');
				$('#cPhoneCheck').html("숫자로만 입력해주세요.");
				$('#cPhoneCheck').css('color','red');
            }
            else if(!res_C2) {
				checkPhoneC = false;
				$('#cPhoneCheck').attr('style','display: table');
				$('#cPhoneCheck').html("연락처는 9자리 이상 30자리 이하로 입력해주세요.");
				$('#cPhoneCheck').css('color','red');
            }
            else {
				checkPhoneC = true;
				$('#cPhoneCheck').attr('style','display: none');
            }
            //console.log("checkPhoneC "+checkPhoneC);
            //console.log("----");
            
            //이메일 입력 확인
            //var checkEmail = false;
            var patt_E1 = new RegExp("^.+@.+$");
            var patt_E2 = new RegExp("^.{3,30}$");
		   	var res_E1 = patt_E1.test( memberEmailInput );
		   	var res_E2 = patt_E2.test( memberEmailInput );
            if(memberEmailInput=="") {
                checkEmail = false;
                $('#emailCheck').html("필수입력 사항입니다.");
                $('#emailCheck').css('color','red');
            }
            else if(!res_E1) {
                checkEmail = false;
                $('#emailCheck').html("올바른 이메일 주소를 입력해주세요.");
                $('#emailCheck').css('color','red');
            }
            else if(!res_E2) {
                checkEmail = false;
                $('#emailCheck').html("이메일 주소는 30자 까지 입력 가능합니다.");
                $('#emailCheck').css('color','red');
            }
            else {
                checkEmail = true;
                $('#emailCheck').html("유효한 이메일 주소입니다.");
                $('#emailCheck').css('color','limegreen');
            }
            //console.log("checkEmail "+checkEmail);
            //console.log("----");
            
            
			if(checkPw
               && checkRePw
               && checkPhoneP
               && checkPhoneC
               && checkEmail) {
				$('#updateSubmit').css('background-color','rgb(0,0,65)');
                $('#updateSubmit').css('color','white');
                $('#updateSubmit').attr('onclick','return update();');
			}else {
				$('#updateSubmit').css('background-color','gainsboro');
                $('#updateSubmit').css('color','dimgray');
                $('#updateSubmit').attr('onclick','return false;');
			}
			if(checkPw && checkRePw) {
				$('#signOut').css('background-color','rgb(0,0,65)');
                $('#signOut').css('color','white');
                $('#signOut').attr('onclick','return signOut();');
			}else {
				$('#signOut').css('background-color','gainsboro');
                $('#signOut').css('color','dimgray');
                $('#signOut').attr('onclick','return false;');
			}
			
		}//function END
	    
        function update() {
            var memberId = $('#memberIdInput').val();
            var memberPw = $('#memberPwInput').val();
            var member_P_Phone = $('#member_P_PhoneInput').val();
            var member_C_Phone = $('#member_C_PhoneInput').val();
            var memberEmail = $('#memberEmailInput').val();
            
            $.ajax({
				url : "/updateMember.do",
				data : {memberId: memberId,
	            		memberPw: memberPw,
	            		member_P_Phone: member_P_Phone,
	            		member_C_Phone: member_C_Phone,
	            		memberEmail: memberEmail},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);///////////
					if(data) {
						alert("성공적으로 회원정보 수정이 완료되었습니다.")
						location.href = "/views/main/mainpage.jsp";
					}
					else {
						alert("회원정보를 수정하는 데 실패했습니다.\n"
								+"입력하신 내용을 다시 한번확인해주세요.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("처리 도중 오류가 발생했습니다. 다시 시도해주세요.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
				},
				complete : function(){
					//alert("complete");
				}
			});
            
        }//function END
        
        function signOut(){
        	var memberId = '<%=memberId%>';
        	var memberPw = $('#memberPwReInput').val();
        	//console.log(memberPw);//////////////////
        	if(memberPw==$('#memberPwInput').val()) {
        		$.ajax({
     				url : "/signOut.do",
     				data : {memberId: memberId,
     	            		memberPw: memberPw},
     				type : "post",
     				success : function(data){
     					//console.log("정상 처리 완료");
     					//alert("success");
     					//console.log(data);///////////
     					if(data) {
     						alert("정상적으로 탈퇴 처리 되었습니다.")
     						location.href = "/logout.do";
     					}
     					else {
     						alert("탈퇴 처리하는 데 실패했습니다.\n"
     								+"입력하신 내용을 다시 한번확인해주세요.\n"
     								+"문제가 지속될 경우 관리자에게 문의해주세요.");
     					}
     				},
     				error : function(){
     					//console.log("ajax 통신 에러");
     					alert("처리 도중 오류가 발생했습니다. 다시 시도해주세요.\n"
     							+"문제가 지속될 경우 관리자에게 문의해주세요.");
     				},
     				complete : function(){
     					//alert("complete");
     				}
     			});
        	}
        	else {
        		alert("비밀번호가 맞지 않습니다.");
        	} 
        }
        
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
                            <tr>
                                <th>이름</th>
                                <td>
                                    <input type="text" id="memberNameInput" readonly/>
                                </td>
                            </tr>
                            <tr>
                                <th>연락처(개인)(선택)</th>
                                <td>
                                    <input type="text" id="member_P_PhoneInput"/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="pPhoneCheck">숫자로만 입력해주세요.</td>
                            </tr>
                            <tr>
                                <th>연락처(회사)</th>
                                <td>
                                    <input type="text" id="member_C_PhoneInput"/>
                                </td>
                            </tr>
                            <tr class="conditions">
                                <th></th>
                                <td id="cPhoneCheck">필수입력 사항입니다.</td>
                            </tr>
                            <tr>
                                <th>이메일</th>
                                <td>
                                    <input type="text" id="memberEmailInput"/>
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
                        <div id="update">
                            <button id="updateSubmit" onclick="return false;">수정</button>
                            <button id="signOut" onclick="return false;">탈퇴</button>
                        </div>
                    </div>
                </div>
        </div>
        
    </div>
    
    
</body>
</html>