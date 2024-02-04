package com.skyline.blog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skyline.blog.entities.Category;
import com.skyline.blog.entities.Post;
import com.skyline.blog.entities.User;

public interface PostRepo extends JpaRepository<Post,Integer>{
	Page<Post> findAll(Pageable pageable);
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	Page<Post> findByCategory(Category category,Pageable p);
	Page<Post> findByUser(User user,Pageable p);
//List<Post> findByTitleContaining(String title );
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
	List<Post> findByTitleOrContentContaining(String keyword);
	
	//get content after applying pagination and sorting
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
	Page<Post> findByTitleOrContentContaining(String keyword,Pageable p);
	

	
}
