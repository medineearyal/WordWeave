<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp" %>

<div class="main-content">
    <%@ include file="partials/user_nav.jsp" %>

    <h2>${actionText} Category</h2>

    <form action="" method="post" class="login-form">
        <input type="hidden" name="action" value="${actionText == 'Edit' ? 'edit' : 'create'}">

        <c:if test="${actionText == 'Edit'}">
            <input type="hidden" name="id" value="${category.category_id}">
        </c:if>

        <label for="name">
            <i class="fas fa-tag"></i>
            <input type="text" id="name" name="name" value="${category.name}" placeholder="Category Name" required>
        </label>

        <button type="submit">${actionText} Category</button>
    </form>
</div>
</body>
</html>
