package com.skyline.blog.controllers;

 
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.skyline.blog.config.AppConstants;
import com.skyline.blog.payloads.ApiResponse;
import com.skyline.blog.payloads.PostDto;
import com.skyline.blog.payloads.PostResponse;
import com.skyline.blog.services.FileService;
import com.skyline.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
    private String path;
	
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
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {

		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
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
	public ResponseEntity<PostResponse> searchPostByTitle(@PathVariable("keywords") String keywords, 
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER, required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false)Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false)String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false)String sortDir){
		 PostResponse postResponse=this.postService.searchPosts(keywords,pageNumber, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(postResponse);
	}
	
	//post image upload 
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file
			, @PathVariable Integer postId) throws IOException {
		
		PostDto postDto=this.postService.getPostbyId(postId);
	    System.out.println(postDto);
		String fileName= this.fileService.uploadImage(path, file);
 
		postDto.setImageName(fileName);
		postService.updatePost(postDto, postId);
		
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//method to serve files
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response
			) throws IOException{
		
		InputStream resource=this.fileService.getResource(path,  imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE );
		StreamUtils.copy(resource, response.getOutputStream());
	}
}
