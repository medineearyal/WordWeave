<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<h2>${actionText} User</h2>

	<form method="post" class="login-form" enctype="multipart/form-data">
		<input type="hidden" name="action"
			value="${actionText == 'Edit' ? 'edit' : 'create'}">
		<c:if test="${actionText == 'Edit'}">
			<input type="hidden" name="id" value="${editUser.user_id}">
		</c:if>

		<label for="fullname"> <i class="fas fa-user"></i> <input
			type="text" id="fullname" name="fullname" value="${editUser.fullname}"
			placeholder="Full Name" required>
		</label>
		<c:if test="${not empty fullnameError}">
			<span class="form-error">${fullnameError}</span>
		</c:if>

		<label for="username"> <i class="fas fa-user-tag"></i> <input
			type="text" id="username" name="username" value="${editUser.username}"
			placeholder="Username" required>
		</label>
		<c:if test="${not empty usernameError}">
			<span class="form-error">${usernameError}</span>
		</c:if>

		<label for="email"> <i class="fas fa-envelope"></i> <input
			type="email" id="email" name="email" value="${editUser.email}"
			placeholder="Email" required>
		</label>
		<c:if test="${not empty emailError}">
			<span class="form-error">${emailError}</span>
		</c:if>

		<label for="profile_picture"> <i class="fas fa-image"></i> <input
			type="file" id="profile_picture" name="profile_picture"
			placeholder="Profile Picture">
		</label>
		<c:if test="${not empty profilePictureError}">
			<span class="form-error">${profilePictureError}</span>
		</c:if>
		
		
		<c:choose>
		    <c:when test="${actionText == 'Create' or actionText == 'Update'}">
		        <label for="password">
		            <i class="fas fa-lock"></i>
		            <input
		                type="password"
		                id="password"
		                name="password"
		                value=""
		                placeholder="Password"
		                required>
		        </label>
		    </c:when>
		    <c:otherwise>
		        <label for="password" style="display: none;">
		            <i class="fas fa-lock"></i>
		            <input
		                type="password"
		                id="password"
		                name="password"
		                value=""
		                placeholder="Password">
		        </label>
		    </c:otherwise>
		</c:choose>
		
		<c:if test="${not empty passwordError}">
			<span class="form-error">${passwordError}</span>
		</c:if>
			
		<c:choose>
		    <c:when test="${actionText != 'Update'}">
		        <label for="role_id">
		            <i class="fas fa-user-shield"></i>
		            <select name="role_id" id="role_id" required>
		                <option value="" disabled selected>Select Role</option>
		                <c:forEach var="role" items="${roles}">
		                    <option value="${role.role_id}"
		                        <c:if test="${editUser.role_id == role.role_id}">selected</c:if>>
		                        ${role.name}
		                    </option>
		                </c:forEach>
		            </select>
		        </label>
		    </c:when>
		    <c:otherwise>
		        <label for="role_id">
		            <i class="fas fa-user-shield"></i>
		            <input type="hidden" id="role_id" name="role_id" value="${editUser.role_id}" />
		            <input type="text" value="${role}" readonly disabled />
		        </label>
		    </c:otherwise>
		</c:choose>
		
		<c:if test="${not empty roleError}">
			<span class="form-error">${roleError}</span>
		</c:if>

		<button type="submit">${actionText} User</button>
	</form>
</div>

<%@ include file="partials/footer.jsp"%>