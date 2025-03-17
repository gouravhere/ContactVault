package com.contractvault.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.contractvault.entities.User;
import com.contractvault.forms.UserForm;
import com.contractvault.helpers.Message;
import com.contractvault.helpers.MessageType;
import com.contractvault.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index() {
		return "redirect:/home";
	}
	
	@RequestMapping("/home")
	public String home(Model model) {
		System.out.println("home Page Handler");
		return "Home";
	}
	@RequestMapping("/about")
	public String about(Model model) {
		System.out.println("about Page Handler");
		return "About";
	}
	@RequestMapping("/services")
	public String services(Model model) {
		System.out.println("services loading");
		return "Services";
	}
	// contact page

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    // this is showing login page
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // registration page
    @GetMapping("/register")
    public String register(Model model) {
    	UserForm userForm = new UserForm();
    	//userForm.setName("Gourav");
    	model.addAttribute("userForm",userForm);
        return "register";
    }

 // processing register
    @RequestMapping(value ="/do-register",method = RequestMethod.POST)
    public String processingRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
    	
    	System.out.println(userForm);
    	if(rBindingResult.hasErrors()) {
    		return"register";
    	}
//    	User user=User.builder()
//    			.name(userForm.getName())
//    			.email(userForm.getEmail())
//    			.password(userForm.getPassword())
//    			.about(userForm.getAbout())
//    			.phoneNumber(userForm.getPhoneNumber())
//    			.profilePic("https://www.google.com/imgres?q=default%20profile%20pic&imgurl=https%3A%2F%2Fgimgs2.nohat.cc%2Fthumb%2Ff%2F640%2Fmale-face-icon-default-profile-image--c3f2c592f9.jpg&imgrefurl=https%3A%2F%2Fnohat.cc%2Ff%2Fmale-face-icon-default-profile-image%2Fc3f2c592f9-202301261009.html&docid=oCgoYuYOOkhVDM&tbnid=-BlnCYBL3fTDrM&vet=12ahUKEwjGjYWItKqKAxVRc_UHHTB7F18QM3oECGYQAA..i&w=640&h=640&hcb=2&ved=2ahUKEwjGjYWItKqKAxVRc_UHHTB7F18QM3oECGYQAA")
//    			.build();
//    
    	User user = new User();
    	
    	user.setName(userForm.getName());
    	user.setEmail(userForm.getEmail());
    	user.setPassword(userForm.getPassword());
    	user.setAbout(userForm.getAbout());
    	user.setPhoneNumber(userForm.getPhoneNumber());
    	user.setEnabled(true);
    	user.setProfilePic("https://www.google.com/");
    	
    	User savedUser = userService.saveUser(user);
    	System.out.println(savedUser);
    	Message message = Message.builder().content("Registration Successfull !!").type(MessageType.GREEN).build();
    	session.setAttribute("message", message);
        return "redirect:/register";
    }

}

