package com.wordweave.controllers.admin;

import java.io.IOException;
import java.util.List;

import com.mysql.cj.Session;
import com.wordweave.models.BlogModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.BlogService;
import com.wordweave.services.UserService;
import com.wordweave.utils.CookieUtil;
import com.wordweave.utils.SessionUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class DashboardController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/admin/dashboard" })
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BlogService blogService = new BlogService();
	private UserService userService = new UserService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userRole = (String) request.getAttribute("role");
		String username = (String) request.getAttribute("username");
		
		if (username == null && userRole == null) {
			response.sendRedirect("/WordWeave/login");
			return;
		}else {
			String role = userRole;
			
			int totalPublishedBlogs = 0;
			
			if (role.equals("user")) {
				UserModel user = userService.getUserByUsername(username);
				totalPublishedBlogs = blogService.getPublishedBlogsCountByAuthorThisWeek(user.getUser_id());
				
				int totalDrafts = blogService.getDraftBlogCountByAuthor(user.getUser_id());
				List<BlogModel> recentPublishedBlogs = blogService.getMostRecentBlogsByAuthor(user.getUser_id(), 3, false); 
				List<BlogModel> recentDraftedBlogs = blogService.getMostRecentBlogsByAuthor(user.getUser_id(), 3, true); 
				
				request.setAttribute("totalDrafts", totalDrafts);
				request.setAttribute("recentPublishedBlogs", recentPublishedBlogs);
				request.setAttribute("recentDraftedBlogs", recentDraftedBlogs);
			}else if (role.equals("admin")) {
				totalPublishedBlogs = blogService.getPublishedBlogsCountThisWeek();
				List<BlogModel> recentBlogs = blogService.getMostRecentBlogs(3, false);
				List<UserModel> recentUsers = userService.getMostRecentUsers(3);
				
				int approvedUsersCount = userService.getApprovedUsersCountByRole("user", true);
				int approvedModeratorsCount = userService.getApprovedUsersCountByRole("moderator", true);
				
				int pendingUsersCount = userService.getApprovedUsersCountByRole("user", false);
				int pendingModeratorsCount = userService.getApprovedUsersCountByRole("moderator", false);
				
				request.setAttribute("recentBlogs", recentBlogs);
				request.setAttribute("recentUsers", recentUsers);
				request.setAttribute("approvedUsersCount", approvedUsersCount);
				request.setAttribute("approvedModeratorsCount", approvedModeratorsCount);
				request.setAttribute("pendingUsersCount", pendingUsersCount);
				request.setAttribute("pendingModeratorsCount", pendingModeratorsCount);
			}else if (role.equals("moderator")) {
				totalPublishedBlogs = blogService.getPublishedBlogsCountThisWeek();
				
				List<BlogModel> recentPublishedBlogs = blogService.getMostRecentBlogs(3, false);
				List<BlogModel> recentDraftedBlogs = blogService.getMostRecentBlogs(3, true); 
				
				request.setAttribute("recentPublishedBlogs", recentPublishedBlogs);
				request.setAttribute("recentDraftedBlogs", recentDraftedBlogs);
			}
			
	
			request.setAttribute("role", role);
			request.setAttribute("totalPublishedBlogs", totalPublishedBlogs);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/admin/dashboard.jsp");
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
