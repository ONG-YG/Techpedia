<%@page import="kr.co.techpedia.member.model.vo.MemberSession"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%
		session = request.getSession(false);
		//System.out.println("\nindex page session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			System.out.println("\nindex page session check\n"+memSession);///////////////////
			if(memSession!=null) {
	%>
			<script>
				location.href="/views/main/mainpage.jsp";
			</script>
	<%
			}
		}
	%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Techpedia</title>
    <link href="/css/index.css" rel="stylesheet" type="text/css">
    
    <script
      type="text/javascript"
        src="/resources/jquery-3.3.1.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
    </script>
    <script>
        var checkId = false;
        var checkPw = false;
        
	    $(document).ready(function(){
            
	    	check();
	    	
            $(document).change(function(){
	    		//console.log("mouseup");
	    		check();
	    	});
	    	
	    	$(document).keyup(function(){
	    		//console.log("keyup");
	    		check();
	    	});
	    	
	    	
	    });//$(document).ready END
        
	    
	    
	    function check() {
            //alert("check start");
			var idInput = document.getElementById("idInput").value;
            var pwInput = document.getElementById("pwInput").value;
            
            //아이디 확인
            //var checkId = false;
            if(idInput!="") {
                checkId = true;
            }else {
                checkId = false;
            }
            //console.log("checkId "+checkId);
            //console.log("----");
            
            //비밀번호 확인
            //var checkPw = false;
            if(pwInput!="") {
                checkPw = true;
            }else {
                checkPw = false;
            }
            //console.log("checkPw "+checkPw);
            //console.log("----");
            
			if(checkId && checkPw) {
				$('#login').css('background-color','rgb(0,0,40)');
                $('#login').css('color','white');
                //$('#login').attr('onclick','return login();');
			}else {
				$('#login').css('background-color','gainsboro');
                $('#login').css('color','dimgray');
                //$('#login').attr('onclick','return cannotLogin();');
			}
			
		}//function END
	    
        function goIndex(){
            //location.href="/index.html";
            location.href="/index.jsp";
        }//function END
        
        function goEnroll(){
            //location.href="./views/member/enroll.html";
        	location.href="/views/member/enroll.jsp";
        }//function END
        
        function login(){
        	check();
            if(!checkId && !checkPw) {
                alert("아이디와 비밀번호를 모두 입력해주세요.");
            }
            else if(!checkId) {
                alert("아이디를 입력해주세요.");
            }
            else if(!checkPw) {
                alert("비밀번호를 입력해주세요.");
            }
            else {
            	var id = $('#idInput').val();
            	var pw = $('#pwInput').val();
            	//console.log(id);
            	//console.log(pw);
            	
            	$.ajax({
    				url : "/login.do",
    				data : {memberId: id, memberPw: pw},
    				type : "post",
    				success : function(data){
    					//console.log("정상 처리 완료");
    					console.log(data);
    					//alert("success");
    					if(data) {
    						location.href = "/views/main/mainpage.jsp";
    					}
    					else {
    						alert("로그인에 실패하였습니다. 아이디와 비밀번호를 확인해주세요.");
    					}
    				},
    				error : function(){
    					//console.log("ajax 통신 에러");
    					alert("로그인에 실패하였습니다. 다시 시도해주세요.");
    				},
    				complete : function(){
    					//alert("complete");
    				}
    			});
            }
        }//function login() END
        
        /* 
        function cannotLogin() {
        	check();
            if(!checkId && !checkPw) {
                alert("아이디와 비밀번호를 모두 입력해주세요.");
            }
            else if(!checkId) {
                alert("아이디를 입력해주세요.");
            }
            else if(!checkPw) {
                alert("비밀번호를 입력해주세요.");
            }
        }
         */
         
    </script>
</head>
<body>
    
    <div id="wrapper">
        
        <div id="header">
        	<div id="mainTitle">
            	<strong onclick="goIndex();">Techpedia</strong>
            </div>
        </div>
        
        
        
        
        <div id="content">
                <div id="login-box">
					<div>
                        <table>
                            <tr>
                                <th>ID</th>
                                <td><input type="text" name="id" id="idInput"
                                placeholder="아이디를 입력해주세요" tabindex="1"/></td>
                                <td rowspan="2">
                                    <button id="login" onclick="return login();" tabindex="3">로그인</button>
                                </td>
                            </tr>
                            <tr>
                                <th>PW</th>
                                <td><input type="password" name="pw" id="pwInput"
                                placeholder="비밀번호를 입력해주세요" tabindex="2"
                                onkeydown="if (window.event.keyCode == 13) {login();}"/></td>
                            </tr>
                        </table>
                        <div id="enrollDiv">
                        	<span id="enroll" onclick="goEnroll();" tabindex="4">회원가입</span>
                        </div>
                    </div>
                </div>
        </div>
        
        
        
        
        <div id="footer">
            <span>
                아이디/비밀번호를 잊었을 경우 관리자에게 문의해주세요
            </span>
        </div>
        
    </div>
    
    
</body>
</html>