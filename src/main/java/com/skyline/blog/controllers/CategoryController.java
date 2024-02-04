package com.skyline.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.blog.payloads.ApiResponse;
import com.skyline.blog.payloads.CategoryDto;
import com.skyline.blog.services.CategoryService;

 

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	
	@Autowired
	private CategoryService categoriesService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory=this.categoriesService.createCategory(categoryDto);
		return ResponseEntity.ok(createCategory);
	}
	//update
	@PutMapping("/{catId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
			@PathVariable("catId") Integer catId)
	{ 
		CategoryDto createCategory=this.categoriesService.updateCategory(categoryDto,catId);
		return ResponseEntity.ok(createCategory);
	}
	
	//delete
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId)
	{
		categoriesService.deleteCategory(catId);
		return ResponseEntity.ok(new ApiResponse("Category Deleted Succesfully",true));
	}
	//get
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory( 
			@PathVariable("catId") Integer catId)
	{ 
		CategoryDto createCategory=this.categoriesService.getCategory(catId);
		return ResponseEntity.ok(createCategory);
	}
	
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategory( )
	{ 
		List<CategoryDto> lst=this.categoriesService.getCategoryies();
		return ResponseEntity.ok(lst);
	}
	
	
	
	
}
