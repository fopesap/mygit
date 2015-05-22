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
	<h2>게시글 수정</h2>
	<hr />

	<div class="prepend-6 span-8 append-8 last">
		<b>${currentUser.name}</b>님으로 로그인 되었습니다.<br/>
		게시물 번호는<b>${board.seqNo}</b>번입니다.<br/>
		작성자는<b>${board.writer}</b>입니다.<br/>
	</div>
	<hr />	
	<div class="prepend-6 span-8 append-8 last">
		<h4><A href='../list'>JDBC 게시글 목록으로 이동.</A></h4>
		<h4><A href='../user/list'>회원 목록으로 이동</A></h4>
		
	</div>
	
	<div class="prepend-6 span-8 append-8 last">
		
	</div>
	<hr />
	<div class="span-14 append-10 last">

	
	<form:form modelAttribute="board" >
		<fieldset>
			<p>
				<form:label path="title">제목:</form:label><br/>
				<form:input path="title" size="20" value="" maxlength="50" /> 
			</p>
			<p>
				<form:label path="content">내용</form:label><br/>
				<form:textarea path="content" size="150" maxlength="500" />
			</p>
			<tr>
				<td><input type="submit" value="저장" /> </td>
			</tr>
			<tr>
				<td><input type="reset" value="취소" /> </td>
			</tr>
			<input type="hidden" name="seqNo" value="${board.seqNo}" />
			<input type="hidden" name="writer" value="${board.writer}" />
		</fieldset>
	</form:form>	
	
	</div>
	
</div>




</body>
</html>