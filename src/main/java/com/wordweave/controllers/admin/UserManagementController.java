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
			response.sendRedirect("/WordWeave/login");
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
			try {
				int userId = Integer.parseInt(request.getParameter("id"));
				String actionText = "Edit";
				UserModel user = userService.getUserById(userId);
				user.setPassword("");
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
			response.sendRedirect("/WordWeave/admin/users");
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

				userModel.setPassword(PasswordUtil.encrypt(userModel.getPassword(), userModel.getUsername()));

				if (!errors.isEmpty()) {
					for (Map.Entry<String, String> entry : errors.entrySet()) {
						request.setAttribute(entry.getKey(), entry.getValue());
					}
					request.setAttribute("userModel", userModel);
					doGet(request, response);
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
					userModel.setPassword(PasswordUtil.encrypt(userModel.getPassword(), userModel.getUsername()));
				} else {
					UserModel existingUser = userService.getUserById(userId);
					userModel.setPassword(existingUser.getPassword());
				}
				

				if (!errors.isEmpty()) {
					for (Map.Entry<String, String> entry : errors.entrySet()) {
						request.setAttribute(entry.getKey() + "Error", entry.getValue());
					}
					request.setAttribute("user", userModel);
					request.setAttribute("actionText", "Edit");
					doGet(request, response);
					return;
				}

				Boolean isUpdated = userService.updateUser(userModel);
				if (isUpdated) {
					request.setAttribute("success", "User successfully updated!");
				} else {
					request.setAttribute("error", "Failed to update user.");
				}
				request.setAttribute("user", userModel);
				request.setAttribute("actionText", "Edit");
				doGet(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "An error occurred while updating the user.");
				request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp").forward(request, response);
				return;
			}
		}
	}

	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
		String fullname = FormUtils.getFormField(req, "fullname");
		String email = FormUtils.getFormField(req, "email");
		String username = FormUtils.getFormField(req, "username");
		String password = FormUtils.getFormField(req, "password");

		int roleId = Integer.parseInt(req.getParameter("role_id"));

		String profilePicture = null;

		boolean isImageUploaded = imageUtil.uploadImage(req, "profile_picture");
		if (isImageUploaded) {
			profilePicture = "/images/" + req.getPart("profile_picture").getSubmittedFileName();
		}

		return new UserModel(fullname, email, username, password, roleId, profilePicture);
	}

	private HashMap<String, String> validateUser(UserModel userModel, boolean isCreate) {
		HashMap<String, String> errors = new HashMap<>();

		if (ValidationUtil.isNullOrEmpty(userModel.getUsername())
				|| !ValidationUtil.isAlphanumericStartingWithLetter(userModel.getUsername())) {
			errors.put("usernameError", "Invalid username.");
		}

		if (!ValidationUtil.isValidEmail(userModel.getEmail())) {
			errors.put("emailError", "Invalid email.");
		}
			
	
		if (isCreate) {
			UserModel existingUser = userService.getUserByUsername(userModel.getUsername());
			if (existingUser != null) {
				errors.put("usernameError", "The user with this username already exists.");
			}
			
			if (!ValidationUtil.isValidPassword(userModel.getPassword())) {
				errors.put("passwordError", "Invalid password.");
			}

		}

		return errors;
	}

}
