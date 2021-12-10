package com.example.music.service;

import java.util.ArrayList;
import java.util.Optional;

import com.example.music.user.UserRepository;
import com.example.music.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			Users check=userRepository.findByEmail(username).get();
			return new User(check.getEmail(),check.getPassword(), new ArrayList<>());
		}
		catch (Exception e) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}
}
