<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section style="display: block;">
	<h1 style="text-align: center;">LOGIN</h1>

	<div class="main-container">
		<form class="login-form" method="post">
			<label for="Username"> <i class="fas fa-user"></i> <input
				type="text" id="Username" name="username" placeholder="Username">
			</label> <label for="password"> <i class="fas fa-lock"></i> <input
				type="password" id="password" name="password" placeholder="Password">
			</label>

			<button id="register">Login Now</button>

			<div class="register-container">
				<div class="register">
					<div class="register-text">
						<p class="register-txt">OR</p>
					</div>
					<div class="register-link">
						<strong> No Account? </strong><a href="/WordWeave/register"
							class="login">Register.</a>
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