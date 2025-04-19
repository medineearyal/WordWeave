package com.wordweave.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.wordweave.models.ContactModel;
import com.wordweave.services.ContactService;
import com.wordweave.utils.CookieUtil;
import com.wordweave.utils.SessionUtil;

/**
 * Servlet implementation class ContactAdminController
 */
@WebServlet("/admin/contacts/")
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
		
		String action = request.getParameter("action");

        if ("create".equals(action)) {
            request.getRequestDispatcher("/WEB-INF/pages/admin/contactForm.jsp").forward(request, response);
        } else if ("edit".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            ContactModel contact = contactService.getContactById(id);
            request.setAttribute("contact", contact);
            request.getRequestDispatcher("/WEB-INF/pages/admin/contactForm.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            contactService.deleteContact(id);
            response.sendRedirect("/contacts?action=list");
        } else {
            List<ContactModel> contacts = contactService.getAllContacts();
            request.setAttribute("contacts", contacts);
            request.getRequestDispatcher("/WEB-INF/pages/admin/contactList.jsp").forward(request, response);
        }
    }

    // Handle form submissions for creating and updating contacts
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        ContactModel contact = new ContactModel();
        contact.setSenderName(request.getParameter("senderName"));
        contact.setSenderEmail(request.getParameter("senderEmail"));
        contact.setMessageText(request.getParameter("messageText"));
        contact.setMessageDate(LocalDateTime.now());

        if ("create".equals(action)) {
            contactService.createContact(contact);
        } else if ("update".equals(action)) {
            contact.setMessageId(Long.parseLong(request.getParameter("messageId")));
            contactService.updateContact(contact);
        }

        response.sendRedirect("/contacts?action=list");
    }

}
