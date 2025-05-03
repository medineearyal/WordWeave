<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section class="section-container">
	<h1 class="h1">LOGIN</h1>

	<div class="main-container">
		<form class="login-form" method="post">
			<c:if test="${not empty error}">
				<div class="error-message" style="color: red; font-weight: bold; margin-bottom: 10px;">
					${error}
				</div>
			</c:if>

			<label for="Username"> 
				<i class="fas fa-user"></i> 
				<input type="text" id="Username" name="username" placeholder="Username"
					value="${username != null ? username : ''}">
			</label>
			<c:if test="${not empty error_username}">
				<div class="form-error">${error_username}</div>
			</c:if>

			<label for="password"> 
				<i class="fas fa-lock"></i> 
				<input type="password" id="password" name="password" placeholder="Password">
			</label>
			
			<c:if test="${not empty error_password}">
				<div class="form-error">${error_password}</div>
			</c:if>

			<button id="register">Login Now</button>

			<div class="register-container">
				<div class="register">
					<div class="register-text">
						<p class="register-txt">OR</p>
					</div>
					<div class="register-link">
						<strong>No Account?</strong> 
						<a href="/WordWeave/register" class="login">Register.</a>
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
