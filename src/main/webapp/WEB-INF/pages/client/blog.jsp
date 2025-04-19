<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>

<section>
	<div class="section-container">
		<h1 class="h1">BLOGS</h1>
		<div style="display: flex;">
			<div class="blog-grid-home">
				<!-- Loop through blogs -->
				<c:forEach var="blog" items="${blogs}">
					<div class="main-article">
						<img src="${pageContext.request.contextPath}${blog.image}"
							class="main-image clickableCard" data-id="${blog.blogId}">
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
				</c:forEach>
			</div>
			<div class="side-content">
				<h3 class="h3">Most Viewed</h3>
				<c:forEach var="blog" items="${mostViewedBlogs}">
					<div class="clickableCard article" data-id="${blog.blogId}">
						<img src="${pageContext.request.contextPath}${blog.image}"
							class="article-image">
						<h3>${blog.title}</h3>
						<span class="blog-meta">${blog.publishDate} -
							${blog.authorName}</span>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</section>
<%@ include file="partials/footer.jsp"%>


