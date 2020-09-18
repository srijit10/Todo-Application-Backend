package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.rest.webservices.restfulwebservices.jwt.JwtInMemoryUserDetailsService;
import com.in28minutes.rest.webservices.restfulwebservices.jwt.JwtUserDetails;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserJpaResource {

	@Autowired
	private UserJpaRepository ur;

	// PUT request--creating new users for todo application
	@PostMapping("/addUser")
	public String createUser(@RequestBody User user) {

		User t = ur.findByUsername(user.getUsername());
		if (t != null)
			return "Account already exists with this username";
		else {
			
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			ur.save(user);
			JwtInMemoryUserDetailsService.inMemoryUserList.add
			(new JwtUserDetails(1L, user.getUsername(),user.getPassword(), "ROLE_USER_2"));
			return "Account successfully created";
		}
	}
}
