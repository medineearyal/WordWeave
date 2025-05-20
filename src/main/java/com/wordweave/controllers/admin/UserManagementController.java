package com.wordweave.controllers.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;
import com.wordweave.utils.FormUtils;
import com.wordweave.utils.ImageUtil;
import com.wordweave.utils.PasswordUtil;
import com.wordweave.utils.ValidationUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/users" })
public class UserManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();
	private RoleService roleService = new RoleService();
	private ImageUtil imageUtil = new ImageUtil();

	List<RoleModel> roles;

	public UserManagementController() {
		super();
		this.roles = roleService.getAllRoles();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		String userRole = (String) request.getAttribute("role");
		String username = (String) request.getAttribute("username");

		if (username == null && userRole == null) {
			response.sendRedirect("/wordweave/login");
			return;
		}

		request.setAttribute("role", userRole);
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
			int userId = Integer.parseInt(request.getParameter("id"));
			String actionText = "Edit";
			UserModel user;
			try {
				user = userService.getUserById(userId);
				user.setPassword("");
				request.setAttribute("editUser", user);
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
				request.getSession().setAttribute("success", "User Sucessfully Deleted..");
			} catch (Exception e) {
				request.getSession().setAttribute("error", "User Deletion Error..");
			}
			response.sendRedirect("/wordweave/admin/users");
		} else if (action.equals("create")) {
			String actionText = "Create";
			request.setAttribute("actionText", actionText);
			request.setAttribute("roles", this.roles);
			request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("create")) {
			UserModel userModel;
			try {
				userModel = extractUserModel(request);

				HashMap<String, String> errors = this.validateUser(userModel, true);

				userModel.setPassword(PasswordUtil.encrypt(userModel.getUsername(), userModel.getPassword()));

				if (!errors.isEmpty()) {
					for (Map.Entry<String, String> entry : errors.entrySet()) {
						request.setAttribute(entry.getKey(), entry.getValue());
					}
					request.setAttribute("userModel", userModel);
					request.setAttribute("actionText", "Create");
					request.setAttribute("roles", this.roles);
					request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
					return;
				}


				Boolean isAdded = userService.registerUser(userModel);
				if (isAdded) {
					request.setAttribute("success", "User Successfully Created!!");
					request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
					return;
				} else {
					request.setAttribute("error", "User registration failed.");
					request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "An error occurred.");
				request.getSession().setAttribute("error", "Failed to create user.");
				request.getSession().setAttribute("actionText", action);
				request.setAttribute("roles", this.roles);
				request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
				return;
			}

		} else if (action.equals("edit")) {
			int userId = Integer.parseInt(request.getParameter("id"));
			
			UserModel userModel;
			try {
				userModel = extractUserModel(request);
				userModel.setUser_id(userId);

				boolean passwordProvided = request.getParameter("password") != null && !request.getParameter("password").trim().isEmpty();
				
				HashMap<String, String> errors = this.validateUser(userModel, false);
				
				if (passwordProvided) {
					userModel.setPassword(PasswordUtil.encrypt(userModel.getUsername(), userModel.getPassword()));
				} else {
					UserModel existingUser = userService.getUserById(userId);
					userModel.setPassword(existingUser.getPassword());
				}
				

				if (!errors.isEmpty()) {
					for (Map.Entry<String, String> entry : errors.entrySet()) {
						request.setAttribute(entry.getKey(), entry.getValue());
					}
					request.setAttribute("editUser", userModel);
					request.setAttribute("roles", this.roles);
					request.setAttribute("actionText", "Edit");
					
					String fromLocation = request.getParameter("from") != null ? request.getParameter("from") : "";
					if (fromLocation.equals("accounts")) {
						request.setAttribute("actionText", "Update");
						String userRole = (String) request.getAttribute("role");
						RoleModel roleObject = roleService.getRole(userRole);
						request.setAttribute("roleObject", roleObject);
					}
					request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
					return;
				}


				Boolean isUpdated = userService.updateUser(userModel);
				if (isUpdated) {
					request.setAttribute("success", "User successfully updated!");
					request.getSession().setAttribute("success", "User successfully updated!");
				} else {
					request.setAttribute("error", "Failed to update user.");
					request.getSession().setAttribute("error", "Failed to update user.");
				}
				request.setAttribute("editUser", userModel);
				request.setAttribute("actionText", "Edit");
				request.setAttribute("roles", this.roles);
				request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "An error occurred while updating the user.");
				request.getSession().setAttribute("actionText", action);
				request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
				return;
			}
		}
	}

	private UserModel extractUserModel(HttpServletRequest req) {
		String fullname = "";
		String email = "";
		String username = "";
		String password = "";

		try {
			fullname = FormUtils.getFormField(req, "fullname");
			email = FormUtils.getFormField(req, "email");
			username = FormUtils.getFormField(req, "username");
			password = FormUtils.getFormField(req, "password");
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}

		String roleIdParam = req.getParameter("role_id");
		int roleId = -1;
		if (roleIdParam != null && !roleIdParam.trim().isEmpty()) {
			try {
				roleId = Integer.parseInt(roleIdParam);
			} catch (NumberFormatException e) {
			}
		}

		String profilePicture = null;
		try {
			boolean isImageUploaded = imageUtil.uploadImage(req, "profile_picture");
			if (isImageUploaded) {
				profilePicture = "/images/" + req.getPart("profile_picture").getSubmittedFileName();
			} else {
				profilePicture = FormUtils.getFormField(req, "existing_profile_picture");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new UserModel(fullname, email, username, password, roleId, profilePicture);
	}


	private HashMap<String, String> validateUser(UserModel userModel, boolean isCreate) {
		HashMap<String, String> errors = new HashMap<>();

		if (ValidationUtil.isNullOrEmpty(userModel.getFullname())) {
			errors.put("fullnameError", "Full name cannot be empty.");
		}

		if (ValidationUtil.isNullOrEmpty(userModel.getUsername())) {
			errors.put("usernameError", "Username cannot be empty.");
		} else if (!ValidationUtil.isAlphanumericStartingWithLetter(userModel.getUsername())) {
			errors.put("usernameError", "Invalid username format.");
		}

		if (ValidationUtil.isNullOrEmpty(userModel.getEmail())) {
			errors.put("emailError", "Email cannot be empty.");
		} else if (!ValidationUtil.isValidEmail(userModel.getEmail())) {
			errors.put("emailError", "Invalid email format.");
		}

		if (userModel.getRole_id() <= 0) {
			errors.put("roleError", "Please select a valid role.");
		}

		if (isCreate) {
			if (ValidationUtil.isNullOrEmpty(userModel.getPassword())) {
				errors.put("passwordError", "Password cannot be empty.");
			} else if (!ValidationUtil.isValidPassword(userModel.getPassword())) {
				errors.put("passwordError", "Password must meet minimum requirements.");
			}

			UserModel existingUser = userService.getUserByUsername(userModel.getUsername());
			if (existingUser != null) {
				errors.put("usernameError", "The user with this username already exists.");
			}
		}

		return errors;
	}

}
