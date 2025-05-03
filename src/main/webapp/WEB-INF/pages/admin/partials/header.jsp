<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Wordweave Dashboard</title>
<!-- Add Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" href="/WordWeave/css/admin/admin.css" />
</head>

<body>
	<div class="sidebar">
		<div class="logo-section">
			<img src="/WordWeave/images/logo.png" alt="Logo"> <span>WORDWEAVE</span>
		</div>
		<ul class="menu">
			<li><a href="/WordWeave/admin/dashboard">Dashboard</a></li>
			
			<c:if test="${role != null and role.toLowerCase().equals('admin')}">
				<li><a href="/WordWeave/admin/users">Users</a></li>
			</c:if>

			<li><a href="/WordWeave/admin/blogs">Blogs</a></li>
			<li><a href="/WordWeave/admin/categories">Categories</a></li>
			
			<c:if test="${role != null and role.toLowerCase().equals('user')}">
				<li><a href="/WordWeave/admin/contacts">Contacts</a></li>
			</c:if>
			
		</ul>
		<ul class="others">
			<li><a href="/WordWeave/admin/accounts">Accounts</a></li>
		</ul>
	</div>
	<div class="message-status">
		<%
		String successMessage = (String) request.getAttribute("success");
		String errorMessage = (String) request.getAttribute("error");
		if (successMessage != null) {
		%>
		<div class="notification success">
			<%=successMessage%>
		</div>
		<%
		} else if (errorMessage != null) {
		%>
		<div class="notification error">
			<%=errorMessage%>
		</div>
		<%
		}
		%>
	</div>