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
	<h2>게시판 목록이다.</h2>
	<hr />
	
	<div class="prepend-6 span-8 append-8 last">
		<b>${currentUser.name}</b>님
	</div>
	
	<div class="prepend-6 span-8 append-8 last">
		<h4><A href='../board/input'>게시글쓰기 이동</A></h4>
		<h4><A href='../user/list'>회원 목록으로 이동</A></h4>
		
	</div>
	<hr />
	<div class="span-14 append-10 last">
		<table>
			<tr>
				<th>순번</th>
				<th>제목</th>
				<th>작성자</th>
				<th>작성일자</th>
				<th>작성시각</th>
			</tr>
			
            <c:forEach var="cnt" begin="0" end="${BBS_LIST.listSize - 1}"> 
            <TR>
                <TD>${BBS_LIST.seqNo[cnt]}</TD>
       			<TD><A href='../board/view?seqNo=${BBS_LIST.seqNo[cnt]}'>
       				${BBS_LIST.title[cnt]}</A></TD>
                <TD>${BBS_LIST.writer[cnt]}</TD>
                <TD>${BBS_LIST.date[cnt]}</TD>
                <TD>${BBS_LIST.time[cnt]}</TD>
                
            </TR>
            </c:forEach> 
		
			<tr>
				<td>
					<c:if test="${!BBS_LIST.firstPage}">
						<A href='../board/list?FIRST_SEQ_NO=${BBS_LIST.seqNo[0]}'>◀</A>
					</c:if>
					<c:forEach var="cnt" begin="1" end="${BBS_LIST.pageNum}">
						<A href='../board/list?PAGE_NO=${cnt}'>${cnt}</A>
					</c:forEach>               
			        
			        <c:if test="${!BBS_LIST.lastPage}"> 
			            <A href='../board/list?LAST_SEQ_NO=${BBS_LIST.seqNo[
			                          BBS_LIST.listSize - 1]}'>▶</A>
			        </c:if> 			
				</td>
			</tr>
			
		</table>
	</div>
	
</div>




</body>
</html>