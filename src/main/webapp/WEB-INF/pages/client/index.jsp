<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp"%>

<section class="section-container">
	<div>
		<h1 class="h1">Trending Reads</h1>
		<div class="swiper" style="margin: 1rem auto;">
			<div class="swiper-wrapper">
				<c:forEach var="blog" items="${trendingBlogs}">
					<div class="swiper-slide">
						<div class="clickableCard trending-card" data-id="${blog.blogId}"
							style="background-image: url('${pageContext.request.contextPath}${blog.image}');">
							<div class="card-body">
								<div class="card-content">
									<h3 class="card-title">${blog.title}</h3>
									<p class="card-meta">${blog.publishDate}
										<c:if test="${not empty blog.categories}">
        									-
        									<c:forEach var="category" items="${blog.categories}"
												varStatus="status">
												<c:out value="${category.name}" />
												<c:if test="${!status.last}">, </c:if>
											</c:forEach>
										</c:if>
									</p>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<div class="swiper-button-prev"></div>
			<div class="swiper-button-next"></div>
		</div>


		<div>
			<h2 class="h2 text-left">Recents</h2>

			<div class="blogs-wrapper">
				<div class="blog-grid-home">
					<!-- Loop through blogs -->
					<c:forEach var="blog" items="${blogs}">
						<div class="main-article">
							<img src="${pageContext.request.contextPath}${blog.image}"
								class="main-image clickableCard" data-id="${blog.blogId}">
							<div class="card-content">
								<h3 class="blog-title">${blog.title}
									<c:if test="${fn:contains(favoriteBlogIds, blog.blogId)}">
										<i class="fas fa-heart fa-l favorite-icon favorite"
											data-blog-id="${blog.blogId}" data-user="${username}"></i>
									</c:if>
									<c:if test="${not fn:contains(favoriteBlogIds, blog.blogId)}">
										<i class="fas fa-heart fa-l favorite-icon"
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
				<div class="side-content">
					<h3 class="h3 text-left">Most Viewed</h3>
					<div style="display: flex; gap: 1rem; flex-direction: column;">
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
		</div>
	</div>
</section>

<%@ include file="partials/footer.jsp"%>
