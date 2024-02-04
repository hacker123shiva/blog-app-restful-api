package com.skyline.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.blog.dao.CommentRepo;
import com.skyline.blog.dao.PostRepo;
import com.skyline.blog.entities.Comment;
import com.skyline.blog.entities.Post;
import com.skyline.blog.exception.ResourceNotFoundException;
import com.skyline.blog.payloads.CommentDto;
import com.skyline.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Post post =this.postRepo.findById(postId). 
        		orElseThrow(()->new ResourceNotFoundException("Post", "Post id",postId));
	 Comment  comment=this.modelMapper.map(commentDto,Comment.class);
	 comment.setPost(post);
//	 comment.setUser(post.getUser());
	 Comment savedComment=this.commentRepo.save(comment);
	 
	 return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment com=this.commentRepo.findById(commentId).
				orElseThrow(()->new ResourceNotFoundException("Comment","comment id",commentId));
		
	}

}
