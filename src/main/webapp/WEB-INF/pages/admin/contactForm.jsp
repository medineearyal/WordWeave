<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="partials/header.jsp" %>

<div class="main-content">
    <%@ include file="partials/user_nav.jsp" %>
 <h2>${contact.messageId == null ? 'Create' : 'Edit'} Contact</h2>
    
    <form method="post" class="login-form">
        <input type="hidden" name="action" value="${contact.messageId == null ? 'create' : 'update'}">
        <input type="hidden" name="messageId" value="${contact.messageId}">
        
         <label for="senderName">
        	<input type="text" id="senderName" name="senderName" placeholder="Sender Name" value="${contact.senderName}" required><br><br>
        </label>
        
        <label for="senderEmail">
        	<input type="email" id="senderEmail" name="senderEmail" placeholder="Sender Email" value="${contact.senderEmail}" required><br><br>
        </label>
        
        <label for="messageText">
        	<textarea id="messageText" name="messageText" placeholder="Mssage Text" rows="4" cols="50" required>${contact.messageText}</textarea><br><br>
        </label>
        
        <button type="submit">${contact.messageId == null ? 'Create Contact' : 'Update Contact'}</button>
    </form>
</body>
</html>