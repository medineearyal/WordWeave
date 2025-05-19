<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<div style="display: flex; justify-content: space-between; align-items: center;">
		<h2>Users</h2>
		<a href="/wordweave/admin/users?action=create"
			style="background-color: #4d1f26; color: white; padding: 10px 15px; border-radius: 8px; text-decoration: none; font-weight: bold;">
			+ Create User </a>
	</div>

	<table>
		<thead>
			<tr>
				<th>SN</th>
				<th>ID</th>
				<th>Full Name</th>
				<th>Email</th>
				<th>Role</th>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}" varStatus="loop">
				<tr>
					<td>${loop.index + 1}</td>
					<td>${user.user_id}</td>
					<td>${user.fullname}</td>
					<td>${user.email}</td>
					<td>
						<c:forEach var="role" items="${roles}">
							<c:if test="${user.role_id == role.role_id}">
								<span>${role.name}</span>
							</c:if>
						</c:forEach>
					</td>
					<td>
						<a href="/wordweave/admin/users?action=edit&id=${user.user_id}"
							style="margin-right: 10px;">
							<i class="fas fa-edit" style="color: #4d1f26;"></i>
						</a>
						<a href="/wordweave/admin/users?action=delete&id=${user.user_id}"
							onclick="return confirm('Are you sure you want to delete this user?')">
							<i class="fas fa-trash-alt" style="color: red;"></i>
						</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

<%@ include file="partials/footer.jsp"%>
