const swiper = new Swiper('.swiper', {
	slidesPerView: 4,
	spaceBetween: 4,
	grid: {
		rows: 1,
		fill: 'column',
	},
	loop: true,
	centeredSlides: true,
	navigation: {
		nextEl: '.swiper-button-next',
		prevEl: '.swiper-button-prev',
	},
	breakpoints: {
		1024: {
			slidesPerView: 4,
		},
		768: {
			slidesPerView: 2,
		},
		480: {
			slidesPerView: 1,
		},
	},
});


document.addEventListener("DOMContentLoaded", function() {
	const clickableCards = document.querySelectorAll(".clickableCard");
	clickableCards?.forEach(card => {
		card.addEventListener("click", function() {
			const blogId = card.getAttribute("data-id");
			window.open(`/WordWeave/blog/${blogId}`, "_blank");
		})
	});

	const favoriteIcons = document.querySelectorAll(".favorite-icon");
	favoriteIcons?.forEach(icon => {
		icon.addEventListener("click", function() {
			icon.classList.toggle("favorite");

			const blogId = icon.getAttribute("data-blog-id");
			const username = icon.getAttribute("data-user");

			if (!username) {
				window.location.href = "/WordWeave/login";
			} else {
				fetch(`/WordWeave/admin/blogs/?action=toggle-favorite&username=${username}&blogId=${blogId}`,
					{
						'method': 'GET',
					})
					.then(response => response.json())
					.then(data => {
						alert(data.message);

					});
			}
		});
	});

	const notifications = document.querySelectorAll('.notification');

	notifications.forEach(notification => {
		// Show the notification
		notification.classList.add('show');

		// Fade out after 5 seconds
		setTimeout(() => {
			notification.classList.remove('show');
		}, 5000);  // 5 seconds
	});

	getCopyrightDate();
});

window.addEventListener("scroll", function() {
	const header = document.querySelector("header");
	if (window.scrollY > 0) {
		header.classList.add("scrolled");
	} else {
		header.classList.remove("scrolled");
	}
});

function getCopyrightDate() {
	const copyrightSpan = document.querySelector(".copyright-date");
	const currentYear = new Date().getFullYear();
	if (copyrightSpan) {
		copyrightSpan.innerHTML = currentYear;	
	}
}

function clearSearchInput() {
	const searchInput = document.getElementById("Search");
	searchInput.value = "";
}