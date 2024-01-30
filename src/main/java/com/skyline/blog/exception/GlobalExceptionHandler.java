package com.skyline.blog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.skyline.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNFE){
		String message=resourceNFE.getMessage();
		ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ma){
		 Map<String,String> rsp=new HashMap<>();
		 ma.getBindingResult().getAllErrors().forEach(error->{
			  String fieldName=((FieldError)error).getField();
			  String message=error.getDefaultMessage();
			  rsp.put(fieldName, message);
		 });
		 return new ResponseEntity<Map<String,String>>(rsp,HttpStatus.BAD_REQUEST);
		 
	}
	
}
