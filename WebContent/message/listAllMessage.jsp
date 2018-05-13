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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
		<title>留言清單</title>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/listAllMessage.css" />
		<!--[if lt IE 9]>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.3/html5shiv.min.js"></script>
			<script src="https://cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.min.js"></script>
		<![endif]-->
	</head>
	<body>
<!-- 		使用JavaBean -->
		<jsp:useBean id="messageSvc" class="com.message.model.MessageService" scope="request" />
		
		<h1 class="text-center">Willis留言板！</h1>
		<div class="container">
			<%@ include file="page1.file" %>
			<div class="title"><a href="<%=request.getContextPath()%>/message/addMessage">我要留言</a>|
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
																		<button type="button" class="btn btn-default button" data-dismiss="modal">關閉</button>
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
		<script src="https://code.jquery.com/jquery.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>