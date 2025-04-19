package com.wordweave.controllers.admin;

import java.io.IOException;

import com.wordweave.models.UserModel;
import com.wordweave.services.UserService;
import com.wordweave.utils.CookieUtil;
import com.wordweave.utils.SessionUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class AccountsAdminController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/accounts/" })
public class AccountsAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountsAdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		
		UserModel user = userService.getUserByUsername(username);

		request.setAttribute("role", userRole.getValue());
		request.setAttribute("user", user);
		request.setAttribute("actionText", user.getFullname());
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
