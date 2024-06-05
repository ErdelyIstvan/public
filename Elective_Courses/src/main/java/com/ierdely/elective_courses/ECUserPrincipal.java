package com.ierdely.elective_courses;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ierdely.elective_courses.entities.User;

public class ECUserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = -7102612371142444716L;
	
	private User user;

	public ECUserPrincipal(User user) {
		
		this.user = user;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;//user.getRoles();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

}
