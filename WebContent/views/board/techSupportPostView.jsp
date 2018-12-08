<%@page import="kr.co.techpedia.board.model.vo.Notice"%>
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
		//System.out.println("\nTechShareBoard session : "+session);/////////////////////////
		if(session!=null) {
			MemberSession memSession = (MemberSession)session.getAttribute("memSession");
			//System.out.println("\nTechShareBoard memSession check 1\n"+memSession);////////////////////////
			if(memSession!=null) {
				String memberTypeCD = memSession.getMemberTypeCD();
				int memberNo = memSession.getMemberNo();
				int compNo = memSession.getCompNo();
				String currPg = request.getParameter("currPg");
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var compNo = <%=compNo%>;
				var currPg = <%=currPg%>;
				var postNo = -1;
				var spptWriter = -1;
				var spptEng = -1;
			</script>
	<%
				if(!request.getParameter("postNo").equals("null")){
					int postNo = Integer.parseInt(request.getParameter("postNo"));
					%>
					<script>
						postNo = <%=postNo%>;
					</script>
					<%
				}else {
					%>
					<script>
						location.href="/views/board/readError.jsp";
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
		}else {
	%>
		<script>
			accessDenied();
		</script>
	<%
		}
	%>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link href="/css/techSupportPostView.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	if(postNo!=-1) {
	    		getTechSppPost();
	    		
	    	}
	    	
	    	$('#techSppWriter_td').click(function(){
	    		getContactInfo(spptWriter);
	    	});
	    	
	    	$('#engName_span').click(function(){
	    		if(!$('#engName_span').hasClass('noEng')) {
	    			getContactInfo(spptEng);
	    		}
	    		
	    	});
	    	
	    	$('#fileDownload_td').click(function(){
	    		if($('#fileDownload_td').hasClass('fileExist')) {
	    			var fileName = $('#fileDownload_td').html();
		    		location.href = "/downloadFile.do?fileName="+fileName;
	    		}
	    		
	    	});
	    	
	    	
	    });//$(document).ready END
	    
	    function goBoard() {
	    	location.href="/views/main/mainpage.jsp?board=TechSpp&currPg="+currPg;
	    }//function END
	    
	    function rewrite() {
	    	location.href="/views/main/mainpage.jsp?board=TechSppRW&postNo="+postNo;
	    }//function END
	    
	    function deletePost() {
	    	
    		$.ajax({
				url : "/deletePost.do",
				data : {postNo: postNo, boardCD: "SPPT"},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
					}
					else {
						alert("게시물이 성공적으로 삭제되었습니다.");
						location.href="/views/main/mainpage.jsp?board=TechSpp";
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
    		
	    }//function END
	    
	    function writeAnsCmm() {
	    	var comment = $('#cmmWrite_txt').val();
	    	var sppStatSelect = $('#sppStatSelect').val();
	    	
	    	if(comment.length>=300) {
	    		alert("300자 이하로 입력해주세요.");
	    	}
	    	else if(comment=='') {
	    		alert("댓글 내용을 입력해주세요.");
	    	}
	    	else {
	    		$.ajax({
					url : "/writeAnswerCmm.do",
					data : {boardCD: "SPPT", postNo: postNo, sppStatSelect: sppStatSelect, comment: comment},
					type : "post",
					success : function(data){
						//console.log("정상 처리 완료");
						//alert("success");
						//console.log(data);
						if(data=='false') {
							alert("오류가 발생했습니다.\n"
									+"문제가 지속될 경우 관리자에게 문의해주세요.");
							location.href="/views/board/writeFail.jsp";
						}
						else {
							alert("댓글이 성공적으로 등록되었습니다.");
							getTechSppPost();
							getComments();
						}
					},
					error : function(){
						//console.log("ajax 통신 에러");
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/views/board/writeFail.jsp";
					},
					complete : function(){
						//alert("complete");
					}
				});
	    		$('#cmmWrite_txt').val('');
	    	}
	    	
	    }//function END
	    
	    
	    function getTechSppPost() {
	    	$.ajax({
				url : "/techSupportPostRead.do",
				data : {postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/views/board/readFail.jsp";
					}
					else {
						var writerCompNo = data.spptWriterCompNo;
						
						if(writerCompNo!=compNo && memberTypeCD=='COP') {
							location.href="/views/board/abnormalAccess.jsp";
						}
						else {
							spptWriter = data.spptWriter;
							spptEng = data.spptEng;
							var engName = data.spptEngName;
							if(engName==null) {
								engName = '(배정되지 않음)';
								$('#engName_span').addClass('noEng');
								$('#engName_span').css('cursor','text');
							}
							
					    	if(data.spptStatcd=='COMPLETE') {
					    		$('#cmmWrite_tr').remove();
					    	}
					    	else if(data.spptStatcd=='NEW' 
					    			&& (memberTypeCD=='MNFE_AD'
					    				|| memberTypeCD=='MNFE')) {
					    		$('#takeCharge_btn').css('display','block');
					    		$('#takeCharge_btn').attr('onclick','takeCharge();');
					    	}
					    	else if(data.spptStatcd!='NEW') {
					    		$('#takeCharge_btn').css('display','none');
					    		$('#takeCharge_btn').attr('onclick','');
					    	}
				    		
							$('#techSppState_span').html(data.spptStatName);
							$('#techSppTitle_td').html(data.spptTitle);
							$('#techSppContent_txt').html(data.spptContent);
							$('#techSppWriter_td').html(data.spptWriterName);
							$('#engName_span').html(engName);
							$('#engCheck_span').html(data.spptEngck);
							$('#techSppDate_td').html(data.spptDate);
							$('#techSppCnt_td').html(data.spptCnt+1);
							$('#fileDownload_td').html(data.attName);//첨부파일

							if(data.attName!=null) {
								$('#fileDownload_td').addClass('fileExist');
								$('#fileDownload_td').css('cursor','pointer');
							}
							
							if(data.spptEngck=='N') {
								$('#engCheck_span').css('color','coral');
							}
							if(data.spptEngName==null) $('#engCheck_span').remove();
							
							if(memberNo==data.spptWriter) {
								
								$('#rewrite_btn').css('display','block');
								$('#rewrite_btn').attr('onclick','rewrite();');
								$('#delete_btn').css('display','block');
								$('#delete_btn').attr('onclick','deletePost();');
								
							}
							
							getComments();
							getSpptStatList();
							
							if(spptEng==memberNo && data.spptEngck=='N') {
								setEngck();
							}
						}
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/views/board/readFail.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	    

	    function getComments(){
	    	$.ajax({
				url : "/getComments.do",
				data : {board: "SPPT", postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						var noComment = "<tr><td colspan='3'>댓글이 존재하지 않습니다.</td></tr>";
						$('#commentView_tb tbody').html(noComment);
					}
					else {
						var comments = '';
						for(var i=0; i<data.length; i++) {
							var buttons = "";
							
							if(data[i].cmmWriterNo==memberNo) {
								/* 
								buttons = "<button class='deleteCmm' onclick='delCmm("+data[i].cmmNo+");'>삭제</button>"
											+"<button class='rewriteCmm' onclick='reWriteCmm("+data[i].cmmNo+");'>수정</button>";
								 */
								buttons = "<button class='rewriteCmm' onclick='reWriteCmm("+data[i].cmmNo+");'>수정</button>";
							}
							
							comments += "<tr id='cmm_"+data[i].cmmNo+"'>"
										+ "<td class='cmmWriter'>"+data[i].cmmWriterName+"</td>"
										+ "<td class='cmmContent'><textarea readonly>"+data[i].cmmContent+"</textarea>"+buttons+"</td>"
										+ "<td class='cmmDate'>"+data[i].cmmDate+"</td>"
									+ "</tr>";
							
						}
						$('#commentView_tb tbody').html(comments);
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/views/board/readFail.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	    
	    function getSpptStatList() {
	    	$.ajax({
				url : "/techSpptStatList.do",
				data : {memberTypeCD: memberTypeCD},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("페이지를 불러오는 도중 오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp"
					}
					else {
						var statListOption = "<option value='none'>선택</option>";
						for(var i=0; i<data.length; i++) {
							statListOption += "<option value='"+data[i].spptStatCD+"'>"
															+data[i].statName+"</option>";
						}
						$('#sppStatSelect').html(statListOption);
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
	    
	    /* 
	    function delCmm(cmmNo){
	    	
    		$.ajax({
				url : "/deleteComment.do",
				data : {cmmNo: cmmNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
					}
					else {
						alert("댓글이 성공적으로 삭제되었습니다.");
						getComments();
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
    		
	    }//function END
	     */
	    function reWriteCmm(cmmNo){
	    	var del_btn_selector = '#cmm_'+cmmNo+' .cmmContent .deleteCmm';
	    	var re_btn_selector = '#cmm_'+cmmNo+' .cmmContent .rewriteCmm';
	    	var td_selector = '#cmm_'+cmmNo+' .cmmContent';
	    	$(td_selector+' textarea').attr('readonly',false);
	    	$(td_selector+' textarea').css('background-color', 'rgb(255,255,255,0.9)');
	    	$(td_selector+' textarea').css('border', '1px solid darkgray');
	    	
	    	$(del_btn_selector).css('display','none');
	    	$(re_btn_selector).css('display','none');
	    	
	    	var td_html = $(td_selector).html();
	    	td_html += "<button class='reInsert' onclick='updateCmm("+cmmNo+");'>등록</button>";
	    	$(td_selector).html(td_html);
	    }//function END
	    
	    function updateCmm(cmmNo){
	    	var del_btn_selector = '#cmm_'+cmmNo+' .cmmContent .deleteCmm';
	    	var re_btn_selector = '#cmm_'+cmmNo+' .cmmContent .rewriteCmm';
	    	var td_selector = '#cmm_'+cmmNo+' .cmmContent';
	    	
	    	var commentRe = $(td_selector+' textarea').val();
	    	
	    	$.ajax({
				url : "/updateComment.do",
				data : {cmmNo: cmmNo, commentRe: commentRe},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
					}
					else {
						$(td_selector+' textarea').attr('readonly',true);
				    	$(td_selector+' textarea').css('background-color', 'transparent');
				    	$(td_selector+' textarea').css('border', 'none');
				    	
				    	$(del_btn_selector).css('display','block');
				    	$(re_btn_selector).css('display','block');
				    	
				    	$(td_selector+' .reInsert').remove();
				    	
				    	alert("댓글이 성공적으로 수정되었습니다.");
				    	
						getComments();
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    	
	    }//function END
	    
	    function setEngck(){
	    	
	    	$.ajax({
				url : "/setTechSpptEngck.do",
				data : {postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
					}
					else {
						$('#engCheck_span').html('Y');
						$('#engCheck_span').css('color','slategrey');
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    	
	    }//function END
	    
	    function getContactInfo(memberNo){
	    	$.ajax({
				url : "/getContactInfo.do",
				data : {memberNo: memberNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
					}
					else {
						var companyPhone = data.memberCompanyPhone;
						var personalPhone = data.memberPrivatePhone;
						var email = data.memberEmail;
						alert("담당자 연락처\n\n"
								+"회사연락처 : "+companyPhone+"\n"
								+"개인연락처 : "+personalPhone+"\n"
								+"Email : "+email);
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
				},
				complete : function(){
					//alert("complete");
				}
			});
	    }//function END
	    
	    function takeCharge(){
	    	
	    	$.ajax({
				url : "/takeChargeOfTechSpp.do",
				data : {postNo: postNo, memberNo: memberNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data=='false') {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/index.jsp";
					}
					else {
						getTechSppPost();
					}
				},
				error : function(){
					//console.log("ajax 통신 에러");
					alert("오류가 발생했습니다.\n"
							+"문제가 지속될 경우 관리자에게 문의해주세요.");
					location.href="/index.jsp";
				},
				complete : function(){
					//alert("complete");
				}
			});
	    	
	    	
	    }//function END
	    
    </script>
</head>
<body>
	
	<div id="techSppWrite">
	    
	    <span>기술 지원 게시물</span>
	    	
		    <table id="techSpp-tb">
		    	<tr>
		            <th></th>
	                <td><button id="takeCharge_btn">이 기술 지원 담당하기</button></td>
		        </tr>
		    	<tr id="techSppState_tr">
		            <th id="techSppState">진행현황</th>
	                <td>
		                <span id="techSppState_span"></span>
	                </td>
		        </tr>
		        <tr id="techSppTitle_tr">
		            <th id="techSppTitle">제목</th>
	                <td id="techSppTitle_td"></td>
		        </tr>
		        <tr id="techSppWriter_tr">
		            <th id="techSppWriter">협력사 담당자</th>
	                <td id="techSppWriter_td"></td>
		        </tr>
		        <tr id="techSppEngineer_tr">
		            <th id="techSppEngineer">담당 엔지니어</th>
	                <td id="techSppEngineer_td">
		                <span id="engName_span"></span>
		                <span id="engCheck_span"></span>
	                </td>
		        </tr>
		        <tr id="techSppDate_tr">
		            <th id="techSppDate">작성일</th>
	                <td id="techSppDate_td"></td>
		        </tr>
		        <tr id="techSppCnt_tr">
		            <th id="techSppCnt">조회수</th>
	                <td id="techSppCnt_td"></td>
		        </tr>
	            <tr id="techSppContent_tr">
		            <th id="techSppContent">내용</th>
	                <td id="techSppContent_td">
                        <textarea id="techSppContent_txt" readonly></textarea>
                    </td>
		        </tr>
		        <tr id="fileDownload_tr">
		            <th id="techSppFile">첨부파일</th>
	                <td id="fileDownload_td">
						첨부파일된 파일이 존재하지 않습니다.
	                </td>
		        </tr>
		        <tr id="button_tr">
	                <td colspan="2">
	                	<button id="goBoard_btn" onclick="goBoard();">목록</button>
	                	<button id="rewrite_btn" onclick="">수정</button>
	                	<button id="delete_btn" onclick="">삭제</button>
	                </td>
	            </tr>
		    </table>
			<table id="commentWrite_tb">
                <tbody>
                    <tr>
                        <th id="sppStatSelect_th">진행현황</th>
                        <td id="sppStatSelect_td">
                        	<select id="sppStatSelect">
                        		<option value="none">선택</option>
                        	</select>
                        </td>
                        <td></td>
                    </tr>
                    <tr id="cmmWrite_tr">
                    	<th rowspan="2">피드백 작성</th>
                        <td id="cmmWrite_td"><textarea id="cmmWrite_txt"></textarea></td>
                        <td rowspan="2" id="cmmWrite_btn_td"><button onclick="writeAnsCmm();">등록</button></td>
                    </tr>
                </tbody>
            </table>
            <table id="commentView_tb">
                    <thead>
                    <tr>
                        <th>작성자</th>
                        <th>피드백 내용</th>
                        <th>작성시간</th>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
	</div>
	
</body>
</html>