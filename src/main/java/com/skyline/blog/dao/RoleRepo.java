package com.skyline.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyline.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
