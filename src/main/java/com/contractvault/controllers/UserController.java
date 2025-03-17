package com.contractvault.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
//	private Logger logger = LoggerFactory.getLogger(UserController.class);
//	
//	@Autowired
//	private UserService userService;
//	
//	@ModelAttribute
//	public void addLoggedInUserInformation(Model model,Authentication authentication) {
//		String userName =Helper.getEmailOfLoggedInUser(authentication);
//		
//		logger.info("User Logger in ",userName);
//		User user = userService.getUserByEmail(userName);
//		model.addAttribute("loggedInUser",user);
//		logger.info(user.getName());
//		logger.info(user.getEmail());
//		System.out.println("User profile");
//	}

	//user dashboard page
	
	@RequestMapping(value = "/dashboard")
	public String userDashboard() {
        System.out.println("User dashboard");
        return "user/dashboard";
    }
	//user profile page
	@RequestMapping(value = "/profile")
	public String userProfile(Model model,Authentication authentication) {
		
        return "user/profile";
    }
	

	
}
