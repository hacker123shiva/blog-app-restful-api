package com.skyline.blog.services;

import com.skyline.blog.payloads.CommentDto;

public interface CommentService {

	public CommentDto createComment(CommentDto commentDto, Integer postId);
	public void deleteComment(Integer commentId);
}
