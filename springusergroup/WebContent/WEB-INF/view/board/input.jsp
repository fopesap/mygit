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
	<h2>�Խñ��� �Ẹ��.</h2>
	<hr />

	<div class="prepend-6 span-8 append-8 last">
		<b>${currentUser.name}</b>������ �α��� �Ǿ����ϴ�.
	</div>
	
	
	<div class="prepend-6 span-8 append-8 last">
		
	</div>
	<hr />
	<div class="span-14 append-10 last">
		<table>
			<H4>�Խñ� ����</H4>

			<FORM ACTION=../board/input METHOD=POST>
			    ����: <INPUT TYPE=TEXT NAME=TITLE><BR>
			    <TEXTAREA COLS=80 ROWS=5 NAME=CONTENT></TEXTAREA><BR>
			    <INPUT TYPE=SUBMIT VALUE='����'>
			    <INPUT TYPE=RESET VALUE='���'>
			    <input type=hidden value= >

			</FORM>

		</table>
	</div>
	
</div>




</body>
</html>