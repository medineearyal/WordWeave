package com.wordweave.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.wordweave.models.RoleModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.RoleService;
import com.wordweave.services.UserService;
import com.wordweave.utils.ValidationUtil;
import com.wordweave.utils.PasswordUtil;
import com.wordweave.utils.FormUtils;
import com.wordweave.utils.ImageUtil;

/**
 * Servlet implementation class RegisterController
 */
@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = { "/register" })
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final UserService userService = new UserService();
	private final ImageUtil imageUtil = new ImageUtil();
	private final RoleService roleService = new RoleService();

    public RegisterController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/register.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Validate and extract user model
            String validationMessage = validateRegistrationForm(request);
            if (validationMessage != null) {
            	System.out.println("Validation Failed: " + validationMessage);
                handleError(request, response, validationMessage);
                return;
            }

            UserModel userModel = extractUserModel(request);

            Boolean isAdded = userService.registerUser(userModel);

            if (isAdded == null) {
                handleError(request, response, "Our server is under maintenance. Please try again later!");
                System.out.println("Not AddedError");
            } else if (isAdded) {
                handleSuccess(request, response, "Your account is successfully created!", "/WEB-INF/pages/client/login.jsp");
            } else {
            	System.out.println("Couldn't Register");
                handleError(request, response, "Could not register your account. Please try again later!");
            }
        } catch (Exception e) {
            handleError(request, response, "An unexpected error occurred. Please try again later!");
            System.out.println("Random Error");
            e.printStackTrace();
        }
    }

    private String validateRegistrationForm(HttpServletRequest req) {
    	try {
    		String fullname = FormUtils.getFormField(req, "fullname");
        	String email = FormUtils.getFormField(req, "email");
        	String username = FormUtils.getFormField(req, "username");
        	String password = FormUtils.getFormField(req, "password");
        	String cPassword = FormUtils.getFormField(req, "cPassword");
        	
        	if (ValidationUtil.isNullOrEmpty(fullname))
                return "Fullname is required.";

            if (ValidationUtil.isNullOrEmpty(username))
                return "Username is required.";

            if (ValidationUtil.isNullOrEmpty(email))
                return "Email is required.";

            if (ValidationUtil.isNullOrEmpty(password))
                return "Password is required.";

            if (ValidationUtil.isNullOrEmpty(cPassword))
                return "Please retype the password.";

            // Validate fields
            if (!ValidationUtil.isAlphanumericStartingWithLetter(username))
                return "Username must start with a letter and contain only letters and numbers.";

            if (!ValidationUtil.isValidEmail(email))
                return "Invalid email format.";

            if (!ValidationUtil.isValidPassword(password))
                return "Password must be at least 8 characters long, with 1 uppercase letter, 1 number, and 1 symbol.";

            if (!ValidationUtil.doPasswordsMatch(password, cPassword))
                return "Passwords do not match.";
		} catch (Exception e) {
			// TODO: handle exception
	        return null;
		}
    	return null;
    }

    private UserModel extractUserModel(HttpServletRequest req) throws Exception {
        String fullname = FormUtils.getFormField(req, "fullname");
        String email = FormUtils.getFormField(req, "email");
        String username = FormUtils.getFormField(req, "username");
        String password = FormUtils.getFormField(req, "password");
        
        RoleModel userRole = roleService.getRole("user");
        System.out.println(userRole.getName());
        int roleId = userRole.getRole_id();
        System.out.println(roleId);
        
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

    private void handleSuccess(HttpServletRequest req, HttpServletResponse response, String message, String redirectPage)
            throws ServletException, IOException {
        req.setAttribute("success", message);
        req.getRequestDispatcher(redirectPage).forward(req, response);
    }

    private void handleError(HttpServletRequest req, HttpServletResponse resp, String message)
            throws ServletException, IOException {
        req.setAttribute("error", message);
        req.setAttribute("fullname", FormUtils.getFormField(req, "fullname"));
        req.setAttribute("username", FormUtils.getFormField(req, "username"));
        req.setAttribute("email", FormUtils.getFormField(req, "email"));
        req.getRequestDispatcher("/WEB-INF/pages/client/register.jsp").forward(req, resp);
    }
}
