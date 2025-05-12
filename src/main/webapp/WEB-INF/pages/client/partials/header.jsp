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
<link
	href="https://fonts.googleapis.com/css2?family=Merriweather:wght@400;700&family=Open+Sans:wght@400;600&display=swap"
	rel="stylesheet">


<link rel="stylesheet" href="/WordWeave/css/main.css">
<link rel="stylesheet" href="/WordWeave/css/footer.css">
<link rel="stylesheet" href="/WordWeave/css/header.css">

</head>
<body>
	<header>
    <nav>
        <a href="/WordWeave"> 
            <img src="/WordWeave/images/logo.png" class="nav-logo">
        </a>

        <div class="navigation">
            <a href="/WordWeave/search" class="search ${servletPath == '/search' ? 'active' : ''}">
                <i class="fas fa-search"></i>
            </a>
            <a href="/WordWeave/" class="link ${servletPath == '/home' or servletPath == '/' ? 'active' : ''}">
                Home
            </a>
            <a href="/WordWeave/blogs" class="link ${servletPath == '/blogs' ? 'active' : ''}">
                Blogs
            </a>
            <a href="/WordWeave/about" class="link ${servletPath == '/about' ? 'active' : ''}">
                About Us
            </a>
            <a href="/WordWeave/contact" class="link ${servletPath == '/contact' ? 'active' : ''}">
                Contact Us
            </a>

            <%
            String username = (String) request.getSession(false).getAttribute("username");
            if (username != null) {
            %>
            <a href="/WordWeave/favourites"> 
                <i class="fas fa-heart favorite-icon favorite"></i>
            </a>

            <!-- User profile and dropdown -->
            <div class="user-dropdown">
                <div class="user-nav-profile">
                    <img src="${pageContext.request.contextPath}${user.profile_picture}" alt="User Profile Picture" />
                    <strong><%= username %></strong>
                    <i class="fa-solid fa-circle-chevron-down"></i>
                </div>
                <div class="dropdown-menu">
                    <a href="/WordWeave/admin/dashboard" class="link">Dashboard</a>
                    <a href="/WordWeave/logout" class="link">Logout</a>
                </div>
            </div>
            <%
            } else {
            %>
            <a href="/WordWeave/login" class="link ${servletPath == '/login' ? 'active' : ''}">
                <i class="fa-solid fa-user"></i> Login
            </a>
            <%
            }
            %>
        </div>
    </nav>
</header>

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