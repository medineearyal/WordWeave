package com.wordweave.models;

public class FavoriteBlogModel {
	private int userId;
	private int blogId;
	
	private UserModel user;
	private BlogModel blog;
	
	public FavoriteBlogModel(int userId, int blogId) {
		super();
		this.userId = userId;
		this.blogId = blogId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public BlogModel getBlog() {
		return blog;
	}
	public void setBlog(BlogModel blog) {
		this.blog = blog;
	}
}
