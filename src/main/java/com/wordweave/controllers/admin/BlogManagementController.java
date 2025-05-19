package com.wordweave.controllers.admin;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.wordweave.models.BlogModel;
import com.wordweave.models.CategoryModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.BlogService;
import com.wordweave.services.CategoryService;
import com.wordweave.services.FavoriteBlogModel;
import com.wordweave.services.UserService;
import com.wordweave.utils.ImageUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BlogAdminController
 */
@MultipartConfig
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/blogs" })
public class BlogManagementController extends HttpServlet {
	/**
	 *
	 */

	private static final long serialVersionUID = 1L;
	private BlogService blogService = new BlogService();
	private UserService userService = new UserService();
	private CategoryService categoryService = new CategoryService();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		
		String userRole = (String) request.getAttribute("role");
		String username = (String) request.getAttribute("username");
		
		String role= "";
	
		
		if (username == null && userRole == null) {
			response.sendRedirect("/wordweave/login");
			return;
		}else {
			role = userRole;
			Boolean canEdit = false;
			Boolean canDelete = false;
			Boolean canView = false;
			Boolean canApprove = false;
			Boolean canCreate = false;
			
			UserModel user = userService.getUserByUsername(username);
			
			if (role.equals("user")) {
				canEdit = true;
				canView = true;
				canCreate = true;
			}else if (role.equals("admin")) {
				canEdit = true;
				canView = true;
				canDelete = true;
				canCreate = true;
			}else if (role.equals("moderator")) {
				canView = true;
				canApprove = true;
			}
			
	
			request.setAttribute("role", role);
			request.setAttribute("user", user);
			request.setAttribute("canEdit", canEdit);
			request.setAttribute("canDelete", canDelete);
			request.setAttribute("canView", canView);
			request.setAttribute("canApprove", canApprove);
			request.setAttribute("canCreate", canCreate);
		}
		

		if (action == null) {
			try {
				List<BlogModel> blogs;
				if (role.equals("user")) {
					blogs = blogService.getEveryBlogs(username);
				}else {
					blogs = blogService.getEveryBlogs();
				}
				
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
			response.sendRedirect("/wordweave/admin/blogs");

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
		}else if (action.equals("toggle-trending")) {
			try {
				int blogId = Integer.parseInt(request.getParameter("id"));
				BlogModel blog = blogService.getBlogById(blogId);
				Timestamp updatedAt = new Timestamp(System.currentTimeMillis());
				
				if (blog.getIsTrending() == null || blog.getIsTrending() == false) {
					blog.setIsTrending(true);
				}else {
					blog.setIsTrending(false);
				}
				
				blog.setUpdatedAt(updatedAt);
				if (blogService.updateBlogIsTrending(blog)) {
					request.setAttribute("success", "Blog Successfully Updated");
				}else {
					request.setAttribute("error", "Blog Update Error");
				}
				
				System.out.println(blog.getIsTrending());
				
				response.sendRedirect(request.getContextPath() + "/admin/blogs");
				return;
				
			} catch(Exception e) {
				request.setAttribute("error", e);
				request.getRequestDispatcher("/admin/blogs").forward(request, response);
				
			}
		}else if (action.equals("toggle-favorite")) {
		    try {
		        int blogId = Integer.parseInt(request.getParameter("blogId"));
		        username = request.getParameter("username");
		        
		        // Retrieve user ID from username
		        UserModel user = userService.getUserByUsername(username);
		        
		        // Check if the blog is already marked as a favorite
		        boolean isFavorite = blogService.isBlogFavorite(user.getUser_id(), blogId);
		        
		        System.out.println(isFavorite);

		        if (isFavorite) {
		            boolean isRemoved = blogService.removeFromFavorite(user.getUser_id(), blogId);
		            
		            response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");

		            String jsonResponse = "{";
		            if (isRemoved) {
		                jsonResponse += "\"status\": \"success\", \"message\": \"Successfully removed from favorites.\"";
		            } else {
		                jsonResponse += "\"status\": \"failure\", \"message\": \"Failed to remove from favorites.\"";
		            }
		            jsonResponse += "}";

		            response.getWriter().write(jsonResponse);
		        } else {
		            FavoriteBlogModel favorite = new FavoriteBlogModel(user.getUser_id(), blogId);
		            boolean isAdded = blogService.addToFavorite(favorite);

		            // Respond with JSON response for adding
		            response.setContentType("application/json");
		            response.setCharacterEncoding("UTF-8");

		            String jsonResponse = "{";
		            if (isAdded) {
		                jsonResponse += "\"status\": \"success\", \"message\": \"Successfully added to favorites.\"";
		            } else {
		                jsonResponse += "\"status\": \"failure\", \"message\": \"Failed to add to favorites.\"";
		            }
		            jsonResponse += "}";

		            response.getWriter().write(jsonResponse);
		        }

		    } catch (Exception e) {
		        // Handle error
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");

		        String errorResponse = "{ \"status\": \"error\", \"message\": \"" + e.getMessage() + "\" }";
		        response.getWriter().write(errorResponse);
		        e.printStackTrace();
		    }
		}else if (action.equals("toggle-draft")) {
		    try {
		        int blogId = Integer.parseInt(request.getParameter("id"));  // Get the blog ID from the request
		        BlogModel blog = blogService.getBlogById(blogId);  // Retrieve the blog from the database
		        Timestamp updatedAt = new Timestamp(System.currentTimeMillis());  // Get the current timestamp for the update
		        
		        // Toggle the draft status	        
		        if (blog.getIsDraft() == null || blog.getIsDraft() == true) {
		            blog.setIsDraft(false);  // Set to published
		        } else {
		            blog.setIsDraft(true);  // Set to draft
		        }
		        

		        blog.setUpdatedAt(updatedAt);  // Update the `updatedAt` timestamp

		        // Update the blog in the database
		        boolean isUpdated = blogService.updateBlogStatus(blog);  // Assuming `updateBlog` updates the draft status too
		        
		        if (isUpdated) {
					request.setAttribute("success", "Blog Successfully Updated");
				}else {
					request.setAttribute("error", "Blog Update Error");
				}
				
				response.sendRedirect(request.getContextPath() + "/admin/blogs");
				return;

		    } catch (Exception e) {
		        // Handle error
		        response.setContentType("application/json");
		        response.setCharacterEncoding("UTF-8");

		        String errorResponse = "{ \"status\": \"error\", \"message\": \"" + e.getMessage() + "\" }";
		        response.getWriter().write(errorResponse);
		    }
		}
	}

	@Override
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

			if (selectedCategories != null) {
		        for (String categoryId : selectedCategories) {
		            try {
						blogService.addCategoryToBlog(blog.getBlogId(), Integer.parseInt(categoryId));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.getSession().setAttribute("error", "Blog Faild to Create");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						request.getSession().setAttribute("error", "Blog Faild to Create");
					}
		        }
		    }
			request.getSession().setAttribute("success", "Blog Successfully Created");
			response.sendRedirect("/wordweave/admin/blogs");
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
		                request.getSession().setAttribute("error", "Category Faild to Fetch");
		            }
		        }
		    }
		    
		    request.getSession().setAttribute("success", "Blog Successfully Edited");
		    response.sendRedirect("/wordweave/admin/blogs");

		}
	}
}
