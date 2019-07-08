package br.com.v1eira.taskmanager.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.v1eira.taskmanager.models.Task;

public interface RepoTask extends JpaRepository<Task, Long>{

}
