package com.skyline.blog.entities;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name="post_title",length=100, nullable=false)
	private String title;
	
	@Column(length=15000)
	private String content;
	
	@Column(length=1000)
	private String imageName;
	
	private Date addDate;
	
	@ManyToOne  
	@JoinColumn(name="category_id")
	private Category category;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

}
