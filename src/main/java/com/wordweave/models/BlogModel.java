package com.wordweave.models;

import java.sql.Timestamp;
import java.util.List;
import java.sql.Date;

public class BlogModel {
	private int blogId;
	private String image;
	private String content;
	private String title;
	private Timestamp updatedAt;
	private int authorId;
	private Date publishDate;

	private List<CategoryModel> categories;
	private String authorName;

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
}
