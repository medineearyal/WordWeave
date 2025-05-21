package com.wordweave.models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
 * Represents a blog post in the system.
 * Encapsulates blog details including content, author info, categories, and status.
 */
public class BlogModel {
	private int blogId;
	private String image;
	private String content;
	private String title;
	private Timestamp updatedAt;
	private int authorId;
	private Date publishDate;
	private Boolean isTrending;
	private Boolean isDraft;
	private int views;

	public Boolean getIsDraft() {
		return isDraft;
	}

	public void setIsDraft(Boolean isDraft) {
		this.isDraft = isDraft;
	}

	private List<CategoryModel> categories;
	private String authorName;
	private String authorBio;
	private String authorImage;

	public String getAuthorImage() {
		return authorImage;
	}

	public void setAuthorImage(String authorImage) {
		this.authorImage = authorImage;
	}

	public String getAuthorBio() {
		return authorBio;
	}

	public void setAuthorBio(String authorBio) {
		this.authorBio = authorBio;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public List<CategoryModel> getCategories() {
		return categories;
	}

	public void setCategories(List<CategoryModel> categories) {
		this.categories = categories;
	}

	public BlogModel() {
	}

	/**
	 * Full constructor with all main blog attributes except categories and author details.
	 * Used when loading a complete blog entity from DB.
	 * 
	 * @param blogId Unique ID of the blog
	 * @param image Image URL/path
	 * @param content Blog content/body
	 * @param title Blog title
	 * @param updatedAt Timestamp of last update
	 * @param authorId Author's user ID
	 * @param publishDate Date when blog was published
	*/
	public BlogModel(int blogId, String image, String content, String title, Timestamp updatedAt, int authorId,
			Date publishDate) {
		this.blogId = blogId;
		this.image = image;
		this.content = content;
		this.title = title;
		this.updatedAt = updatedAt;
		this.authorId = authorId;
		this.publishDate = publishDate;
	}

	/**
	 * Constructor without blogId and updatedAt.
	 * Useful when creating a new blog post before saving to DB (ID and updatedAt assigned later).
	 * 
	 * @param image Image URL/path
	 * @param content Blog content
	 * @param title Blog title
	 * @param authorId Author's user ID
	 * @param publishDate Date of publishing
	*/
	public BlogModel(String image, String content, String title, int authorId, Date publishDate) {
		this.image = image;
		this.content = content;
		this.title = title;
		this.authorId = authorId;
		this.publishDate = publishDate;
	}

	// Getters and setters

	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getAuthorId() {
		return authorId;
	}

	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Boolean getIsTrending() {
		return isTrending;
	}

	public void setIsTrending(Boolean isTrending) {
		this.isTrending = isTrending;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
}
