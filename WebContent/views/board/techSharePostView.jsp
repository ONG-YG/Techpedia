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
				String currPg = request.getParameter("currPg");
				//System.out.println("\nTechShareBoard memSession check 2\n"+memSession);////////////////////////
	%>
			<script>
				var memberTypeCD = '<%=memberTypeCD%>';
				var memberNo = <%=memberNo%>;
				var currPg = <%=currPg%>;
				var postNo = -1;
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
	<link href="/css/techSharePostView.css" rel="stylesheet" type="text/css">
    
	<script>
        
	    $(document).ready(function(){
	    	
	    	if(postNo!=-1) {
	    		getTechShPost();
	    	}
	    	
	    	
	    });//$(document).ready END
	    
	    function goBoard() {
	    	location.href="/views/main/mainpage.jsp?board=TechSh&currPg="+currPg;
	    }//function END
	    
	    function rewrite() {
	    	location.href="/views/main/mainpage.jsp?board=TechShRW&postNo="+postNo;
	    }//function END
	    
	    function deletePost() {
	    	alert("deletePost");////////////////
	    }//function END
	    
	    function writeCmm() {
	    	var comment = $('#cmmWrite_txt').val();
	    	
	    	if(comment.length>=300) {
	    		alert("300자 이하로 입력해주세요.");
	    	}
	    	else if(comment=='') {
	    		alert("댓글 내용을 입력해주세요.");
	    	}
	    	else {
	    		$.ajax({
					url : "/writeComment.do",
					data : {boardCD: "SHR", postNo: postNo, comment: comment},
					type : "post",
					success : function(data){
						//console.log("정상 처리 완료");
						//alert("success");
						//console.log(data);
						if(data==false) {
							alert("오류가 발생했습니다.\n"
									+"문제가 지속될 경우 관리자에게 문의해주세요.");
							location.href="/views/board/writeFail.jsp";
						}
						else {
							alert("댓글이 성공적으로 등록되었습니다.");
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
	    
	    
	    function getTechShPost() {
	    	$.ajax({
				url : "/techSharePostRead.do",
				data : {postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
						alert("오류가 발생했습니다.\n"
								+"문제가 지속될 경우 관리자에게 문의해주세요.");
						location.href="/views/board/readFail.jsp";
					}
					else {
						shrWriter = data.shrWriter;
						$('#techShTitle_td').html(data.shrTitle);
						$('#techShContent_txt').html(data.shrContent);
						$('#techShWriter_td').html(data.shrWriterName);
						$('#techShDate_td').html(data.shrDate);
						$('#techShCnt_td').html(data.shrCnt+1);
						//$('#fileDownload_td').html();//첨부파일
						
						if(memberNo==data.shrWriter) {
							$('#rewrite_btn').css('display','block');
							$('#rewrite_btn').attr('onclick','rewrite();');
							$('#delete_btn').css('display','block');
							$('#delete_btn').attr('onclick','deletePost();');
						}
						
						getComments();
						
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
				data : {board: "SHR", postNo: postNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
						var noComment = "<tr><td colspan='3'>댓글이 존재하지 않습니다.</td></tr>";
						$('#commentView_tb tbody').html(noComment);
					}
					else {
						var comments = '';
						var selectedAns = 'N';
						
						for(var i=0; i<data.length; i++) {
							var buttons = "";
							if(data[i].cmmWriterNo==memberNo) {
								buttons = "<button class='deleteCmm' onclick='delCmm("+data[i].cmmNo+");'>삭제</button>"
											+"<button class='rewriteCmm' onclick='reWriteCmm("+data[i].cmmNo+");'>수정</button>";
							}
							else if(memberNo==shrWriter) {
								buttons += "<button class='answerSelect' onclick='ansSel("+data[i].cmmNo+");'>답변채택</button>";
							}
							
							var selectedCmm = "";
							if(data[i].ansSelected=='Y') {
								selectedAns='Y';
								selectedCmm = "<span class='selectedAns'>채택</span>";
							}
							comments += "<tr id='cmm_"+data[i].cmmNo+"'>"
										+ "<td class='cmmWriter'>"+selectedCmm+data[i].cmmWriterName+"</td>"
										+ "<td class='cmmContent'><textarea readonly>"+data[i].cmmContent+"</textarea>"+buttons+"</td>"
										+ "<td class='cmmDate'>"+data[i].cmmDate+"</td>"
									+ "</tr>";
						}
						
						$('#commentView_tb tbody').html(comments);
						
						if(selectedAns=='Y') $('.answerSelect').remove();
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
	    
	    function delCmm(cmmNo){
	    	
    		$.ajax({
				url : "/deleteComment.do",
				data : {cmmNo: cmmNo},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
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
	    	
	    	alert(commentRe);////////////////
	    	
	    	$.ajax({
				url : "/updateComment.do",
				data : {cmmNo: cmmNo, commentRe: commentRe},
				type : "post",
				success : function(data){
					//console.log("정상 처리 완료");
					//alert("success");
					//console.log(data);
					if(data==false) {
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
	    
	    function ansSel(cmmNo){
	    	var chk = confirm("한번 채택답변으로 설정하면 취소할 수 없습니다.\n"
	    				+"채택답변으로 설정하시겠습니까?");
	    	if(chk) {
	    		$.ajax({
					url : "/selectAnswerCmm.do",
					data : {cmmNo: cmmNo},
					type : "post",
					success : function(data){
						//console.log("정상 처리 완료");
						//alert("success");
						//console.log(data);
						if(data==false) {
							alert("오류가 발생했습니다.\n"
									+"문제가 지속될 경우 관리자에게 문의해주세요.");
							location.href="/index.jsp";
						}
						else {
							alert("성공적으로 답변 채택을 완료하었습니다.");
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
	    	}
	    	
	    }//function END
	    
    </script>
</head>
<body>
	
	<div id="techShWrite">
	    
	    <span>기술 공유 게시물</span>
	    
		    <table id="techSh-tb">
		    	
		        <tr id="techShTitle_tr">
		            <th id="techShTitle">제목</th>
	                <td id="techShTitle_td"></td>
		        </tr>
		        <tr id="techShWriter_tr">
		            <th id="techShWriter">작성자</th>
	                <td id="techShWriter_td"></td>
		        </tr>
		        <tr id="techShDate_tr">
		            <th id="techShDate">작성일</th>
	                <td id="techShDate_td"></td>
		        </tr>
		        <tr id="techShCnt_tr">
		            <th id="techShCnt">조회수</th>
	                <td id="techShCnt_td"></td>
		        </tr>
	            <tr id="techShContent_tr">
		            <th id="techShContent">내용</th>
	                <td id="techShContent_td">
                        <textarea id="techShContent_txt" readonly></textarea>
                    </td>
		        </tr>
		        <tr id="fileDownload_tr">
		            <th id="techShFile">첨부파일</th>
	                <td id="fileDownload_td">
						첨부파일 다운로드 링크
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
                <thead>
                    <tr>
                        <th>댓글 작성</th>
                        <td id="cmmWrite_td"><textarea id="cmmWrite_txt"></textarea></td>
                        <td id="cmmWrite_btn_td"><button onclick="writeCmm();">등록</button></td>
                    </tr>
                </thead>
            </table>
            <table id="commentView_tb">
                    <thead>
                    <tr>
                        <th>작성자</th>
                        <th>댓글내용</th>
                        <th>작성시간</th>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
	</div>
	
</body>
</html>