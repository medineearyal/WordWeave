<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp" %>

<div class="main-content">
    <%@ include file="partials/user_nav.jsp" %>

    <h2>${actionText} User</h2>

    <form method="post" class="login-form" enctype="multipart/form-data">
        <input type="hidden" name="action" value="${actionText == 'Edit' ? 'edit' : 'create'}">
        <c:if test="${actionText == 'Edit'}">
            <input type="hidden" name="id" value="${user.user_id}">
        </c:if>

        <label for="fullname">
            <i class="fas fa-user"></i>
            <input type="text" id="fullname" name="fullname" value="${user.fullname}" placeholder="Full Name" required>
        </label>

        <label for="username">
            <i class="fas fa-user-tag"></i>
            <input type="text" id="username" name="username" value="${user.username}" placeholder="Username" required>
        </label>

        <label for="email">
            <i class="fas fa-envelope"></i>
            <input type="email" id="email" name="email" value="${user.email}" placeholder="Email" required>
        </label>
        
        <label for="email">
            <i class="fas fa-envelope"></i>
            <input type="file" id="profile_picture" name="profile_picture" value="${user.profile_picture}" placeholder="Profile Picture" required>
        </label>

        <label for="password">
            <i class="fas fa-lock"></i>
            <input type="password" id="password" name="password" value="${user.password}" placeholder="Password" required>
        </label>
	
		<c:if test="${not empty profile and not profile.equals('view')}">
			<label for="role_id">
	            <i class="fas fa-user-shield"></i>
	            <select name="role_id" id="role_id" required>
	                <option value="" disabled selected>Select Role</option>
	                <c:forEach var="role" items="${roles}">
	                    <option value="${role.role_id}" 
	                        ${user.role_id == role.role_id ? 'selected' : ''}>
	                        ${role.name}
	                    </option>
	                </c:forEach>
	            </select>
	        </label>
		</c:if>
        

        <button type="submit">${actionText} User</button>
    </form>
</div>
</body>
</html>
