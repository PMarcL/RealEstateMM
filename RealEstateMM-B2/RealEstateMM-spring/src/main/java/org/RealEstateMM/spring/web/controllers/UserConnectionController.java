package org.RealEstateMM.spring.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserConnectionController 
{
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
