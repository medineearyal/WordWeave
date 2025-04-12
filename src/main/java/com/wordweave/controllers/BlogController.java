package com.wordweave.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.wordweave.models.BlogModel;
import com.wordweave.services.BlogService;

/**
 * Servlet implementation class BlogController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/blogs" })
public class BlogController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BlogService blogService = new BlogService();
       
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<BlogModel> blogs;
		try {
			blogs = blogService.getAllBlogs();
			request.setAttribute("blogs", blogs);
			
			System.out.println("blogs: " + blogs);
			
			for (BlogModel blogModel : blogs) {
				System.out.println("Blog id: " + blogModel.getBlogId());
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
