<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="user-profile">
    <div class="user-icon">
        <span><i class="fas fa-user-shield"></i> ${sessionScope.username}</span>
        <span><i class="fas fa-caret-down"></i></span>
    </div>
    
    <div class="dropdown-menu">
        <a href="/WordWeave/logout">Logout</a>
    </div>
</div>

<style>
.user-profile {
    position: relative;
    cursor: pointer;
}

.user-icon {
	padding: 8px;
}

.dropdown-menu {
    display: none;
    position: absolute;
    top: 60%;  /* Position directly below the user icon */
    right: 0;
    background-color: #fff;
    border: 1px solid #ccc;
    box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
    min-width: 160px;
    z-index: 100;
}

.dropdown-menu a {
    display: block;
    padding: 10px;
    text-decoration: none;
    color: #333;
    font-size: 14px;
}

.dropdown-menu a:hover {
    background-color: #ddd;
}

.user-icon:hover {
    background-color: #f1f1f1;
    border-radius: 4px;
}
</style>

<script>
    document.querySelector('.user-profile .user-icon').addEventListener('click', function(event) {
        event.stopPropagation();
        
        var dropdownMenu = document.querySelector('.dropdown-menu');
        dropdownMenu.style.display = (dropdownMenu.style.display === 'block' ? 'none' : 'block');
    });

    window.addEventListener('click', function(event) {
        var dropdownMenu = document.querySelector('.dropdown-menu');
        var userProfile = document.querySelector('.user-profile');
        
        if (!userProfile.contains(event.target)) {
            dropdownMenu.style.display = 'none';
        }
    });
</script>