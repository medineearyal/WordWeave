<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="user-profile">
	<div class="user-icon">
		<span><i class="fas fa-user-shield"></i>
			${sessionScope.username}</span> <span><i class="fas fa-caret-down"></i></span>
	</div>
	<div class="dropdown-menu">
		<a href="/wordweave/logout">Logout</a>
	</div>
</div>

<script>
	document
			.querySelector('.user-profile .user-icon')
			.addEventListener(
					'click',
					function(event) {
						event.stopPropagation();

						var dropdownMenu = document
								.querySelector('.dropdown-menu');
						dropdownMenu.style.display = (dropdownMenu.style.display === 'block' ? 'none'
								: 'block');
					});

	window.addEventListener('click', function(event) {
		var dropdownMenu = document.querySelector('.dropdown-menu');
		var userProfile = document.querySelector('.user-profile');

		if (!userProfile.contains(event.target)) {
			dropdownMenu.style.display = 'none';
		}
	});
</script>