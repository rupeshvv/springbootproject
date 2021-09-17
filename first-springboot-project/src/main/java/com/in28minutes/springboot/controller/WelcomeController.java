package com.in28minutes.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in28minutes.springboot.configuration.BasicConfiguration;
import com.in28minutes.springboot.service.WelcomeService;

@RestController
public class WelcomeController {
	@Autowired
	private WelcomeService service;
	
	@Autowired
	BasicConfiguration configuration;
	
	@GetMapping("/welcome")
	public String welcome() {
		return service.retriveWelcomeMessage();
	}
	
	@GetMapping("/dynamic-configuration")
	public Map dynamicConfiguration() {
		
		Map map=new HashMap();
	
		map.put("message", configuration.getMessage());
		map.put("number",configuration.getNumber());
		map.put("value", configuration.isValue());
		
		
	
		
		return map;
	}
}

