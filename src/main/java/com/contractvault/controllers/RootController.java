package com.contractvault.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.contractvault.entities.User;
import com.contractvault.helpers.Helper;
import com.contractvault.services.UserService;

@ControllerAdvice
public class RootController {
	@Autowired
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@ModelAttribute
	public void addLoggedInUserInformation(Model model,Authentication authentication) {
		if(authentication==null) {
			return;
		}
		String userName =Helper.getEmailOfLoggedInUser(authentication);
		
		logger.info("User Logger in ",userName);
		User user = userService.getUserByEmail(userName);
	
			model.addAttribute("loggedInUser",user);
			logger.info(user.getName());
			logger.info(user.getEmail());
			System.out.println("User profile");
		
		
	}

}
