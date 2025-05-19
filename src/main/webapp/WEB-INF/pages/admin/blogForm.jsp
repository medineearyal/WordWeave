<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="partials/header.jsp"%>

<div class="main-content container">
	<%@ include file="partials/user_nav.jsp"%>

	<h2 class="form-heading">${actionText} Blog</h2>

	<form method="post" class="user-form" enctype="multipart/form-data" novalidate>
		<input type="hidden" name="action" value="${actionText == 'Edit' ? 'edit' : 'create'}">
		<c:if test="${actionText == 'Edit'}">
			<input type="hidden" name="id" value="${blog.blogId}">
		</c:if>

		<div class="form-group">
			<label for="title">Title</label>
			<div class="input-wrapper">
				<i class="fas fa-heading"></i>
				<input type="text" id="title" name="title" value="${blog.title}" placeholder="Blog Title" required>
			</div>
		</div>

		<div class="form-group">
			<label for="content">Content</label>
			<div class="input-wrapper textarea-wrapper">
				<i class="fas fa-align-left"></i>
				<textarea id="content" name="content" rows="6" placeholder="Blog Content" required>${blog.content}</textarea>
			</div>
		</div>		

		<div class="form-group">
			<label for="image">Image</label>
			<div class="input-wrapper">
				<i class="fas fa-image"></i>
				<input type="file" id="image" name="image">
			</div>
			<c:if test="${blog.image != null}">
				<p style="margin-top: 8px;">Current image: ${blog.image}</p>
			</c:if>
		</div>

		<div class="form-group" <c:if test="${role == 'user'}">style="display:none;"</c:if>>
			<label for="author_id">Author</label>
			<div class="input-wrapper">
				<i class="fas fa-user"></i>
				<select id="author_id" name="author_id" required>
					<c:forEach var="u" items="${users}">
						<option value="${u.user_id}"
							<c:if test="${(not empty blog.authorId and blog.authorId == u.user_id) or (empty blog.authorId and user.user_id == u.user_id)}">selected</c:if>>
							${u.fullname} (${u.username})
						</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div class="form-group">
			<label>Select Categories <i class="fas fa-tags"></i></label>
			<div class="input-wrapper">
				<div class="checkbox-group" style="padding-left: 0; padding-top: 6px;">
					<c:forEach var="category" items="${categories}">
						<label>
							<c:set var="isChecked" value="false" />
							<c:if test="${not empty blog.categories}">
								<c:forEach var="selectedCat" items="${blog.categories}">
									<c:if test="${selectedCat.category_id == category.category_id}">
										<c:set var="isChecked" value="true" />
									</c:if>
								</c:forEach>
							</c:if>
							<input type="checkbox" name="categories" value="${category.category_id}"
								<c:if test="${isChecked}">checked</c:if> />
							${category.name}
						</label>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="form-group">
			<label for="publish_date">Publish Date</label>
			<div class="input-wrapper">
				<i class="fas fa-calendar-alt"></i>
				<input type="date" id="publish_date" name="publish_date" value="${blog.publishDate}" required>
			</div>
		</div>

		<div class="form-actions">
			<button type="submit" class="btn-submit">${actionText} Blog</button>
		</div>
	</form>
</div>

<%@ include file="partials/footer.jsp"%>