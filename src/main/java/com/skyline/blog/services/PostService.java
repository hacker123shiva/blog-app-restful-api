package com.skyline.blog.services;

import java.util.List;

 

import com.skyline.blog.entities.Post;
import com.skyline.blog.payloads.PostDto;
import com.skyline.blog.payloads.PostResponse;

public interface PostService { 
 
	
	//create post
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	PostDto updatePost(PostDto postDto, Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	
	//get all posts
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get single post
	PostDto getPostbyId(Integer postId);
	
	//get all posts by category
	PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	//get all posts by user
	 PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
	
	 
	
	//search post
	PostResponse searchPosts(String keywords, Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
