<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section class="section-container">
	<div class="">
		<h1 class="h1">Favourites</h1>

		<div class="blog-grid-home" style="grid-template-columns: repeat(4, 1fr); border: none; padding: 0;">
			<!-- Loop through blogs -->
			<c:forEach var="blog" items="${favoriteBlogs}">
				<div class="main-article">
					<img src="${pageContext.request.contextPath}${blog.image}"
						class="main-image clickableCard" data-id="${blog.blogId}">
					<div class="card-content">
						<h3 class="blog-title">${blog.title}
							<c:if test="${fn:contains(favoriteBlogIds, blog.blogId)}">
								<i class="fas fa-heart favorite-icon favorite"
									data-blog-id="${blog.blogId}" data-user="${username}"></i>
							</c:if>
							<c:if test="${not fn:contains(favoriteBlogIds, blog.blogId)}">
								<i class="fas fa-heart favorite-icon"
									data-blog-id="${blog.blogId}" data-user="${username}"></i>
							</c:if>
						</h3>
						<span class="blog-meta">${blog.publishDate} -
							${blog.authorName}</span>
						<p>${fn:substring(blog.content, 0, 100)}...</p>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</section>
<%@ include file="partials/footer.jsp"%>