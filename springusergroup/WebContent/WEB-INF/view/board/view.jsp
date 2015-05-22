<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>	
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Spring User Group</title>
	<!-- Framework CSS -->
    <link rel="stylesheet" href="../blueprint/screen.css" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="../blueprint/print.css" type="text/css" media="print">
    <!--[if lt IE 8]><link rel="stylesheet" href="../../blueprint/ie.css" type="text/css" media="screen, projection"><![endif]-->
</head>
<body>  

<div class="container">
	<h2>�Խñ� ������</h2>
	<hr />
	<div class="prepend-6 span-8 append-8 last">
		<h4><A href='../board/list'>�Խ��� ����Ʈ �̵�</A></h4>
	</div>
	<hr />
	<div class="span-14 append-10 last">
		<table>
			<H4>�Խñ� �б�</H4>
			[����] ${bbsItem.title} <BR>
			[�ۼ���] ${bbsItem.writer} 
			[�ۼ��Ͻ�] ${bbsItem.date} ${bbsItem.time} <BR>
			------------------------------------------------------------ <BR>
			${bbsItem.content}


		
		</table>
	</div>
	
</div>




</body>
</html>