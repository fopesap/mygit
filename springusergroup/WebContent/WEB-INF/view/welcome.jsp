<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
	<title>Spring User Group</title>
	<!-- Framework CSS -->
    <link rel="stylesheet" href="blueprint/screen.css" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="blueprint/print.css" type="text/css" media="print">
    <!--[if lt IE 8]><link rel="stylesheet" href="../blueprint/ie.css" type="text/css" media="screen, projection"><![endif]-->
</head>
<body> 

<div class="container">
	<h2>회원 환영</h2>
	<hr />	
	<div class="span-12 append-12 last">
		<div class="notice">
			<b>스프링 사용자 모임의 회원이 되셨습니다. 환영합니다.</b> <br/>
			<a href="login">로그인</a> 해주세요.
			<hr />
			
	<!-- 		

			<h2>회원조회</h2>
			<a href="../edit/${id}">[정보수정]</a>
			<a href="../delete/${id}">[회원삭제]</a>	
			<a href="../list">[돌아가기]</a>

			<table>
			
				<tr>
					<td >이름:</td>
					<td>${user.name}</td>
				</tr>
				<tr>
					<td >사용자이름:</td>
					<td>${user.username}</td>
				</tr>
				<tr>
					<td >타입:</td>
					<td>${user.type.name}</td>
				</tr>
				<tr>
					<td >그룹:</td>
					<td>${user.group.name}</td>
				</tr>
				<tr>
					<td >가입일:</td>
					<td><spring:eval expression="user.created" /></td>
				</tr>
				<tr>
					<td >변경일:</td>
					<td><spring:eval expression="user.modified" /></td>
				</tr> 
				<tr>
					<td >로그인횟수:</td>
					<td><spring:eval expression="user.logins" /></td>
				</tr>
			</table>
		 -->	
			
		</div>
	
	</div>
	
	
	
	
</div>



<div class="container">
	
</div>
</body>
</html>