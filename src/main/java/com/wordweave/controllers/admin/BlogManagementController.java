package com.wordweave.controllers.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.wordweave.services.BlogService;
import com.wordweave.services.CategoryService;
import com.wordweave.services.UserService;
import com.wordweave.utils.ImageUtil;
import com.wordweave.config.DbConfig;
import com.wordweave.models.BlogModel;
import com.wordweave.models.CategoryModel;

/**
 * Servlet implementation class BlogAdminController
 */
@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/blogs/" })
public class BlogManagementController extends HttpServlet {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private BlogService blogService = new BlogService();
	private UserService userService = new UserService();
	private CategoryService categoryService = new CategoryService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			try {
				List<BlogModel> blogs = blogService.getAllBlogs();
				List<CategoryModel> categories = categoryService.getAllCategories();
				request.setAttribute("blogs", blogs);
				request.setAttribute("categories", categories);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/WEB-INF/pages/admin/blogList.jsp").forward(request, response);

		} else if (action.equals("edit")) {
			try {
				int blogId = Integer.parseInt(request.getParameter("id"));
				BlogModel blog = blogService.getBlogById(blogId);
				String actionText = "Edit";
				request.setAttribute("blog", blog);
				List<CategoryModel> categories = categoryService.getAllCategories();
				request.setAttribute("categories", categories);
				request.setAttribute("users", userService.getAllUsers());
				request.setAttribute("actionText", actionText);
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("/WEB-INF/pages/admin/blogForm.jsp").forward(request, response);

		} else if (action.equals("delete")) {
			try {
				int blogId = Integer.parseInt(request.getParameter("id"));
				blogService.deleteBlog(blogId);
			} catch (Exception e) {
				e.printStackTrace();
			}
			response.sendRedirect("/WordWeave/admin/blogs");

		} else if (action.equals("create")) {
			String actionText = "Create";
			try {
				request.setAttribute("users", userService.getAllUsers());
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<CategoryModel> categories = categoryService.getAllCategories();
			request.setAttribute("categories", categories);

			request.setAttribute("actionText", actionText);
			request.getRequestDispatcher("/WEB-INF/pages/admin/blogForm.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		String content = request.getParameter("content");
		String title = request.getParameter("title");
		int authorId = Integer.parseInt(request.getParameter("author_id"));
		Date publishDate = Date.valueOf(request.getParameter("publish_date")); // format: YYYY-MM-DD

		// Handle image upload
		String imagePath = null;
		try {
			ImageUtil imageUtil = new ImageUtil();
			boolean isImageUploaded = imageUtil.uploadImage(request, "image");
			if (isImageUploaded) {
				imagePath = "/images/" + request.getPart("image").getSubmittedFileName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (action.equals("create")) {
			BlogModel blog = new BlogModel(imagePath, content, title, authorId, publishDate);
			int blogId = blogService.createBlog(blog);
			blog.setBlogId(blogId);
			
			String[] selectedCategories = request.getParameterValues("categories");
			System.out.println("Selected Categories: " + selectedCategories);
			
			if (selectedCategories != null) {
		        for (String categoryId : selectedCategories) {
		        	System.out.println("CategoryId: " + categoryId);
		        	System.out.println("BlogId: " + blog.getBlogId());
		            try {
						blogService.addCategoryToBlog(blog.getBlogId(), Integer.parseInt(categoryId));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		    }
			
			response.sendRedirect("/WordWeave/admin/blogs/");
		} else if (action.equals("edit")) {
			int blogId = Integer.parseInt(request.getParameter("id"));
		    Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

		    BlogModel blog = new BlogModel(blogId, imagePath, content, title, updatedAt, authorId, publishDate);
		    blogService.updateBlog(blog);

		    // First, remove all existing category mappings for this blog
		    try {
		        blogService.clearCategoriesFromBlog(blogId);
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }

		    // Then, add the new selected categories
		    String[] selectedCategories = request.getParameterValues("categories");
		    if (selectedCategories != null) {
		        for (String categoryId : selectedCategories) {
		            try {
		                blogService.addCategoryToBlog(blogId, Integer.parseInt(categoryId));
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
		        }
		    }

		    response.sendRedirect("/WordWeave/admin/blogs/");

		}
	}
}
