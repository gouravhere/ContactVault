package com.contractvault.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import com.contractvault.forms.ContactForm;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

	
	//add contact page handler
	@RequestMapping("/add")
	public String addContactView(Model model) {
		ContactForm contactForm = new ContactForm();
		model.addAttribute("contactForm",contactForm);
		return "/user/add_contact";
	}
	
	
}
