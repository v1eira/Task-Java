package br.com.v1eira.taskmanager.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.v1eira.taskmanager.models.User;

public interface RepoUser extends JpaRepository<User, Long>{
	
	User findByEmail(String email);
	
}
