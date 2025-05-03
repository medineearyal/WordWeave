<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="partials/header.jsp"%>
<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<h2>${actionText}Blog</h2>

	<form action="" method="post" class="login-form"
		enctype="multipart/form-data">
		<input type="hidden" name="action"
			value="${actionText == 'Edit' ? 'edit' : 'create'}">
		<c:if test="${actionText == 'Edit'}">
			<input type="hidden" name="id" value="${blog.blogId}">
		</c:if>

		<label for="title"> <i class="fas fa-heading"></i> <input
			type="text" id="title" name="title" value="${blog.title}"
			placeholder="Blog Title" required>
		</label> <label for="content"> <i class="fas fa-align-left"></i> <textarea
				id="content" name="content" rows="6" placeholder="Blog Content"
				required>${blog.content}</textarea>
		</label> <label for="image"> <i class="fas fa-image"></i> <input
			type="file" id="image" name="image"> <c:if
				test="${blog.image != null}">
				<p>Current image: ${blog.image}</p>
			</c:if>
		</label>

		<label for="author_id" 
		       <c:if test="${role == 'user'}">style="display:none;"</c:if>>
		    <i class="fas fa-user"></i>
		    <select id="author_id" name="author_id" required>
		        <c:forEach var="user" items="${users}">
		            <option value="${user.user_id}"
		                <c:if test="${blog.authorId == user.user_id}">selected</c:if>>
		                ${user.fullname} (${user.username})
		            </option>
		        </c:forEach>
		    </select>
		</label>


		
		<label style="display: block;"> <i class="fas fa-tags"></i>
			Select Categories:
			<div
				style="display: flex; flex-direction: column; gap: 8px; margin-top: 8px;">
				<c:forEach var="category" items="${categories}">
					<label
						style="display: flex; align-items: start; width: fit-content;">
						<c:set var="isChecked" value="false" /> <c:if
							test="${not empty blog.categories}">
							<c:forEach var="selectedCat" items="${blog.categories}">
								<c:if test="${selectedCat.category_id == category.category_id}">
									<c:set var="isChecked" value="true" />
								</c:if>
							</c:forEach>
						</c:if> <input type="checkbox" name="categories"
						value="${category.category_id}"
						<c:if test="${isChecked}">checked</c:if> /> <span
						style="margin-left: 6px;">${category.name}</span>
					</label>
				</c:forEach>
			</div>
		</label> <label for="publish_date"> <i class="fas fa-calendar-alt"></i>
			<input type="date" id="publish_date" name="publish_date"
			value="${blog.publishDate}" required>
		</label>

		<button type="submit">${actionText}Blog</button>
	</form>
</div>
<%@ include file="partials/footer.jsp"%>
