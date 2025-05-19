<%@page import="com.wordweave.models.UserModel"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="partials/header.jsp"%>
<section class="section-container">
	<h1 class="h1">Contact Us</h1>

	<div class="main-container">
		<form class="message-form" method="POST" novalidate>
			<label for="Fname"> 
				<i class="fas fa-edit"></i> 
				<input type="text" id="Fname" placeholder="Full Name" name="fullName" 
					value="<%= request.getAttribute("fullName") != null ? request.getAttribute("fullName") : "" %>">
			</label> 
			<% if(request.getAttribute("error_fullName") != null) { %>
				<span class="form-error"><%= request.getAttribute("error_fullName") %></span>
			<% } %>
			
			<label for="Email"> 
				<i class="fas fa-envelope"></i> 
				<input type="email" id="Email" placeholder="Email" name="email" 
					value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>">
			</label>
			<% if(request.getAttribute("error_email") != null) { %>
				<span class="form-error"><%= request.getAttribute("error_email") %></span>
			<% } %>
			
			<label for="author">
    			<select id="author" name="author">
					<option value="">Select Author</option>
					<%
						List<UserModel> authors = (List<UserModel>) request.getAttribute("authors");
						if (authors != null) {
							for (UserModel author : authors) {
					%>
						<option value="<%=author.getUser_id()%>"><%=author.getFullname()%></option>
					<%
						}
					}
					%>
				</select>
			</label>
			<% if(request.getAttribute("error_author") != null) { %>
				<span class="form-error"><%= request.getAttribute("error_author") %></span>
			<% } %>
			
			<label for="message"> 
				<textarea id="message" placeholder="Write your message here!!" name="message"><%= request.getAttribute("message") != null ? request.getAttribute("message") : "" %></textarea>
			</label>
			<% if(request.getAttribute("error_message") != null) { %>
				<span class="form-error"><%= request.getAttribute("error_message") %></span>
			<% } %>
			
			<button id="register">Send Message</button>
		</form>
		
		<div class="image">
			<img src="/wordweave/images/Person.png" alt="image">
		</div>
	</div>
</section>
<%@ include file="partials/footer.jsp"%>
