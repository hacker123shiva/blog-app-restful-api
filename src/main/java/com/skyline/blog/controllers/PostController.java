package com.skyline.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 
import com.skyline.blog.payloads.ApiResponse;
import com.skyline.blog.payloads.PostDto;
import com.skyline.blog.payloads.PostResponse;
import com.skyline.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
 
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
	PostDto createPost =this.postService.createPost(postDto, userId, categoryId);
	return new ResponseEntity<PostDto> (createPost,HttpStatus.CREATED);
	}
 
	//get by user
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId, 
			@RequestParam(value="pageNumber",defaultValue="0", required =false)Integer pageNumber, 
			@RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="postId",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
		PostResponse postResponse=this.postService.getPostsByUser(userId,pageNumber, pageSize, sortBy,sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
	}
	
	//get by category
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity< PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
			@RequestParam(value="pageNumber",defaultValue="0", required=false)Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="postId",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
		PostResponse postResponse=this.postService.getPostsByCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue="0", required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue="10",required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue="postId",required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue="asc",required=false)String sortDir){
		return ResponseEntity.ok(postService.getAllPost(pageNumber,pageSize,sortBy, sortDir));
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		return ResponseEntity.ok(postService.getPostbyId(postId));
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return ResponseEntity.ok(new ApiResponse("Post is successfully deleted !!",true));
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatePost);
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDto> postDtos=this.postService.searchPosts(keywords);
		return ResponseEntity.ok(postDtos);
	}
}
