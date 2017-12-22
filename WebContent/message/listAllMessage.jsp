<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.message.model.*"%>
<%
	MessageService messageSvc = new MessageService();
	List<MessageVO> list = messageSvc.getAll();
	pageContext.setAttribute("list",list);
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
		
		<h1 class="text-center">Willis留言板！</h1>

		<div class="container">
			<%@ include file="page1.file" %>
			<div class="title"><a href="">我要留言</a>|<a href="#">登入管理</a>|目前全部有<font color="red">${rowNumber}</font>筆留言</div>
			<div class="title">目前每頁顯示<font color="red">${rowsPerPage}</font>則留言 所以分為<font color="red">${pageNumber}</font>頁 請選擇頁數 現在您在 
				<select onChange="location = this.options[this.selectedIndex].value;"">
					<c:forEach var="num" begin="1" end="<%=pageNumber%>" step="1">	
						<option value="<%=request.getRequestURI()%>?whichPage=${num}" ${num==whichPage? 'selected="selected"':''}>第${num}頁</option>
					</c:forEach>
				</select>
			</div>
			<div class="title2"><lable>搜尋</lable> <input type="text"><button>搜尋</button></div>
				<c:forEach var="messageVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<div class="maindiv">
						<div class="col-xs-12 col-sm-3 divleft">
							 <img class="image" src="https://api.fnkr.net/testimg/60x60/00CED1/FFF/?text=img+placeholder"><br>
							 <a href="#" class="btn btn-default replybtn" role="button">我要回應</a>
						</div>
						<div class="col-xs-12 col-sm-9 divright">
						     <h4>${messageVO.memberVO.mem_id}於${messageVO.mes_date}發布:</h4>
						     <h4>${messageVO.mes_title}</h4>
						     <h4>${messageVO.mes_text}</h4>
						     ================================
								<div>
									<c:forEach var="replymessageVO" items="${messageVO.replymessages}">
										<div class="col-xs-12 col-sm-9 divright">
										     <h4>${replymessageVO.memberVO.mem_id}於${replymessageVO.rep_date}回復說:</h4>
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