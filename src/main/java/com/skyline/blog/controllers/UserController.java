package com.skyline.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyline.blog.payloads.ApiResponse;
import com.skyline.blog.payloads.UserDto;
import com.skyline.blog.services.UserService;

 

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired //In version Spring no need to write @Autowired annnotation
	private UserService userService;
	
	//Post-create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto createUserDto=this.userService.createUser(userDto);
		return new  ResponseEntity<>(createUserDto,HttpStatus.CREATED);
	}
	
	//Put-Update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId")Integer userId){
	 
		UserDto updatedUser=userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
		
	}
	
	//Delete delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
		 this.userService.deleteUser(uid);
//		 return ResponseEntity.ok(Map.of("message","User Deleted Succesfully"));
		 return new ResponseEntity<ApiResponse>( new ApiResponse("User deleted Succesfully",true),HttpStatus.CREATED);
	}
	
	
	//Get - get All user
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUsers());
	}
	
	//Get single user by id
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
	
	
	
	
	
}
