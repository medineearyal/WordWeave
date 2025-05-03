<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp"%>

	<h2>Dashboard</h2>

	<div class="dashboard-layout">
		<div class="card"
			style="display: grid; grid-template-columns: 2fr 1fr; align-items: center;">
			<img src="/WordWeave/images/blogimage.png" alt="Blog Icon"
				style="height: 100%; width: 100%; order: 1; object-fit: cover;" />
			<div class="card-content">
				<h3 style="font-size: 2rem; color: var(--accent-color);">Total
					Blog Published This Week</h3>
				<h1 style="font-size: 128px; color: var(--secondary-color);">${totalPublishedBlogs}</h1>
			</div>
		</div>

		<div class="recent-section">
			<!-- User Recent Section  -->
			<c:if
				test="${recentPublishedBlogs != null and !recentPublishedBlogs.isEmpty() and role != null and role.toLowerCase() == 'user'}">
				<h4 style="margin-bottom: 0.5rem; font-size: 18px;">Recent
					Blogs Posted</h4>
				<div class="timeline">
					<c:forEach var="blog" items="${recentPublishedBlogs}"
						varStatus="status">
						<div class="timeline-item">
							<div>
								<strong>${blog.title}</strong><br> <small>${blog.authorName}<br>${blog.publishDate}</small>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>

			<c:if
				test="${recentDraftedBlogs != null and !recentDraftedBlogs.isEmpty() and role != null and role.toLowerCase() == 'user'}">
				<h4 style="margin-bottom: 0.5rem; font-size: 18px;">Blogs
					Waiting For Approval</h4>
				<div class="timeline">
					<c:forEach var="blog" items="${recentDraftedBlogs}"
						varStatus="status">
						<div class="timeline-item">
							<div>
								<strong>${blog.title}</strong><br> <small>${blog.authorName}<br>${blog.publishDate}</small>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>

			<!-- Recent Moderator Section -->
			<c:if
				test="${recentPublishedBlogs != null and !recentPublishedBlogs.isEmpty() and role != null and role.toLowerCase() == 'moderator'}">
				<h4 style="margin-bottom: 0.5rem; font-size: 18px;">Recent
					Blogs Posted</h4>
				<div class="timeline">
					<c:forEach var="blog" items="${recentPublishedBlogs}"
						varStatus="status">
						<div class="timeline-item">
							<div>
								<strong>${blog.title}</strong><br> <small>${blog.authorName}<br>${blog.publishDate}</small>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>

			<c:if
				test="${recentDraftedBlogs != null and !recentDraftedBlogs.isEmpty() and role != null and role.toLowerCase() == 'moderator'}">
				<h4 style="margin-bottom: 0.5rem; font-size: 18px;">Recent
					Blogs For Review</h4>
				<div class="timeline">
					<c:forEach var="blog" items="${recentDraftedBlogs}"
						varStatus="status">
						<div class="timeline-item">
							<div>
								<strong>${blog.title}</strong><br> <small>${blog.authorName}<br>${blog.publishDate}</small>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			
			<!-- Recent Admin Section -->
			<c:if test="${role != null and role.toLowerCase().equals('admin')}">
				<h4 style="margin-bottom: 0.5rem;">Recent Blogs</h4>
				<div class="timeline">
					<c:forEach var="blog" items="${recentBlogs}"
						varStatus="status">
						<div class="timeline-item">
							<div>
								<strong>${blog.title}</strong><br> <small>${blog.authorName}<br>${blog.publishDate}</small>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
			
			<c:if test="${role != null and role.toLowerCase().equals('admin')}">
				<h4 style="margin-bottom: 0.5rem;">Recent Users</h4>
				<div class="timeline">
					<c:forEach var="user" items="${recentUsers}"
						varStatus="status">
						<div class="timeline-item">
							<div>
								<strong>${user.fullname}</strong><br> <small>${user.createdAt}</small>
							</div>
						</div>
					</c:forEach>
				</div>
			</c:if>
		</div>

		<!-- Bottom Left -->
		<!-- For Admin -->
		<c:if test="${role != null and role.toLowerCase().equals('admin')}">
			<div class="card">
				<div class="card-content" style="order: unset; display: flex; gap: 1rem; flex-direction: column;">
					<h3 style="color: var(--primary-color); font-size: 2rem;">Total Users</h3>
					<p style="font-weght: bold; font-size: 2rem; color: var(--secondary-color)">Moderator: ${approvedModeratorsCount}</p>
					<p style="font-weght: bold; font-size: 2rem; color: var(--secondary-color)">Active Users: ${approvedUsersCount}</p>
				</div>
				<i class="fas fa-users" style="font-size: 5rem; color: var(--secondary-color); margin-top: 40px;"></i>
			</div>
		</c:if>

		<!-- For Mod -->
		<c:if
			test="${role != null and role.toLowerCase().equals('moderator')}">
			<div class="card">
				<div class="card-content" style="order: unset;">
					<h3 style="color: var(--primary-color); font-size: 2rem;">Total
						Blogs</h3>
					<p
						style="font-weght: bold; font-size: 128px; color: var(--secondary-color)">0</p>
				</div>
				<i class="fas fa-users"
					style="font-size: 5rem; color: var(--secondary-color); margin-top: 40px;"></i>
			</div>
		</c:if>

		<!-- For User -->
		<c:if test="${role != null and role.toLowerCase().equals('user')}">
			<div class="card">
				<i class="fas fa-users"
					style="font-size: 5rem; color: var(--secondary-color); margin-top: 40px;"></i>
				<div class="card-content" style="order: unset;">
					<h3 style="color: var(--primary-color); font-size: 2rem;">Your
						Blogs</h3>
					<p
						style="font-weght: bold; font-size: 128px; color: var(--secondary-color)">${totalPublishedBlogs}</p>
				</div>
			</div>
		</c:if>

		<!-- Bottom Right -->
		<!-- For Admin -->
		<c:if test="${role != null and role.toLowerCase().equals('admin')}">
			<div class="card">
				<div class="card-content" style="order: unset; display: flex; gap: 1rem; flex-direction: column;">
					<h3 style="color: var(--primary-color); font-size: 2rem;">Pending</h3>
					<p style="font-weght: bold; font-size: 2rem; color: var(--secondary-color)">Moderator: ${pendingUsersCount}</p>
					<p style="font-weght: bold; font-size: 2rem; color: var(--secondary-color)">Active Users: ${pendingModeratorsCount}</p>
				</div>
				<i class="fa-solid fa-hourglass-half fa-2xl"
					style="font-size: 5rem; color: var(--primary-color); margin-top: 40px;"></i>
			</div>
		</c:if>

		<!-- For Mod -->
		<c:if
			test="${role != null and role.toLowerCase().equals('moderator')}">
			<div class="card">
				<div class="card-content" style="order: unset;">
					<h3 style="color: var(--primary-color); font-size: 2rem;">Pending Post</h3>
					<p
						style="font-weght: blod; font-size: 128px; color: var(--secondary-color)">0</p>
				</div>
				<i class="fa-solid fa-hourglass-half fa-2xl"
					style="font-size: 5rem; color: var(--primary-color); margin-top: 40px;"></i>
			</div>
		</c:if>

		<!-- For User -->
		<c:if test="${role != null and role.toLowerCase().equals('user')}">
			<div class="card">
				<div class="card-content" style="order: unset;">
					<h3 style="color: var(--primary-color); fon-size: 2rem;">Not
						Approved</h3>
					<p
						style="font-weght: blod; font-size: 128px; color: var(--secondary-color)">${totalDrafts}</p>
				</div>
				<i class="fa-solid fa-hourglass-half fa-2xl"
					style="font-size: 5rem; color: var(--primary-color); margin-top: 40px;"></i>
			</div>
		</c:if>
	</div>
</div>
<%@ include file="partials/footer.jsp"%>