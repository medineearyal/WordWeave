package com.wordweave.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.wordweave.utils.CookieUtil;

/**
 * Servlet Filter implementation class AuthFilter
 */
@WebFilter("/*")
public class AuthFilter extends HttpFilter implements Filter {
	private static final String[] PUBLIC_PATHS = { "/", "/home", "/login", "/register", "/about", "/contact" };
	private static final String[] STATIC_RESOURCES = {"/css/", "/js/", "/images/"};

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
        
        boolean isLoggedIn = session != null && session.getAttribute("username") != null && session.getAttribute("role") != null;

        if (isLoggedIn) {
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            
            req.setAttribute("username", username);
            req.setAttribute("role", role);
        }

        boolean isPublic = isPublicPath(path) || isStaticResource(path);

        if (isLoggedIn || isPublic) {
            chain.doFilter(request, response);
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
