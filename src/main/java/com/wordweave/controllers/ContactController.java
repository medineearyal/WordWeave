package com.wordweave.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wordweave.models.ContactModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.ContactService;
import com.wordweave.services.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Controller that handles rendering the contact form and processing contact submissions.
 * Mapped to /contact URL. Allows users to message authors listed in the system.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/contact" })
public class ContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserService();
	ContactService contactService = new ContactService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContactController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * Handles GET requests to display the contact form.
	 * Loads all users with the role "user" (authors) and forwards to contact.jsp.
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<UserModel> authors = userService.getAllUsers("user");
            request.setAttribute("authors", authors);
        } catch (Exception e) {
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/contact.jsp");
        dispatcher.forward(request, response);
    }


	/**
	 * Handles POST requests from the contact form submission.
	 * Validates input, creates a contact message, and saves it to the database.
	 * On error, redisplays the form with error messages.
	 *
	 * @param request  HttpServletRequest
	 * @param response HttpServletResponse
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = validateContactForm(request);

        if (!errors.isEmpty()) {
            handleContactFailure(request, response, errors);
            return;
        }

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        int author = Integer.parseInt(request.getParameter("author"));
        String message = request.getParameter("message");

        ContactModel contact = new ContactModel(author, email, message, fullName);
        contact.setMessageDate(LocalDateTime.now());

        try {
            contactService.createContact(contact);
            request.setAttribute("success", "Your message has been sent successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "There was an issue submitting your message. Please try again.");
        }

        doGet(request, response);
    }

	 /**
     * Validates the contact form inputs.
     * Returns a map of field-specific error messages.
     *
     * @param request HttpServletRequest
     * @return Map of errors
     */
    private Map<String, String> validateContactForm(HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String author = request.getParameter("author");
        String message = request.getParameter("message");

        if (fullName == null || fullName.trim().isEmpty()) {
            errors.put("error_fullName", "Full name is required.");
        }

        if (email == null || email.trim().isEmpty()) {
            errors.put("error_email", "Email is required.");
        } else if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errors.put("error_email", "Please enter a valid email address.");
        }

        if (author == null || author.trim().isEmpty()) {
            errors.put("error_author", "Author selection is required.");
        }

        if (message == null || message.trim().isEmpty()) {
            errors.put("error_message", "Message cannot be empty.");
        }

        return errors;
    }

    /**
     * Handles form re-display in case of validation failure.
     * Re-populates form fields and forwards back to the contact page.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param errors   Map of validation error messages
     * @throws ServletException
     * @throws IOException
     */
    private void handleContactFailure(HttpServletRequest request, HttpServletResponse response, Map<String, String> errors)
            throws ServletException, IOException {

        for (Map.Entry<String, String> entry : errors.entrySet()) {
            request.setAttribute(entry.getKey(), entry.getValue());
        }

        request.setAttribute("fullName", request.getParameter("fullName"));
        request.setAttribute("email", request.getParameter("email"));
        request.setAttribute("message", request.getParameter("message"));
        request.setAttribute("author", request.getParameter("author"));

        doGet(request, response);
    }
}
