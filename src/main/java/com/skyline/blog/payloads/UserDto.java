package com.skyline.blog.payloads;

 
 
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;
	@NotEmpty
	@Size(min=3, message="Username must be min of 3 characters")
	private String name;
	
	@Email(message="Email address is not valid")
	private String email;
	
	@NotEmpty
	@Size(min=3, max=10, message="Password must be min of 3 chars and maximum of 10 chars")
//	@Pattern(regex=)
	private String password;
	
	@NotEmpty
	private String about;
}
