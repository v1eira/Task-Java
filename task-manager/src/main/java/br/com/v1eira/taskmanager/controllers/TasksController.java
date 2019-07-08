package br.com.v1eira.taskmanager.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.v1eira.taskmanager.models.Task;
import br.com.v1eira.taskmanager.repos.RepoTask;

@Controller
@RequestMapping("/tasks")
public class TasksController {

	@Autowired
	private RepoTask repoTask;

	@GetMapping("/list")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tasks/list");
		mv.addObject("tasks", repoTask.findAll());
		return mv;
	}

	@GetMapping("/insert")
	public ModelAndView insert() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tasks/insert");
		mv.addObject("task", new Task());
		return mv;
	}

	@PostMapping("/insert")
	public ModelAndView insert(@Valid Task task, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (task.getExpirationDate() == null) {
			result.rejectValue("expirationDate", "task.expirationDateInvalid", "Expiration date is mandatory");
		} else {
			if (task.getExpirationDate().before(new Date())) {
				result.rejectValue("expirationDate", "task.expirationDateInvalid",
						"Expiration date can't be earlier than current date");
			}
		}
		if (result.hasErrors()) {
			mv.setViewName("tasks/insert");
			mv.addObject(task);
		} else {
			mv.setViewName("redirect:/tasks/list");
			repoTask.save(task);
		}
		return mv;
	}
	
	@GetMapping("/update/{id}")
	public ModelAndView update(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView();
		Task task = repoTask.getOne(id);
		mv.addObject("task", task);
		mv.setViewName("tasks/update");
		return mv;
		
	}
	
	@PostMapping("/update")
	public ModelAndView update(@Valid Task task, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		if (task.getExpirationDate() == null) {
			result.rejectValue("expirationDate", "task.expirationDateInvalid", "Expiration date is mandatory");
		} else {
			if (task.getExpirationDate().before(new Date())) {
				result.rejectValue("expirationDate", "task.expirationDateInvalid",
						"Expiration date can't be earlier than current date");
			}
		}
		if (result.hasErrors()) {
			mv.setViewName("tasks/update");
			mv.addObject(task);
		} else {
			mv.setViewName("redirect:/tasks/list");
			repoTask.save(task);
		}
		return mv;
	}

}
