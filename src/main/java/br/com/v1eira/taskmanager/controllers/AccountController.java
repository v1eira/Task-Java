package br.com.v1eira.taskmanager.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.v1eira.taskmanager.models.User;
import br.com.v1eira.taskmanager.services.UserService;

@Controller
public class AccountController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "account/login";
	}
	
	@GetMapping("/registration")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("account/register");
		mv.addObject("user", new User());
		return mv;
	}
	
	@PostMapping("/registration")
	public ModelAndView register(@Valid User user, BindingResult result) {
		ModelAndView mv = new ModelAndView();
		User usr = userService.findByEmail(user.getEmail());
		if (usr != null) {
			result.rejectValue("email", "", "User already registered.");
		}
		if (result.hasErrors()) {
			mv.setViewName("account/register");
			mv.addObject("user", user);
		} else {
			userService.save(user);
			mv.setViewName("redirect:/login");
		}
		return mv;
	}

}
