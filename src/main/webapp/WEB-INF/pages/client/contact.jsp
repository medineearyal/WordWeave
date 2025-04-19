<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section style="display:block;">
	<h1 style="text-align: center;">Contact Us</h1>

	<div class="main-container">
		<div class="message-form">
			<label for="Fname"> <i class="fas fa-edit"></i> <input
				type="text" id="Fname" placeholder="Full Name">
			</label> <label for="Email"> <i class="fas fa-envelope"></i> <input
				type="email" id="Email" placeholder="Email">
			</label> <label for="Author"> <input type="text" id="author"
				placeholder="Select Author"> <i class="fas fa-edit"></i>
			</label> <label for="message"> <textarea id="message"
					placeholder="Write your message here!!"></textarea>
			</label>
			<button id="register">Register Now</button>
		</div>
		
		<div class="image">
			<img src="/WordWeave/images/Person.png" alt="image">
		</div>
	</div>
</section>
<%@ include file="partials/footer.jsp"%>
