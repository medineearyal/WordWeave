package com.wordweave.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;
import com.wordweave.utils.SessionUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserService userService = new UserService();
	private final RoleService roleService = new RoleService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/login.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    Map<String, String> errors = validateLoginForm(req);

	    if (!errors.isEmpty()) {
	        handleLoginFailure(req, resp, errors);
	        return;
	    }

	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    UserModel userModel = new UserModel(username, password);
	    
	    Boolean loginStatus = userService.loginUser(userModel);

	    if (loginStatus != null && loginStatus) {
	        SessionUtil.setAttribute(req, "username", username);
	        UserModel user = userService.getUserByUsername(username);
	        RoleModel userRole = roleService.getUserRole(user.getUser_id());

	        String role = userRole.getName();
	        SessionUtil.setAttribute(req, "role", role);
	        
	        if (role.equals("admin") || role.equals("moderator")) {
	            resp.sendRedirect(req.getContextPath() + "/admin/dashboard");
	        } else {
	            resp.sendRedirect(req.getContextPath() + "/home");
	        }
	    } else {
	        errors.put("error", loginStatus == null
	            ? "Our server is under maintenance. Please try again later!"
	            : "User credential mismatch. Please try again!");
	        handleLoginFailure(req, resp, errors);
	    }
	}

	private Map<String, String> validateLoginForm(HttpServletRequest req) {
	    Map<String, String> errors = new HashMap<>();

	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    if (username == null || username.trim().isEmpty()) {
	        errors.put("error_username", "Username is required.");
	    }

	    if (password == null || password.trim().isEmpty()) {
	        errors.put("error_password", "Password is required.");
	    }

	    return errors;
	}

	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Map<String, String> errors)
	        throws ServletException, IOException {

	    for (Map.Entry<String, String> entry : errors.entrySet()) {
	        req.setAttribute(entry.getKey(), entry.getValue());
	    }

	    req.setAttribute("username", req.getParameter("username"));
	    req.getRequestDispatcher("/WEB-INF/pages/client/login.jsp").forward(req, resp);
	}

}
