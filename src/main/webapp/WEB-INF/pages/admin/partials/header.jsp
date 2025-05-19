<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	
<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Wordweave Dashboard - </title>
<!-- Add Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link rel="stylesheet" href="/wordweave/css/admin/admin.css" />

<link rel="icon" type="image/png" href="/wordweave/images/favicon/favicon-96x96.png"
	sizes="96x96" />
<link rel="icon" type="image/svg+xml" href="/wordweave/images/favicon/favicon.svg" />
<link rel="shortcut icon" href="/wordweave/images/favicon/favicon.ico" />
<link rel="apple-touch-icon" sizes="180x180"
	href="/wordweave/images/favicon/apple-touch-icon.png" />
<meta name="apple-mobile-web-app-title" content="Word Weave" />
<link rel="manifest" href="/wordweave/images/favicon/site.webmanifest" />
</head>

<body>
	<div class="sidebar">
		<div class="logo-section">
			<a href="/wordweave/">
				<img src="/wordweave/images/logo.png" alt="Logo"> <span>WORDWEAVE</span>
			</a>
		</div>
		<ul class="menu">
			<li><a href="/wordweave/admin/dashboard">Dashboard</a></li>
			
			<c:if test="${role != null and role.toLowerCase().equals('admin')}">
				<li><a href="/wordweave/admin/users">Users</a></li>
			</c:if>

			<li><a href="/wordweave/admin/blogs">Blogs</a></li>
			<li><a href="/wordweave/admin/categories">Categories</a></li>
			
			<c:if test="${role != null and role.toLowerCase().equals('user')}">
				<li><a href="/wordweave/admin/contacts">Contacts</a></li>
			</c:if>
			
		</ul>
		<ul class="others">
			<li><a href="/wordweave/admin/accounts">Accounts</a></li>
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
		
		<c:if test="${not empty sessionScope.success}">
			<div class="notification success">${sessionScope.success}</div>
			<c:remove var="success" scope="session" />
		</c:if>

		<c:if test="${not empty sessionScope.error}">
			<div class="notification error">${sessionScope.error}</div>
			<c:remove var="error" scope="session" />
		</c:if>
	</div>