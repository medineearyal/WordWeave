<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp" %>

<div class="main-content">
    <%@ include file="partials/user_nav.jsp" %>

    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h2>Categories</h2>
        <a href="/WordWeave/admin/categories?action=create"
           style="background-color: #4d1f26; color: white; padding: 10px 15px; border-radius: 8px; text-decoration: none; font-weight: bold;">
            + Create Category
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>SN</th>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categories}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${category.category_id}</td>
                    <td>${category.name}</td>
                    <td>
                        <a href="/WordWeave/admin/categories?action=edit&id=${category.category_id}" style="margin-right: 10px;">
                            <i class="fas fa-edit" style="color: #4d1f26;"></i>
                        </a>
                        <a href="/WordWeave/admin/categories?action=delete&id=${category.category_id}"
                           onclick="return confirm('Are you sure you want to delete this category?')">
                            <i class="fas fa-trash-alt" style="color: red;"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="partials/footer.jsp" %>
