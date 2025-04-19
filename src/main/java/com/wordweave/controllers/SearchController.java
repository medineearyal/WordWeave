package com.wordweave.controllers;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.wordweave.models.BlogModel;
import com.wordweave.services.BlogService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchController
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/search" })
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final BlogService blogService = new BlogService();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Capture the search query from the request
        String searchQuery = request.getParameter("query");
        
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            // Search for blogs matching the query in title or content
            List<BlogModel> searchResults = blogService.searchBlogs(searchQuery);
            request.setAttribute("searchResults", searchResults);
        }else {
        	List<BlogModel> searchResults;
			try {
				searchResults = blogService.getAllBlogs();
				request.setAttribute("searchResults", searchResults);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
        // Forward to the search results page
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pages/client/search.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
