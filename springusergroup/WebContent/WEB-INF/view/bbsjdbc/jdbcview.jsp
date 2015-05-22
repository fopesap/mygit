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
	<h2>게시글 내용임</h2>
	<hr />
	<div class="prepend-6 span-8 append-8 last">
		<h4><A href='../bbsjdbc/list'>JDBC 게시판 리스트 이동</A></h4>
	</div>
	<hr />
	<div class="span-14 append-10 last">
		<table>
			<H4>게시글 읽기</H4>
			[제목] ${board.title} <BR>
			[작성자] ${board.writer} 
			[작성일시] ${board.date} ${board.time} <BR>
			------------------------------------------------------------ <BR>
			${board.content}


		
		</table>
	</div>
	<hr />
	<div class="prepend-6 span-8 append-8 last">
		<a href="../bbsjdbc/edit/${board.seqNo}">[수정]</a>
		<a href="../bbsjdbc/delete/${board.seqNo}">[삭제]</a>	
	</div>
	<hr />
	
</div>




</body>
</html>