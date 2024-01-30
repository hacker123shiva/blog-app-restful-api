package com.skyline.blog.services;

import java.util.List;

import com.skyline.blog.payloads.CategoryDto;

public interface CategoryService {

	//create category
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	//update 
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

	
	//delete
	public void deleteCategory(Integer categoryId);
	
	//get
	public CategoryDto getCategory(Integer categoryId);
	
	
	//get all
	public List<CategoryDto> getCategoryies();
	
	
}
