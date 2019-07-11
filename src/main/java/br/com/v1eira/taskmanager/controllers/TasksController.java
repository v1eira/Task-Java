package br.com.v1eira.taskmanager.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
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
import br.com.v1eira.taskmanager.models.User;
import br.com.v1eira.taskmanager.repos.RepoTask;
import br.com.v1eira.taskmanager.services.UserService;

@Controller
@RequestMapping("/tasks")
public class TasksController {

	@Autowired
	private RepoTask repoTask;
	
	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tasks/list");
		String emailUser = request.getUserPrincipal().getName();
		mv.addObject("tasks", repoTask.loadTasksByUser(emailUser));
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
	public ModelAndView insert(@Valid Task task, BindingResult result, HttpServletRequest request) {
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
			String emailUser = request.getUserPrincipal().getName();
			User userLoged = userService.findByEmail(emailUser);
			task.setUser(userLoged);
			repoTask.save(task);
			mv.setViewName("redirect:/tasks/list");
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
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") Long id) {
		repoTask.deleteById(id);
		return "redirect:/tasks/list";
	}
	
	@GetMapping("/finish/{id}")
	public String finish(@PathVariable("id") Long id) {
		Task task = repoTask.getOne(id);
		task.setFinished(true);
		repoTask.save(task);
		return "redirect:/tasks/list";
	}

}
