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
import com.wordweave.models.FavoriteBlogModel;

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

	/**
	 * Creates a new blog record in the database.
	 * @param blog The BlogModel object containing blog details to insert.
	 * @return The generated blog ID if insertion is successful; otherwise, -1.
	 */
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

	/**
	 * Retrieves all blogs using a predefined query (getAllBlogsQuery).
	 * @return List of all BlogModel objects.
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * Retrieves all blogs joined with author info, ordered by most recently updated.
	 * @return List of BlogModel objects with author names.
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * Retrieves blogs written by a specific user identified by username.
	 * @param username The username of the author.
	 * @return List of BlogModel objects for that user.
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * Retrieves all trending blogs that are not drafts, ordered by last update.
	 * @return List of trending BlogModel objects.
	 */
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

	/**
	 * Retrieves a blog by its ID.
	 * @param id Blog ID to fetch.
	 * @return BlogModel object if found, otherwise null.
	 * @throws ClassNotFoundException
	 */
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

	/**
	 * Updates an existing blog with new data.
	 * @param blog BlogModel object with updated data.
	 * @return true if update successful, false otherwise.
	 */
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

	/**
	 * Updates the draft status of a blog.
	 * @param blog BlogModel object containing the draft status and blog ID.
	 * @return true if update successful, false otherwise.
	 */
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

	/**
	 * Updates the trending status of a blog.
	 * @param blog BlogModel object containing the trending status and blog ID.
	 * @return true if update successful, false otherwise.
	 */
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

	/**
	 * Deletes a blog by its ID.
	 * @param id Blog ID to delete.
	 * @return true if deletion successful, false otherwise.
	 */
	public boolean deleteBlog(int id) {
	    String deleteFromBlogCategorySQL = "DELETE FROM blog_category WHERE blog_id = ?";
	    String deleteFromBlogSQL = "DELETE FROM blog WHERE blog_id = ?";
	    
	    try {
	        dbConn.setAutoCommit(false);

	        try (PreparedStatement stmt1 = dbConn.prepareStatement(deleteFromBlogCategorySQL)) {
	            stmt1.setInt(1, id);
	            stmt1.executeUpdate();
	        }

	        try (PreparedStatement stmt2 = dbConn.prepareStatement(deleteFromBlogSQL)) {
	            stmt2.setInt(1, id);
	            int rowsAffected = stmt2.executeUpdate();

	            dbConn.commit();
	            return rowsAffected > 0;
	        }

	    } catch (SQLException e) {
	        try {
	            dbConn.rollback();
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            dbConn.setAutoCommit(true);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return false;
	}

	/**
	 * Adds a category to a blog.
	 * @param blogId The blog ID.
	 * @param categoryId The category ID.
	 * @throws SQLException if insertion fails.
	 */
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

	/**
	 * Retrieves the list of categories for a given blog.
	 * @param blogId Blog ID to fetch categories for.
	 * @return List of CategoryModel objects linked to the blog.
	 */
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

	/**
	 * Removes all categories associated with a blog.
	 * @param blogId The blog ID.
	 * @throws SQLException if deletion fails.
	 */
	public void clearCategoriesFromBlog(int blogId) throws SQLException {
		String sql = "DELETE FROM blog_category WHERE blog_id = ?";
		try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
			stmt.setInt(1, blogId);
			stmt.executeUpdate();
		}
	}

	/**
	 * Finds blogs that share at least one category with the provided categories.
	 * @param categories List of categories to match.
	 * @return List of BlogModel objects sharing categories.
	 */
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

	/**
	 * Adds a blog to the user's favorites.
	 * @param favorite the FavoriteBlogModel containing userId and blogId
	 * @return true if the insertion was successful, false otherwise
	 */
	public Boolean addToFavorite(FavoriteBlogModel favorite) {
	    String query = "INSERT INTO favorite_blogs (blog_id, user_id) VALUES (?, ?)";
	    System.out.println("User Id : " + favorite.getUserId());
	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, favorite.getBlogId());
	        stmt.setInt(2, favorite.getUserId());

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Retrieves the list of favorite blogs for a specific user.
	 * @param userId the ID of the user
	 * @return a list of BlogModel objects representing the user's favorite blogs
	 */
	public List<BlogModel> getFavoriteBlogsByUser(int userId) {
	    List<BlogModel> favoriteBlogs = new ArrayList<>();
	    String query = "SELECT b.* FROM blog b JOIN favorite_blogs fb ON b.blog_id = fb.blog_id WHERE fb.user_id = ? ORDER BY b.updated_at DESC";

	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, userId);

	        ResultSet rs = stmt.executeQuery();

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

	    return favoriteBlogs;
	}

	/**
	 * Retrieves the list of favorite blog IDs for a specific user.
	 * @param userId the ID of the user
	 * @return a list of integers representing blog IDs favorited by the user
	 */
	public List<Integer> getFavoriteBlogIdsByUser(int userId) {
	    List<Integer> favoriteBlogIds = new ArrayList<>();
	    String query = "SELECT blog_id FROM favorite_blogs WHERE user_id = ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, userId);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            int blogId = rs.getInt("blog_id");
	            favoriteBlogIds.add(blogId);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return favoriteBlogIds;
	}

	/**
	 * Updates the view count of a blog.
	 * @param blog the BlogModel containing the blog ID and new views count
	 * @return true if update was successful, false otherwise
	 */
	public Boolean updateBlogView(BlogModel blog) {
	    String sql = "UPDATE blog SET views = ? WHERE blog_id = ?;";
	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        stmt.setInt(1, blog.getViews());
	        stmt.setInt(2, blog.getBlogId());

	        System.out.println("Blog Views: " + blog.getViews());

	        int rowsAffected = stmt.executeUpdate();
	        System.out.println("Rows Affected : " + rowsAffected);

	        return rowsAffected > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}

	/**
	 * Retrieves the most viewed blogs that are not drafts, ordered by view count.
	 * @return a list of BlogModel objects representing the most viewed blogs
	 */
	public List<BlogModel> getAllMostViewedBlogs() {
	    List<BlogModel> mostViewedBlogs = new ArrayList<>();

	    String query = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.views, b.image, u.fullname AS author_name "
	            + "FROM blog b JOIN user u ON b.author_id = u.user_id WHERE is_draft = 0 ORDER BY b.views DESC";

	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BlogModel blog = new BlogModel();
	            blog.setBlogId(rs.getInt("blog_id"));
	            blog.setTitle(rs.getString("title"));
	            blog.setPublishDate(rs.getDate("publish_date"));
	            blog.setContent(rs.getString("content"));
	            blog.setViews(rs.getInt("views"));
	            blog.setAuthorName(rs.getString("author_name"));
	            blog.setImage(rs.getString("image"));

	            mostViewedBlogs.add(blog);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return mostViewedBlogs;
	}

	/**
	 * Searches blogs by matching title, content, or author name (case-insensitive).
	 * @param query the search term
	 * @return a list of BlogModel objects matching the search criteria
	 */
	public List<BlogModel> searchBlogs(String query) {
	    List<BlogModel> blogs = new ArrayList<>();

	    String sql = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.image, u.fullname AS author_name "
	            + "FROM blog b JOIN user u ON b.author_id = u.user_id "
	            + "WHERE LOWER(b.title) LIKE ? OR LOWER(b.content) LIKE ? OR LOWER(u.fullname) LIKE ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        String searchTerm = "%" + query.toLowerCase() + "%";
	        stmt.setString(1, searchTerm);
	        stmt.setString(2, searchTerm);
	        stmt.setString(3, searchTerm);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BlogModel blog = new BlogModel();
	            blog.setBlogId(rs.getInt("blog_id"));
	            blog.setTitle(rs.getString("title"));
	            blog.setPublishDate(rs.getDate("publish_date"));
	            blog.setContent(rs.getString("content"));
	            blog.setAuthorName(rs.getString("author_name"));
	            blog.setImage(rs.getString("image"));

	            blogs.add(blog);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return blogs;
	}

	/**
	 * Checks if a blog is marked as favorite by a specific user.
	 * @param userId the user ID
	 * @param blogId the blog ID
	 * @return true if the blog is favorited by the user, false otherwise
	 */
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

	/**
	 * Removes a blog from a user's favorites.
	 * @param userId the user ID
	 * @param blogId the blog ID
	 * @return true if the deletion was successful, false otherwise
	 */
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

	/**
	 * Gets the count of published blogs in the current week.
	 * @return the count of published blogs this week
	 */
	public int getPublishedBlogsCountThisWeek() {
	    String sql = "SELECT COUNT(*) FROM blog WHERE is_draft = 0 AND WEEK(publish_date, 1) = WEEK(CURDATE(), 1) AND YEAR(publish_date) = YEAR(CURDATE())";

	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}

	/**
	 * Gets the count of published blogs by a specific author in the current week.
	 * @param authorId the author ID
	 * @return the count of published blogs by the author this week
	 */
	public int getPublishedBlogsCountByAuthorThisWeek(int authorId) {
	    String sql = "SELECT COUNT(*) FROM blog WHERE is_draft = 0 AND author_id = ? "
	            + "AND WEEK(publish_date, 1) = WEEK(CURDATE(), 1) "
	            + "AND YEAR(publish_date) = YEAR(CURDATE())";

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

	/**
	 * Gets the total count of published blogs (not drafts).
	 * @return the count of all published blogs
	 */
	public int getPublishedBlogsCount() {
	    String sql = "SELECT COUNT(*) FROM blog WHERE is_draft = 0;";

	    try (PreparedStatement stmt = dbConn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            return rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return 0;
	}

	/**
	 * Gets the count of draft blogs for a specific author.
	 * @param userId the author/user ID
	 * @return the count of draft blogs
	 */
	public int getDraftBlogCountByAuthor(int userId) {
	    int draftCount = 0;
	    String query = "SELECT COUNT(*) FROM blog WHERE is_draft = true AND author_id = ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, userId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            draftCount = rs.getInt(1);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return draftCount;
	}

	/**
	 * Retrieves the most recent blogs filtered by draft status.
	 * @param limit the maximum number of blogs to return
	 * @param is_draft the draft status filter (true for drafts, false for published)
	 * @return a list of BlogModel objects representing the most recent blogs
	 */
	public List<BlogModel> getMostRecentBlogs(int limit, Boolean is_draft) {
	    List<BlogModel> recentBlogs = new ArrayList<>();

	    String query = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.image, u.fullname AS author_name "
	            + "FROM blog b JOIN user u ON b.author_id = u.user_id WHERE is_draft = ? "
	            + "ORDER BY b.publish_date DESC LIMIT ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setBoolean(1, is_draft);
	        stmt.setInt(2, limit);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BlogModel blog = new BlogModel();
	            blog.setBlogId(rs.getInt("blog_id"));
	            blog.setTitle(rs.getString("title"));
	            blog.setPublishDate(rs.getDate("publish_date"));
	            blog.setContent(rs.getString("content"));
	            blog.setAuthorName(rs.getString("author_name"));
	            blog.setImage(rs.getString("image"));

	            recentBlogs.add(blog);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return recentBlogs;
	}

	/**
	 * Retrieves the most recent blogs by a specific author filtered by draft status.
	 * @param authorId the author ID
	 * @param limit the maximum number of blogs to return
	 * @param is_draft the draft status filter (true for drafts, false for published)
	 * @return a list of BlogModel objects representing the author's most recent blogs
	 */
	public List<BlogModel> getMostRecentBlogsByAuthor(int authorId, int limit, Boolean is_draft) {
	    List<BlogModel> recentBlogs = new ArrayList<>();

	    String query = "SELECT b.blog_id, b.title, b.publish_date, b.content, b.image, u.fullname AS author_name "
	            + "FROM blog b JOIN user u ON b.author_id = u.user_id WHERE b.author_id = ? AND is_draft = ? "
	            + "ORDER BY b.publish_date DESC LIMIT ?";

	    try (PreparedStatement stmt = dbConn.prepareStatement(query)) {
	        stmt.setInt(1, authorId);
	        stmt.setBoolean(2, is_draft);
	        stmt.setInt(3, limit);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            BlogModel blog = new BlogModel();
	            blog.setBlogId(rs.getInt("blog_id"));
	            blog.setTitle(rs.getString("title"));
	            blog.setPublishDate(rs.getDate("publish_date"));
	            blog.setContent(rs.getString("content"));
	            blog.setAuthorName(rs.getString("author_name"));
	            blog.setImage(rs.getString("image"));

	            recentBlogs.add(blog);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return recentBlogs;
	}

}