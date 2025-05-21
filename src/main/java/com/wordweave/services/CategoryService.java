package com.wordweave.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wordweave.config.DbConfig;
import com.wordweave.models.CategoryModel;

public class CategoryService {

    private Connection connection;

    /**
     * Constructor to initialize the database connection.
     * Attempts to get a connection from DbConfig.
     * Prints stack trace if connection fails.
     */
    public CategoryService() {
        try {
			this.connection = DbConfig.getDbConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Inserts a new category into the database.
     *
     * @param category The CategoryModel object containing the category name.
     */
    public void createCategory(CategoryModel category) {
        String query = "INSERT INTO category (name) VALUES (?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return List of CategoryModel objects representing all categories.
     */
    public List<CategoryModel> getAllCategories() {
        List<CategoryModel> categories = new ArrayList<>();
        String query = "SELECT * FROM category";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CategoryModel category = new CategoryModel();
                category.setCategory_id(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param id The ID of the category to retrieve.
     * @return CategoryModel object if found, otherwise null.
     */
    public CategoryModel getCategoryById(int id) {
        String query = "SELECT * FROM category WHERE category_id = ?";
        CategoryModel category = null;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    category = new CategoryModel();
                    category.setCategory_id(rs.getInt("category_id"));
                    category.setName(rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }

    /**
     * Updates an existing category in the database.
     *
     * @param category The CategoryModel object containing the updated data.
     */
    public void updateCategory(CategoryModel category) {
        String query = "UPDATE category SET name = ? WHERE category_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getCategory_id());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a category from the database by its ID.
     *
     * @param id The ID of the category to delete.
     */
    public void deleteCategory(int id) {
        String query = "DELETE FROM category WHERE category_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
