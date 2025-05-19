<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ include file="partials/header.jsp"%>
<section class="section-container">
	<div class="breadcrumb-container">
		<hr class="divider">

		<div class="breadcrumb">
		    <a href="${pageContext.request.contextPath}/">
		        <i class="fas fa-home"></i> Home
		    </a>
		    <i class="fas fa-angle-right separator"></i>
		
		    <a href="${pageContext.request.contextPath}/blogs">
		        <i class="fas fa-blog"></i> Blogs
		    </a>
		    <i class="fas fa-angle-right separator"></i>
		
		    <span><i class="fas fa-file-alt"></i> ${blog.title}</span>
		</div>
		
		<hr class="divider">
	</div>
	<h1 class="h1">${blog.title}</h1>
	<div class="" style="padding: 0;">
		<div class="blog">
			<div style="width: 100%;">
				<div class="detail-container">
					<img src="${pageContext.request.contextPath}${blog.image}"
						class="main-image">
					<h3 class="text-left">${blog.title}</h3>
					<h6 class="text-left">${blog.publishDate}-${blog.authorName} Views: ${blog.views}</h6>
					<p class="text" style="text-align: justify;">${blog.content}</p>
				</div>
				<div class="author">
					<img src="${pageContext.request.contextPath}${blog.authorImage}"
						class="author-image">
					<div class="author-description">
						<div class="author-name">
							<h2>${blog.authorName}</h2>
						</div>
						<div class="author-name">
							<h4 style="color: var(--black-50);">Author</h4>
						</div>
						<div class="author-name">
							<h6>${blog.authorBio}</h6>
						</div>

					</div>
				</div>
			</div>

			<div class="side-content">
				<h2 class="h2 text-left">
					Similar Reads
					</h1>
					<div style="display: flex; gap: 1rem; flex-direction: column;">
						<c:forEach var="similarBlog" items="${similarBlogs}">
							<c:if test="${blog.blogId != similarBlog.blogId}">
								<div class="clickableCard article"
									data-id="${similarBlog.blogId}">
									<img
										src="${pageContext.request.contextPath}${similarBlog.image}"
										class="article-image">
									<h3 class="text-left" style="width: 100%;">${similarBlog.title}</h3>
									<h6 class="text-left" style="width: 100%;">${similarBlog.publishDate}
										${similarBlog.authorName}</h6>
								</div>
							</c:if>
						</c:forEach>
					</div>
			</div>
		</div>



	</div>
</section>
<%@ include file="partials/footer.jsp"%>