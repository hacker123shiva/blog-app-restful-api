package com.skyline.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyline.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
