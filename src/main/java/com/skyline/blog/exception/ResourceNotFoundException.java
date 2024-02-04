package com.skyline.blog.exception;

import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	
	private String resourceName;
	private String fieldName;
	private long fieldValue;
	private String extra;
	public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public ResourceNotFoundException(String resourceName, String fieldName, String extra) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,extra));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.extra=extra;
	}
	 
	
	
	
}
