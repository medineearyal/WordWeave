package com.wordweave.controllers.admin;

import java.io.IOException;
import java.util.List;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;
import com.wordweave.utils.CookieUtil;
import com.wordweave.utils.FormUtils;
import com.wordweave.utils.ImageUtil;
import com.wordweave.utils.PasswordUtil;
import com.wordweave.utils.SessionUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/users/" })
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
		
		Cookie userRole = CookieUtil.getCookie(request, "role");
		String username = (String) SessionUtil.getAttribute(request, "username");
		
		if (userRole == null && username != null) {
			HttpSession session = request.getSession();
		    session.removeAttribute("username");
		    System.out.println("Username removed from session");
		}
		
		if (username == null && userRole == null) {
			response.sendRedirect("/WordWeave/login");
			return;
		}

		request.setAttribute("role", userRole.getValue());
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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		if (action.equals("create")) {
			UserModel userModel;
			try {
				userModel = extractUserModel(request);
				Boolean isAdded = userService.registerUser(userModel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect("/WordWeave/admin/users/");

		} else if (action.equals("edit")) {
			int userId = Integer.parseInt(request.getParameter("id"));
			UserModel userModel;
			try {
				userModel = extractUserModel(request);
				userModel.setUser_id(userId);
				Boolean isAdded = userService.updateUser(userModel);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			response.sendRedirect("/WordWeave/admin/users/");
		}
	}
	
	private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String fullname = FormUtils.getFormField(req, "fullname");
        String email = FormUtils.getFormField(req, "email");
        String username = FormUtils.getFormField(req, "username");
        String password = FormUtils.getFormField(req, "password");
        System.out.println(FormUtils.getFormField(req, "role_id"));
        System.out.println(req.getParameter("role_id"));
        int roleId = Integer.parseInt(req.getParameter("role_id"));
       

        String profilePicture = null;

        // Encrypt the password before storing
        password = PasswordUtil.encrypt(username, password);

        boolean isImageUploaded = imageUtil.uploadImage(req, "profile_picture");
        if (isImageUploaded) {
            profilePicture = "/images/" + req.getPart("profile_picture").getSubmittedFileName();
        }

        // Return a new UserModel with the extracted data
        return new UserModel(fullname, email, username, password, roleId, profilePicture);
    }
}
