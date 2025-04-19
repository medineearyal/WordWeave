<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Blog</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/swiper@11/swiper-bundle.min.css" />


<link rel="stylesheet" href="/WordWeave/css/footer.css">
<link rel="stylesheet" href="/WordWeave/css/header.css">
<link rel="stylesheet" href="/WordWeave/css/main.css">
</head>
<body>
	<header>
		<nav>
			<img src="/WordWeave/images/logo.png" class="nav-logo">
			<div class="navigation">
				<a href="/WordWeave/search" class="search"> <i
					class="fas fa-search"></i>
				</a> <a href="/WordWeave/home" class="link">Home</a> <a
					href="/WordWeave/blogs" class="link">Blogs</a> <a
					href="/WordWeave/about" class="link">About Us</a> <a
					href="/WordWeave/contact" class="link">Contact Us</a>

				<%
				String username = (String) request.getSession(false).getAttribute("username");
				if (username != null) {
				%>
				<a href="/WordWeave/favourites">
					<i class="fas fa-heart favorite-icon favorite"></i>
				</a>
				
				<a href="/WordWeave/admin/dashboard/" class="link">
					Dashboard
				</a>
				
				<div class="user-nav-profile">
					<i class="fa-solid fa-user" style="margin-right: 8px;"></i><strong>${ username }</strong>
				</div>

				<a href="/WordWeave/logout" class="link">Logout</a>
				<%
				} else {
				%>
				<a href="/WordWeave/login" class="link"><i
					class="fa-solid fa-user"></i> Login</a>
				<%
				}
				%>
			</div>
		</nav>
	</header>