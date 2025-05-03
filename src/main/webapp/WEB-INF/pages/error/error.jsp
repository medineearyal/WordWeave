<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../client/partials/header.jsp" %>
<div class="error-container">
        <div class="error-code">${statusCode}</div>

        <c:choose>
            <c:when test="${statusCode == 404}">
                <div class="error-title">Page Not Found</div>
                <div class="error-message">The page you are looking for might have been removed or doesn't exist.</div>
            </c:when>
            <c:when test="${statusCode == 500}">
                <div class="error-title">Server Error</div>
                <div class="error-message">An unexpected error occurred. Please try again later.</div>
            </c:when>
            <c:otherwise>
                <div class="error-title">Oops! Something went wrong</div>
                <div class="error-message">
                    <p>Error code: ${statusCode}</p>
                    <p>Requested URI: ${requestUri}</p>
                    <c:if test="${not empty exception}">
                        <p>Message: ${exception.message}</p>
                    </c:if>
                </div>
            </c:otherwise>
        </c:choose>

        <a href="/WordWeave" class="back-link">Go to Home</a>
    </div>

<%@ include file="../client/partials/footer.jsp" %>
