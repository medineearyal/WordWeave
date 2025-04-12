package com.wordweave.services;

import com.wordweave.config.DbConfig;
import com.wordweave.models.BlogModel;
import com.wordweave.models.CategoryModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BlogService {
	private Connection dbConn;
	private boolean isConnectionError = false;
	private String getAllBlogsQuery = "SELECT b.*, u.fullname AS author_name FROM blog b JOIN user u ON b.author_id = u.user_id;";

	public BlogService() {
		try {
			dbConn = DbConfig.getDbConnection();
		} catch (SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
			isConnectionError = true;
		}
	}

	public int createBlog(BlogModel blog) {
		if (isConnectionError || dbConn == null) {
			return -1;
		}
			

		String sql = "INSERT INTO blog (image, content, title, author_id, publish_date) VALUES (?, ?, ?, ?, ?)";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, blog.getImage());
			stmt.setString(2, blog.getContent());
			stmt.setString(3, blog.getTitle());
			stmt.setInt(4, blog.getAuthorId());
			stmt.setDate(5, blog.getPublishDate());

			int affectedRows = stmt.executeUpdate();
			
			System.out.println("Affected Rows: " + affectedRows);

	        if (affectedRows > 0) {
	            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
	                if (generatedKeys.next()) {
	                	System.out.println("Generated Keys: " + generatedKeys.getInt(1));
	                    return generatedKeys.getInt(1);
	                }
	            }
	        }			

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public List<BlogModel> getAllBlogs() throws ClassNotFoundException {
		List<BlogModel> blogs = new ArrayList<>();
		String sql = getAllBlogsQuery;

		try (PreparedStatement stmt = dbConn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				BlogModel blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setImage(rs.getString("image"));
				blog.setContent(rs.getString("content"));
				blog.setTitle(rs.getString("title"));
				blog.setUpdatedAt(rs.getTimestamp("updated_at"));
				blog.setAuthorId(rs.getInt("author_id"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setCategories(getCategoriesForBlog(blog.getBlogId()));
				blog.setAuthorName(rs.getString("author_name"));

				blogs.add(blog);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blogs;
	}

	public BlogModel getBlogById(int id) throws ClassNotFoundException {
		BlogModel blog = null;
		String sql = "SELECT * FROM blog WHERE blog_id = ?";

		try (Connection conn = DbConfig.getDbConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setImage(rs.getString("image"));
				blog.setContent(rs.getString("content"));
				blog.setTitle(rs.getString("title"));
				blog.setUpdatedAt(rs.getTimestamp("updated_at"));
				blog.setAuthorId(rs.getInt("author_id"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setCategories(getCategoriesForBlog(id));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blog;
	}

	public boolean updateBlog(BlogModel blog) {
		if (isConnectionError || dbConn == null)
			return false;

		String sql = "UPDATE blog SET image = ?, content = ?, title = ?, updated_at = ?, author_id = ?, publish_date = ? WHERE blog_id = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setString(1, blog.getImage());
			stmt.setString(2, blog.getContent());
			stmt.setString(3, blog.getTitle());
			stmt.setTimestamp(4, blog.getUpdatedAt());
			stmt.setInt(5, blog.getAuthorId());
			stmt.setDate(6, blog.getPublishDate());
			stmt.setInt(7, blog.getBlogId());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteBlog(int id) {
		String sql = "DELETE FROM blog WHERE blog_id = ?";
		try {
			PreparedStatement stmt = dbConn.prepareStatement(sql);
			stmt.setInt(1, id);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public void addCategoryToBlog(int blogId, int categoryId) throws SQLException {
		String query = "INSERT INTO blog_category (blog_id, category_id) VALUES (?, ?)";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, blogId);
			stmt.setInt(2, categoryId);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("Failed to add category to blog.", e);
		}
	}
	
	private List<CategoryModel> getCategoriesForBlog(int blogId) {
	    List<CategoryModel> categories = new ArrayList<>();
	    String sql = "SELECT c.category_id, c.name FROM category c "
	               + "JOIN blog_category bc ON c.category_id = bc.category_id "
	               + "WHERE bc.blog_id = ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        stmt.setInt(1, blogId);
	        ResultSet rs = stmt.executeQuery();
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
	
	public void clearCategoriesFromBlog(int blogId) throws SQLException {
	    String sql = "DELETE FROM blog_category WHERE blog_id = ?";
	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        stmt.setInt(1, blogId);
	        stmt.executeUpdate();
		}
	}


}