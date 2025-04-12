<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ include file="partials/header.jsp" %>

<section>
	<div class="section-container">
		<h1>Trending Reads</h1>

		<div class="blog">
			<div class="main-content">
				<!-- Loop through blogs -->
				<c:forEach var="blog" items="${blogs}">
					<div class="main-article">
					    <img src="${pageContext.request.contextPath}${blog.image}" class="main-image">
					    <h3>${blog.title}</h3>
					    <h6>${blog.publishDate} - ${blog.authorName}</h6>
					    <p>${blog.content}</p>
					</div>
				</c:forEach>
			</div>

			<div class="side-content">
				<h1>Most Viewed</h1>
				<c:forEach var="article" items="${mostViewedArticles}">
					<div class="article">
					    <img src="${pageContext.request.contextPath}${article.image}" class="article-image">
					    <h3>${article.title}</h3>
					    <h6>${article.publishDate} ${article.authorName}</h6>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</section>

<%@ include file="partials/footer.jsp" %>
