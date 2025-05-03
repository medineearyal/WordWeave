<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<div class="view-contact-container">
		<h2>Contact Message Details</h2>

		<c:if test="${not empty contact}">
			<div>
				<strong>Sender Name:</strong> ${contact.senderName}
			</div>
			<div>
				<strong>Sender Email:</strong> ${contact.senderEmail}
			</div>
			<div>
				<strong>Message:</strong> 
				<p class="message">${contact.messageText}</p>
			</div>
			<div>
				<strong>Sent Date:</strong> ${contact.messageDate}
			</div>
		</c:if>

		<c:if test="${empty contact}">
			<p>No contact found!</p>
		</c:if>
	</div>
</div>

<%@ include file="partials/footer.jsp"%>
