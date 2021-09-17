package com.in28minutes.springboot.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner{

	public static final Logger log=LoggerFactory.getLogger(UserCommandLineRunner.class);
	
	@Autowired
	private UserRepository repository;
	
	@Override
	public void run(String... args) throws Exception {
		repository.save(new User("vikas","Admin"));
		repository.save(new User("Naveen","user"));
		repository.save(new User("krishna","Admin"));
		repository.save(new User("Jyothi","user"));
		repository.save(new User("tu tu","user"));
		 
		for(User user:repository.findAll()) {
			log.info(user.toString());	
		}
	   System.out.println(repository.count());
	   log.info("Admin users are.......");
	   log.info("-----------------------------");
	   for(User user:repository.findByRole("Admin")) {
			log.info(user.toString());	
		}
	   
	   
	   
	   
	   
	   
	}

	  
	
}
