package com.wordweave.filters;

import java.io.IOException;
import java.util.Map;

import com.wordweave.models.BlogModel;
import com.wordweave.models.UserModel;
import com.wordweave.services.BlogService;
import com.wordweave.services.UserService;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8986607195582074099L;
	private static final String[] PUBLIC_PATHS = { "/", "/home", "/login", "/register", "/about", "/contact" };
	private static final String[] STATIC_RESOURCES = { "/css/", "/js/", "/images/" };
	private static final UserService userSerivce = new UserService();
	private static final BlogService blogService = new BlogService();

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public AuthFilter() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);

		String path = req.getRequestURI().substring(req.getContextPath().length());
		String servletPath = req.getServletPath();
		req.setAttribute("servletPath", servletPath);
		
		Map<String, String> staticTitles = Map.of(
			    "/", "Home",
			    "/login", "Login",
			    "/register", "Register",
			    "/blogs", "Blogs",
			    "/about", "About Us",
			    "/contact", "Contact Us",
			    "/search", "Search"
			);

			String pathInfo = req.getPathInfo();
			String title;

			if ("/blog".equals(servletPath)) {
			    if (pathInfo != null && pathInfo.length() > 1) {
			        try {
			            int id = Integer.parseInt(pathInfo.substring(1));
			            BlogModel blog = blogService.getBlogById(id);
			            title = (blog != null && blog.getTitle() != null) ? blog.getTitle() : "Blog Details";
			        } catch (NumberFormatException | ClassNotFoundException e) {
			            e.printStackTrace();
			            title = "Blog Details";
			        }
			    } else {
			        title = "Blog";
			    }
			} else {
			    title = staticTitles.getOrDefault(servletPath, "");
			}

			request.setAttribute("pageTitle", title);


		boolean isLoggedIn = session != null && session.getAttribute("username") != null
				&& session.getAttribute("role") != null;
		if (isLoggedIn) {
			req.setAttribute("username", session.getAttribute("username"));
			req.setAttribute("role", session.getAttribute("role"));

			UserModel user = userSerivce.getUserByUsername((String) session.getAttribute("username"));
			req.setAttribute("user", user);
		}

		boolean isPublic = isPublicPath(path) || isStaticResource(path);
		if (isLoggedIn || isPublic) {
			chain.doFilter(request, response); // proceed to servlet or JSP
		} else {
			res.sendRedirect(req.getContextPath() + "/login");
		}
	}

	private boolean isPublicPath(String path) {
		for (String pub : PUBLIC_PATHS) {
			if (path.startsWith(pub)) {
				return true;
			}
		}
		return false;
	}

	private boolean isStaticResource(String path) {
		for (String staticPath : STATIC_RESOURCES) {
			if (path.startsWith(staticPath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
