package com.wordweave.controllers;

import java.io.IOException;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;
import com.wordweave.utils.CookieUtil;
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
		// TODO Auto-generated method stub
		String username = req.getParameter("username");
		String password = req.getParameter("password");

		UserModel userModel = new UserModel(username, password);
		Boolean loginStatus = userService.loginUser(userModel);

		if (loginStatus != null && loginStatus) {
			SessionUtil.setAttribute(req, "username", username);
			UserModel user = userService.getUserByUsername(username);
			RoleModel userRole = roleService.getUserRole(user.getUser_id());

			if (userRole.getName().equals("admin")) {
				CookieUtil.addCookie(resp, "role", "admin", 5 * 30);

				resp.sendRedirect(req.getContextPath() + "/admin/dashboard/");
			}else if (userRole.getName().equals("moderator")) {
				CookieUtil.addCookie(resp, "role", "moderator", 5 * 30);

				resp.sendRedirect(req.getContextPath() + "/admin/dashboard/");
			}else {
				CookieUtil.addCookie(resp, "role", "user", 5 * 30);
				resp.sendRedirect(req.getContextPath() + "/home");
			}
		} else {
			handleLoginFailure(req, resp, loginStatus);
		}
	}

	private void handleLoginFailure(HttpServletRequest req, HttpServletResponse resp, Boolean loginStatus)
			throws ServletException, IOException {
		String errorMessage;
		if (loginStatus == null) {
			errorMessage = "Our server is under maintenance. Please try again later!";
		} else {
			errorMessage = "User credential mismatch. Please try again!";
		}
		req.setAttribute("error", errorMessage);
		System.out.println(errorMessage);
		req.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(req, resp);
	}

}
