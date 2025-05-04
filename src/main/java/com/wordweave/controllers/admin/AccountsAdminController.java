package com.wordweave.controllers.admin;

import java.io.IOException;
import java.util.List;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountsAdminController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/accounts" })
public class AccountsAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService();
	List<RoleModel> roles;
	RoleService roleSerivce = new RoleService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountsAdminController() {
        super();
        this.roles = roleSerivce.getAllRoles();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userRole = (String) request.getAttribute("role");
		String username = (String) request.getAttribute("username");
		
		if (username == null && userRole == null) {
			response.sendRedirect("/WordWeave/login");
			return;
		}
		
		UserModel user = userService.getUserByUsername(username);

		request.setAttribute("role", userRole);
		request.setAttribute("roles", this.roles);
		request.setAttribute("editUser", user);
		request.setAttribute("actionText", "Update");
		request.setAttribute("profile", "view");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/admin/userForm.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
