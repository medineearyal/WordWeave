<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp"%>

<div class="main-content container">
	<%@ include file="partials/user_nav.jsp"%>

	<h2 class="form-heading">${actionText} User</h2>

	<form method="post" class="user-form" enctype="multipart/form-data"
		<c:if test="${actionText == 'Update'}">action="/wordweave/admin/users?action=edit&from=accounts&id=${editUser.user_id}"</c:if>
		novalidate>

		<input type="hidden" name="action" value="${actionText == 'Edit' ? 'edit' : 'create'}">
		<c:if test="${actionText == 'Edit'}">
			<input type="hidden" name="id" value="${editUser.user_id}">
		</c:if>

		<div class="form-group">
			<label for="fullname">Full Name</label>
			<div class="input-wrapper">
				<i class="fas fa-user"></i>
				<input type="text" id="fullname" name="fullname"
					   value="${editUser.fullname}" placeholder="Full Name" required>
			</div>
			<c:if test="${not empty fullnameError}">
				<span class="form-error">${fullnameError}</span>
			</c:if>
		</div>

		<div class="form-group">
			<label for="username">Username</label>
			<div class="input-wrapper">
				<i class="fas fa-user-tag"></i>
				<input type="text" id="username" name="username"
					   value="${editUser.username}" placeholder="Username" required>
			</div>
			<c:if test="${not empty usernameError}">
				<span class="form-error">${usernameError}</span>
			</c:if>
		</div>

		<div class="form-group">
			<label for="email">Email</label>
			<div class="input-wrapper">
				<i class="fas fa-envelope"></i>
				<input type="email" id="email" name="email"
					   value="${editUser.email}" placeholder="Email" required>
			</div>
			<c:if test="${not empty emailError}">
				<span class="form-error">${emailError}</span>
			</c:if>
		</div>

		<div class="form-group">
			<label for="profile_picture">Profile Picture</label>
			<div class="input-wrapper">
				<i class="fas fa-image"></i>
				<input type="file" id="profile_picture" name="profile_picture" onchange="previewImage(event)">
			</div>
			<c:if test="${not empty profilePictureError}">
				<span class="form-error">${profilePictureError}</span>
			</c:if>

			<c:choose>
				<c:when test="${not empty editUser.profile_picture}">
					<input type="hidden" name="existing_profile_picture" value="${editUser.profile_picture}" />
					<img id="profileImage"
						 src="/wordweave/${editUser.profile_picture}"
						 alt="Profile Picture"
						 style="max-height: 120px; border-radius: 8px; margin-top: 10px;">
				</c:when>
				<c:otherwise>
					<img id="profileImage"
						 src="/wordweave/assets/images/default_profile.png"
						 alt="Preview"
						 style="max-height: 120px; border-radius: 8px; display: none;">
				</c:otherwise>
			</c:choose>
		</div>

		<c:if test="${actionText == 'Create' or actionText == 'Update'}">
			<div class="form-group">
				<label for="password">Password</label>
				<div class="input-wrapper">
					<i class="fas fa-lock"></i>
					<input type="password" id="password" name="password" placeholder="Password" required>
				</div>
				<c:if test="${not empty passwordError}">
					<span class="form-error">${passwordError}</span>
				</c:if>
			</div>
		</c:if>

		<c:choose>
			<c:when test="${actionText != 'Update'}">
				<div class="form-group">
					<label for="role_id">Role</label>
					<div class="input-wrapper">
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
					</div>
					<c:if test="${not empty roleError}">
						<span class="form-error">${roleError}</span>
					</c:if>
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label>Role</label>
					<div class="input-wrapper">
						<i class="fas fa-user-shield"></i>
						<select name="role_id" id="role_id">
							<option value="${roleObject.role_id}" selected>
								${roleObject.name}
							</option>
						</select>
					</div>
				</div>
			</c:otherwise>
		</c:choose>

		<div class="form-actions">
			<button type="submit" class="btn-submit">${actionText} User</button>
		</div>
	</form>
</div>

<%@ include file="partials/footer.jsp"%>