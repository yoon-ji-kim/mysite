package com.douzone.mysite.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.douzone.mysite.vo.UserVo;

@SuppressWarnings("serial")
public class User implements UserDetails {
	@Autowired
	UserVo uservo;
	
	boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialNonExpired;
    boolean enabled = false;
    List<GrantedAuthority> roles = new ArrayList<>();
    
    public User(UserVo uservo) {
    	super();
    	this.uservo = uservo;
	}
    
    public User(String subject, String string, Collection<? extends GrantedAuthority> authorities) {
		// TODO Auto-generated constructor stub
	}

	public UserVo getUser() {
		return this.uservo;
    }
    
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.uservo.getPassword();
	}

	@Override
	public String getUsername() {
		return this.uservo.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.credentialNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}
