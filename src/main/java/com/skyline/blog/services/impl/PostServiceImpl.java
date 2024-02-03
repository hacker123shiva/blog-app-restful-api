package com.skyline.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.skyline.blog.dao.CategoryRepo;
import com.skyline.blog.dao.PostRepo;
import com.skyline.blog.dao.UserRepo;
import com.skyline.blog.entities.Category;
import com.skyline.blog.entities.Post;
import com.skyline.blog.entities.User;
import com.skyline.blog.exception.ResourceNotFoundException;
import com.skyline.blog.payloads.PostDto;
import com.skyline.blog.payloads.PostResponse;
import com.skyline.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
 
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user=this.userRepo.
		findById(userId).
	    orElseThrow(()->new ResourceNotFoundException("User ", "user id", userId));
		
		Category category =this.categoryRepo.
				findById(categoryId).
			    orElseThrow(()->new ResourceNotFoundException("Category ", "category id", categoryId));
		
		Post post =this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
	 Post newPost=this.postRepo.save(post);
	 return modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		 Post post= this.postRepo.findById(postId). 
				 orElseThrow( ()->new ResourceNotFoundException("Post ", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
	 Post upd=	this.postRepo.save(post);
		return this.modelMapper.map(upd, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		 Post post= this.postRepo.findById(postId). 
		 orElseThrow( ()->new ResourceNotFoundException("Post ", "post id", postId));
		 this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
	 
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else if(sortDir.equalsIgnoreCase("dsc")) {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> posts=pagePost.getContent();
		
		List<PostDto> postDtos=  posts.stream().
		map((post)->modelMapper.
		map(post, PostDto.class)).
		collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		 postResponse.setContent(postDtos);
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setLastpage(pagePost.isLast());
		 postResponse.setTotalElements(pagePost.getTotalElements());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalpages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public PostDto getPostbyId(Integer postId ) {

		 Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post ", "post id", postId));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostResponse getPostsByCategory(Integer categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		
		Category cat=this.categoryRepo.
		 findById(categoryId).
		 orElseThrow(()->new ResourceNotFoundException("Catgory ", "category id", categoryId));
		 
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else if(sortDir.equalsIgnoreCase("dsc")) {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber,pageSize,sort);
	    Page<Post> pagePost=	this.postRepo.findByCategory(cat,p);
	   List<Post> posts=       pagePost.getContent();	    
		 
		List<PostDto> postDtos=posts.
				stream().
				map((post)->this.modelMapper.
				map(post, PostDto.class)).
				collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		 postResponse.setContent(postDtos);
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setLastpage(pagePost.isLast());
		 postResponse.setTotalElements(pagePost.getTotalElements());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalpages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public  PostResponse getPostsByUser(Integer userId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		User us=this.userRepo.
				 findById(userId).
				 orElseThrow(()->new ResourceNotFoundException("User ", "user id", userId));
		 
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else if(sortDir.equalsIgnoreCase("dsc")) {
			sort=Sort.by(sortBy).descending();
		}
		Pageable p=PageRequest.of(pageNumber,pageSize,sort);
	    Page<Post> pagePost=	this.postRepo.findByUser(us,p);
	   List<Post> posts=       pagePost.getContent();	    
		 
		List<PostDto> postDtos=posts.
				stream().
				map((post)->this.modelMapper.
				map(post, PostDto.class)).
				collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		 postResponse.setContent(postDtos);
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setLastpage(pagePost.isLast());
		 postResponse.setTotalElements(pagePost.getTotalElements());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalpages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public PostResponse searchPosts(String keywords, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		keywords=keywords.toLowerCase();
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort=Sort.by(sortBy).ascending();
		}else if(sortDir.equalsIgnoreCase("dsc")) {
			sort=Sort.by(sortBy).descending();
		}
		
		Pageable p=PageRequest.of(pageNumber,pageSize,sort);
		 Page<Post> pagePost=this.postRepo.findByTitleOrContentContaining(keywords,p);
		 List<Post> posts=pagePost.getContent();
		List<PostDto> postDtos= posts.stream().
				 map((post)->this.modelMapper.
				 map(post,PostDto.class)).
				 collect(Collectors.toList());
		
		PostResponse pr=new PostResponse();
		pr.setContent(postDtos);
		pr.setPageNumber(pagePost.getNumber());
		pr.setLastpage(pagePost.isLast());
		 pr.setTotalElements(pagePost.getTotalElements());
		pr.setPageSize(pagePost.getSize());
		 pr.setTotalpages(pagePost.getTotalPages());
		
		return pr;
	}

}
