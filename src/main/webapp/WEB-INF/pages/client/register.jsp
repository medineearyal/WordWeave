<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="partials/header.jsp"%>

<section class="section-container">
	<h1 class="h1">REGISTER</h1>

	<div class="main-container">
		<form class="login-form" action="" method="post" enctype="multipart/form-data">

			<c:if test="${not empty error}">
				<div class="error-message" style="color: red; font-weight: bold; margin-bottom: 10px;">
					${error}
				</div>
			</c:if>

			<label for="Fname"> 
				<i class="fas fa-edit"></i> 
				<input type="text" id="Fname" name="fullname" placeholder="Full Name" 
					value="${fullname != null ? fullname : ''}">
			</label>
			<c:if test="${not empty error_fullname}">
				<div style="color: red;">${error_fullname}</div>
			</c:if>

			<label for="Email"> 
				<i class="fas fa-envelope"></i> 
				<input type="text" id="Email" name="email" placeholder="Email" 
					value="${email != null ? email : ''}">
			</label>
			<c:if test="${not empty error_email}">
				<div style="color: red;">${error_email}</div>
			</c:if>

			<label for="Username"> 
				<i class="fas fa-user"></i> 
				<input type="text" id="Username" name="username" placeholder="Username" 
					value="${username != null ? username : ''}">
			</label>
			<c:if test="${not empty error_username}">
				<div style="color: red;">${error_username}</div>
			</c:if>

			<label for="password"> 
				<i class="fas fa-lock"></i> 
				<input type="password" id="password" name="password" placeholder="Password">
			</label>
			<c:if test="${not empty error_password}">
				<div style="color: red;">${error_password}</div>
			</c:if>

			<label for="CPassword"> 
				<i class="fas fa-lock"></i> 
				<input type="password" id="Cpassword" name="cPassword" placeholder="Confirm Password">
			</label>
			<c:if test="${not empty error_cpassword}">
				<div style="color: red;">${error_cpassword}</div>
			</c:if>

			<label for="profilePicture"> 
				<i class="fas fa-image"></i> 
				<input type="file" id="profilePicture" name="profile_picture" accept="image/*">
			</label>

			<button id="register" type="submit">Register Now</button>

			<div class="register-container">
				<div class="register">
					<div class="register-text">
						<p class="register-txt">Or</p>
					</div>
					<div class="register-link">
						<strong> Already have an Account?</strong>
						<a href="/WordWeave/login" class="login">Login</a>
					</div>
				</div>
			</div>
		</form>

		<div class="image">
			<img src="/WordWeave/images/Person.png" alt="image">
		</div>
	</div>
</section>

<%@ include file="partials/footer.jsp"%>
