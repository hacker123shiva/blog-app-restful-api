package com.skyline.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.skyline.blog.dao.UserRepo;
import com.skyline.blog.entities.User;
import com.skyline.blog.exception.ResourceNotFoundException;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //loading user from database by username
		User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("user", "email",username));
		
		return user;
	}

}
