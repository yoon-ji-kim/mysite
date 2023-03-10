<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	pageContext.setAttribute("newline", "\n");
%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="board" class="board-form">
				<table class="tbl-ex">
					<tr>
						<th colspan="2">글보기</th>
					</tr>
					<tr>
						<td class="label">제목</td>
						<td>${vo.title }</td>
					</tr>
					<tr>
						<td class="label">내용</td>
						<td>
							<div class="view-content">
								${fn:replace(vo.contents,newline, "<br>")}
							</div>
						</td>
					</tr>
				</table>
					
				<div class="bottom">
					<a href="${pageContext.request.contextPath }/board?page=${param.page}&keyword=${param.keyword }">글목록</a>
					<c:if test="${not empty authUser }">
						<c:if test="${vo.userNo == authUser.no }">
							<a href="${pageContext.request.contextPath }/board/update?no=${vo.no}&page=${param.page}&keyword=${param.keyword}">글수정</a>
						</c:if>	
							<form method="post" action="${pageContext.request.contextPath }/board/writeform?page=${param.page}&keyword=${param.keyword}">
								<input type = "hidden" name = "groupNo" value="${vo.groupNo }">
								<input type = "hidden" name = "orderNo" value="${vo.orderNo }">
								<input type = "hidden" name = "depth" value="${vo.depth }">
								<input type= "submit" value="댓글">
							</form>
					</c:if>
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>