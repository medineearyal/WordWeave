<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp" %>

<div class="main-content container">
    <%@ include file="partials/user_nav.jsp" %>

    <h2 class="form-heading">${actionText} Category</h2>

    <form method="post" class="user-form" novalidate>
        <input type="hidden" name="action" value="${actionText == 'Edit' ? 'edit' : 'create'}">

        <c:if test="${actionText == 'Edit'}">
            <input type="hidden" name="id" value="${category.category_id}">
        </c:if>

        <div class="form-group">
            <label for="name">Category Name <span style="color: red;">*</span></label>
            <div class="input-wrapper">
                <i class="fas fa-tag"></i>
                <input type="text" id="name" name="name"
                       value="${category.name}" placeholder="Category Name" required>
            </div>
            <c:if test="${not empty nameError}">
                <span class="form-error">${nameError}</span>
            </c:if>
        </div>

        <div class="form-actions">
            <button type="submit" class="btn-submit">${actionText} Category</button>
        </div>
    </form>
</div>

<%@ include file="partials/footer.jsp"%>
