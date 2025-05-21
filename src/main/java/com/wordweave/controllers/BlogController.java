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

/**
 * Controller that handles requests to the "/blogs" URL.
 * It retrieves all blogs and the most viewed blogs,
 * and optionally fetches the user's favorite blogs if logged in.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/blogs" })
public class BlogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BlogService blogService = new BlogService();
	UserService userService = new UserService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlogController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<BlogModel> blogs;
		List<BlogModel> mostViewed;
		try {
			blogs = blogService.getAllBlogs();
			mostViewed = blogService.getAllMostViewedBlogs();
		    
		    if (mostViewed.size() > 4) {
		    	mostViewed = mostViewed.subList(0, 4);
	        }
			request.setAttribute("blogs", blogs);
			request.setAttribute("mostViewedBlogs", mostViewed);
			
			String sessionUsername = (String) request.getAttribute("username");

		    if (sessionUsername != null) {
		        UserModel user = userService.getUserByUsername(sessionUsername);
		        
		        if (user != null) {
		            int userId = user.getUser_id();
		            List<Integer> favoriteBlogIds = blogService.getFavoriteBlogIdsByUser(userId);
		            request.setAttribute("favoriteBlogIds", favoriteBlogIds);
		        } else {
		            request.setAttribute("error", "User not found.");
		        }
		    }
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/blog.jsp");
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
