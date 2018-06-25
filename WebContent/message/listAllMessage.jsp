<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		
		<h1 class="text-center">Willis留言板！</h1>
		<div class="container">
			<%@ include file="page1.file" %>
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
<%-- 				                    <td><form:errors path="mes_title" cssClass="error"/></td> --%>
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
							</div>
						</div>
					</div>
				</div>
				<c:if test="${memberVO eq null}">
					<a href="<%=request.getContextPath()%>/member/loginMember">登入會員</a>
				</c:if>
				<c:if test="${memberVO ne null}">
					<a href="<%=request.getContextPath()%>/member/logout">登出</a>|
					<a href="<%=request.getContextPath()%>/member/getOneForUpdate?mem_no=${memberVO.mem_no}">修改密碼</a>
				</c:if>
			<!-- a href="#">登入管理</a> -->|目前全部有<font color="red">${rowNumber}</font>筆留言</div>
			<div class="title">目前每頁顯示<font color="red">${rowsPerPage}</font>則留言 所以分為<font color="red">${pageNumber}</font>頁 請選擇頁數 現在您在 
				<select onChange="location = this.options[this.selectedIndex].value;"">
					<c:forEach var="num" begin="1" end="<%=pageNumber%>" step="1">	
						<option value="<%=request.getRequestURI()%>?whichPage=${num}" ${num==whichPage? 'selected="selected"':''}>第${num}頁</option>
					</c:forEach>
				</select>
			</div>
			<div class="title2"><lable>搜尋</lable> <input type="text"><button>搜尋</button></div>
				<c:forEach var="messageVO" items="${messageSvc.all}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<div class="maindiv">
						<div class="col-xs-12 col-sm-3 divleft">
							 <img id="image"class="image" src="<%=request.getContextPath()%>/member/MemberPhoto?mem_id=${messageVO.memberVO.mem_id}"><br>
							 <a href="<%=request.getContextPath()%>/replymessage/addReplyMessage?mes_no=${messageVO.mes_no}" class="btn btn-primary button" role="button">我要回應</a>
							 	<c:if test="${memberVO.mem_id == messageVO.memberVO.mem_id or memberVO.mem_id.equals('admin')}">
									 <br><br><a href="<%=request.getContextPath()%>/message/getupdate?mes_no=${messageVO.mes_no}" class="btn btn-primary button">修改留言</a>
							 	</c:if>
								<c:if test="${memberVO.mem_id.equals('admin')}">						 
									 <br><br><a href="<%=request.getContextPath()%>/message/delete?mes_no=${messageVO.mes_no}" class="btn btn-primary button">刪除留言</a>
								</c:if>
						</div>
						<div class="col-xs-12 col-sm-9 divright">
						     <h4>${messageVO.memberVO.mem_id}於${messageVO.mes_date}發布:</h4>
						     <h4>${messageVO.mes_title}</h4>
						     <h4>${messageVO.mes_text}</h4>
						     ================================
								<div>
									<c:forEach var="replymessageVO" items="${messageVO.replymessages}">
										<div class="col-xs-12 col-sm-9 divright">
										     <h4>${replymessageVO.memberVO.mem_id}於${replymessageVO.rep_date}回復說:
										     	<c:if test="${memberVO.mem_id == replymessageVO.memberVO.mem_id or memberVO.mem_id.equals('admin')}">
													<a href="<%=request.getContextPath()%>/replymessage/getupdate?rep_no=${replymessageVO.rep_no}" class="btn btn-primary button">修改留言</a>
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
																		<button type="button" class="btn btn-default" data-dismiss="modal">關閉</button>
																		<a href="<%=request.getContextPath()%>/replymessage/delete?rep_no=${replymessageVO.rep_no}" class="btn btn-primary" role="button">確認</a>
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
			<%@ include file="page2.file" %>
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
			    data: "mes_title=" + $('#mes_title').val() + "&" + "mes_text=" + $('#mes_text').val(),
				cache: false,
				success: function(jsonData){
					var returndata = eval(jsonData);
					if(returndata.result == "nologin"){
						alert("請先登入會員")
						url="<%=request.getContextPath()%>/member/loginMember"
						window.location.replace(url);
					}else if(returndata.result == "empty"){
						alert("標題內容請勿空白")
<%-- 						url="<%=request.getContextPath()%>/message/listAllMessage.jsp" --%>
// 						window.location.replace(url);
					}else{
						toastr.success("留言新增成功");
						$('.closeAddMessage').click();				
						window.location.reload(); 	
					}
				
				},
// 			    error: function(xhr, ajaxOptions, thrownError)
// 			    { 
// 			    	alert("error")
// 			    }
		 });
	});
	
});
</script>