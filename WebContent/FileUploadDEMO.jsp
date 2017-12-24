<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${pageContext.request.contextPath }/member/demo" method="post" enctype="multipart/form-data">
                     <input type="text" name="mem_id" /><br>
                     <input type="text" name="mem_psw" /><br>
					 <input type="file" name="upload" /><br>
                     <input type="submit" value="上傳">
  	</form>
</body>
</html>