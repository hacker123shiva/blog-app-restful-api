package com.skyline.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyline.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
