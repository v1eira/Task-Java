package br.com.v1eira.taskmanager.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.v1eira.taskmanager.models.Task;

public interface RepoTask extends JpaRepository<Task, Long>{
	
	@Query("SELECT t FROM Task t WHERE t.user.email = :emailUser")
	List<Task> loadTasksByUser(@Param("emailUser") String email);

}
