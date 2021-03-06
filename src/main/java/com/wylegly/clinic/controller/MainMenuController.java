package com.wylegly.clinic.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class MainMenuController {

	@GetMapping(value = "/home")
	public String main(Locale locale, Model model) {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		System.out.println(dateFormat.format(date));
		model.addAttribute("serverTime", dateFormat.format(date));
		return "home";

	}


	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user(@RequestParam("username") String username, @RequestParam("userPassword") String password,
			Model model) {

		System.out.println("User page requested");
		// model.addAttribute("userFirstName", user.getFirstName());
		model.addAttribute("username", username);
		return "user";
	}
}
