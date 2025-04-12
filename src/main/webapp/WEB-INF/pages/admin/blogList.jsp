<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ include file="partials/header.jsp" %>

<div class="main-content">
    <%@ include file="partials/user_nav.jsp" %>

    <div style="display: flex; justify-content: space-between; align-items: center;">
        <h2>Blogs</h2>
        <a href="/WordWeave/admin/blogs/?action=create"
           style="background-color: #4d1f26; color: white; padding: 10px 15px; border-radius: 8px; text-decoration: none; font-weight: bold;">
            + Create Blog
        </a>
    </div>

    <table>
        <thead>
            <tr>
                <th>SN</th>
                <th>ID</th>
                <th>Title</th>
                <th>Author Name</th>
                <th>Publish Date</th>
                <th>Last Updated</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="blog" items="${blogs}" varStatus="loop">
                <tr>
                    <td>${loop.index + 1}</td>
                    <td>${blog.blogId}</td>
                    <td>${blog.title}</td>
                    <td>${blog.authorName}</td>
                    <td>${blog.publishDate}</td>
                    <td>${blog.updatedAt}</td>
                    <td>
                        <a href="/WordWeave/admin/blogs/?action=edit&id=${blog.blogId}" style="margin-right: 10px;">
                            <i class="fas fa-edit" style="color: #4d1f26;"></i>
                        </a>
                        <a href="/WordWeave/admin/blogs/?action=delete&id=${blog.blogId}"
                           onclick="return confirm('Are you sure you want to delete this blog?')">
                            <i class="fas fa-trash-alt" style="color: red;"></i>
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
