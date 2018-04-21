package com.example.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/hello")
@RestController
public class Hello {
	
	@RequestMapping("/world")
	public String hello(){
		return "HelloWorld!";
	}
}
