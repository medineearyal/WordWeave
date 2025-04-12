<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="content-1">
	<div class="main-article">
		<img src="${pageContext.request.contextPath}${blog.image}" class="main-image">
		<h3>
			${blog.title} <i class="fas fa-heart"></i>
		</h3>
		<h6>${blog.publishDate} - ${blog.authorName}</h6>
		<p class="text">${blog.content} Read More...</p>
	</div>
</div>