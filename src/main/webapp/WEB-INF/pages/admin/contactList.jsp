<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<div style="display: flex; justify-content: space-between; align-items: center;">
		<h2>Users</h2>
		<a href="/WordWeave/admin/contacts/?action=create"
			style="background-color: #4d1f26; color: white; padding: 10px 15px; border-radius: 8px; text-decoration: none; font-weight: bold;">
			+ Create Contact </a>
	</div>

	<table>
		<thead>
			<tr>
                <th>Sender Name</th>
                <th>Sender Email</th>
                <th>Message Text</th>
                <th>Actions</th>
            </tr>
		</thead>
		<tbody>
			 <c:forEach var="contact" items="${contacts}">
                <tr>
                    <td>${contact.senderName}</td>
                    <td>${contact.senderEmail}</td>
                    <td>${contact.messageText}</td>
                    <td>
                        <a href="/WordWeave/admin/contacts/?action=edit&id=${contact.messageId}">Edit</a> |
                        <a href="/WordWeave/admin/contacts/?action=delete&id=${contact.messageId}" onclick="return confirm('Are you sure?');">Delete</a>
                    </td>
                </tr>
            </c:forEach>
		</tbody>
	</table>

</div>
</body>
</html>
