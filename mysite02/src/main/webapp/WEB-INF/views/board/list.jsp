<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }/board?page=${page}" method="post">					
					<input type="hidden"  name="a" value="keyword">
					<input type="text" id="kwd" name="kwd" value="${keyword }">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list }" var="vo" varStatus="status">
						<tr>
							<td>${status.index }</td>
							<td style="text-align: left; padding-left: ${vo.depth*15}px">
								<c:if test="${vo.depth >0 }">
								<img src="${pageContext.request.contextPath }/assets/images/reply.png">
								</c:if>
								<a href="${pageContext.request.contextPath }/board?a=search&no=${vo.no}">
									${vo.title }
								</a>
							</td>
							<td>${vo.name }</td>
							<td>${vo.hit }</td>
							<td>${vo.regDate }</td>
							<td>
								<c:if test="${authUser.no == vo.userNo}">
									<a href="${pageContext.request.contextPath }/board?a=delete&no=${vo.no}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>					
					</c:forEach>
				</table>
				
				<c:if test="${empty keyword }">
					<!-- pager 추가 -->
					<div class="pager">
							<ul>
								<c:choose>
									<c:when test="${page == 1 }">
										<li class="disabled">◀</li>								
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/board?page=${page-1}">◀</a></li>
									</c:otherwise>
								</c:choose>
								<c:forEach var="page" begin="1" end="${totalPage}" step="1">
									<li class="selected"><a href="${pageContext.request.contextPath }/board?page=${page}">${page }</a></li>
								</c:forEach>
								<c:choose>
									<c:when test="${page == totalPage }">
										<li class="disabled">▶</li>	
									</c:when>
									<c:otherwise>
										<li><a href="${pageContext.request.contextPath }/board?page=${page+1}">▶</a></li>									
									</c:otherwise>
								</c:choose>
							</ul>
					</div>	
				</c:if>
				<c:if test="${not empty authUser}">
					<div class="bottom">
						<a href="${pageContext.request.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
					</div>				
				</c:if>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp" />
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>