<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="partials/header.jsp"%>

<div class="main-content">
	<%@ include file="partials/user_nav.jsp" %>

	<h2>Dashboard</h2>

	<div class="dashboard-layout">
		<div class="card">
			<div class="card-content">
				<h3>Total Blog Published This Week</h3>
				<h1>15</h1>
			</div>
			<img src="../Images/blogimage.png" alt="Blog Icon">
		</div>

		<div class="recent-section">
			<h4>Recent Blogs</h4>
			<div class="timeline">
				<div class="timeline-item">

					<div>
						<strong>Shradda Kapoor AI</strong><br> <small>Medinee
							Aryal<br>29 March, 2025
						</small>
					</div>
				</div>
				<div class="timeline-item">

					<div>
						<strong>Why Not To Go Japan?</strong><br> <small>Rima
							Ghale<br>27 March, 2025
						</small>
					</div>
				</div>
				<div class="timeline-item">

					<div>
						<strong>Locket From SweetPeas</strong><br> <small>Rita
							Kumari<br>29 March, 2025
						</small>
					</div>
				</div>
			</div>

			<h4>Recent Users</h4>
			<div class="timeline">
				<div class="timeline-item">

					<div>
						<strong>Medinee Aryal</strong><br> <small>29 March,
							2025</small>
					</div>
				</div>
				<div class="timeline-item">

					<div>
						<strong>Rima Ghale</strong><br> <small>27 March, 2025</small>
					</div>
				</div>
				<div class="timeline-item">

					<div>
						<strong>Rita Kumari</strong><br> <small>29 March,
							2025</small>
					</div>
				</div>
			</div>
		</div>

		<div class="card">
			<div class="card-content">
				<h3>Total Users</h3>
				<p>Moderator: 4</p>
				<p>Active Users: 30</p>
			</div>
			<i class="fas fa-users" style="font-size: 40px; color: #4d1f26;"></i>
		</div>

		<div class="card">
			<div class="card-content">
				<h3>Pending</h3>
				<p>Moderator: 4</p>
				<p>Users: 30</p>
			</div>
			<i class="fas fa-hourglass-half"
				style="font-size: 40px; color: #4d1f26;"></i>
		</div>
	</div>
</div>
</body>
</html>