package com.wordweave.controllers;

import java.io.IOException;
import java.util.List;

import com.wordweave.models.BlogModel;
import com.wordweave.models.CategoryModel;
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
 * Servlet implementation class BlogDetailController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/blog", "/blog/*"})
public class BlogDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BlogService blogService = new BlogService();
	private UserService userService = new UserService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlogDetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pathInfo = request.getPathInfo();
		if (pathInfo != null && pathInfo.startsWith("/")) {
			pathInfo = pathInfo.substring(1); 
		}
		
		if (pathInfo != null && !pathInfo.isEmpty()) {
            try {
                int blogId = Integer.parseInt(pathInfo);
                BlogModel blog = blogService.getBlogById(blogId);
                UserModel user = userService.getUserById(blog.getAuthorId());
                blog.setAuthorImage(user.getProfile_picture());
                blog.setAuthorName(user.getFullname());
                blog.setAuthorBio(user.getBio());
                
                int currentViews = blog.getViews();
                blog.setViews(currentViews + 1);
                
                if (!blogService.updateBlogView(blog)) {
                	System.out.println("Blog Views: " + blog.getViews());
                	request.setAttribute("errror", "Blog Views Update Failed");
                }
                
                List<CategoryModel> categories = blog.getCategories();
                List<BlogModel> similarBlogs = blogService.getBlogsByCategories(categories);
                
                if (blog != null) {
                    // Set the blog details as request attribute
                    request.setAttribute("blog", blog);
                    request.setAttribute("similarBlogs", similarBlogs);
                } else {
                    request.setAttribute("error", "Blog not found");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Blog ID");
            } catch (Exception e) {
            	e.printStackTrace();
            }
        } else {
            request.setAttribute("error", "Blog ID is required");
        }

        // Forward to the blogdetail.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/blogdetail.jsp");
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
