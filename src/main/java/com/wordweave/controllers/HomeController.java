package com.wordweave.controllers;

import java.io.IOException;
import java.util.List;

import com.wordweave.models.BlogModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.BlogService;
import com.wordweave.services.UserService;
import com.wordweave.utils.SessionUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/" })
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BlogService blogService = new BlogService();
	UserService userService = new UserService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
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
		List<BlogModel> trendingBlogs;
		List<BlogModel> mostViewed;
		
		try {
		    blogs = blogService.getAllBlogs();
		    trendingBlogs = blogService.getAllTrendingBlogs();
		    mostViewed = blogService.getAllMostViewedBlogs();
		    
		    if (mostViewed.size() > 4) {
		    	mostViewed = mostViewed.subList(0, 4);
	        }
		   
		    request.setAttribute("blogs", blogs);
		    request.setAttribute("trendingBlogs", trendingBlogs);
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

		} catch (Exception e) {
		    e.printStackTrace();
		    request.setAttribute("error", "An error occurred while fetching the blogs.");
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/index.jsp");
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
