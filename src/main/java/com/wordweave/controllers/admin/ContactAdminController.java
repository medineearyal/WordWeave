package com.wordweave.controllers.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.wordweave.models.ContactModel;
import com.wordweave.services.ContactService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet for managing contacts in the admin panel.
 * Supports listing contacts for the logged-in user and viewing details.
 */
@WebServlet("/admin/contacts")
public class ContactAdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactService contactService = new ContactService();
       
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

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userRole = (String) request.getAttribute("role");
		String username = (String) request.getAttribute("username");
		
		if (username == null && userRole == null) {
			response.sendRedirect("/wordweave/login");
			return;
		}
		request.setAttribute("role", userRole);
		
		String action = request.getParameter("action");

        if ("view".equals(action)) {
        	int id = Integer.parseInt(request.getParameter("id"));
        	ContactModel contact = contactService.getContactById(id);
        	request.setAttribute("contact", contact);
        	request.getRequestDispatcher("/WEB-INF/pages/admin/viewContact.jsp").forward(request, response);
        } else {
			try {
				List<ContactModel> contacts = contactService.getAllContactsForAuthor(username);
				request.setAttribute("contacts", contacts);
			} catch (SQLException e) {
				e.printStackTrace();
			}
            request.getRequestDispatcher("/WEB-INF/pages/admin/contactList.jsp").forward(request, response);
        }
    }

    // Handle form submissions for creating and updating contacts
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/admin/contacts?action=list");
    }

}
