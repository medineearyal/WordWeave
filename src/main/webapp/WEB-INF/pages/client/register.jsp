<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section style="display: block;">
	<h1 style="text-align: center;">REGISTER</h1>

	<div class="main-container">
		<form class="login-form" action="" method="post" enctype="multipart/form-data">
			<label for="Fname"> <i class="fas fa-edit"></i> 
                <input type="text" id="Fname" name="fullname" placeholder="Full Name" 
                       value="${requestScope.fullname != null ? requestScope.fullname : ''}">
            </label> 
            
            <label for="Email"> <i class="fas fa-envelope"></i> 
                <input type="email" id="Email" name="email" placeholder="Email" 
                       value="${requestScope.email != null ? requestScope.email : ''}">
            </label> 
            
            <label for="Username"> <i class="fas fa-user"></i> 
                <input type="text" id="Username" name="username" placeholder="Username" 
                       value="${requestScope.username != null ? requestScope.username : ''}">
            </label> 
            
            <label for="password"> <i class="fas fa-lock"></i> 
                <input type="password" id="password" name="password" placeholder="Password">
            </label> 
            
            <label for="CPassword"> <i class="fas fa-lock"></i> 
                <input type="password" id="Cpassword" name="cPassword" placeholder=" Confirm Password">
            </label>
            
            <!-- Profile Picture Input -->
            <label for="profilePicture"> <i class="fas fa-image"></i> 
                <input type="file" id="profilePicture" name="profile_picture" accept="image/*">
            </label>

			<button id="register" type="submit">Register Now</button>
			<div class="register-container">
				<div class="register">
					<div class="register-text">
						<p class="register-txt">Or</p>
					</div>
					<div class="register-link">
						<strong> Already have an Account?</strong><a href="/WordWeave/login"
							class="login">Login</a>
					</div>
				</div>
			</div>
		</form>
		<div class="image">
			<img src="./Images/Person.png" alt="image">
		</div>
	</div>
</section>
<%@ include file="partials/footer.jsp"%>
