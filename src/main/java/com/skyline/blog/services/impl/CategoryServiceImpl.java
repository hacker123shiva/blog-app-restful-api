package com.skyline.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.blog.dao.CategoryRepo;
import com.skyline.blog.entities.Category;
import com.skyline.blog.exception.ResourceNotFoundException;
import com.skyline.blog.payloads.CategoryDto;
import com.skyline.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category cat= modelMapper.map(categoryDto, Category.class);
		Category addedCat=categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category cat =categoryRepo.findById(categoryId)
				.orElseThrow(()->new ResourceNotFoundException("catgory","catgory Id", categoryId));
	 
	     cat.setCategoryTitle(categoryDto.getCategoryTitle()); 
         cat.setCategoryDescription(categoryDto.getCategoryDescription());
           
		    Category updatedCat=   categoryRepo.save(cat);
		    return this.modelMapper.map(updatedCat,CategoryDto.class);
		
		
	 
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		 Category cat =this.categoryRepo.findById(categoryId).orElseThrow(
				 ()->new ResourceNotFoundException("category","category id", categoryId));
		 this.categoryRepo.delete(cat);

	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		 Category cat =this.categoryRepo.findById(categoryId).orElseThrow(
				 ()->new ResourceNotFoundException("category","category id", categoryId));
		 this.categoryRepo.delete(cat);
		return modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategoryies() {
		List<Category> lst= categoryRepo.findAll();
		List<CategoryDto> ldto=lst.stream().
				map((category)->modelMapper.
				map(category,CategoryDto.class)).
				collect(Collectors.toList());
		return ldto;
	}

}
