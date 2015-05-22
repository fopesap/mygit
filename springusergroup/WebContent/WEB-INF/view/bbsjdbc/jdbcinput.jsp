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
	<hr />	
	<div class="prepend-6 span-8 append-8 last">
		<h4><A href='../bbsjdbc/list'>JDBC �Խñ� ������� �̵�.</A></h4>
		<h4><A href='../user/list'>ȸ�� ������� �̵�</A></h4>
		
	</div>
	
	<div class="prepend-6 span-8 append-8 last">
		
	</div>
	<hr />
	<div class="span-14 append-10 last">

	
	<form:form modelAttribute="board" >
		<fieldset>
			<p>
				<form:label path="title">����:</form:label><br/>
				<form:input path="title" size="20" maxlength="50" /> 
			</p>
			<p>
				<form:label path="content">����</form:label><br/>
				<form:textarea path="content" size="150" maxlength="500" />
			</p>
			<tr>
				<td><input type="submit" value="����" /> </td>
			</tr>
			<tr>
				<td><input type="reset" value="���" /> </td>
			</tr>
		</fieldset>
	</form:form>	
	
	</div>
	
</div>




</body>
</html>