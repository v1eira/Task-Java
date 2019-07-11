package br.com.v1eira.taskmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.v1eira.taskmanager.models.User;
import br.com.v1eira.taskmanager.repos.RepoUser;

@Service
public class UserService {
	
	@Autowired
	private RepoUser repoUser;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public User findByEmail(String email) {
		return repoUser.findByEmail(email);
	}
	
	public void save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repoUser.save(user);
	}

}
