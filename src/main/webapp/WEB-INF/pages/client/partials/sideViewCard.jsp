<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="article">
    <img src="${pageContext.request.contextPath}${article.image}" class="article-image">
    <h3>${article.title}</h3>
    <h6>${article.publishDate} ${article.authorName}</h6>
</div>