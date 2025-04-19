<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section style="display: block;">
	<div class="section-container">
		<h1 class="h1">SEARCH</h1>
		<div class="search-box" style="margin: 1rem 0">
			<form method="get">
				<label for="Search"> 
					<input type="text" id="Search" placeholder="travel" name="query"> <i class="fas fa-times"></i> <i
					class="fas fa-search"></i>
				</label>
			</form>
		</div>
		<div>
			<div class="blog-grid-home" style="border: none;">
				<!-- Loop through blogs -->
				<c:forEach var="blog" items="${searchResults}">
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
		</div>
	</div>
</section>
<%@ include file="partials/footer.jsp"%>