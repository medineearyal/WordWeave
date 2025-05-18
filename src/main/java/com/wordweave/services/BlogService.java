package com.wordweave.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wordweave.config.DbConfig;
import com.wordweave.models.BlogModel;
import com.wordweave.models.CategoryModel;

public class BlogService {
	private Connection dbConn;
	private boolean isConnectionError = false;
	private String getAllBlogsQuery = "SELECT b.*, u.fullname AS author_name FROM blog b JOIN user u ON b.author_id = u.user_id WHERE is_draft = 0;";

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

		try (PreparedStatement stmt = dbConn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
				blog.setIsTrending(rs.getBoolean("is_trending"));
				blog.setViews(rs.getInt("views"));

				blogs.add(blog);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blogs;
	}

	public List<BlogModel> getEveryBlogs() throws ClassNotFoundException {
		List<BlogModel> blogs = new ArrayList<>();
		String sql = "SELECT b.*, u.fullname AS author_name FROM blog b JOIN user u ON b.author_id = u.user_id ORDER BY updated_at DESC;";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
				blog.setIsTrending(rs.getBoolean("is_trending"));
				blog.setViews(rs.getInt("views"));
				blog.setIsDraft(rs.getBoolean("is_draft"));

				blogs.add(blog);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blogs;
	}

	public List<BlogModel> getEveryBlogs(String username) throws ClassNotFoundException {
		List<BlogModel> blogs = new ArrayList<>();
		String sql = "SELECT b.*, u.fullname AS author_name FROM blog b JOIN user u ON b.author_id = u.user_id WHERE u.username = ? ORDER BY updated_at DESC;";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setString(1, username);

			try (ResultSet rs = stmt.executeQuery()) {
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
					blog.setIsTrending(rs.getBoolean("is_trending"));
					blog.setViews(rs.getInt("views"));
					blog.setIsDraft(rs.getBoolean("is_draft"));

					blogs.add(blog);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blogs;
	}

	public List<BlogModel> getAllTrendingBlogs() {
		List<BlogModel> blogs = new ArrayList<>();
		String sql = "SELECT b.*, u.fullname AS author_name FROM blog b JOIN user u ON b.author_id = u.user_id WHERE is_trending = true and is_draft = 0 ORDER BY updated_at DESC;";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

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
				blog.setIsTrending(rs.getBoolean("is_trending"));
				blog.setViews(rs.getInt("views"));

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
				blog.setViews(rs.getInt("views"));
				blog.setIsTrending(rs.getBoolean("is_trending"));
				blog.setIsDraft(rs.getBoolean("is_draft"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blog;
	}

	public boolean updateBlog(BlogModel blog) {
		if (isConnectionError || dbConn == null) {
			return false;
		}

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

	public boolean updateBlogStatus(BlogModel blog) {
		if (isConnectionError || dbConn == null) {
			return false;
		}

		String sql = "UPDATE blog SET is_draft = ? WHERE blog_id = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setBoolean(1, blog.getIsDraft());
			stmt.setInt(2, blog.getBlogId());

			return stmt.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateBlogIsTrending(BlogModel blog) {
		if (isConnectionError || dbConn == null) {
			return false;
		}

		String sql = "UPDATE blog SET is_trending = ? WHERE blog_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setBoolean(1, blog.getIsTrending());
			stmt.setInt(2, blog.getBlogId());

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
				+ "JOIN blog_category bc ON c.category_id = bc.category_id " + "WHERE bc.blog_id = ?";

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

	public List<BlogModel> getBlogsByCategories(List<CategoryModel> categories) {
		List<BlogModel> similarBlogs = new ArrayList<>();
		List<BlogModel> allBlogs;

		try {
			allBlogs = this.getAllBlogs();

			Set<String> providedCategoryTitles = new HashSet<>();
			for (CategoryModel category : categories) {
				providedCategoryTitles.add(category.getName());
			}

			for (BlogModel blog : allBlogs) {
				Set<String> blogCategoryTitles = new HashSet<>();
				for (CategoryModel category : blog.getCategories()) {
					blogCategoryTitles.add(category.getName());
				}

				if (!Collections.disjoint(providedCategoryTitles, blogCategoryTitles)) {
					similarBlogs.add(blog);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return similarBlogs;
	}

	public Boolean addToFavorite(FavoriteBlogModel favorite) {
		String query = "INSERT INTO favorite_blogs (blog_id, user_id) VALUES (?, ?)";
		System.out.println("User Id : " + favorite.getUserId());
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, favorite.getBlogId());
			stmt.setInt(2, favorite.getUserId());

			// Use executeUpdate() for INSERT operations
			int rowsAffected = stmt.executeUpdate(); // executeUpdate returns the number of affected rows
			return rowsAffected > 0; // Return true if the insertion was successful, false otherwise
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<BlogModel> getFavoriteBlogsByUser(int userId) {
		List<BlogModel> favoriteBlogs = new ArrayList<>();

		String query = "SELECT b.* FROM blog b JOIN favorite_blogs fb ON b.blog_id = fb.blog_id WHERE fb.user_id = ? ORDER BY b.updated_at DESC";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, userId);

			ResultSet rs = stmt.executeQuery();

			// Iterate over the result set and map the rows to BlogModel objects
			while (rs.next()) {
				BlogModel blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setTitle(rs.getString("title"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setContent(rs.getString("content"));
				blog.setImage(rs.getString("image"));

				favoriteBlogs.add(blog);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return favoriteBlogs; // Return the list of favorite blogs
	}

	public List<Integer> getFavoriteBlogIdsByUser(int userId) {
		List<Integer> favoriteBlogIds = new ArrayList<>();

		// SQL query to get all blog ids for the given userId
		String query = "SELECT blog_id FROM favorite_blogs WHERE user_id = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, userId); // Set the user ID to the query

			ResultSet rs = stmt.executeQuery(); // Execute the query

			// Iterate over the result set to fetch blog IDs
			while (rs.next()) {
				int blogId = rs.getInt("blog_id"); // Get the blog_id
				favoriteBlogIds.add(blogId); // Add it to the list
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return favoriteBlogIds; // Return the list of blog IDs
	}

	public Boolean updateBlogView(BlogModel blog) {
		String sql = "UPDATE blog SET views = ? WHERE blog_id = ?;";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, blog.getViews());
			stmt.setInt(2, blog.getBlogId());

			System.out.println("Blog Views: " + blog.getViews());

			int rowsAffected = stmt.executeUpdate(); // Execute the query
			System.out.println("Rows Affected : " + rowsAffected);

			if (rowsAffected > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public List<BlogModel> getAllMostViewedBlogs() {
		List<BlogModel> mostViewedBlogs = new ArrayList<>();

		// SQL query to select blogs ordered by views count and join with users to get
		// the author's name
		String query = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.views, b.image, u.fullname AS author_name "
				+ "FROM blog b JOIN user u ON b.author_id = u.user_id WHERE is_draft = 0 ORDER BY b.views DESC";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			ResultSet rs = stmt.executeQuery();

			// Iterate over the result set and map the rows to BlogModel objects
			while (rs.next()) {
				BlogModel blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setTitle(rs.getString("title"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setContent(rs.getString("content"));
				blog.setViews(rs.getInt("views"));
				blog.setAuthorName(rs.getString("author_name")); // Set the author's name
				blog.setImage(rs.getString("image"));

				mostViewedBlogs.add(blog); // Add the blog to the list
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mostViewedBlogs;
	}

	public List<BlogModel> searchBlogs(String query) {
		List<BlogModel> blogs = new ArrayList<>();

		// SQL query to search for blogs by title, content, or author's name
		// (case-insensitive search)
		String sql = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.image, u.fullname AS author_name "
				+ "FROM blog b " + "JOIN user u ON b.author_id = u.user_id "
				+ "WHERE LOWER(b.title) LIKE ? OR LOWER(b.content) LIKE ? OR LOWER(u.fullname) LIKE ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			String searchTerm = "%" + query.toLowerCase() + "%";
			stmt.setString(1, searchTerm); // Search in the title
			stmt.setString(2, searchTerm); // Search in the content
			stmt.setString(3, searchTerm); // Search in the author's name

			ResultSet rs = stmt.executeQuery();

			// Map the result set to BlogModel objects
			while (rs.next()) {
				BlogModel blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setTitle(rs.getString("title"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setContent(rs.getString("content"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setImage(rs.getString("image"));

				blogs.add(blog); // Add the blog to the list
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return blogs; // Return the list of blogs
	}

	public Boolean isBlogFavorite(int userId, int blogId) {
		String query = "SELECT COUNT(*) FROM favorite_blogs WHERE user_id = ? AND blog_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, blogId);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean removeFromFavorite(int userId, int blogId) {
		String query = "DELETE FROM favorite_blogs WHERE user_id = ? AND blog_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, blogId);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int getPublishedBlogsCountThisWeek() {
		String sql = "SELECT COUNT(*) FROM blog WHERE is_draft = 0 AND WEEK(publish_date, 1) = WEEK(CURDATE(), 1) AND YEAR(publish_date) = YEAR(CURDATE())";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1); // Return the count of blogs
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0; // Return 0 if there is an error or no blogs found
	}

	public int getPublishedBlogsCountByAuthorThisWeek(int authorId) {
		String sql = "SELECT COUNT(*) FROM blog WHERE is_draft = 0 AND author_id = ? "
				+ "AND WEEK(publish_date, 1) = WEEK(CURDATE(), 1) " + "AND YEAR(publish_date) = YEAR(CURDATE())";

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, authorId);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public int getPublishedBlogsCount() {
		String sql = "SELECT COUNT(*) FROM blog WHERE is_draft = 0;"; // Filter by current week

		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1); // Return the count of blogs for the given author for the current week
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0; // Return 0 if there is an error or no published blogs for this author in the
					// current week
	}

	public int getDraftBlogCountByAuthor(int userId) {
		int draftCount = 0;

		// Query to count the number of draft blogs for the given user_id
		String query = "SELECT COUNT(*) FROM blog WHERE is_draft = true AND author_id = ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, userId); // Set the user_id parameter in the query
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				draftCount = rs.getInt(1); // Get the count of draft blogs from the result set
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return draftCount; // Return the count of draft blogs
	}

	public List<BlogModel> getMostRecentBlogs(int limit, Boolean is_draft) {
		List<BlogModel> recentBlogs = new ArrayList<>();

		// Query to select blogs ordered by the publish date in descending order
		// You can also use `updated_at` instead of `publish_date` depending on your use
		// case
		String query = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.image, u.fullname AS author_name "
				+ "FROM blog b " + "JOIN user u ON b.author_id = u.user_id " + "WHERE is_draft = ? "
				+ "ORDER BY b.publish_date DESC " + "LIMIT ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setBoolean(1, is_draft);
			stmt.setInt(2, limit); // Set the limit parameter to get the most recent blogs

			ResultSet rs = stmt.executeQuery();

			// Iterate over the result set and map the rows to BlogModel objects
			while (rs.next()) {
				BlogModel blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setTitle(rs.getString("title"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setContent(rs.getString("content"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setImage(rs.getString("image"));

				recentBlogs.add(blog); // Add the blog to the list
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recentBlogs; // Return the list of most recent blogs
	}

	public List<BlogModel> getMostRecentBlogsByAuthor(int authorId, int limit, Boolean is_draft) {
		List<BlogModel> recentBlogs = new ArrayList<>();

		// Query to select blogs ordered by the publish date in descending order,
		// filtered by author_id
		String query = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.image, u.fullname AS author_name "
				+ "FROM blog b " + "JOIN user u ON b.author_id = u.user_id " + "WHERE b.author_id = ? AND is_draft = ? "
				+ // Add space after the condition
				"ORDER BY b.publish_date DESC " + "LIMIT ?";

		try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
			stmt.setInt(1, authorId); // Set the author_id parameter to filter by author
			stmt.setBoolean(2, is_draft); // Set the limit parameter to restrict the number of results
			stmt.setInt(3, limit);
			ResultSet rs = stmt.executeQuery();

			// Iterate over the result set and map the rows to BlogModel objects
			while (rs.next()) {
				BlogModel blog = new BlogModel();
				blog.setBlogId(rs.getInt("blog_id"));
				blog.setTitle(rs.getString("title"));
				blog.setPublishDate(rs.getDate("publish_date"));
				blog.setContent(rs.getString("content"));
				blog.setAuthorName(rs.getString("author_name"));
				blog.setImage(rs.getString("image"));

				recentBlogs.add(blog); // Add the blog to the list
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return recentBlogs; // Return the list of most recent blogs filtered by author_id
	}

}