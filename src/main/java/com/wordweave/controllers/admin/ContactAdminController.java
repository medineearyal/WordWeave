package com.wordweave.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.wordweave.utils.CookieUtil;
import com.wordweave.utils.SessionUtil;

/**
 * Servlet implementation class ContactAdminController
 */
@WebServlet("/admin/contacts/")
public class ContactAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactAdminController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
