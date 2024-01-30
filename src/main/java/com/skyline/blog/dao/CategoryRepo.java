package com.skyline.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.skyline.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
