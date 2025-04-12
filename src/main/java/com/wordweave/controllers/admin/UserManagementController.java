package com.wordweave.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/users" })
public class UserManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	
	List<RoleModel> roles;

	public UserManagementController() {
		super();
		this.roles = roleService.getAllRoles();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action == null) {
			try {
				List<UserModel> users = userService.getAllUsers();
				request.setAttribute("users", users);
				request.setAttribute("roles", this.roles);
			} catch (Exception e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("/WEB-INF/pages/admin/userList.jsp").forward(request, response);
		} else if (action.equals("edit")) {
			try {
				int userId = Integer.parseInt(request.getParameter("id"));
				String actionText = "Edit";
				UserModel user = userService.getUserById(userId);
				
				request.setAttribute("user", user);
				request.setAttribute("actionText", actionText);
				request.setAttribute("roles", this.roles);
			} catch (Exception e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);

		} else if (action.equals("delete")) {
			try {
				int userId = Integer.parseInt(request.getParameter("id"));
				userService.deleteUser(userId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("/WordWeave/admin/users/");
		} else if (action.equals("create")) {
			String actionText = "Create";
			request.setAttribute("actionText", actionText);
			request.setAttribute("roles", this.roles);
			request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("create")) {
			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			int roleId = Integer.parseInt(request.getParameter("role_id"));
			String profilePicture = request.getParameter("profile_picture");

			UserModel newUser = new UserModel(fullname, email, username, password, roleId, profilePicture);
			userService.registerUser(newUser);

			response.sendRedirect("/admin/users");

		} else if (action.equals("edit")) {
			int userId = Integer.parseInt(request.getParameter("id"));
			String fullname = request.getParameter("fullname");
			String email = request.getParameter("email");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			int roleId = Integer.parseInt(request.getParameter("role_id"));
			String profilePicture = request.getParameter("profile_picture");

			try {
				UserModel updatedUser = new UserModel(userId, fullname, email, username, roleId, password, profilePicture);
				userService.updateUser(updatedUser);
			} catch (Exception e) {
				e.printStackTrace();
			}

			response.sendRedirect("/admin/users");
		}
	}
}
