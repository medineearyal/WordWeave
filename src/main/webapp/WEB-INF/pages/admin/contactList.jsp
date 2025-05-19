<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<div style="display: flex; justify-content: space-between; align-items: center;">
		<h2>Users</h2>
	</div>

	<table>
		<thead>
			<tr>
                <th>Sender Name</th>
                <th>Sender Email</th>
                <th>Message Text</th>
                <th>Sent Date</th>
                <th></th>
            </tr>
		</thead>
		<tbody>
			 <c:forEach var="contact" items="${contacts}">
                <tr>
                    <td>${contact.senderName}</td>
                    <td>${contact.senderEmail}</td>
                    <td>${fn:substring(contact.messageText, 0, 50)}...</td>
                    <td>${contact.messageDate }</td>
                    <td><a href="/wordweave/admin/contacts/?action=view&id=${contact.messageId}"><i class="fa-solid fa-eye"></i></a></td>
                </tr>
            </c:forEach>
		</tbody>
	</table>
</div>
<%@ include file="partials/footer.jsp"%>
