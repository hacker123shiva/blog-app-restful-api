package com.skyline.blog.payloads;

import java.util.Date;

import com.skyline.blog.entities.Category;
import com.skyline.blog.entities.User;

 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addDate;
	
	private UserDto user;
	
	private CategoryDto category;
	
	
}
