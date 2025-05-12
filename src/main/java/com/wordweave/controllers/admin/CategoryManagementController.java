package com.wordweave.controllers.admin;

import java.io.IOException;
import java.util.List;

import com.wordweave.models.CategoryModel;
import com.wordweave.services.CategoryService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(asyncSupported = true, urlPatterns = { "/admin/categories" })
public class CategoryManagementController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CategoryService categoryService = new CategoryService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
		String userRole = (String) request.getAttribute("role");
		String username = (String) request.getAttribute("username");
		
		if (username == null && userRole == null) {
			response.sendRedirect("/WordWeave/login");
			return;
		}

        String action = request.getParameter("action");

        try {
            if (action == null) {
                List<CategoryModel> categories = categoryService.getAllCategories();
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/pages/admin/categoryList.jsp").forward(request, response);

            } else if (action.equals("create")) {
                request.setAttribute("actionText", "Create");
                request.getRequestDispatcher("/WEB-INF/pages/admin/categoryForm.jsp").forward(request, response);

            } else if (action.equals("edit")) {
                int categoryId = Integer.parseInt(request.getParameter("id"));
                CategoryModel category = categoryService.getCategoryById(categoryId);
                request.setAttribute("category", category);
                request.setAttribute("actionText", "Edit");
                request.getRequestDispatcher("/WEB-INF/pages/admin/categoryForm.jsp").forward(request, response);

            } else if (action.equals("delete")) {
                int categoryId = Integer.parseInt(request.getParameter("id"));
                categoryService.deleteCategory(categoryId);
                
                request.getSession().setAttribute("success", "Category Successfully Deleted.");
                response.sendRedirect("/WordWeave/admin/categories");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/WordWeave/admin/categories");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String name = request.getParameter("name");

        try {
            if ("create".equals(action)) {
                CategoryModel category = new CategoryModel();
                category.setName(name);
                categoryService.createCategory(category);
                
                request.getSession().setAttribute("success", "Category Successfully Created.");
            } else if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                CategoryModel category = new CategoryModel();
                category.setCategory_id(id);
                category.setName(name);
                categoryService.updateCategory(category);
                
                request.getSession().setAttribute("success", "Category Successfully Updated.");
            }

            response.sendRedirect("/WordWeave/admin/categories");
            
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "Failed To Create/Edit Cateogry.");
            response.sendRedirect("/WordWeave/admin/categories");
        }
    }
}
