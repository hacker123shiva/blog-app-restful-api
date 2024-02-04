package com.skyline.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@OneToMany(mappedBy="post", cascade=CascadeType.ALL )
//	@JsonManagedReference
	private Set<Comment> comments =new HashSet<>();
	
}
