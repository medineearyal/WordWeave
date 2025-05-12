package com.wordweave.controllers;

import java.io.IOException;
import java.util.List;

import com.wordweave.models.BlogModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.BlogService;
import com.wordweave.services.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class FavouriteController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/favourites" })
public class FavouriteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final BlogService blogService = new BlogService();
	private final UserService userService = new UserService();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FavouriteController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get the current user's ID from the session
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		if (username != null) {
			try {
				// Get the user ID (You might need a method to get user by username)
				// Here, we assume you have a method like getUserByUsername to get the user ID.
				UserModel user = userService.getUserByUsername(username); // You may need to implement this method

				// Fetch the favorite blogs of the user
				List<BlogModel> favoriteBlogs = blogService.getFavoriteBlogsByUser(user.getUser_id());

				if (user != null) {
					// Get the user ID and fetch favorite blogs
					int userId = user.getUser_id();
					List<Integer> favoriteBlogIds = blogService.getFavoriteBlogIdsByUser(userId);
					request.setAttribute("favoriteBlogIds", favoriteBlogIds);
				}

				// Set the favorite blogs as a request attribute
				request.setAttribute("favoriteBlogs", favoriteBlogs);

			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "Failed to load favorite blogs.");
			}
		} else {
			request.setAttribute("error", "User is not logged in.");
			response.sendRedirect("/WordWeave/login");
			return;
		}

		// Forward to the favourites.jsp page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/favourites.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
