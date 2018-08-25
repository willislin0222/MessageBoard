<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page import="java.util.*"%>
<%@ page import="com.message.model.*"%>
<%@ page import="com.member.model.*"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
//     pageContext.setAttribute("memberVO", (MemberVO) session.getAttribute("memberVO"));
%>

<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>留言清單</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/listAllMessage.css" />
		<link rel="stylesheet" href="<%= request.getContextPath()%>/resources/css/member/bootstrap.min.css">
		<script src="<%=request.getContextPath()%>/resources/js/jquery-3.3.1.min.js"></script>
	    <script src="<%=request.getContextPath()%>/resources/js/toastr.js"></script>
	    <link href="<%=request.getContextPath()%>/resources/css/toastr.css" rel="stylesheet" type="text/css" />
	</head>
	<body>
<!-- 		使用JavaBean -->
		<jsp:useBean id="messageSvc" class="com.message.model.MessageService" scope="request" />
		<% int count=2;%>
		<h1 class="text-center">Willis留言板！${searchselect}-${searchtext}</h1>
		<div class="container">
			<%@ include file="page3.file" %>
			<div class="title"><a href='#modal-id' data-toggle="modal">我要留言</a>|
				<div class="modal fade" id="modal-id">
					<div class="modal-dialog">
						<div class="modal-content addWindow">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title">請輸入留言內容</h4>
							</div>
							<div class="col-lg-12">
				                  <div class="form-group">
				                    <label for="ID" id="addtitle">標題</label>
				                    <input type="text" name="mes_title" id="mes_title" tabindex="1" />
				                  	<font size="3" style="color:red"><div id="checkid"></div></font>
				                  </div>
				                  
				                  <div class="form-group">
				                    <label for="ID">內容</label>
				                    <input type="text" name="mes_text" id="mes_text" tabindex="1">
				                  </div>
				              </div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default closeAddMessage" data-dismiss="modal">關閉</button>
								<button id="addMessageBtn" type="button" class="btn btn-primary">新增留言</button>
								<input type="hidden" name="requestURL" id="requestURL" tabindex="1" value="<%= request.getRequestURI()%>"> <%--傳送目前網址到controller --%>
								<input type="hidden" name="whichPage" id="whichPage" tabindex="1" value="<%=whichPage%>">     <%--傳送目前頁數到controller --%>
								<input type="hidden" name="pageNumber" id="pageNumber" tabindex="1" value="<%=pageNumber%>">  <%--傳送總頁數到controller --%>
							</div>
						</div>
					</div>
				</div>
				<c:if test="${memberVO eq null}">
					<a href="<%=request.getContextPath()%>/member/loginMember">登入會員</a>
				</c:if>
				<c:if test="${memberVO ne null}">
					<a href="<%=request.getContextPath()%>/member/logout">登出</a>|
					<a href="<%=request.getContextPath()%>/member/getOneForUpdate?mem_id=${memberVO.mem_id}">修改密碼</a>
				</c:if>
			<!-- a href="#">登入管理</a> -->|目前全部有<font color="red">${rowNumber}</font>筆留言</div>
			<div class="title">目前每頁顯示<font color="red">${rowsPerPage}</font>則留言 所以分為<font color="red">${pageNumber}</font>頁 請選擇頁數 現在您在 
				<select onChange="location = this.options[this.selectedIndex].value;"">
					<c:forEach var="num" begin="1" end="<%=pageNumber%>" step="1">	
						<option value="<%=request.getRequestURI()%>?whichPage=${num}" ${num==whichPage? 'selected="selected"':''}>第${num}頁</option>
					</c:forEach>
				</select>
			</div>
			<div class="title2">
				<form action="<%=request.getContextPath()%>/message/searchMessages" method="POST">
					<select name="searchselect">
						<option value="0">請選擇</option>
						<option value="1">留言者</option>
						<option value="2">留言內容</option>
						<option value="3">回覆者</option>
						<option value="4">回覆內容</option>
					</select>
					<input type="text" width="50%" name="searchtext" id="keyword" placeholder="請輸入搜尋內容">
					<button class="btn btn-success" id="search_submit" type="submit">搜尋</button>
				</form>
				<font color="red"><h4>訪問人數${Messagelist}:${count}</h4></font>
			</div>
			    
				<c:forEach var="messageVO" items="${messagelist}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<div class="maindiv" ${(messageVO.mes_no==param.mes_no) ? 'style="background-color:#30FFFF;"':''}>
						<div class="col-xs-12 col-sm-3 divleft">
							 <img id="image"class="image" src="<%=request.getContextPath()%>/member/MemberPhoto?mem_id=${messageVO.memberVO.mem_id}"><br>
							 <a href='#modal-id<%= count%>' data-toggle="modal" class="btn btn-primary button" role="button">我要回應</a>
							 	<div class="modal fade" id="modal-id<%= count%>">
								<div class="modal-dialog">
									<div class="modal-content addWindow">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
											<h4 class="modal-title">請輸入回應內容</h4>
										</div>
										<div class="col-lg-12">
							                  <div class="form-group">
							                    <label for="ID">內容</label>
							                    <input type="text" name="rep_text" id="rep_text<%= count%>" tabindex="1">
							                  </div>
							              </div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default closeAddMessage" data-dismiss="modal">關閉</button>
											<button id="addReplyMessageBtn" type="button" class="btn btn-primary" onclick=addReplyMessage(${messageVO.mes_no},<%= count++%>);>回應</button>
										</div>
									</div>
								</div>
							</div>
							 	
							 	
							 	
							 	<c:if test="${memberVO.mem_id == messageVO.memberVO.mem_id or memberVO.mem_id.equals('admin')}"><br><br>
									      <FORM METHOD="POST" ACTION="<%=request.getContextPath()%>/message/getupdate">
											  <input type="submit" value="修改留言" class="btn btn-primary">
											  <input type="hidden" name="mes_no" value="${messageVO.mes_no}">
											  <input type="hidden" name="requestURL" id="requestURL" tabindex="1" value="<%= request.getRequestURI()%>"> <%--傳送目前網址到controller --%>
											  <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
										  </FORM>
							 	</c:if>
								<c:if test="${memberVO.mem_id.equals('admin')}">						 
<%-- 									 <br><a href="<%=request.getContextPath()%>/message/delete?mes_no=${messageVO.mes_no}" class="btn btn-primary button">刪除留言</a> --%>
									 <FORM METHOD="POST" ACTION="<%=request.getContextPath()%>/message/delete">
										  <input type="submit" value="刪除留言" class="btn btn-primary">
										  <input type="hidden" name="mes_no" value="${messageVO.mes_no}">
										  <input type="hidden" name="requestURL" id="requestURL" tabindex="1" value="<%= request.getRequestURI()%>"> <%--傳送目前網址到controller --%>
										  <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
									 </FORM>
								</c:if>
						</div>
						<div class="col-xs-12 col-sm-9 divright">
						     <h4>${messageVO.memberVO.mem_id}於${messageVO.mes_date}發布:</h4>
						     <h4>${messageVO.mes_title}</h4>
						     <h4>${messageVO.mes_text}</h4>
						     ================================
								<div>
									<c:forEach var="replymessageVO" items="${messageVO.replymessages}">
										<div class="col-xs-12 col-sm-9 divright" ${(replymessageVO.rep_no==param.rep_no) ? 'style="background-color:#30FFFF;"':''}>
										     <h4>${replymessageVO.memberVO.mem_id}於${replymessageVO.rep_date}回復說:
										     	<c:if test="${memberVO.mem_id == replymessageVO.memberVO.mem_id or memberVO.mem_id.equals('admin')}">
													<a href="<%=request.getContextPath()%>/replymessage/getupdate?rep_no=${replymessageVO.rep_no}&whichPage=${whichPage}" class="btn btn-primary button">修改留言</a>
													<a href='#modal-id1' data-toggle="modal" class="btn btn-primary button">刪除</a>
														<div class="modal fade" id="modal-id1">
															<div class="modal-dialog">
																<div class="modal-content">
																	<div class="modal-header">
																		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
																		<h4 class="modal-title">刪除確認</h4>
																	</div>
																	<div class="modal-body">
																		您確定要刪除此留言
																	</div>
																	<div class="modal-footer">
																		
<%-- 																		<a href="<%=request.getContextPath()%>/replymessage/delete?rep_no=${replymessageVO.rep_no}" class="btn btn-primary" role="button">確認</a> --%>
																		  <FORM METHOD="POST" ACTION="<%=request.getContextPath()%>/replymessage/delete">
																			  <button type="button" class="btn btn-primary" data-dismiss="modal">關閉</button>
																			  <input type="submit" value="確認" class="btn btn-primary">
																			  <input type="hidden" name="rep_no" value="${replymessageVO.rep_no}">
																			  <input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
																		  </FORM>
																	</div>
																</div>
															</div>
														</div>
												</c:if>
										     </h4>
										     <h4>${replymessageVO.rep_text}</h4>
										</div>
									</c:forEach>
								</div>
						</div>
					</div>
				</c:forEach>
			<%@ include file="page4.file" %>
		</div>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>
<script>
$(document).ready(function(){
	$('#addMessageBtn').click(function(){
		 $.ajax({
			    type: "POST",
			    url: "<%=request.getContextPath()%>/message/insert",
			    data: {
  		      	       mes_title :  $('#mes_title').val(),
			    	   mes_text :  $('#mes_text').val(),
			    	   requestURL : $('#requestURL').val(),
			    	   whichPage : $('#whichPage').val(),
			    	   pageNumber : $('#pageNumber').val()
			    	  },
				cache: false,
				success: function(jsonData){
					var returndata = eval(jsonData);
					if(returndata.result == "nologin"){
						alert("請先登入會員");
						url="<%=request.getContextPath()%>/member/loginMember"
						window.location.replace(url);
					}else if(returndata.result == "empty"){
						alert("標題內容請勿空白");
<%-- 						url="<%=request.getContextPath()%>/message/listAllMessage.jsp" --%>
// 						window.location.replace(url);
					}else{
						toastr.success("留言新增成功");
						$('.closeAddMessage').click();	
						url=returndata.requestURL + "?whichPage=" + returndata.pageNumber;
						window.location.replace(url);
					}
				
				},
// 			    error: function(xhr, ajaxOptions, thrownError)
// 			    { 
// 			    	alert("error")
// 			    }
		 });
	});
});

function addReplyMessage(mes_no,count){
	$.ajax({
	    type: "POST",
	    url: "<%=request.getContextPath()%>/replymessage/insert",
	    data:{ 
	    		rep_text : $('#rep_text' + count).val(),
	    		mes_no : mes_no,
	    		requestURL : $('#requestURL').val(),
		    	whichPage : $('#whichPage').val(),
		    	pageNumber : $('#pageNumber').val()
	    
	    	 },
		cache: false,
		success: function(jsonData){
			var returndata = eval(jsonData);
			if(returndata.result == "nologin"){
				alert("請先登入會員");
				url="<%=request.getContextPath()%>/member/loginMember"
				window.location.replace(url);
			}else if(returndata.result == "empty"){
				alert("回應內容請勿空白");
//					window.location.replace(url);
			}else{
				toastr.success("留言回復成功");
				$('.closeAddMessage').click();	
				url=returndata.requestURL + "?whichPage=" + returndata.pageNumber;
				window.location.replace(url);
				window.location.reload(); 	
			}
		
		}
 });
}
</script>