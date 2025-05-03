<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<div
		style="display: flex; justify-content: space-between; align-items: center;">
		<h2>Blogs</h2>
		<c:if test="${canCreate}">
			<a href="/WordWeave/admin/blogs?action=create"
				style="background-color: #4d1f26; color: white; padding: 10px 15px; border-radius: 8px; text-decoration: none; font-weight: bold;">
				+ Create Blog </a>
		</c:if>
	</div>

	<table>
		<thead>
			<tr>
				<th>SN</th>
				<th>Title</th>
				<c:if test="${role == 'moderator' }">
					<th>Author Name</th>
				</c:if>
				<th>Publish Date</th>
				<th>Last Updated</th>
				<c:if test="${canApprove}">
					<th>Trending</th>
					<th>Is Approved</th>
				</c:if>
				<c:if test="${role == 'user' }">
					<th>Status</th>
				</c:if>
				<th>Actions</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="blog" items="${blogs}" varStatus="loop">
				<tr>
					<td>${loop.index + 1}</td>
					<td>${blog.title}</td>
					<c:if test="${role == 'moderator' }">
						<td>${blog.authorName}</td>
					</c:if>
					<td>${blog.publishDate}</td>
					<td>${blog.updatedAt}</td>
					<c:if test="${canApprove}">
						<td>
							<a
								href="/WordWeave/admin/blogs?action=toggle-trending&id=${blog.blogId}">
								<button
									style="background-color: ${blog.isTrending ? '#4CAF50' : '#f44336'}; color: white; padding: 5px 10px; border-radius: 5px; width: 100%;">
									${blog.isTrending ? 'Remove from Trending' : 'Add to Trending'}
								</button>
							</a>
						</td>
					
						<td>
							<a href="/WordWeave/admin/blogs?action=toggle-draft&id=${blog.blogId}" style="width=100%;">
								<button
									style="background-color: ${blog.isDraft ? '#4CAF50' : '#f44336'}; color: white; padding: 5px 10px; border-radius: 5px; width: 100%;">
									${blog.isDraft ? 'Publish' : 'Add to Draft'}
								</button>
							</a>
						</td>
					</c:if>
					
					<c:if test="${role == 'user'}">
					    <td>
					        <c:choose>
					            <c:when test="${blog.isDraft}">Pending</c:when>
					            <c:otherwise>Approved</c:otherwise>
					        </c:choose>
					    </td>
					</c:if>
					
					<td>
						<c:if test="${canEdit }">
							<a
								href="/WordWeave/admin/blogs?action=edit&id=${blog.blogId}"
								style="margin-right: 10px;"> <i class="fas fa-edit"
									style="color: #4d1f26;"></i>
							</a> 
						</c:if>
						
						
						<c:if test="${canDelete }">
							<a href="/WordWeave/admin/blogs?action=delete&id=${blog.blogId}"
							onclick="return confirm('Are you sure you want to delete this blog?')">
								<i class="fas fa-trash-alt" style="color: red;"></i>
							</a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>

<%@ include file="partials/footer.jsp"%>
